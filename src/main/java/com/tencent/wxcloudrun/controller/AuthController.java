package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginRequestDTO;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String token = userService.loginOrRegister(loginRequest.getCode(), loginRequest.getUsername(), loginRequest.getAvatar());
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/userInfo")
    public ApiResponse getUserInfo(HttpServletRequest request) {
        try {
            User user = userService.getUserByOpenId(request.getAttribute("openId").toString());
            return ApiResponse.ok(user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}


