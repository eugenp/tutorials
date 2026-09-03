package com.baeldung.mcp.logging.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springaicommunity.mcp.context.McpSyncRequestContext;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class PasswordStrengthService {

	private static final int MIN_RECOMMENDED_LENGTH = 12;

	private static final Set<String> COMMON_PASSWORDS = Set.of("password", "123456", "qwerty", "letmein", "password1",
			"admin");

	@McpTool(name = "check_password_strength", description = "Evaluates password strength and returns a score with recommendations.")
	public PasswordStrengthResult checkStrength(
			@McpToolParam(description = "The password to evaluate", required = true) String password,
			McpSyncRequestContext ctx) {
		ctx.debug("Evaluating password of length " + password.length());

		List<String> issues = new ArrayList<>();

		if (password.length() < MIN_RECOMMENDED_LENGTH) {
			ctx.warn("Password shorter than recommended " + MIN_RECOMMENDED_LENGTH + " characters");
			issues.add("too short");
		} else {
			ctx.debug("Length check passed");
		}

		if (!password.matches(".*[A-Z].*")) {
			ctx.warn("Password missing uppercase letters");
			issues.add("no uppercase");
		} else {
			ctx.debug("Uppercase check passed");
		}

		if (!password.matches(".*[0-9].*")) {
			ctx.warn("Password missing digits");
			issues.add("no digits");
		} else {
			ctx.debug("Digit check passed");
		}

		if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
			ctx.error("Password found in common-password list");
			issues.add("commonly used");
		}

		int score = Math.max(0, 100 - issues.size() * 25);
		ctx.info("Final score: " + score);

		return new PasswordStrengthResult(score, issues);
	}
}