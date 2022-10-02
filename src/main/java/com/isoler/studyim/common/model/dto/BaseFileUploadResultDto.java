package com.isoler.studyim.common.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 */
@Data
@Accessors(chain = true)
@ApiModel("基础文件上传返回结果")
public class BaseFileUploadResultDto implements Serializable {
    private String fileId;

    private String fileName;

    private String fileProtocol;

    private String downloadUrl;
}
