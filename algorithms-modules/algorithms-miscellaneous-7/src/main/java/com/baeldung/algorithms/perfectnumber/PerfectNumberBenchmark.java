package com.baeldung.algorithms.perfectnumber;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class PerfectNumberBenchmark {

    @Benchmark
    public boolean bruteForceBenchmark() {
        return PerfectNumber.isPerfectBruteForce(33550336);
    }

    @Benchmark
    public boolean streamBenchmark() {
        return PerfectNumber.isPerfectStream(33550336);
    }

    @Benchmark
    public boolean euclidEulerBenchmark() {
        return PerfectNumber.isPerfectEuclidEuler(33550336);
    }

    @Benchmark
    public boolean euclidEulerUsingShiftBenchmark() {
        return PerfectNumber.isPerfectEuclidEulerUsingShift(33550336);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(PerfectNumberBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}