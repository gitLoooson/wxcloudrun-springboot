package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.BookingMapper;
import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.dto.BatchBookingResult;
import com.tencent.wxcloudrun.dto.BookingRequest;
import com.tencent.wxcloudrun.dto.TimeSlotPrice;
import com.tencent.wxcloudrun.enums.OrderStatus;
import com.tencent.wxcloudrun.model.Booking;
import com.tencent.wxcloudrun.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookingService {
    private final BookingMapper bookingMapper;
    private final PricingService pricingService;
    private final OrderMapper orderMapper;
    private final UserAccountService userAccountService;

    /**
     * 原子性批量预订 - 任何一个失败就全部回滚
     */
    private BatchBookingResult createAtomicBatchBookings(Long userId, List<BookingRequest> bookingRequests) {
        BatchBookingResult result = new BatchBookingResult();
        List<Booking> bookingsToInsert = new ArrayList<>();

        List<TimeSlotPrice> timeSlotPricesByCourt = pricingService.getTimeSlotPricesByCourt(bookingRequests.get(0).getDate(), bookingRequests.get(0).getCourtId());

        // 1. 预先计算所有价格和创建预订对象
        for (BookingRequest request : bookingRequests) {
            Optional<TimeSlotPrice> firstMatching = timeSlotPricesByCourt.stream().findFirst().filter(timeSlotPrices -> timeSlotPrices.getTimeSlotId().equals(request.getTimeSlotId()));
            if(firstMatching.isPresent()) {
                Booking booking = new Booking();
                booking.setCourtId(request.getCourtId());
                booking.setTimeSlotId(request.getTimeSlotId());
                booking.setDate(request.getDate());
                booking.setUserId(userId);
                booking.setPrice(firstMatching.get().getPrice());
                booking.setStatus("confirmed");
                booking.setCreatedAt(LocalDateTime.now());
                booking.setUpdatedAt(LocalDateTime.now());
                bookingsToInsert.add(booking);
            }else{
                throw new RuntimeException("请联系管理员!时间段" + request.getTimeSlotId() +"未设置价格!");
            }
        }

        // 2. 批量插入（如果任何一条失败，整个事务回滚）
        try {
            int successCount = bookingMapper.insertBatchBookings(bookingsToInsert);

            result.setSuccessCount(successCount);
            result.setSuccessfulBookings(bookingsToInsert);
            result.setTotalRequests(bookingRequests.size());

        } catch (DuplicateKeyException e) {
            // 唯一键冲突，事务会自动回滚
            throw new RuntimeException("预订冲突：存在重复的时间段", e);
        } catch (Exception e) {
            // 其他异常，事务会自动回滚
            throw new RuntimeException("批量预订失败: " + e.getMessage(), e);
        }

        return result;
    }

    /**
     * 创建带订单的原子性批量预订
     */
    @Transactional(rollbackFor = Exception.class)
    public Order createOrderWithAtomicBookings(Long userId, List<BookingRequest> bookingRequests) {
        // 1. 生成订单号
        String orderNumber = generateOrderNumber();

        // 2. 创建预订（原子性操作）
        BatchBookingResult bookingResult = createAtomicBatchBookings(userId, bookingRequests);

        if (bookingResult.getSuccessfulBookings().isEmpty()) {
            throw new RuntimeException("预订冲突：存在已选择的时间段!");
        }

        // 3. 计算订单总金额
        BigDecimal totalAmount = bookingResult.getSuccessfulBookings().stream()
                .map(Booking::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. 创建订单
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING.getCode());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        orderMapper.insertOrder(order);

        // 5. 更新预订的订单ID
        List<Long> bookingIds = bookingResult.getSuccessfulBookings().stream()
                .map(Booking::getId)
                .collect(Collectors.toList());

        orderMapper.updateBookingsOrderId(order.getId(), bookingIds);

        // 扣款支付
        boolean paymentSuccess = userAccountService.consume(
                userId,
                order.getTotalAmount(),
                order.getId(),
                "支付订单：" + order.getOrderNumber()
        );

        if (paymentSuccess) {
            // 更新订单状态为已确认
            orderMapper.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED.getCode(), null);
        }
        return order;
    }

    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis() +
                String.format("%04d", (int)(Math.random() * 10000));
    }


    /**
     * 根据订单ID取消所有关联预订
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelBookingsByOrder(Long orderId,String cancelReason) {
        try {
            Order order = orderMapper.selectOrderById(orderId);
            if (order == null) {
                throw new RuntimeException("订单不存在");
            }
            Map<String, Boolean> stringObjectMap = orderMapper.checkOrderCancelable(orderId);
            if(!stringObjectMap.get("cancelStatus")){
                throw new RuntimeException("订单与预定时间不足24小时，不能取消!");
            }
            // 3. 执行退款
            boolean refundSuccess = userAccountService.refund(
                    order.getUserId(),
                    order.getTotalAmount(),
                    orderId,
                    "订单取消退款：" + (cancelReason != null ? cancelReason : "用户取消")
            );
            // 批量取消预订
            int affectedRows = bookingMapper.cancelBookingsByOrder(orderId, "cancelled");
        } catch (Exception e) {
            throw new RuntimeException("取消订单预订失败: " + e.getMessage(), e);
        }
    }

    /**
     * 根据订单查询所有的预定
     * @param orderId
     * @return
     */
    public List<Booking> selectBookingsByOrder(Long orderId) {
        return bookingMapper.selectBookingsByOrder(orderId);
    }
}
