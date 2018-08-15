package com.baeldung.primitive;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.function.Function;

/**
 * This is a class that measures the execution time of comparison operation for
 * the following pairs of the primitive-reference types:
 * 1. int - Integer
 * 2. long - Long
 * 3. double - Double
 * 4. char - Character
 * 5. byte - Byte
 * 6. boolean - Boolean
 * 7. short - Short
 * 8. float - Float
 */
@State(Scope.Thread)
public class PrimitiveVsReferenceRunner {

    private int s = 10;



    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupPrimitiveDouble() {
        double[] elements = new double[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        double pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupWrapperDouble() {
        Double[] elements = new Double[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1D;
        }
        Double pivot = 2D;
        elements[s - 1] = pivot;
        int index = 0;
        while (!pivot.equals(elements[index])) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupPrimitiveShort() {
        short[] elements = new short[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        short pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupWrapperShort() {
        Short[] elements = new Short[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        Short pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        while (!pivot.equals(elements[index])) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupPrimitiveBoolean() {
        boolean[] elements = new boolean[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = true;
        }
        boolean pivot = false;
        elements[s - 1] = pivot;
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupWrapperBoolean() {
        Boolean[] elements = new Boolean[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = Boolean.TRUE;
        }
        Boolean pivot = Boolean.FALSE;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupPrimitiveByte() {
        byte[] elements = new byte[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        byte pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupWrapperByte() {
        Byte[] elements = new Byte[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        Byte pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupPrimitiveChar() {
        char[] elements = new char[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 'a';
        }
        char pivot = 'b';
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupWrapperChar() {
        Character[] elements = new Character[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 'a';
        }
        Character pivot = 'b';
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupPrimitiveLong() {
        long[] elements = new long[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        long pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        while (pivot != elements[index]) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void lookupWrapperLong() {
        Long[] elements = new Long[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1L;
        }
        Long pivot = 2L;
        elements[s - 1] = pivot;
        int index = 0;
        while (!pivot.equals(elements[index])) {
            index++;
        }
        assert index == s - 1 : "Wrong index";

    }

}
