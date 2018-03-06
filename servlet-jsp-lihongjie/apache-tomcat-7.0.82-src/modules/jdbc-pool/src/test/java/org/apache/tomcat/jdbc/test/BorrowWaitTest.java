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
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class BorrowWaitTest extends DefaultTestCase {

    @Test
    public void testWaitTime() throws Exception {

        int wait = 10000;
        this.datasource.setMaxActive(1);
        this.datasource.setMaxWait(wait);
        Connection con = datasource.getConnection();
        long start = System.currentTimeMillis();
        try {
            Connection con2 = datasource.getConnection();
            Assert.assertFalse("This should not happen, connection should be unavailable.",true);
            con2.close();
        }catch (SQLException x) {
            long delta = System.currentTimeMillis() - start;
            boolean inrange = Math.abs(wait-delta) <= 1000;
            Assert.assertTrue("Connection should have been acquired within +/- 1 second, but was "+(wait-delta)+" ms.",inrange);
        }
        con.close();
    }

    public void testWaitTimeInfinite() throws Exception {
        if(true){
            System.err.println("testWaitTimeInfinite() test is disabled.");
            return;//this would lock up the test suite
        }
        /*
        int wait = -1;
        this.datasource.setMaxActive(1);
        this.datasource.setMaxWait(wait);
        Connection con = datasource.getConnection();
        long start = System.currentTimeMillis();
        try {
            Connection con2 = datasource.getConnection();
            assertFalse("This should not happen, connection should be unavailable.",true);
        }catch (SQLException x) {
            long delta = System.currentTimeMillis();
            boolean inrange = Math.abs(wait-delta) < 1000;
            assertTrue("Connection should have been acquired within +/- 1 second.",true);
        }
        con.close();
        */
    }


}
