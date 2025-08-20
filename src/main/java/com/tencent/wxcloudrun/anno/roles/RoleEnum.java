package com.tencent.wxcloudrun.anno.roles;

public enum RoleEnum {
    USER_COURT(1, "user_court", "球场_普通用户"),
    MEMBER_COURT(2, "member_court", "球场_会员用户"),
    ADMIN_COURT(3, "admin_court", "球场_管理员"),
    COACH_COUNT(4, "coach_count", "球场_教练");

    private final Integer id;
    private final String code;
    private final String description;

    RoleEnum(Integer id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RoleEnum getById(Integer id) {
        for (RoleEnum role : values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        return null;
    }

    public static RoleEnum getByCode(String code) {
        for (RoleEnum role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }
}