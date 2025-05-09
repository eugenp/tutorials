package com.baeldung;

import org.junit.Rule;
import org.junit.Test;

public class RetryTestJUnit4 {
	@Rule
	public RetryRule retryRule = new RetryRule(3);
	private static int attempt = 0;

	@Test
	public void testWithRetry() {
		attempt++;
		System.out.println("Test attempt: " + attempt);
		if (attempt < 3) {
			throw new RuntimeException("Failing test");
		}
	}
}
