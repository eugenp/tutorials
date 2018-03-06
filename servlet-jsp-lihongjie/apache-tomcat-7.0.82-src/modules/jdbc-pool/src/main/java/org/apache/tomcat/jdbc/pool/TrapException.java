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

package org.apache.tomcat.jdbc.pool;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
/**
 * Interceptor that traps any unhandled exception types and throws an exception that has been declared by the method
 * called, or throw a SQLException if it is declared.
 * If the caught exception is not declared, and the method doesn't throw SQLException, then this interceptor will
 * throw a RuntimeException
 * @author fhanik
 *
 */
public class TrapException extends JdbcInterceptor {


    public TrapException() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return super.invoke(proxy, method, args);
        }catch (Exception t) {
            Throwable exception = t;
            if (t instanceof InvocationTargetException && t.getCause() != null) {
                exception = t.getCause();
                if (exception instanceof Error) {
                    throw exception;
                }
            }
            Class<?> exceptionClass = exception.getClass();
            if (!isDeclaredException(method, exceptionClass)) {
                if (isDeclaredException(method,SQLException.class)) {
                    SQLException sqlx = new SQLException("Uncaught underlying exception.");
                    sqlx.initCause(exception);
                    exception = sqlx;
                } else {
                    RuntimeException rx = new RuntimeException("Uncaught underlying exception.");
                    rx.initCause(exception);
                    exception = rx;
                }
            }
            throw exception;
        }

    }

    public boolean isDeclaredException(Method m, Class<?> clazz) {
        for (Class<?> cl : m.getExceptionTypes()) {
            if (cl.equals(clazz) || cl.isAssignableFrom(clazz)) return true;
        }
        return false;
    }

    /**
     * no-op for this interceptor. no state is stored.
     */
    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        // NOOP
    }

}
