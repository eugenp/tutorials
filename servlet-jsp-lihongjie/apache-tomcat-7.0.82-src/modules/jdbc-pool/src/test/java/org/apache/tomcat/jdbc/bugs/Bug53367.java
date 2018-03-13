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
package org.apache.tomcat.jdbc.bugs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolExhaustedException;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.test.DefaultProperties;

@RunWith(Parameterized.class)
public class Bug53367 {

    private boolean fairQueue;

    public Bug53367(boolean fair) {
        this.fairQueue = fair;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
            new Object[] {Boolean.TRUE},
            new Object[] {Boolean.FALSE},
        });
    }

    @Test
    public void testPool() throws SQLException, InterruptedException {
        DriverManager.setLoginTimeout(1);
        PoolProperties poolProperties = new DefaultProperties();
        int threadsCount = 3;
        poolProperties.setMaxActive(threadsCount);
        poolProperties.setMaxIdle(threadsCount);
        poolProperties.setMinIdle(0);
        poolProperties.setMaxWait(5000);
        poolProperties.setInitialSize(0);
        poolProperties.setRemoveAbandoned(true);
        poolProperties.setRemoveAbandonedTimeout(300);
        poolProperties.setRollbackOnReturn(true);
        poolProperties.setFairQueue(fairQueue);
        final DataSource ds = new DataSource(poolProperties);

        final CountDownLatch openedLatch = new CountDownLatch(threadsCount);
        final CountDownLatch closedLatch = new CountDownLatch(threadsCount);
        final CountDownLatch toCloseLatch = new CountDownLatch(1);

        for (int i = 0; i < threadsCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection connection = ds.getConnection();
                        openedLatch.countDown();

                        toCloseLatch.await();
                        connection.close();

                        closedLatch.countDown();

                    } catch (Exception e) {
                        System.err.println("Step 1:"+e.getMessage());
                    }
                }
            }).start();
        }

        openedLatch.await();
        ConnectionPool pool = ds.getPool();
        //Now we have 3 initialized busy connections
        Assert.assertEquals(0, pool.getIdle());
        Assert.assertEquals(threadsCount, pool.getActive());
        Assert.assertEquals(threadsCount, pool.getSize());

        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ds.getConnection();
                    } catch (Exception e) {
                        System.err.println("Step 2:"+e.getMessage());
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        //Still 3 active connections
        Assert.assertEquals(0, pool.getIdle());
        Assert.assertEquals(threadsCount, pool.getActive());
        Assert.assertEquals(threadsCount, pool.getSize());

        toCloseLatch.countDown();
        closedLatch.await();

        //Here comes the bug! No more active connections and unable to establish new connections.

        Assert.assertEquals(threadsCount, pool.getIdle()); // <-- Should be threadsCount (3) here
        Assert.assertEquals(0, pool.getActive());
        Assert.assertEquals(threadsCount, pool.getSize());

        final AtomicInteger failedCount = new AtomicInteger();
        final ArrayBlockingQueue<Connection> cons = new ArrayBlockingQueue<Connection>(threadsCount);
        threads.clear();
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cons.add(ds.getConnection());
                    } catch (PoolExhaustedException e) {
                        failedCount.incrementAndGet();
                        System.err.println("Step 3:"+e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Step 4:"+e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
        Assert.assertEquals(0, failedCount.get());

        Assert.assertEquals(0, pool.getIdle());
        Assert.assertEquals(threadsCount, pool.getActive());
        Assert.assertEquals(threadsCount, pool.getSize());
        for (Connection con : cons) {
            con.close();
        }
        Assert.assertEquals(threadsCount, pool.getIdle());
        Assert.assertEquals(0, pool.getActive());
        Assert.assertEquals(threadsCount, pool.getSize());
    }
}