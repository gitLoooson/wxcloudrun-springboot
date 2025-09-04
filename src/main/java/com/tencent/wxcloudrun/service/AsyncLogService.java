package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.MiniLogMapper;
import com.tencent.wxcloudrun.model.MiniLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AsyncLogService {

    @Autowired
    private MiniLogMapper logMapper;

    @Async("logTaskExecutor")
    public void saveLog(Long userId, String description, String apiPath,String params) {
        try {
            MiniLog logEntity = new MiniLog();
            logEntity.setUserId(userId);
            logEntity.setDescription(description);
            logEntity.setApiPath(apiPath);
            logEntity.setParams(apiPath);
            logEntity.setCreateTime(LocalDateTime.now());

            logMapper.insert(logEntity);
        } catch (Exception e) {
            // 日志保存失败只记录错误，不影响业务
            log.error("异步保存操作日志失败: {}", e.getMessage());
        }
    }
}