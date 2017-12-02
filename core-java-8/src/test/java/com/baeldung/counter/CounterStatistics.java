package com.baeldung.counter;

import java.util.HashMap;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import com.baeldung.counter.CounterUtil.MutableInteger;

public class CounterStatistics {

    private static final Map<String, Integer> counterMap = new HashMap<>();
    private static final Map<String, MutableInteger> counterWithMutableIntMap = new HashMap<>();
    private static final Map<String, int[]> counterWithIntArrayMap = new HashMap<>();
    
    @Benchmark
    @Fork(value = 1, warmups = 3)
    @BenchmarkMode(Mode.All)
    public void mapWithWrapper() {
        CounterUtil.mapWithWrapper(counterMap);
    }
    
    @Benchmark
    @Fork(value = 1, warmups = 3)
    @BenchmarkMode(Mode.All)
    public void mapWithLambda() {
        CounterUtil.mapWithLambda();
    }
    
    @Benchmark
    @Fork(value = 1, warmups = 3)
    @BenchmarkMode(Mode.All)
    public void mapWithMutableInteger() {
        CounterUtil.mapWithMutableInteger(counterWithMutableIntMap);
    }
    
    @Benchmark
    @Fork(value = 1, warmups = 3)
    @BenchmarkMode(Mode.All)
    public void mapWithPrimitiveArray() {
        CounterUtil.mapWithPrimitiveArray(counterWithIntArrayMap);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
