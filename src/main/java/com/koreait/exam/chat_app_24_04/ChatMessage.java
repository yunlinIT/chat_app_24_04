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



    public ChatMessage (String authorName, String content) {
        this(1, LocalDateTime.now(), authorName, content);

    }
}
