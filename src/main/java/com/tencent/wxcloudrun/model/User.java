package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Long id;
    private String openid;
    private String username;
    private String avatar;
    private String phoneNumber;
    private Date createTime;
    private Date updateTime;
}