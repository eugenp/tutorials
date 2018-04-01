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

package org.apache.tomcat.jdbc.test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.FairBlockingQueue;

public class TestAsyncQueue {

    protected FairBlockingQueue<Object> queue = null;

    @Before
    public void setUp() throws Exception {
        this.queue = new FairBlockingQueue<Object>();
    }

    @After
    public void tearDown() throws Exception {
        this.queue = null;
    }


    @Test
    public void testAsyncPoll1() throws Exception {
        Object item = new Object();
        queue.offer(item);
        Future<Object> future = queue.pollAsync();
        Assert.assertEquals(future.get(),item);
    }


    @Test
    public void testAsyncPoll2() throws Exception {
        Object item = new Object();
        OfferThread thread = new OfferThread(item,5000);
        thread.start();
        Future<Object> future = queue.pollAsync();
        try {
            future.get(2000, TimeUnit.MILLISECONDS);
            Assert.assertFalse("Request should have timed out",true);
        }catch (TimeoutException x) {
            Assert.assertTrue("Request timed out properly",true);
        }catch (Exception x) {
            Assert.assertTrue("Request threw an error",false);
            x.printStackTrace();
        }
        Assert.assertEquals(future.get(),item);
    }

    protected class OfferThread extends Thread {
        Object item = null;
        long delay = 5000;
        volatile boolean offered = false;
        public OfferThread(Object i, long d) {
            this.item = i;
            this.delay = d;
            this.setDaemon(false);
            this.setName(TestAsyncQueue.class.getName()+"-OfferThread");
        }
        @Override
        public void run() {
            try {
                sleep(delay);
            } catch (Exception ignore){
                // Ignore
            }
            offered = true;
            TestAsyncQueue.this.queue.offer(item);
        }
    }
}
