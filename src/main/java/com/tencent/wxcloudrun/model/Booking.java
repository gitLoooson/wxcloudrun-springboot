package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long orderId;
    // 用于有时拼接timeslot start_time - end_time
    private String time;
    private String orderNumber;
}