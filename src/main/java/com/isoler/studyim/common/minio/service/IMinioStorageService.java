package com.isoler.studyim.common.minio.service;

import com.isoler.studyim.common.minio.model.MinioAttribute;

import java.io.InputStream;

public interface IMinioStorageService {

    Integer DEFAULT_EXPIRE = 7;

    String DEFAULT_STORAGE_ID = "s3";

    long PART_SIZE_10M = 10 * 1024 * 1024;

    String FILE_PROTOCOL_COLON = ":";

    String FILE_PROTOCOL_SPILT = "/";
    /**
     * 创建桶
     *
     * @param bucketName
     */
    void createBucket(String bucketName);

    /**
     * 获取文件流
     *
     * @param attribute
     * @return
     */
    InputStream getInputStream(MinioAttribute attribute);

    /**
     * 获取文件流
     *
     * @param fileProtocol
     * @return
     */
    InputStream getInputStream(String fileProtocol);

    /**
     * 下载到指定文件
     *
     * @param path
     * @param attribute
     */
    void downloadFile(String path, MinioAttribute attribute);

    /**
     * 获取文件的下载路径
     *
     * @param attribute
     * @return
     */
    String getDownloadUrl(MinioAttribute attribute);

    /**
     * 获取文件的下载路径
     *
     * @param fileProtocol
     * @return
     */
    String getDownloadUrl(String fileProtocol);

    /**
     * 删除文件
     *
     * @param attribute
     */
    void deleteFile(MinioAttribute attribute);

    /**
     * 删除文件
     *
     * @param fileProtocol
     */
    void deleteFile(String fileProtocol);

    /**
     * 上传文件
     *
     * @param inputStream
     * @param attribute
     * @return
     */
    String uploadFile(InputStream inputStream, MinioAttribute attribute);

    /**
     * 文件复制
     *
     * @param source 源文件
     * @param target 目标文件
     * @return 结果
     */
    String copyFile(MinioAttribute source, MinioAttribute target);
}
