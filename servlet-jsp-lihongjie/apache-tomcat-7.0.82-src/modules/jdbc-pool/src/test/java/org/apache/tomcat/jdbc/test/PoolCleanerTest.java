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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;

public class PoolCleanerTest extends DefaultTestCase {

    private int countPoolCleanerThreads() {
        Map<Thread, StackTraceElement[]> threadmap = Thread.getAllStackTraces();
        int result = 0;
        for (Thread t : threadmap.keySet()) {
            if (t.getName().startsWith("PoolCleaner[")) result++;
        }
        return result;
    }

    @Test
    public void testPoolCleaner() throws Exception {
        datasource.getPoolProperties().setTimeBetweenEvictionRunsMillis(2000);
        datasource.getPoolProperties().setTestWhileIdle(true);
        Assert.assertEquals("Pool cleaner should not be started yet.",0,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNull("Pool timer should be null", ConnectionPool.getPoolTimer());
        Assert.assertEquals("Pool cleaner threads should not be present.",0, countPoolCleanerThreads());

        datasource.getConnection().close();
        Assert.assertEquals("Pool cleaner should have 1 cleaner.",1,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNotNull("Pool timer should not be null", ConnectionPool.getPoolTimer());
        Assert.assertEquals("Pool cleaner threads should be 1.",1, countPoolCleanerThreads());

        datasource.close();
        Assert.assertEquals("Pool shutdown, no cleaners should be present.",0,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNull("Pool timer should be null after shutdown", ConnectionPool.getPoolTimer());
        Assert.assertEquals("Pool cleaner threads should not be present after close.",0, countPoolCleanerThreads());


    }

    @Test
    public void test2PoolCleaners() throws Exception {
        datasource.getPoolProperties().setTimeBetweenEvictionRunsMillis(2000);
        datasource.getPoolProperties().setTestWhileIdle(true);

        DataSource ds2 = new DataSource(datasource.getPoolProperties());

        Assert.assertEquals("Pool cleaner should not be started yet.",0,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNull("Pool timer should be null", ConnectionPool.getPoolTimer());
        Assert.assertEquals("Pool cleaner threads should not be present.",0, countPoolCleanerThreads());

        datasource.getConnection().close();
        ds2.getConnection().close();
        Assert.assertEquals("Pool cleaner should have 2 cleaner.",2,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNotNull("Pool timer should not be null", ConnectionPool.getPoolTimer());
        Assert.assertEquals("Pool cleaner threads should be 1.",1, countPoolCleanerThreads());

        datasource.close();
        Assert.assertEquals("Pool cleaner should have 1 cleaner.",1,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNotNull("Pool timer should not be null", ConnectionPool.getPoolTimer());

        ds2.close();
        Assert.assertEquals("Pool shutdown, no cleaners should be present.",0,ConnectionPool.getPoolCleaners().size() );
        Assert.assertNull("Pool timer should be null after shutdown", ConnectionPool.getPoolTimer());
        Assert.assertEquals("Pool cleaner threads should not be present after close.",0, countPoolCleanerThreads());
    }

    @Test
    public void testIdleTimeout() throws Exception {
        datasource.getPoolProperties().setTimeBetweenEvictionRunsMillis(2000);
        datasource.getPoolProperties().setTestWhileIdle(true);
        datasource.getPoolProperties().setMaxIdle(0);
        datasource.getPoolProperties().setInitialSize(0);
        datasource.getPoolProperties().setMinIdle(0);
        datasource.getPoolProperties().setMinEvictableIdleTimeMillis(1000);
        datasource.getConnection().close();
        Assert.assertEquals("Pool should have 1 idle.", 1, datasource.getIdle());
        Thread.sleep(3000);
        Assert.assertEquals("Pool should have 0 idle.", 0, datasource.getIdle());
    }
}
