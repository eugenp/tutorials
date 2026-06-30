package com.baeldung.apachecamel;

import org.apache.camel.main.Main;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatAppWithMemory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatAppWithMemory.class);

    public static void main(String[] args) throws Exception {
        List<String> prompts = List.of("My name is Burak and I am a Java developer.", "What is my name and what do I do?");

        List<String> responses = runMultiTurnChat(prompts);
        responses.forEach(response -> LOGGER.info("AI: {}", response));
    }

    /**
     * Boots the Camel context, sends a sequence of prompts sequentially using 
     * a single Exchange to maintain memory, and returns the list of responses.
     */
    public static List<String> runMultiTurnChat(List<String> prompts) throws Exception {
        Main main = new Main();
        main.configure()
            .addRoutesBuilder(new ChatWithMemoryRoute());
        main.start();

        List<String> aiResponses = new ArrayList<>();

        try {
            ProducerTemplate template = main.getCamelContext()
                .createProducerTemplate();

            // Create a single Exchange to maintain the conversational memory state
            Exchange exchange = template.getCamelContext()
                .getEndpoint("direct:startChatWithMemory")
                .createExchange();

            for (String prompt : prompts) {
                exchange.getMessage()
                    .setBody(prompt);
                template.send("direct:startChatWithMemory", exchange);

                // Read and store the answer
                aiResponses.add(exchange.getMessage()
                    .getBody(String.class));
            }

            return aiResponses;

        } finally {
            main.stop();
        }
    }
}
