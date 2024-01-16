package com.baeldung.zoneoffsetvszoneidof;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZoneOffSetAndZoneIdOf {
    public static void main(String[] args) {
        printDateTimeWithUTCZoneOffset();
        printZonedDateTimeWithUTCZoneId();
    }

    private static void printDateTimeWithUTCZoneOffset() {
        OffsetDateTime dateTimeWithOffset = OffsetDateTime.now(ZoneOffset.UTC);
        System.out.println("DateTime with UTC offset: " + dateTimeWithOffset);
    }

    public static void printZonedDateTimeWithUTCZoneId() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println("ZonedDateTime with UTC: " + zonedDateTime);
    }
}