package com.baeldung.concurrent.Scheduledexecutorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {

	public void execute() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> scheduledFuture = executorService.schedule(() -> {
			// Task
		}, 1, TimeUnit.SECONDS);

		executorService.scheduleAtFixedRate(() -> {
			// Task
		}, 1, 10, TimeUnit.SECONDS);

		executorService.scheduleWithFixedDelay(() -> {
			// Task
		}, 1, 10, TimeUnit.SECONDS);

		Future<String> future = executorService.schedule(() -> {
			// Task
			return "Hellow world";
		}, 1, TimeUnit.SECONDS);

		executorService.shutdown();
	}

}
