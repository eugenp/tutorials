package com.baeldung.streamordering;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class BenchmarkManualTest
{

  public void
  launchBenchmark() throws Exception {

    Options opt = new OptionsBuilder()
      // Specify which benchmarks to run.
      // You can be more specific if you'd like to run only one benchmark per test.
      .include(this.getClass().getName() + ".*")
      // Set the following options as needed
      .mode (Mode.AverageTime)
      .timeUnit(TimeUnit.MICROSECONDS)
      .warmupTime(TimeValue.seconds(1))
      .warmupIterations(2)
      .measurementTime(TimeValue.seconds(1))
      .measurementIterations(2)
      .threads(2)
      .forks(1)
      .shouldFailOnError(true)
      .shouldDoGC(true)
      //.jvmArgs("-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining")
      //.addProfiler(WinPerfAsmProfiler.class)
      .build();

    new Runner(opt).run();


  }

  @Benchmark
  public void givenOrderedStreamInput_whenStreamFiltered_showOpsPerMS(){
    IntStream.range(1, 100_000_000).parallel().filter(i -> i % 10 == 0).toArray();
  }

  @Benchmark
  public void givenUnorderedStreamInput_whenStreamFiltered_showOpsPerMS(){
    IntStream.range(1,100_000_000).unordered().parallel().filter(i -> i % 10 == 0).toArray();
  }

  @Benchmark
  public void givenUnorderedStreamInput_whenStreamDistinct_showOpsPerMS(){
    IntStream.range(1, 1_000_000).unordered().parallel().distinct().toArray();
  }

  @Benchmark
  public void givenOrderedStreamInput_whenStreamDistinct_showOpsPerMS() {
    //section 5.1.
    IntStream.range(1, 1_000_000).parallel().distinct().toArray();
  }


  // The JMH samples are the best documentation for how to use it
  // http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
  @State(Scope.Thread)
  public static class BenchmarkState
  {
    List<Integer> list;

    @Setup(Level.Trial) public void
    initialize() {

      Random rand = new Random();

      list = new ArrayList<>();
      for (int i = 0; i < 1000; i++)
        list.add (rand.nextInt());
    }
  }

  @Benchmark public void
  benchmark1 (BenchmarkState state, Blackhole bh) {

    List<Integer> list = state.list;

    for (int i = 0; i < 1000; i++)
      bh.consume (list.get (i));
  }
}
