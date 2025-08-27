package com.tencent.wxcloudrun.interceptor;

import com.tencent.wxcloudrun.anno.MiniLog;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.service.AsyncLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class MiniLogInterceptor implements HandlerInterceptor {

    @Autowired
    private AsyncLogService asyncLogService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // 只在请求完成后处理，即使有异常也会执行
        try {
            // 只处理POST请求
            if (!"POST".equalsIgnoreCase(request.getMethod())) {
                return;
            }

            // 检查token
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                return;
            }

            // 解析用户ID
            Long userId = RequestAttr.USER_ID.get(request);
            if (userId == null) return;

            // 检查是否有MiniLog注解
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                MiniLog miniLog = handlerMethod.getMethodAnnotation(MiniLog.class);
                if (miniLog != null) {
                    // 异步保存日志
                    asyncLogService.saveLog(userId, miniLog.value(), request.getRequestURI());
                }
            }
        } catch (Exception e) {
            // 拦截器异常不能影响业务
            log.error("日志拦截器异常: {}", e.getMessage());
        }
    }

}