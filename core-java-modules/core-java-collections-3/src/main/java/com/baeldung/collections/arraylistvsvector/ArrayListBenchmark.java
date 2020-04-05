package com.baeldung.collections.arraylistvsvector;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
public class ArrayListBenchmark {

    @State(Scope.Thread)
    public static class MyState {

        List<Employee> employeeList = new ArrayList<>();
        Vector<Employee> employeeVector = new Vector<>();
        //LinkedList<Employee> employeeList = new LinkedList<>();

        long iterations = 100000;

        Employee employee = new Employee(100L, "Harry");

        int employeeIndex = -1;

        @Setup(Level.Trial)
        public void setUp() {
            for (long i = 0; i < iterations; i++) {
                employeeList.add(new Employee(i, "John"));
                employeeVector.add(new Employee(i, "John"));
            }

            employeeList.add(employee);
            employeeVector.add(employee);
            employeeIndex = employeeList.indexOf(employee);
        }
    }

    @Benchmark
    public void testAddAt(ArrayListBenchmark.MyState state) {
        state.employeeList.add((int) (state.iterations), new Employee(state.iterations, "John"));
    }

    @Benchmark
    public boolean testContains(ArrayListBenchmark.MyState state) {
        return state.employeeList.contains(state.employee);
    }

    @Benchmark
    public boolean testContainsVector(ArrayListBenchmark.MyState state) {
        return state.employeeVector.contains(state.employee);
    }

    @Benchmark
    public int testIndexOf(ArrayListBenchmark.MyState state) {
        return state.employeeList.indexOf(state.employee);
    }

    @Benchmark
    public Employee testGet(ArrayListBenchmark.MyState state) {
        return state.employeeList.get(state.employeeIndex);
    }

    @Benchmark
    public Employee testVectorGet(ArrayListBenchmark.MyState state) {
        return state.employeeVector.get(state.employeeIndex);
    }

    @Benchmark
    public boolean testRemove(ArrayListBenchmark.MyState state) {
        return state.employeeList.remove(state.employee);
    }

    @Benchmark
    public void testAdd(ArrayListBenchmark.MyState state) {
        state.employeeList.add(new Employee(state.iterations + 1, "John"));
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(ArrayListBenchmark.class.getSimpleName()).threads(3)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
