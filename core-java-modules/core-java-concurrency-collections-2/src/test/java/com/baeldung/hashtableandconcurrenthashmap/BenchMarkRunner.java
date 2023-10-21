package com.baeldung.hashtableandconcurrenthashmap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Threads(10) // 10 threads for the test
public class BenchMarkRunner {
    private Hashtable<String, Integer> hashTable;
    private ConcurrentHashMap<String, Integer> concurrentHashMap;

    @Setup(Level.Trial)
    public void setup() {
        hashTable = new Hashtable<>();
        concurrentHashMap = new ConcurrentHashMap<>();
    }

    @Benchmark
    @Group("hashtable")
    public void benchmarkHashtablePut() {
        for (int i = 0; i < 10000; i++) {
            hashTable.put(String.valueOf(i), i);
        }
    }

    @Benchmark
    @Group("hashtable")
    public void benchmarkHashtableGet(Blackhole blackhole) {
        for (int i = 0; i < 10000; i++) {
            Integer value = hashTable.get(String.valueOf(i));
            blackhole.consume(value);
        }
    }

    @Benchmark
    @Group("concurrentHashMap")
    public void benchmarkConcurrentHashMapPut() {
        for (int i = 0; i < 10000; i++) {
            concurrentHashMap.put(String.valueOf(i), i);
        }
    }

    @Benchmark
    @Group("concurrentHashMap")
    public void benchmarkConcurrentHashMapGet(Blackhole blackhole) {
        for (int i = 0; i < 10000; i++) {
            Integer value = concurrentHashMap.get(String.valueOf(i));
            blackhole.consume(value);
        }
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
          .include(BenchMarkRunner.class.getSimpleName())
          .shouldFailOnError(true)
          .shouldDoGC(true)
          .jvmArgs("-server")
          .build();
        new Runner(options).run();
    }
}