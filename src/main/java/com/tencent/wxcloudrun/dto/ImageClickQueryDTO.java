package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ImageClickQueryDTO {
    Long userId;
    Long imageId;
    Date startTime;
    Date endTime;
}
