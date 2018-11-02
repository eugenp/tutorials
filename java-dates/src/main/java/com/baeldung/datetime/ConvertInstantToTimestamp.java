package com.baeldung.datetime;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

public class ConvertInstantToTimestamp {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant); // same point on the time-line as Instant
        assertEquals(instant.toEpochMilli(), timestamp.getTime());

        instant = timestamp.toInstant();
        assertEquals(instant.toEpochMilli(), timestamp.getTime());

        DateFormat df = DateFormat.getDateTimeInstance();
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals(instant.toString(), df.format(timestamp).toString());
    }
}
