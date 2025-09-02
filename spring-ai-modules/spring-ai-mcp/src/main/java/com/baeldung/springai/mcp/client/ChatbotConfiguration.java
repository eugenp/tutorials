package com.baeldung.springai.mcp.client;

import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.mcp.customizer.McpSyncClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatbotConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotConfiguration.class);

    @Bean
    ChatClient chatClient(ChatModel chatModel, SyncMcpToolCallbackProvider toolCallbackProvider) {
        return ChatClient
            .builder(chatModel)
            .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
            .build();
    }

    @Bean
    McpSyncClientCustomizer mcpSyncClientCustomizer() {
        return (name, mcpClientSpec) -> {
            mcpClientSpec.toolsChangeConsumer(tools -> {
                logger.info("Detected tools changes.");
            });
        };
    }

}