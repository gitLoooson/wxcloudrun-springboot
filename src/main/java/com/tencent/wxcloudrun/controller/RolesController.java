package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.UserWithRolesDTO;
import com.tencent.wxcloudrun.service.impl.UserRolesService;
import com.tencent.wxcloudrun.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private UserService userService;


    @PostMapping("/getRolesIds")
    public ApiResponse getRolesIds(HttpServletRequest request) {
        try {
            List<String> roles = userRolesService.getRolesIdByUserId(RequestAttr.USER_ID.get(request));
            JSONObject result = new JSONObject();
            result.put("rolesId", roles);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

//    @RequiresRoles({RoleEnum.ADMIN_COURT})
    @GetMapping("getAllUsersWithRoles")
    public ApiResponse getAllUsersWithRoles(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size){
        Page<UserWithRolesDTO> page = new Page<>(current, size);
        List<UserWithRolesDTO> users = userService.selectAllUsersWithRoles(page);
        return ApiResponse.ok(users);
    }
}
