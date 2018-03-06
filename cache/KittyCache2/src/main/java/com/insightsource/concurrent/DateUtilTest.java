package com.insightsource.concurrent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * normal unit test, one single thread
 */
public class DateUtilTest {
    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCompareDateTime() {
        String dateTime1 = "20120111 01:02:03";
        String dateTime2 = "20130111 01:02:03";
        String dateTime3 = "20130111 01:02:03";

        Assert.assertEquals(-1, DateUtil.compareDateTime(dateTime1, dateTime2));
        Assert.assertEquals(1, DateUtil.compareDateTime(dateTime2, dateTime1));
        Assert.assertEquals(0, DateUtil.compareDateTime(dateTime2, dateTime3));
    }
}
