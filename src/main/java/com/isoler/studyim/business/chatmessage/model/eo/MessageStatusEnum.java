package com.isoler.studyim.business.chatmessage.model.eo;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatusEnum {
    /**
     * 有效
     */
    VALID("1"),
    /**
     * 失效
     */
    INVALID("2");
    private String status;
}
