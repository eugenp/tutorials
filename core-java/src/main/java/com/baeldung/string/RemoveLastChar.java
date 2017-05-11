package com.baeldung.string;

public class RemoveLastChar {
    public static String substring(String s) {
        if (s == null || s.length() == 0) {
            return s;
        } else {
            return (s.substring(0, s.length() - 1));
        }
    }

    public static String chop(String s) {
        if (s == null) {
            return s;
        } else {
            return s.replaceAll(".$", "");
        }
    }
}
