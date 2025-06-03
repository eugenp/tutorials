package com.baeldung.springai.mcp.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatbotController {

    private final ChatbotService chatbotService;

    ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        String answer = chatbotService.chat(chatRequest.question());
        return ResponseEntity.ok(new ChatResponse(answer));
    }

    record ChatRequest(String question) {
    }

    record ChatResponse(String answer) {
    }

}