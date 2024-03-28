package com.baeldung.runvssupply;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RunAndSupplyCompare {

    public static void main(String args[]) throws ExecutionException, InterruptedException {
        chainingOperationCompare();
    }

    public static void inputAndReturnCompare() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            // Perform non-result producing task
            System.out.println("Task executed asynchronously");
        });

        CompletableFuture<String> supplyAsyncFuture = CompletableFuture.supplyAsync(() -> {
            // Perform result-producing task
            return "Result of the asynchronous computation";
        });
        // Get the result later
        String result = supplyAsyncFuture.get();
        System.out.println("Result: " + result);
    }

    public static void exceptionHandlingCompare() {
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            // Task that may throw an exception
            throw new RuntimeException("Exception occurred in asynchronous task");
        });
        try {
            runAsyncFuture.get();
            // Exception will be thrown here
        } catch (ExecutionException ex) {
            Throwable cause = ex.getCause();
            System.out.println("Exception caught: " + cause.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture<Object> supplyAsyncFuture = CompletableFuture.supplyAsync(() -> {
                // Task that may throw an exception
                throw new RuntimeException("Exception occurred in asynchronous task");
            })
            .exceptionally(ex -> {
                // Exception handling logic
                return "Default value";
            });

        Object result = supplyAsyncFuture.join();
        // Get the result or default value
        System.out.println("Result: " + result);
    }

    public static void chainingOperationCompare() {
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            // Perform non-result producing task
            System.out.println("Task executed asynchronously");
        });
        runAsyncFuture.thenRun(() -> {
            // Execute another task after the completion of runAsync()
            System.out.println("Another task executed after runAsync() completes");
        });

        CompletableFuture<String> supplyAsyncFuture = CompletableFuture.supplyAsync(() -> {
            // Perform result-producing task
            return "Result of the asynchronous computation";
        });
        supplyAsyncFuture.thenApply(result -> {
                // Transform the result
                return result.toUpperCase();
            })
            .thenAccept(transformedResult -> {
                // Consume the transformed result
                System.out.println("Transformed Result: " + transformedResult);
            });
    }
}
