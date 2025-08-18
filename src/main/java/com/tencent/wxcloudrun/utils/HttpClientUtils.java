package com.tencent.wxcloudrun.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClientUtils {

    private static final CloseableHttpClient httpClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 配置连接池和超时时间
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)   // 连接超时（毫秒）
                .setSocketTimeout(5000)    // 读取超时（毫秒）
                .build();

        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setMaxConnTotal(100)      // 最大连接数
                .setMaxConnPerRoute(20)    // 每个路由的最大连接数
                .build();
    }

    /**
     * 发送 GET 请求（返回 Map）
     */
    public static Map<String, String> doGet(String url, Map<String, String> params) {
        // 1. 构建带参数的URL
        String query = params.entrySet().stream()
                .map(e -> {
                    try {
                        return e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.joining("&"));
        String fullUrl = url + "?" + query;

        // 2. 创建GET请求
        HttpGet httpGet = new HttpGet(fullUrl);
        httpGet.setHeader("Accept", "application/json");

        // 3. 执行请求并解析响应
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return parseResponse(response);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送 POST 请求（JSON 格式）
     */
    public static Map<String, String> doPost(String url, Object requestBody) throws IOException {
        // 1. 创建POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");

        // 2. 设置JSON请求体
        String json = objectMapper.writeValueAsString(requestBody);
        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

        // 3. 执行请求并解析响应
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return parseResponse(response);
        }
    }

    /**
     * 解析HTTP响应为Map
     */
    private static Map<String, String> parseResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new IOException("响应体为空");
        }
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return objectMapper.readValue(json, Map.class);
    }
}