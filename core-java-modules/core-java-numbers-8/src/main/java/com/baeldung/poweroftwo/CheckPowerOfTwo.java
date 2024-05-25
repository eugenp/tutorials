package com.baeldung.poweroftwo;

public class CheckPowerOfTwo {

    public static boolean isPowerOfTwoUsingLogarithm(int n) {
        return (Math.log(n) / Math.log(2)) % 1 == 0;
    }

    public static boolean isPowerOfTwoUsingBitwiseOperations(int n) {
        return (n != 0) && ((n & (n - 1)) == 0);
    }

    public static boolean isPowerOfTwoUsingLoopDivision(int n) {
        while (n != 1 && n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }

    public static boolean isPowerOfTwoUsingHighestOneBit(int n) {
        return n > 0 && (n == Integer.highestOneBit(n));
    }
}
