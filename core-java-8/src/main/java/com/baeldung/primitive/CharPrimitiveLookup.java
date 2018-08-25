package com.baeldung.primitive;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class CharPrimitiveLookup extends Lookup {

    private char[] elements;
    private final char pivot = 'b';

    @Setup
    @Override
    public void prepare() {
        char common = 'a';
        elements = new char[s];
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
        return CharPrimitiveLookup.class.getSimpleName();
    }



}
