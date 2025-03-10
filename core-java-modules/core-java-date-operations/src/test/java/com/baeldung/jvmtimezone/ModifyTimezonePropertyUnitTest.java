package com.baeldung.jvmtimezone;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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