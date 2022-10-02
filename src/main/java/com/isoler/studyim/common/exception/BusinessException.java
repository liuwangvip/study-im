package com.isoler.studyim.common.exception;

import com.isoler.studyim.common.api.IErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author liuwang
 * @Date 2022-10-01
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private String message;

    private long code;


    public BusinessException(IErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
