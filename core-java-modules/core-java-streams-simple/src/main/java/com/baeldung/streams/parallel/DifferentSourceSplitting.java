package com.baeldung.streams.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DifferentSourceSplitting {

    private static final List<Integer> arrayListOfNumbers = new ArrayList<>();
    private static final List<Integer> linkedListOfNumbers = new LinkedList<>();

    static {
        IntStream.rangeClosed(1, 1_000_000).forEach(i -> {
            arrayListOfNumbers.add(i);
            linkedListOfNumbers.add(i);
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void differentSourceArrayListSequential() {
        arrayListOfNumbers.stream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void differentSourceArrayListParallel() {
        arrayListOfNumbers.parallelStream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void differentSourceLinkedListSequential() {
        linkedListOfNumbers.stream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void differentSourceLinkedListParallel() {
        linkedListOfNumbers.parallelStream().reduce(0, Integer::sum);
    }

}
