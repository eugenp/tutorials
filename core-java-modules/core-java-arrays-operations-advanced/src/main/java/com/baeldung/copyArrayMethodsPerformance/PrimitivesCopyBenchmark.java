package com.baeldung.copyArrayMethodsPerformance;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Fork(1)
@Measurement(iterations = 100)
public class PrimitivesCopyBenchmark {

    @Param({ "10", "1000000" })
    public int SIZE;

    int[] src;

    @Setup
    public void setup() {
        Random r = new Random();
        src = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            src[i] = r.nextInt();
        }
    }

    @Benchmark
    public int[] systemArrayCopyBenchmark() {
        int[] target = new int[SIZE];
        System.arraycopy(src, 0, target, 0, SIZE);
        return target;
    }

    @Benchmark
    public int[] arraysCopyOfBenchmark() {
        return Arrays.copyOf(src, SIZE);
    }
}
