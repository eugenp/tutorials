package com.baeldung.jvmtimezone;

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
        System.setProperty("user.timezone", "Asia/Kolkata");
        TimeZone.setDefault(null);
    }

    @After
    public void teardown() {
        System.clearProperty("user.timezone");
    }

    @Test
    public void givenTimezonePropertySet_thenDateTimezoneIsCorrect() {
        Calendar calendar = Calendar.getInstance();
        assertEquals(calendar.getTimeZone(), TimeZone.getTimeZone("Asia/Kolkata"));
    }

}