package com.isoler.studyim.common.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果
 *
 * @author liuwang
 * @Date：2021/5/11
 */
@Slf4j
@ApiModel("通用返回结果")
public class CommonResult<T> {

    @ApiModelProperty("状态码")
    private long code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("结果集")
    private T result;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, boolean success, T result) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.result = result;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, data);
    }

    /**
     * 使用response输出JSON
     *
     * @param response
     * @param resultMap
     */
    public static void responseJson(ServletResponse response, Map<String, Object> resultMap) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter();) {
            out.println(JSON.toJSONString(resultMap));
        } catch (Exception e) {
            log.error("【JSON输出异常】" + e);
        }
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, true, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), false, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, false, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, false, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), false, data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), false, data);
    }

    /**
     * 数据为空返回结果
     */
    public static <T> CommonResult<T> dataNull(T data) {
        return new CommonResult<T>(ResultCode.DATANULL.getCode(), ResultCode.DATANULL.getMessage(), false, data);
    }

    /**
     * 返回成功示例
     */
    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap) {
        resultMap.put("message", "操作成功");
        resultMap.put("code", 200);
        return resultMap;
    }

    /**
     * 返回失败示例
     */
    public static Map<String, Object> resultError(Map<String, Object> resultMap) {
        resultMap.put("message", "操作失败");
        resultMap.put("code", 500);
        return resultMap;
    }

    /**
     * 通用示例
     */
    public static Map<String, Object> resultCode(Integer code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", msg);
        resultMap.put("code", code);
        return resultMap;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
