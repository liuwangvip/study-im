package com.isoler.studyim.business.work;

import com.isoler.studyim.business.chatmessage.service.IChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * description 删除过期消息
 *
 * @author liuwang
 * @Date 2022-10-04
 */
@Slf4j
@Service
public class CleanMessageJob {

    @Resource
    private IChatMessageService chatMessageService;

    @Scheduled(cron = "${schedule.clean-message.cron:0 0 2 * * ?}")
    public void execute() {
        log.info("定期清理超过30天的消息");
        final LocalDateTime localDateTime = LocalDateTime.now().minusDays(30);
        chatMessageService.cleanMessage(localDateTime);
    }
}
