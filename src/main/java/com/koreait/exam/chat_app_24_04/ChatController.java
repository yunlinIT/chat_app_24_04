package com.koreait.exam.chat_app_24_04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public record writeMessageResponse(long id) {

    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<writeMessageResponse> writeMessage() {

        ChatMessage message = new ChatMessage("홍길동", "안녕하세요");

        chatMessages.add(message);

        return new RsData(
                "S-1",
                "메세지가 작성됨",
                new writeMessageResponse(message.getId()
                )
        );
    }


}