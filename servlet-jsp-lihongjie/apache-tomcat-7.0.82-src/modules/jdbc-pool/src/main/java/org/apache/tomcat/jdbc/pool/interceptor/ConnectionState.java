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

import java.lang.reflect.Method;
import java.sql.SQLException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PooledConnection;

/**
 * Interceptor that keep track of connection state to avoid roundtrips to the database.
 * The {@link org.apache.tomcat.jdbc.pool.ConnectionPool} is optimized to do as little work as possible.
 * The pool itself doesn't remember settings like {@link java.sql.Connection#setAutoCommit(boolean)},
 * {@link java.sql.Connection#setReadOnly(boolean)}, {@link java.sql.Connection#setCatalog(String)} or
 * {@link java.sql.Connection#setTransactionIsolation(int)}. It relies on the application to remember how and when
 * these settings have been applied.
 * In the cases where the application code doesn't know or want to keep track of the state, this interceptor helps cache the
 * state, and it also avoids roundtrips to the database asking for it.
 * @author fhanik
 *
 */

public class ConnectionState extends JdbcInterceptor  {
    private static final Log log = LogFactory.getLog(ConnectionState.class);

    protected final String[] readState = {"getAutoCommit","getTransactionIsolation","isReadOnly","getCatalog"};
    protected final String[] writeState = {"setAutoCommit","setTransactionIsolation","setReadOnly","setCatalog"};

    protected Boolean autoCommit = null;
    protected Integer transactionIsolation = null;
    protected Boolean readOnly = null;
    protected String catalog = null;


    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        if (parent==null || con==null) {
            //we are resetting, reset our defaults
            autoCommit = null;
            transactionIsolation = null;
            readOnly = null;
            catalog = null;
            return;
        }
        PoolConfiguration poolProperties = parent.getPoolProperties();
        if (poolProperties.getDefaultTransactionIsolation()!=DataSourceFactory.UNKNOWN_TRANSACTIONISOLATION) {
            try {
                if (transactionIsolation==null || transactionIsolation.intValue()!=poolProperties.getDefaultTransactionIsolation()) {
                    con.getConnection().setTransactionIsolation(poolProperties.getDefaultTransactionIsolation());
                    transactionIsolation = Integer.valueOf(poolProperties.getDefaultTransactionIsolation());
                }
            }catch (SQLException x) {
                transactionIsolation = null;
                log.error("Unable to reset transaction isolation state to connection.",x);
            }
        }
        if (poolProperties.getDefaultReadOnly()!=null) {
            try {
                if (readOnly==null || readOnly.booleanValue()!=poolProperties.getDefaultReadOnly().booleanValue()) {
                    con.getConnection().setReadOnly(poolProperties.getDefaultReadOnly().booleanValue());
                    readOnly = poolProperties.getDefaultReadOnly();
                }
            }catch (SQLException x) {
                readOnly = null;
                log.error("Unable to reset readonly state to connection.",x);
            }
        }
        if (poolProperties.getDefaultAutoCommit()!=null) {
            try {
                if (autoCommit==null || autoCommit.booleanValue()!=poolProperties.getDefaultAutoCommit().booleanValue()) {
                    con.getConnection().setAutoCommit(poolProperties.getDefaultAutoCommit().booleanValue());
                    autoCommit = poolProperties.getDefaultAutoCommit();
                }
            }catch (SQLException x) {
                autoCommit = null;
                log.error("Unable to reset autocommit state to connection.",x);
            }
        }
        if (poolProperties.getDefaultCatalog()!=null) {
            try {
                if (catalog==null || (!catalog.equals(poolProperties.getDefaultCatalog()))) {
                    con.getConnection().setCatalog(poolProperties.getDefaultCatalog());
                    catalog = poolProperties.getDefaultCatalog();
                }
            }catch (SQLException x) {
                catalog = null;
                log.error("Unable to reset default catalog state to connection.",x);
            }
        }

    }


    @Override
    public void disconnected(ConnectionPool parent, PooledConnection con, boolean finalizing) {
        //we are resetting, reset our defaults
        autoCommit = null;
        transactionIsolation = null;
        readOnly = null;
        catalog = null;
        super.disconnected(parent, con, finalizing);
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        boolean read = false;
        int index = -1;
        for (int i=0; (!read) && i<readState.length; i++) {
            read = compare(name,readState[i]);
            if (read) index = i;
        }
        boolean write = false;
        for (int i=0; (!write) && (!read) && i<writeState.length; i++) {
            write = compare(name,writeState[i]);
            if (write) index = i;
        }
        Object result = null;
        if (read) {
            switch (index) {
                case 0:{result = autoCommit; break;}
                case 1:{result = transactionIsolation; break;}
                case 2:{result = readOnly; break;}
                case 3:{result = catalog; break;}
                default: // NOOP
            }
            //return cached result, if we have it
            if (result!=null) return result;
        }

        result = super.invoke(proxy, method, args);
        if (read || write) {
            switch (index) {
                case 0:{autoCommit = (Boolean) (read?result:args[0]); break;}
                case 1:{transactionIsolation = (Integer)(read?result:args[0]); break;}
                case 2:{readOnly = (Boolean)(read?result:args[0]); break;}
                case 3:{catalog = (String)(read?result:args[0]); break;}
            }
        }
        return result;
    }

}
