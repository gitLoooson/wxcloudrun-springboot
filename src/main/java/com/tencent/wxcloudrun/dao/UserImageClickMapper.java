package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.UserImageClick;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface UserImageClickMapper {

    // 每次点击都插入一条新记录
    @Insert("INSERT INTO user_image_click(user_id, image_id) VALUES(#{userId}, #{imageId})")
    int insertClick(UserImageClick record);

    // 查询是否点击过（返回 count > 0 表示点过）
    @Select("SELECT COUNT(1) FROM user_image_click WHERE user_id = #{userId} AND image_id = #{imageId}")
    int hasClicked(@Param("userId") Long userId, @Param("imageId") Long imageId);

    // 通用查询（动态 SQL）
    Page<UserImageClick> queryClicks(@Param("userId") Long userId,
                                     @Param("imageId") Long imageId,
                                     @Param("startTime") Date startTime,
                                     @Param("endTime") Date endTime,Page <UserImageClick> page);
}