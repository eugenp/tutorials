package com.baeldung.reversenumber;

public class ReverseNumber {

    public static int reverseNumberWhileLoop(int number) {
        int reversedNumber = 0;

        while (number > 0) {
            int mod = number % 10;
            reversedNumber = reversedNumber * 10 + mod;
            number /= 10;
        }

        return reversedNumber;
    }

    public static int reverseNumberForLoop(int number) {
        int reversedNumber = 0;

        for (;number>0;number/=10) {
            int mod = number % 10;
            reversedNumber = reversedNumber * 10 + mod;
        }

        return reversedNumber;
    }

    public static int recursiveReversedNumber = 0;
    public static void reverseNumberRec(int number) {

        if (number > 0) {
            int mod = number % 10;
            recursiveReversedNumber = recursiveReversedNumber * 10 + mod;
            number /= 10;
            reverseNumberRec(number);
        }
    }
}
