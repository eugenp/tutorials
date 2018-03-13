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
import java.security.SecureRandom;

/**
 * @author fhanik
 *
 */
public class InduceSlowQuery extends AbstractQueryReport {
    public static SecureRandom random = new SecureRandom();

    public InduceSlowQuery() {
        // TODO Auto-generated constructor stub
    }

    public void doWait() {
        try {
            int b = random.nextInt(10);
            if (b == 0) {
                Thread.sleep(random.nextInt(2000));
            }
        } catch (InterruptedException x) {

        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        Object result = super.invoke(proxy, method, args);
        return result;
    }

    @Override
    protected void prepareCall(String query, long time) {
    }

    @Override
    protected void prepareStatement(String sql, long time) {
    }

    @Override
    public void closeInvoked() {
    }

    @Override
    protected String reportQuery(String query, Object[] args, String name, long start, long delta) {
        doWait();
        return super.reportQuery(query, args, name, start, delta);
    }

    @Override
    protected String reportSlowQuery(String query, Object[] args, String name, long start, long delta) {
        doWait();
        return super.reportSlowQuery(query, args, name, start, delta);
    }
}
