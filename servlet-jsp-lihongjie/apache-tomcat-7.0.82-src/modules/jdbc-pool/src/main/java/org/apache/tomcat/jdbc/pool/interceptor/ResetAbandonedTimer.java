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

import java.lang.reflect.Method;

import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.tomcat.jdbc.pool.ProxyConnection;

/**
 * Class that resets the abandoned timer on any activity on the
 * Connection or any successful query executions.
 * This interceptor is useful for when you have a {@link org.apache.tomcat.jdbc.pool.PoolConfiguration#setRemoveAbandonedTimeout(int)}
 * that is fairly low, and you want to reset the abandoned time each time any operation on the connection is performed
 * This is useful for batch processing programs that use connections for extensive amount of times.
 * @author fhanik
 *
 */
public class ResetAbandonedTimer extends AbstractQueryReport {

    public ResetAbandonedTimer() {
    }

    public boolean resetTimer() {
        boolean result = false;
        JdbcInterceptor interceptor = this.getNext();
        while (interceptor!=null && result==false) {
            if (interceptor instanceof ProxyConnection) {
                PooledConnection con = ((ProxyConnection)interceptor).getConnection();
                if (con!=null) {
                    con.setTimestamp(System.currentTimeMillis());
                    result = true;
                } else {
                    break;
                }
            }
            interceptor = interceptor.getNext();
        }
        return result;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = super.invoke(proxy, method, args);
        resetTimer();
        return result;
    }

    @Override
    protected void prepareCall(String query, long time) {
        resetTimer();
    }

    @Override
    protected void prepareStatement(String sql, long time) {
        resetTimer();

    }

    @Override
    public void closeInvoked() {
        resetTimer();
    }

    @Override
    protected String reportQuery(String query, Object[] args, String name,long start, long delta) {
        resetTimer();
        return super.reportQuery(query, args, name, start, delta);
    }

    @Override
    protected String reportSlowQuery(String query, Object[] args, String name,long start, long delta) {
        resetTimer();
        return super.reportSlowQuery(query, args, name, start, delta);
    }
}
