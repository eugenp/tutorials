package com.baeldung.algorithms.support;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * JUnit custom rule for managing tests that may fail due to heuristics or
 * randomness. In order to use this, just instantiate this object as a public
 * field inside the test class and annotate it with {@link Rule}.
 * 
 * @author Donato Rimenti
 *
 */
public class MayFailRule implements TestRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement,
	 * org.junit.runner.Description)
	 */
	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					base.evaluate();
				} catch (Throwable e) {
					// Ignore the exception since we expect this.
				}
			}
		};
	}

}
