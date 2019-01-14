package com.baeldung.zoneddatetime;

import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeExample {

    public OffsetTime getCurrentTimeByZoneOffset(String offset) {
        ZoneOffset zoneOffSet = ZoneOffset.of(offset);
        OffsetTime time = OffsetTime.now(zoneOffSet);
        return time;
    }
}
