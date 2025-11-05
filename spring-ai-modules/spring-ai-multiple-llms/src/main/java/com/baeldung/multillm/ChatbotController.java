package com.baeldung.multillm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatbotController {

    private final ChatbotService chatbotService;

    ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/api/chatbot/chat")
    ChatResponse chat(@RequestBody ChatRequest request) {
        String response = chatbotService.chat(request.prompt);
        return new ChatResponse(response);
    }

    record ChatRequest(String prompt) {}
    record ChatResponse(String response) {}

}