package com.baeldung.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * This class demonstrates the usage of Guava's exiting executor services that keep the VM from hanging.
 * Without the exiting executor service, the task would hang indefinitely.
 * This behaviour cannot be demonstrated in JUnit tests, as JUnit kills the VM after the tests.
 */
public class ExitingExecutorServiceExample {

    public static void main(String... args) {

        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        final ExecutorService executorService = MoreExecutors.getExitingExecutorService(executor, 100, TimeUnit.MILLISECONDS);

        executorService.submit((Runnable) () -> {
            while (true) {
            }
        });

    }

}
