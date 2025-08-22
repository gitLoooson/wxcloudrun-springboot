package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.dto.UserWithRolesDTO;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User findByOpenid(String openid);

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User findByUserId(Long userId);

    @Insert("INSERT INTO user(openid, username, avatar) VALUES(#{openid}, #{username}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET username=#{username}, avatar=#{avatar} WHERE openid=#{openid}")
    int update(User user);

    Page<UserWithRolesDTO> selectAllUsersWithRoles(Page<UserWithRolesDTO> page);

    @Update("UPDATE user SET phone_number=#{phoneNumber} WHERE openid=#{openid}")
    int updatePhoneNumber(User user);
}