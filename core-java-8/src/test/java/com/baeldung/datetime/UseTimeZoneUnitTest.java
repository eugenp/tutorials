package com.baeldung.datetime;


import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UseTimeZoneUnitTest {

    String pattern = "yyyy-MM-dd HH:mm";
    String timeZone = "UTC";

    @Test
    public void setTimeZoneUsingJava7_success() {
        String time = UseTimeZone.setTimeZoneUsingJava7(pattern, timeZone);
        assertNotNull(time);
    }

    @Test
    public void setTimeZoneUsingJava8_success() {
        String time = UseTimeZone.setTimeZoneUsingJava8(pattern, timeZone);
        assertNotNull(time);
    }

    @Test
    public void setTimeZoneUsingJodaTime_success() {
        String time = UseTimeZone.setTimeZoneUsingJodaTime(pattern, timeZone);
        assertNotNull(time);
    }

}
