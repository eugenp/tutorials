package com.baeldung.chatbot.mongodb.controllers;

import com.baeldung.chatbot.mongodb.assistants.ArticleBasedAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatBotController {
    private final ArticleBasedAssistant assistant;

    @Autowired
    public ChatBotController(ArticleBasedAssistant assistant) {
        this.assistant = assistant;
    }

    @GetMapping("/chat-bot")
    public String answer(@RequestParam("question") String question) {
        return assistant.answer(question);
    }
}
