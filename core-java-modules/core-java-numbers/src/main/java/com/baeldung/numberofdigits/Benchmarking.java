package com.baeldung.numberofdigits;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

public class Benchmarking {
    public static void main(String[] args) throws RunnerException, IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class ExecutionPlan {
        public int number = Integer.MAX_VALUE;
        public int length = 0;
        public NumberOfDigits numberOfDigits= new NumberOfDigits();
    }
    
    @Benchmark 
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void stringBasedSolution(ExecutionPlan plan) {
        plan.length = plan.numberOfDigits.stringBasedSolution(plan.number);
    }
    
    @Benchmark 
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void logarithmicApproach(ExecutionPlan plan) {
        plan.length = plan.numberOfDigits.logarithmicApproach(plan.number);
    }
    
    @Benchmark 
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void repeatedMultiplication(ExecutionPlan plan) {
        plan.length = plan.numberOfDigits.repeatedMultiplication(plan.number);
    }
    
    @Benchmark 
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void shiftOperators(ExecutionPlan plan) {
        plan.length = plan.numberOfDigits.shiftOperators(plan.number);
    }
    
    @Benchmark 
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void dividingWithPowersOf2(ExecutionPlan plan) {
        plan.length = plan.numberOfDigits.dividingWithPowersOf2(plan.number);
    }
    
    @Benchmark 
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void divideAndConquer(ExecutionPlan plan) {
        plan.length = plan.numberOfDigits.divideAndConquer(plan.number);
    }
}
