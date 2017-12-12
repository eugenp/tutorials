package com.baeldung.regexp.datepattern.validator;

import java.util.regex.Pattern;

class PreciseDateValidator implements DateValidator {

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$" 
            + "|^(((19|2[0-9])\\d{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|2[0-9])\\d{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" 
            + "|^(((19|2[0-9])\\d{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

    public boolean validate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
