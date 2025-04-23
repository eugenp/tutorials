package com.baeldung.minusoperation;

import java.util.stream.Collectors;

/**
 * Container for all implementations of what could be considered a 'String-minus-operations'
 */
public class StringMinusOperations {

    public static String removeLastCharBySubstring(String sentence) {
        return sentence.substring(0, sentence.length() - 1);
    }

    public static String minusByReplace(String sentence, char removeMe) {
        return sentence.replace(String.valueOf(removeMe), "");
    }

    public static String minusByReplace(String sentence, String removeMe) {
        return sentence.replace(removeMe, "");
    }

    public static String minusByStream(String sentence, char removeMe) {
        return sentence.chars()
            .mapToObj(c -> (char) c)
            .filter(it -> !it.equals(removeMe))
            .map(String::valueOf)
            .collect(Collectors.joining());
    }
}
