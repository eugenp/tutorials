package com.baeldung.springai.agentskills.anthropic;

public record AnthropicDocument(String fileName, String mimeType, byte[] content) {
}