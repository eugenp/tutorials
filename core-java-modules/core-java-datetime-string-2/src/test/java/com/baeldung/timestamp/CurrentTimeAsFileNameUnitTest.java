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

    static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
    static final SimpleDateFormat SIMPLEDATE_FORMAT = new SimpleDateFormat(TIMESTAMP_FORMAT);

    @Test
    public void whenUsingCalendar_thenCurrentTimeAsFileName() {
        String currentTime = SIMPLEDATE_FORMAT.format(Calendar.getInstance().getTime());
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingDate_thenCurrentTimeAsFileName() {
        String currentTime = SIMPLEDATE_FORMAT.format(new Date());
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingInstant_thenCurrentTimeAsFileName() {
        String currentTime = Instant
            .now()
            .truncatedTo(ChronoUnit.SECONDS)
            .toString()
            .replaceAll("[:TZ-]", "");
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingLocalDateTime_thenCurrentTimeAsFileName() {
        String currentTime = LocalDateTime.now().format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingZonedDateTime_thenCurrentTimeAsFileName() {
        String currentTime = ZonedDateTime
            .now(ZoneId.of("Europe/Paris"))
            .format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingOffsetDateTime_thenCurrentTimeAsFileName() {
        String currentTime = OffsetDateTime
            .of(LocalDateTime.now(), ZoneOffset.of("+01:00"))
            .format(DATETIME_FORMATTER);
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingJodaDateTime_thenCurrentTimeAsFileName() {
        String currentTime = DateTime.now().toString(TIMESTAMP_FORMAT);
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    @Test
    public void whenUsingJodaInstant_thenCurrentTimeAsFileName() {
        String currentTime = DateTimeFormat
            .forPattern(TIMESTAMP_FORMAT)
            .print(org.joda.time.Instant.now()
                .toDateTime());
        String fileName = getFileName(currentTime);

        assertTrue(verifyFileName(fileName));
    }

    String getFileName(String currentTime) {
        return MessageFormat.format("{0}.txt", currentTime);
    }

    boolean verifyFileName(String fileName) {
        return Pattern
            .compile("[0-9]{14}+\\.txt", Pattern.CASE_INSENSITIVE)
            .matcher(fileName)
            .matches();
    }
}
