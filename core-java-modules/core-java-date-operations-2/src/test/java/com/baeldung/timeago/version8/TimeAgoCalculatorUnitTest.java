package com.baeldung.timeago.version8;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.Test;

public class TimeAgoCalculatorUnitTest {

    // fixing test in BAEL-5647
    //@Test
    public void calculateTimeAgoWithPeriodAndDurationTest() {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        Assert.assertEquals("moments ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()), ZoneId.systemDefault()));
        Assert.assertEquals("several seconds ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (5 * 1000)), ZoneId.systemDefault()), ZoneId.systemDefault()));
        Assert.assertEquals("several minutes ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (5 * 60 * 1000)), ZoneId.systemDefault()), ZoneId.systemDefault()));
        Assert.assertEquals("several hours ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (5 * 60 * 60 * 1000)), ZoneId.systemDefault()), ZoneId.systemDefault()));
        Assert.assertEquals("several days ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (5 * DAY_IN_MILLIS)), ZoneId.systemDefault()), ZoneId.systemDefault()));
        Assert.assertEquals("several months ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (5 * DAY_IN_MILLIS * 30)), ZoneId.systemDefault()), ZoneId.systemDefault()));
        Assert.assertEquals("several years ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - (5 * DAY_IN_MILLIS * 365)), ZoneId.systemDefault()), ZoneId.systemDefault()));
    }
}
