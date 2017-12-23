package com.baeldung.regexp.datepattern.validator;

import java.util.regex.Pattern;

class RangedDateValidator implements DateValidator {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^((19|2[0-9])\\d{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");

    public boolean validate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
