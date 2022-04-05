package com.baeldung.optional.orelse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class OrElseAndOrElseGetBenchmarkRunner {

    private OrElseAndOrElseGet orElsevsOrElseGet = new OrElseAndOrElseGet();

    public static void main(String[] args) throws RunnerException, IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public String orElseBenchmark() {
        return orElsevsOrElseGet.getNameUsingOrElse("baeldung");
    }

    @Benchmark
    public String orElseGetBenchmark() {
        return orElsevsOrElseGet.getNameUsingOrElseGet("baeldung");
    }

}
