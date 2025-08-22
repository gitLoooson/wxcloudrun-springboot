package com.tencent.wxcloudrun.service.impl;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tencent.wxcloudrun.dao.RolesMapper;
import com.tencent.wxcloudrun.model.Roles;

import java.util.List;

@Service
public class RolesService{

    @Autowired
    private RolesMapper rolesMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return rolesMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Roles record) {
        return rolesMapper.insert(record);
    }

    
    public int insertSelective(Roles record) {
        return rolesMapper.insertSelective(record);
    }

    
    public Roles selectByPrimaryKey(Integer id) {
        return rolesMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Roles record) {
        return rolesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Roles record) {
        return rolesMapper.updateByPrimaryKey(record);
    }

    public List<Roles> getAllRoles() {
        return rolesMapper.getAllRoles();
    }

}
