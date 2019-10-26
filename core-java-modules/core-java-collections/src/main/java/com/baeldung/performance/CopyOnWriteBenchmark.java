package com.baeldung.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
public class CopyOnWriteBenchmark {

    @State(Scope.Thread)
    public static class MyState {

        CopyOnWriteArrayList<Employee> employeeList = new CopyOnWriteArrayList<>();

        long iterations = 100000;

        Employee employee = new Employee(100L, "Harry");

        int employeeIndex = -1;

        @Setup(Level.Trial)
        public void setUp() {
            for (long i = 0; i < iterations; i++) {
                employeeList.add(new Employee(i, "John"));
            }

            employeeList.add(employee);

            employeeIndex = employeeList.indexOf(employee);
        }
    }

        @Benchmark
    public void testAdd(CopyOnWriteBenchmark.MyState state) {
            state.employeeList.add(new Employee(state.iterations + 1, "John"));
    }

    @Benchmark
    public void testAddAt(CopyOnWriteBenchmark.MyState state) {
        state.employeeList.add((int) (state.iterations), new Employee(state.iterations, "John"));
    }

    @Benchmark
    public boolean testContains(CopyOnWriteBenchmark.MyState state) {
        return state.employeeList.contains(state.employee);
    }

    @Benchmark
    public int testIndexOf(CopyOnWriteBenchmark.MyState state) {
        return state.employeeList.indexOf(state.employee);
    }

    @Benchmark
    public Employee testGet(CopyOnWriteBenchmark.MyState state) {
        return state.employeeList.get(state.employeeIndex);
    }

    @Benchmark
    public boolean testRemove(CopyOnWriteBenchmark.MyState state) {
        return state.employeeList.remove(state.employee);
    }


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(CopyOnWriteBenchmark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
