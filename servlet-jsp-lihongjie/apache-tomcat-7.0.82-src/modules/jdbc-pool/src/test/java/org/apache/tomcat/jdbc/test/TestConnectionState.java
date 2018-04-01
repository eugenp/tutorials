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

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;

public class TestConnectionState extends DefaultTestCase {

    @Test
    public void testAutoCommitFalse() throws Exception {
        DataSourceProxy d1 = this.createDefaultDataSource();
        d1.setMaxActive(1);
        d1.setMinIdle(1);
        d1.setMaxIdle(1);
        d1.setJdbcInterceptors(ConnectionState.class.getName());
        d1.setDefaultAutoCommit(Boolean.FALSE);
        Connection c1 = d1.getConnection();
        Assert.assertFalse("Auto commit should be false",c1.getAutoCommit());
        c1.setAutoCommit(true);
        Assert.assertTrue("Auto commit should be true",c1.getAutoCommit());
        c1.close();
        c1 = d1.getConnection();
        Assert.assertFalse("Auto commit should be false for a reused connection",c1.getAutoCommit());
        d1.close(true);
        Assert.assertTrue("Connection should be closed",c1.isClosed());
    }

    @Test
    public void testAutoCommitTrue() throws Exception {
        DataSourceProxy d1 = this.createDefaultDataSource();
        d1.setMaxActive(1);
        d1.setJdbcInterceptors(ConnectionState.class.getName());
        d1.setDefaultAutoCommit(Boolean.TRUE);
        d1.setMinIdle(1);
        Connection c1 = d1.getConnection();
        Assert.assertTrue("Auto commit should be true",c1.getAutoCommit());
        c1.setAutoCommit(false);
        Assert.assertFalse("Auto commit should be false",c1.getAutoCommit());
        c1.close();
        c1 = d1.getConnection();
        Assert.assertTrue("Auto commit should be true for a reused connection",c1.getAutoCommit());
    }

    @Test
    public void testDefaultCatalog() throws Exception {
        DataSourceProxy d1 = this.createDefaultDataSource();
        d1.setMaxActive(1);
        d1.setJdbcInterceptors(ConnectionState.class.getName());
        d1.setDefaultCatalog("information_schema");
        d1.setMinIdle(1);
        Connection c1 = d1.getConnection();
        Assert.assertEquals("Catalog should be information_schema",c1.getCatalog(),"information_schema");
        c1.close();
        c1 = d1.getConnection();
        Assert.assertEquals("Catalog should be information_schema",c1.getCatalog(),"information_schema");
        c1.setCatalog("mysql");
        Assert.assertEquals("Catalog should be information_schema",c1.getCatalog(),"mysql");
        c1.close();
        c1 = d1.getConnection();
        Assert.assertEquals("Catalog should be information_schema",c1.getCatalog(),"information_schema");
    }
}
