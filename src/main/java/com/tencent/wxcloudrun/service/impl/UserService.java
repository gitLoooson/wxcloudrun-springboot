package com.tencent.wxcloudrun.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.dao.UserAccountMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dao.UserRolesMapper;
import com.tencent.wxcloudrun.dto.UserWithRolesDTO;
import com.tencent.wxcloudrun.jwt.JwtUtil;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.UserAccount;
import com.tencent.wxcloudrun.model.UserRoles;
import com.tencent.wxcloudrun.wx.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.tencent.wxcloudrun.anno.roles.RoleEnum.USER_COURT;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatUtil weChatUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRolesMapper userRolesMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> loginOrRegister(String openid,String code, String username, String avatar) {
        // 1. 获取微信openid
        if(null == openid){
            Map<String, String> wxResult = weChatUtil.getSessionKeyOrOpenId(code);
            openid = wxResult.get("openid");
            if (openid == null || openid.isEmpty()) {
                throw new RuntimeException("微信登录失败: " + wxResult.get("errmsg"));
            }
        }

        // 2. 查询用户是否存在
        User user = userMapper.findByOpenid(openid);

        boolean isFirstLogin = user == null;

        // 3. 不存在则注册
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setUsername(username);
            user.setAvatar(avatar);
            user.setBalance(BigDecimal.ZERO);
            int insert = userMapper.insert(user);

            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(Long.valueOf(String.valueOf(user.getId())));
            userRoles.setRoleId(Long.valueOf(USER_COURT.getId().toString()));
            userRolesMapper.insert(userRoles);

            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(Long.valueOf(String.valueOf(user.getId())));
            userAccount.setBalance(BigDecimal.ZERO);
            userAccount.setTotalConsumption(BigDecimal.ZERO);
            userAccount.setTotalRecharge(BigDecimal.ZERO);
            userAccountMapper.insertUserAccount(userAccount);
        } else {
//             4. 存在则更新用户信息(可选)
//            user.setUsername(username);
//            user.setAvatar(avatar);
//            userMapper.update(user);
        }

        // 5. 生成并返回token
        Map<String, Object> result = new HashMap<>();
        result.put("token", jwtUtil.generateToken(openid,user.getId()));
        result.put("user", user);
        result.put("isFirstLogin",isFirstLogin);
        return result;
    }

    public User getUserByOpenId(String openid) {
        return userMapper.findByOpenid(openid);
    }

    public Page<UserWithRolesDTO> selectAllUsersWithRoles(Page<UserWithRolesDTO> page) {
        return userMapper.selectAllUsersWithRoles(page);
    }

    public int updateUserPhoneNumber(User user) {
        return userMapper.updatePhoneNumber(user);
    }

    public User getUserById(Long userId) {
        return userMapper.findByUserId(userId);
    }

    public Page<User> getAllUserInfo(Page<User> page) {
        return userMapper.getAllUserInfo(page);
    }
}