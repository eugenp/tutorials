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
    @State(Scope.Thread)
    public static class MyState {
        public int[] arrayCreation() {
            int[] array = new int[1000000];
            return array;
        }
        public ArrayList<Integer> arrayListCreation() {
            ArrayList<Integer> list = new ArrayList<>(1000000);
            return list;
        }
        public int[] arrayItemsSetting() {
            int[] array = arrayCreation();
            for (int i = 0; i < 1000000; i++) {
                array[i] = i;
            }
            return array;
        }

        public ArrayList<Integer> arrayListItemsSetting() {
            ArrayList<Integer> list = arrayListCreation();
            for (int i = 0; i < 1000000; i++) {
                list.add(i);
            }
            return list;
        }
    }
    int[] array = new int[1000000];
    ArrayList<Integer> list = new ArrayList<>(1000000);

    @Benchmark
    public int[] arrayCreation() {
        int[] array = new int[1000000];
        return array;
    }

    @Benchmark
    public ArrayList<Integer> arrayListCreation() {
        ArrayList<Integer> list = new ArrayList<>(1000000);
        return list;
    }

    @Benchmark
    public int[] arrayItemsSetting(MyState state) {
        int[] array = state.arrayCreation();
        for (int i = 0; i < 1000000; i++) {
            array[i] = i;
        }
        return array;
    }

    @Benchmark
    public ArrayList<Integer> arrayListItemsSetting(MyState state) {
        ArrayList<Integer> list = state.arrayListCreation();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
        return list;
    }

    @Benchmark
    public void arrayItemsRetrieval(Blackhole blackhole,MyState state) {
        int[] array = state.arrayCreation();
        for (int i = 0; i < 1000000; i++) {
            int item = array[i];
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void arrayListItemsRetrieval(Blackhole blackhole,MyState state) {
        ArrayList<Integer> list = state.arrayListItemsSetting();
        for (int i = 0; i < 1000000; i++) {
            int item = list.get(i);
            blackhole.consume(item);
        }
    }

    @Benchmark
    public void arrayCloning(Blackhole blackhole,MyState state) {
        int[] array = state.arrayItemsSetting();
        int[] newArray = array.clone();
        blackhole.consume(newArray);
    }

    @Benchmark
    public void arrayListCloning(Blackhole blackhole,MyState state) {
        ArrayList<Integer> list = state.arrayListItemsSetting();
        ArrayList<Integer> newList = new ArrayList<>(list);
        blackhole.consume(newList);
    }
}