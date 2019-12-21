package com.baeldung.timezone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class ModifyTimezonePropertyUnitTest {

    @Before
    public void setup() {
        System.setProperty("user.timezone", "IST");
        TimeZone.setDefault(null);
    }

    @After
    public void teardown() {
        System.clearProperty("user.timezone");
    }

    @Test
    public void givenTimezonePropertySet_thenDateTimezoneIsCorrect() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(calendar.getTimeZone(), TimeZone.getTimeZone("IST"));
    }

}