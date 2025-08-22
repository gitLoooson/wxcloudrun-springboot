package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.interceptor.JwtInterceptor;
import com.tencent.wxcloudrun.interceptor.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**") // 拦截所有/api开头的请求
//                .excludePathPatterns(
//                        "/api/auth/login",  // 登录接口不拦截
//                        "/api/auth/register", // 注册接口不拦截
//                        "/swagger**/**",     // 放行swagger
//                        "/webjars/**",       // 放行webjars
//                        "/v2/api-docs",      // 放行api文档
//                        "/doc.html",          // 放行swagger-ui
//                        "/images/getAllImages" // 放行获取所有图片接口
//                );

        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/login", "/register"); // 排除不需要拦截的路径
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许所有域，生产环境应指定具体域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // 不知道干嘛的
                .allowCredentials(false)
                .maxAge(3600);
    }
}