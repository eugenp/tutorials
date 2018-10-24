package com.baeldung.datetime;

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
        instant = timestamp.toInstant();

        System.out.println("Instant (milliseconds from epoch): " + instant.toEpochMilli());
        System.out.println("Timestamp (milliseconds from epoch): " + timestamp.getTime());
        System.out.print("\n");

        // Local TimeZone of the machine at the time of running this code is GMT +5:30 a.k.a IST
        ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("IST"));

        DateFormat df = DateFormat.getDateTimeInstance();
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Instant (in UTC): " + instant);
        System.out.println("Timestamp (in UTC): " + df.format(timestamp));
        System.out.println("Instant (in GMT +05:30): " + instant.atZone(zoneId));
        System.out.println("Timestamp (in GMT +05:30): " + timestamp);
    }
}