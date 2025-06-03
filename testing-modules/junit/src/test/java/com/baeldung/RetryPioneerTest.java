package com.baeldung;

import org.junitpioneer.jupiter.RetryingTest;

public class RetryPioneerTest {
	private static int attempt = 0;

	@RetryingTest(maxAttempts = 3)
	void testWithRetry() {
		attempt++;
		System.out.println("Test attempt: " + attempt);
		if (attempt < 3) {
			throw new RuntimeException("Failing test");
		}
	}
}
