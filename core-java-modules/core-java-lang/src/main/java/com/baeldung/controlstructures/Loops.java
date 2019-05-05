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
     * Splits a sentence in words, and prints each word in a new line.
     * @param sentence Sentence to print as independent words.
     */
    public static void printWordByWord(String sentence) {
        for (String word : sentence.split(" ")) {
            System.out.println(word);
        }
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
     * Prints text an N amount of times, up to 50. Shows usage of the {@code break} branching statement.
     * @param textToPrint Text to repeatedly print.
     * @param times Amount to times to print received text. If times is higher than 50, textToPrint will only be printed 50 times.
     */
    public static void printTextNTimesUpTo50(String textToPrint, int times) {
        int counter = 1;
        while (counter < 50) {
            System.out.println(textToPrint);
            if (counter == times) {
                break;
            }
        }
    }

    /**
     * Finds the index of {@code name} in a list
     * @param name The name to look for
     * @param names The list of names
     * @return The index where the name was found or -1 otherwise
     */
    public static int findFirstInstanceOfName(String name, String[] names) {
        int index = 0;
        for ( ; index < names.length; index++) {
            if (names[index].equals(name)) {
                break;
            }
        }
        return index == names.length ? -1 : index;
    }

    /**
     * Takes several names and makes a list, skipping the specified {@code name}.
     *
     * @param name The name to skip
     * @param names The list of names
     * @return The list of names as a single string, missing the specified {@code name}.
     */
    public static String makeListSkippingName(String name, String[] names) {
        String list = "";
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(name)) {
                continue;
            }
            list += names[i];
        }
        return list;
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

    /**
     * Prints an specified amount of even numbers, up to 100. Shows usage of both {@code break} and {@code continue} branching statements.
     * @param amountToPrint Amount of even numbers to print.
     */
    public static void printEvenNumbersToAMaxOf100(int amountToPrint) {
        if (amountToPrint <= 0) { // Invalid input
            return;
        }
        int iterator = 0;
        int amountPrinted = 0;
        while (amountPrinted < 100) {
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
