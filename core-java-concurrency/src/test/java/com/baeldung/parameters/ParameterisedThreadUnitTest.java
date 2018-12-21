package com.baeldung.parameters;

import com.baeldung.concurrent.parameter.AverageCalculator;
import org.junit.Test;

import java.util.concurrent.*;

import static com.baeldung.concurrent.parameter.ParameterizedThreadExample.*;
import static org.junit.Assert.assertEquals;

public class ParameterisedThreadUnitTest {

    @Test
    public void whenSendingParameterToCallable_thenSuccessful() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Double> result = executorService.submit(new AverageCalculator(1, 2, 3));
        try {
            assertEquals(Double.valueOf(2.0), result.get());
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void whenParametersToThreadWithLamda_thenParametersPassedCorrectly() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        int[] numbers = new int[] { 4, 5, 6 };
        Future<Integer> sumResult = executorService.submit(sumCalculator(numbers));
        Future<Double> averageResult = executorService.submit(averageCalculator(numbers));

        try {
            assertEquals(Integer.valueOf(15), sumResult.get());
            assertEquals(Double.valueOf(5.0), averageResult.get());
        } finally {
            executorService.shutdown();
        }
    }

}
