package com.baeldung.inlining;

public class InliningExample {

    public static final int NUMBERS_OF_ITERATIONS = 15000;

    public static void main(String[] args) {
        for (int i = 1; i < NUMBERS_OF_ITERATIONS; i++) {
            calculateSum(i);
        }
    }

    private static long calculateSum(int n) {
        return new ConsecutiveNumbersSum(n).getTotalSum();
    }
}
