package com.baeldung.donerunnables;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableCompletionCheckerWithThreadPoolExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunnableCompletionCheckerWithThreadPoolExecutor.class);
    private static final int NUMBER_OF_RUNNABLES = 5;
    private static final int PAUSE_MILLIS = 1000;
    private static final int NUMBER_OF_THREADS = 5;

    private static Runnable RUNNABLE = () -> {
        try {
            LOGGER.info("launching runnable");
            Thread.sleep(PAUSE_MILLIS);
        } catch (InterruptedException e) {
        }
    };

    public static void main(String args[]) throws InterruptedException {
        List<Runnable> runnables = IntStream.range(0, NUMBER_OF_RUNNABLES)
            .mapToObj(x -> RUNNABLE)
            .collect(Collectors.toList());
        ThreadPoolExecutor executor = createThreadPoolExecutor(runnables);
        executor.shutdown();
        LOGGER.info("After a timeout of 0 seconds, every Runnable is done: {}", isEveryRunnableDone(executor, 0));
        Thread.sleep(100);
        LOGGER.info("After a timeout of 100 milliseconds, every Runnable is done: {}", isEveryRunnableDone(executor, 100));
        Thread.sleep(2000);
        LOGGER.info("After a timeout of 2 seconds, every Runnable is done: {}", isEveryRunnableDone(executor, 1500));
    }

    public static ThreadPoolExecutor createThreadPoolExecutor(List<Runnable> runnables) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        runnables.forEach(executor::execute);
        return executor;
    }

    public static boolean isEveryRunnableDone(ThreadPoolExecutor executor, int timeout) throws InterruptedException {
        return executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
    }

}
