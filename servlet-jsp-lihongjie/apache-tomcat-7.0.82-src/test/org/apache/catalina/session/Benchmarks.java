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
package org.apache.catalina.session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import static org.junit.Assert.fail;

import org.junit.Test;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Session;
import org.apache.catalina.core.StandardContext;

/**
 * Named Benchmarks so it is not automatically executed as part of the unit
 * tests.
 */
public class Benchmarks {

    /*
     * Results on markt's 4-core Windows dev box
     *  1 thread  -  ~1,400ms
     *  2 threads -  ~2,100ms
     *  4 threads -  ~3,100ms
     * 16 threads - ~14,700ms
     *
     * Results on markt's 2-core OSX dev box
     *  1 thread  -   ~1,400ms
     *  2 threads -   ~1,700ms
     *  4 threads -   ~3,500ms
     * 16 threads -  ~14,465ms
     */
    @Test
    public void testManagerBaseGenerateSessionId() throws Exception {
        doTestManagerBaseGenerateSessionId(1, 1000000);
        doTestManagerBaseGenerateSessionId(1, 1000000);
        doTestManagerBaseGenerateSessionId(1, 1000000);
        doTestManagerBaseGenerateSessionId(2, 1000000);
        doTestManagerBaseGenerateSessionId(2, 1000000);
        doTestManagerBaseGenerateSessionId(2, 1000000);
        doTestManagerBaseGenerateSessionId(4, 1000000);
        doTestManagerBaseGenerateSessionId(4, 1000000);
        doTestManagerBaseGenerateSessionId(4, 1000000);
        doTestManagerBaseGenerateSessionId(16, 1000000);
        // Reduce iterations as context switching will slow things down
        doTestManagerBaseGenerateSessionId(100, 100000);
        doTestManagerBaseGenerateSessionId(400, 10000);
    }


    private void doTestManagerBaseGenerateSessionId(int threadCount,
            int iterCount) throws Exception {

        // Create a default session manager
        StandardManager mgr = new StandardManager();
        try {
            mgr.startInternal();
        } catch (LifecycleException e) {
            // Ignore - this is expected
        }
        mgr.generateSessionId();
        while (mgr.sessionCreationTiming.size() <
                ManagerBase.TIMING_STATS_CACHE_SIZE) {
            mgr.sessionCreationTiming.add(null);
        }
        while (mgr.sessionExpirationTiming.size() <
                ManagerBase.TIMING_STATS_CACHE_SIZE) {
            mgr.sessionExpirationTiming.add(null);
        }


        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(
                    new TestThreadGenerateSessionId(mgr, iterCount));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();

        StringBuilder result = new StringBuilder();
        result.append("Threads: ");
        result.append(threadCount);
        result.append(", Time(ms): ");
        result.append(end-start);
        System.out.println(result.toString());
    }


    private static final class TestThreadGenerateSessionId implements Runnable {

        private ManagerBase mgr;
        private int count;

