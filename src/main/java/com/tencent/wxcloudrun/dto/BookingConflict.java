package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 冲突检查结果类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingConflict {
    private Long courtId;
    private Long timeSlotId;
    private LocalDate date;
    private boolean available;
    private String conflictReason;
}