package com.baeldung.scannerinput;

import java.util.Scanner;

public class DoWhileScanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            input = sc.nextLine();
            System.out.println(input);
        } while (!input.equals("exit"));

        sc.close();
    }
}
