package com.baeldung.map.concurrenthashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@Fork(5)
@Threads(10)
@Warmup(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MapPerformanceComparison {
    
    private int TEST_NO_ITEMS;
    
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
    
    @Setup
    public void setup() {
        TEST_NO_ITEMS = 1000;
    }
    
    @Benchmark
    public void randomReadAndWriteSynchronizedMap() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        performReadAndWriteTest(map);
    }
    
    @Benchmark
    public void randomReadAndWriteConcurrentHashMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        performReadAndWriteTest(map);
    }
    
    private void performReadAndWriteTest(final Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.get(String.valueOf(randNumber));
            map.put(String.valueOf(randNumber), randNumber);
        }
    }
    
    @Benchmark
    public void randomWriteSynchronizedMap() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        performWriteTest(map);
    }
    
    @Benchmark
    public void randomWriteConcurrentHashMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        performWriteTest(map);
    }
    
    private void performWriteTest(final Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.put(String.valueOf(randNumber), randNumber);
        }
    }
    
    @Benchmark
    public void randomReadSynchronizedMap() {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
        performReadTest(map);
    }
    
    @Benchmark
    public void randomReadConcurrentHashMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        performReadTest(map);
    }
    
    private void performReadTest(final Map<String, Integer> map) {
        for (int i = 0; i < TEST_NO_ITEMS; i++) {
            Integer randNumber = (int) Math.ceil(Math.random() * TEST_NO_ITEMS);
            map.get(String.valueOf(randNumber));
        }
    }
}
