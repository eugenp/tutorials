package com.baeldung.integerToBinary;

public class IntegerToBinary {
    public static String convertIntegerToBinary(int n) {
        if(n == 0) {
            return "0";
        }
        StringBuilder binaryNumber = new StringBuilder();
        while (n > 0) {
            int remainder = n % 2;
            binaryNumber.append(remainder);
            n /= 2;
        }
        binaryNumber = binaryNumber.reverse();
        return binaryNumber.toString();
    }
}
