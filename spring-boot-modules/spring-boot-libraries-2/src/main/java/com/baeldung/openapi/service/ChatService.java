package com.baeldung.openapi.service;

import com.baeldung.openapi.dto.ChatRequest;
import com.baeldung.openapi.dto.ChatResponse;
import com.baeldung.openapi.dto.Message;
import com.baeldung.openapi.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * A service class that handles chat requests and responses by interacting with the OpenAI API.
 */
@Service
public class ChatService {
    /**
     * A constant string representing a message indicating no response was received.
     */
    private static final String NO_RESPONSE_MESSAGE = "No valid response.";

    /**
     * A logger instance for the ChatService class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChatService.class);

    /**
     * The RestTemplate instance used to make HTTP requests to the OpenAI API.
     */
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    /**
     * The name of the OpenAI model used for chat requests.
     */
    @Value("${openai.model}")
    private String model;

    /**
     * The URL of the OpenAI API endpoint for chat requests.
     */
    @Value("${openai.api.url}")
    private String apiUrl;

    /**
     * Creates a chat request with the specified prompt and sends it to the OpenAI API.
     * Returns the first message from the API response.
     *
     * @param prompt the prompt to send to the API
     * @return a ResponseEntity object containing the first message from the API response
     */
    public ResponseEntity<Object> chatWithOpenAiGpt(String prompt) {
        ChatRequest request = new ChatRequest(model, prompt);
        LOG.info("Payload POST to OpenAI API: {}", request);
        try {
            ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            LOG.debug("Complete ChatResponse from OpenAI API : {}", response);
            String responseMessageContentFromChatGPT = response.getChoices()
                    .stream()
                    .findFirst()
                    .map(ChatResponse.Choice::getMessage)
                    .map(Message::getContent)
                    .orElse(NO_RESPONSE_MESSAGE);

            return ResponseEntity.ok(new ResponseMessage(responseMessageContentFromChatGPT));
        } catch (RestClientException e) {
            LOG.error("Failed to retrieve response from OpenAI API: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Failed to retrieve response from OpenAI API: " + e.getMessage()));
        }
    }
}