package com.baeldung.async;

import static com.ea.async.Async.await;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.ea.async.Async;

public class EAAsyncExample {

    static {
        Async.init();
    }

    public static void main(String[] args) throws Exception {
        usingCompletableFuture();
        usingAsyncAwait();
    }

    public static void usingCompletableFuture() throws InterruptedException, ExecutionException, Exception {
        CompletableFuture<Void> completableFuture = hello()
            .thenComposeAsync(hello -> mergeWorld(hello))
            .thenAcceptAsync(helloWorld -> print(helloWorld))
            .exceptionally( throwable -> {
                System.out.println(throwable.getCause()); 
                return null;
            });
        completableFuture.get();
    }

    public static CompletableFuture<String> hello() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        return completableFuture;
    }

    public static CompletableFuture<String> mergeWorld(String s) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return s + " World!";
        });
        return completableFuture;
    }

    public static void print(String str) {
        CompletableFuture.runAsync(() -> System.out.println(str));
    }

    private static void usingAsyncAwait() {
        try {
            String hello = await(hello());
            String helloWorld = await(mergeWorld(hello));
            await(CompletableFuture.runAsync(() -> print(helloWorld)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
