package com.isoler.studyim.common.model.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1773435007881948303L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "c_id", type = IdType.UUID)
    protected String id;


    @ApiModelProperty(value = "创建时间")
    @TableField(value = "dt_create_time", fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "dt_update_time", fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;
}
