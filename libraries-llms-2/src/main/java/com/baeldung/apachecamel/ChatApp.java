package com.baeldung.apachecamel;

import org.apache.camel.main.Main;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatApp.class);

    public static void main(String[] args) throws Exception {
        String userPrompt = "Give me information about Baeldung.";
        String aiResponse = runChat(userPrompt);
        LOGGER.info(aiResponse);
    }

    /**
     * Boots a Camel context with ChatRoute, sends the given prompt
     * through direct:startChat, and returns the response.
     */
    public static String runChat(String userPrompt) throws Exception {
        Main main = new Main();
        main.configure()
            .addRoutesBuilder(new ChatRoute());
        main.start();

        try {
            ProducerTemplate template = main.getCamelContext()
                .createProducerTemplate();
            String aiResponse = template.requestBody("direct:startChat", userPrompt, String.class);

            return aiResponse;

        } finally {
            main.stop();
        }
    }
}
