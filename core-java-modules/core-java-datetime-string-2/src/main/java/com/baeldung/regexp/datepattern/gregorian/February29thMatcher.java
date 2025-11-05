package com.baeldung.regexp.datepattern.gregorian;

import java.util.regex.Pattern;

import com.baeldung.regexp.datepattern.DateMatcher;

public class February29thMatcher implements DateMatcher {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$");

    @Override
    public boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
