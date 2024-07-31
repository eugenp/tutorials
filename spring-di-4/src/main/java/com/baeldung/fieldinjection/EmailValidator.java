package com.baeldung.fieldinjection;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private static final String REGEX_PATTERN = "^(.+)@(\\S+)$";

    public boolean isValid(final String email) {
        return Pattern.compile(REGEX_PATTERN)
                .matcher(email)
                .matches();
    }
}
