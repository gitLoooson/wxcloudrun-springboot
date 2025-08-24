package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Booking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookingMapper {
    // 批量插入（新增）
    int insertBatchBookings(@Param("bookings") List<Booking> bookings);


    // 批量取消预订（根据订单ID）
    int cancelBookingsByOrder(@Param("orderId") Long orderId,
                              @Param("status") String status);

    // 根据订单ID获取预订ID列表
    List<Long> selectBookingIdsByOrder(@Param("orderId") Long orderId);

    int cancelBatchBookings(@Param("bookingIds") List<Long> bookingIds,@Param("status") String status);

    // 根据订单ID查询预订
    List<Booking> selectBookingsByOrder(@Param("orderId") Long orderId);
}