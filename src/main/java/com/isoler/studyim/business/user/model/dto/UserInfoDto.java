package com.isoler.studyim.business.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "用户信息dto", description = "dto")
public class UserInfoDto implements Serializable {
    /**
     * 用户在线状态
     */
    @ApiModelProperty("在线状态")
    private String onlineStatus;
}
