package com.baeldung.intposneg;

import org.openjdk.jmh.annotations.Benchmark;

public class UnaryVsNegateExactBenchmark {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void unaryNegate() {
        int x = 100;
        int y = -x;
    }

    @Benchmark
    public void negateExactNegate() {
        int x = 100;
        int y = Math.negateExact(x);
    }
}
