package mcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;

class McpClientAppServerUnitTest {

    private static McpSyncClient client;

    @BeforeAll
    static void init() {
        client = McpClientApp.getClient();
        client.initialize();
    }

    @Test
    void whenLogPromptToolCalled_thenReturnsResult() {
        McpSchema.CallToolRequest request = new McpSchema.CallToolRequest("", Map.of("prompt", "Unit test message"));

        McpServerFeatures.SyncToolSpecification toolSpec = LoggingTool.logPromptTool();
        McpSchema.CallToolResult result = toolSpec.callHandler()
            .apply(null, request);
        assertNotNull(result);
        assertFalse(result.isError());
        assertEquals("Input Prompt: Unit test message", ((McpSchema.TextContent) (result.content()
            .getFirst())).text());
    }

    @Test
    void whenCalledViaClient_thenReturnsLoggedResult() {
        McpSchema.CallToolRequest request = new McpSchema.CallToolRequest("echo", Map.of("message", "Client-server test message"));
        McpSchema.CallToolResult result = client.callTool(request);

        assertNotNull(result);
        assertNull(result.isError());
        assertEquals("Echo: Client-server test message", ((McpSchema.TextContent) (result.content()
            .getFirst())).text());
    }
}

