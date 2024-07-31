package com.baeldung.exchanger;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

import java.util.concurrent.ExecutionException;
import org.junit.Test;

import static java.util.concurrent.CompletableFuture.runAsync;

public class ExchangerUnitTest {
    
    
    @Test
    public void givenThreads_whenMessageExchanged_thenCorrect() {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable taskA = () -> {
            try {
                String message = exchanger.exchange("from A");
                assertEquals("from B", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        Runnable taskB = () -> {
            try {
                String message = exchanger.exchange("from B");
                assertEquals("from A", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        CompletableFuture.allOf(runAsync(taskA), runAsync(taskB)).join();
    }

    @Test
    public void givenThread_WhenExchangedMessage_thenCorrect() throws InterruptedException, ExecutionException {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable runner = () -> {
            try {
                String message = exchanger.exchange("from runner");
                assertEquals("to runner", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<Void> result = CompletableFuture.runAsync(runner);
        String msg = exchanger.exchange("to runner");
        assertEquals("from runner", msg);
        result.join();
    }

}
