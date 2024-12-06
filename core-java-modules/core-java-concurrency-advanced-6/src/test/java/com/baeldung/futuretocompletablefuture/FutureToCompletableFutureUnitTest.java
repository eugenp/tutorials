package com.baeldung.futuretocompletablefuture;

import static com.baeldung.futuretocompletablefuture.FutureToCompletableFuture.allOfFutures;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

public class FutureToCompletableFutureUnitTest {

    @Test
    void givenFuture_whenGet_thenBlockingCall() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(1000); // Simulate delay
            return "Hello from Future!";
        });

        String result = future.get(); // Blocking call
        executor.shutdown();

        assertEquals("Hello from Future!", result);
    }

    @Test
    void givenFuture_whenWrappedInCompletableFuture_thenNonBlockingCall() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(1000);
            return "Hello from Future!";
        });

        CompletableFuture<String> completableFuture = FutureToCompletableFuture.toCompletableFuture(future, executor);

        completableFuture.thenAccept(result -> assertEquals("Hello from Future!", result));

        executor.shutdown();
    }

    @Test
    void givenFuture_whenTransformedAndChained_thenCorrectResult() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(1000);
            return "Hello from Future!";
        });

        CompletableFuture<String> completableFuture = FutureToCompletableFuture.toCompletableFuture(future, executor);

        completableFuture
            .thenApply(result -> result.toUpperCase()) // Transform result
            .thenAccept(transformedResult -> assertEquals("HELLO FROM FUTURE!", transformedResult));

        executor.shutdown();
    }

    @Test
    void givenFuture_whenWrappedUsingSupplyAsync_thenNonBlockingCall() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(() -> {
            Thread.sleep(1000);
            return "Hello from Future!";
        });

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        completableFuture.thenAccept(result -> assertEquals("Hello from Future!", result));

        executor.shutdown();
    }

    @Test
    void givenMultipleFutures_whenCombinedWithAllOf_thenAllResultsAggregated() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Simulating multiple Futures
        List<Future<String>> futures = List.of(
            executor.submit(() -> {
                Thread.sleep(1000);
                return "Task 1 Result";
            }),
            executor.submit(() -> {
                Thread.sleep(500);
                return "Task 2 Result";
            }),
            executor.submit(() -> {
                Thread.sleep(1500);
                return "Task 3 Result";
            })
        );

        // Combine all Futures using the allOfFutures method
        CompletableFuture<Void> allOf = allOfFutures(futures, executor);

        // Verify that all tasks are completed
        allOf.thenRun(() -> {
            try {
                List<String> results = futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

                // Assertions to verify the results
                assertEquals(3, results.size());
                assertTrue(results.contains("Task 1 Result"));
                assertTrue(results.contains("Task 2 Result"));
                assertTrue(results.contains("Task 3 Result"));
            } catch (Exception e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }).join();

        executor.shutdown();
    }
}
