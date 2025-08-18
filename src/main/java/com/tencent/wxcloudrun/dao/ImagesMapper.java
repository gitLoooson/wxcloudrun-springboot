package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);

    Image[] selectAll();
}