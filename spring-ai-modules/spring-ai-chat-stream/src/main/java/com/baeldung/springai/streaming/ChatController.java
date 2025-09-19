package com.baeldung.springai.streaming;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/chat")
    public Mono<String> chat(@RequestBody @Valid ChatRequest request) {
        return chatService.chatAsWord(request.getPrompt())
            .collectList()
            .map(list -> String.join("", list));
    }

    @PostMapping(value = "/chat-word", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatAsWord(@RequestBody @Valid ChatRequest request) {
        return chatService.chatAsWord(request.getPrompt());
    }

    @PostMapping(value = "/chat-chunk", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatAsChunk(@RequestBody @Valid ChatRequest request) {
        return chatService.chatAsChunk(request.getPrompt());
    }

    @PostMapping(value = "/chat-json", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatAsJson(@RequestBody @Valid ChatRequest request) {
        return chatService.chatAsJson(request.getPrompt());
    }

}
