package com.baeldung.firstchardigit;

import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;

public class FirstCharDigit {

    public static boolean checkUsingCharAtMethod(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        char c = str.charAt(0);
        return c >= '0' && c <= '9';
    }

    public static boolean checkUsingIsDigitMethod(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return Character.isDigit(str.charAt(0));
    }

    public static boolean checkUsingPatternClass(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return Pattern.compile("^[0-9].*")
          .matcher(str)
          .matches();
    }

    public static boolean checkUsingMatchesMethod(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return str.matches("^[0-9].*");
    }

    public static boolean checkUsingCharMatcherInRangeMethod(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return CharMatcher.inRange('0', '9')
          .matches(str.charAt(0));
    }

    public static boolean checkUsingCharMatcherForPredicateMethod(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return CharMatcher.forPredicate(Character::isDigit)
          .matches(str.charAt(0));
    }

}
