package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.ImagesMapper;
import com.tencent.wxcloudrun.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface ImagesService {

    public int deleteByPrimaryKey(Integer id);


    public int insert(Images record);


    public int insertSelective(Images record);


    public Images selectByPrimaryKey(Integer id);


    public int updateByPrimaryKeySelective(Images record);


    public int updateByPrimaryKey(Images record);

    public Images[] selectAllImages();
}
