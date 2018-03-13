/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TestLimitLatch {

    @Test
    public void testNoThreads() throws Exception {
        LimitLatch latch = new LimitLatch(0);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
    }

    @Test
    public void testOneThreadNoWait() throws Exception {
        LimitLatch latch = new LimitLatch(1);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
        Thread testThread = new TestThread(latch);
        testThread.start();
        Thread.sleep(50);
        assertEquals("0 threads should be waiting", 0,
                latch.getQueuedThreads().size());
        latch.countUpOrAwait();
        Thread.sleep(50);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
    }

    @Test
    public void testOneThreadWaitCountUp() throws Exception {
        LimitLatch latch = new LimitLatch(1);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
        Thread testThread = new TestThread(latch);
        latch.countUpOrAwait();
        testThread.start();
        Thread.sleep(50);
        assertEquals("1 threads should be waiting", 1,
                latch.getQueuedThreads().size());
        latch.countDown();
        Thread.sleep(50);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
    }

    @Test
    public void testOneRelease() throws Exception {
        LimitLatch latch = new LimitLatch(1);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
        Thread testThread = new TestThread(latch);
        latch.countUpOrAwait();
        testThread.start();
        Thread.sleep(50);
        assertEquals("1 threads should be waiting", 1,
                latch.getQueuedThreads().size());
        latch.releaseAll();
        Thread.sleep(50);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
    }

    @Test
    public void testTenWait() throws Exception {
        LimitLatch latch = new LimitLatch(10);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
        Thread[] testThread = new TestThread[30];
        for (int i = 0; i < 30; i++) {
            testThread[i] = new TestThread(latch, 1000);
            testThread[i].start();
        }
        Thread.sleep(50);
        assertEquals("20 threads should be waiting", 20,
                latch.getQueuedThreads().size());
        Thread.sleep(1000);
        assertEquals("10 threads should be waiting", 10,
                latch.getQueuedThreads().size());
        Thread.sleep(1000);
        assertFalse("No threads should be waiting", latch.hasQueuedThreads());
    }

    private static class TestThread extends Thread {

        private int holdTime;
        private LimitLatch latch;

        public TestThread(LimitLatch latch) {
            this(latch, 100);
        }

        public TestThread(LimitLatch latch, int holdTime) {
            this.latch = latch;
            this.holdTime = holdTime;
        }

        @Override
        public void run() {
            try {
                latch.countUpOrAwait();
                Thread.sleep(holdTime);
                latch.countDown();
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }
    }
}
