package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeDto {
    Long userId;
    BigDecimal amount;
    String description;
}
