package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PricingDetail {
    private Long id;
    private Long policyId;
    private Long courtId;
    private Long timeSlotId;
    private String periodType;
    private String dayType;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer priority;
    private LocalDateTime createdAt;

    private String courtName;
    private String timeSlotRange;
    private String policyName;
}
