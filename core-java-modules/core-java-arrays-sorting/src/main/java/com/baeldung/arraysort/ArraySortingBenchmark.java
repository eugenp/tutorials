package com.baeldung.arraysort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ArraySortingBenchmark {

    @State(Scope.Benchmark)
    public static class ArrayContainer {

        @Param({ "1000", "10000", "100000", "1000000" })
        int arraySize;

        // initial unsorted array
        int[] unsortedArray;
        
        //cloned array to sort
        int[] arrayToSort;

        @Setup(Level.Trial)
        public void createUnSortedArray() {
            unsortedArray = new int[arraySize];
            for (int i = 0; i < arraySize; i++) {
                unsortedArray[i] = new Random().nextInt(1000);
            }
        }

        @Setup(Level.Invocation)
        public void createUnSortedArrayCopy() {
            arrayToSort = unsortedArray.clone();
        }

        int[] getArrayToSort() {
            return arrayToSort;
        }
    }

    @Benchmark
    public void benchmark_arrays_parallel_sort(ArrayContainer d, Blackhole b) {
        int[] arr = d.getArrayToSort();
        Arrays.parallelSort(arr);
        b.consume(arr);
    }

    @Benchmark
    public void benchmark_arrays_sort(ArrayContainer d, Blackhole b) {
        int[] arr = d.getArrayToSort();
        Arrays.sort(arr);
        b.consume(arr);
    }

}
