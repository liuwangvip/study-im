package com.isoler.studyim.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 属性枚举
 *
 * @author admin
 */
@Getter
@AllArgsConstructor
public enum CommonProperty {
    CREATE_TIME("createTime"),
    UPDATE_TIME("updateTime"),
    CREATOR_ID("creatorId"),
    UPDATOR_ID("updatorId");
    private String name;


}
