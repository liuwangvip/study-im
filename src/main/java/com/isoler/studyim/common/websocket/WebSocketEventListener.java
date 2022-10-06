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

    private AtomicLong onlineCount = new AtomicLong(0);

    @Resource
    private SimpMessageSendingOperations messagingTemplate;

    @Resource
    private ISysUserService sysUserService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        final Principal principal = event.getUser();
        final SysUser sysUser = this.getSysUserByPrincipal(principal);
        if (sysUser == null) {
            return;
        }
        log.info(String.format("%s %s", sysUser.getUsername(), "进入"));
        onlineCount.getAndIncrement();
        messagingTemplate.convertAndSend("/topic/online", onlineCount.get());
        sysUserService.updateOnlineStatus(sysUser.getId(), OnlineStatusEnum.ON_LINE.getStatus());
    }

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


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        final Principal principal = event.getUser();
        final SysUser sysUser = this.getSysUserByPrincipal(principal);
        if (sysUser == null) {
            return;
        }
        log.info(String.format("%s %s", sysUser.getUsername(), "离开"));
        onlineCount.getAndDecrement();
        messagingTemplate.convertAndSend("/topic/online", onlineCount.get());
        sysUserService.updateOnlineStatus(sysUser.getId(), OnlineStatusEnum.OFF_LINE.getStatus());
        ChatMessage chatMessage = new ChatMessage()
                .setType(MessageTypeEnum.NOTICE_ENTER_OUT.getType())
                .setContent(String.format("%s %s", sysUser.getUsername(), "离开"));
        chatMessage.setCreateTime(DateUtil.toDate(LocalDateTime.now()));
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

}
