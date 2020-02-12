package com.baeldung.primitive;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class FloatPrimitiveLookup extends Lookup {
    private float[] elements;
    private final float pivot = 2;

    @Setup
    @Override
    public void prepare() {
        int common = 1;
        elements = new float[s];
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
        return FloatPrimitiveLookup.class.getSimpleName();
    }
}
