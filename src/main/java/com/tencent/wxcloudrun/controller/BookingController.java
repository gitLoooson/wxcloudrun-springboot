package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.anno.MiniLog;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.BookingRequest;
import com.tencent.wxcloudrun.dto.CreateOrderDTO;
import com.tencent.wxcloudrun.service.impl.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/createOrder")
    @MiniLog("创建订单")
    public ApiResponse createBatchBookings(
            HttpServletRequest request,
            @RequestBody CreateOrderDTO createOrderDTO
            ) {
        for (BookingRequest bookingRequest : createOrderDTO.getBookingRequests()) {
            bookingRequest.setUserId(RequestAttr.USER_ID.get(request));
        }
        return ApiResponse.ok( bookingService.createOrderWithAtomicBookings(RequestAttr.USER_ID.get(request), createOrderDTO) );
    }
}