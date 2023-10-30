package com.baeldung.concurrent.completablefuturelist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

public class Application {

    ScheduledExecutorService asyncOperationEmulation;

    Application initialize() {
        asyncOperationEmulation = Executors.newScheduledThreadPool(10);
        return this;
    }

    CompletableFuture<String> asyncOperation(String operationId) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        asyncOperationEmulation.submit(() -> {
            // The following lines simulate an exception happening on the 567th operation
            //  if (operationId.endsWith("567")) {
            //      cf.completeExceptionally(new Exception("Error on operation " + operationId));
            //      return;
            //  }
            try {
                Thread.sleep(100);
                cf.complete(operationId);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted error");
                cf.completeExceptionally(e);
            }
        });
        return cf;
    }

    void startNaive() {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            String operationId = "Naive-Operation-" + i;
            futures.add(asyncOperation(operationId));
        }

        CompletableFuture<List<String>> aggregate = CompletableFuture.completedFuture(new ArrayList<>());
        for (CompletableFuture<String> future : futures) {
            aggregate = aggregate.thenCompose(list -> {
                try {
                    list.add(future.get());
                    return CompletableFuture.completedFuture(list);
                } catch (Exception e) {
                    final CompletableFuture<List<String>> excFuture = new CompletableFuture<>();
                    excFuture.completeExceptionally(e);
                    return excFuture;
                }
            });
        }

        try {
            final List<String> results = aggregate.join();
            System.out.println("Printing first 10 results");
            for (int i = 0; i < 10; i++) {
                System.out.println("Finished " + results.get(i));
            }
        } finally {
            close();
        }
    }

    void start() {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            String operationId = "Operation-" + i;
            futures.add(asyncOperation(operationId));
        }
        CompletableFuture<?>[] futuresArray = futures.toArray(new CompletableFuture<?>[0]);
        CompletableFuture<List<String>> listFuture = CompletableFuture.allOf(futuresArray).thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        try {
            final List<String> results = listFuture.join();
            System.out.println("Printing first 10 results");
            for (int i = 0; i < 10; i++) {
                System.out.println("Finished " + results.get(i));
            }
        } finally {
            close();
        }
    }

    void close() {
        asyncOperationEmulation.shutdownNow();
    }

    public static void main(String[] args) {
        new Application().initialize()
        // Switch between .startNaive() and .start() to test both implementations
        //  .startNaive();
          .start();
    }

}
