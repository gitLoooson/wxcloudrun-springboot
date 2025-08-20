package com.tencent.wxcloudrun.service.impl;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tencent.wxcloudrun.model.UserRoles;
import com.tencent.wxcloudrun.dao.UserRolesMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRolesService{

    @Autowired
    private UserRolesMapper userRolesMapper;

    
    public int insert(UserRoles record) {
        return userRolesMapper.insert(record);
    }

    
    public int insertSelective(UserRoles record) {
        return userRolesMapper.insertSelective(record);
    }

    public List<String> getRolesIdByUserId(Long userId) {
        return userRolesMapper.selectByUserId(userId).stream().map(e-> e.getRoleId().toString()).collect(Collectors.toList());
    }
}
