package com.baeldung.concurrent.parameter;

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class AverageCalculator implements Callable<Double> {

    int[] numbers;

    public AverageCalculator(int... parameter) {
        this.numbers = parameter == null ? new int[0] : parameter;
    }

    @Override
    public Double call() throws Exception {
        return IntStream.of(this.numbers)
            .average()
            .orElse(0d);
    }
}
