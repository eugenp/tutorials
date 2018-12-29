package com.baeldung.algorithms.roundedup;

import java.util.Scanner;

public class RoundUpToHundred {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double input = scanner.nextDouble();
        scanner.close();

        RoundUpToHundred.round(input);
    }

    static long round(double input) {
        long i = (long) Math.ceil(input);
        return ((i + 99) / 100) * 100;
    };

}
