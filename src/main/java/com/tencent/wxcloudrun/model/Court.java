package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

// Court.java
@Data
public class Court {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
}