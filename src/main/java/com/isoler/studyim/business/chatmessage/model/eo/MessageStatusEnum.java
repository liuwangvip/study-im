package com.isoler.studyim.business.chatmessage.model.eo;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatusEnum {
    VALID("1"),
    INVALID("2");
    private String status;
}
