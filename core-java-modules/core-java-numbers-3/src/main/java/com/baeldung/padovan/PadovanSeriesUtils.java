package com.baeldung.padovan;

public class PadovanSeriesUtils {

    public static int nthPadovanTermRecursiveMethod(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        return nthPadovanTermRecursiveMethod(n - 2) + nthPadovanTermRecursiveMethod(n - 3);
    }

    public static int nthPadovanTermRecursiveMethodWithMemoization(int n) {
        int[] memo = new int[n + 1];
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        if (memo[n] != 0) {
            return memo[n];
        }
        memo[n] = nthPadovanTermRecursiveMethodWithMemoization(n - 2) + nthPadovanTermRecursiveMethodWithMemoization(n - 3);
        return memo[n];
    }

    public static int nthPadovanTermIterativeMethodWithArray(int n) {
        int[] memo = new int[n + 1];
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        memo[0] = 1;
        memo[1] = 1;
        memo[2] = 1;
        for (int i = 3; i <= n; i++) {
            memo[i] = memo[i - 2] + memo[i - 3];
        }
        return memo[n];
    }

    public static int nthPadovanTermIterativeMethodWithVariables(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        int p0 = 1, p1 = 1, p2 = 1;
        int tempNthTerm;
        for (int i = 3; i <= n; i++) {
            tempNthTerm = p0 + p1;
            p0 = p1;
            p1 = p2;
            p2 = tempNthTerm;
        }
        return p2;
    }

    public static int nthPadovanTermUsingFormula(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }

        // Padovan spiral constant (plastic number) - the real root of x^3 - x - 1 = 0
        final double PADOVAN_CONSTANT = 1.32471795724474602596;
        
        // Normalization factor to approximate Padovan sequence values
        final double NORMALIZATION_FACTOR = 1.045356793252532962;
        
        double p = Math.pow(PADOVAN_CONSTANT, n - 1); 
        return (int) Math.round(p / NORMALIZATION_FACTOR);
    }
}

