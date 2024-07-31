package com.baeldung.timeago.version8;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.Test;

public class TimeAgoCalculatorUnitTest {

    private LocalDateTime getCurrentTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        return localDateTime.atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        //We return a fixed date and time in order to avoid issues related to getting time from local in unit tests.
        //return LocalDateTime.now(zone);
    }

    @Test
    public void calculateTimeAgoWithPeriodAndDurationTest() {
        Assert.assertEquals("moments ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime(), ZoneId.systemDefault()));
        Assert.assertEquals("several seconds ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime().minus(Duration.ofSeconds(5)), ZoneId.systemDefault()));
        Assert.assertEquals("several minutes ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime().minus(Duration.ofMinutes(5)), ZoneId.systemDefault()));
        Assert.assertEquals("several hours ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime().minus(Duration.ofHours(5)), ZoneId.systemDefault()));
        Assert.assertEquals("several days ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime().minus(Period.ofDays(5)), ZoneId.systemDefault()));
        Assert.assertEquals("several months ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime().minus(Period.ofMonths(5)), ZoneId.systemDefault()));
        Assert.assertEquals("several years ago", TimeAgoCalculator.calculateTimeAgoWithPeriodAndDuration(getCurrentTime().minus(Period.ofYears(5)), ZoneId.systemDefault()));
    }
}
