package com.insightsource.concurrent;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/*
 * http://blog.javabenchmark.org/2013/04/check-that-your-code-is-thread-safe.html
 * 
 */
public class DateUtilContiPerf {
    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 10, threads = 2)
    public void testCompareDateTime() {
        String dateTime1 = "20120111 01:02:03";
        String dateTime2 = "20130111 01:02:03";
        String dateTime3 = "20130111 01:02:03";

        Assert.assertEquals(-1, DateUtil.compareDateTime(dateTime1, dateTime2));
        Assert.assertEquals(1, DateUtil.compareDateTime(dateTime2, dateTime1));
        Assert.assertEquals(0, DateUtil.compareDateTime(dateTime2, dateTime3));
    }
}
