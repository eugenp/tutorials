package com.baeldung.mcp.mcpserveroauth2;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(McpServerOauth2Application.class, args);
    }

    @Bean
    public ToolCallbackProvider calculatorTools(CalculatorService calculatorService) {
        return MethodToolCallbackProvider.builder()
            .toolObjects(calculatorService)
            .build();
    }
}