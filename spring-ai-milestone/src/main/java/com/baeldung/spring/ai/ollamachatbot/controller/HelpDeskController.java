package com.baeldung.spring.ai.ollamachatbot.controller;

import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.ai.ollamachatbot.model.HelpDeskRequest;
import com.baeldung.spring.ai.ollamachatbot.model.HelpDeskResponse;
import com.baeldung.spring.ai.ollamachatbot.service.HelpDeskChatbotAgentService;

@RestController
@RequestMapping("/helpdesk")
public class HelpDeskController {
    private final HelpDeskChatbotAgentService helpDeskChatbotAgentService;

    public HelpDeskController(OllamaChatClient chatBotService, HelpDeskChatbotAgentService helpDeskChatbotAgentService) {
        this.helpDeskChatbotAgentService = helpDeskChatbotAgentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<HelpDeskResponse> chat(@RequestBody HelpDeskRequest helpDeskRequest) {
        var chatResponse = helpDeskChatbotAgentService.call(helpDeskRequest.getPromptMessage(), helpDeskRequest.getHistoryId());

        return new ResponseEntity<>(new HelpDeskResponse(chatResponse), HttpStatus.OK);
    }
}
