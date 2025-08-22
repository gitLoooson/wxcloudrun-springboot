package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.RolesMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dao.UserRolesMapper;
import com.tencent.wxcloudrun.excep.BusinessException;
import com.tencent.wxcloudrun.model.Roles;
import com.tencent.wxcloudrun.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRolesService{

    @Autowired
    private UserRolesMapper userRolesMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private UserMapper userMapper;
    
    public int insert(UserRoles record) {
        return userRolesMapper.insert(record);
    }

    public int insertSelective(UserRoles record) {
        return userRolesMapper.insertSelective(record);
    }

    public List<String> getRolesIdByUserId(Long userId) {
        return userRolesMapper.selectByUserId(userId).stream().map(e-> e.getRoleId().toString()).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        // 1. 验证用户存在
        if (!userExists(userId)) {
            throw new BusinessException("用户不存在");
        }
        // 2. 验证角色存在
        validateRolesExist(roleIds);

        // 3. 删除用户所有现有角色
        userRolesMapper.deleteByUserId(userId);

        // 4. 添加新角色（如果有）
        if (roleIds != null && !roleIds.isEmpty()) {
            batchInsertUserRoles(userId, roleIds);
        }
    }

    // 验证用户是否存在
    private boolean userExists(Long userId) {
        // 这里需要根据你的实际实现来检查用户是否存在
        return userMapper.findByUserId(userId).getId() != null; // 假设用户存在
    }

    // 验证所有角色是否存在
    private void validateRolesExist(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }

        // 查询数据库中存在的角色
        List<Roles> existingRoles = rolesMapper.selectBatchIds(roleIds);

        if (existingRoles.size() != roleIds.size()) {
            // 找出不存在的角色ID
            List<Long> existingRoleIds = existingRoles.stream()
                    .map(Roles::getId)
                    .collect(Collectors.toList());

            List<Long> notExistRoleIds = roleIds.stream()
                    .filter(id -> !existingRoleIds.contains(id))
                    .collect(Collectors.toList());

            throw new BusinessException("以下角色不存在: " + notExistRoleIds);
        }
    }

    // 批量插入用户角色关系
    private void batchInsertUserRoles(Long userId, List<Long> roleIds) {
        List<UserRoles> userRoles = roleIds.stream()
                .map(roleId -> {
                    UserRoles userRole = new UserRoles();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    userRole.setCreatedAt(new Date());
                    return userRole;
                })
                .collect(Collectors.toList());

        // 使用 MyBatis 的批量插入
        userRolesMapper.batchInsert(userRoles);
    }
}
