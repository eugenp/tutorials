package com.baeldung.collectionsvsarrays;

import com.baeldung.collectionsvsarrays.sorting.MergeSort;
import com.baeldung.collectionsvsarrays.sorting.Quicksort;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

@Measurement(iterations = 2, time = 10, timeUnit = TimeUnit.MINUTES)
@Warmup(iterations = 5, time = 10)
public class PerformanceBenchmark {

    private static final Random RANDOM = new Random();
    private static final int ARRAY_SIZE = 10000;
    private static final int[] randomNumbers = RANDOM.ints(ARRAY_SIZE).toArray();
    private static final int[] sameNumbers = IntStream.generate(() -> 42).limit(ARRAY_SIZE).toArray();
    public static final Supplier<int[]> randomNumbersSupplier = randomNumbers::clone;
    public static final Supplier<int[]> sameNumbersSupplier = sameNumbers::clone;

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, jvmArgs = {"-Xlog:gc:file=gc-logs-quick-sort-same-number-%t.txt,filesize=900m -Xmx6gb -Xms6gb"})
    public void quickSortSameNumber() {
        Quicksort.sort(sameNumbersSupplier.get());
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, jvmArgs = {"-Xlog:gc:file=gc-logs-quick-sort-random-number-%t.txt,filesize=900m -Xmx6gb -Xms6gb"})
    public void quickSortRandomNumber() {
        Quicksort.sort(randomNumbersSupplier.get());
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, jvmArgs = {"-Xlog:gc:file=gc-logs-merge-sort-same-number-%t.txt,filesize=900m -Xmx6gb -Xms6gb"})
    public void mergeSortSameNumber() {
        MergeSort.sort(sameNumbersSupplier.get());
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, jvmArgs = {"-Xlog:gc:file=gc-logs-merge-sort-random-number-%t.txt,filesize=900m -Xmx6gb -Xms6gb"})
    public void mergeSortRandomNumber() {
        MergeSort.sort(randomNumbersSupplier.get());
    }
}
