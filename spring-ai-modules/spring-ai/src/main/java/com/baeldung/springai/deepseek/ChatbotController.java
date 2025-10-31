package com.baeldung.springai.deepseek;

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
        ChatResponse chatResponse = chatbotService.chat(chatRequest);
        return ResponseEntity.ok(chatResponse);
    }

}