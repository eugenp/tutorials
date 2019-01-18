package com.baeldung.array;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import gnu.trove.list.array.TIntArrayList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
public class PrimitivesListPerformance {

    @State(Scope.Thread)
    public static class Initialize {

        List<Integer> arrayList = new ArrayList<>();
        TIntArrayList tList = new TIntArrayList();
        cern.colt.list.IntArrayList coltList = new cern.colt.list.IntArrayList();
        IntArrayList fastUtilList = new IntArrayList();

        int getValue = 10;

        final int iterations = 100000;

        @Setup(Level.Trial)
        public void setUp() {

            for (int i = 0; i < iterations; i++) {
                arrayList.add(i);
                tList.add(i);
                coltList.add(i);
                fastUtilList.add(i);
            }

            arrayList.add(getValue);
            tList.add(getValue);
            coltList.add(getValue);
            fastUtilList.add(getValue);
        }
    }

    @Benchmark
    public boolean containsArrayList(PrimitivesListPerformance.Initialize state) {
        return state.arrayList.contains(state.getValue);
    }

    @Benchmark
    public boolean containsTIntList(PrimitivesListPerformance.Initialize state) {
        return state.tList.contains(state.getValue);
    }

    @Benchmark
    public boolean containsColtIntList(PrimitivesListPerformance.Initialize state) {
        return state.coltList.contains(state.getValue);
    }

    @Benchmark
    public boolean containsFastUtilIntList(PrimitivesListPerformance.Initialize state) {
        return state.fastUtilList.contains(state.getValue);
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
