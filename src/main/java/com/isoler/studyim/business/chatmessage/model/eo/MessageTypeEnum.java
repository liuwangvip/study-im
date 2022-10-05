package com.isoler.studyim.business.chatmessage.model.eo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuwang
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {


    /**
     * 用户的聊天消息
     */
    CHAT("1"),
    /**
     * 提示消息-用户在线消息
     */
    NOTICE_ONLINE("2.1"),
    /**
     * 提示消息-用户进出聊天室消息
     */
    NOTICE_ENTER_OUT("2.2");

    private String type;
}
