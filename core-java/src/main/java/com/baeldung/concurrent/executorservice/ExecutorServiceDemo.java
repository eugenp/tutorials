package com.baeldung.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

	ExecutorService executor = Executors.newFixedThreadPool(10);

	public void execute() {

		executor.execute(new Runnable() {
			@Override
			public void run() {
				// task details
			}
		});

		executor.submit(new Task());

		executor.shutdown();
		executor.shutdownNow();
		try {
			executor.awaitTermination(20l, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
