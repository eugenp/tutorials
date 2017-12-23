package com.baeldung.regexp.datepattern.validator;

import java.util.regex.Pattern;

class SimpleDateValidator implements DateValidator {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$");

    public boolean validate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
