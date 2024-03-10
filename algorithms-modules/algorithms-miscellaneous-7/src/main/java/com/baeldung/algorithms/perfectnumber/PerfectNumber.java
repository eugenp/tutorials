package com.baeldung.algorithms.perfectnumber;

import java.util.stream.IntStream;

class PerfectNumber {

    public static boolean isPerfectBruteForce(int number) {
        int sum = 0;
        for (int i = 1; i <= number / 2; i++) {
            if (number % i == 0) {
                sum += i;
            }
        }
        return sum == number;
    }

    public static boolean isPerfectStream(int number) {
        int sum = IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .filter(test -> number % test == 0)
                .reduce(1, (s, test) -> s + test + (number / test));
        return sum == number;
    }

    public static boolean isPerfectEuclidEuler(int number) {
        int p = 2;
        int perfectNumber = (int) (Math.pow(2, p - 1) * (Math.pow(2, p) - 1));
        while (perfectNumber <= number) {
            if (perfectNumber == number) {
                return true;
            }
            p++;
            perfectNumber = (int) (Math.pow(2, p - 1) * (Math.pow(2, p) - 1));
        }
        return false;
    }

    public static boolean isPerfectEuclidEulerUsingShift(int number) {
        int p = 2;
        int perfectNumber = (2 << (p - 1)) * ((2 << p) - 1);
        while (perfectNumber <= number) {
            if (perfectNumber == number) {
                return true;
            }
            p++;
            perfectNumber = (2 << (p - 1)) * ((2 << p) - 1);
        }
        return false;
    }

}