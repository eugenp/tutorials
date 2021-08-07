package com.baeldung.emailvalidation;

import java.util.regex.Pattern;

public class EmailValidation {

    private static String regexPattern;

    public static boolean usingSimpleRegex(String emailAddress) {
        regexPattern = "^(.+)@(\\S+)$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }

    public static boolean usingStrictRegex(String emailAddress) {
        regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }

    public static boolean usingUnicodeRegex(String emailAddress) {
        regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }

    public static boolean usingRFC5322Regex(String emailAddress) {
        regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }

    public static boolean restrictDots(String emailAddress) {
        regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@" + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }

    public static boolean owaspValidation(String emailAddress) {
        regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }

    public static boolean topLevelDomain(String emailAddress) {
        regexPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }
}