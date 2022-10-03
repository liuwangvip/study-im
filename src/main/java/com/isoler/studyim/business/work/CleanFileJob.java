package com.isoler.studyim.business.work;

import com.isoler.studyim.business.filestorage.service.IFileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * description: 定期清理过期的文件
 *
 * @author liuwang
 * @Date 2022-10-03
 */
@Slf4j
@Service
public class CleanFileJob {

    @Resource
    private IFileStorageService fileStorageService;

    @Scheduled(cron = "${schedule.clean-file.cron:0 0 0 * * ?}")
    public void execute() {
        log.info("开始定期清理过期的文件");
        fileStorageService.cleanExpireFile(LocalDateTime.now());
    }
}
