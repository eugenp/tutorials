package com.baeldung.concurrent.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class SharedObjectWithLock {

	Logger logger = LoggerFactory.getLogger(SharedObjectWithLock.class);

	ReentrantLock lock = new ReentrantLock(true);

	int counter = 0;

	public void perform() {

		lock.lock();
		logger.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");
		try {
			logger.info("Thread - " + Thread.currentThread().getName() + " processing");
			counter++;
		} catch (Exception exception) {
			logger.error(" Interrupted Exception ", exception);
		} finally {
			lock.unlock();
			logger.info("Thread - " + Thread.currentThread().getName() + " released the lock");
		}
	}

	public void performTryLock() {

		logger.info("Thread - " + Thread.currentThread().getName() + " attempting to acquire the lock");
		try {
			boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
			if (isLockAcquired) {
				try {
					logger.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");

					logger.info("Thread - " + Thread.currentThread().getName() + " processing");
					sleep(1000);
				} finally {
					lock.unlock();
					logger.info("Thread - " + Thread.currentThread().getName() + " released the lock");

				}
			}
		} catch (InterruptedException exception) {
			logger.error(" Interrupted Exception ", exception);
		}
		logger.info("Thread - " + Thread.currentThread().getName() + " could not acquire the lock");
	}

	public ReentrantLock getLock() {
		return lock;
	}

	boolean isLocked() {
		return lock.isLocked();
	}

	boolean hasQueuedThreads() {
		return lock.hasQueuedThreads();
	}

	int getCounter() {
		return counter;
	}

	public static void main(String[] args) {

		final int threadCount = 2;
		final ExecutorService service = Executors.newFixedThreadPool(threadCount);
		final SharedObjectWithLock object = new SharedObjectWithLock();

		service.execute(() -> {
			object.perform();
		});
		service.execute(() -> {
			object.performTryLock();
		});

		service.shutdown();

	}

}
