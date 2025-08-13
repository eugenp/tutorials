package com.baeldung.springai.mcp.oauth2;

import org.springframework.ai.autoconfigure.chat.client.ChatClientAutoConfiguration;
import org.springframework.ai.autoconfigure.mistralai.MistralAiAutoConfiguration;
import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {
    ChatClientAutoConfiguration.class,
    MongoAutoConfiguration.class,
    MistralAiAutoConfiguration.class,
    MongoDataAutoConfiguration.class,
    org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreAutoConfiguration.class,
    OpenAiAutoConfiguration.class,
})
class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(McpServerApplication.class);
        app.setAdditionalProfiles("mcp");
        app.run(args);
    }
}