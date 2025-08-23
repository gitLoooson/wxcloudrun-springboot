package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BatchPriceRequest {
    private Long courtId;
    private Long timeSlotId;
    private BigDecimal dayPrice;
    private BigDecimal nightPrice;
    private LocalDate startDate;
    private LocalDate endDate;
}
