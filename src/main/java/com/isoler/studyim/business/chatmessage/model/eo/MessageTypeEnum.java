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
    CHAT("1", "聊天"),
    /**
     * 提示消息-用户在线消息
     */
    NOTICE_ONLINE("2.1", "上线"),
    /**
     * 提示消息-用户进出聊天室消息
     */
    NOTICE_OFFLINE("2.2", "下线");

    private String type;
    private String desc;
}
