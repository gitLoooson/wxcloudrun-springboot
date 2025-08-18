package com.tencent.wxcloudrun.wx;

import com.tencent.wxcloudrun.factory.RestTemplateFactory;
import com.tencent.wxcloudrun.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeChatUtil {
    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    private final RestTemplate restTemplate = RestTemplateFactory.create();

    public Map<String, String> getSessionKeyOrOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("js_code", code);
        params.put("grant_type","authorization_code" );

        return HttpClientUtils.doGet(url, params);
    }
}