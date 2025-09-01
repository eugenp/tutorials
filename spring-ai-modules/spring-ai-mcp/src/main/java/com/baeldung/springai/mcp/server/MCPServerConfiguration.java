package com.baeldung.springai.mcp.server;

import io.modelcontextprotocol.server.McpSyncServer;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;

@Configuration
class MCPServerConfiguration {

    // --- Unconditional tool registration at starup ---
    // Uncomment the below bean to enable unconditional tool registration
    /*
    @Bean
    ToolCallbackProvider authorTools() {
        return MethodToolCallbackProvider
            .builder()
            .toolObjects(new AuthorRepository())
            .build();
    }
    */

    // --- Runtime conditional tool registration ---
    // Comment the below bean to disable conditional tool registration
    @Bean
    CommandLineRunner commandLineRunner(
        McpSyncServer mcpSyncServer,
        @Value("${com.baeldung.author-tools.enabled:false}") boolean authorToolsEnabled
    ) {
        return args -> {
            if (authorToolsEnabled) {
                ToolCallback[] toolCallbacks = ToolCallbacks.from(new AuthorRepository());
                List<SyncToolSpecification> tools = McpToolUtils.toSyncToolSpecifications(toolCallbacks);
                tools.forEach(tool -> {
                    mcpSyncServer.addTool(tool);
                });
            }
        };
    }

}