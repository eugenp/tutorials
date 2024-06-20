package com.baeldung.spring.ai.ollamachatbot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.ai.ollamachatbot.model.ChatBotRequest;
import com.baeldung.spring.ai.ollamachatbot.model.ChatBotResponse;
import com.baeldung.spring.ai.ollamachatbot.service.ChatBotService;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    private final ChatBotService chatBotService;

    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatBotResponse> chat(@RequestBody ChatBotRequest chatBotRequest) {
        return new ResponseEntity<>(chatBotService.chat(chatBotRequest), HttpStatus.OK);
    }
}
