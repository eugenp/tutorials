package com.baeldung.integertohex;

class IntegerToHex {
    static final String digits = "0123456789ABCDEF";
    static String integerToHex(int input) {
        if (input <= 0)
            return "0";
        StringBuilder hex = new StringBuilder();
        while (input > 0) {
            int digit = input % 16;
            hex.insert(0, digits.charAt(digit));
            input = input / 16;
        }
        return hex.toString();
    }
}
