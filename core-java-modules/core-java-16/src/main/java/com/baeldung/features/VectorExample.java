package com.baeldung.features;

import jdk.incubator.vector.IntVector;

public class VectorExample {
    public int[] scalarComputation(int[] a, int[] b) {
        var c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] * b[i];
        }
        return c;
    }

    public int[] vectorComputation(int[] a, int[] b) {
        var c = new int[a.length];

        var vectorA = IntVector.fromArray(IntVector.SPECIES_128, a, 0);
        var vectorB = IntVector.fromArray(IntVector.SPECIES_128, b, 0);
        var vectorC = vectorA.mul(vectorB);
        vectorC.intoArray(c, 0);
        return c;
    }
}
