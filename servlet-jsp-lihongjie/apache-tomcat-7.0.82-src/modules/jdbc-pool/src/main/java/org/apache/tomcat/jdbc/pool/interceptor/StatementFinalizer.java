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

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PooledConnection;
/**
 * Keeps track of statements associated with a connection and invokes close upon {@link java.sql.Connection#close()}
 * Useful for applications that dont close the associated statements after being done with a connection.
 * @author fhanik
 *
 */
public class StatementFinalizer extends AbstractCreateStatementInterceptor {
    private static final Log log = LogFactory.getLog(StatementFinalizer.class);

    protected ArrayList<WeakReference<Statement>> statements = new ArrayList<WeakReference<Statement>>();

    @Override
    public Object createStatement(Object proxy, Method method, Object[] args, Object statement, long time) {
        try {
            if (statement instanceof Statement)
                statements.add(new WeakReference<Statement>((Statement)statement));
        }catch (ClassCastException x) {
            //ignore this one
        }
        return statement;
    }

    @Override
    public void closeInvoked() {
        while (statements.size()>0) {
            WeakReference<Statement> ws = statements.remove(0);
            Statement st = ws.get();
            if (st!=null) {
                try {
                    st.close();
                } catch (Exception ignore) {
                    if (log.isDebugEnabled()) {
                        log.debug("Unable to closed statement upon connection close.",ignore);
                    }
                }
            }
        }
    }

    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        statements.clear();
        super.reset(parent, con);
    }


}
