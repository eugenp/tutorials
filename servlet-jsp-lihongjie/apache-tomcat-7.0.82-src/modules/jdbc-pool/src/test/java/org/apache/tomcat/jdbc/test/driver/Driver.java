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
package org.apache.tomcat.jdbc.test.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
    public static final String url = "jdbc:tomcat:test";
    public static final AtomicInteger connectCount = new AtomicInteger(0);
    public static final AtomicInteger disconnectCount = new AtomicInteger(0);

    public static void reset() {
        connectCount.set(0);
        disconnectCount.set(0);
    }

    static {
        try {
            DriverManager.registerDriver(new Driver());
        }catch (Exception x) {
            x.printStackTrace();
            throw new RuntimeException(x);
        }
    }

    public Driver() {
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url!=null && url.equals(Driver.url);
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        connectCount.addAndGet(1);
        return new org.apache.tomcat.jdbc.test.driver.Connection(info);
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

    // ---------------------------------------------------------- Java 7 methods
    // Can't add @Override annotations since this code also has to compile with
    // Java 6 for Tomcat 7.

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
