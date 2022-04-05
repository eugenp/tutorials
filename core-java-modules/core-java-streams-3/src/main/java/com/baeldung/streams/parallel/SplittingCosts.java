package com.baeldung.streams.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SplittingCosts {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void sourceSplittingIntStreamSequential() {
        IntStream.rangeClosed(1, 100).reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void sourceSplittingIntStreamParallel() {
        IntStream.rangeClosed(1, 100).parallel().reduce(0, Integer::sum);
    }

}
