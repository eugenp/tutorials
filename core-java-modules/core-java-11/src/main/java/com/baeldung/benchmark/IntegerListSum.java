package com.baeldung.benchmark;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class IntegerListSum {

    private List<Integer> jdkIntList;
    private MutableList<Integer> ecMutableList;
    private ExecutorService executor;
    private IntList ecIntList;

    @Setup
    public void setup() {
        PrimitiveIterator.OfInt iterator = new Random(1L).ints(-10000, 10000).iterator();
        ecMutableList = FastList.newWithNValues(1_000_000, iterator::nextInt);
        jdkIntList = new ArrayList<>(1_000_000);
        jdkIntList.addAll(ecMutableList);
        ecIntList = ecMutableList.collectInt(i -> i, new IntArrayList(1_000_000));
        executor = Executors.newWorkStealingPool();
    }

    @Benchmark
    public long jdkList() {
        return jdkIntList.stream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long ecMutableList() {
        return ecMutableList.sumOfInt(i -> i);
    }

    @Benchmark
    public long jdkListParallel() {
        return jdkIntList.parallelStream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long ecMutableListParallel() {
        return ecMutableList.asParallel(executor, 100_000).sumOfInt(i -> i);
    }

    @Benchmark
    public long ecPrimitive() {
        return this.ecIntList.sum();
    }

    @Benchmark
    public long ecPrimitiveParallel() {
        return this.ecIntList.primitiveParallelStream().sum();
    }
}