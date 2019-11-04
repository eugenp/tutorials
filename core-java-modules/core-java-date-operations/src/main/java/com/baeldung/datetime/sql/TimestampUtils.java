package com.baeldung.datetime.sql;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampUtils {

    public static Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimestamp(String timestampAsString) {
        return Timestamp.valueOf(timestampAsString);
    }

    public static Timestamp getTimestamp(String dateAsString, String pattern) throws ParseException {
        java.util.Date customUtilDate = new SimpleDateFormat(pattern).parse(dateAsString);
        return new Timestamp(customUtilDate.getTime());
    }
}