package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TimeSlot {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String periodType;
    private LocalDateTime createdAt;
}