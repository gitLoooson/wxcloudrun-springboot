package com.tencent.wxcloudrun.enums;

// TransactionType.java
public enum TransactionType {
    RECHARGE("recharge", "充值"),
    CONSUMPTION("consumption", "消费"),
    REFUND("refund", "退款");

    private final String code;
    private final String description;

    TransactionType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
}