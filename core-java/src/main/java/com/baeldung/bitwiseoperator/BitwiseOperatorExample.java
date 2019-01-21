package com.baeldung.bitwiseoperator;

public class BitwiseOperatorExample {

    public static void main(String[] args) {

        int value1 = 6;
        int value2 = 5;

        // Bitwise AND Operator
        int result = value1 & value2;
        System.out.println("result : " + result);

        // Bitwise OR Operator
        result = value1 | value2;
        System.out.println("result : " + result);

        // Bitwise Exclusive OR Operator
        result = value1 ^ value2;
        System.out.println("result : " + result);

        // Bitwise NOT operator
        result = ~value1;
        System.out.println("result : " + result);

        // Right Shift Operator with positive number
        int value = 12;
        int rightShift = value >> 2;
        System.out.println("rightShift result with positive number : " + rightShift);

        // Right Shift Operator with negative number
        value = -12;
        rightShift = value >> 2;
        System.out.println("rightShift result with negative number : " + rightShift);

        // Left Shift Operator with positive number
        value = 1;
        int leftShift = value << 1;
        System.out.println("leftShift result with positive number : " + leftShift);

        // Left Shift Operator with negative number
        value = -12;
        leftShift = value << 2;
        System.out.println("leftShift result with negative number : " + leftShift);

        // Unsigned Right Shift Operator with positive number
        value = 12;
        int unsignedRightShift = value >>> 2;
        System.out.println("unsignedRightShift result with positive number : " + unsignedRightShift);

        // Unsigned Right Shift Operator with negative number
        value = -12;
        unsignedRightShift = value >>> 2;
        System.out.println("unsignedRightShift result with negative number : " + unsignedRightShift);
    }
}
