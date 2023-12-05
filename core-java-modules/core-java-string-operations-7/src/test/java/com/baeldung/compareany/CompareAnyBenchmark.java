package com.baeldung.compareany;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1)
public class CompareAnyBenchmark {
    private final String[] groupOfFruits = {"Apple", "Mango", "Dragon Fruit", "Water Melon", "Avocado", "Guava", "Orange"};
    private final String fruit = "Apple";

    @Benchmark
    public boolean compareWithAnyUsingStringUtils() {
        return StringUtils.containsAny(fruit, groupOfFruits);
    }

    @Benchmark
    public boolean compareWithAnyCaseInsensitiveUsingStringUtils() {
        return StringUtils.containsAnyIgnoreCase(fruit, groupOfFruits);
    }

    @Benchmark
    public boolean compareWithAnyUsingSet() {
        return Set.of(groupOfFruits).contains(fruit);
    }

    @Benchmark
    public boolean compareWithAnyUsingRegularExpression() {
        return fruit.matches(String.join("|", groupOfFruits));
    }

    @Benchmark
    public boolean compareWithAnyCaseInsensitiveUsingRegularExpression() {
        return fruit.matches("(?i)" + String.join("|", groupOfFruits));
    }

    @Benchmark
    public boolean compareWithAnyUsingStream() {
        return Arrays.stream(groupOfFruits).anyMatch(fruit::equals);
    }

    @Benchmark
    public boolean compareWithAnyCaseInsensitiveUsingStream() {
        return Arrays.stream(groupOfFruits).anyMatch(fruit::equalsIgnoreCase);
    }

    @Benchmark
    public boolean compareWithAnyUsingArrayUtils() {
        return ArrayUtils.contains(groupOfFruits, fruit);
    }

    @Benchmark
    public boolean compareWithAnyWithIf() {
        for(String s : groupOfFruits) {
            if (fruit.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().include(CompareAnyBenchmark.class.getSimpleName())
            .threads(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server")
            .build();
        new Runner(options).run();
    }

}
