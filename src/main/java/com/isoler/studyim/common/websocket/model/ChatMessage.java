package com.isoler.studyim.common.websocket.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String date;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        NOTICE
    }



}
