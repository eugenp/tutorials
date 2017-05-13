package com.baeldung.string;

import java.util.Optional;

public class StringHelper {
    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? s : (s.substring(0, s.length() - 1));
    }

    public static String removeLastCharRegex(String s) {
        return (s == null) ? s : s.replaceAll(".$", "");
    }

    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
            .filter(str -> str.length() != 0)
            .map(str -> str.substring(0, str.length() - 1))
            .orElse(s);
    }

    public static String removeLastCharRegexOptional(String s) {
        return Optional.ofNullable(s)
            .map(str -> str.replaceAll(".$", ""))
            .orElse(null);
    }
}
