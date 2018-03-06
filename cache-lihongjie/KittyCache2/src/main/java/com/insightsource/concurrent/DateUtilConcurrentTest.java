package com.insightsource.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

/*
 * http://devno.blogspot.hk/2013/06/testing-thread-safety-with-junit.html
 * http://garygregory.wordpress.com/2011/09/09/multi-threaded-unit-testing/
 */
public class DateUtilConcurrentTest {
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
        int runCount = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(runCount);

        /************* first usage ***/
        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        for (int x = 0; x < runCount; x++) {
            Callable<Void> callable = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    testCompareDateTime();
                    return null;
                }
            };
            Future<Void> submit = executorService.submit(callable);
            futures.add(submit);
        }

        /************* second usage.
         Callable<Void> task = new Callable<Void>() {
        @Override public Void call() {
        testCompareDateTime();
        }
        };
         List<Callable<Void>> tasks = Collections.nCopies(runCount, task);
         List<Future<Void>> futures = executorService.invokeAll(tasks);
         */

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        executorService.shutdown();

    }
}
