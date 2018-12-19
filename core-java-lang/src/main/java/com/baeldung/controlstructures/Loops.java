package com.baeldung.controlstructures;

public class Loops {

    /**
     * Dummy method. Only prints a generic message.
     */
    private static void methodToRepeat() {
        System.out.println("Dummy method.");
    }

    /**
     * Shows how to iterate 50 times with 3 different method/control structures.
     */
    public static void repetitionTo50Examples() {
        for (int i = 1; i <= 50; i++) {
            methodToRepeat();
        }

        int whileCounter = 1;
        while (whileCounter <= 50) {
            methodToRepeat();
            whileCounter++;
        }

        int count = 1;
        do {
            methodToRepeat();
            count++;
        } while (count < 50);
    }

    /**
     * Prints text an N amount of times. Shows usage of the {@code break} branching statement.
     * @param textToPrint Text to repeatedly print.
     * @param times Amount to times to print received text.
     */
    public static void printTextNTimes(String textToPrint, int times) {
        int counter = 1;
        while (true) {
            System.out.println(textToPrint);
            if (counter == times) {
                break;
            }
        }
    }

    /**
     * Prints an specified amount of even numbers. Shows usage of both {@code break} and {@code continue} branching statements.
     * @param amountToPrint Amount of even numbers to print.
     */
    public static void printEvenNumbers(int amountToPrint) {
        if (amountToPrint <= 0) { // Invalid input
            return;
        }
        int iterator = 0;
        int amountPrinted = 0;
        while (true) {
            if (iterator % 2 == 0) { // Is an even number
                System.out.println(iterator);
                amountPrinted++;
                iterator++;
            } else {
                iterator++;
                continue; // Won't print
            }
            if (amountPrinted == amountToPrint) {
                break;
            }
        }
    }

}
