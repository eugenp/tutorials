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

package org.apache.tomcat.jdbc.pool.interceptor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorProperty;
import org.apache.tomcat.jdbc.pool.PooledConnection;

public class TestInterceptor extends JdbcInterceptor {
    public static boolean poolstarted = false;
    public static boolean poolclosed = false;
    public static AtomicInteger instancecount = new AtomicInteger(0);

    @Override
    public void poolClosed(ConnectionPool pool) {
        // TODO Auto-generated method stub
        super.poolClosed(pool);
        poolclosed = true;
    }

    @Override
    public void poolStarted(ConnectionPool pool) {
        super.poolStarted(pool);
        poolstarted = true;
    }

    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProperties(Map<String, InterceptorProperty> properties) {
        instancecount.incrementAndGet();
        super.setProperties(properties);
    }


}
