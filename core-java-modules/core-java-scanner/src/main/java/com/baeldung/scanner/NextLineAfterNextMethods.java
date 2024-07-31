package com.baeldung.scanner;

import java.util.Scanner;

public class NextLineAfterNextMethods {

    private static void produceSkippingNextLineMethod() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine(); // Skipped because it reads the remaining newline character
            System.out.println(age + ":" + firstName);
        }
    }

    private static void fixSkippingNextLineMethodV1() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println(age + ":" + firstName);
        }
    }

    private static void fixSkippingNextLineMethodV2() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println(age + ":" + firstName);
        }
    }

    private static void fixSkippingNextLineMethodV3() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            scanner.skip("\r\n");
            System.out.print("Enter your first name: ");
            String firstName = scanner.nextLine();
            System.out.println(age + ":" + firstName);
        }
    }

    public static void main(String[] args) {
        produceSkippingNextLineMethod();
        fixSkippingNextLineMethodV1();
        fixSkippingNextLineMethodV2();
        fixSkippingNextLineMethodV3();
    }

}
