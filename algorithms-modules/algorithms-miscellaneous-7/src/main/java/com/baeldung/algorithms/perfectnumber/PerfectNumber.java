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

    public static boolean isPerfectStream(int num) {
        int sum = IntStream.rangeClosed(2, (int) Math.sqrt(num))
                .filter(test -> num % test == 0)
                .reduce(1, (s, test) -> s + test + (num / test));
        return sum == num;
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

}