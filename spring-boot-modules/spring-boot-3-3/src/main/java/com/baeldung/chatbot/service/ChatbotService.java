package com.baeldung.chatbot.service;

import java.time.Duration;

import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.langchain4j.model.ollama.OllamaChatModel;
import jakarta.annotation.PostConstruct;

@Service
public class ChatbotService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    @Value("${ollama.api_url}")
    private String apiUrl;

    @Value("${ollama.model}")
    private String modelName;
    
    @Value("${ollama.timeout}")
    private int timeout;

    @Value("${ollama.max_response_length}")
    private int maxResponseLength;

    private OllamaChatModel ollamaChatModel;

    @PostConstruct
    public void init() {
        this.ollamaChatModel = OllamaChatModel.builder()
            .baseUrl(apiUrl)
            .modelName(modelName)
            .timeout(Duration.ofSeconds(timeout))
            .numPredict(maxResponseLength)
            .build();
    }

    public String getResponse(String question) {
        logger.debug("Sending to Ollama: {}",  question);
        String answer = ollamaChatModel.generate(question);
        logger.debug("Receiving from Ollama: {}",  answer);
        if (answer != null && !answer.isEmpty()) {
            return answer;
        } else {
            logger.error("Invalid Ollama response for:\n\n" + question);
            throw new ResponseStatusException(
                    HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    "Ollama didn't generate a valid response",
                    null);
        }
    }
}
