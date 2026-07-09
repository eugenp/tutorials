package com.baeldung.springai.chat;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class StreamingChatController {

    private final ChatService chatService;

    public StreamingChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/v1/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChatTokens(@RequestParam String prompt) {
        return chatService.streamChatTokens(prompt);
    }

}
