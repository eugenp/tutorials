package com.baeldung.arbitrarylengthbinaryintegers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryStringOperations {

    private static final Logger log = LoggerFactory.getLogger(BinaryStringOperations.class);

    public static void main(String[] args) {
        String a = "+110100101000";
        String b = "+1011";
        log.info(getBinaryOperations(a, b));
        a = "+110100101000";
        b = "-1011";
        log.info(getBinaryOperations(a, b));
        a = "-110100101000";
        b = "+1011";
        log.info(getBinaryOperations(a, b));
        a = "-110100101000";
        b = "-1011";
        log.info(getBinaryOperations(a, b));
        b = "+110100101000";
        a = "+1011";
        log.info(getBinaryOperations(a, b));
        b = "+110100101000";
        a = "-1011";
        log.info(getBinaryOperations(a, b));
        b = "-110100101000";
        a = "+1011";
        log.info(getBinaryOperations(a, b));
        b = "-110100101000";
        a = "-1011";
        log.info(getBinaryOperations(a, b));

    }

    public static String getBinaryOperations(String a, String b) {
        return "\na = " + a + "\n" + 
               "b = " + b + "\n" + 
               "a + b = " + add(a, b) + "\n" + 
               "a - b = " + subtract(a, b) + "\n" + 
               "a * b = " + multiply(a, b) +"\n" + 
               "a / b = " + divide(a, b) + "\n";
    }

    public static String add(String a, String b) {
        String unsignedA = removePlusMinus(a);
        String unsignedB = removePlusMinus(b);

        if (isPositive(a)) {
            if (isPositive(b)) {
                // Example: a=1011, b=1111
                return unsignedAdd(unsignedA, unsignedB);
            } else {
                // Example: a=1011, b=-1111
                return subtract(unsignedA, unsignedB);
            }
        } else {
            if (isPositive(b)) {
                // Example: a=-1011, b=1111
                return subtract(unsignedB, unsignedA);
            } else {
                // Example: a=-1011, b=-1111
                return '-' + unsignedAdd(unsignedA, unsignedB);
            }
        }
    }
    
    public static String unsignedAdd(String a, String b) {
        validateUnsignedBinaryString(a);
        validateUnsignedBinaryString(b);
        
        // Remove leading zeros
        a = a.replaceFirst("^0+(?!$)", "");
        b = b.replaceFirst("^0+(?!$)", "");

        int length = Math.max(a.length(), b.length());

        // Pad the shorter string with leading zeros to make both strings of equal length
        a = String.format("%" + length + "s", a).replace(' ', '0');
        b = String.format("%" + length + "s", b).replace(' ', '0');

        StringBuilder result = new StringBuilder(length * 2);
        boolean carry = false;

        // Iterate from the LSB (least significant bit) to the MSB (most significant bit)
        for (int i = length - 1; i >= 0; i--) {
            // Determine the bit values of the current position for both strings
            boolean v1 = (a.charAt(i) == '1');
            boolean v2 = (b.charAt(i) == '1');

            // Calculate the result bit for the current position considering the carry
            boolean r = carry && v1 && v2 || carry && !v1 && !v2 || !carry && v1 && !v2 || !carry && !v1 && v2;

            // Update the carry for the next iteration
            carry = carry && v1 || carry && v2 || v1 && v2;

            // Insert the result bit at the beginning of the result string
            result.insert(0, r ? '1' : '0');
        }

        // If there is a carry left, insert it at the beginning of the result string
        if (carry) {
            result.insert(0, '1');
        }

        return result.toString();
    }


    public static String subtract(String a, String b) {
        String unsignedA = removePlusMinus(a);
        String unsignedB = removePlusMinus(b);
        boolean isGreater = compareBinaryStrings(unsignedA, unsignedB) >= 0;

        if (isPositive(a)) {
            if (isPositive(b)) {
                if (isGreater) {
                    // Example: a=1011, b=101
                    return unsignedSubtract(unsignedA, unsignedB);
                } else {
                    // Example: a=101, b=1011
                    return '-' + unsignedSubtract(unsignedB, unsignedA);
                }
            } else {
                // Example: a=1011, b=-1011
                return unsignedAdd(unsignedA, unsignedB);
            }
        } else {
            if (isPositive(b)) {
                // Example: a=-1011, b=1011
                return '-' + unsignedAdd(unsignedA, unsignedB);
            } else {
                if (isGreater) {
                    // Example: a=-1011, b=-101
                    return '-' + unsignedSubtract(unsignedA, unsignedB);
                } else {
                    // Example: a=-101, b=-1011
                    return unsignedSubtract(unsignedB, unsignedA);
                }
            }
        }
    }
    
    private static String unsignedSubtract(String a, String b) {
        validateUnsignedBinaryString(a);
        validateUnsignedBinaryString(b);
        
        // If b is greater than a, throw an exception since subtraction would result in a negative value
        if (compareBinaryStrings(b, a) > 0) {
            throw new IllegalArgumentException("Cannot subtract: b is greater than a.");
        }
        
        // Remove leading zeros
        a = a.replaceFirst("^0+(?!$)", "");
        b = b.replaceFirst("^0+(?!$)", "");

        // Since a >= b, without leading zeros b.length() <= a.length()
        int length = a.length();

        // Pad "b" with leading zeros to make both strings of equal length
        b = String.format("%" + length + "s", b).replace(' ', '0');

        // Two's complement step 1: invert all the bits
        StringBuilder inverted = new StringBuilder();
        for (int i = 0; i < b.length(); i++) {
            char c = b.charAt(i);
            if (c == '0') {
                inverted.append('1');
            } else {
                inverted.append('0');
            }
        }
        
        // Two's complement step 2: add 1 to the inverted bits
        String b2complement = addOne(inverted.toString());
        
        // Executes the sum between a and the two's complement of b
        // Since a>=b, the result will always have an extra bit due to the carry out
        // We remove this extra bit by using substring(1)
        String result = unsignedAdd(a, b2complement).substring(1);
        
        // Remove leading zeros from the result
        result = result.replaceFirst("^0+(?!$)", "");

        // If the result is an empty string, it means the result is zero
        if (result.isEmpty()) {
            result = "0";
        }

        return result;

    }

    public static String multiply(String a, String b) {
        String unsignedA = removePlusMinus(a);
        String unsignedB = removePlusMinus(b);

        if (isPositive(a)) {
            if (isPositive(b)) {
                // Example: a=1011, b=1111
                return unsignedMultiply(unsignedA, unsignedB);
            } else {
                // Example: a=1011, b=-1111
                return '-' + unsignedMultiply(unsignedA, unsignedB);
            }
        } else {
            if (isPositive(b)) {
                // Example: a=-1011, b=1111
                return '-' + unsignedMultiply(unsignedA, unsignedB);
            } else {
                // Example: a=-1011, b=-1111
                return unsignedMultiply(unsignedA, unsignedB);
            }
        }
    }

    public static String divide(String a, String b) {
        String unsignedA = removePlusMinus(a);
        String unsignedB = removePlusMinus(b);

        if (isPositive(a)) {
            if (isPositive(b)) {
                // Example: a=1011, b=1111
                return unsignedDivide(unsignedA, unsignedB);
            } else {
                // Example: a=1011, b=-1111
                String result = unsignedDivide(unsignedA, unsignedB);
                if (result.equals("0")) {
                    // We treat the zero separately so that it does not return -0
                    return result;
                }
                return '-' + result;
            }
        } else {
            if (isPositive(b)) {
                // Example: a=-1011, b=1111
                String result = unsignedDivide(unsignedA, unsignedB);
                if (result.equals("0")) {
                    // We treat the zero separately so that it does not return -0
                    return result;
                }
                return '-' + result;
            } else {
                // Example: a=-1011, b=-1111
                return unsignedDivide(unsignedA, unsignedB);
            }
        }
    }
    
    private static void validateUnsignedBinaryString(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("The string is empty.");
        }
        if (!s.matches("[01]*")) {
            throw new IllegalArgumentException("The string contains invalid characters. Only '0' and '1' are allowed.");
        }
    }
    
    private static int compareBinaryStrings(String a, String b) {
        // Remove leading zeros
        a = a.replaceFirst("^0+(?!$)", "");
        b = b.replaceFirst("^0+(?!$)", "");
        
        // Compare lengths
        if (a.length() > b.length()) {
            return 1;
        } else if (a.length() < b.length()) {
            return -1;
        }
        
        // Compare digit by digit
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.charAt(i) - b.charAt(i);
            }
        }
        return 0; // They are equal
    }
    
    private static String addOne(String binary) {
        StringBuilder result = new StringBuilder(binary);
        int length = result.length();

        // Start from the LSB (Least Significant Bit) and add 1
        for (int i = length - 1; i >= 0; i--) {
            if (result.charAt(i) == '0') {
                result.setCharAt(i, '1');
                return result.toString();
            } else {
                result.setCharAt(i, '0');
            }
        }

        // If all bits were inverted (e.g., "111" becomes "000"), add 1 as MSB (Most Significant Bit)
        return "1" + result.toString();
    }
    
    private static String unsignedMultiply(String a, String b) {
        validateUnsignedBinaryString(a);
        validateUnsignedBinaryString(b);
        
        // Remove leading zeros
        a = a.replaceFirst("^0+(?!$)", "");
        b = b.replaceFirst("^0+(?!$)", "");
        
        String result = "0";
        int zeros = -1;
        
        // Iterate from the LSB (least significant bit) to the MSB (most significant bit) of "b"
        for (int i = b.length() - 1; i >= 0; i--) {
            zeros++;
            if (b.charAt(i) == '1') {
                // Calculate the partial product by appending the necessary number of zeros to "a"
                // and this partial product is added to the result
                result = unsignedAdd(result, appendZeros(a, zeros));
            }
        }
        
        return result;
    }
    
    private static String appendZeros(String a, int zeros) {
        StringBuilder sb = new StringBuilder(a);
        for (int i = 0; i < zeros; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
    
    private static String unsignedDivide(String a, String b) {
        validateUnsignedBinaryString(a);
        validateUnsignedBinaryString(b);
        
        if (compareBinaryStrings(b, a) > 0) {
            return "0";
        }

        // Remove leading zeros
        a = a.replaceFirst("^0+(?!$)", "");
        b = b.replaceFirst("^0+(?!$)", "");
        
        if (b.equals("0")) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        
        StringBuilder result = new StringBuilder(a.length());
        String remainder = "";
        
        // Iterate through each bit of the dividend "a" from MSB to LSB
        for (int i = 0; i < a.length(); i++) {
            if (result.length() == 0) {
                // Initialize result and remainder if not yet done
                if (compareBinaryStrings(a.substring(0, i + 1), b) >= 0) {
                    result.append('1');
                    remainder = unsignedSubtract(a.substring(0, i + 1), b);
                }
            } else {
                // Concatenate the current bit of "a" to the remainder
                remainder += a.charAt(i);
                // Compare the current remainder with the divisor "b"
                if (compareBinaryStrings(remainder, b) >= 0) {
                    // If remainder is greater than or equal to divisor, append '1' to result
                    result.append('1');
                    // Subtract divisor "b" from remainder
                    remainder = unsignedSubtract(remainder, b);
                } else {
                    // If remainder is less than divisor, append '0' to result
                    result.append('0');
                }
            }
        }

        return result.toString();
    }
    
    private static String removePlusMinus(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("The string cannot be null or empty");
        }
        if (s.startsWith("+") || s.startsWith("-")) {
            return s.substring(1);
        }
        return s;
    }
    
    private static boolean isPositive(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null or empty");
        }

        char firstChar = s.charAt(0);

        if (firstChar == '+' || firstChar == '0' || firstChar == '1') {
            return true;
        } else if (firstChar == '-') {
            return false;
        } else {
            throw new IllegalArgumentException("String does not start with a valid character");
        }
    }
}
