package com.baeldung.parallel;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

class ProcessorUnitTest {
    private final Processor processor = new Processor();

    @Test
    public void givenList_whenProcessedSequentially_thenCorrect() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        processor.processSerially();
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken sequentially: " + (endTime - startTime));
    }

    @Test
    public void givenList_whenProcessedParallelyWithExecutor_thenCorrect() throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        processor.processParallelyWithExecutor();
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken in parallel with Executor: " + (endTime - startTime));
    }

    @Test
    public void givenList_whenProcessedParallelyWithStream_thenCorrect() throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        processor.processParallelyWithStream();
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken in parallel with Stream: " + (endTime - startTime));
    }

    @Test
    public void givenList_whenProcessedParallel_thenCorrect() throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        processor.processParallelyWithStreamSupport();
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken in parallel with Stream support: " + (endTime - startTime));
    }
}