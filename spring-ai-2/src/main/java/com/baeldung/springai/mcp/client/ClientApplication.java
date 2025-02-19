package com.baeldung.springai.mcp.client;

import org.springframework.ai.autoconfigure.bedrock.converse.BedrockConverseProxyChatAutoConfiguration;
import org.springframework.ai.autoconfigure.ollama.OllamaAutoConfiguration;
import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.chroma.ChromaVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Excluding the below auto-configurations to avoid start up
 * failure. Their corresponding starters are present on the classpath but are
 * only needed by other articles in the shared codebase.
 */
@SpringBootApplication(exclude = {
    OllamaAutoConfiguration.class,
    OpenAiAutoConfiguration.class,
    ChromaVectorStoreAutoConfiguration.class,
    BedrockConverseProxyChatAutoConfiguration.class
})
@PropertySource("classpath:application-mcp-client.properties")
class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}