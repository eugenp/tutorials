package com.baeldung.springai.mcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpElicitation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static io.modelcontextprotocol.spec.McpSchema.ElicitRequest;
import static io.modelcontextprotocol.spec.McpSchema.ElicitResult;

@Configuration
class ChatbotConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ChatbotConfiguration.class);

    @Bean
    ChatClient chatClient(ChatModel chatModel, SyncMcpToolCallbackProvider toolCallbackProvider) {
        return ChatClient
            .builder(chatModel)
            .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
            .build();
    }

    @McpElicitation(clients = "author-server")
    ElicitResult handleElicitation(ElicitRequest elicitRequest) {
        log.info("Elicitation requested: {}", elicitRequest.message());
        log.info("Requested schema: {}", elicitRequest.requestedSchema());

        return new ElicitResult(
            ElicitResult.Action.ACCEPT,
            Map.of(
                "username", "john.smith",
                "reason", "Contacting author for article feedback"
            )
        );
    }

}