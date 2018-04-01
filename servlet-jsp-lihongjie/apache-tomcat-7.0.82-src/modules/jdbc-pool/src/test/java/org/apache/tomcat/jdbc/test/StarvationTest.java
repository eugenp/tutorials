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
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

/**
 * If a connection is abandoned and closed,
 * then that should free up a spot in the pool, and other threads
 * that are waiting should not time out and throw an error but be
 * able to acquire a connection, since one was just released.
 */
public class StarvationTest extends DefaultTestCase {

    private void config() {
        datasource.getPoolProperties().setMaxActive(1);
        datasource.getPoolProperties().setMaxIdle(1);
        datasource.getPoolProperties().setInitialSize(1);
        datasource.getPoolProperties().setRemoveAbandoned(true);
        datasource.getPoolProperties().setRemoveAbandonedTimeout(5);
        datasource.getPoolProperties().setTimeBetweenEvictionRunsMillis(500);
        datasource.getPoolProperties().setMaxWait(10000);
        datasource.getPoolProperties().setLogAbandoned(true);
    }

//    @Test
//    public void testDBCPConnectionStarvation() throws Exception {
//        config();
//        this.transferProperties();
//        this.tDatasource.getConnection().close();
//        javax.sql.DataSource datasource = this.tDatasource;
//        Connection con1 = datasource.getConnection();
//        Connection con2 = null;
//        try {
//            con2 = datasource.getConnection();
//            try {
//                con2.setCatalog("mysql");//make sure connection is valid
//            }catch (SQLException x) {
//                assertFalse("2nd Connection is not valid:"+x.getMessage(),true);
//            }
//            assertTrue("Connection 1 should be closed.",con1.isClosed()); //first connection should be closed
//        }catch (Exception x) {
//            assertFalse("Connection got starved:"+x.getMessage(),true);
//        }finally {
//            if (con2!=null) con2.close();
//        }
//    }

    @Test
    public void testConnectionStarvation() throws Exception {
        config();
        Connection con1 = datasource.getConnection();
        Connection con2 = null;
        try {
            con2 = datasource.getConnection();
            try {
                con2.setCatalog("mysql");//make sure connection is valid
            }catch (SQLException x) {
                Assert.assertFalse("2nd Connection is not valid:"+x.getMessage(),true);
            }
            Assert.assertTrue("Connection 1 should be closed.",con1.isClosed()); //first connection should be closed
        }catch (Exception x) {
            Assert.assertFalse("Connection got starved:"+x.getMessage(),true);
        }finally {
            if (con2!=null) con2.close();
        }
    }

    @Test
    public void testFairConnectionStarvation() throws Exception {
        init();
        config();
        datasource.getPoolProperties().setFairQueue(true);
        Connection con1 = datasource.getConnection();
        Connection con2 = null;
        try {
            con2 = datasource.getConnection();
            try {
                con2.setCatalog("mysql");//make sure connection is valid
            }catch (SQLException x) {
                Assert.assertFalse("2nd Connection is not valid:"+x.getMessage(),true);
            }
            Assert.assertTrue("Connection 1 should be closed.",con1.isClosed()); //first connection should be closed
        }catch (Exception x) {
            Assert.assertFalse("Connection got starved:"+x.getMessage(),true);
        }finally {
            if (con2!=null) con2.close();
        }
    }
}
