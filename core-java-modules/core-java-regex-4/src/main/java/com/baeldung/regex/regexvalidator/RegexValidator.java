package com.baeldung.regex.regexvalidator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexValidator {

    public static boolean isValid(String regex) {
        try {
            Pattern.compile(regex);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}
