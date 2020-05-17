package com.baeldung.exchanger;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

import static java.util.concurrent.CompletableFuture.runAsync;



public class ExchangerPipeLineManualTest {

    private static final int BUFFER_SIZE = 100;

    @Test
    public void givenData_whenPassedThrough_thenCorrect() throws InterruptedException, ExecutionException {

        Exchanger<Queue<String>> readerExchanger = new Exchanger<>();
        Exchanger<Queue<String>> writerExchanger = new Exchanger<>();
        int counter = 0;

        Runnable reader = () -> {
            Queue<String> readerBuffer = new ConcurrentLinkedQueue<>();
            while (true) {
                readerBuffer.add(UUID.randomUUID().toString());
                if (readerBuffer.size() >= BUFFER_SIZE) {
                    try {
                        readerBuffer = readerExchanger.exchange(readerBuffer);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Runnable processor = () -> {
            Queue<String> processorBuffer = new ConcurrentLinkedQueue<>();
            Queue<String> writterBuffer = new ConcurrentLinkedQueue<>();
            try {
                processorBuffer = readerExchanger.exchange(processorBuffer);
                while (true) {
                    writterBuffer.add(processorBuffer.poll());
                    if (processorBuffer.isEmpty()) {
                        try {
                            processorBuffer = readerExchanger.exchange(processorBuffer);
                            writterBuffer = writerExchanger.exchange(writterBuffer);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        Runnable writer = () -> {
            Queue<String> writterBuffer = new ConcurrentLinkedQueue<>();
            try {
                writterBuffer = writerExchanger.exchange(writterBuffer);
                while (true) {
                    System.out.println(writterBuffer.poll());
                    if (writterBuffer.isEmpty()) {
                        writterBuffer = writerExchanger.exchange(writterBuffer);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        CompletableFuture.allOf(runAsync(reader), runAsync(processor), runAsync(writer)).get();
    }

}
