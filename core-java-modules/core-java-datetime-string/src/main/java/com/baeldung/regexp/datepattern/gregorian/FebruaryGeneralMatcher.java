package com.baeldung.regexp.datepattern.gregorian;

import com.baeldung.regexp.datepattern.DateMatcher;

import java.util.regex.Pattern;

public class FebruaryGeneralMatcher implements DateMatcher {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$");

    @Override
    public boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
