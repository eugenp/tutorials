package com.baeldung.primitive;

import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class CharacterWrapperLookup extends Lookup {
    private Character[] elements;
    private final char pivot = 'b';

    @Override
    @Setup
    public void prepare() {
        char common = 'a';
        elements = new Character[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = common;
        }
        elements[s - 1] = pivot;
    }

    @Override
    @TearDown
    public void clean() {
        elements = null;
    }

    @Override
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int findPosition() {
        int index = 0;
        Character pivotWrapper = pivot;
        while (!pivotWrapper.equals(elements[index])) {
            index++;
        }
        return index;

    }

    @Override
    public String getSimpleClassName() {
        return CharacterWrapperLookup.class.getSimpleName();
    }


}
