package com.tencent.wxcloudrun.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Long id;
    private String openid;
    private String username;
    private String avatar;
    private Date createTime;
    private Date updateTime;
}