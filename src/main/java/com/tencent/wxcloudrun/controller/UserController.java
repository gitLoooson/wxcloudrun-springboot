package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.MiniLog;
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
    @MiniLog("更新电话号码")
    public ApiResponse updateUserPhoneNumber(HttpServletRequest request, @RequestBody User user) {
        user.setOpenid(RequestAttr.OPEN_ID.get(request));
        int row = userService.updateUserPhoneNumber(user);
        return ApiResponse.ok(row);
    }

    @GetMapping("/getAllUserInfo")
    @MiniLog("管理员获取所有人的用户信息")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    public ApiResponse getAllUserInfo(@RequestParam(defaultValue = "1") Integer current
            ,@RequestParam(defaultValue = "10") Integer size) {
        Page<User> page = new Page<>(current, size);
        return ApiResponse.ok(userService.getAllUserInfo(page));
    }

    @GetMapping("/getUserInfo")
    @MiniLog("获取本人用户信息")
    public ApiResponse getUserInfo(HttpServletRequest request) {
        return ApiResponse.ok(userService.getUserById(RequestAttr.USER_ID.get(request)));
    }
}
