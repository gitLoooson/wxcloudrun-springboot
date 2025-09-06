package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserImageClick {
    private Long id;
    private Long userId;
    private Long imageId;
    private LocalDateTime createTime;
    private String userName;
    private String phoneNumber;
}