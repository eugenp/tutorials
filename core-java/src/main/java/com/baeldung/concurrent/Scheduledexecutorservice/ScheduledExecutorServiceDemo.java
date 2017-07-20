package com.baeldung.concurrent.Scheduledexecutorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {

	public void execute() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		ScheduledFuture<?> scheduledFuture = executorService.schedule(new Runnable() {
			@Override
			public void run() {
				// task details
			}
		}, 1, TimeUnit.SECONDS);

		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// task details
			}
		}, 1, 10, TimeUnit.SECONDS);

		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				// task details
			}
		}, 1, 10, TimeUnit.SECONDS);

		Future<String> future = executorService.schedule(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "Hello World";
			}
		}, 1, TimeUnit.SECONDS);

		executorService.shutdown();
	}

}
