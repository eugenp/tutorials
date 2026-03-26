package com.baeldung.springai.mcp.test;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestMcpClientFactory {

    private final String protocol;

    public TestMcpClientFactory(@Value("${spring.ai.mcp.server.protocol:sse}") String protocol) {
        this.protocol = protocol;
    }

    public McpSyncClient create(String baseUrl) {
        String resolvedProtocol = protocol.trim().toLowerCase();
        return switch (resolvedProtocol) {
            case "sse" -> McpClient.sync(HttpClientSseClientTransport.builder(baseUrl)
                .sseEndpoint("/sse")
                .build()
            ).build();
            case "streamable" -> McpClient.sync(HttpClientStreamableHttpTransport.builder(baseUrl)
                .endpoint("/mcp")
                .build()
            ).build();
            default -> throw new IllegalArgumentException("Unknown MCP protocol: " + protocol);
        };
    }
}
