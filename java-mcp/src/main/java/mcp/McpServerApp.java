package mcp;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

public class McpServerApp {

    public static McpSyncServer createServer() {
        JacksonMcpJsonMapper jsonMapper = new JacksonMcpJsonMapper(new ObjectMapper());
        StdioServerTransportProvider transportProvider = new StdioServerTransportProvider(jsonMapper);

        return McpServer.sync(transportProvider)
            .serverInfo("baeldung-demo-server", "0.0.1")
            .capabilities(McpSchema.ServerCapabilities.builder()
                .tools(true)
                .logging()
                .build())
            .tools(LoggingTool.logPromptTool())
            .build();
    }

    public static void main(String[] args) {
        createServer();
    }
}
