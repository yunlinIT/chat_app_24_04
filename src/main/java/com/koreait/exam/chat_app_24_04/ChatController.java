package com.koreait.exam.chat_app_24_04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public record writeMessageRequest(String authorName, String content) {
    }

    public record writeMessageResponse(long id) {

    }

    @GetMapping("/room")
    public String showRoom() {
        return "chat/room";
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<writeMessageResponse> writeMessage(@RequestBody writeMessageRequest req) {
        ChatMessage message = new ChatMessage(req.authorName, req.content);
        chatMessages.add(message);
        return new RsData<>(
                "S-1",
                "메세지가 작성됨",
                new writeMessageResponse(message.getId())
        );
    }

    public record MessagesRequest(Long fromId) {

    }

    public record MessagesResponse(List<ChatMessage> messages, long count) {

    }

    @GetMapping("/messages")
    @ResponseBody
    public RsData<MessagesResponse> messages(MessagesRequest req) {
//        log.debug("req : {}",req);

        List<ChatMessage> messages = chatMessages;

        // 번호가 입력 되었다면
        if (req.fromId != null) {
            // 해당 번호의 채팅 메세지가 전체 리스트의 몇번째 인덱스인지? 없다면
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId)
                    .findFirst()
                    .orElse(-1);

            if (index != -1) {
                // 만약에 index가 -1이 아니라면? 0번 부터 index번 까지 제거한 리스트를 만든다.
                messages = messages.subList(index + 1, messages.size());
            }
        }


        return new RsData<>(
                "S-1",
                "성공",
                new MessagesResponse(messages, messages.size())
        );

    }
}