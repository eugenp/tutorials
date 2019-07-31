package com.baeldung.incrementdecrementunaryoperator;

public class IncrementDecrementUnaryOperators {

    public static void main(String[] args) {

        int operand, number;

        // Pre-Increment Unary Operator
        operand = 1;
        ++operand;
        System.out.println("operand = " + operand); // operand = 2
        number = ++operand;
        System.out.println("operand = " + operand + ", number = " + number); // operand = 3, number = 3

        // Pre-Decrement Unary Operator
        operand = 2;
        --operand;
        System.out.println("operand = " + operand); // operand = 1
        number = --operand;
        System.out.println("operand = " + operand + ", number = " + number); // operand = 0, number = 0

        // Post-Increment Unary Operator
        operand = 1;
        operand++;
        System.out.println("operand = " + operand); // operand = 2
        number = operand++;
        System.out.println("operand = " + operand + ", number = " + number); // operand = 3, number = 2

        // Post-Decrement Unary Operator
        operand = 2;
        operand--;
        System.out.println("operand = " + operand); // operand = 1
        number = operand--;
        System.out.println("operand = " + operand + ", number = " + number); // operand = 0, number = 1
    }
}
