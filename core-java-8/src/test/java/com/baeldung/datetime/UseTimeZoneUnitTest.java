package com.baeldung.datetime;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UseTimeZoneUnitTest {

    /* https://en.wikipedia.org/wiki/List_of_tz_database_time_zones */
    String timeZone = "Asia/Singapore";

    @Test
    public void setTimeZoneUsingJava7_success() {
        assertNotNull(UseTimeZone.setTimeZoneUsingJava7(timeZone));
    }

    @Test
    public void setTimeZoneUsingJava8_success() {
        assertNotNull(UseTimeZone.setTimeZoneUsingJava8(timeZone));
    }

    @Test
    public void setTimeZoneUsingJodaTime_success() {
        assertNotNull(UseTimeZone.setTimeZoneUsingJodaTime(timeZone));
    }

}
