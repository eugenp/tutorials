package com.baeldung.streams.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MemoryLocalityCosts {

    private static final int[] intArray = new int[100_000];
    private static final Integer[] integerArray = new Integer[100_000];

    static {
        IntStream.rangeClosed(1, 100_000).forEach(i -> {
            intArray[i-1] = i;
            integerArray[i-1] = i;
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void localityIntArraySequential() {
        Arrays.stream(intArray).reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void localityIntArrayParallel() {
        Arrays.stream(intArray).parallel().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void localityIntegerArraySequential() {
        Arrays.stream(integerArray).reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void localityIntegerArrayParallel() {
        Arrays.stream(integerArray).parallel().reduce(0, Integer::sum);
    }

}
