package com.isoler.studyim.business.chatmessage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoler.studyim.business.chatmessage.model.bean.ChatMessage;
import com.isoler.studyim.business.chatmessage.model.dto.ChatMessagePageDto;
import com.isoler.studyim.business.chatmessage.model.eo.MessageTypeEnum;
import com.isoler.studyim.business.chatmessage.service.IChatMessageService;
import com.isoler.studyim.business.user.model.bean.SysUser;
import com.isoler.studyim.common.api.CommonResult;
import com.isoler.studyim.common.websocket.WebSocketEventListener;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 聊天消息 前端控制器
 * </p>
 *
 * @author AutoGenerated
 * @since 2022-07-28
 */
@Slf4j
@RestController
@Api(tags = "消息接口")
@RequestMapping("")
public class ChatMessageController {

    @Resource
    private IChatMessageService chatMessageService;

    @Resource
    private WebSocketEventListener webSocketEventListener;

    /**
     * 查询消息列表
     *
     * @param dto 参数
     * @return
     */
    @PostMapping("chat/page")
    public CommonResult<Page<ChatMessage>> pageChatMessage(@RequestBody ChatMessagePageDto dto) {
        return CommonResult.success(chatMessageService.pageChatMessage(dto));
    }

    /**
     * 发送广播消息
     *
     * @param chatMessage 消息内容
     * @return
     */
    @MessageMapping("/public.sendMessage")
    public ChatMessage sendPublicMessage(@Payload ChatMessage chatMessage) {
        chatMessageService.sendPublicMessage(chatMessage);
        return chatMessage;
    }

    /**
     * 上线消息
     *
     * @param chatMessage
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/chat.online")
    public ChatMessage onlineNotice(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
        if (authentication.getPrincipal() instanceof SysUser) {
            SysUser user = (SysUser) authentication.getPrincipal();
            webSocketEventListener.noticeOnline(user, MessageTypeEnum.NOTICE_ONLINE, true);
        }
        webSocketEventListener.noticeOnlineCount();
        return chatMessage;
    }


}
