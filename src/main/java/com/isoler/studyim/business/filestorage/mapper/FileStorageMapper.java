package com.isoler.studyim.business.filestorage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isoler.studyim.business.filestorage.model.bean.FileStorage;

import java.time.LocalDateTime;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-10-02
 */
public interface FileStorageMapper extends BaseMapper<FileStorage> {
    /**
     * 清理过期的文件
     *
     * @param now
     */
    Integer cleanExpireFile(LocalDateTime now);
}