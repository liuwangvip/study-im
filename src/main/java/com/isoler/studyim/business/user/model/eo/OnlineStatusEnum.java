package com.isoler.studyim.business.user.model.eo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description
 *
 * @author liuwang
 * @Date 2022-10-06
 */
@Getter
@AllArgsConstructor
public enum OnlineStatusEnum {
    /**
     * 在线
     */
    ON_LINE("1"),
    /**
     * 离线
     */
    OFF_LINE("2");

    private String status;
}
