package com.isoler.studyim.business.filestorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoler.studyim.business.filestorage.mapper.FileStorageMapper;
import com.isoler.studyim.business.filestorage.model.bean.FileStorage;
import com.isoler.studyim.business.filestorage.model.dto.FileDownloadResultDto;
import com.isoler.studyim.business.filestorage.model.dto.FileUploadResultDto;
import com.isoler.studyim.business.filestorage.service.IFileStorageService;
import com.isoler.studyim.common.minio.model.MinioAttribute;
import com.isoler.studyim.common.minio.service.IMinioStorageService;
import com.isoler.studyim.common.util.FileNameUtil;
import com.isoler.studyim.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-10-02
 */
@Slf4j
@Service
public class FileStorageServiceImpl extends ServiceImpl<FileStorageMapper, FileStorage> implements IFileStorageService {

    @Resource
    private IMinioStorageService minioStorageService;

    @Override
    public FileDownloadResultDto downloadFile(String id) {
        FileStorage fileStorage = this.getById(id);
        if (fileStorage == null || StringUtils.isBlank(fileStorage.getFileProtocol())) {
            log.error("文件下载失败，文件不存在，文件id:{}", id);
            throw new RuntimeException("文件下载失败，文件不存在");
        }
        InputStream inputStream = minioStorageService.getInputStream(fileStorage.getFileProtocol());
        return new FileDownloadResultDto().setFileName(fileStorage.getFileName())
                .setInputStream(inputStream);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileUploadResultDto uploadFile(MultipartFile file) {
        checkBeforeUpload(file);
        String fileName = FileNameUtil.getName(file.getOriginalFilename());
        Path path = null;
        try (InputStream inputStream = file.getInputStream()) {
            byte[] bytes = FileUtil.toByteArr(inputStream);
            path = FileUtil.writeToTempFile(bytes);
            final String fileMd5 = FileUtil.getFileMd5(bytes);
            //优化性能
            bytes = null;
            final FileStorage oldFileStorage = checkFileRepeat(fileMd5);
            if (oldFileStorage != null) {
                return copyFile(oldFileStorage, fileName);
            }
            return uploadFileNew(path, fileName, fileMd5);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败,获取文件流失败");
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败,请联系管理员");
        } finally {
            if (path != null) {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    log.error("删除临时文件失败", e);
                }
            }
        }
    }

    /**
     * 上传新文件
     *
     * @param path
     * @param fileName
     * @param fileMd5
     * @return
     */
    private FileUploadResultDto uploadFileNew(Path path, String fileName, String fileMd5) {
        String uploadFileName = new StringBuilder(IdWorker.get32UUID())
                .append("-").append(fileName).toString();
        MinioAttribute minioAttribute = new MinioAttribute()
                .setFilePrePath(getDatePath())
                .setFileName(uploadFileName);
        final long fileSize = FileUtil.fileSize(path.toFile());
        try (InputStream ins = Files.newInputStream(path)) {
            final String fileProtocol = minioStorageService.uploadFile(ins, minioAttribute);
            FileStorage fileStorage = new FileStorage()
                    .setFileName(fileName)
                    .setFileProtocol(fileProtocol)
                    .setFileMd5(fileMd5)
                    .setFileSize(fileSize)
                    .setExpireTime(LocalDateTime.now().plusDays(7));
            this.save(fileStorage);
            return new FileUploadResultDto().setFileId(fileStorage.getId())
                    .setFileName(fileStorage.getFileName());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败,获取文件流失败");
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败,请联系管理员");
        }
    }

    /**
     * 复用相同的文件
     *
     * @param fileStorage 文件存储
     * @param fileName    文件名
     * @return 结果
     */
    private FileUploadResultDto copyFile(FileStorage fileStorage, String fileName) {
        FileStorage newFileStorage = new FileStorage();
        BeanUtils.copyProperties(fileStorage, newFileStorage);
        newFileStorage.setId(null);
        newFileStorage.setFileName(fileName);
        newFileStorage.setExpireTime(LocalDateTime.now().plusDays(7));
        this.save(newFileStorage);
        return new FileUploadResultDto().setFileId(newFileStorage.getId())
                .setFileId(fileName);
    }

    /**
     * 校验文件内容是否相同
     *
     * @param fileMd5 文件md5
     * @return
     */
    private FileStorage checkFileRepeat(String fileMd5) {
        QueryWrapper<FileStorage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_file_md5", fileMd5);
        final List<FileStorage> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(String id) {
        FileStorage fileStorage = this.getById(id);
        if (fileStorage == null || StringUtils.isBlank(fileStorage.getFileProtocol())) {
            log.error("文件删除失败，文件不存在，文件id:{}", id);
            throw new RuntimeException("文件删除失败，文件不存在");
        }
        this.removeById(id);
        minioStorageService.deleteFile(fileStorage.getFileProtocol());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanExpireFile(LocalDateTime now) {
        QueryWrapper<FileStorage> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("c_file_protocol");
        queryWrapper.lt("dt_expire_time", now);
        final List<FileStorage> expireFileList = this.list(queryWrapper);
        final Set<String> expireFileProtocolList = Optional.ofNullable(expireFileList).orElse(Collections.emptyList())
                .stream().map(FileStorage::getFileProtocol).filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(expireFileProtocolList)) {
            return;
        }
        this.remove(queryWrapper);
        expireFileProtocolList.stream().forEach(minioStorageService::deleteFile);
    }

    /**
     * 获取日期路径
     *
     * @return
     */
    private String getDatePath() {
        LocalDate now = LocalDate.now();
        return new StringBuilder()
                .append(now.getYear())
                .append(IMinioStorageService.FILE_PROTOCOL_SPILT)
                .append(now.getMonthValue())
                .append(IMinioStorageService.FILE_PROTOCOL_SPILT)
                .append(now.getDayOfMonth())
                .toString();
    }

    /**
     * 文件上传前进行校验
     *
     * @param file
     */
    private void checkBeforeUpload(MultipartFile file) {
        checkFileSize(file);
    }

    private void checkFileSize(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件上传失败，文件内容不能为空");
        }
    }
}
