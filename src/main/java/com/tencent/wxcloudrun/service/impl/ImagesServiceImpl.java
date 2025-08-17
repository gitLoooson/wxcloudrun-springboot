package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.service.ImagesService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tencent.wxcloudrun.model.Image;
import com.tencent.wxcloudrun.dao.ImagesMapper;
@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return imagesMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Image record) {
        return imagesMapper.insert(record);
    }

    
    public int insertSelective(Image record) {
        return imagesMapper.insertSelective(record);
    }

    
    public Image selectByPrimaryKey(Integer id) {
        return imagesMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Image record) {
        return imagesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Image record) {
        return imagesMapper.updateByPrimaryKey(record);
    }

    @Override
    public Image[] selectAllImages() {
        return imagesMapper.selectAll();
    }

}
