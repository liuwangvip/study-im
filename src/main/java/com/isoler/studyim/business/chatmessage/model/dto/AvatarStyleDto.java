package com.isoler.studyim.business.chatmessage.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description
 *
 * @author liuwang
 * @Date 2022-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "头像样式对象", description = "头像样式对象")
public class AvatarStyleDto implements Serializable {
    /**
     * 背景颜色
     */
    @JSONField(name = "background-color")
    private String backgroundColor;
}
