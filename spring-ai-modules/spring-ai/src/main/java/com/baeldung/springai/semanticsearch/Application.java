package com.baeldung.springai.semanticsearch;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.chroma.ChromaVectorStoreAutoConfiguration;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = { OpenAiAutoConfiguration.class, ChromaVectorStoreAutoConfiguration.class })
@PropertySource("classpath:application-semantic-search.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public ChatModel chatModel(OllamaChatModel ollamaChatModel) {
        return ollamaChatModel;
    }
}
