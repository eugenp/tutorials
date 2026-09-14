package com.baeldung.mcp.logging.client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpLogging;
import org.springframework.stereotype.Component;

import io.modelcontextprotocol.spec.McpSchema;

@Component
public class PasswordStrengthMcpClientHandlers {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordStrengthMcpClientHandlers.class);

	private final List<McpSchema.LoggingMessageNotification> receivedLogs = new CopyOnWriteArrayList<>();

	@McpLogging(clients = "password-strength-logging-server")
	public void handleLoggingMessage(McpSchema.LoggingMessageNotification notification) {
		LOGGER.info("Received server logging notification [{}]: {}", notification.level(), notification.data());
		receivedLogs.add(notification);
	}

	public List<McpSchema.LoggingMessageNotification> getReceivedLogs() {
		return receivedLogs;
	}

	public void reset() {
		receivedLogs.clear();
	}
}