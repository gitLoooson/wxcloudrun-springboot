package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Roles implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;
}