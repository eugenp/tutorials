package com.baeldung.core.operators.notoperator;

/**
 * Examples used in the article `Using the Not Operator in If Conditions in Java`.
 */
public class NotOperator {

    public static void ifElseStatementExample() {
        boolean isValid = true;

        if (isValid) {
            System.out.println("Valid");
        } else {
            System.out.println("Invalid");
        }
    }

    public static void checkIsValidIsFalseWithEmptyIfBlock() {
        boolean isValid = true;

        if (isValid) {

        } else {
            System.out.println("Invalid");
        }
    }

    public static void checkIsValidIsFalseWithJustTheIfBlock() {
        boolean isValid = true;

        if (isValid == false) {
            System.out.println("Invalid");
        }
    }

    public static void checkIsValidIsFalseWithTheNotOperator() {
        boolean isValid = true;

        if (!isValid) {
            System.out.println("Invalid");
        }
    }

    public static void notOperatorWithBooleanValueAsOperand() {
        System.out.println(!true);   // prints false
        System.out.println(!false);  // prints true
        System.out.println(!!false); // prints false
    }

    public static void applyNotOperatorToAnExpression_example1() {
        int count = 2;

        System.out.println(!(count > 2));  // prints true
        System.out.println(!(count <= 2)); // prints false
    }

    public static void applyNotOperatorToAnExpression_LogicalOperators() {
        boolean x = true;
        boolean y = false;

        System.out.println(!(x && y));  // prints true
        System.out.println(!(x || y));  // prints false
    }

    public static void precedence_example() {
        boolean x = true;
        boolean y = false;

        System.out.println(!x && y);   // prints false
        System.out.println(!(x && y)); // prints true
    }

    public static void pitfalls_ComplexConditionsExample() {
        int count = 9;
        int total = 100;

        if (!(count >= 10 || total >= 1000)) {
            System.out.println("Some more work to do");
        }
    }

    public static void pitfalls_simplifyComplexConditionsByReversingLogicExample() {
        int count = 9;
        int total = 100;

        if (count < 10 && total < 1000) {
            System.out.println("Some more work to do");
        }
    }

    public static void exitEarlyExample() {
        boolean isValid = false;

        if(!isValid) {
            throw new IllegalArgumentException("Invalid input");
        }
        // Code to execute when isValid == true goes here
    }
}