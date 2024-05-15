package com.baeldung.twelvehourstotwentyhours;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class TimeConversionUnitTest {

    @Test
    public void givenTimeInTwelveHours_whenConvertingToTwentyHours_thenConverted() throws ParseException {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = parseFormat.parse("06:00 PM");
        assertEquals("18:00", displayFormat.format(date));
    }

    @Test
    public void givenTimeInTwelveHours_whenConvertingToTwentyHoursWithDateTimeFormatter_thenConverted() throws ParseException {
        String time = LocalTime.parse("06:00 PM", DateTimeFormatter.ofPattern("hh:mm a", Locale.US))
          .format(DateTimeFormatter.ofPattern("HH:mm"));
        assertEquals("18:00", time);
    }

}
