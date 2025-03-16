package com.baeldung.jvmtimezone;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

public class ModifyDefaultTimezoneUnitTest {

    @Test
    public void givenDefaultTimezoneSet_thenDateTimezoneIsCorrect() {
        TimeZone.setDefault(TimeZone.getTimeZone("Portugal"));
        Calendar calendar = Calendar.getInstance();
        assertEquals(calendar.getTimeZone(), TimeZone.getTimeZone("Portugal"));
    }

}
