package com.baeldung.algorithms.relativelyprime;

import java.math.BigInteger;

class RelativelyPrime {

    static boolean iterativeRelativelyPrime(int a, int b) {
        return iterativeGCD(a, b) == 1;
    }

    static boolean recursiveRelativelyPrime(int a, int b) {
        return recursiveGCD(a, b) == 1;
    }

    static boolean bigIntegerRelativelyPrime(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).equals(BigInteger.ONE);
    }

    private static int iterativeGCD(int a, int b) {
        int tmp;
        while (b != 0) {
            if (a < b) {
                tmp = a;
                a = b;
                b = tmp;
            }
            tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }

    private static int recursiveGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (a < b) {
            return recursiveGCD(b, a);
        }
        return recursiveGCD(b, a % b);
    }


}
