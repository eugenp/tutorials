package com.baeldung.streams.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import java.util.stream.IntStream;

public class SplittingCosts {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void arrayListSequential() {
        IntStream.rangeClosed(1, 1_000).reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void arrayListParallel() {
        IntStream.rangeClosed(1, 1_000).parallel().reduce(0, Integer::sum);
    }

}
