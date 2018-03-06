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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Implementation of <b>JdbcInterceptor</b> that proxies resultSets and statements.
 * @author Guillermo Fernandes
 */
public class StatementDecoratorInterceptor extends AbstractCreateStatementInterceptor {

    private static final Log logger = LogFactory.getLog(StatementDecoratorInterceptor.class);

    protected static final String EXECUTE_QUERY  = "executeQuery";
    protected static final String GET_GENERATED_KEYS = "getGeneratedKeys";
    protected static final String GET_RESULTSET  = "getResultSet";

    protected static final String[] RESULTSET_TYPES = {EXECUTE_QUERY, GET_GENERATED_KEYS, GET_RESULTSET};

    /**
     * the constructor to create the resultSet proxies
     */
    protected static Constructor<?> resultSetConstructor = null;

    @Override
    public void closeInvoked() {
        // nothing to do
    }

    protected Constructor<?> getResultSetConstructor() throws NoSuchMethodException {
        if (resultSetConstructor == null) {
            Class<?> proxyClass = Proxy.getProxyClass(StatementDecoratorInterceptor.class.getClassLoader(),
                    new Class[] { ResultSet.class });
            resultSetConstructor = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
        }
        return resultSetConstructor;
    }

    /**
     * Creates a statement interceptor to monitor query response times
     */
    @Override
    public Object createStatement(Object proxy, Method method, Object[] args, Object statement, long time) {
        try {
            String name = method.getName();
            Constructor<?> constructor = null;
            String sql = null;
            if (compare(CREATE_STATEMENT, name)) {
                // createStatement
                constructor = getConstructor(CREATE_STATEMENT_IDX, Statement.class);
            } else if (compare(PREPARE_STATEMENT, name)) {
                // prepareStatement
                constructor = getConstructor(PREPARE_STATEMENT_IDX, PreparedStatement.class);
                sql = (String)args[0];
            } else if (compare(PREPARE_CALL, name)) {
                // prepareCall
                constructor = getConstructor(PREPARE_CALL_IDX, CallableStatement.class);
                sql = (String)args[0];
            } else {
                // do nothing, might be a future unsupported method
                // so we better bail out and let the system continue
                return statement;
            }
            return createDecorator(proxy, method, args, statement, constructor, sql);
        } catch (Exception x) {
            if (x instanceof InvocationTargetException) {
                Throwable cause = x.getCause();
                if (cause instanceof ThreadDeath) {
                    throw (ThreadDeath) cause;
                }
                if (cause instanceof VirtualMachineError) {
                    throw (VirtualMachineError) cause;
                }
            }
            logger.warn("Unable to create statement proxy for slow query report.", x);
        }
        return statement;
    }

    /**
     * Creates a proxy for a Statement.
     *
     * @param proxy         The proxy object on which the method that triggered
     *                          the creation of the statement was called.
     * @param method        The method that was called on the proxy
     * @param args          The arguments passed as part of the method call to
     *                          the proxy
     * @param statement     The statement object that is to be proxied
     * @param constructor   The constructor for the desired proxy
     * @param sql           The sql of of the statement
     *
     * @return  A new proxy for the Statement
     */
    protected Object createDecorator(Object proxy, Method method, Object[] args,
                                     Object statement, Constructor<?> constructor, String sql)
    throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Object result = null;
        StatementProxy<Statement> statementProxy =
                new StatementProxy<Statement>((Statement)statement,sql);
        result = constructor.newInstance(new Object[] { statementProxy });
        statementProxy.setActualProxy(result);
        statementProxy.setConnection(proxy);
        statementProxy.setConstructor(constructor);
        return result;
    }

    protected boolean isExecuteQuery(String methodName) {
        return EXECUTE_QUERY.equals(methodName);
    }

    protected boolean isExecuteQuery(Method method) {
        return isExecuteQuery(method.getName());
    }

    protected boolean isResultSet(Method method, boolean process) {
        return process(RESULTSET_TYPES, method, process);
    }

    /**
     * Class to measure query execute time
     *
     * @author fhanik
     *
     */
    protected class StatementProxy<T extends java.sql.Statement> implements InvocationHandler {

        protected boolean closed = false;
        protected T delegate;
        private Object actualProxy;
        private Object connection;
        private String sql;
        private Constructor<?> constructor;

        public StatementProxy(T delegate, String sql) {
            this.delegate = delegate;
            this.sql = sql;
        }
        public T getDelegate() {
            return this.delegate;
        }

        public String getSql() {
            return sql;
        }

        public void setConnection(Object proxy) {
            this.connection = proxy;
        }
        public Object getConnection() {
            return this.connection;
        }

        public void setActualProxy(Object proxy){
            this.actualProxy = proxy;
        }
        public Object getActualProxy() {
            return this.actualProxy;
        }


        public Constructor<?> getConstructor() {
            return constructor;
        }
        public void setConstructor(Constructor<?> constructor) {
            this.constructor = constructor;
        }
        public void closeInvoked() {
            if (getDelegate()!=null) {
                try {
                    getDelegate().close();
                }catch (SQLException ignore) {
                }
            }
            closed = true;
            delegate = null;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (compare(TOSTRING_VAL,method)) {
                return toString();
            }
            // was close invoked?
            boolean close = compare(CLOSE_VAL, method);
            // allow close to be called multiple times
            if (close && closed)
                return null;
            // are we calling isClosed?
            if (compare(ISCLOSED_VAL, method))
                return Boolean.valueOf(closed);
            // if we are calling anything else, bail out
            if (closed)
                throw new SQLException("Statement closed.");
            if (compare(GETCONNECTION_VAL,method)){
                return connection;
            }
            boolean process = false;
            process = isResultSet(method, process);
            // check to see if we are about to execute a query
            // if we are executing, get the current time
            Object result = null;
            try {
                // perform close cleanup
                if (close) {
                    closeInvoked();
                } else {
                    // execute the query
                    result = method.invoke(delegate, args);
                }
            } catch (Throwable t) {
                if (t instanceof InvocationTargetException
                        && t.getCause() != null) {
                    throw t.getCause();
                } else {
                    throw t;
                }
            }
            if (process && result != null) {
                Constructor<?> cons = getResultSetConstructor();
                result = cons.newInstance(new Object[]{new ResultSetProxy(actualProxy, result)});
            }
            return result;
        }

        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer(StatementProxy.class.getName());
            buf.append("[Proxy=");
            buf.append(System.identityHashCode(this));
            buf.append("; Sql=");
            buf.append(getSql());
            buf.append("; Delegate=");
            buf.append(getDelegate());
            buf.append("; Connection=");
            buf.append(getConnection());
            buf.append("]");
            return buf.toString();
        }
    }

    protected class ResultSetProxy implements InvocationHandler {

        private Object st;
        private Object delegate;

        public ResultSetProxy(Object st, Object delegate) {
            this.st = st;
            this.delegate = delegate;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getStatement")) {
                return this.st;
            } else {
                try {
                    return method.invoke(this.delegate, args);
                } catch (Throwable t) {
                    if (t instanceof InvocationTargetException
                            && t.getCause() != null) {
                        throw t.getCause();
                    } else {
                        throw t;
                    }
                }
            }
        }
    }
}
