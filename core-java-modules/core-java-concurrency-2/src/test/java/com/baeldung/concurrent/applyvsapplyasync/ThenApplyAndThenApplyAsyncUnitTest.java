package com.baeldung.concurrent.applyvsapplyasync;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThenApplyAndThenApplyAsyncUnitTest {

    @Test
    public void givenCompletableFuture_whenUsingThenApply_thenResultIsAsExpected() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> thenApplyResultFuture = future.thenApply(num -> "Result: " + num);

        String thenApplyResult = thenApplyResultFuture.join();
        assertEquals("Result: 5", thenApplyResult);
    }

    @Test
    public void givenCompletableFuture_whenUsingThenApplyAsync_thenResultIsAsExpected() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> thenApplyAsyncResultFuture = future.thenApplyAsync(num -> "Result: " + num);

        String thenApplyAsyncResult = thenApplyAsyncResultFuture.join();
        assertEquals("Result: 5", thenApplyAsyncResult);
    }

    @Test
    public void givenCompletableFuture_whenUsingThenApply_thenExceptionIsPropagated() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> resultFuture = future.thenApply(num -> "Result: " + num / 0);
        assertThrows(CompletionException.class, () -> resultFuture.join());
    }

    @Test
    public void givenCompletableFuture_whenUsingThenApply_thenExceptionIsHandledAsExpected() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> resultFuture = future.thenApply(num -> "Result: " + num / 0);
        try {
            // Accessing the result
            String result = resultFuture.join();
            assertEquals("Result: 5", result);
        } catch (CompletionException e) {
            assertEquals("java.lang.ArithmeticException: / by zero", e.getMessage());
            System.err.println("Exception caught: " + e.getMessage());
        }
    }

    @Test
    public void givenCompletableFuture_whenUsingThenApplyAsync_thenExceptionIsHandledAsExpected() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<String> thenApplyAsyncResultFuture = future.thenApplyAsync(num -> "Result: " + num / 0);

        String result = thenApplyAsyncResultFuture.handle((res, error) -> {
                if (error != null) {
                    // Handle the error appropriately, e.g., return a default value
                    return "Error occurred";
                } else {
                    return res;
                }
            })
            .join(); // Now join() won't throw the exception
        assertEquals("Error occurred", result);
    }

    @Test
    public void givenCompletableFutureWithExecutor_whenUsingThenApplyAsync_thenThreadExecutesAsExpected() {
        ExecutorService customExecutor = Executors.newFixedThreadPool(4);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        }, customExecutor);

        CompletableFuture<String> resultFuture = future.thenApplyAsync(num -> "Result: " + num, customExecutor);

        String result = resultFuture.join();
        assertEquals("Result: 5", result);

        customExecutor.shutdown();
    }
}
