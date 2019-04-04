package com.baeldung.controlstructures;

public class ConditionalBranches {

    /**
     * Multiple if/else/else if statements examples. Shows different syntax usage.
     */
    public static void ifElseStatementsExamples() {
        int count = 2; // Initial count value.

        // Basic syntax. Only one statement follows. No brace usage.
        if (count > 1)
            System.out.println("Count is higher than 1");

        // Basic syntax. More than one statement can be included. Braces are used (recommended syntax).
        if (count > 1) {
            System.out.println("Count is higher than 1");
            System.out.println("Count is equal to: " + count);
        }

        // If/Else syntax. Two different courses of action can be included.
        if (count > 2) {
            System.out.println("Count is higher than 2");
        } else {
            System.out.println("Count is lower or equal than 2");
        }

        // If/Else/Else If syntax. Three or more courses of action can be included.
        if (count > 2) {
            System.out.println("Count is higher than 2");
        } else if (count <= 0) {
            System.out.println("Count is less or equal than zero");
        } else {
            System.out.println("Count is either equal to one, or two");
        }
    }

    /**
     * Ternary Operator example.
     * @see ConditionalBranches#ifElseStatementsExamples()
     */
    public static void ternaryExample() {
        int count = 2;
        System.out.println(count > 2 ? "Count is higher than 2" : "Count is lower or equal than 2");
    }

    /**
     * Switch structure example. Shows how to replace multiple if/else statements with one structure.
     */
    public static void switchExample() {
        int count = 3;
        switch (count) {
        case 0:
            System.out.println("Count is equal to 0");
            break;
        case 1:
            System.out.println("Count is equal to 1");
            break;
        case 2:
            System.out.println("Count is equal to 2");
            break;
        default:
            System.out.println("Count is either negative, or higher than 2");
            break;
        }
    }

}
