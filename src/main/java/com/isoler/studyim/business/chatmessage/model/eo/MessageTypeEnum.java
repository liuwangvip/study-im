package com.isoler.studyim.business.chatmessage.model.eo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuwang
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    NOTICE("1"),
    TEXT("2"),
    PIC("3"),
    FILE("4");
    private String type;
}
