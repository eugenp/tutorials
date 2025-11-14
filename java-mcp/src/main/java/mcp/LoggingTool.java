package mcp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;

public class LoggingTool {

    public static McpServerFeatures.SyncToolSpecification logPromptTool() {
        McpSchema.JsonSchema inputSchema = new McpSchema.JsonSchema("object", Map.of("prompt", String.class), List.of("prompt"), false, null, null);

        return new McpServerFeatures.SyncToolSpecification(
            new McpSchema.Tool("logPrompt", "Log Prompt", "Logs a provided prompt", inputSchema, null, null, null), (exchange, args) -> {
            String prompt = (String) args.get("prompt");
            return McpSchema.CallToolResult.builder()
                .content(List.of(new McpSchema.TextContent("Input Prompt: " + prompt)))
                .isError(false)
                .build();
        });
    }
}
