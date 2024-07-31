package com.baeldung.concurrent.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubmitExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Callable<Integer> task = () -> {
            int[] numbers = { 1, 2, 3, 4, 5 };
            int sum = 0;
            for (int num : numbers) {
                sum += num;
            }
            return sum;
        };
        // Submit the task and obtain a Future
        Future<Integer> result = executorService.submit(task);
        try {
            // Get the result
            int sum = result.get();
            System.out.println("Sum calculated using submit:" + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}