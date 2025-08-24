package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.model.Booking;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// 批量预订结果类
@Data
public class BatchBookingResult {
    private int totalRequests;
    private int successCount;
    private List<Booking> successfulBookings;
    private List<BookingConflict> conflicts;
    private LocalDateTime processedAt = LocalDateTime.now();

    public boolean isAllSuccess() {
        return successCount == totalRequests;
    }
}