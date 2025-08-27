package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mini_log")
public class MiniLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;      // 用户ID
    private String description; // 操作描述
    private String apiPath;     // 接口路径
    private LocalDateTime createTime;
}
