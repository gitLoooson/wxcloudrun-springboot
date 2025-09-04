package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class PointTransactionRecord {
    private Long id;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private String type; // recharge, consumption, refund
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String description;
    private String status; // pending, completed, failed
    private LocalDateTime createTime;

    // 关联字段
    private String username;
    private String orderNumber;
}
