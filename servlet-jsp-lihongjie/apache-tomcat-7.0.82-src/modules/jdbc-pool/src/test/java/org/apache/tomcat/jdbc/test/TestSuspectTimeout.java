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

import org.junit.Assert;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.PooledConnection;

public class TestSuspectTimeout extends DefaultTestCase {

    @Test
    public void testSuspect() throws Exception {
        this.datasource.setMaxActive(100);
        this.datasource.setMaxIdle(100);
        this.datasource.setInitialSize(0);
        this.datasource.getPoolProperties().setAbandonWhenPercentageFull(0);
        this.datasource.getPoolProperties().setTimeBetweenEvictionRunsMillis(100);
        this.datasource.getPoolProperties().setRemoveAbandoned(true);
        this.datasource.getPoolProperties().setRemoveAbandonedTimeout(100);
        this.datasource.getPoolProperties().setSuspectTimeout(1);
        this.datasource.getPoolProperties().setLogAbandoned(true);
        Connection con = datasource.getConnection();
        Assert.assertEquals("Number of connections active/busy should be 1",1,datasource.getPool().getActive());
        Thread.sleep(3000);
        PooledConnection pcon = con.unwrap(PooledConnection.class);
        Assert.assertTrue("Connection should be marked suspect",pcon.isSuspect());
        con.close();
    }
}
