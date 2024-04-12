package com.baeldung.array.conversions;

import java.util.Arrays;

public class CharArrayToIntArrayUtils {

    static int[] usingGetNumericValueMethod(char[] chars) {
        if (chars == null) {
            return null;
        }

        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Character.getNumericValue(chars[i]);
        }

        return ints;
    }

    static int[] usingDigitMethod(char[] chars) {
        if (chars == null) {
            return null;
        }

        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Character.digit(chars[i], 10);
        }

        return ints;
    }

    static int[] usingStreamApiMethod(char[] chars) {
        if (chars == null) {
            return null;
        }

        return new String(chars).chars()
          .map(c -> c - 48)
          .toArray();
    }

    static int[] usingParseIntMethod(char[] chars) {
        if (chars == null) {
            return null;
        }

        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }

        return ints;
    }

    static int[] usingArraysSetAllMethod(char[] chars) {
        if (chars == null) {
            return null;
        }

        int[] ints = new int[chars.length];
        Arrays.setAll(ints, i -> Character.getNumericValue(chars[i]));

        return ints;
    }

}
