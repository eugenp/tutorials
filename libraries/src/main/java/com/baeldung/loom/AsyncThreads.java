package com.baeldung.loom;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncThreads {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {

            System.out.println("Hello " + Thread.currentThread().getName());
        });

        cf.thenRun(() -> {

            System.out.println("World " + Thread.currentThread().getName());
        });

        cf.get();
    }
}
