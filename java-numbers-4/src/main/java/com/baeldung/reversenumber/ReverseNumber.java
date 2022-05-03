package com.baeldung.reversenumber;

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

    public static int recursiveReversedNumber = 0;
    public static boolean negativeReversedNumber = false;

    public static void reverseNumberRec(int number) {
        int numberToReverse = Math.abs(number);

        if (number < 0) {
            negativeReversedNumber = true;
        }

        if (numberToReverse > 0) {
            int mod = numberToReverse % 10;
            recursiveReversedNumber = recursiveReversedNumber * 10 + mod;
            numberToReverse /= 10;
            reverseNumberRec(numberToReverse);
        } else if (numberToReverse == 0 && negativeReversedNumber) {
            recursiveReversedNumber *= -1;
        }
    }
}
