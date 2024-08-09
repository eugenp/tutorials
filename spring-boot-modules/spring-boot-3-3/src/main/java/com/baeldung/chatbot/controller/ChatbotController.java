package com.baeldung.chatbot.controller;

import com.baeldung.chatbot.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {
    @Autowired
    private ChatbotService chatbotService;

    @GetMapping("/api/chatbot/send")
    public String getChatbotResponse(@RequestParam String question) {
        return chatbotService.getResponse(question);
    }
}
