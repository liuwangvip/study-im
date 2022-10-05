package com.isoler.studyim.business.chatmessage.model.eo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTargetTypeEnum {
    /**
     * 单发-私聊
     */
    POINT("1"),
    /**
     * 群发-群聊
     */
    GROUP("2"),
    /**
     * 广播消息
     */
    BROADCAST("3");
    private String type;
}
