package com.baeldung.copyarraymethodsperformance;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
@Fork(1)
@Measurement(iterations = 100)
public class ObjectsCopyBenchmark {

    @Param({ "10", "1000000" })
    public int SIZE;
    Integer[] src;

    @Setup
    public void setup() {
        Random r = new Random();
        src = new Integer[SIZE];

        for (int i = 0; i < SIZE; i++) {
            src[i] = r.nextInt();
        }
    }

    @Benchmark
    public Integer[] systemArrayCopyBenchmark() {
        Integer[] target = new Integer[SIZE];
        System.arraycopy(src, 0, target, 0, SIZE);
        return target;
    }

    @Benchmark
    public Integer[] arraysCopyOfBenchmark() {
        return Arrays.copyOf(src, SIZE);
    }
}
