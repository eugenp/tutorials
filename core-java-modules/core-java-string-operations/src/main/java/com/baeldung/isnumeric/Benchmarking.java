package com.baeldung.isnumeric;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
    private static final TestMode MODE = TestMode.DIVERS;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(Benchmarking.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }

    private static final IsNumeric subject = new IsNumeric();

    @State(Scope.Thread)
    public static class ExecutionPlan {
        private final Map<String, Boolean> testPlan = testPlan();

        void validate(Function<String, Boolean> functionUnderTest) {
            testPlan.forEach((key, value) -> {
                Boolean result = functionUnderTest.apply(key);

                assertEquals(value, result, key);
            });
        }

        private void assertEquals(Boolean expectedResult, Boolean result, String input) {
            if (result != expectedResult) {
                throw new IllegalStateException("The output does not match the expected output, for input: " + input);
            }
        }

        private Map<String, Boolean> testPlan() {
            HashMap<String, Boolean> plan = new HashMap<>();
            plan.put(Integer.toString(Integer.MAX_VALUE), true);

            if (MODE == TestMode.SIMPLE) {
                return plan;
            }

            plan.put("x0", false);
            plan.put("0..005", false);
            plan.put("--11", false);
            plan.put("test", false);
            plan.put(null, false);
            for (int i = 0; i < 94; i++) {
                plan.put(Integer.toString(i), true);
            }
            return plan;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingCoreJava(ExecutionPlan plan) {
        plan.validate(subject::usingCoreJava);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingRegularExpressions(ExecutionPlan plan) {
        plan.validate(subject::usingPreCompiledRegularExpressions);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingNumberUtils_isCreatable(ExecutionPlan plan) {
        plan.validate(subject::usingNumberUtils_isCreatable);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingNumberUtils_isParsable(ExecutionPlan plan) {
        plan.validate(subject::usingNumberUtils_isParsable);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringUtils_isNumeric(ExecutionPlan plan) {
        plan.validate(subject::usingStringUtils_isNumeric);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void usingStringUtils_isNumericSpace(ExecutionPlan plan) {
        plan.validate(subject::usingStringUtils_isNumericSpace);
    }

    private enum TestMode {
        SIMPLE, DIVERS
    }
}