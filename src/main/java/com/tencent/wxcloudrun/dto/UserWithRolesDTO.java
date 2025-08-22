package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.model.Roles;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserWithRolesDTO {
    private Integer id;
    private String openid;
    private String username;
    private String nickname;
    private String avatar;
    private String phoneNumber;
    private List<Roles> roles;
    private Date createTime;
}