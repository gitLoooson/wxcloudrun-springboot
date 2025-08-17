package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.service.ImagesService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tencent.wxcloudrun.model.Images;
import com.tencent.wxcloudrun.dao.ImagesMapper;
@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return imagesMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Images record) {
        return imagesMapper.insert(record);
    }

    
    public int insertSelective(Images record) {
        return imagesMapper.insertSelective(record);
    }

    
    public Images selectByPrimaryKey(Integer id) {
        return imagesMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Images record) {
        return imagesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Images record) {
        return imagesMapper.updateByPrimaryKey(record);
    }

    @Override
    public Images[] selectAllImages() {
        return imagesMapper.selectAll();
    }

}
