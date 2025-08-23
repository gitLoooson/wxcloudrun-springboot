package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Booking {
    private Long id;
    private Long courtId;
    private Long timeSlotId;
    private LocalDate date;
    private Long userId;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String courtName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String username;
}