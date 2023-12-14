package com.baeldung.callback.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureCallbackExample {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Runnable runnable = downloadFile(completableFuture);
        completableFuture.whenComplete((res, error) -> {
            if (error != null) {
                // handle the exception scenario
            } else if (res != null) {
                // send data to DB
            }
        });
        new Thread(runnable).start();
    }

    private static Runnable downloadFile(CompletableFuture<String> completableFuture) {
        return () -> {
            try {
                //logic to download file
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete("pic.jpg");
        };
    }
}
