package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserRoles;

import java.util.List;

public interface UserRolesMapper {
    int insert(UserRoles record);

    int insertSelective(UserRoles record);

    List<UserRoles> selectByUserId(long userId);
}