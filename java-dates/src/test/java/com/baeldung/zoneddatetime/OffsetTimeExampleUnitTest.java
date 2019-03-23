package com.baeldung.zoneddatetime;

import static org.junit.Assert.assertTrue;

import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.Test;

public class OffsetTimeExampleUnitTest {

    OffsetTimeExample offsetTimeExample = new OffsetTimeExample();

    @Test
    public void givenZoneOffset_whenGetCurrentTime_thenResultHasZone() {
        String offset = "+02:00";
        OffsetTime time = offsetTimeExample.getCurrentTimeByZoneOffset(offset);

        assertTrue(time.getOffset()
            .equals(ZoneOffset.of(offset)));
    }
}
