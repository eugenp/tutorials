package com.baeldung.features;

import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JEP414UnitTest {

    @Test
    void vectorComputation_shouldMultiplyVectors() {
        var jep414 = new JEP414();

        float[] a = initArray(100);
        float[] b = initArray(100);
        float[] c = new float[100];
        float[] d = new float[100];

        jep414.newVectorComputation(a, b, c);
        jep414.commonVectorComputation(a, b, d);

        for (int i = 0; i < a.length; i++) {
            assertEquals(c[i], d[i]);
        }
    }

    private float[] initArray(int size) {
        final var jep356 = new JEP356();
        final var values = new float[size];
        final var pseudoInts = jep356.getPseudoInts("Xoshiro256PlusPlus", size).mapToObj(Float::valueOf).collect(toList());

        for (int i = 0; i < pseudoInts.size(); i++) {
            values[i] = pseudoInts.get(i);
        }

        return values;
    }
}