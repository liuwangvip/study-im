package com.isoler.studyim.common.minio.service.impl;

import com.isoler.studyim.common.minio.config.MinioProperties;
import com.isoler.studyim.common.minio.model.MinioAttribute;
import com.isoler.studyim.common.minio.service.IMinioStorageService;
import com.isoler.studyim.common.minio.util.FileTypeUtils;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinioStorageServiceImpl implements IMinioStorageService {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;


    @Override
    public void createBucket(String bucketName) {

    }

    @Override
    public InputStream getInputStream(MinioAttribute attribute) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(this.getBucketName(attribute))
                            .object(this.getObjectName(attribute))
                            .build());
        } catch (Exception e) {
            log.error("获取文件流失败,MinioAttribute", e);
            throw new RuntimeException("文件下载失败");
        }
    }

    @Override
    public InputStream getInputStream(String fileProtocol) {
        MinioAttribute attribute = getAttribute(fileProtocol);
        return this.getInputStream(attribute);
    }

    @Override
    public void downloadFile(String path, MinioAttribute attribute) {
        try {
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(this.getBucketName(attribute))
                            .object(this.getObjectName(attribute))
                            .filename(path)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取文件流失败,MinioAttribute", e);
            throw new RuntimeException("文件下载失败");
        }
    }

    @Override
    public String getDownloadUrl(MinioAttribute attribute) {
        try {
            Map<String, String> reqParams = new HashMap();
            String fileName = this.getFileName(attribute);
            String mediaTypeValue = FileTypeUtils.getMediaTypeValue(FileNameUtils.getExtension(fileName));
            reqParams.put("response-content-type", mediaTypeValue);
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder().method(Method.GET)
                            .bucket(this.getBucketName(attribute))
                            .object(this.getObjectName(attribute))
                            .expiry(this.getExpire(attribute), this.getExpireUnit(attribute))
                            .extraQueryParams(reqParams)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取文件的下载地址失败", e);
            throw new RuntimeException("获取文件下载地址失败");
        }
    }

    @Override
    public String getDownloadUrl(String fileProtocol) {
        MinioAttribute attribute = getAttribute(fileProtocol);
        return this.getDownloadUrl(attribute);
    }

    @Override
    public void deleteFile(MinioAttribute attribute) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(this.getBucketName(attribute))
                            .object(this.getObjectName(attribute))
                            .build()
            );
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new RuntimeException("删除文件失败");
        }

    }

    @Override
    public void deleteFile(String fileProtocol) {
        MinioAttribute attribute = getAttribute(fileProtocol);
        this.deleteFile(attribute);
    }

    @Override
    public String uploadFile(InputStream inputStream, MinioAttribute attribute) {
        String fileName = this.getFileName(attribute);
        String mediaTypeValue = FileTypeUtils.getMediaTypeValue(FileNameUtils.getExtension(fileName));
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.getBucketName(attribute))
                            .object(this.getObjectName(attribute))
                            .stream(inputStream, -1, PART_SIZE_10M)
                            .contentType(mediaTypeValue)
                            .build()
            );
            return getFileProtocol(attribute);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public String copyFile(MinioAttribute source, MinioAttribute target) {
        try {
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(this.getBucketName(target))
                            .object(this.getObjectName(target))
                            .source(
                                    CopySource.builder()
                                            .bucket(this.getBucketName(source))
                                            .object(this.getObjectName(source))
                                            .build())
                            .build()
            );
            return this.getFileProtocol(target);
        } catch (Exception e) {
            log.error("文件复制失败", e);
            throw new RuntimeException("文件复制失败");
        }
    }

    /**
     * 获取桶的名称
     *
     * @param attribute
     * @return
     */
    private String getBucketName(MinioAttribute attribute) {
        final String bucketName = attribute.getBucketName();
        if (StringUtils.isNotBlank(bucketName)) {
            return bucketName;
        }
        String defaultBucketName = minioProperties.getBucketName();
        if (StringUtils.isBlank(defaultBucketName)) {
            log.error("未配置minio桶的名称");
            throw new RuntimeException("未配置minio桶的名称");
        }
        return defaultBucketName;
    }

    /**
     * 获取对象的名称
     *
     * @param attribute
     * @return
     */
    private String getOriginObjectName(MinioAttribute attribute) {
        if (StringUtils.isNotBlank(attribute.getObjectName())) {
            return StringUtils.removeStart(attribute.getObjectName(), "/");
        }
        String path = attribute.getFilePrePath();
        StringBuilder pathBuilder = new StringBuilder(50);
        if (StringUtils.isNotBlank(path)) {
            pathBuilder.append(path);
            if (!path.endsWith("/")) {
                pathBuilder.append("/");
            }
        }
        pathBuilder.append(attribute.getFileName());
        return pathBuilder.toString();
    }

    /**
     * 获取对象的名称
     *
     * @param attribute
     * @return
     */
    private String getObjectName(MinioAttribute attribute) {
        String prePath = StringUtils.removeStart(minioProperties.getPrePath(), "/");
        final String objectName = this.getOriginObjectName(attribute);
        if (StringUtils.isBlank(prePath)) {
            return objectName;
        }
        prePath = StringUtils.removeEnd(prePath, "/");
        return new StringBuilder(prePath)
                .append("/").append(objectName).toString();
    }


    /**
     * 获取文件名
     *
     * @param attribute
     * @return
     */
    private String getFileName(MinioAttribute attribute) {
        if (StringUtils.isNotBlank(attribute.getFileName())) {
            return attribute.getFileName();
        }
        final String objectName = attribute.getObjectName();
        return FileNameUtils.getBaseName(objectName);
    }

    /**
     * 获取过期时间
     *
     * @param attributes
     * @return
     */
    private Integer getExpire(MinioAttribute attributes) {
        if (attributes.getExpire() < 1) {
            return DEFAULT_EXPIRE;
        }
        return attributes.getExpire();
    }

    /**
     * 获取过期时间的单位
     *
     * @param attributes
     * @return
     */
    private TimeUnit getExpireUnit(MinioAttribute attributes) {
        if (attributes.getTimeUnit() == null) {
            return TimeUnit.DAYS;
        }
        return attributes.getTimeUnit();
    }

    /**
     * 获取桶的名称
     *
     * @param fileProtocol
     * @return
     */
    private MinioAttribute getAttribute(String fileProtocol) {
        String[] protocolArr = fileProtocol.split(FILE_PROTOCOL_COLON);
        if (protocolArr.length != 2) {
            throw new IllegalArgumentException("协议串对于 s3 协议不合理：" + fileProtocol);
        }
        String bucketObjectName = protocolArr[1];
        if (bucketObjectName.startsWith("/")) {
            bucketObjectName = bucketObjectName.replaceFirst("/", "");
        }

        String bucketName = bucketObjectName.substring(0, bucketObjectName.indexOf("/"));
        String objectName = bucketObjectName.substring(bucketObjectName.indexOf("/") + 1);

        String fileName = objectName;
        String pathName = "";
        if (objectName.contains("/")) {
            int lastIndexOffset = objectName.lastIndexOf("/");
            pathName = objectName.substring(0, lastIndexOffset);
            fileName = objectName.substring(lastIndexOffset + 1);
        }

        try {
            pathName = URLDecoder.decode(URLEncoder.encode(pathName, StandardCharsets.UTF_8.name()), StandardCharsets.UTF_8.name());
            fileName = URLDecoder.decode(URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()), StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(String.format("【MINIO 异常】对文件名【%s】或者路径【%s】进行 decode 解码失败.", fileName, pathName), e);
        }
        return new MinioAttribute().setBucketName(bucketName)
                .setObjectName(objectName)
                .setFileName(fileName)
                .setFilePrePath(pathName);
    }

    /**
     * 获取文件存储协议
     *
     * @param attribute
     * @return
     */
    private String getFileProtocol(MinioAttribute attribute) {
        String storageId = minioProperties.getStorageId();
        if (StringUtils.isBlank(storageId)) {
            log.warn("未配置minio的storageId,将使用默认的storageId:{}", DEFAULT_STORAGE_ID);
            storageId = DEFAULT_STORAGE_ID;
        }
        String bucketName = StringUtils.removeStart(this.getBucketName(attribute), FILE_PROTOCOL_SPILT);
        String objectName = StringUtils.removeStart(this.getObjectName(attribute), FILE_PROTOCOL_SPILT);
        return new StringBuilder(storageId).append(FILE_PROTOCOL_COLON)
                .append(bucketName).append(FILE_PROTOCOL_SPILT).append(objectName).toString();
    }
}
