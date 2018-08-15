package com.baeldung.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
public class HashMapBenchMark {

    @State(Scope.Thread)
    public static class MyState {

        Map<Long, Employee> employeeMap = new HashMap<>();

        long iterations = 10000;

        Employee employee = new Employee(100L, "Harry");

        int employeeIndex = -1;

        @Setup(Level.Trial)
        public void setUp() {
            for (long i = 0; i < iterations; i++) {
                employeeMap.put(i, new Employee(i, "John"));
            }

            employeeMap.put(iterations, employee);
        }
    }

    @Benchmark
    public Employee testGet(HashMapBenchMark.MyState state) {
        return state.employeeMap.get(state.iterations);
    }

    @Benchmark
    public Employee testRemove(HashMapBenchMark.MyState state) {
        return state.employeeMap.remove(state.iterations);
    }


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(HashMapBenchMark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
