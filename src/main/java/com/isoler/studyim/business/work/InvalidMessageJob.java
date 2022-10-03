package com.isoler.studyim.business.work;

import com.isoler.studyim.business.chatmessage.service.IChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: 定期设置消息失效
 *
 * @author liuwang
 * @Date 2022-10-03
 */
@Slf4j
@Service
public class InvalidMessageJob {

    @Resource
    private IChatMessageService chatMessageService;

    @Scheduled(cron = "${schedule.invalid-message.cron:0 0 1 * * ?}")
    public void execute() {
        log.info("开始定期设置消息失效");
        chatMessageService.invalidMessage();
    }
}
