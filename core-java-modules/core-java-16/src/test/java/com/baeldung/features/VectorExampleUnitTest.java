package com.baeldung.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class VectorExampleUnitTest {

    @Test
    void givenAScalarComputation_whenCalculatingUsingVectorAPI_thenResultIsSameAsPreviousMethodOfComputation() {
        VectorExample objectUnderTest = new VectorExample();

        int[] a = {1, 2, 3, 4};
        int[] b = {5, 6, 7, 8};

        int[] result = objectUnderTest.scalarComputation(a, b);
        int[] result2 = objectUnderTest.vectorComputation(a, b);

        assertArrayEquals(result, result2);
    }
}