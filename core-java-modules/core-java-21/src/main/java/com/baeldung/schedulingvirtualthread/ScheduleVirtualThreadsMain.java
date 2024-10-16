package com.baeldung.schedulingvirtualthread;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ScheduleVirtualThreadsMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

        try (virtualThreadExecutor) {
            for (int i = 0; i < 10_000; i++) {
                schedule(mockSlowHelloWorldTask(Duration.of(2, SECONDS)), 3, TimeUnit.SECONDS, virtualThreadExecutor);
                schedule(mockSlowHelloWorldTask(Duration.of(2, SECONDS)), 3, ChronoUnit.SECONDS, virtualThreadExecutor))
            }
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
                // handle interrupted error
            }

            task.run();
        });
    }

    private static Runnable mockSlowHelloWorldTask(Duration time) {
        return () -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Hello World");
        };
    }
}
