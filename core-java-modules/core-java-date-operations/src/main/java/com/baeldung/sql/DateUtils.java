package com.baeldung.sql;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getDate(String dateAsString) {
        return Date.valueOf(dateAsString);
    }

    public static Date getDate(String dateAsString, String pattern) throws ParseException {
        java.util.Date customUtilDate = new SimpleDateFormat(pattern).parse(dateAsString);
        return new Date(customUtilDate.getTime());
    }
}