package com.baeldung.primitive;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class DoublePrimitiveLookup extends Lookup {
    private double[] elements;
    private final double pivot = 2;

    @Setup
    @Override
    public void prepare() {
        double common = 1;
        elements = new double[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = common;
        }
        elements[s - 1] = pivot;
    }

    @TearDown
    @Override
    public void clean() {
        elements = null;
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int findPosition() {
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        return index;
    }

    @Override
    public String getSimpleClassName() {
        return DoublePrimitiveLookup.class.getSimpleName();
    }
}
