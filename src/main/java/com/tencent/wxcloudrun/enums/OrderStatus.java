package com.tencent.wxcloudrun.enums;

import lombok.Getter;

// OrderStatus.java
@Getter
public enum OrderStatus {
    PENDING("pending", "待确认"),
    CONFIRMED("confirmed", "已确认"),
    CANCELLED("cancelled", "已取消"),
    COMPLETED("completed", "已完成");

    private final String code;
    private final String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

}