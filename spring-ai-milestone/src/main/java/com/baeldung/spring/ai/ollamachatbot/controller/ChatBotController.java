package com.baeldung.spring.ai.ollamachatbot.controller;

import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.ai.ollamachatbot.model.ChatBotRequest;
import com.baeldung.spring.ai.ollamachatbot.model.ChatBotResponse;
import com.baeldung.spring.ai.ollamachatbot.service.HelpDeskChatbotAgentService;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController {
    private final HelpDeskChatbotAgentService helpDeskChatbotAgentService;

    public ChatBotController(OllamaChatClient chatBotService, HelpDeskChatbotAgentService helpDeskChatbotAgentService) {
        this.helpDeskChatbotAgentService = helpDeskChatbotAgentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatBotResponse> chat(@RequestBody ChatBotRequest chatBotRequest) {
        var chatResponse = helpDeskChatbotAgentService.call(chatBotRequest.getPromptMessage(), chatBotRequest.getContextId());

        return new ResponseEntity<>(new ChatBotResponse(chatResponse), HttpStatus.OK);
    }
}
