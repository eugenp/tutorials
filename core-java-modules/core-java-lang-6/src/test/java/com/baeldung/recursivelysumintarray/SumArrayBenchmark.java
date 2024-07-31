package com.baeldung.recursivelysumintarray;

import static com.baeldung.recursivelysumintarray.RecursivelySumIntArrayUnitTest.sumIntArray1;
import static com.baeldung.recursivelysumintarray.RecursivelySumIntArrayUnitTest.sumIntArray2;

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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2)
@Fork(1)
@Measurement(iterations = 5)
public class SumArrayBenchmark {

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(SumArrayBenchmark.class.getSimpleName())
          .build();
        new Runner(options).run();
    }

    @Param({ "10", "10000" })
    public int size;
    int[] array;

    @Setup
    public void setup() {
        var r = new Random();
        array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = r.nextInt();
        }
    }

    @Benchmark
    public int withArrayCopy() {
        return sumIntArray1(array);
    }

    @Benchmark
    public int withoutArrayCopy() {
        return sumIntArray2(array, array.length - 1);
    }
}