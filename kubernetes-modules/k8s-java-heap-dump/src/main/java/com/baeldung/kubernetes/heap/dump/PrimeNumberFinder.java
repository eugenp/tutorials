package com.baeldung.kubernetes.heap.dump;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PrimeNumberFinder {
    public static void main(String[] args) {
        List<Integer> primes = new ArrayList<>();
        int maxNumber = Integer.MAX_VALUE; // set to huge to make it run a long time

        for (int i = 2; i < maxNumber; i++) {
            if (isPrime(i)) {
                System.out.println(i);
                primes.add(i);
            }
        }
    }

    private static boolean isPrime(int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .allMatch(n -> number % n != 0);
    }
}
