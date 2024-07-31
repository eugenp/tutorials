package com.baeldung.concurrent.futurevspromise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureVsPromise {

    private static final Logger LOG = LoggerFactory.getLogger(FutureVsPromise.class);

    private static void executePromiseTask() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // Submit a task and simulate an asynchronous operation
        CompletableFuture<String> completableFutureResult = CompletableFuture.supplyAsync(() -> {
            // Simulate a time-consuming task
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "CompletableFuture Result";
        }, executorService);

        completableFutureResult.thenAccept(result -> {
                LOG.debug("Promise Result: " + result);
            })
            .exceptionally(throwable -> {
                LOG.error("Error occurred: " + throwable.getMessage());
                return null;
            });

        // Additional work while the Promise is in progress
        LOG.debug("Doing other tasks...");

        executorService.shutdown();
    }

    private static void executeFutureTask() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> futureResult = executorService.submit(() -> {
            Thread.sleep(2000);
            return "Future Result";
        });

        while (!futureResult.isDone()) {
            LOG.debug("Future task is still in progress...");
            Thread.sleep(500);
        }

        String resultFromFuture = futureResult.get();
        LOG.debug("Future Result: " + resultFromFuture);

        executorService.shutdown();
    }

    private static void handleFutureControlFlow() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> futureTask = executorService.submit(() -> {
            LOG.debug("Future Task: Executing...");
            return 1;
        });

        try {
            int result = futureTask.get();
            LOG.debug("Future Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void handlePromiseControlFlow() {
        CompletableFuture<Integer> firstTask = CompletableFuture.supplyAsync(() -> {
                LOG.debug("Promise: First Task Executing...");
                return 1;
            })
            .thenApplyAsync(result -> {
                LOG.debug("Promise: Second Task Executing...");
                return result * 2;
            })
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    LOG.error("Error occurred: " + ex.getMessage());
                } else {
                    LOG.debug("Final result: " + result);
                }
            });
        try {
            int finalResult = firstTask.get();
            LOG.debug("Promise Result: " + finalResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handlePromiseError() {
        CompletableFuture<String> promiseWithError = new CompletableFuture<>();
        promiseWithError.completeExceptionally(new RuntimeException("An error occurred"));

        promiseWithError.exceptionally(throwable -> {
            LOG.error("Error occurred with CompletableFuture: " + throwable.getMessage());
            return "Fallback value";
        });
    }

    private static void handleFutureError() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> futureWithError = executorService.submit(() -> {
            throw new RuntimeException("An error occurred");
        });

        try {
            String result = futureWithError.get();
            LOG.debug("Result with Future: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void handlePromiseWritable() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<Integer> totalPromise = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }, executorService);

        totalPromise.thenAccept(value -> System.out.println("Total $" + value));
        totalPromise.complete(10);
    }
}
