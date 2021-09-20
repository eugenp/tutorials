package com.baeldung.accentsanddiacriticsremoval;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.StringJoiner;

class StringNormalizer {

    static String removeAccentsWithApacheCommons(String input) {
        return StringUtils.stripAccents(input);
    }

    static String removeAccents(String input) {
        return normalize(input).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    static String unicodeValueOfNormalizedString(String input) {
        return toUnicode(normalize(input));
    }

    private static String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input, Normalizer.Form.NFD);
    }

    private static String toUnicode(String input) {
        if (input.length() == 1) {
            return toUnicode(input.charAt(0));
        } else {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (char c : input.toCharArray()) {
                stringJoiner.add(toUnicode(c));
            }
            return stringJoiner.toString();
        }
    }

    private static String toUnicode(char input) {

        String hex = Integer.toHexString(input);
        StringBuilder sb = new StringBuilder(hex);

        while (sb.length() < 4) {
            sb.insert(0, "0");
        }
        sb.insert(0, "\\u");
        return sb.toString();
    }
}
