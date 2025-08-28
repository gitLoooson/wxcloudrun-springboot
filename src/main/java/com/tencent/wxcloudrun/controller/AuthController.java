package com.tencent.wxcloudrun.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.tencent.wxcloudrun.anno.MiniLog;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginRequestDTO;
import com.tencent.wxcloudrun.dto.LoginRequestPhoneDTO;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private WxMaService wxMaService;

    @PostMapping("/login")
    @MiniLog("通过openid登陆")
    public ApiResponse login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Map<String, Object> result = userService.loginOrRegister(null,loginRequest.getCode(), loginRequest.getUsername(), loginRequest.getAvatar());
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/loginByPhone")
    @MiniLog("通过电话号码登陆")
    public ApiResponse login(@RequestBody LoginRequestPhoneDTO loginRequest) {
        try {
            // 使用jsCode获取session信息
            WxMaJscode2SessionResult session = wxMaService.getUserService()
                    .getSessionInfo(loginRequest.getCode());

            String openid = session.getOpenid();
            String sessionKey = session.getSessionKey();

            // 使用sessionKey解密手机号信息
            WxMaPhoneNumberInfo phoneInfo = wxMaService.getUserService()
                    .getPhoneNoInfo(sessionKey, loginRequest.getEncryptedData(), loginRequest.getIv());

            String phoneNumber = phoneInfo.getPhoneNumber();

            Map<String, Object> result = userService.loginOrRegister(openid,loginRequest.getCode(), loginRequest.getUsername(), loginRequest.getAvatar());
            User user = (User)result.get("user");
            user.setPhoneNumber(phoneNumber);

            return ApiResponse.ok(user);
        } catch (Exception e) {
            return ApiResponse.ok(e.getMessage());
        }
    }

    @GetMapping("/userInfo")
    public ApiResponse getUserInfo(HttpServletRequest request) {
        try {
            User user = userService.getUserById(RequestAttr.USER_ID.get(request));
            return ApiResponse.ok(user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}


