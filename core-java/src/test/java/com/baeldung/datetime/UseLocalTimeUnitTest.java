package com.baeldung.datetime;

import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class UseLocalTimeUnitTest {

    UseLocalTime useLocalTime = new UseLocalTime();

    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalTime() {
        Assert.assertEquals("07:07:07", useLocalTime.getLocalTimeUsingFactoryOfMethod(7, 7, 7).toString());
    }

    @Test
    public void givenString_whenUsingParse_thenLocalTime() {
        Assert.assertEquals("06:30", useLocalTime.getLocalTimeUsingParseMethod("06:30").toString());
    }

    @Test
    public void givenTime_whenAddHour_thenLocalTime() {
        Assert.assertEquals("07:30", useLocalTime.addAnHour(LocalTime.of(6, 30)).toString());
    }

    @Test
    public void getHourFromLocalTime() {
        Assert.assertEquals(1, useLocalTime.getHourFromLocalTime(LocalTime.of(1, 1)));
    }

    @Test
    public void getLocalTimeWithMinuteSetToValue() {
        Assert.assertEquals(LocalTime.of(10, 20), useLocalTime.getLocalTimeWithMinuteSetToValue(LocalTime.of(10, 10), 20));
    }
}
