package com.baeldung.mcp.logging.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.modelcontextprotocol.spec.McpSchema;

class PasswordStrengthMcpClientHandlersUnitTest {

	private final PasswordStrengthMcpClientHandlers handlers = new PasswordStrengthMcpClientHandlers();

	@Test
	void whenLoggingMessageReceived_thenStoredInReceivedLogs() {
		McpSchema.LoggingMessageNotification notification = McpSchema.LoggingMessageNotification.builder()
				.level(McpSchema.LoggingLevel.WARNING).logger("password-strength-logging-server")
				.data("Password shorter than recommended 12 characters").build();

		handlers.handleLoggingMessage(notification);

		assertThat(handlers.getReceivedLogs()).containsExactly(notification);
	}

	@Test
	void whenLoggingMessageCleared_thenReceivedLogsEmpty() {
		handlers.handleLoggingMessage(McpSchema.LoggingMessageNotification.builder().level(McpSchema.LoggingLevel.INFO)
				.logger("password-strength-logging-server").data("Final score: 25").build());

		handlers.reset();

		assertThat(handlers.getReceivedLogs()).isEmpty();
	}
}