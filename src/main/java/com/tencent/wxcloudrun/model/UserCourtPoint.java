package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class UserCourtPoint {
    private Long id;

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 账户余额
    */
    private BigDecimal balance;

    /**
    * 累计充值积分
    */
    private BigDecimal totalRecharge;

    /**
    * 累计消费积分
    */
    private BigDecimal totalConsumption;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}