package com.baeldung.stringbuilderstringbuffer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StringBuilderStringBuffer {

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
            .include(StringBuilderStringBuffer.class.getSimpleName())
            .build();

        new Runner(opt).run();
    }
    
    @State(Scope.Benchmark)
    public static class MyState {
        int iterations = 1000;
        String initial = "abc";
        String suffix = "def";
    }
     
    @Benchmark
    public StringBuffer benchmarkStringBuffer(MyState state) {
        StringBuffer stringBuffer = new StringBuffer(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuffer.append(state.suffix);
        }
        return stringBuffer;
    }

    @Benchmark
    public StringBuilder benchmarkStringBuilder(MyState state) {
        StringBuilder stringBuilder = new StringBuilder(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuilder.append(state.suffix);
        }
        return stringBuilder;
    }
}