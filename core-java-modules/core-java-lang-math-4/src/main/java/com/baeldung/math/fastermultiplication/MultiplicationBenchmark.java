package com.baeldung.math.fastermultiplication;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class MultiplicationBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(MultiplicationBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }

    private int smallValue = 255;
    private int largeValue = 2187657;

    @Benchmark
    public int smallValueWithParentheses() {
        return 2 * (smallValue * smallValue);
    }

    @Benchmark
    public int smallValueWithoutParentheses() {
        return 2 * smallValue * smallValue;
    }

    @Benchmark
    public int largeValueWithParentheses() {
        return 2 * (largeValue * largeValue);
    }

    @Benchmark
    public int largeValueWithoutParentheses() {
        return 2 * largeValue * largeValue;
    }
}