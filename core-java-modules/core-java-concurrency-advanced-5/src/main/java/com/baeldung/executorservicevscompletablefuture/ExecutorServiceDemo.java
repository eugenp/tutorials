package com.baeldung.executorservicevscompletablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorServiceDemo {

    public static void executorServiceMethod() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Integer> future = executor.submit(() -> {
            return 42;
        });

        System.out.println(future.get());
    }

    public static void chainingTaskExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> firstTask = executor.submit(() -> {return 42;});

        Future<String> secondTask = executor.submit(() -> {
            try {
                Integer result = firstTask.get();
                return "Result based on Task 1: " + result;
            } catch (InterruptedException | ExecutionException e) {
                // Handle exception
                System.err.println("Error occured: " + e.getMessage());
            }
            return null;
        });
        executor.shutdown();

        try {
            // Wait for the second task to complete and retrieve the result
            String result = secondTask.get();
            System.out.println(result); // Output: Result based on Task 1: 42
        } catch (InterruptedException | ExecutionException e) {
            // Handle exception
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    public static void exceptionHandlingExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> future = executor.submit(() -> {
            // Simulate a task that might throw an exception
            if (true) {
                throw new RuntimeException("Something went wrong!");
            }
            return "Success";
        });

        try {
            // This might block the main thread if the task throws an exception
            String result = future.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions thrown by the task or during retrieval
            System.err.println("Error occured: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    public static void timeoutExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> future = executor.submit(() -> {
            try {
                System.out.println("Start");
                Thread.sleep(5000);
                System.out.println("End");
            } catch (InterruptedException e) {
                System.err.println("Error occured: " + e.getMessage());
            }
            return "Task completed";
        });

        try {
            String result = future.get(2, TimeUnit.SECONDS);
            System.out.println("Result: " + result);
        } catch (TimeoutException e) {
            System.err.println("Task execution timed out!");
            future.cancel(true);
        } catch (Exception e) {
            System.err.println("Error occured: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        timeoutExample();
    }
}
