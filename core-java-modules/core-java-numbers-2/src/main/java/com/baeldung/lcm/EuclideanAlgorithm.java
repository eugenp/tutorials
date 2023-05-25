package com.baeldung.lcm;

import java.util.Arrays;

public class EuclideanAlgorithm {

    public static int gcd(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return number1 + number2;
        } else {
            int absNumber1 = Math.abs(number1);
            int absNumber2 = Math.abs(number2);
            int biggerValue = Math.max(absNumber1, absNumber2);
            int smallerValue = Math.min(absNumber1, absNumber2);
            return gcd(biggerValue % smallerValue, smallerValue);
        }
    }

    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0)
            return 0;
        else {
            int gcd = gcd(number1, number2);
            return Math.abs(number1 * number2) / gcd;
        }
    }

    public static int lcmForArray(int[] numbers) {
        int lcm = numbers[0];
        for (int i = 1; i <= numbers.length - 1; i++) {
            lcm = lcm(lcm, numbers[i]);
        }
        return lcm;
    }

    public static int lcmByLambda(int... numbers) {
        return Arrays.stream(numbers).reduce(1, (lcmSoFar, currentNumber) -> Math.abs(lcmSoFar * currentNumber) / gcd(lcmSoFar, currentNumber));
    }

}
