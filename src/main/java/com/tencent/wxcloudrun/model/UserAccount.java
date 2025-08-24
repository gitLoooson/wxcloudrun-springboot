package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserAccount {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private BigDecimal totalRecharge;
    private BigDecimal totalConsumption;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联字段
    private String username;
}

