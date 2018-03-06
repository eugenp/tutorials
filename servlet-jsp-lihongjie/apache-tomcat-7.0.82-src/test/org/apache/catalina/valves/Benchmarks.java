/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.valves;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * Some simple micro-benchmarks to help determine best approach for thread
 * safety in valves, particularly the {@link AccessLogValve}. Implemented as
 * JUnit tests to make the simple to execute but does not used Test* as the
 * class name to avoid being included in the automated unit tests.
 */
public class Benchmarks {
    @Test
    public void testAccessLogGetDate() throws Exception {
        // Is it better to use a sync or a thread local here?
        BenchmarkTest benchmark = new BenchmarkTest();
        Runnable[] tests = new Runnable[] { new GetDateBenchmarkTest_Sync(),
                new GetDateBenchmarkTest_Local(),
                new GetDateBenchmarkTest_LocalMutableLong(),
                new GetDateBenchmarkTest_LocalStruct() };
        benchmark.doTest(5, tests);
    }

    private static class GetDateBenchmarkTest_Sync implements Runnable {

        @Override
        public String toString() {
            return "Syncs";
        }

        private volatile long currentMillis = 0;
        private volatile Date currentDate = null;

        @Override
        public void run() {
            getCurrentDate();
        }

        public Date getCurrentDate() {
            long systime = System.currentTimeMillis();
            if ((systime - currentMillis) > 1000) {
                synchronized (this) {
                    if ((systime - currentMillis) > 1000) {
                        currentDate = new Date(systime);
                        currentMillis = systime;
                    }
                }
            }
            return currentDate;
        }
    }

    private static class GetDateBenchmarkTest_Local implements Runnable {

        @Override
        public String toString() {
            return "ThreadLocals";
        }

        private ThreadLocal<Long> currentMillisLocal = new ThreadLocal<Long>() {
            @Override
            protected Long initialValue() {
                return Long.valueOf(0);
            }
        };

        private ThreadLocal<Date> currentDateLocal = new ThreadLocal<Date>();

        @Override
        public void run() {
            getCurrentDate();
        }

        public Date getCurrentDate() {
            long systime = System.currentTimeMillis();
            if ((systime - currentMillisLocal.get().longValue()) > 1000) {
                currentDateLocal.set(new Date(systime));
                currentMillisLocal.set(Long.valueOf(systime));
            }
            return currentDateLocal.get();
        }
    }

    private static class GetDateBenchmarkTest_LocalMutableLong implements
            Runnable {

        @Override
        public String toString() {
            return "ThreadLocals with a mutable Long";
        }

        private static class MutableLong {
            long value = 0;
        }

        private ThreadLocal<MutableLong> currentMillisLocal = new ThreadLocal<MutableLong>() {
            @Override
            protected MutableLong initialValue() {
                return new MutableLong();
            }
        };

        private ThreadLocal<Date> currentDateLocal = new ThreadLocal<Date>();

        @Override
        public void run() {
            getCurrentDate();
        }

        public Date getCurrentDate() {
            long systime = System.currentTimeMillis();
            if ((systime - currentMillisLocal.get().value) > 1000) {
                currentDateLocal.set(new Date(systime));
                currentMillisLocal.get().value = systime;
            }
            return currentDateLocal.get();
        }
    }

    private static class GetDateBenchmarkTest_LocalStruct implements Runnable {

        @Override
        public String toString() {
            return "single ThreadLocal";
        }

        // note, that we can avoid (long -> Long) conversion
        private static class Struct {
            public long currentMillis = 0;
            public Date currentDate;
        }

        private ThreadLocal<Struct> currentStruct = new ThreadLocal<Struct>() {
            @Override
            protected Struct initialValue() {
                return new Struct();
            }
        };

        @Override
        public void run() {
            getCurrentDate();
        }

        public Date getCurrentDate() {
            Struct struct = currentStruct.get();
            long systime = System.currentTimeMillis();
            if ((systime - struct.currentMillis) > 1000) {
                struct.currentDate = new Date(systime);
                struct.currentMillis = systime;
            }
            return struct.currentDate;
        }
    }

