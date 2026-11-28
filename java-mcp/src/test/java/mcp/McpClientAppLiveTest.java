package mcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;

// Requires Node.js with npx on the PATH, and access to the npm registry to download the server package
class McpClientAppLiveTest {

    private static McpSyncClient client;

    @BeforeAll
    static void init() {
        client = McpClientApp.getClient();
        client.initialize();
    }

    @AfterAll
    static void cleanUp() {
        if (client != null) {
            client.closeGracefully();
        }
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
