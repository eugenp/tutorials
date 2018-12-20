package com.baeldung.concurrent.parameter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class AverageCalculator implements Callable<Double> {

    List<Integer> numbers;

    public AverageCalculator(List<Integer> parameter) {
        this.numbers = parameter;
    }

    @Override
    public Double call() throws Exception {
        if (this.numbers != null && !this.numbers.isEmpty()) {
            return this.numbers.stream()
                .collect(Collectors.averagingInt(v -> v));
        }
        return null;
    }
}
