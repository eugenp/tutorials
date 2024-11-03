package com.baeldung.schedulingvirtualthread;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleVirtualThreadsMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

        try (virtualThreadExecutor) {
            var taskResult = schedule(() -> System.out.println("Running on a scheduled virtual thread!"), 5, TimeUnit.SECONDS, virtualThreadExecutor);

            try {
                Thread.sleep(10 * 1000); // Sleep for 10 seconds to wait task results
            } catch (InterruptedException e) {
                Thread.currentThread()
                    .interrupt();
            }

            System.out.println(taskResult.get());
        }

        try (virtualThreadExecutor) {
            var taskResult = schedule(() -> System.out.println("Running on a scheduled virtual thread!"), 5, ChronoUnit.SECONDS, virtualThreadExecutor);

            try {
                Thread.sleep(10 * 1000); // Sleep for 10 seconds to wait task results
            } catch (InterruptedException e) {
                Thread.currentThread()
                    .interrupt();
            }

            System.out.println(taskResult.get());
        }
    }

    static Future<?> schedule(Runnable task, int delay, TimeUnit unit, ExecutorService executorService) {
        return executorService.submit(() -> {
            ScheduledExecutorService singleThreadScheduler = Executors.newSingleThreadScheduledExecutor();

            // try with resources to close the executor properly
            try (singleThreadScheduler) {
                singleThreadScheduler.schedule(task, delay, unit);
            }

        });
    }

    static Future<?> schedule(Runnable task, int delay, TemporalUnit unit, ExecutorService executorService) {
        return executorService.submit(() -> {
            try {
                Thread.sleep(Duration.of(delay, unit));
            } catch (InterruptedException e) {
                Thread.currentThread()
                    .interrupt();
            }

            task.run();
        });
    }
}
