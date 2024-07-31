package com.baeldung.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // Task using Runnable
        Runnable task = () -> {
            int[] numbers = { 1, 2, 3, 4, 5 };
            int sum = 0;
            for (int num : numbers) {
                sum += num;
            }
            System.out.println("Sum calculated using execute:" + sum);
        };
        // Submit the task using execute
        executorService.execute(task);
        executorService.shutdown();

    }

}
