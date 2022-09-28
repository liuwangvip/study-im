package com.isoler.studyim.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 *
 * @author liuwang
 * @Date：2021/5/11
 */
@AllArgsConstructor
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "ok"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    DATANULL(600, "数据为空");

    private long code;
    private String message;

}
