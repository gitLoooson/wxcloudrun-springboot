package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.MiniLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MiniLogMapper {
    @Insert("INSERT INTO mini_log(user_id, description, api_path, create_time) " +
            "VALUES(#{userId}, #{description}, #{apiPath}, #{createTime})")
    void insert(MiniLog log);
}