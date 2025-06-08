package com.baeldung.texttosql;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
class ChatbotConfiguration {

    @Bean
    PromptTemplate promptTemplate(
        @Value("classpath:prompt.st") Resource promptTemplate,
        @Value("classpath:db/migration/V01__creating_database_tables.sql") Resource ddlSchema
    ) throws IOException {
        PromptTemplate template = new PromptTemplate(promptTemplate);
        template.add("ddl", ddlSchema.getContentAsString(Charset.defaultCharset()));
        return template;
    }

    @Bean
    ChatClient chatClient(ChatModel chatModel) {
        return ChatClient
            .builder(chatModel)
            .build();
    }

}