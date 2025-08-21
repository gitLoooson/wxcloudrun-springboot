package com.tencent.wxcloudrun.anno;

import javax.servlet.http.HttpServletRequest;

public enum RequestAttr {
    OPEN_ID("openId", String.class),
    USER_ID("userId", Long.class)
    ;

    private final String key;
    private final Class<?> type;

    RequestAttr(String key, Class<?> type) {
        this.key = key;
        this.type = type;
    }

    // 直接在枚举中实现set方法
    public void set(HttpServletRequest request, Object value) {
        if (type.isInstance(value)) {
            request.setAttribute(key, value);
        } else {
            throw new IllegalArgumentException("Value type mismatch for " + key);
        }
    }

    // 直接在枚举中实现get方法
    @SuppressWarnings("unchecked")
    public <T> T get(HttpServletRequest request) {
        Object value = request.getAttribute(key);
        return type.isInstance(value) ? (T) value : null;
    }

    // 带默认值的get方法
    @SuppressWarnings("unchecked")
    public <T> T get(HttpServletRequest request, T defaultValue) {
        Object value = request.getAttribute(key);
        return type.isInstance(value) ? (T) value : defaultValue;
    }
}