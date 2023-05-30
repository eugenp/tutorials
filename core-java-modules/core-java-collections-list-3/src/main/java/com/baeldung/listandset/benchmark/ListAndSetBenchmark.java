package com.baeldung.listandset.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.MILLISECONDS)
public class ListAndSetBenchmark {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);

    }

    @Benchmark
    public void addElementToArrayList(Params param, Blackhole blackhole) {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < param.addNumber; i++) {
            blackhole.consume(arrayList.add(i));
        }
    }

    @Benchmark
    public void addElementToHashSet(Params param, Blackhole blackhole) {
        Set<Integer> hashSet = new HashSet<>();

        for (int i = 0; i < param.addNumber; i++) {
            blackhole.consume(hashSet.add(i));
        }
    }

    @Benchmark
    public void searchElementInArrayList(Params param, Blackhole blackhole) {
        List<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < param.containNumber; i++) {
            arrayList.add(i);
        }
        for (int i = 0; i < param.containNumber; i++) {
            blackhole.consume(arrayList.contains(i));
        }
    }

    @Benchmark
    public void searchElementInHashSet(Params param, Blackhole blackhole) {
        Set<Integer> hashSet = new HashSet<>();

        for (int i = 0; i < param.containNumber; i++) {
            hashSet.add(i);
        }
        for (int i = 0; i < param.containNumber; i++) {
            blackhole.consume(hashSet.contains(i));
        }
    }

    @State(Scope.Benchmark)
    public static class Params {
        public int addNumber = 10000000;
        public int containNumber = 10000;
    }

}
