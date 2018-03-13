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
package org.apache.tomcat.jdbc.test;

import java.sql.Connection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.test.driver.Driver;

public class PoolPurgeTest extends DefaultTestCase {

    static final int expectedSize = 2;


    @Override
    public org.apache.tomcat.jdbc.pool.DataSource createDefaultDataSource() {
        // TODO Auto-generated method stub
        org.apache.tomcat.jdbc.pool.DataSource ds = super.createDefaultDataSource();
        ds.getPoolProperties().setDriverClassName(Driver.class.getName());
        ds.getPoolProperties().setUrl(Driver.url);
        ds.getPoolProperties().setInitialSize(expectedSize);
        ds.getPoolProperties().setMaxIdle(expectedSize);
        ds.getPoolProperties().setMinIdle(expectedSize);
        ds.getPoolProperties().setMaxActive(expectedSize);
        ds.getPoolProperties().setTimeBetweenEvictionRunsMillis(30000);
        ds.getPoolProperties().setMaxAge(Long.MAX_VALUE);
        return ds;
    }


    @Override
    @After
    public void tearDown() throws Exception {
        Driver.reset();
        super.tearDown();
    }


    @Test
    public void testPoolPurge() throws Exception {
        this.datasource.getConnection().close();
        Assert.assertEquals("Nr of connections should be "+expectedSize, expectedSize , datasource.getSize());
        this.datasource.purge();
        Assert.assertEquals("Nr of connections should be 0", 0 , datasource.getSize());
    }

    @Test
    public void testPoolPurgeWithActive() throws Exception {
        Connection con = datasource.getConnection();
        Assert.assertEquals("Nr of connections should be "+expectedSize, expectedSize , datasource.getSize());
        this.datasource.purge();
        Assert.assertEquals("Nr of connections should be "+(expectedSize-1), (expectedSize-1) , datasource.getSize());
        con.close();
        Assert.assertEquals("Nr of connections should be 0", 0 , datasource.getSize());
    }

    @Test
    public void testPoolPurgeOnReturn() throws Exception {
        init();
        Connection con = datasource.getConnection();
        Assert.assertEquals("Nr of connections should be "+expectedSize, expectedSize , datasource.getSize());
        this.datasource.purgeOnReturn();
        Assert.assertEquals("Nr of connections should be "+expectedSize, expectedSize , datasource.getSize());
        con.close();
        Assert.assertEquals("Nr of connections should be "+(expectedSize-1), (expectedSize-1) , datasource.getSize());
        tearDown();
    }
}

