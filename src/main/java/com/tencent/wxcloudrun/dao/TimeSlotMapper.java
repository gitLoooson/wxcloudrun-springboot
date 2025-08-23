package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.TimeSlot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimeSlotMapper {
    List<TimeSlot> selectAllTimeSlots();
    TimeSlot selectTimeSlotById(Long id);
}