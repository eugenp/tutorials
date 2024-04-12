package com.baeldung.stringbuffer;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 10000, iterations = 10)
@Warmup(batchSize = 1000, iterations = 10)
@State(Scope.Thread)
public class ComparePerformance {

    String strInitial = "springframework";
    String strFinal = "";
    String replacement = "java-";

    @Benchmark
    public String benchmarkStringConcatenation() {
        strFinal = "";
        strFinal += strInitial;
        return strFinal;
    }

    @Benchmark
    public StringBuffer benchmarkStringBufferConcatenation() {
        StringBuffer stringBuffer = new StringBuffer(strFinal);
        stringBuffer.append(strInitial);
        return stringBuffer;
    }

    @Benchmark
    public String benchmarkStringReplacement() {
        strFinal = strInitial.replaceFirst("spring", replacement);
        return strFinal;
    }

    @Benchmark
    public StringBuffer benchmarkStringBufferReplacement() {
        StringBuffer stringBuffer = new StringBuffer(strInitial);
        stringBuffer.replace(0,6, replacement);
        return stringBuffer;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ComparePerformance.class.getSimpleName()).threads(1)
            .forks(1).shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
