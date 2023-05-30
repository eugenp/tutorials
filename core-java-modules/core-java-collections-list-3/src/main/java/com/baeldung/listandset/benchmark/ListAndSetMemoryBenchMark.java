package com.baeldung.listandset.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
public class ListAndSetMemoryBenchMark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
          .include(ListAndSetBenchmark.class.getSimpleName())
          .forks(1)
          .addProfiler("gc")
          .build();
        new Runner(opt).run();
    }
}
