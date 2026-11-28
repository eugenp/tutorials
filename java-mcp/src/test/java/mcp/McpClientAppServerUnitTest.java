package mcp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;

class McpClientAppServerUnitTest {

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
}
