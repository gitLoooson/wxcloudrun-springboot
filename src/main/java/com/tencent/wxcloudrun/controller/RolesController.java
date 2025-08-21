package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.impl.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private UserRolesService userRolesService;

    @PostMapping("/getRolesIds")
    public ApiResponse login(HttpServletRequest request) {
        try {
            List<String> roles = userRolesService.getRolesIdByUserId(RequestAttr.USER_ID.get(request));
            JSONObject result = new JSONObject();
            result.put("rolesId", roles);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
