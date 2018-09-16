package com.java.src;

import java.util.Scanner;

public class RoundUpToHundred {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double input = scanner.nextDouble();
        scanner.close();

        RoundUpToHundred.round(input);
    }

    static int round(double input) {
        int i = (int) Math.ceil(input);
        return ((i + 99) / 100) * 100;
    };

}
