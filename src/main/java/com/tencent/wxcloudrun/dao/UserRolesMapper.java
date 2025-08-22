package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserRoles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRolesMapper {
    int insert(UserRoles record);

    /**
     * 批量插入用户角色关系
     */
    int batchInsert(@Param("list") List<UserRoles> userRoles);


    int insertSelective(UserRoles record);

    List<UserRoles> selectByUserId(long userId);

    int deleteByUserId(Long userId);
}