package com.baeldung.image;

import org.springframework.ai.autoconfigure.anthropic.AnthropicAutoConfiguration;
import org.springframework.ai.autoconfigure.bedrock.converse.BedrockConverseProxyChatAutoConfiguration;
import org.springframework.ai.autoconfigure.ollama.OllamaAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.chroma.ChromaVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Exclude the configurations that are from the shared codebase
*/
@SpringBootApplication(
    exclude = {
        AnthropicAutoConfiguration.class,
        BedrockConverseProxyChatAutoConfiguration.class,
        ChromaVectorStoreAutoConfiguration.class,
        OllamaAutoConfiguration.class,
        PgVectorStoreAutoConfiguration.class
    }
)
public class ImageApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ImageApplication.class);
        app.setAdditionalProfiles("image");
        app.run(args);
    }

}
