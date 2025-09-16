package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.HotelRoomType;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface HotelRoomTypeMapper {

    // 查询所有上架房型（用于前端列表）
    @Select("SELECT id, name, images, price, original_price, details, cancel_policy" +
            ",room_size_desc,room_type_desc,pepole_number,breakfast " +
            "FROM hotel_room_type WHERE status = 1 ORDER BY `order`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "images", column = "images"),
            @Result(property = "price", column = "price"),
            @Result(property = "originalPrice", column = "original_price"),
            @Result(property = "details", column = "details", jdbcType = JdbcType.VARCHAR,
                    typeHandler = com.tencent.wxcloudrun.handle.JsonArrayTypeHandler.class),
            @Result(property = "cancelPolicy", column = "cancel_policy"),
            @Result(property = "roomSizeDesc", column = "room_size_desc"),
            @Result(property = "roomTypeDesc", column = "room_type_desc"),
            @Result(property = "pepoleNumber", column = "pepole_number"),
            @Result(property = "breakfast", column = "breakfast")
    })
    List<HotelRoomType> selectAllActiveRooms();

    // 根据ID查询房型详情
    @Select("SELECT * FROM hotel_room_type WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "images", column = "images"),
            @Result(property = "price", column = "price"),
            @Result(property = "originalPrice", column = "original_price"),
            @Result(property = "details", column = "details", jdbcType = JdbcType.VARCHAR,
                    typeHandler = com.tencent.wxcloudrun.handle.JsonArrayTypeHandler.class),
            @Result(property = "cancelPolicy", column = "cancel_policy"),
            @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "roomSizeDesc", column = "room_size_desc"),
            @Result(property = "roomTypeDesc", column = "room_type_desc"),
            @Result(property = "pepoleNumber", column = "pepole_number"),
            @Result(property = "breakfast", column = "breakfast")
    })
    HotelRoomType selectRoomById(Integer id);

    int insertRoom(HotelRoomType room);

    // 更新房型
    int updateRoom(HotelRoomType room);

    int deleteRoom(Integer id);
}