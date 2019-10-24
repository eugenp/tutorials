package com.baeldung.sql;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

    public static Time getNow() {
        return new Time(System.currentTimeMillis());
    }

    public static Time getTime(String timeAsString) {
        return Time.valueOf(timeAsString);
    }

    public static Time getTime(String dateAsString, String pattern) throws ParseException {
        java.util.Date customUtilDate = new SimpleDateFormat(pattern).parse(dateAsString);
        return new Time(customUtilDate.getTime());
    }
}