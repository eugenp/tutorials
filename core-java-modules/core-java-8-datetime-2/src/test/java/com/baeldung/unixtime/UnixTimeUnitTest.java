package com.baeldung.unixtime;


import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Test;

public class UnixTimeUnitTest {

    @Test
    public void givenCurrentTimeUsingDateApi_whenConvertedToUnixTime_thenConsistent() {
        long marginOfError = 1L;
        Date date = new Date();
        long longTime = date.getTime() / 1000L;

        for (int i = 0; i < 10; i++) {
            long unixTime = System.currentTimeMillis() / 1000L;
            assertEquals(longTime, unixTime, marginOfError);
        }
    }

    @Test
    public void givenCurrentTimeUsingInstantNow_whenConvertedToUnixTime_thenConsistent() {
        long marginOfError = 1L;
        long longTime = Instant.now().getEpochSecond();

        for (int i = 0; i < 10; i++) {
            long unixTime = System.currentTimeMillis() / 1000L;
            assertEquals(longTime, unixTime, marginOfError);
        }
    }

    @Test
    public void givenCurrentTimeUsingJodaTime_whenConvertedToUnixTime_thenConsistent() {
        long marginOfError = 1L;
        DateTime dateTime = DateTime.now(DateTimeZone.UTC);
        long longTime = dateTime.getMillis() / 1000L;

        for (int i = 0; i < 10; i++) {
            long unixTime = System.currentTimeMillis() / 1000L;
            assertEquals(longTime, unixTime, marginOfError);
        }
    }

}
