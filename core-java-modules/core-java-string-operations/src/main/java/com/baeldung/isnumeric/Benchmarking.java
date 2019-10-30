package com.baeldung.isnumeric;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Benchmarking {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(Benchmarking.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class ExecutionPlan {
        public String number = Integer.toString(Integer.MAX_VALUE);
        public boolean isNumber = false;
        public IsNumeric isNumeric = new IsNumeric();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingCoreJava(ExecutionPlan plan) {
        plan.isNumber = plan.isNumeric.usingCoreJava(plan.number);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingRegularExpressions(ExecutionPlan plan) {
        plan.isNumber = plan.isNumeric.usingRegularExpressions(plan.number);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingNumberUtils_isCreatable(ExecutionPlan plan) {
        plan.isNumber = plan.isNumeric.usingNumberUtils_isCreatable(plan.number);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingNumberUtils_isParsable(ExecutionPlan plan) {
        plan.isNumber = plan.isNumeric.usingNumberUtils_isParsable(plan.number);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringUtils_isNumeric(ExecutionPlan plan) {
        plan.isNumber = plan.isNumeric.usingStringUtils_isNumeric(plan.number);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringUtils_isNumericSpace(ExecutionPlan plan) {
        plan.isNumber = plan.isNumeric.usingStringUtils_isNumericSpace(plan.number);
    }
}