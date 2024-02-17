package com.baeldung.applyvsapplyasnc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThenApplyAndThenApplyAsyncCompare {

    public static void main(String args[]) {
        executionThreadCompare();
        exceptionHandlingCompare();
        controlThreadUsingExecutor();
    }

    public static void executionThreadCompare() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);

        // Using thenApply()
        CompletableFuture<String> thenApplyResultFuture = future.thenApply(num -> {
            // This function may execute synchronously on the calling thread
            System.out.println("Thread executing thenApply(): " + Thread.currentThread()
                .getName());
            return "Result: " + num;
        });

        String thenApplyResult = thenApplyResultFuture.join();
        System.out.println(thenApplyResult);

        // Using thenApplyAsync()
        CompletableFuture<String> thenApplyAsyncResultFuture = future.thenApplyAsync(num -> {
            // Transformation function
            System.out.println("Thread executing thenApplyAsync(): " + Thread.currentThread()
                .getName());
            return "Result: " + num;
        });

        String thenApplyAsyncResult = thenApplyAsyncResultFuture.join();
        System.out.println(thenApplyAsyncResult);
    }

    public static void exceptionHandlingCompare() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);

        // Using thenApply()
        CompletableFuture<String> resultFuture = future.thenApply(num -> "Result: " + num / 0);

        try {
            // Accessing the result
            String result = resultFuture.join();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Exception caught: " + e.getMessage());
        }

        // Using thenApplyAsync()
        CompletableFuture<String> thenApplyAsyncResultFuture = future.thenApplyAsync(num -> "Result: " + num / 0);
        thenApplyAsyncResultFuture.handle((result, error) -> {
                if (error != null) {
                    System.err.println("Exception caught: " + error.getMessage());
                    // Handle the error appropriately, e.g., return a default value
                    return "Error occurred";
                } else {
                    // Successful case
                    return result;
                }
            })
            .join(); // Now join() won't throw the exception
    }

    public static void controlThreadUsingExecutor() {
        // Create a custom executor
        ExecutorService customExecutor = Executors.newFixedThreadPool(4);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // Simulating a long-running computation
            try {
                Thread.sleep(2000); // Delay of 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        }, customExecutor);

        CompletableFuture<String> resultFuture = future.thenApplyAsync(num -> {
            // Transformation function
            System.out.println("Thread executing thenApplyAsync(): " + Thread.currentThread()
                .getName());
            return "Result: " + num;
        }, customExecutor); // Use the same custom executor

        String result = resultFuture.join();
        System.out.println(result);

        // Shutdown the custom executor
        customExecutor.shutdown();
    }
}
