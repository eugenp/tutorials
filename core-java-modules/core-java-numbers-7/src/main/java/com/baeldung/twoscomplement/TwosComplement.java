package com.baeldung.twoscomplement;

import java.math.BigInteger;
import java.util.stream.Collectors;

public class TwosComplement {

    public static String decimalToTwosComplementBinary(BigInteger num, int numBits) {
        if (!canRepresentInNBits(num, numBits)) {
            throw new IllegalArgumentException(numBits + " bits is not enough to represent the number " + num);
        }
        var isNegative = num.signum() == -1;
        var absNum = num.abs();

        // Convert the abs value of the number to its binary representation
        String binary = absNum.toString(2);

        // Pad the binary representation with zeros to make it numBits long
        while (binary.length() < numBits) {
            binary = "0" + binary;
        }

        // If the input number is negative, calculate two's complement
        if (isNegative) {
            binary = performTwosComplement(binary);
        }

        return formatInNibbles(binary);
    }

    private static String performTwosComplement(String binary) {
        StringBuilder result = new StringBuilder();
        boolean carry = true;
        // Perform one's complement
        StringBuilder onesComplement = new StringBuilder();
        for (int i = binary.length() - 1; i >= 0; i--) {
            char bit = binary.charAt(i);
            onesComplement.insert(0, bit == '0' ? '1' : '0');
        }
        // Addition by 1
        for (int i = onesComplement.length() - 1; i >= 0; i--) {
            char bit = onesComplement.charAt(i);
            if (bit == '1' && carry) {
                result.insert(0, '0');
            } else if (bit == '0' && carry) {
                result.insert(0, '1');
                carry = false;
            } else {
                result.insert(0, bit);
            }
        }

        if (carry) {
            result.insert(0, '1');
        }

        return result.toString();
    }

    private static String formatInNibbles(String binary) {
        StringBuilder formattedBin = new StringBuilder();
        for (int i = 1; i <= binary.length(); i++) {
            if (i % 4 == 0 && i != binary.length()) {
                formattedBin.append(binary.charAt(i - 1)).append(" ");
            } else {
                formattedBin.append(binary.charAt(i - 1));
            }
        }
        return formattedBin.toString();
    }

    private static boolean canRepresentInNBits(BigInteger number, int numBits) {
        BigInteger minValue = BigInteger.ONE.shiftLeft(numBits - 1).negate(); // -2^(numBits-1)
        BigInteger maxValue = BigInteger.ONE.shiftLeft(numBits - 1).subtract(BigInteger.ONE); // 2^(numBits-1) - 1
        return number.compareTo(minValue) >= 0 && number.compareTo(maxValue) <= 0;
    }

    public static String decimalToTwosComplementBinaryUsingShortCut(BigInteger num, int numBits) {
        if (!canRepresentInNBits(num, numBits)) {
            throw new IllegalArgumentException(numBits + " bits is not enough to represent the number " + num);
        }
        var isNegative = num.signum() == -1;
        var absNum = num.abs();

        // Convert the abs value of the number to its binary representation
        String binary = absNum.toString(2);

        // Pad the binary representation with zeros to make it numBits long
        while (binary.length() < numBits) {
            binary = "0" + binary;
        }

        // If the input number is negative, calculate two's complement
        if (isNegative) {
            binary = performTwosComplementUsingShortCut(binary);
        }

        return formatInNibbles(binary);
    }

    private static String performTwosComplementUsingShortCut(String binary) {
        int firstOneIndexFromRight = binary.lastIndexOf('1');
        if (firstOneIndexFromRight == -1) {
            return binary;
        }
        String rightPart = binary.substring(firstOneIndexFromRight);
        String leftPart = binary.substring(0, firstOneIndexFromRight);
        String leftWithOnes = leftPart.chars().mapToObj(c -> c == '0' ? '1' : '0')
                .map(String::valueOf).collect(Collectors.joining(""));
        return leftWithOnes + rightPart;
    }

}



