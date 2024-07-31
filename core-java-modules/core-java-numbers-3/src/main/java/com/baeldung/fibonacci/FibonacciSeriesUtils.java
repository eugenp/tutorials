package com.baeldung.fibonacci;

public class FibonacciSeriesUtils {

    public static int nthFibonacciTermRecursiveMethod(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        return nthFibonacciTermRecursiveMethod(n - 1) + nthFibonacciTermRecursiveMethod(n - 2);
    }

    public static int nthFibonacciTermIterativeMethod(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int n0 = 0, n1 = 1;
        int tempNthTerm;
        for (int i = 2; i <= n; i++) {
            tempNthTerm = n0 + n1;
            n0 = n1;
            n1 = tempNthTerm;
        }
        return n1;
    }

    public static int nthFibonacciTermUsingBinetsFormula(int n) {
        final double squareRootOf5 = Math.sqrt(5);
        final double phi = (1 + squareRootOf5)/2;
        int nthTerm = (int) ((Math.pow(phi, n) - Math.pow(-phi, -n))/squareRootOf5);
        return nthTerm;
    }
}
