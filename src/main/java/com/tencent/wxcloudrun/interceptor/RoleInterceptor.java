package com.tencent.wxcloudrun.interceptor;

import com.tencent.wxcloudrun.anno.roles.Logical;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.jwt.JwtUtil;
import com.tencent.wxcloudrun.service.impl.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRolesService userRoleService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresRoles requiresRoles = handlerMethod.getMethodAnnotation(RequiresRoles.class);

        if (requiresRoles == null) {
            return true;
        }

        // 从token中获取用户ID
        String token = request.getHeader("Authorization");
        Long userId = jwtUtil.getUserIdFromToken(token);

        // 获取用户角色
        List<String> userRolesIds = userRoleService.getRolesIdByUserId(userId);

        // 验证角色
        if (requiresRoles.logical() == Logical.AND) {
            for (RoleEnum role : requiresRoles.value()) {
                if (!userRolesIds.contains(role.getId().toString())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"code\":401,\"message\":\"not have auth!\"}");
                    return false;
                }
            }
        } else {
            boolean hasAnyRole = false;
            for (RoleEnum role : requiresRoles.value()) {
                if (userRolesIds.contains(role.getId().toString())) {
                    hasAnyRole = true;
                    break;
                }
            }
            if (!hasAnyRole) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"code\":401,\"message\":\"not have auth!\"}");
                return false;
            }
        }
        return true;
    }
}