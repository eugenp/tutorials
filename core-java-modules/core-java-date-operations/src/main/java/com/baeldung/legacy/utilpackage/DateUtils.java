package com.baeldung.legacy.utilpackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date getNow() {
        return new Date();
    }

    public static Date getDate(long millis) {
        return new Date(millis);
    }

    public static Date getDate(String dateAsString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateAsString);
    }
}