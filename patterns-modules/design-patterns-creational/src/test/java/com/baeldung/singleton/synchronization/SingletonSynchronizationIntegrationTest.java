package com.baeldung.singleton.synchronization;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the singleton synchronization package with the same name.
 * 
 * @author Donato Rimenti
 *
 */
public class SingletonSynchronizationIntegrationTest {

	/**
	 * Size of the thread pools used.
	 */
	private static final int POOL_SIZE = 1_000;
	
	/**
	 * Number of tasks to submit.
	 */
	private static final int TASKS_TO_SUBMIT = 1_000_000;

	/**
	 * Tests the thread-safety of {@link DraconianSingleton}.
	 */
	@Test
	public void givenDraconianSingleton_whenMultithreadInstancesEquals_thenTrue() {
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		Set<DraconianSingleton> resultSet = Collections.synchronizedSet(new HashSet<>());

		// Submits the instantiation tasks.
		for (int i = 0; i < TASKS_TO_SUBMIT; i++) {
			executor.submit(() -> resultSet.add(DraconianSingleton.getInstance()));
		}

		// Since the instance of the object we inserted into the set is always
		// the same, the size should be one.
		Assert.assertEquals(1, resultSet.size());
	}
	
	/**
	 * Tests the thread-safety of {@link DclSingleton}.
	 */
	@Test
	public void givenDclSingleton_whenMultithreadInstancesEquals_thenTrue() {
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		Set<DclSingleton> resultSet = Collections.synchronizedSet(new HashSet<>());

		// Submits the instantiation tasks.
		for (int i = 0; i < TASKS_TO_SUBMIT; i++) {
			executor.submit(() -> resultSet.add(DclSingleton.getInstance()));
		}

		// Since the instance of the object we inserted into the set is always
		// the same, the size should be one.
		Assert.assertEquals(1, resultSet.size());
	}

	/**
	 * Tests the thread-safety of {@link EarlyInitSingleton}.
	 */
	@Test
	public void givenEarlyInitSingleton_whenMultithreadInstancesEquals_thenTrue() {
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		Set<EarlyInitSingleton> resultSet = Collections.synchronizedSet(new HashSet<>());

		// Submits the instantiation tasks.
		for (int i = 0; i < TASKS_TO_SUBMIT; i++) {
			executor.submit(() -> resultSet.add(EarlyInitSingleton.getInstance()));
		}

		// Since the instance of the object we inserted into the set is always
		// the same, the size should be one.
		Assert.assertEquals(1, resultSet.size());
	}

	/**
	 * Tests the thread-safety of {@link InitOnDemandSingleton}.
	 */
	@Test
	public void givenInitOnDemandSingleton_whenMultithreadInstancesEquals_thenTrue() {
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		Set<InitOnDemandSingleton> resultSet = Collections.synchronizedSet(new HashSet<>());

		// Submits the instantiation tasks.
		for (int i = 0; i < TASKS_TO_SUBMIT; i++) {
			executor.submit(() -> resultSet.add(InitOnDemandSingleton.getInstance()));
		}

		// Since the instance of the object we inserted into the set is always
		// the same, the size should be one.
		Assert.assertEquals(1, resultSet.size());
	}

	/**
	 * Tests the thread-safety of {@link EnumSingleton}.
	 */
	@Test
	public void givenEnumSingleton_whenMultithreadInstancesEquals_thenTrue() {
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		Set<EnumSingleton> resultSet = Collections.synchronizedSet(new HashSet<>());

		// Submits the instantiation tasks.
		for (int i = 0; i < TASKS_TO_SUBMIT; i++) {
			executor.submit(() -> resultSet.add(EnumSingleton.INSTANCE));
		}

		// Since the instance of the object we inserted into the set is always
		// the same, the size should be one.
		Assert.assertEquals(1, resultSet.size());
	}
}
