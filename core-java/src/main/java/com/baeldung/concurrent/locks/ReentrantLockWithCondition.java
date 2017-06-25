package com.baeldung.concurrent.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentrantLockWithCondition {

	static Logger logger = LoggerFactory.getLogger(ReentrantLockWithCondition.class);

	Stack<String> stack = new Stack<>();
	int CAPACITY = 5;

	ReentrantLock lock = new ReentrantLock();
	Condition stackEmptyCondition = lock.newCondition();
	Condition stackFullCondition = lock.newCondition();

	public void pushToStack(String item) throws InterruptedException {
		try {
			lock.lock();
			if (stack.size() == CAPACITY) {
				logger.info(Thread.currentThread().getName() + " wait on stack full");
				stackFullCondition.await();
			}
			logger.info("Pushing the item " + item);
			stack.push(item);
			stackEmptyCondition.signalAll();
		} finally {
			lock.unlock();
		}

	}

	public String popFromStack() throws InterruptedException {
		try {
			lock.lock();
			if (stack.size() == 0) {
				logger.info(Thread.currentThread().getName() + " wait on stack empty");
				stackEmptyCondition.await();
			}
			return stack.pop();
		} finally {
			stackFullCondition.signalAll();
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		final int threadCount = 2;
		ReentrantLockWithCondition object = new ReentrantLockWithCondition();
		final ExecutorService service = Executors.newFixedThreadPool(threadCount);
		service.execute(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					object.pushToStack("Item " + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		service.execute(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					logger.info("Item popped " + object.popFromStack());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		service.shutdown();
	}
}
