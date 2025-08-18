package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.loginOrRegister(loginRequest.getCode(), loginRequest.getUsername(), loginRequest.getAvatar());
            return ApiResponse.ok(token);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/userInfo")
    public ApiResponse getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            User user = userService.getUserByToken(token);
            return ApiResponse.ok(user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}


