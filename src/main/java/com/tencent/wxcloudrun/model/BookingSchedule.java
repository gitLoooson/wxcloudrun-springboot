package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class BookingSchedule {
    private LocalTime startTime;
    private String courtName;
    private String status;
    private BigDecimal price;
    private String bookedBy;
}