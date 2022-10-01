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
    ALL("2");
    private String type;
}
