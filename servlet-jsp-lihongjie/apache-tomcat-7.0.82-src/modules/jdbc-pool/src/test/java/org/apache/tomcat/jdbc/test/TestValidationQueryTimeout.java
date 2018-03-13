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
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.tomcat.jdbc.pool.interceptor.QueryTimeoutInterceptor;

public class TestValidationQueryTimeout extends DefaultTestCase {

    private static int TIMEOUT = 10;
    private static boolean isTimeoutSet;
    private static final String longQuery = "select * from test as A, test as B, test as C, test as D, test as E";

    @Before
    public void setUp() throws SQLException {
        DriverManager.registerDriver(new MockDriver());

        // use our mock driver
        this.datasource.setDriverClassName(MockDriver.class.getName());
        this.datasource.setUrl(MockDriver.url);

        // Required to trigger validation query's execution
        this.datasource.setInitialSize(1);
        this.datasource.setTestOnBorrow(true);
        this.datasource.setValidationInterval(-1);
        this.datasource.setValidationQuery("SELECT 1");
        this.datasource.setValidationQueryTimeout(TIMEOUT);

        TIMEOUT = 10;
        isTimeoutSet = false;
    }

    @Override
    @After
    public void tearDown() throws SQLException {
        DriverManager.deregisterDriver(new MockDriver());
    }

    @Test
    public void testValidationQueryTimeoutEnabled() throws Exception {
        // because testOnBorrow is true, this triggers the validation query
        this.datasource.getConnection();
        Assert.assertTrue(isTimeoutSet);
    }

    @Test
    public void testValidationQueryTimeoutDisabled() throws Exception {
        this.datasource.setValidationQueryTimeout(-1);

        // because testOnBorrow is true, this triggers the validation query
        this.datasource.getConnection();
        Assert.assertFalse(isTimeoutSet);
    }

    @Test
    public void testValidationQueryTimeoutWithQueryTimeoutInterceptor() throws Exception {
        int interceptorTimeout = 30;
        this.datasource.setJdbcInterceptors(
                            QueryTimeoutInterceptor.class.getName()+
                            "(queryTimeout="+ interceptorTimeout +")");

        // because testOnBorrow is true, this triggers the validation query
        Connection con = this.datasource.getConnection();
        Assert.assertTrue(isTimeoutSet);

        // increase the expected timeout to 30, which is what we set for the interceptor
        TIMEOUT = 30;

        // now create a statement, make sure the query timeout is set by the interceptor
        Statement st = con.createStatement();
        Assert.assertEquals(interceptorTimeout, st.getQueryTimeout());
        st.close();
        st = con.prepareStatement("");
        Assert.assertEquals(interceptorTimeout, st.getQueryTimeout());
        st.close();
        st = con.prepareCall("");
        Assert.assertEquals(interceptorTimeout, st.getQueryTimeout());
        st.close();
        con.close();

        // pull another connection and check it
        TIMEOUT = 10;
        isTimeoutSet = false;
        this.datasource.getConnection();
        Assert.assertTrue(isTimeoutSet);
    }

    // this test depends on the execution time of the validation query
    //   specifically, it needs to run for longer than 1 second to pass
    //   if this fails
    @Test(expected=SQLException.class)
    public void testValidationQueryTimeoutOnConnection() throws Exception {
        // use our mock driver
        this.datasource.setDriverClassName("org.h2.Driver");
        this.datasource.setUrl("jdbc:h2:~/.h2/test;QUERY_TIMEOUT=0;DB_CLOSE_ON_EXIT=FALSE");

        // Required to trigger validation query's execution
        this.datasource.setTestOnConnect(true);
        this.datasource.setValidationInterval(-1);
        this.datasource.setValidationQuery(longQuery);
        this.datasource.setValidationQueryTimeout(1);

        this.datasource.getConnection();
    }

