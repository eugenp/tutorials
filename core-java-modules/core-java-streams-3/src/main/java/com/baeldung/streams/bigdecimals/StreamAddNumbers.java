package com.baeldung.streams.bigdecimals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StreamAddNumbers {

    public int addInts() {
        IntStream intNumbers = new Random().ints(5, 1, 100);
        return intNumbers.sum();
    }

    public double addDoubleOnList() {
        List<Double> doubleNumbers = Arrays.asList(23.48, 52.26, 13.5);
        return doubleNumbers.stream()
            .mapToDouble(Double::doubleValue)
            .sum();
    }

    public int addNumbersWithReduce() {
        List<Integer> intNumbers = Arrays.asList(5, 1, 100);
        return intNumbers.stream()
            .reduce(0, Integer::sum);
    }

    public BigDecimal addBigDecimals() {
        List<BigDecimal> bigDecimalNumber = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN);
        return bigDecimalNumber.stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
