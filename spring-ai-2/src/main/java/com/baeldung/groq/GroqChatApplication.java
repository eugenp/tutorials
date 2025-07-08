package com.baeldung.groq;

import org.springframework.ai.autoconfigure.anthropic.AnthropicAutoConfiguration;
import org.springframework.ai.autoconfigure.bedrock.converse.BedrockConverseProxyChatAutoConfiguration;
import org.springframework.ai.autoconfigure.ollama.OllamaAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.chroma.ChromaVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication(exclude = {
    OllamaAutoConfiguration.class,
    AnthropicAutoConfiguration.class,
    PgVectorStoreAutoConfiguration.class,
    ChromaVectorStoreAutoConfiguration.class,
    BedrockConverseProxyChatAutoConfiguration.class,
    RedisAutoConfiguration.class
})
public class GroqChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroqChatApplication.class, args);
    }
}
