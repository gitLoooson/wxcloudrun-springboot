package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// BookingRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long courtId;
    private Long timeSlotId;
    private LocalDate date;
    private Long userId;
    private String remark; // 备注信息
    private String contactPhone; // 联系电话
}