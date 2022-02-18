package com.baeldung.emailvalidation;

import java.util.regex.Pattern;

public class EmailValidation {

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }
}
