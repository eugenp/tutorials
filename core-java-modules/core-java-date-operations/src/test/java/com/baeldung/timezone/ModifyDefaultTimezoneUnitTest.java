package com.baeldung.timezone;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class ModifyDefaultTimezoneUnitTest {

    @Test
    public void givenDefaultTimezoneSet_thenDateTimezoneIsCorrect() {
        TimeZone.setDefault(TimeZone.getTimeZone("Portugal"));
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(calendar.getTimeZone(), TimeZone.getTimeZone("Portugal"));
    }

}
