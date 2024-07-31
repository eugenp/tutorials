package com.baeldung.concurrent.completablefuture.retry;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class RetryCompletableFuture {
    public static <T> CompletableFuture<T> retryTask(Supplier<T> supplier, int maxRetries) {
        Supplier<T> retryableSupplier = retryFunction(supplier, maxRetries);
        return CompletableFuture.supplyAsync(retryableSupplier);
    }

    static <T> Supplier<T> retryFunction(Supplier<T> supplier, int maxRetries) {
        return () -> {
            int retries = 0;
            while (retries < maxRetries) {
                try {
                    return supplier.get();
                } catch (Exception e) {
                    retries++;
                }
            }
            throw new IllegalStateException(String.format("Task failed after %s attempts", maxRetries));
        };
    }

    public static <T> CompletableFuture<T> retryUnsafe(Supplier<T> supplier, int maxRetries) {
        CompletableFuture<T> cf = CompletableFuture.supplyAsync(supplier);
        sleep(100l);
        for (int i = 0; i < maxRetries; i++) {
            cf = cf.exceptionally(__ -> supplier.get());
        }
        return cf;
    }

    public static <T> CompletableFuture<T> retryNesting(Supplier<T> supplier, int maxRetries) {
        CompletableFuture<T> cf = CompletableFuture.supplyAsync(supplier);
        sleep(100);
        for (int i = 0; i < maxRetries; i++) {
            cf = cf.thenApply(CompletableFuture::completedFuture)
              .exceptionally(__ -> CompletableFuture.supplyAsync(supplier))
              .thenCompose(Function.identity());
        }
        return cf;
    }

    public static <T> CompletableFuture<T> retryExceptionallyAsync(Supplier<T> supplier, int maxRetries) {
        CompletableFuture<T> cf = CompletableFuture.supplyAsync(supplier);
        sleep(100);
        for (int i = 0; i < maxRetries; i++) {
            cf = cf.exceptionallyAsync(__ -> supplier.get());
        }
        return cf;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
