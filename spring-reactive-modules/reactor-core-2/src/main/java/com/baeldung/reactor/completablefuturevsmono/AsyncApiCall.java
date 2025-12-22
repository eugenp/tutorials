package com.baeldung.reactor.completablefuturevsmono;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class AsyncApiCall {

    public static void main(String[] args) {
        AsyncApiCall apiCall = new AsyncApiCall();
        CompletableFuture<String> completableFuture = apiCall.nonBlockingApiCall("test parameter");

        completableFuture.thenAccept(result -> System.out.println("Future completed successfully: " + result))
            .exceptionally(error -> {
                System.out.println("Future completed exceptionally: " + error.getMessage());
                return null;
            });
    }

    public void myAsyncCall(String param, BiConsumer<String, Throwable> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                callback.accept("Response from API with param: " + param, null);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public CompletableFuture<String> nonBlockingApiCall(String param) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        myAsyncCall(param, (result, error) -> {
            if (error != null) {
                completableFuture.completeExceptionally(error);
            } else {
                completableFuture.complete(result);
            }
        });
        return completableFuture;
    }
}