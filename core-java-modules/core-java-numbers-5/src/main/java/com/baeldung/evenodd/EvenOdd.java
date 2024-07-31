package com.baeldung.evenodd;

public class EvenOdd {

    static boolean isEven(int x) {
        return x % 2 == 0;
    }

    static boolean isOdd(int x) {
        return x % 2 != 0;
    }

    static boolean isOrEven(int x) {
        return (x | 1) > x;
    }

    static boolean isOrOdd(int x) {
        return (x | 1) == x;
    }

    static boolean isAndEven(int x) {
        return (x & 1) == 0;
    }

    static boolean isAndOdd(int x) {
        return (x & 1) == 1;
    }

    static boolean isXorEven(int x) {
        return (x ^ 1) > x;
    }

    static boolean isXorOdd(int x) {
        return (x ^ 1) < x;
    }

    static boolean isLsbEven(int x) {
        return Integer.toBinaryString(x)
            .endsWith("0");
    }

    static boolean isLsbOdd(int x) {
        return Integer.toBinaryString(x)
            .endsWith("1");
    }
}
