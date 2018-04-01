/* Licensed to the Apache Software Foundation (ASF) under one or more
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorProperty;
import org.apache.tomcat.jdbc.pool.PooledConnection;

/**
 * Interceptor that caches {@code PreparedStatement} and/or
 * {@code CallableStatement} instances on a connection.
 */
public class StatementCache extends StatementDecoratorInterceptor {
    protected static final String[] ALL_TYPES = new String[] {PREPARE_STATEMENT,PREPARE_CALL};
    protected static final String[] CALLABLE_TYPE = new String[] {PREPARE_CALL};
    protected static final String[] PREPARED_TYPE = new String[] {PREPARE_STATEMENT};
    protected static final String[] NO_TYPE = new String[] {};

    protected static final String STATEMENT_CACHE_ATTR = StatementCache.class.getName() + ".cache";

    /*begin properties for the statement cache*/
    private boolean cachePrepared = true;
    private boolean cacheCallable = false;
    private int maxCacheSize = 50;
    private PooledConnection pcon;
    private String[] types;


    public boolean isCachePrepared() {
        return cachePrepared;
    }

    public boolean isCacheCallable() {
        return cacheCallable;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public String[] getTypes() {
        return types;
    }

    public AtomicInteger getCacheSize() {
        return cacheSize;
    }

    @Override
    public void setProperties(Map<String, InterceptorProperty> properties) {
        super.setProperties(properties);
        InterceptorProperty p = properties.get("prepared");
        if (p!=null) cachePrepared = p.getValueAsBoolean(cachePrepared);
        p = properties.get("callable");
        if (p!=null) cacheCallable = p.getValueAsBoolean(cacheCallable);
        p = properties.get("max");
        if (p!=null) maxCacheSize = p.getValueAsInt(maxCacheSize);
        if (cachePrepared && cacheCallable) {
            this.types = ALL_TYPES;
        } else if (cachePrepared) {
            this.types = PREPARED_TYPE;
        } else if (cacheCallable) {
            this.types = CALLABLE_TYPE;
        } else {
            this.types = NO_TYPE;
        }

    }
    /*end properties for the statement cache*/

    /*begin the cache size*/
    private static ConcurrentHashMap<ConnectionPool,AtomicInteger> cacheSizeMap =
        new ConcurrentHashMap<ConnectionPool,AtomicInteger>();

    private AtomicInteger cacheSize;

    @Override
    public void poolStarted(ConnectionPool pool) {
        cacheSizeMap.putIfAbsent(pool, new AtomicInteger(0));
        super.poolStarted(pool);
    }

    @Override
    public void poolClosed(ConnectionPool pool) {
        cacheSizeMap.remove(pool);
        super.poolClosed(pool);
    }
    /*end the cache size*/

    /*begin the actual statement cache*/
    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        super.reset(parent, con);
        if (parent==null) {
            cacheSize = null;
            this.pcon = null;
        } else {
            cacheSize = cacheSizeMap.get(parent);
            this.pcon = con;
            if (!pcon.getAttributes().containsKey(STATEMENT_CACHE_ATTR)) {
                ConcurrentHashMap<CacheKey,CachedStatement> cache = new ConcurrentHashMap<CacheKey, CachedStatement>();
                pcon.getAttributes().put(STATEMENT_CACHE_ATTR,cache);
            }
        }
    }

    @Override
    public void disconnected(ConnectionPool parent, PooledConnection con, boolean finalizing) {
        @SuppressWarnings("unchecked")
        ConcurrentHashMap<CacheKey,CachedStatement> statements =
            (ConcurrentHashMap<CacheKey,CachedStatement>)con.getAttributes().get(STATEMENT_CACHE_ATTR);

        if (statements!=null) {
            for (Map.Entry<CacheKey, CachedStatement> p : statements.entrySet()) {
                closeStatement(p.getValue());
            }
            statements.clear();
        }

        super.disconnected(parent, con, finalizing);
    }

    public void closeStatement(CachedStatement st) {
        if (st==null) return;
        st.forceClose();
    }

