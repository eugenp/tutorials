package com.baeldung.futuretocompletablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureToCompletableFuture {

    public static <T> CompletableFuture<T> toCompletableFuture(Future<T> future, ExecutorService executor) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        executor.submit(() -> {
            try {
                completableFuture.complete(future.get());
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    public static CompletableFuture<Void> allOfFutures(List<Future<String>> futures, ExecutorService executor) {
        // Convert all Future objects into CompletableFuture instances
        List<CompletableFuture<String>> completableFutures = futures.stream()
            .map(future -> FutureToCompletableFuture.toCompletableFuture(future, executor))
            .toList();

        // Combine all CompletableFuture instances into one
        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
    }
}
