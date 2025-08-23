package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.BatchPriceRequest;
import com.tencent.wxcloudrun.dto.PricingDetailRequest;
import com.tencent.wxcloudrun.dto.TimeSlotPrice;
import com.tencent.wxcloudrun.model.PricingDetail;
import com.tencent.wxcloudrun.service.impl.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
public class PricingController {
    private final PricingService pricingService;

    @PostMapping("/detail")
    public ResponseEntity<String> createPricingDetail(@RequestBody PricingDetailRequest request) {
        pricingService.setPrice(
                request.getPolicyId(),
                request.getCourtId(),
                request.getTimeSlotId(),
                request.getPeriodType(),
                request.getDayType(),
                request.getPrice(),
                request.getStartDate(),
                request.getEndDate(),
                request.getPriority()
        );
        return ResponseEntity.ok("价格设置成功");
    }

    @PostMapping("/weekday")
    public ResponseEntity<String> setWeekdayPrices(@RequestBody BatchPriceRequest request) {
        pricingService.setWeekdayDayPrice(request.getCourtId(), request.getTimeSlotId(),
                request.getDayPrice(), request.getStartDate(), request.getEndDate());
        pricingService.setWeekdayNightPrice(request.getCourtId(), request.getTimeSlotId(),
                request.getNightPrice(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok("工作日价格设置成功");
    }

    @PostMapping("/weekend")
    public ResponseEntity<String> setWeekendPrices(@RequestBody BatchPriceRequest request) {
        pricingService.setWeekendDayPrice(request.getCourtId(), request.getTimeSlotId(),
                request.getDayPrice(), request.getStartDate(), request.getEndDate());
        pricingService.setWeekendNightPrice(request.getCourtId(), request.getTimeSlotId(),
                request.getNightPrice(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok("周末价格设置成功");
    }

    @GetMapping
    public ResponseEntity<List<PricingDetail>> getPrices(
            @RequestParam(required = false) Long courtId,
            @RequestParam(required = false) Long timeSlotId,
            @RequestParam(required = false) String periodType,
            @RequestParam(required = false) String dayType) {
        return ResponseEntity.ok(pricingService.getPricesByConditions(courtId, timeSlotId, periodType, dayType));
    }

    @GetMapping("/time-slots/formatted")
    public ApiResponse getFormattedTimeSlotPrices(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TimeSlotPrice> formattedPrices = pricingService.getTimeSlotPricesByCourt(date);
        return ApiResponse.ok(formattedPrices);
    }

}

