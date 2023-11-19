package com.baeldung.concurrent.executorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ScheduledExecutorServiceDemo {

	private Task runnableTask;
	private CallableTask callableTask;
	private void execute() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		getTasksToRun().apply(executorService);
		executorService.shutdown();
	}

	private void executeWithMultiThread() {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		getTasksToRun().apply(executorService);
		executorService.shutdown();
	}

	private Function<ScheduledExecutorService, Void> getTasksToRun() {

		runnableTask = new Task();
		callableTask = new CallableTask();

		return (executorService -> {
			Future<String> resultFuture = executorService.schedule(callableTask, 1, TimeUnit.SECONDS);
			executorService.scheduleAtFixedRate( runnableTask, 100, 450, TimeUnit.SECONDS);
			executorService.scheduleWithFixedDelay( runnableTask, 100, 150, TimeUnit.SECONDS);
			return null;
		});
	}

	public static void main(String... args) {
		ScheduledExecutorServiceDemo demo = new ScheduledExecutorServiceDemo();
		demo.execute();
		demo.executeWithMultiThread();
	}

}
