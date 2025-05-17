package com.baeldung.reactor.fluxvsparallelflux;

public class Fibonacci {
    public static long fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
