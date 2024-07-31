package com.baeldung.listandset.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.MILLISECONDS)
public class ListAndSetAddBenchmark {

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
          .include(ListAndSetAddBenchmark.class.getSimpleName())
          .forks(1)
          .addProfiler("gc")
          .build();
        new Runner(opt).run();

    }

    @Benchmark
    public void addElementToArrayList(Params param, Blackhole blackhole) {
        param.arrayList.clear();
        for (int i = 0; i < param.addNumber; i++) {
            blackhole.consume(param.arrayList.add(i));
        }
    }

    @Benchmark
    public void addElementToHashSet(Params param, Blackhole blackhole) {
        param.hashSet.clear();
        for (int i = 0; i < param.addNumber; i++) {
            blackhole.consume(param.hashSet.add(i));
        }
    }

    @State(Scope.Benchmark)
    public static class Params {
        public int addNumber = 10000000;

        public List<Integer> arrayList = new ArrayList<>();
        public Set<Integer> hashSet = new HashSet<>();
    }

}
