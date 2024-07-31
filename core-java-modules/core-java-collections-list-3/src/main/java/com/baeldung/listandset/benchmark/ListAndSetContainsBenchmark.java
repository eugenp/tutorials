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
public class ListAndSetContainsBenchmark {

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
          .include(ListAndSetContainsBenchmark.class.getSimpleName())
          .forks(1)
          .addProfiler("gc")
          .build();
        new Runner(opt).run();

    }




    @Benchmark
    public void searchElementInArrayList(Params param, Blackhole blackhole) {

        blackhole.consume(param.arrayList.contains(param.searchElement));
    }

    @Benchmark
    public void searchElementInHashSet(Params param, Blackhole blackhole) {

        blackhole.consume(param.hashSet.contains(param.searchElement));

    }

    @State(Scope.Benchmark)
    public static class Params {
        @Param({"5000000"})
        public int searchElement;

        @Param({"10000000"})
        public int collectionSize;

        public List<Integer> arrayList;
        public Set<Integer> hashSet;

        @Setup(Level.Iteration)
        public void setup() {
            arrayList = new ArrayList<>();
            hashSet = new HashSet<>();
            for (int i = 0; i < collectionSize; i++) {
                arrayList.add(i);
                hashSet.add(i);
            }
        }
    }

}
