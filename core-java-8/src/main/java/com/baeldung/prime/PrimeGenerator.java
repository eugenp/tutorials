package com.baeldung.prime;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeGenerator {
    public static List<Integer> sieveOfEratosthenes(int n) {
        final boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);

        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p)
                    prime[i] = false;
            }
        }

        final List<Integer> primes = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i])
                primes.add(i);
        }
        return primes;
    }

    public static List<Integer> primeNumbersBruteForce(int max) {
        final List<Integer> primeNumbers = new LinkedList<Integer>();
        for (int i = 2; i <= max; i++) {
            if (isPrimeBruteForce(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    private static boolean isPrimeBruteForce(int x) {
        for (int i = 2; i < x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> primeNumbersTill(int max) {
        return IntStream.rangeClosed(2, max)
            .filter(x -> isPrime(x))
            .boxed()
            .collect(Collectors.toList());
    }

    private static boolean isPrime(int x) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(x)))
            .allMatch(n -> x % n != 0);
    }
}
