package com.baeldung;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class FalseSharing {

    private java.util.concurrent.atomic.LongAdder builtin = new java.util.concurrent.atomic.LongAdder();
    private LongAdder custom = new LongAdder();

    @Benchmark
    public void builtin() {
        builtin.increment();
    }

    @Benchmark
    public void custom() {
        custom.increment();
    }
}
