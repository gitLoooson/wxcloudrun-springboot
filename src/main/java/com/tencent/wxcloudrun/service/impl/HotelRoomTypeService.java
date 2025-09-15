package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.HotelRoomTypeMapper;
import com.tencent.wxcloudrun.model.HotelRoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomTypeService {

    @Autowired
    private HotelRoomTypeMapper hotelRoomTypeMapper;

    public List<HotelRoomType> getAllActiveRooms() {
        return hotelRoomTypeMapper.selectAllActiveRooms();
    }

    public HotelRoomType getRoomById(Integer id) {
        return hotelRoomTypeMapper.selectRoomById(id);
    }

    public boolean addRoom(HotelRoomType room) {
        return hotelRoomTypeMapper.insertRoom(room) > 0;
    }

    public boolean updateRoom(HotelRoomType room) {
        return hotelRoomTypeMapper.updateRoom(room) > 0;
    }

    public boolean deleteRoom(Integer id) {
        return hotelRoomTypeMapper.deleteRoom(id) > 0;
    }
}
