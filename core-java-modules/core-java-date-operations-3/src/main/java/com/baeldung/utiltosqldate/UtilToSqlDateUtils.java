package com.baeldung.utiltosqldate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

public class UtilToSqlDateUtils {

    public static java.util.Date createAmericanDate(String date) throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        return isoFormat.parse(date);
    }

    public static void switchTimezone(String timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    public static LocalDate getLocalDate(java.util.Date date, String timeZone) {
        return date.toInstant().atZone(ZoneId.of(timeZone)).toLocalDate();
    }
}
