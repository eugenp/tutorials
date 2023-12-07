package com.baeldung.timestamp;

import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class CurrentTimeAsFileNameUnitTest {
    private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
    private static final SimpleDateFormat SIMPLEDATE_FORMATTER = new SimpleDateFormat(TIMESTAMP_FORMAT);

    private String getFileName(String currentTime) {
        return MessageFormat.format("{0}.txt", currentTime);
    }

    private boolean verifyFileName(String fileName) {
        return Pattern
          .compile("[0-9]{14}+\\.txt", Pattern.CASE_INSENSITIVE)
          .matcher(fileName)
          .matches();
    }

    @Test
    public void whenUsingCalender_thenGetCurrentTime() {
        String currentTime = SIMPLEDATE_FORMATTER.format(Calendar.getInstance().getTime());
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingDate_thenGetCurrentTime() {
        String currentTime = SIMPLEDATE_FORMATTER.format(new Date());
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingLocalDateTime_thenGetCurrentTime() {
        String currentTime = LocalDateTime.now().format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingZonedDateTime_thenGetCurrentTime() {
        String currentTime = ZonedDateTime
          .now(ZoneId.of("Europe/Paris"))
          .format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingOffsetDateTime_thenGetCurrentTime() {
        String currentTime = OffsetDateTime
          .of(LocalDateTime.now(), ZoneOffset.of("+02:00"))
          .format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingInstant_thenGetCurrentTime() {
        String currentTime = Instant
          .ofEpochMilli(System.currentTimeMillis())
          .truncatedTo(ChronoUnit.SECONDS)
          .toString()
          .replaceAll("[:TZ-]", "");
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingInstantWithZone_thenGetCurrentTime() {
        String currentTime = Instant
          .now()
          .atZone(ZoneId.of("Europe/Paris"))
          .format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingJodaTime_thenGetCurrentTime() {
        String currentTime = DateTime.now().toString(TIMESTAMP_FORMAT);
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingJodaTimeInstant_thenGetCurrentTime() {
        String currentTime = DateTimeFormat
          .forPattern(TIMESTAMP_FORMAT)
          .print(org.joda.time.Instant.now().toDateTime());
        String fileName = getFileName(currentTime);
        assertTrue(verifyFileName(fileName));
    }
}