    @Test(expected=SQLException.class)
    public void testValidationInvalidOnConnection() throws Exception {
        // use our mock driver
        this.datasource.setDriverClassName("org.h2.Driver");
        this.datasource.setUrl("jdbc:h2:~/.h2/test;QUERY_TIMEOUT=0;DB_CLOSE_ON_EXIT=FALSE");

        // Required to trigger validation query's execution
        this.datasource.setTestOnBorrow(true);
        this.datasource.setInitialSize(1);
        this.datasource.setTestOnConnect(true);
        this.datasource.setValidationInterval(-1);
        this.datasource.setValidationQuery("SELECT");
        this.datasource.setValidationQueryTimeout(1);

        this.datasource.getConnection();
    }

    @Test
    public void testLongValidationQueryTime() throws Exception {
        // use our mock driver
        this.datasource.setDriverClassName("org.h2.Driver");
        this.datasource.setUrl("jdbc:h2:~/.h2/test;QUERY_TIMEOUT=0;DB_CLOSE_ON_EXIT=FALSE");
        Connection con = this.datasource.getConnection();
        Statement stmt = null;
        long start = 0, end = 0;
        try {
            stmt = con.createStatement();
            // set the query timeout to 2 sec
            //  this keeps this test from slowing things down too much
            stmt.setQueryTimeout(2);
            // assert that our long query takes longer than one second to run
            //  this is a requirement for other tests to run properly
            start = System.currentTimeMillis();
            stmt.execute(longQuery);
        } catch (SQLException ex) {}
        finally {
            end = System.currentTimeMillis();

            if (stmt != null) { stmt.close(); }
            if (con != null) { con.close(); }

            Assert.assertTrue(start != 0 && end != 0);
            Assert.assertTrue((end - start) > 1000);
        }
    }

    @Test
    public void testValidationQueryTimeoutOnBorrow() throws Exception {
        // use our mock driver
        this.datasource.setDriverClassName("org.h2.Driver");
        this.datasource.setUrl("jdbc:h2:~/.h2/test;QUERY_TIMEOUT=0;DB_CLOSE_ON_EXIT=FALSE");

        // Required to trigger validation query's execution
        this.datasource.setTestOnBorrow(true);
        this.datasource.setValidationInterval(-1);
        this.datasource.setValidationQuery(longQuery);
        this.datasource.setValidationQueryTimeout(1);

        // assert that even though the validation query times out, we still get a connection
        Connection con = this.datasource.getConnection();
        Assert.assertNotNull(con);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT 1");
        rs.close();
        st.close();
        con.close();
    }

    /**
     * Mock Driver, Connection and Statement implementations use to verify setQueryTimeout was called.
     */
    public static class MockDriver implements java.sql.Driver {
        public static final String url = "jdbc:tomcat:mock";

        public MockDriver() {
        }

        @Override
        public boolean acceptsURL(String url) throws SQLException {
            return url!=null && url.equals(MockDriver.url);
        }

        @Override
        public Connection connect(String url, Properties info) throws SQLException {
            return new MockConnection(info);
        }

        @Override
        public int getMajorVersion() {
            return 0;
        }

        @Override
        public int getMinorVersion() {
            return 0;
        }

        @Override
        public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
            return null;
        }

        @Override
        public boolean jdbcCompliant() {
            return false;
        }

        /**
         * @return <code>null</code>
         * @throws SQLFeatureNotSupportedException
         */
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
    }

    public static class MockConnection extends org.apache.tomcat.jdbc.test.driver.Connection {
        public MockConnection(Properties info) {
            super(info);
        }

        @Override
        public Statement createStatement() throws SQLException {
            return new MockStatement();
        }
    }

    public static class MockStatement extends org.apache.tomcat.jdbc.test.driver.Statement {
        @Override
        public void setQueryTimeout(int seconds) throws SQLException {
            super.setQueryTimeout(seconds);
            Assert.assertEquals(TIMEOUT, seconds);
            isTimeoutSet = true;
        }
    }
}
