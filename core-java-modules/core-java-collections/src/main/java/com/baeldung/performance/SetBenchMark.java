package com.baeldung.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
public class SetBenchMark {

    @State(Scope.Thread)
    public static class MyState {

        //Set<Employee> employeeSet = new HashSet<>();
        LinkedHashSet<Employee> employeeSet = new LinkedHashSet<>();
        //ConcurrentSkipListSet<Employee> employeeSet = new ConcurrentSkipListSet <>();

        // TreeSet 

        long iterations = 1000;
        Employee employee = new Employee(100L, "Harry");

        @Setup(Level.Trial)
        public void setUp() {
            for (long i = 0; i < iterations; i++) {
                employeeSet.add(new Employee(i, "John"));
            }

            //employeeSet.add(employee);
        }
    }

    @Benchmark
    public boolean testAdd(SetBenchMark.MyState state) {
        return state.employeeSet.add(state.employee);
    }

    @Benchmark
    public Boolean testContains(SetBenchMark.MyState state) {
        return state.employeeSet.contains(state.employee);
    }

    @Benchmark
    public boolean testRemove(SetBenchMark.MyState state) {
        return state.employeeSet.remove(state.employee);
    }


    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(SetBenchMark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
