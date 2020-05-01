package com.baeldung.regexp.datepattern.gregorian;

import com.baeldung.regexp.datepattern.DateMatcher;

import java.util.regex.Pattern;

public class MonthsOf30DaysMatcher implements DateMatcher {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

    @Override
    public boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

}
