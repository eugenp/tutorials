package com.baeldung.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Async {

    public static void main(String[] args) {
        System.out.println("hello world!!");

        try {
            Future<String> completableFuture = calculateAsync();

            String result = completableFuture.get();
            
            System.out.println(result);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        CompletableFuture.runAsync(() -> {
            System.out.println("This is Async");
        });
    }

    public static Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

}