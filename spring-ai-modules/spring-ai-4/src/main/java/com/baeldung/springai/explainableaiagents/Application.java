package com.baeldung.springai.explainableaiagents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.ai.model.vertexai.autoconfigure.embedding.VertexAiEmbeddingConnectionAutoConfiguration.class,
    org.springframework.ai.model.vertexai.autoconfigure.embedding.VertexAiMultiModalEmbeddingAutoConfiguration.class,
    org.springframework.ai.model.vertexai.autoconfigure.embedding.VertexAiTextEmbeddingAutoConfiguration.class,
    org.springframework.ai.model.vertexai.autoconfigure.gemini.VertexAiGeminiChatAutoConfiguration.class,
})
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setAdditionalProfiles("explainableaiagents");
        app.run(args);
    }
}
