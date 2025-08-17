package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Images;

public interface ImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);

    Images[] selectAll();
}