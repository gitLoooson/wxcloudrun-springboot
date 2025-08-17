package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Image;

public interface ImagesService {

    public int deleteByPrimaryKey(Integer id);


    public int insert(Image record);


    public int insertSelective(Image record);


    public Image selectByPrimaryKey(Integer id);


    public int updateByPrimaryKeySelective(Image record);


    public int updateByPrimaryKey(Image record);

    public Image[] selectAllImages();
}
