package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);
    int updateOrderStatus(@Param("id") Long id,
                          @Param("status") String status,
                          @Param("cancelReason") String cancelReason);
    Order selectOrderById(Long id);
    Order selectOrderByNumber(String orderNumber);
    Page<Order> selectOrdersByUser(Long userId, Page<Order> page);
    List<Order> selectOrdersByStatus(String status);

    // 更新订单金额
    int updateOrderAmount(@Param("id") Long id, @Param("totalAmount") BigDecimal totalAmount);

    // 批量更新预订的订单ID
    int updateBookingsOrderId(@Param("orderId") Long orderId,
                              @Param("bookingIds") List<Long> bookingIds);

    Page<Order> getAllOrders(Page<Order> page);

    /**
     * 检查订单是否可以取消（返回详细信息）
     */
    Map<String, Long> checkOrderCancelable(@Param("orderId") Long orderId);
}