package com.isoler.studyim.common.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {

    /**
     * 存储类型
     */
    private String storageId;
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 目录
     */
    private String prePath;

}