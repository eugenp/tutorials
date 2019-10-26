package com.baeldung.string;

import java.util.Optional;

class StringHelper {
    static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? s : (s.substring(0, s.length() - 1));
    }

    static String removeLastCharRegex(String s) {
        return (s == null) ? s : s.replaceAll(".$", "");
    }

    static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
            .filter(str -> str.length() != 0)
            .map(str -> str.substring(0, str.length() - 1))
            .orElse(s);
    }

    static String removeLastCharRegexOptional(String s) {
        return Optional.ofNullable(s)
            .map(str -> str.replaceAll(".$", ""))
            .orElse(s);
    }
}
