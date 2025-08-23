package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class LoginRequestPhoneDTO {
    private String code;
    private String encryptedData;
    private String iv;
    private String username;
    private String avatar;
}
