package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.BookingRequest;
import com.tencent.wxcloudrun.service.impl.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/batch")
    public ApiResponse createBatchBookings(
            @RequestParam Long userId,
            @RequestBody List<BookingRequest> bookingRequests
            ) {
        return ApiResponse.ok( bookingService.createOrderWithAtomicBookings(userId, bookingRequests) );
    }
}