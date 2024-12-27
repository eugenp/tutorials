package com.baeldung.concurrent.completablefuture.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CustomCompletableFuture<T> extends CompletableFuture<T> {
    private static final Executor executor = Executors.newSingleThreadExecutor(runnable -> new Thread(runnable, "Custom-Single-Thread"));

    public static <TYPE> CustomCompletableFuture<TYPE> supplyAsync(Supplier<TYPE> supplier) {
        CustomCompletableFuture<TYPE> future = new CustomCompletableFuture<>();
        executor.execute(() -> {
            try {
                future.complete(supplier.get());
            } catch (Exception ex) {
                future.completeExceptionally(ex);
            }
        });
        return future;
    }

    @Override
    public Executor defaultExecutor() {
        return executor;
    }

}