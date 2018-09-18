package com.baeldung.zoneddatetime;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;

import org.junit.Test;

public class OffsetTimeExampleUnitTest {

    OffsetTimeExample offsetTimeExample = new OffsetTimeExample();

    @Test
    public void givenZoneOffset_whenGetCurrentTime_thenResultHasZone() {
        String zone = "Europe/Berlin";
        OffsetTime time = offsetTimeExample.getCurrentTimeByZoneOffset(zone);   

        assertTrue(time.getOffset()
            .equals(ZoneId.of(zone)
                .getRules()
                .getOffset(LocalDateTime.now())));
    }
}
