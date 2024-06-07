package com.baeldung.joinvsget;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureUnitTest {

    @Test
    public void testJoin() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Test join");
        
        assertEquals("Test join", future.join());

        CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Test join exception");
        });

        assertThrows(CompletionException.class, exceptionFuture::join);
    }

    @Test
    public void testGet() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Test get");

        try {
            assertEquals("Test get", future.get());
        } catch (InterruptedException | ExecutionException e) {
            fail("Exception should not be thrown");
        }

        CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Test get exception");
        });

        assertThrows(ExecutionException.class, exceptionFuture::get);
    }
}
