package com.baeldung.springai.texttosql;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
class SqlGenerator {

    private final ChatClient chatClient;

    SqlGenerator(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String generate(String question) {
        String response = chatClient
            .prompt(question)
            .call()
            .content();

        boolean isSelectQuery = response.startsWith("SELECT");
        if (Boolean.FALSE.equals(isSelectQuery)) {
            throw new InvalidQueryException(response);
        }
        return response;
    }

}