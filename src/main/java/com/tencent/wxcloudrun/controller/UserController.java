package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/updateUserPhoneNumber")
    public ApiResponse updateUserPhoneNumber(HttpServletRequest request, @RequestBody User user) {
        user.setOpenid(RequestAttr.OPEN_ID.get(request));
        int row = userService.updateUserPhoneNumber(user);
        return ApiResponse.ok(row);
    }

    @GetMapping("/getAllUserInfo")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    public ApiResponse getAllUserInfo(@RequestParam(defaultValue = "1") Integer current
            ,@RequestParam(defaultValue = "10") Integer size) {
        Page<User> page = new Page<>(current, size);
        return ApiResponse.ok(userService.getAllUserInfo(page));
    }

    @GetMapping("/getUserInfo")
    public ApiResponse getUserInfo(HttpServletRequest request) {
        return ApiResponse.ok(userService.getUserById(RequestAttr.USER_ID.get(request)));
    }
}
