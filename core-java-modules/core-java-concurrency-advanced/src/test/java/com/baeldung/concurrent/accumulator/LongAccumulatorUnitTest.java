package com.baeldung.concurrent.accumulator;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class LongAccumulatorUnitTest {

    @Test
    public void givenLongAccumulator_whenApplyActionOnItFromMultipleThrads_thenShouldProduceProperResult() throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        LongBinaryOperator sum = Long::sum;
        LongAccumulator accumulator = new LongAccumulator(sum, 0L);
        int numberOfThreads = 4;
        int numberOfIncrements = 100;

        // when
        Runnable accumulateAction = () -> IntStream.rangeClosed(0, numberOfIncrements).forEach(accumulator::accumulate);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(accumulateAction);
        }

        // then
        executorService.awaitTermination(500, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        assertEquals(accumulator.get(), 20200);

    }
}
