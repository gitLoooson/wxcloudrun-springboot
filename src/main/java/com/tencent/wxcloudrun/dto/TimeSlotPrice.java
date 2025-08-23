package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class TimeSlotPrice {
    private Long timeSlotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String periodType;
    private BigDecimal price;
    private String dayType;
    private Long courtId;
    private String courtName;
    private boolean available; // 该时间段是否可用
}