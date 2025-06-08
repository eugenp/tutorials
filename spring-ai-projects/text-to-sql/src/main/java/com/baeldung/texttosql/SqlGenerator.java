package com.baeldung.texttosql;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class SqlGenerator {

    private final ChatClient chatClient;
    private final PromptTemplate promptTemplate;

    SqlGenerator(ChatClient chatClient, PromptTemplate promptTemplate) {
        this.chatClient = chatClient;
        this.promptTemplate = promptTemplate;
    }

    String generate(String question) {
        Prompt prompt = promptTemplate.create(Map.of("question", question));
        String response = chatClient
            .prompt(prompt)
            .call()
            .content();

        boolean isSelectQuery = response.startsWith("SELECT");
        if (Boolean.FALSE.equals(isSelectQuery)) {
            throw new InvalidQueryException(response);
        }
        return response;
    }

}