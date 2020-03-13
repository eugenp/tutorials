package com.baeldung.concurrent.lock;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Fork(value = 2)
@Warmup(iterations = 0)
public class ConcurrentAccessBenchmark {
    ConcurrentAccessExperiment accessMyMap;
    static final int SLOTS = 4;
    static final int THREADS = 10000;
    static final int BUCKETS = Runtime.getRuntime().availableProcessors() * SLOTS;

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Map<String,String> singleLockHashMap() throws InterruptedException {
        return (new SingleLock()).doWork(new HashMap<String,String>(), THREADS, SLOTS);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Map<String,String> stripedLockHashMap() throws InterruptedException {
        return (new StripedLock(BUCKETS)).doWork(new HashMap<String,String>(), THREADS, SLOTS);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Map<String,String> singleLockConcurrentHashMap() throws InterruptedException {
        return (new SingleLock()).doWork(new ConcurrentHashMap<String,String>(), THREADS, SLOTS);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Map<String,String> stripedLockConcurrentHashMap() throws InterruptedException {
        return (new StripedLock(BUCKETS)).doWork(new ConcurrentHashMap<String,String>(), THREADS, SLOTS);
    }
}
