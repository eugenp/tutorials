package com.baeldung.timestamptolong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimestampToLong {

    public long usingSimpleDateFormat(String timestampString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(timestampString);
        String currentDateString = sdf.format(date);
        return sdf.parse(currentDateString).getTime();
    }

    public long usingInstantClass(String timestampString) {
        Instant instant = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return instant.toEpochMilli();
    }

    public long usingJava8DateTime(String timestampString) {
        LocalDateTime localDateTime = LocalDateTime.parse(timestampString.replace(" ", "T"));
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
