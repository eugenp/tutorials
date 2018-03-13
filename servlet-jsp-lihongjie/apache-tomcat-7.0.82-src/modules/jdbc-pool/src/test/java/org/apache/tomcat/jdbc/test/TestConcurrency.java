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

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.test.driver.Driver;

public class TestConcurrency extends DefaultTestCase {

    public static final  boolean debug = Boolean.getBoolean("jdbc.debug");

    protected volatile DataSource ds = null;

    @Before
    public void setUp() throws Exception {
        ds = createDefaultDataSource();
        ds.getPoolProperties().setDriverClassName(Driver.class.getName());
        ds.getPoolProperties().setUrl(Driver.url);
        ds.getPoolProperties().setInitialSize(0);
        ds.getPoolProperties().setMaxIdle(0);
        ds.getPoolProperties().setMinIdle(0);
        ds.getPoolProperties().setMaxActive(10);
        ds.getPoolProperties().setRemoveAbandoned(true);
        ds.getPoolProperties().setLogAbandoned(true);
        ds.getPoolProperties().setTestWhileIdle(true);
        ds.getPoolProperties().setMinEvictableIdleTimeMillis(750);
        ds.getPoolProperties().setTimeBetweenEvictionRunsMillis(25);
        ds.setFairQueue(true);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        ds.close(true);
        Driver.reset();
        super.tearDown();
    }

    @Test
    public void testSimple() throws Exception {
        ds.getConnection().close();
        final int iter = 1000 * 10;
        final AtomicInteger loopcount = new AtomicInteger(0);
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    while (loopcount.incrementAndGet() < iter) {
                        Connection con = ds.getConnection();
                        Thread.sleep(10);
                        con.close();
                    }
                }catch (Exception x) {
                    loopcount.set(iter); //stops the test
                    x.printStackTrace();
                }
            }
        };
        Thread[] threads = new Thread[20];
        for (int i=0; i<threads.length; i++) {
            threads[i] = new Thread(run);
        }
        for (int i=0; i<threads.length; i++) {
            threads[i].start();
        }
        try {
            while (loopcount.get()<iter) {
                Assert.assertTrue("Size comparison(less than 11):",ds.getPool().getSize()<=10);
                if (debug) {
                    System.out.println("Size: "+ds.getPool().getSize());
                    System.out.println("Used: "+ds.getPool().getActive());
                    System.out.println("Idle: "+ds.getPool().getIdle());
                    System.out.println("Wait: "+ds.getPool().getWaitCount());
                }
                Thread.sleep(250);
            }
        }catch (Exception x) {
            loopcount.set(iter); //stops the test
            x.printStackTrace();
        }
        for (int i=0; i<threads.length; i++) {
            threads[i].join();
        }
        Assert.assertEquals("Size comparison:",10, ds.getPool().getSize());
        Assert.assertEquals("Idle comparison:",10, ds.getPool().getIdle());
        Assert.assertEquals("Used comparison:",0, ds.getPool().getActive());
        Assert.assertEquals("Connect count",10,Driver.connectCount.get());

    }

    @Test
    public void testBrutal() throws Exception {
        ds.getPoolProperties().setRemoveAbandoned(false);
        ds.getPoolProperties().setRemoveAbandonedTimeout(1);
        ds.getPoolProperties().setMinEvictableIdleTimeMillis(100);
        ds.getPoolProperties().setTimeBetweenEvictionRunsMillis(10);
        ds.getConnection().close();
        final int iter = 100000 * 10;
        final AtomicInteger loopcount = new AtomicInteger(0);
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    while (loopcount.incrementAndGet() < iter) {
                        Connection con = ds.getConnection();
                        con.close();
                    }
                }catch (Exception x) {
                    loopcount.set(iter); //stops the test
                    x.printStackTrace();
                }
            }
        };
        Thread[] threads = new Thread[20];
        for (int i=0; i<threads.length; i++) {
            threads[i] = new Thread(run);
        }
        for (int i=0; i<threads.length; i++) {
            threads[i].start();
        }
        try {
            while (loopcount.get()<iter) {
                Assert.assertTrue("Size comparison(less than 11):",ds.getPool().getSize()<=10);
                ds.getPool().testAllIdle();
                ds.getPool().checkAbandoned();
                ds.getPool().checkIdle();
            }
        }catch (Exception x) {
            loopcount.set(iter); //stops the test
            x.printStackTrace();
        }
        for (int i=0; i<threads.length; i++) {
            threads[i].join();
        }
        System.out.println("Connect count:"+Driver.connectCount.get());
        System.out.println("DisConnect count:"+Driver.disconnectCount.get());
        Assert.assertEquals("Size comparison:",10, ds.getPool().getSize());
        Assert.assertEquals("Idle comparison:",10, ds.getPool().getIdle());
        Assert.assertEquals("Used comparison:",0, ds.getPool().getActive());
        Assert.assertEquals("Connect count",10,Driver.connectCount.get());
    }

    @Test
    public void testBrutalNonFair() throws Exception {
        ds.getPoolProperties().setRemoveAbandoned(false);
        ds.getPoolProperties().setRemoveAbandonedTimeout(1);
        ds.getPoolProperties().setMinEvictableIdleTimeMillis(100);
        ds.getPoolProperties().setTimeBetweenEvictionRunsMillis(10);
        ds.getConnection().close();
        final int iter = 100000 * 10;
        final AtomicInteger loopcount = new AtomicInteger(0);
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    while (loopcount.incrementAndGet() < iter) {
                        Connection con = ds.getConnection();
                        con.close();
                    }
                }catch (Exception x) {
                    loopcount.set(iter); //stops the test
                    x.printStackTrace();
                }
            }
        };
        Thread[] threads = new Thread[20];
        for (int i=0; i<threads.length; i++) {
            threads[i] = new Thread(run);
        }
        for (int i=0; i<threads.length; i++) {
            threads[i].start();
        }
        try {
            while (loopcount.get()<iter) {
                Assert.assertTrue("Size comparison(less than 11):",ds.getPool().getSize()<=10);
                ds.getPool().testAllIdle();
                ds.getPool().checkAbandoned();
                ds.getPool().checkIdle();
            }
        }catch (Exception x) {
            loopcount.set(iter); //stops the test
            x.printStackTrace();
        }
        for (int i=0; i<threads.length; i++) {
            threads[i].join();
        }
        System.out.println("Connect count:"+Driver.connectCount.get());
        System.out.println("DisConnect count:"+Driver.disconnectCount.get());
        Assert.assertEquals("Size comparison:",10, ds.getPool().getSize());
        Assert.assertEquals("Idle comparison:",10, ds.getPool().getIdle());
        Assert.assertEquals("Used comparison:",0, ds.getPool().getActive());
        Assert.assertEquals("Connect count",10,Driver.connectCount.get());
    }
}
