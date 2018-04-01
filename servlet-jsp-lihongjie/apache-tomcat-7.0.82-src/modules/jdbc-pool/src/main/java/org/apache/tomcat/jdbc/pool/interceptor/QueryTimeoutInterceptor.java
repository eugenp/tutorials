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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorProperty;

public class QueryTimeoutInterceptor extends AbstractCreateStatementInterceptor {
    private static Log log = LogFactory.getLog(QueryTimeoutInterceptor.class);
    int timeout = 1;

    @Override
    public void setProperties(Map<String,InterceptorProperty> properties) {
        super.setProperties(properties);
        InterceptorProperty p = properties.get("queryTimeout");
        if (p!=null) timeout = p.getValueAsInt(timeout);
    }

    @Override
    public Object createStatement(Object proxy, Method method, Object[] args, Object statement, long time) {
        if (statement instanceof Statement && timeout > 0) {
            Statement s = (Statement)statement;
            try {
                s.setQueryTimeout(timeout);
            }catch (SQLException x) {
                log.warn("[QueryTimeoutInterceptor] Unable to set query timeout:"+x.getMessage(),x);
            }
        }
        return statement;
    }

    @Override
    public void closeInvoked() {
    }

}
