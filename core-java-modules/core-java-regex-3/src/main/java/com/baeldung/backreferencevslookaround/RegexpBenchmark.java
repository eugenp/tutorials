package com.baeldung.backreferencevslookaround;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
public class RegexpBenchmark {

    private static final int ITERATIONS_COUNT = 1000;

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        String testString = "*example*text**with*many*asterisks**".repeat(ITERATIONS_COUNT);
    }

    @Benchmark
    public void backReference(BenchmarkState state) {
        state.testString.replaceAll("(^\\*)|(\\*$)|\\*", "$1$2");
    }

    @Benchmark
    public void lookaround(BenchmarkState state) {
        state.testString.replaceAll("(?<!^)\\*+(?!$)", "");
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(RegexpBenchmark.class.getSimpleName())
            .build();

        new Runner(opt).run();
    }
}