package com.baeldung.arrayandlistperformance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayAndArrayListPerformance {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.runner.Runner runner = new org.openjdk.jmh.runner.Runner(new OptionsBuilder().include(ArrayAndArrayListPerformance.class.getSimpleName()).forks(1).build());
        runner.run();
    }

    @Benchmark
    public void arrayCreation(Blackhole blackhole) {
        int[] array = new int[1000000];
        blackhole.consume(array);
    }

    @Benchmark
    public void arrayListCreation(Blackhole blackhole) {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        blackhole.consume(list);
    }

    @Benchmark
    public void arrayItemsSetting(Blackhole blackhole) {
        int[] array = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }
        blackhole.consume(array);

    }

    @Benchmark
    public void arrayListItemsSetting(Blackhole blackhole) {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        blackhole.consume(list);
    }

    @Benchmark
    public void arrayItemsRetrieval(Blackhole blackhole) {
        int[] array = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }
        for (int i = 0; i < 1000000; i++) {
            int item = array[i];
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void arrayListItemsRetrieval(Blackhole blackhole) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        for (int i = 0; i < 1000000; i++) {
            int item = list.get(i);
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void arrayCloning(Blackhole blackhole) {
        int[] array = new int[1000000];
        int[] newArray = array.clone();
        blackhole.consume(newArray);
    }

    @Benchmark
    public void arrayListCloning(Blackhole blackhole) {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        ArrayList<Integer> newList = new ArrayList<>(list);
        blackhole.consume(newList);
    }
}