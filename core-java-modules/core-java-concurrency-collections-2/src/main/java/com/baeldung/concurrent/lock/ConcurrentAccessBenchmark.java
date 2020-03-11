package com.baeldung.concurrent.lock;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Fork(value = 1)
@Warmup(iterations = 0)
public class ConcurrentAccessBenchmark {
    ConcurrentAccessExperiment accessMyMap;
    static final int SLOTS = 4;
    static final int THREADS = 10000;
    static final int BUCKETS = Runtime.getRuntime().availableProcessors() * SLOTS;

    @Param({"Single Lock", "Striped Lock"})
    private String lockType;

    @Param({"HashMap", "ConcurrentHashMap"})
    private String mapType;

    @Setup
    public void setup() {
        switch (lockType) {
            case "Single Lock":
                accessMyMap = new SingleLock();
                break;
            case "Striped Lock":
                accessMyMap = new StripedLock(BUCKETS);
                break;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void test() throws InterruptedException {
        accessMyMap.doWork(mapType, THREADS, SLOTS);
    }
}