    @Test
    public void testAccessLogTimeDateElement() throws Exception {
        // Is it better to use a sync or a thread local here?
        BenchmarkTest benchmark = new BenchmarkTest();
        Runnable[] tests = new Runnable[] {
                new TimeDateElementBenchmarkTest_Sync(),
                new TimeDateElementBenchmarkTest_Local(),
                new TimeDateElementBenchmarkTest_LocalStruct(),
                new TimeDateElementBenchmarkTest_LocalStruct_SBuilder() };
        benchmark.doTest(5, tests);
    }

    private abstract static class TimeDateElementBenchmarkTestBase {
        protected static final String months[] = { "Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        protected String lookup(String month) {
            int index;
            try {
                index = Integer.parseInt(month) - 1;
            } catch (Throwable t) {
                index = 0; // Can not happen, in theory
            }
            return (months[index]);
        }
    }

    private static class TimeDateElementBenchmarkTest_Sync extends
            TimeDateElementBenchmarkTestBase implements Runnable {

        @Override
        public String toString() {
            return "Syncs";
        }

        private volatile Date currentDate = new Date();
        private volatile String currentDateString = null;
        private SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
        private SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
        private SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
        private SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "hh:mm:ss");

        @Override
        public void run() {
            printDate();
        }

        public String printDate() {
            StringBuilder buf = new StringBuilder();
            Date date = getDateSync();
            if (currentDate != date) {
                synchronized (this) {
                    if (currentDate != date) {
                        StringBuilder current = new StringBuilder(32);
                        current.append('[');
                        current.append(dayFormatter.format(date)); // Day
                        current.append('/');
                        current.append(lookup(monthFormatter.format(date))); // Month
                        current.append('/');
                        current.append(yearFormatter.format(date)); // Year
                        current.append(':');
                        current.append(timeFormatter.format(date)); // Time
                        current.append(']');
                        currentDateString = current.toString();
                        currentDate = date;
                    }
                }
            }
            buf.append(currentDateString);
            return buf.toString();
        }

