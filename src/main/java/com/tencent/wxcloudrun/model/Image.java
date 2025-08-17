package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Image implements Serializable {
    private Integer id;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 完整的文件路径，包含文件名称
     */
    private String path;

    /**
     * 属性名，对应微信小程序中的参数名
     */
    private String paramName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

}