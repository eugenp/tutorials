package com.baeldung.overflow;

import java.math.BigInteger;

public class Overflow {

    public static void showIntegerOverflow() {

        int value = Integer.MAX_VALUE-1;

        for(int i = 0; i < 4; i++, value++) {
            System.out.println(value);
        }
    }

    public static void noOverflowWithBigInteger() {

        BigInteger largeValue = new BigInteger(Integer.MAX_VALUE + "");
        for(int i = 0; i < 4; i++) {
            System.out.println(largeValue);
            largeValue = largeValue.add(BigInteger.ONE);
        }
    }

    public static void exceptionWithAddExact() {

        int value = Integer.MAX_VALUE-1;
        for(int i = 0; i < 4; i++) {
            System.out.println(value);
            value = Math.addExact(value, 1);
        }
    }

    public static int addExact(int x, int y) {

        int r = x + y;
        if (((x ^ r) & (y ^ r)) < 0) {
            throw new ArithmeticException("int overflow");
        }
        return r;
    }

    public static void demonstrateUnderflow() {

        for(int i = 1073; i <= 1076; i++) {
            System.out.println("2^" + i + " = " + Math.pow(2, -i));
        }
    }

    public static double powExact(double base, double exponent)
    {
        if(base == 0.0) {
            return 0.0;
        }

        double result = Math.pow(base, exponent);

        if(result == Double.POSITIVE_INFINITY ) {
            throw new ArithmeticException("Double overflow resulting in POSITIVE_INFINITY");
        } else if(result == Double.NEGATIVE_INFINITY) {
            throw new ArithmeticException("Double overflow resulting in NEGATIVE_INFINITY");
        } else if(Double.compare(-0.0f, result) == 0) {
            throw new ArithmeticException("Double overflow resulting in negative zero");
        } else if(Double.compare(+0.0f, result) == 0) {
            throw new ArithmeticException("Double overflow resulting in positive zero");
        }

        return result;
    }
}
