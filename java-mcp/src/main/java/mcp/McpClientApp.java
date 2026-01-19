package mcp;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import io.modelcontextprotocol.spec.McpClientTransport;

public class McpClientApp {

    public static McpSyncClient getClient() {
        ServerParameters params = ServerParameters.builder("npx")
            .args("-y", "@modelcontextprotocol/server-everything")
            .build();

        JacksonMcpJsonMapper jsonMapper = new JacksonMcpJsonMapper(new ObjectMapper());
        McpClientTransport transport = new StdioClientTransport(params, jsonMapper);

        return io.modelcontextprotocol.client.McpClient.sync(transport)
            .build();

    }

    public static void main(String[] args) {
        McpSyncClient client = getClient();
        client.initialize();
    }
}
