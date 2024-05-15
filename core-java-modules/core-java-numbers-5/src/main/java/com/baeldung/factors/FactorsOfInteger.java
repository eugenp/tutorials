package com.baeldung.factors;

import java.util.HashSet;
import java.util.Set;

public class FactorsOfInteger {
    public static Set<Integer> getAllFactorsVer1(int n) {
        Set<Integer> factors = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }

    public static Set<Integer> getAllFactorsVer2(int n) {
        Set<Integer> factors = new HashSet<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        return factors;
    }

    public static Set<Integer> getAllFactorsVer3(int n) {
        Set<Integer> factors = new HashSet<>();
        int step = n % 2 == 0 ? 1 : 2;
        for (int i = 1; i <= Math.sqrt(n); i += step) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        return factors;
    }
}
