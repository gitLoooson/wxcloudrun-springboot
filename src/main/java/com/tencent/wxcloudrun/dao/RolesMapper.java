package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Roles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);

    List<Roles> getAllRoles();

    /**
     * 根据角色ID列表批量查询角色
     * MyBatis-Plus 默认提供了 selectBatchIds 方法
     * 但我们需要明确声明返回类型
     */
    List<Roles> selectBatchIds(@Param("roleIds") List<Long> roleIds);
}