        private Date getDateSync() {
            long systime = System.currentTimeMillis();
            if ((systime - currentDate.getTime()) > 1000) {
                synchronized (this) {
                    if ((systime - currentDate.getTime()) > 1000) {
                        currentDate.setTime(systime);
                    }
                }
            }
            return currentDate;
        }
    }

    private static class TimeDateElementBenchmarkTest_Local extends
            TimeDateElementBenchmarkTestBase implements Runnable {

        @Override
        public String toString() {
            return "ThreadLocals";
        }

        private ThreadLocal<String> currentDateStringLocal = new ThreadLocal<String>();

        private ThreadLocal<Date> currentDateLocal = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                return new Date();
            }
        };
        private ThreadLocal<SimpleDateFormat> dayFormatterLocal = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("dd");
            }
        };
        private ThreadLocal<SimpleDateFormat> monthFormatterLocal = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("MM");
            }
        };
        private ThreadLocal<SimpleDateFormat> yearFormatterLocal = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy");
            }
        };
        private ThreadLocal<SimpleDateFormat> timeFormatterLocal = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("hh:mm:ss");
            }
        };

        @Override
        public void run() {
            printDate();
        }

        public String printDate() {
            getDateLocal();
            if (currentDateStringLocal.get() == null) {
                StringBuilder current = new StringBuilder(32);
                current.append('[');
                current.append(dayFormatterLocal.get().format(
                        currentDateLocal.get())); // Day
                current.append('/');
                current.append(lookup(monthFormatterLocal.get().format(
                        currentDateLocal.get()))); // Month
                current.append('/');
                current.append(yearFormatterLocal.get().format(
                        currentDateLocal.get())); // Year
                current.append(':');
                current.append(timeFormatterLocal.get().format(
                        currentDateLocal.get())); // Time
                current.append(']');
                currentDateStringLocal.set(current.toString());
            }
            return currentDateStringLocal.get();
        }

        private Date getDateLocal() {
            long systime = System.currentTimeMillis();
            if ((systime - currentDateLocal.get().getTime()) > 1000) {
                currentDateLocal.get().setTime(systime);
                currentDateStringLocal.set(null);
            }
            return currentDateLocal.get();
        }
    }

    private static class TimeDateElementBenchmarkTest_LocalStruct extends
            TimeDateElementBenchmarkTestBase implements Runnable {

        @Override
        public String toString() {
            return "single ThreadLocal";
        }

        private static class Struct {
            public String currentDateString;
            public Date currentDate = new Date();
            public SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
            public SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
            public SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
            public SimpleDateFormat timeFormatter = new SimpleDateFormat(
                    "hh:mm:ss");
        }

        private ThreadLocal<Struct> structLocal = new ThreadLocal<Struct>() {
            @Override
            protected Struct initialValue() {
                return new Struct();
            }
        };

        @Override
        public void run() {
            printDate();
        }

        public String printDate() {
            getDateLocal();
            Struct struct = structLocal.get();
            if (struct.currentDateString == null) {
                StringBuilder current = new StringBuilder(32);
                current.append('[');
                current.append(struct.dayFormatter.format(struct.currentDate)); // Day
                current.append('/');
                current.append(lookup(struct.monthFormatter
                        .format(struct.currentDate))); // Month
                current.append('/');
                current.append(struct.yearFormatter.format(struct.currentDate)); // Year
                current.append(':');
                current.append(struct.timeFormatter.format(struct.currentDate)); // Time
                current.append(']');
                struct.currentDateString = current.toString();
            }
            return struct.currentDateString;
        }

        private Date getDateLocal() {
            Struct struct = structLocal.get();
            long systime = System.currentTimeMillis();
            if ((systime - struct.currentDate.getTime()) > 1000) {
                struct.currentDate.setTime(systime);
                struct.currentDateString = null;
            }
            return struct.currentDate;
        }
    }

    private static class TimeDateElementBenchmarkTest_LocalStruct_SBuilder extends
            TimeDateElementBenchmarkTestBase implements Runnable {

        @Override
        public String toString() {
            return "single ThreadLocal, with StringBuilder";
        }

        private static class Struct {
            public String currentDateString;
            public Date currentDate = new Date();
            public SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
            public SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
            public SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
            public SimpleDateFormat timeFormatter = new SimpleDateFormat(
                    "hh:mm:ss");
        }

        private ThreadLocal<Struct> structLocal = new ThreadLocal<Struct>() {
            @Override
            protected Struct initialValue() {
                return new Struct();
            }
        };

        @Override
        public void run() {
            printDate();
        }

        public String printDate() {
            getDateLocal();
            Struct struct = structLocal.get();
            if (struct.currentDateString == null) {
                StringBuilder current = new StringBuilder(32);
                current.append('[');
                current.append(struct.dayFormatter.format(struct.currentDate)); // Day
                current.append('/');
                current.append(lookup(struct.monthFormatter
                        .format(struct.currentDate))); // Month
                current.append('/');
                current.append(struct.yearFormatter.format(struct.currentDate)); // Year
                current.append(':');
                current.append(struct.timeFormatter.format(struct.currentDate)); // Time
                current.append(']');
                struct.currentDateString = current.toString();
            }
            return struct.currentDateString;
        }

        private Date getDateLocal() {
            Struct struct = structLocal.get();
            long systime = System.currentTimeMillis();
            if ((systime - struct.currentDate.getTime()) > 1000) {
                struct.currentDate.setTime(systime);
                struct.currentDateString = null;
            }
            return struct.currentDate;
        }
    }

    private static class BenchmarkTest {
        public void doTest(int threadCount, Runnable[] tests) throws Exception {
            for (int iterations = 1000000; iterations < 10000001; iterations += 1000000) {
                for (int i = 0; i < tests.length; i++) {
                    doTestInternal(threadCount, iterations, tests[i]);
                }
            }
        }

        private void doTestInternal(int threadCount, int iterations,
                Runnable test) throws Exception {
            long start = System.currentTimeMillis();
            Thread[] threads = new Thread[threadCount];
            for (int i = 0; i < threadCount; i++) {
                threads[i] = new Thread(new TestThread(iterations, test));
            }
            for (int i = 0; i < threadCount; i++) {
                threads[i].start();
            }
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }
            long end = System.currentTimeMillis();

            System.out.println(test.getClass().getSimpleName() + ": "
                    + threadCount + " threads and " + iterations
                    + " iterations using " + test + " took " + (end - start)
                    + "ms");
        }
    }

    private static class TestThread implements Runnable {
        private int count;
        private Runnable test;

        public TestThread(int count, Runnable test) {
            this.count = count;
            this.test = test;
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                test.run();
            }
        }
    }
}
