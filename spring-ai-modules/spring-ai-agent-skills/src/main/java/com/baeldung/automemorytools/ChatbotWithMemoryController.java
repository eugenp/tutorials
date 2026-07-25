package com.baeldung.automemorytools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatbotWithMemoryController {

    private final ChatClient chatClient;

    ChatbotWithMemoryController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/chat-with-memory")
    ResponseEntity<String> chat(@RequestBody String question, @RequestHeader("X-Conversation-ID") String conversationId) {
        String answer = chatClient
                .prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(question)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
