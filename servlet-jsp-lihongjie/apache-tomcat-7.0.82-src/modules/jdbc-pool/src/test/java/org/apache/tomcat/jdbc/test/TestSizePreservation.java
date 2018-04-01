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

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.test.driver.Driver;

/**
 * https://bz.apache.org/bugzilla/show_bug.cgi?id=50613
 */
public class TestSizePreservation {

    protected volatile DataSource ds = null;

    private void initSimplePoolProperties() {
        PoolConfiguration p = new DefaultProperties();
        ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setPoolProperties(p);

        ds.getPoolProperties().setDriverClassName(Driver.class.getName());
        ds.getPoolProperties().setUrl(Driver.url);
        ds.getPoolProperties().setFairQueue(true);
        ds.getPoolProperties().setJmxEnabled(false);
        ds.getPoolProperties().setTestWhileIdle(true);
        ds.getPoolProperties().setTestOnBorrow(false);
        ds.getPoolProperties().setTestOnReturn(false);
        ds.getPoolProperties().setValidationInterval(30000);
        ds.getPoolProperties().setTimeBetweenEvictionRunsMillis(30000);
        ds.getPoolProperties().setInitialSize(100);
        ds.getPoolProperties().setMaxActive(100);
        ds.getPoolProperties().setMinIdle(0);
        ds.getPoolProperties().setMaxIdle(0);
        ds.getPoolProperties().setMaxWait(10000);
        ds.getPoolProperties().setRemoveAbandonedTimeout(10);
        ds.getPoolProperties().setMinEvictableIdleTimeMillis(10000);
        ds.getPoolProperties().setLogAbandoned(false);
        ds.getPoolProperties().setRemoveAbandoned(false);
        ds.getPoolProperties().setUseLock(true);
    }

    private void initEvictingPool() {
        initSimplePoolProperties();
        ds.getPoolProperties().setTimeBetweenEvictionRunsMillis(25);
        ds.getPoolProperties().setMinEvictableIdleTimeMillis(750);
        ds.getPoolProperties().setRemoveAbandoned(true);
        ds.getPoolProperties().setRemoveAbandonedTimeout(1);
    }

    @Test
    public void testSimple() throws Exception {
        initSimplePoolProperties();
        common();
        ds.close(true);
        Driver.reset();
    }

    @Test
    public void testEvicting() throws Exception {
        initEvictingPool();
        common();
        ds.close(true);
        Driver.reset();
    }

    private void common() throws Exception {
        ds.getConnection().close();
        final int iterations = 1000;
        final AtomicInteger loopcount = new AtomicInteger(0);
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    while (loopcount.incrementAndGet() < iterations) {
                        Connection c = ds.getConnection();
                        Thread.sleep(1000);
                        c.close();
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        };
        Thread[] threads = new Thread[200];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(run);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            while (loopcount.get() < iterations) {
                Thread.sleep(250);
            }
        } catch (Exception x) {
            loopcount.set(iterations); // stops the test
            x.printStackTrace();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        System.out.println("Pool size:"+ds.getPool().getSize());
        Assert.assertTrue("Size validity check: ", ds.getPool().getSize() >= 0);
    }

}
