package com.insightsource.concurrent;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TestFactory;
import com.clarkware.junitperf.TestMethodFactory;

public class DateUtilJUnitPerfTest extends TestCase {
    public DateUtilJUnitPerfTest(String name) {
        super(name);
    }

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    public void testCompareDateTime() {
        String dateTime1 = "20120111 01:02:03";
        String dateTime2 = "20130111 01:02:03";
        String dateTime3 = "20130111 01:02:03";

        Assert.assertEquals(-1, DateUtil.compareDateTime(dateTime1, dateTime2));
        Assert.assertEquals(1, DateUtil.compareDateTime(dateTime2, dateTime1));
        Assert.assertEquals(0, DateUtil.compareDateTime(dateTime2, dateTime3));
    }

    /*
     * http://www.cnblogs.com/loggingselenium/archive/2013/01/08/2850605.html
     */
    public static junit.framework.Test compareDateTimeLoadTestMethod() {
        int users = 5;
        TestFactory factory = new TestMethodFactory(DateUtilJUnitPerfTest.class, "testCompareDateTime");
        junit.framework.Test loadTest = new LoadTest(factory, users);
        return loadTest;
    }

    public static junit.framework.Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(compareDateTimeLoadTestMethod());
        return suite;
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }
}
