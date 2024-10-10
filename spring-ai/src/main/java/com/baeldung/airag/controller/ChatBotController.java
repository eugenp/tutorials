package com.baeldung.airag.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.airag.service.ChatBotService;

@RestController
public class ChatBotController {
    @Autowired
    private ChatBotService chatBotService;

    @GetMapping("/chat")
    public Map chat(@RequestParam(name = "query") String query) {
        return Map.of("answer", chatBotService.chat(query));
    }
}
