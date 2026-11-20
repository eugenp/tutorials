package com.baeldung.springai.chat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/v1/chat/simple")
    public String simplifiedPrompt(@RequestParam(defaultValue = "Hello") String message) {
        return chatService.simplifiedPrompt(message);
    }

    @GetMapping("/v1/chat/fluent")
    public String fluentPrompt(@RequestParam String prompt) {
        return chatService.fluentPrompt(prompt);
    }

    @PostMapping("/v1/chat/review")
    public String reviewCode(@RequestParam(defaultValue = "Java") String language, @RequestBody String codeSnippet) {
        return chatService.reviewCode(language, codeSnippet);
    }

    @GetMapping("/v1/chat/grounded")
    public String searchGroundedPrompt(@RequestParam String currentEventQuery) {
        return chatService.searchGroundedPrompt(currentEventQuery);
    }

}
