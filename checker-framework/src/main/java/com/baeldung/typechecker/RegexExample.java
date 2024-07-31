package com.baeldung.typechecker;

import org.checkerframework.checker.regex.qual.Regex;

public class RegexExample {

    // For some reason we want to be sure that this regex
    // contains at least one capturing group.
    // However, we do an error and we forgot to define such
    // capturing group in it.
    @Regex(1) private static String findNumbers = "\\d*";

    public static void main(String[] args) {
        String message = "My phone number is +3911223344.";
        message.matches(findNumbers);
    }

}
