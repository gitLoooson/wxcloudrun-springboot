package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class HotelRoomType {
    private Integer id;
    private String name;
    private String images;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private List<String> details;
    private String cancelPolicy;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}