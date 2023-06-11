package com.baeldung.concurrent.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class NonBlockingExample {

    private static final Logger logger = LoggerFactory.getLogger(NonBlockingExample.class);

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> "Baeldung")
          .thenApply(String::length)
          .thenAccept(s -> logger.info(String.valueOf(s)));
    }
}
