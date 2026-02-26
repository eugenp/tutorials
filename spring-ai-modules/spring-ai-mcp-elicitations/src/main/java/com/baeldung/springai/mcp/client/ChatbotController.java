package com.baeldung.springai.mcp.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatbotController {

    private final ChatClient chatClient;

    ChatbotController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/chat")
    ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        String answer = chatClient
            .prompt()
            .user(chatRequest.question())
            .call()
            .content();
        return ResponseEntity.ok(new ChatResponse(answer));
    }

    record ChatRequest(String question) {
    }

    record ChatResponse(String answer) {
    }

}