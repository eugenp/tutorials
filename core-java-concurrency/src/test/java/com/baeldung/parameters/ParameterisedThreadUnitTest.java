package com.baeldung.parameters;

import com.baeldung.concurrent.parameter.AverageCalculator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.baeldung.concurrent.parameter.ParameterizedThreadExample.averageCalculator;
import static com.baeldung.concurrent.parameter.ParameterizedThreadExample.numberGenerator;
import static org.junit.Assert.assertEquals;

public class ParameterisedThreadUnitTest {

    @Test
    public void whenSendingParameterToCallable_thenSuccessful() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        Double expectedAverage = numbers.stream()
            .collect(Collectors.averagingInt(v -> v));

        Future<Double> result = executorService.submit(new AverageCalculator(numbers));
        try {
            Double average = result.get();
            assertEquals(expectedAverage, average);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    @Test
    public void whenParametersToThreadWithLambda_thenParametersPassedCorrectly() {
        BlockingQueue<Integer> topic = new ArrayBlockingQueue<>(5);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(numberGenerator(topic));
        Future<Double> average = executorService.submit(averageCalculator(topic));

        try {
            assertEquals(Double.valueOf(5.5), average.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
