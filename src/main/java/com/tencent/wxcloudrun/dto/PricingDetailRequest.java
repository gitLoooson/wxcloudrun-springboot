package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

// 请求参数类
@Data
public class PricingDetailRequest {
    private Long policyId;
    private Long courtId;
    private Long timeSlotId;
    private String periodType;
    private String dayType;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority = 10;
}
