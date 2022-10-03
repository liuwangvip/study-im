package com.isoler.studyim.common.exception;

import com.google.common.collect.ImmutableList;
import com.isoler.studyim.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常拦截处理，只能处理到达controller层的异常
 *
 * @author: liuwang
 * @Date: 2022-07-08
 */
@RestControllerAdvice
@Slf4j
@Configuration
public class GlobalExceptionHandler {

    private static final List<String> CONTENT_TYPE_LIST = ImmutableList.of(
            MediaType.APPLICATION_PDF_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    ).asList();

    public GlobalExceptionHandler() {
        log.info("注入全局异常");
    }

    @ExceptionHandler(BusinessException.class)
    public CommonResult businessExceptionHandler(BusinessException e) {
        log.error("【全局异常捕获】BusinessException", e);
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * spring validate 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult BindExceptionHandler(BindException e) {
        log.error("【全局异常捕获】BindException", e);
        final FieldError fieldError = e.getFieldError();
        return CommonResult.failed(String.format("参数校验失败:%s", fieldError.getDefaultMessage()));
    }

    /**
     * spring validate 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("【全局异常捕获】MethodArgumentNotValidException", e);
        final BindingResult bindingResult = e.getBindingResult();
        final String errMsg = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return CommonResult.failed(String.format("参数校验失败：%s", errMsg));
    }

    /**
     * 运行时异常
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object runtimeExceptionHandler(RuntimeException e, HttpServletRequest req) {
        log.error("【全局异常捕获】RuntimeException", e);
        return handlerException(e, req, CommonResult.failed("服务异常，请联系管理员"));
    }

    /**
     * exception 异常
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandler(Exception e, HttpServletRequest req) {
        log.error("【全局异常捕获】Exception", e);
        return handlerException(e, req, CommonResult.failed("服务异常，请联系管理员"));
    }

    /**
     * 处理异常
     *
     * @param e
     * @param req
     * @param result
     * @return
     */
    private Object handlerException(Exception e, HttpServletRequest req, CommonResult result) {
        String contentTypeHeader = req.getHeader(HttpHeaders.CONTENT_TYPE);
        String acceptHeader = req.getHeader(HttpHeaders.ACCEPT);
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && CONTENT_TYPE_LIST.stream().anyMatch(contentTypeHeader::contains))
                || StringUtils.contains(acceptHeader, MediaType.APPLICATION_JSON_VALUE)
                || StringUtils.equalsIgnoreCase("XMLHttpRequest", xRequestedWith)) {
            return result;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("trace", e.getStackTrace());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}


