package com.baeldung.automorphicnumber;

public class AutomorphicNumber {

    public static void main(String[] args) {
        System.out.println(isAutomorphicUsingLoop(76));
        System.out.println(isAutomorphicUsingMath(76));
    }

    public static boolean isAutomorphicUsingMath(int number) {
        int square = number * number;

        int numberOfDigits = (int) Math.floor(Math.log10(number) + 1);
        int lastDigits = (int) (square % (Math.pow(10, numberOfDigits)));

        return number == lastDigits;
    }

    public static boolean isAutomorphicUsingLoop(int number) {
        int square = number * number;

        while (number > 0) {
            if (number % 10 != square % 10) {
                return false;
            }
            number /= 10;
            square /= 10;
        }
        return true;
    }
}