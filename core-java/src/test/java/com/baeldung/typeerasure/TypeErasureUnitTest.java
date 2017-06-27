package com.baeldung.typeerasure;

import org.junit.Test;

public class TypeErasureExamplesUnitTest {

    @Test(expected = ArrayStoreException.class)
    public void objectArrayRuntimeExceptionUnitTest() {
        TypeErasureExamples.objectArrayErasure();
    }

    @Test
    public void listArrayTypeErasureUnitTest() {
        TypeErasureExamples.listArrayErasure();
    }

    @Test
    public void printArrayMethodTypeErasureUnitTest() {
        Integer[] scores = new Integer[] { 100, 200, 10, 99, 20 };
        TypeErasureExamples.printArray(scores);
    }

}
