package com.baeldung.concurrent.completablefuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockingUnitTest {

    @Test
    void givenCompletableFuture_whenGet_thenReturnResult()
      throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture
          .supplyAsync(() -> "Baeldung")
          .thenApply(String::toUpperCase);

        assertEquals("BAELDUNG", completableFuture.get());
    }

    @Test
    void givenCompletableFuture_whenJoin_thenReturnResult() {
        CompletableFuture<String> completableFuture = CompletableFuture
          .supplyAsync(() -> "Blocking")
          .thenApply(s -> s + " Operation")
          .thenApply(String::toLowerCase);

        assertEquals("blocking operation", completableFuture.join());
    }
}
