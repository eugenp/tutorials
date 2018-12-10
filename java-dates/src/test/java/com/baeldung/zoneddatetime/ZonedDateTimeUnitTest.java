package com.baeldung.zoneddatetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.junit.Test;

public class ZonedDateTimeUnitTest {

    private static final Logger log = Logger.getLogger(ZonedDateTimeUnitTest.class.getName());

    @Test
    public void testZonedDateTimeToString() {

        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime zonedDateTimeOf = ZonedDateTime.of(2018, 01, 01, 0, 0, 0, 0, ZoneId.of("UTC"));

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - hh:mm:ss Z");
        String formattedString = zonedDateTime.format(formatter);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy - hh:mm:ss z");
        String formattedString2 = zonedDateTime.format(formatter2);

        log.info(formattedString);
        log.info(formattedString2);

    }

    @Test
    public void testZonedDateTimeFromString() {

        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2011-12-03T10:15:30+01:00", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        log.info(zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }

}
