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

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * @author Filip Hanik
 * @version 1.0
 */
public class DefaultProperties extends PoolProperties {
    private static final long serialVersionUID = 1L;

    public DefaultProperties() {
        setDbProperties(new Properties());

        //mysql
        //url = System.getProperty("url","jdbc:mysql://localhost:3306/mysql?autoReconnect=true");
        //driverClassName = System.getProperty("driverClassName","com.mysql.jdbc.Driver");

        //derby
        //url = System.getProperty("url","jdbc:derby:derbyDB;create=true");
        //driverClassName = System.getProperty("driverClassName","org.apache.derby.jdbc.EmbeddedDriver");

        setUrl(System.getProperty("url","jdbc:h2:~/.h2/test;QUERY_TIMEOUT=0;DB_CLOSE_ON_EXIT=FALSE"));
        setDriverClassName(System.getProperty("driverClassName","org.h2.Driver"));
        System.setProperty("h2.serverCachedObjects", "10000");

        setPassword(System.getProperty("password","password"));
        setUsername(System.getProperty("username","root"));

        setValidationQuery(System.getProperty("validationQuery","SELECT 1"));
        setDefaultAutoCommit(Boolean.TRUE);
        setDefaultReadOnly(Boolean.FALSE);
        setDefaultTransactionIsolation(DataSourceFactory.UNKNOWN_TRANSACTIONISOLATION);
        setConnectionProperties(null);
        setDefaultCatalog(null);
        setInitialSize(10);
        setMaxActive(100);
        setMaxIdle(getInitialSize());
        setMinIdle(getInitialSize());
        setMaxWait(10000);

        setTestOnBorrow(true);
        setTestOnReturn(false);
        setTestWhileIdle(true);
        setTimeBetweenEvictionRunsMillis(5000);
        setNumTestsPerEvictionRun(0);
        setMinEvictableIdleTimeMillis(1000);
        setRemoveAbandoned(true);
        setRemoveAbandonedTimeout(5000);
        setLogAbandoned(true);
        setValidationInterval(0); //always validate
        setInitSQL(null);
        setTestOnConnect(false);
        getDbProperties().setProperty("user",getUsername());
        getDbProperties().setProperty("password",getPassword());
    }
}
