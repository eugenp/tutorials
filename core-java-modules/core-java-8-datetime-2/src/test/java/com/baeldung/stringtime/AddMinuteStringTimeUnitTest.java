package com.baeldung.stringtime;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class AddMinuteStringTimeUnitTest {

    @Test
    public void givenTimeStringUsingSimpleDateFormat_whenIncrementedWith10Minutes_thenResultShouldBeCorrect() throws ParseException {
        String timeString = "23:45";
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = timeFormat.parse(timeString);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 10);
        String result = timeFormat.format(cal.getTime());
        assertEquals("23:55", result);

    }

    @Test
    public void givenTimeStringUsingLocalTime_whenIncrementedWith10Minutes_thenResultShouldBeCorrect() {
        String timeString = "23:45";
        LocalTime time = LocalTime.parse(timeString);
        LocalTime newTime = time.plusMinutes(10);
        String result = newTime.toString();
        assertEquals("23:55", result);
    }

}
