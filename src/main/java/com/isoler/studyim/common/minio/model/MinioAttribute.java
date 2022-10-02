package com.isoler.studyim.common.minio.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@Accessors(chain = true)
@ApiModel("minio参数属性")
public class MinioAttribute implements Serializable {

    /**
     * minin 存储桶名称
     */
    @ApiModelProperty(value = "存储桶名称")
    private String bucketName;

    /**
     * minin 存储对象名称
     */
    @ApiModelProperty(value = "存储对象名称")
    private String objectName;

    /**
     * minin 文件存储目录
     */
    @ApiModelProperty(value = "文件存储目录")
    private String filePrePath;

    /**
     * minin 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;


    /**
     * 文件过期时间
     */
    @ApiModelProperty(value = "文件过期时间")
    private Integer expire;

    /**
     * 文件过期时间单位
     */
    @ApiModelProperty(value = "文件过期时间单位")
    private TimeUnit timeUnit;

}
