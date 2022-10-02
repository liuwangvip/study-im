package com.isoler.studyim.common.minio.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("minio上传dto")
public class MinioUploadDto {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String fileProtocol;
}
