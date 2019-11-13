package com.baeldung.benchmark;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
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
import java.util.stream.Collectors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class IntegerListFilter {

    private List<Integer> jdkIntList;
    private MutableList<Integer> ecMutableList;
    private IntList ecIntList;
    private ExecutorService executor;

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
    public List<Integer> jdkList() {
        return jdkIntList.stream().filter(i -> i % 5 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<Integer> ecMutableList() {
        return ecMutableList.select(i -> i % 5 == 0);
    }

    @Benchmark
    public List<Integer> jdkListParallel() {
        return jdkIntList.parallelStream().filter(i -> i % 5 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<Integer> ecMutableListParallel() {
        return ecMutableList.asParallel(executor, 100_000).select(i -> i % 5 == 0).toList();
    }

    @Benchmark
    public IntList ecPrimitive() {
        return this.ecIntList.select(i -> i % 5 == 0);
    }

    @Benchmark
    public IntList ecPrimitiveParallel() {
        return this.ecIntList.primitiveParallelStream().filter(i -> i % 5 == 0).collect(IntLists.mutable::empty, MutableIntList::add, MutableIntList::addAll);
    }
}