package com.baeldung.springai.mcp.oauth2.configuration;

import com.baeldung.springai.mcp.oauth2.StockInformationHolder;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("mcp")
@Configuration
public class McpServerConfiguration {

    @Bean
    public ToolCallbackProvider stockTools() {
        return MethodToolCallbackProvider
          .builder()
          .toolObjects(new StockInformationHolder())
          .build();
    }
}
