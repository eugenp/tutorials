package com.baeldung.arrayandlistperformance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayAndArrayListPerformance {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.runner.Runner runner = new org.openjdk.jmh.runner.Runner(new OptionsBuilder().include(ArrayAndArrayListPerformance.class.getSimpleName()).forks(1).build());
        runner.run();
    }
    public static Integer[] array = Collections.nCopies(256, 1).toArray(new Integer[0]);
    public static ArrayList<Integer> list = new ArrayList<Integer>(
            Arrays.asList(array));
    @Benchmark
    public Integer[] arrayCreation() {
        return new Integer[256];
    }

    @Benchmark
    public ArrayList<Integer> arrayListCreation() {
        return new ArrayList<>(256);
    }

    @Benchmark
    public Integer[] arrayItemsSetting() {
        for (int i = 0; i < 256; i++) {
            array[i] = i;
        }
        return array;
    }

    @Benchmark
    public ArrayList<Integer> arrayListItemsSetting() {
        for (int i = 0; i < 256; i++) {
            list.set(i,i);
        }
        return list;
    }

    @Benchmark
    public void arrayItemsRetrieval(Blackhole blackhole) {
        for (int i = 0; i < 256; i++) {
            int item = array[i];
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void arrayListItemsRetrieval(Blackhole blackhole) {
        for (int i = 0; i < 256; i++) {
            int item = list.get(i);
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void arrayCloning(Blackhole blackhole) {
        Integer[] newArray = array.clone();
        blackhole.consume(newArray);
    }

    @Benchmark
    public void arrayListCloning(Blackhole blackhole) {
        ArrayList<Integer> newList = new ArrayList<>(list);
        blackhole.consume(newList);
    }
}