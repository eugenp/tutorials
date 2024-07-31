package com.baeldung.donerunnables;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableCompletionCheckerWithCompletableFuture {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunnableCompletionCheckerWithCompletableFuture.class);
    private static final int NUMBER_OF_RUNNABLES = 5;
    private static final int PAUSE_MILLIS = 1000;

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
        CompletableFuture<?>[] completableFutures = runAsynchronousTasks(runnables);
        LOGGER.info("Right after the creation of the completable future array, every completable future is done: {}", isEveryCompletableFutureDone(completableFutures));
        Thread.sleep((NUMBER_OF_RUNNABLES + 1) * PAUSE_MILLIS);
        LOGGER.info("After {} seconds, every completable future is done: {}", NUMBER_OF_RUNNABLES + 1, isEveryCompletableFutureDone(completableFutures));
    }

    public static CompletableFuture<?>[] runAsynchronousTasks(List<Runnable> runnables) {
        return runnables.stream()
            .map(CompletableFuture::runAsync)
            .toArray(CompletableFuture<?>[]::new);
    }

    public static boolean isEveryCompletableFutureDone(CompletableFuture<?>[] completableFutures) {
        return CompletableFuture.allOf(completableFutures)
            .isDone();
    }

}
