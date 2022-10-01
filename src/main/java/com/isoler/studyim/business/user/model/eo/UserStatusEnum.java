package com.isoler.studyim.business.user.model.eo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    NORMAL("1"),
    LOCK("2");
    private String status;
}
