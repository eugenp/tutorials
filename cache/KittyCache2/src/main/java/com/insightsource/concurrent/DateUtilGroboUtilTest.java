package com.insightsource.concurrent;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Assert;
import org.junit.Test;

/*
 * http://mushiqianmeng.blog.51cto.com/3970029/897786
 */
public class DateUtilGroboUtilTest {
    private void testCompareDateTime() {
        String dateTime1 = "20120111 01:02:03";
        String dateTime2 = "20130111 01:02:03";
        String dateTime3 = "20130111 01:02:03";

        Assert.assertEquals(-1, DateUtil.compareDateTime(dateTime1, dateTime2));
        Assert.assertEquals(1, DateUtil.compareDateTime(dateTime2, dateTime1));
        Assert.assertEquals(0, DateUtil.compareDateTime(dateTime2, dateTime3));
    }

    @Test
    public void multiThreadTest() {
        TestRunnable runner = new TestRunnable() {
            public void runTest() throws Throwable {
                testCompareDateTime();
            }
        };

        int runCount = 10;

        long start = System.currentTimeMillis();
        TestRunnable[] trs = new TestRunnable[runCount];
        for (int i = 0; i < runCount; i++) {
            trs[i] = runner;
        }

        // ����ִ�ж��̲߳���������Runner����ǰ�涨��ĵ���Runner��ɵ����鴫�� 
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        try {
            // ��������ִ�������ﶨ�������
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        long used = System.currentTimeMillis() - start;
        System.out.printf("total last milli-seconds " + used);
    }
}
