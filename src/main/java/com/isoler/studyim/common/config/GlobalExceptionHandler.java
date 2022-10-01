package com.isoler.studyim.common.config;

import com.isoler.studyim.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: liuwang
 * @Date: 2022-07-08
 */
@RestControllerAdvice
@Slf4j
@Configuration
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public CommonResult runtimeExceptionHandler(RuntimeException e) {
        log.error("全局异常处理-RuntimeException", e);
        return CommonResult.failed(e.getMessage());
    }

}
