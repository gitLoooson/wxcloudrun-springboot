package com.tencent.wxcloudrun.enums;

// TransactionStatus.java
public enum TransactionStatus {
    PENDING("pending", "处理中"),
    COMPLETED("completed", "已完成"),
    FAILED("failed", "失败");

    private final String code;
    private final String description;

    TransactionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
}