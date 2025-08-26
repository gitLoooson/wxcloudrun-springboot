package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Order.java
@Data
public class Order {
    private Long id;
    private String orderNumber;
    private Long userId;
    private BigDecimal totalAmount;
    private String status; // pending, confirmed, cancelled, completed
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String cancelReason;
    private LocalDateTime cancelledTime;

    // 关联字段
    private String username;
    private String phoneNumber;
    private List<Booking> bookings;
}