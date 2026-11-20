package com.baeldung.mcp.logging.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import org.springaicommunity.mcp.context.McpSyncRequestContext;

class PasswordStrengthServiceUnitTest {

	private final PasswordStrengthService passwordStrengthService = new PasswordStrengthService();

	@Test
	void whenPasswordIsStrong_thenScoreIsPerfectAndNoIssuesReported() {
		McpSyncRequestContext ctx = mock(McpSyncRequestContext.class);

		PasswordStrengthResult result = passwordStrengthService.checkStrength("Tr0ubad0urCastle!", ctx);

		assertThat(result.score()).isEqualTo(100);
		assertThat(result.issues()).isEmpty();
	}

	@Test
	void whenPasswordIsShortWithNoUppercaseOrDigits_thenAllThreeIssuesAreReported() {
		McpSyncRequestContext ctx = mock(McpSyncRequestContext.class);

		PasswordStrengthResult result = passwordStrengthService.checkStrength("lowercase", ctx);

		assertThat(result.issues()).containsExactlyInAnyOrder("too short", "no uppercase", "no digits");
		assertThat(result.score()).isEqualTo(25);
	}

	@Test
	void whenPasswordIsCommonlyUsed_thenErrorLevelNotificationIsSent() {
		McpSyncRequestContext ctx = mock(McpSyncRequestContext.class);

		PasswordStrengthResult result = passwordStrengthService.checkStrength("password1", ctx);

		assertThat(result.issues()).contains("commonly used");
		verify(ctx).error("Password found in common-password list");
	}

	@Test
	void whenCheckStrengthIsCalled_thenNotificationsAreSentAtLevelsMatchingEachFinding() {
		McpSyncRequestContext ctx = mock(McpSyncRequestContext.class);

		passwordStrengthService.checkStrength("weak", ctx);

		verify(ctx).debug("Evaluating password of length 4");
		verify(ctx).warn("Password shorter than recommended 12 characters");
		verify(ctx).warn("Password missing uppercase letters");
		verify(ctx).warn("Password missing digits");
		verify(ctx).info("Final score: 25");
	}
}