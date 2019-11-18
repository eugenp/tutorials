package com.baeldung.maths.calculator.basic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BasicCalculatorIfElse {

    public static void main(String[] args) {

        System.out.println("---------------------------------- \n" +
                "Welcome to Basic Calculator \n" +
                "----------------------------------");
        System.out.println("Following operations are supported : \n" +
                "1. Addition (+) \n" +
                "2. Subtraction (-) \n" +
                "3. Multiplication (* OR x) \n" +
                "4. Division (/) \n");

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter an operator: (+ OR - OR * OR /) ");
            char operation = scanner.next().charAt(0);

            if (!(operation == '+' || operation == '-' || operation == '*' || operation == 'x' || operation == '/')) {
                System.err.println("Invalid Operator. Please use only + or - or * or /");
            }

            System.out.println("Enter First Number: ");
            double num1 = scanner.nextDouble();

            System.out.println("Enter Second Number: ");
            double num2 = scanner.nextDouble();

            if (operation == '/' && num2 == 0.0) {
                System.err.println("Second Number cannot be zero for Division operation.");
            }

            if (operation == '+') {
                System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
            } else if (operation == '-') {
                System.out.println(num1 + " - " + num2 + " = " + (num1 - num2));
            } else if (operation == '*' || operation == 'x') {
                System.out.println(num1 + " x " + num2 + " = " + (num1 * num2));
            } else if (operation == '/') {
                System.out.println(num1 + " / " + num2 + " = " + (num1 / num2));
            } else {
                System.err.println("Invalid Operator Specified.");
            }
        } catch (InputMismatchException exc) {
            System.err.println(exc.getMessage());
        } finally {
            scanner.close();
        }
    }
}