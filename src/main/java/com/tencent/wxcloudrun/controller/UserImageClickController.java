package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.UserImageClick;
import com.tencent.wxcloudrun.service.impl.UserImageClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/image")
public class UserImageClickController {

    @Autowired
    private UserImageClickService clickService;

    // 记录点击
    @PostMapping("/click")
    public ApiResponse clickImage(@RequestBody UserImageClick userImageClick) {
        clickService.recordClick(userImageClick.getUserId(), userImageClick.getImageId());
        return ApiResponse.ok("点击已记录");
    }

    // 通用查询接口
    @GetMapping("/query")
    public ApiResponse queryClicks(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long imageId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<UserImageClick> userImageClickPage = new Page<>(current,size);
        return ApiResponse.ok(clickService.queryClicks(userId,imageId,startTime,endTime,userImageClickPage));
    }
}