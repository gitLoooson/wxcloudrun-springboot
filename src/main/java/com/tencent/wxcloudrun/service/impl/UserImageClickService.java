package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.dao.UserImageClickMapper;
import com.tencent.wxcloudrun.model.UserImageClick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserImageClickService {

    @Autowired
    private UserImageClickMapper clickMapper;

    public void recordClick(Long userId, Long imageId) {
        UserImageClick click = new UserImageClick();
        click.setUserId(userId);
        click.setImageId(imageId);
        clickMapper.insertClick(click);
    }

    public boolean hasClicked(Long userId, Long imageId) {
        return clickMapper.hasClicked(userId, imageId) > 0;
    }

    public Page<UserImageClick> queryClicks(Long userId, Long imageId, Date startTime, Date endTime, Page<UserImageClick> page) {
        return clickMapper.queryClicks(userId, imageId, startTime, endTime,page);
    }
}