package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Booking;
import com.tencent.wxcloudrun.model.BookingSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookingMapper {
    int insertBooking(Booking booking);
    int updateBookingStatus(@Param("id") Long id, @Param("status") String status);
    int deleteBooking(Long id);

    Booking selectBookingById(Long id);
    List<Booking> selectBookingsByUser(Long userId);
    List<Booking> selectBookingsByDate(LocalDate date);

    boolean existsBooking(@Param("date") LocalDate date,
                          @Param("courtId") Long courtId,
                          @Param("timeSlotId") Long timeSlotId);

    List<BookingSchedule> selectDailySchedule(LocalDate date);
}