package com.baeldung.convertnumberbases;

public class ConvertNumberBases {

    public static String convertNumberToNewBase(String number, int base, int newBase) {
        return Integer.toString(Integer.parseInt(number, base), newBase);
    }

    public static String convertNumberToNewBaseCustom(String num, int base, int newBase) {
        int decimalNumber = convertFromAnyBaseToDecimal(num, base);
        String targetBase = "";
        try {
            targetBase = convertFromDecimalToBaseX(decimalNumber, newBase);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return  targetBase;
    }

    public static String convertFromDecimalToBaseX(int num, int newBase) throws IllegalArgumentException {
        if ((newBase < 2 || newBase > 10) && newBase != 16) {
            throw new IllegalArgumentException("New base must be from 2 - 10 or 16");
        }

        String result = "";
        int remainder;
        while (num > 0) {
            remainder = num % newBase;
            if (newBase == 16) {
                if (remainder == 10) {
                    result += 'A';
                } else if (remainder == 11) {
                    result += 'B';
                } else if (remainder == 12) {
                    result += 'C';
                } else if (remainder == 13) {
                    result += 'D';
                } else if (remainder == 14) {
                    result += 'E';
                } else if (remainder == 15) {
                    result += 'F';
                } else {
                    result += remainder;
                }
            } else {
                result += remainder;
            }
            num /= newBase;
        }
        return new StringBuffer(result).reverse().toString();
    }

    public static int convertFromAnyBaseToDecimal(String num, int base) {
        if (base < 2 || (base > 10 && base != 16)) {
            return -1;
        }
        int val = 0;
        int power = 1;
        for (int i = num.length() - 1; i >= 0; i--) {
            int digit = charToDecimal(num.charAt(i));
            if (digit < 0 || digit >= base) {
                return -1;
            }
            val += digit * power;
            power = power * base;
        }
        return val;
    }

    public static int charToDecimal(char c) {
        if (c >= '0' && c <= '9') {
            return (int) c - '0';
        } else {
            return (int) c - 'A' + 10;
        }
    }
}
