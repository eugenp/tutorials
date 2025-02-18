package com.baeldung.springai.mcp.client;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ChatbotConfiguration {

    @Bean
    ChatClient chatClient(ChatModel chatModel, List<McpSyncClient> mcpClients) {
        return ChatClient
            .builder(chatModel)
            .defaultTools(new SyncMcpToolCallbackProvider(mcpClients).getToolCallbacks())
            .build();
    }

}