package com.baeldung.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * This is defined as a manual test because it tries to simulate the race conditions
 * in a concurrent program that is poorly designed and hence may fail nondeterministically.
 * This will help the CI jobs to ignore these tests and a developer to run them manually.
 *
 */
public class MyCounterSimpleManualTest {

	@Test
	public void testCounter() {
		MyCounter counter = new MyCounter();
		for (int i = 0; i < 500; i++)
			counter.increment();
		assertEquals(500, counter.getCount());
	}

	@Test
	public void testCounterWithConcurrency() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				counter.increment();
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}

	@Test
	public void testSummationWithConcurrencyAndWait() throws InterruptedException {
		int numberOfThreads = 2;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		MyCounter counter = new MyCounter();
		for (int i = 0; i < numberOfThreads; i++) {
			service.submit(() -> {
				try {
					counter.incrementWithWait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, counter.getCount());
	}

}
