package com.baeldung.stringmaxlength;

public class StringMaxLengthMain {

    public static void main(String[] args) {
        displayRuntimeMaxStringLength();
        displayMaxStringLength();
        simulateStringOverflow();
    }

    public static void simulateStringOverflow() {
        try {
            int maxLength = Integer.MAX_VALUE;
            char[] charArray = new char[maxLength];
            for (int i = 0; i < maxLength; i++) {
                charArray[i] = 'a';
            }
            String longString = new String(charArray);
            System.out.println("Successfully created a string of length: " + longString.length());
        } catch (OutOfMemoryError e) {
            System.err.println("Overflow error: Attempting to create a string longer than Integer.MAX_VALUE");
            e.printStackTrace();
        }
    }

    public static void displayRuntimeMaxStringLength() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("Maximum String length based on available memory: " + (maxMemory));
    }

    public static void displayMaxStringLength() {
        int maxStringLength = Integer.MAX_VALUE;
        System.out.println("Maximum String length based on Integer.MAX_VALUE: " + maxStringLength);
    }
}