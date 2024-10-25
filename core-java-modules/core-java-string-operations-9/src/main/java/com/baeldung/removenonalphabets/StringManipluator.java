package com.baeldung.removenonalphabets;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class StringManipluator {
    public static String removeNonAlphabeticUsingRegex(String input) {
        return input.replaceAll("[^a-zA-Z]", "");
    }

    public static String removeNonAlphabeticUsingStreams(String input) {
        return input.chars()
          .filter(Character::isLetter)
          .mapToObj(c -> String.valueOf((char) c))
          .collect(Collectors.joining());
    }

    public static String removeNonAlphabeticUsingStringBuilder(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String removeNonAlphabeticWithApacheCommons(String input) {
        return StringUtils.replacePattern(input, "[^a-zA-Z]", "");
    }
}

