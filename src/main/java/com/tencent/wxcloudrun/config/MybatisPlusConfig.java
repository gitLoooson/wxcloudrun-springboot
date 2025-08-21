package com.tencent.wxcloudrun.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 配置 MyBatis-Plus 插件
     * 包括分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加分页插件，配置为 MySQL 数据库
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);

        // 设置分页参数（可选）
        paginationInnerInterceptor.setMaxLimit(1000L); // 单页分页条数限制

        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}