package com.baeldung.openapi.controller;

import com.baeldung.openapi.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST controller class that handles chat requests.
 * <p>
 * This controller creates a chat request and sends it to the OpenAI API, then returns the first message from the API response to the client.
 * The chat request is created with a prompt specified as a request parameter.
 */
@RestController
public class ChatController {
    /**
     * A logger instance for the ChatController class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChatController.class);

    /**
     * The ChatService instance used to create and send chat requests.
     */
    @Autowired
    private ChatService chatService;

    /**
     * Handles GET requests to the /chat endpoint by creating a chat request with the
     * specified prompt and sending it to the OpenAI API. Returns the first message from
     * the API response.
     *
     * @param prompt the prompt to send to the API
     * @return a ResponseEntity object containing the first message from the API response
     */
    @GetMapping(value = "/chat")
    public ResponseEntity<Object> chat(@RequestParam String prompt) {
        LOG.info("Received GET request on /chat endpoint with prompt: {}", prompt);
        return chatService.chatWithOpenAiGpt(prompt);
    }
}