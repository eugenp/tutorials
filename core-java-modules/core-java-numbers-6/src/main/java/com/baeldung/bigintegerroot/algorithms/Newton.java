package com.baeldung.bigintegerroot.algorithms;

import java.math.BigInteger;

public class Newton {

    private Newton() {
    }

    public static BigInteger sqrt(BigInteger n) {
        // Initial approximation
        BigInteger x = n.divide(BigInteger.TWO);

        // Tolerance level (small positive integer)
        BigInteger tolerance = BigInteger.ONE;

        while (true) {
            // x_new = 0.5 * (x + n / x)
            BigInteger xNew = x.add(n.divide(x)).divide(BigInteger.TWO);

            // Check for convergence within tolerance
            if (x.subtract(xNew).abs().compareTo(tolerance) <= 0) {
                return xNew;
            }

            x = xNew;
        }
    }
}
