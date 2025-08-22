package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.UpdateUserRolesRequestDTO;
import com.tencent.wxcloudrun.dto.UserWithRolesDTO;
import com.tencent.wxcloudrun.service.impl.RolesService;
import com.tencent.wxcloudrun.service.impl.UserRolesService;
import com.tencent.wxcloudrun.service.impl.UserService;
import com.tencent.wxcloudrun.utils.ResultUtil;
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
    @Autowired
    private RolesService rolesService;

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

    @RequiresRoles({RoleEnum.ADMIN_COURT})
    @GetMapping("/getAllUsersWithRoles")
    public ApiResponse getAllUsersWithRoles(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size){
        Page<UserWithRolesDTO> page = new Page<>(current, size);
        Page<UserWithRolesDTO> users = userService.selectAllUsersWithRoles(page);
        return ApiResponse.ok(ResultUtil.getResultFromPage(users));
    }

    @GetMapping("/getAllRoles")
//    @RequiresRoles({RoleEnum.ADMIN_COURT})
    public ApiResponse getAllRoles(){
        return ApiResponse.ok(rolesService.getAllRoles());
    }

    // 更新用户角色
    @PostMapping("/updateUserRoles")
    public ApiResponse updateUserRoles(
            @RequestBody UpdateUserRolesRequestDTO request) {
        userRolesService.updateUserRoles(request.getUserId(), request.getRoleIds());
        return ApiResponse.ok("权限更新成功!");
    }
}
