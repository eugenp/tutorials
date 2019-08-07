package com.baeldung.algorithms.perfectsquarecounter;

public class PerfectSquareCounter {

    static int evenPerfectSquareNumbers = 0;

    public static void main(String[] args) {
        int i = 100;
        System.out.println("Total Perfect Squares: " + calculateCount(i));
        System.out.println("Even Perfect Squares : " + evenPerfectSquareNumbers);
    }

    public static int calculateCount(int i) {
        int perfectSquaresCount = 0;
        for (int number = 1; number <= i; number++) {
            if (isPerfectSquare(number)) {
                perfectSquaresCount++;
                if (number % 2 == 0) {
                    evenPerfectSquareNumbers++;
                }
            }
        }
        return perfectSquaresCount;
    }

    private static boolean isPerfectSquare(int number) {
        double sqrt = Math.sqrt(number);
        return sqrt - Math.floor(sqrt) == 0;
    }
}
