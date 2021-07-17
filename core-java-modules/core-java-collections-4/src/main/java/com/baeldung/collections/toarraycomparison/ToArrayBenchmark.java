package com.baeldung.collections.toarraycomparison;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
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

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgsAppend = { "-XX:+UseParallelGC", "-Xms4g", "-Xmx4g" })
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class ToArrayBenchmark {

    @Param({ "10", "10000", "10000000" })
    private int size;

    @Param({ "array-list", "tree-set" })
    private String type;

    private Collection<String> collection;

    @Setup
    public void setup() {
        switch (type) {
        case "array-list":
            collection = new ArrayList<String>();
            break;
        case "tree-set":
            collection = new TreeSet<String>();
            break;
        default:
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i < size; i++) {
            collection.add(String.valueOf(i));
        }
    }

    @Benchmark
    public String[] zero_sized() {
        return collection.toArray(new String[0]);
    }

    @Benchmark
    public String[] pre_sized() {
        return collection.toArray(new String[collection.size()]);
    }


    public static void main(String[] args) {
        try {
            org.openjdk.jmh.Main.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
