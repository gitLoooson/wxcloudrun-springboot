package com.tencent.wxcloudrun.dto;

import lombok.Data;

// 定义 DTO 类
@Data
public class LoginRequestDTO {
    private String code;
    private String username;
    private String avatar;
}