package com.isoler.studyim.business.chatmessage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoler.studyim.business.chatmessage.model.bean.ChatMessage;

import java.time.LocalDateTime;

/**
 * <p>
 * 聊天消息 服务类
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
public interface IChatMessageService extends IService<ChatMessage> {
    /**
     * 让消息失效
     */
    void invalidMessage();

    /**
     * 清理消息
     *
     * @param localDateTime
     */
    void cleanMessage(LocalDateTime localDateTime);

    /**
     * 发送广播消息
     *
     * @param chatMessage
     */
    void sendPublicMessage(ChatMessage chatMessage);
}