    @Override
    protected Object createDecorator(Object proxy, Method method, Object[] args,
                                     Object statement, Constructor<?> constructor, String sql)
    throws InstantiationException, IllegalAccessException, InvocationTargetException {
        boolean process = process(this.types, method, false);
        if (process) {
            Object result = null;
            CachedStatement statementProxy = new CachedStatement((Statement)statement,sql);
            result = constructor.newInstance(new Object[] { statementProxy });
            statementProxy.setActualProxy(result);
            statementProxy.setConnection(proxy);
            statementProxy.setConstructor(constructor);
            statementProxy.setCacheKey(createCacheKey(method, args));
            return result;
        } else {
            return super.createDecorator(proxy, method, args, statement, constructor, sql);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean process = process(this.types, method, false);
        if (process && args.length>0 && args[0] instanceof String) {
            CachedStatement statement = isCached(method, args);
            if (statement!=null) {
                //remove it from the cache since it is used
                removeStatement(statement);
                return statement.getActualProxy();
            } else {
                return super.invoke(proxy, method, args);
            }
        } else {
            return super.invoke(proxy,method,args);
        }
    }

    /**
     * @param sql  Ignored
     *
     * @return {@code null}
     *
     * @deprecated Unused. Will be removed in Tomcat 9
     */
    @Deprecated
    public CachedStatement isCached(String sql) {
        return null;
    }

    public CachedStatement isCached(Method method, Object[] args) {
        @SuppressWarnings("unchecked")
        ConcurrentHashMap<CacheKey,CachedStatement> cache =
            (ConcurrentHashMap<CacheKey,CachedStatement>)pcon.getAttributes().get(STATEMENT_CACHE_ATTR);
        return cache.get(createCacheKey(method, args));
    }

    public boolean cacheStatement(CachedStatement proxy) {
        @SuppressWarnings("unchecked")
        ConcurrentHashMap<CacheKey,CachedStatement> cache =
            (ConcurrentHashMap<CacheKey,CachedStatement>)pcon.getAttributes().get(STATEMENT_CACHE_ATTR);
        if (proxy.getCacheKey()==null) {
            return false;
        } else if (cache.containsKey(proxy.getCacheKey())) {
            return false;
        } else if (cacheSize.get()>=maxCacheSize) {
            return false;
        } else if (cacheSize.incrementAndGet()>maxCacheSize) {
            cacheSize.decrementAndGet();
            return false;
        } else {
            //cache the statement
            cache.put(proxy.getCacheKey(), proxy);
            return true;
        }
    }

    public boolean removeStatement(CachedStatement proxy) {
        @SuppressWarnings("unchecked")
        ConcurrentHashMap<CacheKey,CachedStatement> cache =
            (ConcurrentHashMap<CacheKey,CachedStatement>)pcon.getAttributes().get(STATEMENT_CACHE_ATTR);
        if (cache.remove(proxy.getCacheKey()) != null) {
            cacheSize.decrementAndGet();
            return true;
        } else {
            return false;
        }
    }
    /*end the actual statement cache*/


    protected class CachedStatement extends StatementDecoratorInterceptor.StatementProxy<Statement> {
        boolean cached = false;
        CacheKey key;
        public CachedStatement(Statement parent, String sql) {
            super(parent, sql);
        }

        @Override
        public void closeInvoked() {
            //should we cache it
            boolean shouldClose = true;
            if (cacheSize.get() < maxCacheSize) {
                //cache a proxy so that we don't reuse the facade
                CachedStatement proxy = new CachedStatement(getDelegate(),getSql());
                proxy.setCacheKey(getCacheKey());
                try {
                    // clear Resultset
                    ResultSet result = getDelegate().getResultSet();
                    if (result != null && !result.isClosed()) {
                        result.close();
                    }
                    //create a new facade
                    Object actualProxy = getConstructor().newInstance(new Object[] { proxy });
                    proxy.setActualProxy(actualProxy);
                    proxy.setConnection(getConnection());
                    proxy.setConstructor(getConstructor());
                    if (cacheStatement(proxy)) {
                        proxy.cached = true;
                        shouldClose = false;
                    }
                } catch (Exception x) {
                    removeStatement(proxy);
                }
            }
            if (shouldClose) {
                super.closeInvoked();
            }
            closed = true;
            delegate = null;

        }

        public void forceClose() {
            removeStatement(this);
            super.closeInvoked();
        }

        public CacheKey getCacheKey() {
            return key;
        }

        public void setCacheKey(CacheKey cacheKey) {
            key = cacheKey;
        }

    }

    protected CacheKey createCacheKey(Method method, Object[] args) {
        return createCacheKey(method.getName(), args);
    }

    protected CacheKey createCacheKey(String methodName, Object[] args) {
        CacheKey key = null;
        if (compare(PREPARE_STATEMENT, methodName)) {
            key = new CacheKey(PREPARE_STATEMENT, args);
        } else if (compare(PREPARE_CALL, methodName)) {
            key = new CacheKey(PREPARE_CALL, args);
        }
        return key;
    }


    private static final class CacheKey {
        private final String stmtType;
        private final Object[] args;
        private CacheKey(String type, Object[] methodArgs) {
            stmtType = type;
            args = methodArgs;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(args);
            result = prime * result
                    + ((stmtType == null) ? 0 : stmtType.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CacheKey other = (CacheKey) obj;
            if (!Arrays.equals(args, other.args))
                return false;
            if (stmtType == null) {
                if (other.stmtType != null)
                    return false;
            } else if (!stmtType.equals(other.stmtType))
                return false;
            return true;
        }
    }
}
