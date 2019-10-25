package com.baeldung.nulls;

public class PrimitivesAndWrapper {

    public static int primitiveSum(int a, int b) {
        return a + b;
    }

    public static Integer wrapperSum(Integer a, Integer b) {
        return a + b;
    }

    public static Integer goodSum(Integer a, Integer b) {
        if (a != null && b != null) {
            return a + b;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
