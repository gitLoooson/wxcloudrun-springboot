package com.tencent.wxcloudrun.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class ResultUtil {
    public static <T> JSONObject getResultFromPage(Page<T> page){
        JSONObject result = new JSONObject();
        result.put("total", page.getTotal());
        result.put("pages", page.getPages());
        result.put("records", page.getRecords());
        return result;
    }
}
