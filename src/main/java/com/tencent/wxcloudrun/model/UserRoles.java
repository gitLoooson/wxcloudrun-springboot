package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserRoles implements Serializable {
    private Long userId;

    private Long roleId;

    private Date createdAt;
}