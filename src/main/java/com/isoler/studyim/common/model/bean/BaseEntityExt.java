package com.isoler.studyim.common.model.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseEntityExt extends BaseEntity {
    private static final long serialVersionUID = 1773435007881948303L;

    @ApiModelProperty(value = "主键")
    @TableField(value = "c_creator_id", fill = FieldFill.INSERT)
    protected String creatorId;

    @ApiModelProperty(value = "主键")
    @TableField(value = "c_updator_id", fill = FieldFill.INSERT_UPDATE)
    protected String updatorId;


}
