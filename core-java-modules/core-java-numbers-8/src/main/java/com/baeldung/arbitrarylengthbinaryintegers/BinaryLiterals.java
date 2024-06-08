package com.baeldung.arbitrarylengthbinaryintegers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryLiterals {

    private static final Logger log = LoggerFactory.getLogger(BinaryLiterals.class);

    public static void main(String[] args) {
        int a = 0b110100101101010;
        int b = 0b1000;

        log.info(getBinaryOperations(a, b));

        int c = 0b11111111111111111111111111111000; // (2^32 - 8) written in base 2
        log.info("c = " + Integer.toBinaryString(c) + " (" + c + ")");
    }

    public static String getBinaryOperations(int a, int b) {
        return "a = " + Integer.toBinaryString(a) + " (" + a + ")\n" +
               "b = " + Integer.toBinaryString(b) + " (" + b + ")\n" +
               "a + b = " + Integer.toBinaryString(a + b) + " (" + (a + b) + ")\n" +
               "a - b = " + Integer.toBinaryString(a - b) + " (" + (a - b) + ")\n" +
               "a * b = " + Integer.toBinaryString(a * b) + " (" + (a * b) + ")\n" +
               "a / b = " + Integer.toBinaryString(a / b) + " (" + (a / b) + ")";
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int divide(int a, int b) {
        return a / b;
    }
}