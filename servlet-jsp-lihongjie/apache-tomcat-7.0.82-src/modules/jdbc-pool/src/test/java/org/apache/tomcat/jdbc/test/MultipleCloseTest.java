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

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.test.driver.Driver;

public class MultipleCloseTest extends DefaultTestCase {

    @Override
    public org.apache.tomcat.jdbc.pool.DataSource createDefaultDataSource() {
        org.apache.tomcat.jdbc.pool.DataSource ds = super.createDefaultDataSource();
        ds.getPoolProperties().setDriverClassName(Driver.class.getName());
        ds.getPoolProperties().setUrl(Driver.url);
        ds.getPoolProperties().setInitialSize(0);
        ds.getPoolProperties().setMaxIdle(1);
        ds.getPoolProperties().setMinIdle(1);
        ds.getPoolProperties().setMaxActive(1);
        ds.getPoolProperties().setUseDisposableConnectionFacade(true);
        return ds;
    }

    @After
    @Override
    public void tearDown() throws Exception {
        Driver.reset();
        super.tearDown();
    }

    @Test
    public void testClosedConnectionsNotReused() throws Exception {
        this.init();

        Connection con1 = datasource.getConnection();

        // A new connection is open
        Assert.assertFalse(con1.isClosed());

        con1.close();

        // Confirm that a closed connection is closed
        Assert.assertTrue(con1.isClosed());

        // Open a new connection (This will re-use the previous pooled connection)
        datasource.getConnection();

        // A connection, once closed, should stay closed
        Assert.assertTrue(con1.isClosed());
    }
}
