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

    public static int reverseNumberRecWrapper(int number) {
        int output = reverseNumberRec(Math.abs(number), 0);
        return number < 0 ? output * -1 : output;
    }
    private static int reverseNumberRec(int numberToReverse, int recursiveReversedNumber) {

        if (numberToReverse > 0) {
            int mod = numberToReverse % 10;
            recursiveReversedNumber = recursiveReversedNumber * 10 + mod;
            numberToReverse /= 10;
            return reverseNumberRec(numberToReverse, recursiveReversedNumber);
        }

        return recursiveReversedNumber;
    }
}
