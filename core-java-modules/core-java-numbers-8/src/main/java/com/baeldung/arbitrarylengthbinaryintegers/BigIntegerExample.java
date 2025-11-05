package com.baeldung.arbitrarylengthbinaryintegers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class BigIntegerExample {

    private static final Logger log = LoggerFactory.getLogger(BigIntegerExample.class);

    public static void main(String[] args) {
        BigInteger a = new BigInteger("1101001011010101010101010101010101010101010101010101010101010101010", 2);
        BigInteger b = new BigInteger("-10000", 2);

        log.info(getBinaryOperations(a, b));

        BigInteger c = new BigInteger("11111111111111111111111111111000", 2);
        log.info("c = " + c.toString(2) + " (" + c + ")");
    }

    public static String getBinaryOperations(BigInteger a, BigInteger b) {
        return "a = " + a.toString(2) + " (" + a + ")\n" +
               "b = " + b.toString(2) + " (" + b + ")\n" +
               "a + b = " + a.add(b).toString(2) + " (" + a.add(b) + ")\n" +
               "a - b = " + a.subtract(b).toString(2) + " (" + a.subtract(b) + ")\n" +
               "a * b = " + a.multiply(b).toString(2) + " (" + a.multiply(b) + ")\n" +
               "a / b = " + a.divide(b).toString(2) + " (" + a.divide(b) + ")";
    }

    public static BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    public static BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    public static BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public static BigInteger divide(BigInteger a, BigInteger b) {
        return a.divide(b);
    }
}