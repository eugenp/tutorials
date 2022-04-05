package com.baeldung.list.primitive;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import gnu.trove.list.array.TIntArrayList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 100000, iterations = 10)
@Warmup(batchSize = 100000, iterations = 10)
@State(Scope.Thread)
public class PrimitivesListPerformance {

    private List<Integer> arrayList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    private TIntArrayList tList = new TIntArrayList(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    private cern.colt.list.IntArrayList coltList = new cern.colt.list.IntArrayList(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    private IntArrayList fastUtilList = new IntArrayList(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

    private int getValue = 4;

    @Benchmark
    public boolean addArrayList() {
        return arrayList.add(getValue);
    }

    @Benchmark
    public boolean addTroveIntList() {
        return tList.add(getValue);
    }

    @Benchmark
    public void addColtIntList() {
        coltList.add(getValue);
    }

    @Benchmark
    public boolean addFastUtilIntList() {
        return fastUtilList.add(getValue);
    }

    @Benchmark
    public int getArrayList() {
        return arrayList.get(getValue);
    }

    @Benchmark
    public int getTroveIntList() {
        return tList.get(getValue);
    }

    @Benchmark
    public int getColtIntList() {
        return coltList.get(getValue);
    }

    @Benchmark
    public int getFastUtilIntList() {
        return fastUtilList.getInt(getValue);
    }

    @Benchmark
    public boolean containsArrayList() {
        return arrayList.contains(getValue);
    }

    @Benchmark
    public boolean containsTroveIntList() {
        return tList.contains(getValue);
    }

    @Benchmark
    public boolean containsColtIntList() {
        return coltList.contains(getValue);
    }

    @Benchmark
    public boolean containsFastUtilIntList() {
        return fastUtilList.contains(getValue);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(PrimitivesListPerformance.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
