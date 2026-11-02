package com.baeldung.mcp.logging.server;

import java.util.List;

public record PasswordStrengthResult(int score, List<String> issues) {
}