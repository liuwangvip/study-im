package com.isoler.studyim.common.api;

/**
 * 错误码
 * @author liuwang
 * @Date：2021/5/11
 */
public interface IErrorCode {
    /**
     * code
     * @return
     */
    long getCode();

    /**
     * message
     * @return
     */
    String getMessage();
}
