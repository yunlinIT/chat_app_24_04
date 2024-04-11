package com.koreait.exam.chat_app_24_04;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ChatMessage {
    private long id;
    private LocalDateTime createTime;
    private String authorName;
    private String content;

    public ChatMessage(String authorName, String content) {
        this(ChatMessageIdGenerator.getNextId(),LocalDateTime.now(),authorName,content);
    }

}

class ChatMessageIdGenerator{
    public static long id = 0;

    public static long getNextId(){
        return ++id;
    }
}