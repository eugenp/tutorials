package com.baeldung.chatbot.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class WhatsAppService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);

    @Value("${whatsapp.api_url}")
    private String apiUrl;

    @Value("${whatsapp.access_token}")
    private String apiToken;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProducerTemplate producerTemplate;
    
    @Autowired
    private ChatbotService chatbotService;

    @PostConstruct
    public void init() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
                jacksonDataFormat.setPrettyPrint(true);

                from("direct:sendWhatsAppMessage")
                    .setHeader("Authorization", constant("Bearer " + apiToken))
                    .setHeader("Content-Type", constant("application/json"))
                    .marshal(jacksonDataFormat)
                    .process(exchange -> {
                        logger.debug("Sending JSON: {}", exchange.getIn().getBody(String.class));
                    }).to(apiUrl).process(exchange -> {
                        logger.debug("Response: {}", exchange.getIn().getBody(String.class));
                    });
            }
        });
    }

    public void sendWhatsAppMessage(String toNumber, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", toNumber);
        body.put("type", "text");

        Map<String, String> text = new HashMap<>();
        text.put("body", message);
        body.put("text", text);

        producerTemplate.sendBody("direct:sendWhatsAppMessage", body);
    }

    public void processIncomingMessage(String payload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            JsonNode messages = jsonNode.at("/entry/0/changes/0/value/messages");
            if (messages.isArray() && messages.size() > 0) {
                String receivedText = messages.get(0).at("/text/body").asText();
                String fromNumber = messages.get(0).at("/from").asText();
                logger.debug(fromNumber + " sent the message: " + receivedText);
                this.sendWhatsAppMessage(fromNumber, chatbotService.getResponse(receivedText));
            }
        } catch (Exception e) {
            logger.error("Error processing incoming payload: {} ", payload, e);
        }
    }
}
