package com.baeldung.concurrent;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

/**
 * This is defined as a manual test because it tries to simulate the race conditions
 * in a concurrent program that is poorly designed and hence may fail nondeterministically.
 * This will help the CI jobs to ignore these tests and a developer to run them manually.
 *
 */
public class MyCounterTempusFugitManualTest {

	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	private static MyCounter counter = new MyCounter();

	@Test
	@Concurrent(count = 2)
	@Repeating(repetition = 10)
	public void runsMultipleTimes() {
		counter.increment();
	}

	@AfterClass
	public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
		assertEquals(counter.getCount(), 20);
	}

}
