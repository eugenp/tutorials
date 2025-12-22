package com.baeldung.reactor.completablefuturevsmono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

class CompletableFutureUnitTest {

    @Test
    void whenGetMethodOnCompletableFutureItBlocksMainThread() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                return "Delayed result";
            } catch (InterruptedException e) {
                Thread.currentThread()
                    .interrupt();
                return null;
            }
        });

        assertThrows(TimeoutException.class, () -> future.get(2, TimeUnit.SECONDS));
    }
}