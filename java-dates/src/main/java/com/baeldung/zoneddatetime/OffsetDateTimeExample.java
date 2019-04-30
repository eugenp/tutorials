package com.baeldung.zoneddatetime;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeExample {

    public OffsetDateTime getCurrentTimeByZoneOffset(String offset) {
        ZoneOffset zoneOffSet= ZoneOffset.of(offset);
        OffsetDateTime date = OffsetDateTime.now(zoneOffSet);
        return date;
    }
}
