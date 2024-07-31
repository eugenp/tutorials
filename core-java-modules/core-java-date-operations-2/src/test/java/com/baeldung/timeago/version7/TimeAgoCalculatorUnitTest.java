package com.baeldung.timeago.version7;

import java.util.Date;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

public class TimeAgoCalculatorUnitTest {

    private long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 1, 1, 12, 0, 0);
        return calendar.getTimeInMillis();
        //We return a fixed date and time in order to avoid issues related to getting time from local in unit tests.
        //return System.currentTimeMillis();
    }

    @Test
    public void timeAgoByTimeGranularityTest() {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        Assert.assertEquals("5 seconds ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * 1000)), TimeGranularity.SECONDS));
        Assert.assertEquals("5 minutes ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * 60 * 1000)), TimeGranularity.MINUTES));
        Assert.assertEquals("5 hours ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * 60 * 60 * 1000)), TimeGranularity.HOURS));
        Assert.assertEquals("5 days ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS)), TimeGranularity.DAYS));
        Assert.assertEquals("5 months ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 30)), TimeGranularity.MONTHS));
        Assert.assertEquals("5 weeks ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 7)), TimeGranularity.WEEKS));
        Assert.assertEquals("5 years ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 365)), TimeGranularity.YEARS));
        Assert.assertEquals("5 decades ago", TimeAgoCalculator.calculateTimeAgoByTimeGranularity(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 365 * 10)), TimeGranularity.DECADES));
    }

    @Test
    public void humanFriendlyTimeAgoTest() {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        Assert.assertEquals("moments ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * 1000))));
        Assert.assertEquals("several minutes ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * 60 * 1000))));
        Assert.assertEquals("several hours ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * 60 * 60 * 1000))));
        Assert.assertEquals("several days ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS))));
        Assert.assertEquals("several months ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 30))));
        Assert.assertEquals("several weeks ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (3 * DAY_IN_MILLIS * 7))));
        Assert.assertEquals("several years ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 365))));
        Assert.assertEquals("several decades ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgo(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 365 * 10))));
    }

    @Test
    public void calculateExactTimeAgoWithJodaTimeTest() {
        Assert.assertEquals("5 hours and 15 minutes and 3 seconds", TimeAgoCalculator.calculateExactTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * 60 * 60 * 1000 + 15 * 60 * 1000 + 3 * 1000))));
        Assert.assertEquals("5 hours and 1 minute and 1 second", TimeAgoCalculator.calculateExactTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * 60 * 60 * 1000 + 1 * 60 * 1000 + 1 * 1000))));
        Assert.assertEquals("2 days and 1 minute and 1 second", TimeAgoCalculator.calculateExactTimeAgoWithJodaTime(new Date(getCurrentTime() - (2 * 24 * 60 * 60 * 1000 + 1 * 60 * 1000 + 1 * 1000))));
    }

    @Test
    public void calculateHumanFriendlyTimeAgoWithJodaTimeTest() {
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        Assert.assertEquals("moments ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * 1000))));
        Assert.assertEquals("several minutes ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * 60 * 1000))));
        Assert.assertEquals("several hours ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * 60 * 60 * 1000))));
        Assert.assertEquals("several days ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS))));
        Assert.assertEquals("several months ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 30))));
        Assert.assertEquals("several weeks ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (3 * DAY_IN_MILLIS * 7))));
        Assert.assertEquals("several years ago", TimeAgoCalculator.calculateHumanFriendlyTimeAgoWithJodaTime(new Date(getCurrentTime() - (5 * DAY_IN_MILLIS * 365))));
    }

}
