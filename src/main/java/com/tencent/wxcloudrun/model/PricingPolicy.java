package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PricingPolicy {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
