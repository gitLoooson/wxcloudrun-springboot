package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.BookingMapper;
import com.tencent.wxcloudrun.dto.BookingResult;
import com.tencent.wxcloudrun.model.Booking;
import com.tencent.wxcloudrun.model.BookingSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingMapper bookingMapper;
    private final PricingService pricingService;

    public BookingResult createBooking(Long userId, LocalDate date, Long courtId, Long timeSlotId) {
        if (bookingMapper.existsBooking(date, courtId, timeSlotId)) {
            return BookingResult.error("该时间段已被预订");
        }

        BigDecimal price = pricingService.getActualPrice(date, courtId, timeSlotId);

        Booking booking = new Booking();
        booking.setCourtId(courtId);
        booking.setTimeSlotId(timeSlotId);
        booking.setDate(date);
        booking.setUserId(userId);
        booking.setPrice(price);
        booking.setStatus("confirmed");

        int result = bookingMapper.insertBooking(booking);
        return result > 0 ? BookingResult.success("预订成功", booking) : BookingResult.error("预订失败");
    }

    public boolean cancelBooking(Long bookingId) {
        return bookingMapper.updateBookingStatus(bookingId, "cancelled") > 0;
    }

    public List<BookingSchedule> getDailySchedule(LocalDate date) {
        return bookingMapper.selectDailySchedule(date);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingMapper.selectBookingsByUser(userId);
    }
}
