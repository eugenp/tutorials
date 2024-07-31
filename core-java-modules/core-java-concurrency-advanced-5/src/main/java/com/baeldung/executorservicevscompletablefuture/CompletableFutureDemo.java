package com.baeldung.executorservicevscompletablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static void completableFutureMethod() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 42;
        });

        System.out.println(future.join());
    }

    public static void chainingTaskExample() {
        CompletableFuture<Integer> firstTask = CompletableFuture.supplyAsync(() -> {
            return 42;
        });

        CompletableFuture<String> secondTask = firstTask.thenApply(result -> {
            return "Result based on Task 1: " + result;
        });

        System.out.println(secondTask.join());
    }

    public static void exceptionHandlingExample() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                // Simulate a task that might throw an exception
                if (true) {
                    throw new RuntimeException("Something went wrong!");
                }
                return "Success";
            })
            .exceptionally(ex -> {
                System.err.println("Error in task: " + ex.getMessage());
                // Can optionally return a default value
                return "Error occurred";
            });

        future.thenAccept(result -> System.out.println("Result: " + result));
    }

    public static void timeoutExample() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.println("Task execution timed out!");
            }
            return "Task completed";
        });

        CompletableFuture<String> timeoutFuture = future.completeOnTimeout("Timed out!", 2, TimeUnit.SECONDS);
        String result = timeoutFuture.join();
        System.out.println("Result: " + result);
    }

    public static void main(String[] args) {
        timeoutExample();
    }
}
