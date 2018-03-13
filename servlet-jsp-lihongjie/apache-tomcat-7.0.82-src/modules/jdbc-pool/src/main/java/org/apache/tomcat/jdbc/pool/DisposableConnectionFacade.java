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

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

/**
 * A DisposableConnectionFacade object is the top most interceptor that wraps an
 * object of type {@link PooledConnection}. The DisposableConnectionFacade intercepts
 * two methods:
 * <ul>
 *   <li>{@link java.sql.Connection#close()} - returns the connection to the
 *       pool then breaks the link between cutoff and the next interceptor.
 *       May be called multiple times.</li>
 *   <li>{@link java.lang.Object#toString()} - returns a custom string for this
 *       object</li>
 * </ul>
 * By default method comparisons is done on a String reference level, unless the
 * {@link PoolConfiguration#setUseEquals(boolean)} has been called with a
 * <code>true</code> argument.
 */
public class DisposableConnectionFacade extends JdbcInterceptor {
    protected DisposableConnectionFacade(JdbcInterceptor interceptor) {
        setUseEquals(interceptor.isUseEquals());
        setNext(interceptor);
    }

    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
    }



    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (compare(EQUALS_VAL, method)) {
            return Boolean.valueOf(this.equals(Proxy.getInvocationHandler(args[0])));
        } else if (compare(HASHCODE_VAL, method)) {
            return Integer.valueOf(this.hashCode());
        } else if (getNext()==null) {
            if (compare(ISCLOSED_VAL, method)) {
                return Boolean.TRUE;
            }
            else if (compare(CLOSE_VAL, method)) {
                return null;
            }
            else if (compare(ISVALID_VAL, method)) {
                return Boolean.FALSE;
            }
        }

        try {
            return super.invoke(proxy, method, args);
        } catch (NullPointerException e) {
            if (getNext() == null) {
                if (compare(TOSTRING_VAL, method)) {
                    return "DisposableConnectionFacade[null]";
                }
                throw new SQLException(
                        "PooledConnection has already been closed.");
            }

            throw e;
        } finally {
            if (compare(CLOSE_VAL, method)) {
                setNext(null);
            }
        }
    }
}
