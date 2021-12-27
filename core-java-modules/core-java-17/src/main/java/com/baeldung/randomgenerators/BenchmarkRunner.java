package com.baeldung.randomgenerators;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void testL128X256MixRandom() {
        RandomGenerator generator = GeneratorSelection.getSpecificGenerator("L128X256MixRandom");
        generator.nextInt();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public static void testRandom() {
        RandomGenerator generator = GeneratorSelection.getSpecificGenerator("Random");
        generator.nextInt();
    }

}
