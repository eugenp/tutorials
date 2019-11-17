package com.baeldung.maths;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BasicCalculatorSwitchCase {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------- \n"
            + "Welcome to Basic Calculator \n"
            + "----------------------------------");
        System.out.println("Following operations are supported : \n" +
                "1. Addition (+) \n" +
                "2. Subtraction (-) \n" +
                "3. Multiplication (* OR x) \n" +
                "4. Division (/) \n");
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

            switch (operation) {
                case '+':
                    System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
                    break;
                case '-':
                    System.out.println(num1 + " - " + num2 + " = " + (num1 - num2));
                    break;
                case '*':
                    System.out.println(num1 + " x " + num2 + " = " + (num1 * num2));
                    break;
                case 'x':
                    System.out.println(num1 + " x " + num2 + " = " + (num1 * num2));
                    break;
                case '/':
                    System.out.println(num1 + " / " + num2 + " = " + (num1 / num2));
                    break;
                default:
                    System.err.println("Invalid Operator Specified.");
                    break;
            }
        } catch (InputMismatchException exc) {
            System.err.println(exc.getMessage());
        }
        scanner.close();
    }
}

