package com.baeldung.joinvsget;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public static void main(String[] args) {
        exampleJoin();
        exampleGet();
    }

    public static void exampleJoin() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello from join!");

        try {
            String result = future.join();
            System.out.println("Result from join(): " + result);
        } catch (CompletionException e) {
            System.out.println("Exception from join(): " + e.getCause());
        }
    }

    public static void exampleGet() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("Error in computation");
            }
            return "Hello from get!";
        });

        try {
            String result = future.get();
            System.out.println("Result from get(): " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception from get(): " + e.getCause());
        }
    }
}
