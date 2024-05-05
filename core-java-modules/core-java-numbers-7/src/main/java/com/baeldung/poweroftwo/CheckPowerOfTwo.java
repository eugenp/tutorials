package com.baeldung.poweroftwo;

public class CheckPowerOfTwo {

    public static boolean usingLogarithm(int n) {
        return (Math.log(n) / Math.log(2)) % 1 == 0;
    }

    public static boolean usingBitwiseOperations(int n) {
        return (n != 0) && ((n & (n - 1)) == 0);
    }

    public static boolean usingLoopDivision(int n) {
        while (n != 1 && n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }

    public static boolean usingHighestOneBit(int n) {
        return n > 0 && (n == Integer.highestOneBit(n));
    }
}
