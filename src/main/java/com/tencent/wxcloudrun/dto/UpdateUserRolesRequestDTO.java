package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;
@Data
public class UpdateUserRolesRequestDTO {
    Long userId;
    List<Long> roleIds;
}
