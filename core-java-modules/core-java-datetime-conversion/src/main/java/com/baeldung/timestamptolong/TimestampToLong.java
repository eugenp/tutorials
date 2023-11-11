package com.baeldung.timestamptolong;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimestampToLong {
    public void usingSimpleDateFormat() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        long actualTimestamp = sdf.parse(currentDateString).getTime();
    }

    public void usingInstantClass() {
        Instant instant = Instant.now();
        long actualTimestamp = instant.toEpochMilli();
    }

    public void usingTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long actualTimestamp = timestamp.getTime();
    }

    public void usingJava8DateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long actualTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}