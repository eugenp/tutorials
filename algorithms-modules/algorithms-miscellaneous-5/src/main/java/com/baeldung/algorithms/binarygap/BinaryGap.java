package com.baeldung.algorithms.binarygap;

public class BinaryGap {
    static int calculateBinaryGap(int n) {
        return calculateBinaryGap(n >>> Integer.numberOfTrailingZeros(n), 0, 0);
    }

    static int calculateBinaryGap(int n, int current, int maximum) {
        if (n == 0) {
            return maximum;
        } else if ((n & 1) == 0) {
            return calculateBinaryGap(n >>> 1, current + 1, maximum);
        } else {
            return calculateBinaryGap(n >>> 1, 0, Math.max(maximum, current));
        }
    }
}
