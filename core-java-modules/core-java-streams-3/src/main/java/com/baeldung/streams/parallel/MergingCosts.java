package com.baeldung.streams.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MergingCosts {

    private static final List<Integer> arrayListOfNumbers = new ArrayList<>();

    static {
        IntStream.rangeClosed(1, 100_000).forEach(i -> {
            arrayListOfNumbers.add(i);
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void mergingCostsSumSequential() {
        arrayListOfNumbers.stream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void mergingCostsSumParallel() {
        arrayListOfNumbers.stream().parallel().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void mergingCostsGroupingSequential() {
        arrayListOfNumbers.stream().collect(Collectors.groupingBy(i -> i % 2 == 0));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void mergingCostsGroupingParallel() {
        arrayListOfNumbers.stream().parallel().collect(Collectors.groupingBy(i -> i % 2 == 0));
    }

}
