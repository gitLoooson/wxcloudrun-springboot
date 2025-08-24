package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.enums.OrderStatus;
import com.tencent.wxcloudrun.model.Booking;
import com.tencent.wxcloudrun.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OrderService {
    private final OrderMapper orderMapper;
    private final BookingService bookingService;

    /**
     * 取消订单（同时取消所有关联预订）
     */
    public boolean cancelOrder(Long orderId, String cancelReason) {
        // 1. 取消所有关联预订
        bookingService.cancelBookingsByOrder(orderId);

        // 2. 更新订单状态为已取消
        return orderMapper.updateOrderStatus(orderId, OrderStatus.CANCELLED.getCode(), cancelReason) > 0;
    }

    /**
     * 确认订单
     */
    public boolean confirmOrder(Long orderId) {
        return orderMapper.updateOrderStatus(orderId, OrderStatus.CONFIRMED.getCode(), null) > 0;
    }

    /**
     * 获取订单详情（包含预订信息）
     */
    public Order getOrderDetail(Long orderId) {
        Order order = orderMapper.selectOrderById(orderId);
        if (order != null) {
            List<Booking> bookings = bookingService.selectBookingsByOrder(orderId);
            order.setBookings(bookings);
        }
        return order;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderMapper.selectOrdersByUser(userId);
    }

    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

}