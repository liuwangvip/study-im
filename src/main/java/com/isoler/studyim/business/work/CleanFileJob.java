package com.isoler.studyim.business.work;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定期清理过期的文件
 */
@Service
public class CleanFileJob {

    @Scheduled(cron = "${schdule.clean-file.cron:0 0 0 * * ?}")
    public void execute() {

    }
}
