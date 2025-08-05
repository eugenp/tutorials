package com.baeldung.instantandlong;


import org.junit.Test;
import static org.junit.Assert.assertEquals;//&&&&//

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class InstantAndLongUnitTest {

    private long someDayLong = 1_753_610_399_076L;
    private long oneDayLong = 86_400_000L;
    private String stringDate = "2025-01-30T17:33:21";
    //2025.01.30 17:33:21 in milliseconds, Java epoch
    private long dayInMillis = ((((2025 - 1970) * 365 + 29 + 14) * 24 // days, including an additional day for each of the 14 leap years
                                + 17) * 3600  //to seconds
                                + 33*60 + 21) //add minutes and seconds
                                * 1000L;      //to milliseconds

    @Test
    public void whenPlusMillis_thenNextDay() {
        Instant someDay = Instant.ofEpochMilli(someDayLong);
        Instant nextDay = someDay.plusMillis(oneDayLong);

        assertEquals(nextDay.toEpochMilli(), someDayLong + oneDayLong);
    }

    @Test
    public void whenPlus_thenNextDay() {
        Instant someDay = Instant.ofEpochMilli(someDayLong);
        Instant nextDay = someDay.plus(oneDayLong, ChronoUnit.MILLIS);

        assertEquals(nextDay.toEpochMilli(), someDayLong + oneDayLong);
    }

    @Test
    public void whenMinusMillis_thenPreviousDay() {
        Instant someDay = Instant.ofEpochMilli(someDayLong);
        Instant previousDay = someDay.minusMillis(oneDayLong);

        assertEquals(previousDay.toEpochMilli(), someDayLong - oneDayLong);
    }

    @Test
    public void whenMinus_thenPreviousDay() {
        Instant someDay = Instant.ofEpochMilli(someDayLong);
        Instant previousDay = someDay.minus(oneDayLong, ChronoUnit.MILLIS);

        assertEquals(previousDay.toEpochMilli(), someDayLong - oneDayLong);
    }

    @Test
    public void whenToEpochMilli_thenDaysInMillis() {
        LocalDateTime dateTime = LocalDateTime.parse(stringDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
        Instant instant = zonedDateTime.toInstant();

        assertEquals(instant.toEpochMilli(), dayInMillis);
    }

    @Test
    public void whenOfEpochMilli_thenDateTimeAsInstant() {
        LocalDateTime dateTime = LocalDateTime.parse(stringDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
        Instant instant = zonedDateTime.toInstant();

        assertEquals(Instant.ofEpochMilli(dayInMillis), instant);
    }

}

