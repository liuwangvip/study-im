package com.isoler.studyim.common.websocket;

import com.isoler.studyim.business.chatmessage.model.bean.ChatMessage;
import com.isoler.studyim.business.chatmessage.model.eo.MessageTypeEnum;
import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.business.user.model.eo.OnlineStatusEnum;
import com.isoler.studyim.business.user.service.ISysUserService;
import com.isoler.studyim.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.annotation.Resource;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class WebSocketEventListener {

    /**
     * 广播消息
     */
    public static final String SUPER_ADMIN_SENDER_ID = "superAdmin";
    public static final String SUPER_ADMIN_SENDER_NAME = "超级管理员";

    public AtomicLong onlineCount = new AtomicLong(0);

    @Resource
    private SimpMessageSendingOperations messagingTemplate;

    @Resource
    private ISysUserService sysUserService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor.wrap(event.getMessage());
        final Principal principal = event.getUser();
        final SysUser sysUser = this.getSysUserByPrincipal(principal);
        if (sysUser == null) {
            return;
        }
        onlineCount.getAndIncrement();
        sysUserService.updateOnlineStatus(sysUser.getId(), OnlineStatusEnum.ON_LINE.getStatus());
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor.wrap(event.getMessage());
        final Principal principal = event.getUser();
        final SysUser sysUser = this.getSysUserByPrincipal(principal);
        if (sysUser == null) {
            return;
        }
        onlineCount.getAndDecrement();
        noticeOnlineCount();
        noticeOnline(sysUser, MessageTypeEnum.NOTICE_OFFLINE, true);
        sysUserService.updateOnlineStatus(sysUser.getId(), OnlineStatusEnum.OFF_LINE.getStatus());
    }


    /**
     * 上/下线通知
     *
     * @param sysUser
     * @param messageTypeEnum
     */
    public void noticeOnline(SysUser sysUser, MessageTypeEnum messageTypeEnum, boolean sendMsg) {
        if (sysUser == null) {
            return;
        }
        log.info(String.format("%s %s", sysUser.getUsername(), messageTypeEnum.getDesc()));
        ChatMessage chatMessage = new ChatMessage()
                .setType(messageTypeEnum.getType())
                .setContent(String.format("%s %s", sysUser.getUsername(), messageTypeEnum.getDesc()));
        chatMessage.setCreateTime(DateUtil.toDate(LocalDateTime.now()));
        if (sendMsg) {
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

    /**
     * 发送在线人数消息
     */
    public void noticeOnlineCount() {
        messagingTemplate.convertAndSend("/topic/online", onlineCount.get());
    }

    /**
     * 获取当前登录用户信息
     *
     * @param obj
     * @return
     */
    private SysUser getSysUserByPrincipal(Object obj) {
        if (obj instanceof SysUser) {
            return (SysUser) obj;
        }
        if (obj instanceof Authentication) {
            Authentication authentication = (Authentication) obj;
            Object principal = authentication.getPrincipal();
            return getSysUserByPrincipal(principal);
        }
        return null;
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void correctOnlineCount() {
        final long l = sysUserService.countOnlineUser(OnlineStatusEnum.ON_LINE.getStatus());
        onlineCount.getAndSet(l);
    }

}
