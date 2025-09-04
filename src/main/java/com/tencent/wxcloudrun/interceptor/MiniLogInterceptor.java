package com.tencent.wxcloudrun.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.anno.MiniLog;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.service.AsyncLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Slf4j
public class MiniLogInterceptor implements HandlerInterceptor {

    @Autowired
    private AsyncLogService asyncLogService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
                    String params = extractRequestParams(request);
                    // 异步保存日志
                    asyncLogService.saveLog(userId, miniLog.value(), request.getRequestURI(),params);
                }
            }
        } catch (Exception e) {
            // 拦截器异常不能影响业务
            log.error("日志拦截器异常: {}", e.getMessage());
        }
    }

    /**
     * 提取请求参数
     */
    private String extractRequestParams(HttpServletRequest request) {
        try {
            // application/x-www-form-urlencoded、query string 参数
            Map<String, String[]> paramMap = request.getParameterMap();
            if (!paramMap.isEmpty()) {
                return objectMapper.writeValueAsString(paramMap);
            }

            // 如果是 application/json，用 ContentCachingRequestWrapper 获取 body
            if (request instanceof ContentCachingRequestWrapper) {
                ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    return new String(buf, StandardCharsets.UTF_8);
                }
            }
        } catch (Exception e) {
            log.warn("提取请求参数失败: {}", e.getMessage());
        }
        return "{}";
    }

}