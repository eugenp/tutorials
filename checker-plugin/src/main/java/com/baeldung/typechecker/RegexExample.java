package com.baeldung.typechecker;

import org.checkerframework.checker.regex.qual.Regex;

public class RegexExample {

    @Regex(1) private static String findNumbers = "\\d*";

    public static void main(String[] args) {
        String message = "My phone number is +3911223344.";
        message.matches(findNumbers);
    }

}
