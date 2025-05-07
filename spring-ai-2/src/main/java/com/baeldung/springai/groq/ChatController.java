package com.baeldung.springai.groq;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GroqChatService chatService;

    public ChatController(GroqChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        String aiResponse = chatService.getChatResponse(userMessage);
        return ResponseEntity.ok(aiResponse);
    }
}