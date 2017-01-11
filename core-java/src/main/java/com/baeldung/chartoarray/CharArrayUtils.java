package com.baeldung.chartoarray;

import java.util.Arrays;

public class CharArrayUtils {

    public static void main(String[] args) {
        String testString = "abcdefg";
        char[] testArray = { 'e', 'g', 'p' };

        System.out.println(testString + " to array => " + Arrays.toString(stringToCharArray1(testString)));
        System.out.println(Arrays.toString(testArray) + " to string => " + charArrayToString1(testArray));
    }

    public static char[] stringToCharArray1(final String input) {
        if (null == input) return null;

        return input.toCharArray();
    }

    public static char[] stringToCharArray2(final String input) {
        if (null == input) return null;

        char[] charArray = new char[input.length()];

        for(int i = 0; i < input.length(); ++i)
            charArray[i] = input.charAt(i);

        return charArray;
    }

    public static String charArrayToString1(final char[] charArray) {
        if (null == charArray) return null;

        StringBuilder stringBuilder = new StringBuilder();

        for (char c : charArray) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public static String charArrayToString2(final char[] charArray) {
        if (null == charArray) return null;

        return String.valueOf(charArray);
    }
}
