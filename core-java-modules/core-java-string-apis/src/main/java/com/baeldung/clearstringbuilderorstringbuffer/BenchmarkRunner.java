package com.baeldung.clearstringbuilderorstringbuffer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Benchmark)
    public static class MyState {
        final String HELLO = "Hello World";
        final StringBuilder sb = new StringBuilder().append(HELLO);
    }

    @Benchmark
    public void evaluateSetLength(Blackhole blackhole, MyState state) {
        state.sb.setLength(0);
        blackhole.consume(state.sb.toString());
    }

    @Benchmark
    public void evaluateDelete(Blackhole blackhole, MyState state) {
        state.sb.delete(0, state.sb.length());
        blackhole.consume(state.sb.toString());
    }

}
