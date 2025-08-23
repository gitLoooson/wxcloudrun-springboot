package com.tencent.wxcloudrun.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxMaConfiguration {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;
    @Bean
    public WxMaConfig wxMaConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appid); // 设置小程序AppID
        config.setSecret(secret); // 设置小程序AppSecret
        // 其他配置如token、aesKey等根据需要设置
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig wxMaConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(wxMaConfig);
        return service;
    }
}
