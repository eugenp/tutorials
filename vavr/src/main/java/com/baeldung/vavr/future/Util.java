package com.baeldung.vavr.future;

public class Util {

    public static String appendData(String initial) {
        return initial + "Baeldung!";
    }

    public static int divideByZero(int num) {
        return num / 0;
    }

    public static String getSubstringMinusOne(String s) {
        return s.substring(-1);
    }

    public static String getSubstringMinusTwo(String s) {
        return s.substring(-2);
    }
}
