package com.tencent.wxcloudrun.config;


import com.tencent.wxcloudrun.excep.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        return ApiResponse.error(e.getMessage());
    }

    // 业务异常处理
    @ExceptionHandler(BusinessException.class)
    public ApiResponse handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getMessage());
    }
}