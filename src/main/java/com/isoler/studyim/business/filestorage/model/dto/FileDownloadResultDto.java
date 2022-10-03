package com.isoler.studyim.business.filestorage.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "文件下载结果dto", description = "")
public class FileDownloadResultDto implements Serializable {

    @ApiModelProperty(value = "文件流")
    private InputStream inputStream;

    @ApiModelProperty(value = "文件名")
    private String fileName;
}
