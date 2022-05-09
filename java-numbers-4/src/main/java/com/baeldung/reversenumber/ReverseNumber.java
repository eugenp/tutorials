package com.baeldung.reversenumber;

import java.util.concurrent.atomic.AtomicInteger;

public class ReverseNumber {

    public static int reverseNumberWhileLoop(int number) {
        int reversedNumber = 0;
        int numberToReverse = Math.abs(number);

        while (numberToReverse > 0) {
            int mod = numberToReverse % 10;
            reversedNumber = reversedNumber * 10 + mod;
            numberToReverse /= 10;
        }

        return number < 0 ? reversedNumber * -1 : reversedNumber;
    }

    public static int reverseNumberForLoop(int number) {
        int reversedNumber = 0;
        int numberToReverse = Math.abs(number);

        for (; numberToReverse > 0; numberToReverse /= 10) {
            int mod = numberToReverse % 10;
            reversedNumber = reversedNumber * 10 + mod;
        }

        return number < 0 ? reversedNumber * -1 : reversedNumber;
    }

    public static int reverseNumberRecWrapper(int number) {
        AtomicInteger output = new AtomicInteger(0);
        reverseNumberRec(Math.abs(number), output);
        return number < 0 ? output.get() * -1 : output.get();
    }
    private static void reverseNumberRec(int numberToReverse, AtomicInteger recursiveReversedNumber) {

        if (numberToReverse > 0) {
            int mod = numberToReverse % 10;
            int currentValue = recursiveReversedNumber.get();
            recursiveReversedNumber.getAndSet(currentValue * 10 + mod);
            numberToReverse /= 10;
            reverseNumberRec(numberToReverse, recursiveReversedNumber);
        }
    }
}
