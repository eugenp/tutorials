package com.baeldung.decimaltofraction;

import org.apache.commons.math3.fraction.Fraction;

public class DecimalToFraction {

    public static String convertDecimalToFractionUsingMultiplyingWithPowerOf10(double decimal) {
        String decimalStr = String.valueOf(decimal);
        int decimalPlaces = decimalStr.length() - decimalStr.indexOf('.') - 1;
        long denominator = (long) Math.pow(10, decimalPlaces);
        long numerator = (long) (decimal * denominator);
        return numerator + "/" + denominator;
    }

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    public static String convertDecimalToFractionUsingGCD(double decimal) {
        String decimalStr = String.valueOf(decimal);
        int decimalPlaces = decimalStr.length() - decimalStr.indexOf('.') - 1;
        long denominator = (long) Math.pow(10, decimalPlaces);
        long numerator = (long) (decimal * denominator);
        long gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return numerator + "/" + denominator;
    }

    public static String convertDecimalToFractionUsingApacheCommonsMath(double decimal) {
        Fraction fraction = new Fraction(decimal);
        return fraction.toString();
    }

    private static String extractRepeatingDecimal(String fractionalPart) {
        int length = fractionalPart.length();
        for (int i = 1; i <= length / 2; i++) {
            String sub = fractionalPart.substring(0, i);
            boolean repeating = true;
            for (int j = i; j + i <= length; j += i) {
                if (!fractionalPart.substring(j, j + i)
                    .equals(sub)) {
                    repeating = false;
                    break;
                }
            }
            if (repeating) {
                return sub;
            }
        }
        return "";
    }

    public static String convertDecimalToFractionUsingGCDRepeating(double decimal) {
        String decimalStr = String.valueOf(decimal);
        int indexOfDot = decimalStr.indexOf('.');
        String afterDot = decimalStr.substring(indexOfDot + 1);
        String repeatingNumber = extractRepeatingDecimal(afterDot);
        if (repeatingNumber == "") {
            return convertDecimalToFractionUsingGCD(decimal);
        } else {
            int n = repeatingNumber.length();
            int repeatingValue = Integer.parseInt(repeatingNumber);
            int integerPart = Integer.parseInt(decimalStr.substring(0, indexOfDot));
            long denominator = (long) Math.pow(10, n) - 1;
            long numerator = repeatingValue + (integerPart * denominator);

            long gcd = gcd(numerator, denominator);
            numerator /= gcd;
            denominator /= gcd;
            return numerator + "/" + denominator;
        }
    }
}
