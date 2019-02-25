package com.baeldung.nulls;

public class PrimitivesAndWrapper {

    public static int sum(int a, int b) {
        return a + b;
    }

    public static Integer sum(Integer a, Integer b) {
        return a + b;
    }

    public static Integer goodSum(Integer a, Integer b) {
        if (a != null && b != null)
            return a + b;
        else
            throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        sum(0, 0);
        sum(null, null);
    }
}
