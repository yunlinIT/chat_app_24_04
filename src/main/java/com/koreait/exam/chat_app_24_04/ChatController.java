package com.koreait.exam.chat_app_24_04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public record writeMessageRequest(String authorName, String content) {
    }

    public record writeMessageResponse(long id) {

    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<writeMessageResponse> writeMessage(@RequestBody writeMessageRequest req) {
        ChatMessage message = new ChatMessage(req.authorName, req.content);
        chatMessages.add(message);
        return new RsData<>(
                "S-1",
                "메세지가 작성됨",
                new writeMessageResponse(message.getId()
                )
        );
    }

    public record MessagesResponse(List<ChatMessage> messages, long count){

    }

    @GetMapping("/messages")
    @ResponseBody
    public RsData<MessagesResponse> messages() {
        return new RsData<>(
                "S-1",
                "성공",
                new MessagesResponse(chatMessages,chatMessages.size())
        );

    }
}