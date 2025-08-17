package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.Images;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.service.ImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * counter控制器
 */
@RestController("/images")

public class ImagesController {

  final ImagesService imagesService;
  final Logger logger;

  public ImagesController( ImagesService imagesService) {
    this.imagesService = imagesService;
    this.logger = LoggerFactory.getLogger(ImagesController.class);
  }


  /**
   * 获取当前计数
   * @return API response json
   */
  @GetMapping(value = "/getAllImages")
  ApiResponse get() {
    logger.info("/api/count get request");
    Images[] images = imagesService.selectAllImages();
    return ApiResponse.ok(images);
  }

}