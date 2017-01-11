package com.baeldung.chartoarray;

import java.util.Arrays;

public class CharArrayUtils {

    public static void main(String[] args) {
        String testString = "abcdefg";
        char[] testArray = { 'e', 'g', 'p' };

        System.out.println(testString + " to array => " + Arrays.toString(stringToCharArray(testString)));
        System.out.println(Arrays.toString(testArray) + " to string => " + charArrayToString(testArray));
    }

    public static char[] stringToCharArray(final String input) {
        if (null == input) return null;

        return input.toCharArray();
    }

    public static String charArrayToString(final char[] charArray) {
        if (null == charArray) return null;

        StringBuilder stringBuilder = new StringBuilder();

        for (char c : charArray) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }
}
