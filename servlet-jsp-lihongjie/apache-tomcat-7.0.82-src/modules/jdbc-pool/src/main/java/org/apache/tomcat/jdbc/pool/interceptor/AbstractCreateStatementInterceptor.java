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
package org.apache.tomcat.jdbc.pool.interceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PooledConnection;

/**
 * Abstraction interceptor. This component intercepts all calls to create some type of SQL statement.
 * By extending this class, one can intercept queries and update statements by overriding the {@link #createStatement(Object, Method, Object[], Object, long)}
 * method.
 * @author Filip Hanik
 * @version 1.0
 */
public abstract class  AbstractCreateStatementInterceptor extends JdbcInterceptor {
    protected static final String CREATE_STATEMENT      = "createStatement";
    protected static final int    CREATE_STATEMENT_IDX  = 0;
    protected static final String PREPARE_STATEMENT     = "prepareStatement";
    protected static final int    PREPARE_STATEMENT_IDX = 1;
    protected static final String PREPARE_CALL          = "prepareCall";
    protected static final int    PREPARE_CALL_IDX      = 2;

    protected static final String[] STATEMENT_TYPES = {CREATE_STATEMENT, PREPARE_STATEMENT, PREPARE_CALL};
    protected static final int    STATEMENT_TYPE_COUNT = STATEMENT_TYPES.length;

    protected static final String EXECUTE        = "execute";
    protected static final String EXECUTE_QUERY  = "executeQuery";
    protected static final String EXECUTE_UPDATE = "executeUpdate";
    protected static final String EXECUTE_BATCH  = "executeBatch";

    protected static final String[] EXECUTE_TYPES = {EXECUTE, EXECUTE_QUERY, EXECUTE_UPDATE, EXECUTE_BATCH};

    /**
     * the constructors that are used to create statement proxies
     */
    protected static final Constructor<?>[] constructors =
            new Constructor[AbstractCreateStatementInterceptor.STATEMENT_TYPE_COUNT];

    public  AbstractCreateStatementInterceptor() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (compare(CLOSE_VAL,method)) {
            closeInvoked();
            return super.invoke(proxy, method, args);
        } else {
            boolean process = false;
            process = isStatement(method, process);
            if (process) {
                long start = System.currentTimeMillis();
                Object statement = super.invoke(proxy,method,args);
                long delta = System.currentTimeMillis() - start;
                return createStatement(proxy,method,args,statement, delta);
            } else {
                return super.invoke(proxy,method,args);
            }
        }
    }

    /**
     * Creates a constructor for a proxy class, if one doesn't already exist
     *
     * @param idx
     *            - the index of the constructor
     * @param clazz
     *            - the interface that the proxy will implement
     * @return - returns a constructor used to create new instances
     * @throws NoSuchMethodException Constructor not found
     */
    protected Constructor<?> getConstructor(int idx, Class<?> clazz) throws NoSuchMethodException {
        if (constructors[idx] == null) {
            Class<?> proxyClass = Proxy.getProxyClass(AbstractCreateStatementInterceptor.class.getClassLoader(),
                    new Class[] { clazz });
            constructors[idx] = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
        }
        return constructors[idx];
    }

    /**
     * This method will be invoked after a successful statement creation. This method can choose to return a wrapper
     * around the statement or return the statement itself.
     * If this method returns a wrapper then it should return a wrapper object that implements one of the following interfaces.
     * {@link java.sql.Statement}, {@link java.sql.PreparedStatement} or {@link java.sql.CallableStatement}
     * @param proxy the actual proxy object
     * @param method the method that was called. It will be one of the methods defined in {@link #STATEMENT_TYPES}
     * @param args the arguments to the method
     * @param statement the statement that the underlying connection created
     * @return a {@link java.sql.Statement} object
     */
    public abstract Object createStatement(Object proxy, Method method, Object[] args, Object statement, long time);

    /**
     * Method invoked when the operation {@link java.sql.Connection#close()} is invoked.
     */
    public abstract void closeInvoked();

    /**
     * Returns true if the method that is being invoked matches one of the statement types.
     *
     * @param method the method being invoked on the proxy
     * @param process boolean result used for recursion
     * @return returns true if the method name matched
     */
    protected boolean isStatement(Method method, boolean process){
        return process(STATEMENT_TYPES, method, process);
    }

    /**
     * Returns true if the method that is being invoked matches one of the execute types.
     *
     * @param method the method being invoked on the proxy
     * @param process boolean result used for recursion
     * @return returns true if the method name matched
     */
    protected boolean isExecute(Method method, boolean process){
        return process(EXECUTE_TYPES, method, process);
    }

    /*
     * Returns true if the method that is being invoked matches one of the method names passed in
     * @param names list of method names that we want to intercept
     * @param method the method being invoked on the proxy
     * @param process boolean result used for recursion
     * @return returns true if the method name matched
     */
    protected boolean process(String[] names, Method method, boolean process) {
        final String name = method.getName();
        for (int i=0; (!process) && i<names.length; i++) {
            process = compare(names[i],name);
        }
        return process;
    }

    /**
     * no-op for this interceptor. no state is stored.
     */
    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        // NOOP
    }
}
