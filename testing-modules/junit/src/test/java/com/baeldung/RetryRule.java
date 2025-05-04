package com.baeldung;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {
	private final int retryCount;

	public RetryRule(int retryCount) {
		this.retryCount = retryCount;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Throwable failure = null;
				for (int i = 0; i < retryCount; i++) {
					try {
						base.evaluate();
						return;
					} catch (Throwable t) {
						failure = t;
						System.out.println("Retry " + (i + 1) + "/" + retryCount + " for test " + description.getDisplayName());
					}
				}
				throw failure;
			}
		};
	}
}
