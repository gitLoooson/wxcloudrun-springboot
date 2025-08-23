package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.BookingResult;
import com.tencent.wxcloudrun.model.Booking;
import com.tencent.wxcloudrun.model.BookingSchedule;
import com.tencent.wxcloudrun.service.impl.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResult> createBooking(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long courtId,
            @RequestParam Long timeSlotId) {

        BookingResult result = bookingService.createBooking(userId, date, courtId, timeSlotId);
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        boolean success = bookingService.cancelBooking(id);
        return success ? ResponseEntity.ok("取消预订成功") : ResponseEntity.badRequest().body("取消预订失败");
    }

    @GetMapping("/daily")
    public ResponseEntity<List<BookingSchedule>> getDailySchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.getDailySchedule(date));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }
}