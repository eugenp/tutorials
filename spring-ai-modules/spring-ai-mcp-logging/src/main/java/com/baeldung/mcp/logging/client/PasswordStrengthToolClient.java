package com.baeldung.mcp.logging.client;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;

@Service
public class PasswordStrengthToolClient {

	public static final String CHECK_PASSWORD_STRENGTH_TOOL = "check_password_strength";

	private final McpSyncClient mcpSyncClient;

	public PasswordStrengthToolClient(List<McpSyncClient> mcpSyncClients) {
		if (mcpSyncClients.isEmpty()) {
			throw new IllegalStateException("No McpSyncClient beans were configured");
		}
		this.mcpSyncClient = mcpSyncClients.get(0);
	}

	public McpSchema.CallToolResult checkPasswordStrength(String password) {
		McpSchema.CallToolRequest request = new McpSchema.CallToolRequest(CHECK_PASSWORD_STRENGTH_TOOL,
				Map.of("password", password));
		return mcpSyncClient.callTool(request);
	}
}