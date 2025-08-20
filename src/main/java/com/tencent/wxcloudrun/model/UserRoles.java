package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserRoles implements Serializable {
    private Integer userId;

    private Integer roleId;

    private Date createdAt;
}