package com.isoler.studyim.business.filestorage.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "文件下载dto", description = "")
public class FileDownloadDto implements Serializable {
    private InputStream inputStream;

    private String fileName;
}
