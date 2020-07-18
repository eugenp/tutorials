package com.baeldung;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ExceptionBenchmark {
    private static final int LIMIT = 10_000;

    @Benchmark
    public void doNotThrowException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Object());
        }
    }

    @Benchmark
    public void throwAndCatchException(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }

    @Benchmark
    public void createExceptionWithoutThrowingIt(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            blackhole.consume(new Exception());
        }
    }

    @Benchmark
    @Fork(value = 1, jvmArgs = "-XX:-StackTraceInThrowable")
    public void throwExceptionWithoutAddingStackTrace(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e);
            }
        }
    }

    @Benchmark
    public void throwExceptionAndUnwindStackTrace(Blackhole blackhole) {
        for (int i = 0; i < LIMIT; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
                blackhole.consume(e.getStackTrace());
            }
        }
    }
}
