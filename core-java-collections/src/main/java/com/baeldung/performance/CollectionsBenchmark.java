package com.baeldung.performance;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CollectionsBenchmark {

    @State(Scope.Thread)
    public static class MyState {
        private Set<Employee> employeeSet = new HashSet<>();
        private List<Employee> employeeList = new ArrayList<>();

        private final long m_count = 16;

        private Employee employee = new Employee(100L, "Harry");

        @Setup(Level.Invocation)
        public void setUp() {

            for (long ii = 0; ii < m_count; ii++) {
                employeeSet.add(new Employee(ii, "John"));
                employeeList.add(new Employee(ii, "John"));

                employeeList.add(employee);
                employeeSet.add(employee);
            }
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MINUTES)
    public boolean testArrayList(MyState state) {
        return state.employeeList.contains(state.employee);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MINUTES)
    public boolean testHashSet(MyState state) {
        return state.employeeSet.contains(state.employee);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
