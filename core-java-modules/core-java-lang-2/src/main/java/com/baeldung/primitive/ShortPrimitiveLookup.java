package com.baeldung.primitive;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class ShortPrimitiveLookup extends Lookup {

    private short[] elements;
    private final short pivot = 2;

    @Setup
    @Override
    public void prepare() {
        short common = 1;
        elements = new short[s];
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
        return ShortPrimitiveLookup.class.getSimpleName();
    }


}
