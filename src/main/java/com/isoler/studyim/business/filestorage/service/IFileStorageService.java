package com.isoler.studyim.business.filestorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoler.studyim.business.filestorage.model.bean.FileStorage;
import com.isoler.studyim.business.filestorage.model.dto.FileDownloadResultDto;
import com.isoler.studyim.business.filestorage.model.dto.FileUploadResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-10-02
 */
public interface IFileStorageService extends IService<FileStorage> {
    /**
     * 下载文件
     *
     * @param id
     * @return
     */
    FileDownloadResultDto downloadFile(String id);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    FileUploadResultDto uploadFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param id
     */
    void deleteFile(String id);

    /**
     * 清理过期的文件
     */
    void cleanExpireFile(LocalDateTime now);
}