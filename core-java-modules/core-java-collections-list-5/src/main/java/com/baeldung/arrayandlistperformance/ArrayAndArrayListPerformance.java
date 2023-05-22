package com.baeldung.arrayandlistperformance;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayAndArrayListPerformance {

    @Benchmark
    public void arrayCreation() {
        int[] array = new int[1000000];
    }

    @Benchmark
    public void arrayListCreation() {
        ArrayList<Integer> list = new ArrayList<>(1000000);
    }

    @Benchmark
    public void arrayItemSetting() {
        int[] array = new int[1000000];
        array[0] = 10;
    }

    @Benchmark
    public void arrayListItemSetting() {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        list.add(0, 10);
    }

    @Benchmark
    public void arrayItemRetrieval() {
        int[] array = new int[1000000];
        array[0] = 10;
        int item = array[0];
    }

    @Benchmark
    public void arrayListItemRetrieval() {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        list.add(0, 10);
        int item2 = list.get(0);
    }

    @Benchmark
    public void arrayCloning() {
        int[] array = new int[1000000];
        int[] newArray = array.clone();
    }

    @Benchmark
    public void arrayListCloning() {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        ArrayList<Integer> newList = new ArrayList<>(list);
    }
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.runner.Runner runner = new org.openjdk.jmh.runner.Runner(new OptionsBuilder()
                .include(ArrayAndArrayListPerformance.class.getSimpleName())
                .forks(1)
                .build());
        runner.run();
    }
    }