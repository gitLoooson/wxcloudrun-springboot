package com.tencent.wxcloudrun.service.impl;


import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.jwt.JwtUtil;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.wx.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatUtil weChatUtil;

    @Autowired
    private JwtUtil jwtUtil;

    public String loginOrRegister(String code, String username, String avatar) {
        // 1. 获取微信openid
        Map<String, String> wxResult = weChatUtil.getSessionKeyOrOpenId(code);
        String openid = wxResult.get("openid");

        if (openid == null || openid.isEmpty()) {
            throw new RuntimeException("微信登录失败: " + wxResult.get("errmsg"));
        }

        // 2. 查询用户是否存在
        User user = userMapper.findByOpenid(openid);

        // 3. 不存在则注册
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setUsername(username);
            user.setAvatar(avatar);
            userMapper.insert(user);
        } else {
            // 4. 存在则更新用户信息(可选)
            user.setUsername(username);
            user.setAvatar(avatar);
            userMapper.update(user);
        }

        // 5. 生成并返回token
        return jwtUtil.generateToken(openid,user.getId());
    }

    public User getUserByOpenId(String openid) {
        return userMapper.findByOpenid(openid);
    }
}