package com.baeldung.bigintegerroot;

import static com.baeldung.bigintegerroot.BigIntegerHolder.*;

import com.baeldung.bigintegerroot.algorithms.Newton;
import com.baeldung.bigintegerroot.algorithms.NewtonPlus;
import com.google.common.math.BigIntegerMath;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@Warmup(iterations = 1)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.MINUTES)
@Fork(1)
@State(Scope.Benchmark)
public class BigIntegerSquareRootBenchmark {

    @Param({BIG_NUMBER, VERY_BIG_NUMBER, INSANELY_BIG_NUMBER})
    public String number;
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void calculateRootWithJava(Blackhole blackhole) {
        final BigInteger integer = new BigInteger(number);
        final BigInteger root = integer.sqrt();
        blackhole.consume(root);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void calculateRootWithGuava(Blackhole blackhole) {
        final BigInteger integer = new BigInteger(number);
        final BigInteger root = BigIntegerMath.sqrt(integer, RoundingMode.DOWN);
        blackhole.consume(root);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void calculateRootWithNewtonPlus(Blackhole blackhole) {
        final BigInteger integer = new BigInteger(number);
        final BigInteger root = NewtonPlus.sqrt(integer);
        blackhole.consume(root);
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void calculateRootWithNewton(Blackhole blackhole) {
        final BigInteger integer = new BigInteger(number);
        final BigInteger root = Newton.sqrt(integer);
        blackhole.consume(root);
    }
}
