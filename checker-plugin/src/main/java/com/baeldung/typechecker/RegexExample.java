package com.baeldung.typechecker;

import org.checkerframework.checker.regex.qual.Regex;

public class RegexExample {

    @Regex String findNumbers() {
        return "\\D*";
    }

    public static void main(String[] args) {

        String message = "My phone number is +3911223344.";


    }

}
