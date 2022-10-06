package com.isoler.studyim.common.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description
 *
 * @author liuwang
 * @Date 2022-10-06
 */
@Data
@Accessors(chain = true)
@ApiModel("分页查询条件")
public class PageQo implements Serializable {
    /**
     * 每页显示条数 默认10
     */
    @ApiModelProperty("每页显示条数 默认10")
    protected long size = 10;
    /**
     * 当前页
     */
    @ApiModelProperty("当前页 默认1")
    protected long current = 1;
}
