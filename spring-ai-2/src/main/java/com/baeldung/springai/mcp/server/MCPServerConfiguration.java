package com.baeldung.springai.mcp.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MCPServerConfiguration {

    @Bean
    ToolCallbackProvider authorTools() {
        return MethodToolCallbackProvider
            .builder()
            .toolObjects(new AuthorRepository())
            .build();
    }

}