        public TestThreadGenerateSessionId(ManagerBase mgr, int count) {
            this.mgr = mgr;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                mgr.generateSessionId();
            }
        }
    }


    /*
     * Results on markt's 4-core Windows dev box
     *  1 thread  -  ~3,800ms
     *  2 threads -  ~6,700ms
     *  4 threads - ~11,000ms
     * 16 threads - ~43,500ms
     *
     * Results on markt's 2-core OSX dev box
     *  1 thread  -  ~4,100ms
     *  2 threads -  ~5,700ms
     *  4 threads - ~11,700ms
     * 16 threads - ~45,600ms
     */
    @Test
    public void testManagerBaseCreateSession() {
        doTestManagerBaseCreateSession(1, 1000000);
        doTestManagerBaseCreateSession(2, 1000000);
        doTestManagerBaseCreateSession(4, 1000000);
        doTestManagerBaseCreateSession(16, 1000000);
        // Reduce iterations as context switching will slow things down
        doTestManagerBaseCreateSession(100, 100000);
        doTestManagerBaseCreateSession(400, 10000);
    }


    private void doTestManagerBaseCreateSession(int threadCount,
            int iterCount) {

        // Create a default session manager
        StandardManager mgr = new StandardManager();
        try {
            mgr.startInternal();
        } catch (LifecycleException e) {
            // Ignore - this is expected
        }
        mgr.setContainer(new StandardContext());
        mgr.generateSessionId();
        while (mgr.sessionCreationTiming.size() <
                ManagerBase.TIMING_STATS_CACHE_SIZE) {
            mgr.sessionCreationTiming.add(null);
        }
        while (mgr.sessionExpirationTiming.size() <
                ManagerBase.TIMING_STATS_CACHE_SIZE) {
            mgr.sessionExpirationTiming.add(null);
        }

        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(
                    new TestThreadCreateSession(mgr, iterCount));
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();

        StringBuilder result = new StringBuilder();
        result.append("Threads: ");
        result.append(threadCount);
        result.append(", Time(ms): ");
        result.append(end-start);
        System.out.println(result.toString());
    }

    private static final class TestThreadCreateSession implements Runnable {

        private ManagerBase mgr;
        private int count;

        public TestThreadCreateSession(ManagerBase mgr, int count) {
            this.mgr = mgr;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                Session session = mgr.createSession(mgr.generateSessionId());
                session.expire();
            }
        }
    }


    /*
     * SecureRandom vs. reading /dev/urandom. Very different performance noted
     * on some platforms.
     *
     * Results on markt's 4-core Windows dev box
     *              SecureRandom  /dev/urandom
     *  1 thread  -    ~766ms        N/A
     *  2 threads -    ~843ms        N/A
     *  4 threads -    ~766ms        N/A
     *
     * Results on markt's 2-core OSX dev box
     *              SecureRandom  /dev/urandom
     *  1 thread  -     ~759ms      ~3,500ms
     *  2 threads -     ~725ms      ~5,200ms
     *  4 threads -   ~1,265ms     ~10,500ms
     */
    @Test
    public void testSecureRandomVsDevURandom() throws Exception {
        doTestSecureRandomVsDevURandom(1, 1000000);
        doTestSecureRandomVsDevURandom(2, 1000000);
        doTestSecureRandomVsDevURandom(4, 1000000);
    }

    private void doTestSecureRandomVsDevURandom(int threadCount, int iterCount)
            throws Exception {
        doTestSecureRandomVsDevURandomInner(threadCount, iterCount, true);
        doTestSecureRandomVsDevURandomInner(threadCount, iterCount, false);
    }

    private void doTestSecureRandomVsDevURandomInner(int threadCount,
            int iterCount, boolean useSecureRandom) throws Exception {

        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            if (useSecureRandom) {
                threads[i] = new Thread(new TestThreadSecureRandom(iterCount));
            } else {
                threads[i] = new Thread(new TestThreadDevUrandom(iterCount));
            }
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();

        StringBuilder result = new StringBuilder();
        if (useSecureRandom) {
            result.append("SecureRandom ");
        } else {
            result.append("/dev/urandom ");
        }
        result.append("Threads: ");
        result.append(threadCount);
        result.append(", Time(ms): ");
        result.append(end-start);
        System.out.println(result.toString());
    }

    private static final class TestThreadSecureRandom implements Runnable {

        private SecureRandom secureRandom;
        private byte[] bytes = new byte[16];
        private int count;

        TestThreadSecureRandom(int iterCount) throws Exception {
            this.count = iterCount;
            this.secureRandom = SecureRandom.getInstance("SHA1PRNG");
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                secureRandom.nextBytes(bytes);
            }
        }

    }

    private static final class TestThreadDevUrandom implements Runnable {

        private InputStream is;
        private byte[] bytes = new byte[16];
        private int count;

        TestThreadDevUrandom(int iterCount) {
            try {
                is = new FileInputStream("/dev/urandom");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.count = iterCount;
        }

        @Override
        public void run() {
            try {
                int read = 0;
                for (int i = 0; i < count; i++) {
                    read = is.read(bytes);
                    if (read < bytes.length) {
                        throw new IOException("Only read " + read + " bytes");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
