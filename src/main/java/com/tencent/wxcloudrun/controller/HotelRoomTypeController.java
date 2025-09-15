package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.HotelRoomType;
import com.tencent.wxcloudrun.service.impl.HotelRoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class HotelRoomTypeController {

    @Autowired
    private HotelRoomTypeService hotelRoomTypeService;

    @GetMapping("/list")
    public ApiResponse getRoomList() {
        try {
            List<HotelRoomType> roomList = hotelRoomTypeService.getAllActiveRooms();
            return ApiResponse.ok(roomList);
        } catch (Exception e) {
            return ApiResponse.error( "获取房型列表失败");
        }
    }

    @GetMapping("/{id}")
    public ApiResponse getRoomById(@PathVariable Integer id) {
        try {
            HotelRoomType room = hotelRoomTypeService.getRoomById(id);
            if (room != null) {
                return ApiResponse.ok(room);
            } else {
                return ApiResponse.error( "房型不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error( "获取房型详情失败");
        }
    }
}