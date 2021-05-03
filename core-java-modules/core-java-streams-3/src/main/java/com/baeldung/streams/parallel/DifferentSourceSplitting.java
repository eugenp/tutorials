package com.baeldung.streams.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class DifferentSourceSplitting {

    private static final List<Integer> arrayListOfNumbers = new ArrayList<>();
    private static final List<Integer> linkedListOfNumbers = new LinkedList<>();

    static {
        IntStream.rangeClosed(1, 100_000_000).forEach(i -> {
            arrayListOfNumbers.add(i);
            linkedListOfNumbers.add(i);
        });
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void arrayListSequential() {
        arrayListOfNumbers.stream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void arrayListParallel() {
        arrayListOfNumbers.parallelStream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void linkedListSequential() {
        linkedListOfNumbers.stream().reduce(0, Integer::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public static void linkedListParallel() {
        linkedListOfNumbers.parallelStream().reduce(0, Integer::sum);
    }

}
