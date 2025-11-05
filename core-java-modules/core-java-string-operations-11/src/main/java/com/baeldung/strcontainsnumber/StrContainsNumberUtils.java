package com.baeldung.strcontainsnumber;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CharMatcher;

public class StrContainsNumberUtils {

    static boolean checkUsingMatchesMethod(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        return input.matches(".*\\d.*");
    }

    static boolean checkUsingPatternClass(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        return Pattern.compile(".*\\d.*")
          .matcher(input)
          .matches();
    }

    static boolean checkUsingReplaceAllMethod(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String result = input.replaceAll("\\d", "");
        return result.length() != input.length();
    }

    static boolean checkUsingIsDigitMethod(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }

    static boolean checkUsingStreamApi(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        return input.chars()
          .anyMatch(Character::isDigit);
    }

    static boolean checkUsingApacheCommonsLang(String input) {
        String result = StringUtils.getDigits(input);
        return result != null && !result.isEmpty();
    }

    static boolean checkUsingGuava(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String result = CharMatcher.forPredicate(Character::isDigit)
          .retainFrom(input);

        return !result.isEmpty();
    }

}
