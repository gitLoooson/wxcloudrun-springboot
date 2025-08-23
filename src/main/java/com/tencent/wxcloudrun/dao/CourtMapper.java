package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Court;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourtMapper {
    List<Court> selectAllCourts();
    Court selectCourtById(Long id);
}