package com.baeldung.algorithms.factorial;

public class Factorial {

    public static int findFactorial(int n) {
        if (n == 1) {
            return n;
        }
        return n * findFactorial(n - 1);
    }

}
