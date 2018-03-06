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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.sql.XAConnection;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorDefinition;

/**
 *
 * The DataSource proxy lets us implements methods that don't exist in the current
 * compiler JDK but might be methods that are part of a future JDK DataSource interface.
 * <br>
 * It's a trick to work around compiler issues when implementing interfaces. For example,
 * I could put in Java 6 methods of javax.sql.DataSource here, and compile it with JDK 1.5
 * and still be able to run under Java 6 without getting NoSuchMethodException.
 *
 * @author Filip Hanik
 * @version 1.0
 */

public class DataSourceProxy implements PoolConfiguration {
    private static final Log log = LogFactory.getLog(DataSourceProxy.class);

    protected volatile ConnectionPool pool = null;

    protected volatile PoolConfiguration poolProperties = null;

    public DataSourceProxy() {
        this(new PoolProperties());
    }

    public DataSourceProxy(PoolConfiguration poolProperties) {
        if (poolProperties == null) throw new NullPointerException("PoolConfiguration can not be null.");
        this.poolProperties = poolProperties;
    }


    @SuppressWarnings("unused") // Has to match signature in DataSource
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // we are not a wrapper of anything
        return false;
    }


    @SuppressWarnings("unused") // Has to match signature in DataSource
    public <T> T unwrap(Class<T> iface) throws SQLException {
        //we can't unwrap anything
        return null;
    }

    /**
     * {@link javax.sql.DataSource#getConnection()}
     */
    public Connection getConnection(String username, String password) throws SQLException {
        if (this.getPoolProperties().isAlternateUsernameAllowed()) {
            if (pool == null)
                return createPool().getConnection(username,password);
            return pool.getConnection(username,password);
        } else {
            return getConnection();
        }
    }

    public PoolConfiguration getPoolProperties() {
        return poolProperties;
    }

    /**
     * Sets up the connection pool, by creating a pooling driver.
     * @return Driver
     * @throws SQLException
     */
    public ConnectionPool createPool() throws SQLException {
        if (pool != null) {
            return pool;
        } else {
            return pCreatePool();
        }
    }

    /**
     * Sets up the connection pool, by creating a pooling driver.
     * @return Driver
     * @throws SQLException
     */
    private synchronized ConnectionPool pCreatePool() throws SQLException {
        if (pool != null) {
            return pool;
        } else {
            pool = new ConnectionPool(poolProperties);
            return pool;
        }
    }

    /**
     * {@link javax.sql.DataSource#getConnection()}
     */

    public Connection getConnection() throws SQLException {
        if (pool == null)
            return createPool().getConnection();
        return pool.getConnection();
    }

    /**
     * Invokes an sync operation to retrieve the connection.
     * @return a Future containing a reference to the connection when it becomes available
     * @throws SQLException
     */
    public Future<Connection> getConnectionAsync() throws SQLException {
        if (pool == null)
            return createPool().getConnectionAsync();
        return pool.getConnectionAsync();
    }

    /**
     * {@link javax.sql.XADataSource#getXAConnection()}
     */
    public XAConnection getXAConnection() throws SQLException {
        Connection con = getConnection();
        if (con instanceof XAConnection) {
            return (XAConnection)con;
        } else {
            try {
                con.close();
            } catch (Exception ignore) {
                // Ignore
            }
            throw new SQLException("Connection from pool does not implement javax.sql.XAConnection");
        }
    }

    /**
     * {@link javax.sql.XADataSource#getXAConnection(String, String)}
     */
    public XAConnection getXAConnection(String username, String password) throws SQLException {
        Connection con = getConnection(username, password);
        if (con instanceof XAConnection) {
            return (XAConnection)con;
        } else {
            try {
                con.close();
            } catch (Exception ignore) {
                // Ignore
            }
            throw new SQLException("Connection from pool does not implement javax.sql.XAConnection");
        }
    }


    /**
     * {@link javax.sql.DataSource#getConnection()}
     */
    public javax.sql.PooledConnection getPooledConnection() throws SQLException {
        return (javax.sql.PooledConnection) getConnection();
    }

    /**
     * {@link javax.sql.DataSource#getConnection()}
     * @param username unused
     * @param password unused
     */
    public javax.sql.PooledConnection getPooledConnection(String username,
            String password) throws SQLException {
        return (javax.sql.PooledConnection) getConnection();
    }

    public ConnectionPool getPool() {
        return pool;
    }


    public void close() {
        close(false);
    }
    public void close(boolean all) {
        try {
            if (pool != null) {
                final ConnectionPool p = pool;
                pool = null;
                if (p!=null) {
                    p.close(all);
                }
            }
        }catch (Exception x) {
            log.warn("Error duing connection pool closure.", x);
        }
    }

    public int getPoolSize() {
        final ConnectionPool p = pool;
        if (p == null) return 0;
        else return p.getSize();
    }


    @Override
    public String toString() {
        return super.toString()+"{"+getPoolProperties()+"}";
    }


/*-----------------------------------------------------------------------*/
//      PROPERTIES WHEN NOT USED WITH FACTORY
/*------------------------------------------------------------------------*/

    /**
     * {@inheritDoc}
     */

    @Override
    public String getPoolName() {
        return pool.getName();
    }


    public void setPoolProperties(PoolConfiguration poolProperties) {
        this.poolProperties = poolProperties;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDriverClassName(String driverClassName) {
        this.poolProperties.setDriverClassName(driverClassName);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInitialSize(int initialSize) {
        this.poolProperties.setInitialSize(initialSize);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInitSQL(String initSQL) {
        this.poolProperties.setInitSQL(initSQL);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setLogAbandoned(boolean logAbandoned) {
        this.poolProperties.setLogAbandoned(logAbandoned);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxActive(int maxActive) {
        this.poolProperties.setMaxActive(maxActive);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxIdle(int maxIdle) {
        this.poolProperties.setMaxIdle(maxIdle);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxWait(int maxWait) {
        this.poolProperties.setMaxWait(maxWait);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.poolProperties.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMinIdle(int minIdle) {
        this.poolProperties.setMinIdle(minIdle);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.poolProperties.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setPassword(String password) {
        this.poolProperties.setPassword(password);
        this.poolProperties.getDbProperties().setProperty("password",this.poolProperties.getPassword());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.poolProperties.setRemoveAbandoned(removeAbandoned);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.poolProperties.setRemoveAbandonedTimeout(removeAbandonedTimeout);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.poolProperties.setTestOnBorrow(testOnBorrow);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestOnConnect(boolean testOnConnect) {
        this.poolProperties.setTestOnConnect(testOnConnect);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestOnReturn(boolean testOnReturn) {
        this.poolProperties.setTestOnReturn(testOnReturn);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.poolProperties.setTestWhileIdle(testWhileIdle);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.poolProperties.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUrl(String url) {
        this.poolProperties.setUrl(url);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUsername(String username) {
        this.poolProperties.setUsername(username);
        this.poolProperties.getDbProperties().setProperty("user",getPoolProperties().getUsername());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidationInterval(long validationInterval) {
        this.poolProperties.setValidationInterval(validationInterval);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidationQuery(String validationQuery) {
        this.poolProperties.setValidationQuery(validationQuery);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidatorClassName(String className) {
        this.poolProperties.setValidatorClassName(className);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.poolProperties.setValidationQueryTimeout(validationQueryTimeout);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setJdbcInterceptors(String interceptors) {
        this.getPoolProperties().setJdbcInterceptors(interceptors);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setJmxEnabled(boolean enabled) {
        this.getPoolProperties().setJmxEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setFairQueue(boolean fairQueue) {
        this.getPoolProperties().setFairQueue(fairQueue);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUseLock(boolean useLock) {
        this.getPoolProperties().setUseLock(useLock);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultCatalog(String catalog) {
        this.getPoolProperties().setDefaultCatalog(catalog);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultAutoCommit(Boolean autocommit) {
        this.getPoolProperties().setDefaultAutoCommit(autocommit);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
        this.getPoolProperties().setDefaultTransactionIsolation(defaultTransactionIsolation);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setConnectionProperties(String properties) {
        try {
            java.util.Properties prop = DataSourceFactory
                    .getProperties(properties);
            Iterator<?> i = prop.keySet().iterator();
            while (i.hasNext()) {
                String key = (String) i.next();
                String value = prop.getProperty(key);
                getPoolProperties().getDbProperties().setProperty(key, value);
            }

        } catch (Exception x) {
            log.error("Unable to parse connection properties.", x);
            throw new RuntimeException(x);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUseEquals(boolean useEquals) {
        this.getPoolProperties().setUseEquals(useEquals);
    }

    /**
     * no-op
     * {@link javax.sql.DataSource#getParentLogger}
     */
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    /**
     * no-op
     * {@link javax.sql.DataSource#getLogWriter}
     */
    @SuppressWarnings("unused") // Has to match signature in DataSource
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }


    /**
     * no-op
     * {@link javax.sql.DataSource#setLogWriter(PrintWriter)}
     */
    @SuppressWarnings("unused") // Has to match signature in DataSource
    public void setLogWriter(PrintWriter out) throws SQLException {
        // NOOP
    }

    /**
     * no-op
     * {@link javax.sql.DataSource#getLoginTimeout}
     */
    public int getLoginTimeout() {
        if (poolProperties == null) {
            return 0;
        } else {
            return poolProperties.getMaxWait() / 1000;
        }
    }

    /**
     * {@link javax.sql.DataSource#setLoginTimeout(int)}
     */
    public void setLoginTimeout(int i) {
        if (poolProperties == null) {
            return;
        } else {
            poolProperties.setMaxWait(1000 * i);
        }

    }


    /**
     * {@inheritDoc}
     */

    @Override
    public int getSuspectTimeout() {
        return getPoolProperties().getSuspectTimeout();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setSuspectTimeout(int seconds) {
        getPoolProperties().setSuspectTimeout(seconds);
    }

  //===============================================================================
//  Expose JMX attributes through Tomcat's dynamic reflection
//===============================================================================
    /**
     * If the pool has not been created, it will be created during this call.
     * @return the number of established but idle connections
     */
    public int getIdle() {
        try {
            return createPool().getIdle();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * {@link #getIdle()}
     */
    public int getNumIdle() {
        return getIdle();
    }

    /**
     * Forces an abandon check on the connection pool.
     * If connections that have been abandoned exists, they will be closed during this run
     */
    public void checkAbandoned() {
        try {
            createPool().checkAbandoned();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * Forces a check for resizing of the idle connections
     */
    public void checkIdle() {
        try {
            createPool().checkIdle();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * @return number of connections in use by the application
     */
    public int getActive() {
        try {
            return createPool().getActive();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * @return number of connections in use by the application
     * {@link DataSource#getActive()}
     */
    public int getNumActive() {
        return getActive();
    }

    /**
     * @return number of threads waiting for a connection
     */
    public int getWaitCount() {
        try {
            return createPool().getWaitCount();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * @return the current size of the pool
     */
    public int getSize() {
        try {
            return createPool().getSize();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * Performs a validation on idle connections
     */
    public void testIdle() {
        try {
            createPool().testAllIdle();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections borrowed from this pool.
     * @return the borrowed connection count
     */
    public long getBorrowedCount() {
        try {
            return createPool().getBorrowedCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections returned to this pool.
     * @return the returned connection count
     */
    public long getReturnedCount() {
        try {
            return createPool().getReturnedCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections created by this pool.
     * @return the created connection count
     */
    public long getCreatedCount() {
        try {
            return createPool().getCreatedCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections released from this pool.
     * @return the released connection count
     */
    public long getReleasedCount() {
        try {
            return createPool().getReleasedCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections released by remove abandoned.
     * @return the PoolCleaner removed abandoned connection count
     */
    public long getRemoveAbandonedCount() {
        try {
            return createPool().getRemoveAbandonedCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections released by eviction.
     * @return the PoolCleaner evicted idle connection count
     */
    public long getReleasedIdleCount() {
        try {
            return createPool().getReleasedIdleCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * The total number of connections reconnected by this pool.
     * @return the reconnected connection count
     */
    public long getReconnectedCount() {
        try {
            return createPool().getReconnectedCount();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /**
     * reset the statistics of this pool.
     */
    public void resetStats() {
        try {
            createPool().resetStats();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    //=========================================================
    //  PROPERTIES / CONFIGURATION
    //=========================================================

    /**
     * {@inheritDoc}
     */

    @Override
    public String getConnectionProperties() {
        return getPoolProperties().getConnectionProperties();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Properties getDbProperties() {
        return getPoolProperties().getDbProperties();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getDefaultCatalog() {
        return getPoolProperties().getDefaultCatalog();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getDefaultTransactionIsolation() {
        return getPoolProperties().getDefaultTransactionIsolation();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getDriverClassName() {
        return getPoolProperties().getDriverClassName();
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public int getInitialSize() {
        return getPoolProperties().getInitialSize();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getInitSQL() {
        return getPoolProperties().getInitSQL();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getJdbcInterceptors() {
        return getPoolProperties().getJdbcInterceptors();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMaxActive() {
        return getPoolProperties().getMaxActive();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMaxIdle() {
        return getPoolProperties().getMaxIdle();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMaxWait() {
        return getPoolProperties().getMaxWait();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMinEvictableIdleTimeMillis() {
        return getPoolProperties().getMinEvictableIdleTimeMillis();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMinIdle() {
        return getPoolProperties().getMinIdle();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public long getMaxAge() {
        return getPoolProperties().getMaxAge();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getName() {
        return getPoolProperties().getName();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getNumTestsPerEvictionRun() {
        return getPoolProperties().getNumTestsPerEvictionRun();
    }

    /**
     * @return DOES NOT RETURN THE PASSWORD, IT WOULD SHOW UP IN JMX
     */
    @Override
    public String getPassword() {
        return "Password not available as DataSource/JMX operation.";
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getRemoveAbandonedTimeout() {
        return getPoolProperties().getRemoveAbandonedTimeout();
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public int getTimeBetweenEvictionRunsMillis() {
        return getPoolProperties().getTimeBetweenEvictionRunsMillis();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getUrl() {
        return getPoolProperties().getUrl();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getUsername() {
        return getPoolProperties().getUsername();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public long getValidationInterval() {
        return getPoolProperties().getValidationInterval();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getValidationQuery() {
        return getPoolProperties().getValidationQuery();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getValidationQueryTimeout() {
        return getPoolProperties().getValidationQueryTimeout();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getValidatorClassName() {
        return getPoolProperties().getValidatorClassName();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Validator getValidator() {
        return getPoolProperties().getValidator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValidator(Validator validator) {
        getPoolProperties().setValidator(validator);
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isAccessToUnderlyingConnectionAllowed() {
        return getPoolProperties().isAccessToUnderlyingConnectionAllowed();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean isDefaultAutoCommit() {
        return getPoolProperties().isDefaultAutoCommit();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean isDefaultReadOnly() {
        return getPoolProperties().isDefaultReadOnly();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isLogAbandoned() {
        return getPoolProperties().isLogAbandoned();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isPoolSweeperEnabled() {
        return getPoolProperties().isPoolSweeperEnabled();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isRemoveAbandoned() {
        return getPoolProperties().isRemoveAbandoned();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getAbandonWhenPercentageFull() {
        return getPoolProperties().getAbandonWhenPercentageFull();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestOnBorrow() {
        return getPoolProperties().isTestOnBorrow();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestOnConnect() {
        return getPoolProperties().isTestOnConnect();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestOnReturn() {
        return getPoolProperties().isTestOnReturn();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestWhileIdle() {
        return getPoolProperties().isTestWhileIdle();
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean getDefaultAutoCommit() {
        return getPoolProperties().getDefaultAutoCommit();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean getDefaultReadOnly() {
        return getPoolProperties().getDefaultReadOnly();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public InterceptorDefinition[] getJdbcInterceptorsAsArray() {
        return getPoolProperties().getJdbcInterceptorsAsArray();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean getUseLock() {
        return getPoolProperties().getUseLock();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isFairQueue() {
        return getPoolProperties().isFairQueue();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isJmxEnabled() {
        return getPoolProperties().isJmxEnabled();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isUseEquals() {
        return getPoolProperties().isUseEquals();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setAbandonWhenPercentageFull(int percentage) {
        getPoolProperties().setAbandonWhenPercentageFull(percentage);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
        getPoolProperties().setAccessToUnderlyingConnectionAllowed(accessToUnderlyingConnectionAllowed);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDbProperties(Properties dbProperties) {
        getPoolProperties().setDbProperties(dbProperties);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultReadOnly(Boolean defaultReadOnly) {
        getPoolProperties().setDefaultReadOnly(defaultReadOnly);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxAge(long maxAge) {
        getPoolProperties().setMaxAge(maxAge);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setName(String name) {
        getPoolProperties().setName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataSource(Object ds) {
        getPoolProperties().setDataSource(ds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getDataSource() {
        return getPoolProperties().getDataSource();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataSourceJNDI(String jndiDS) {
        getPoolProperties().setDataSourceJNDI(jndiDS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataSourceJNDI() {
        return getPoolProperties().getDataSourceJNDI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlternateUsernameAllowed() {
        return getPoolProperties().isAlternateUsernameAllowed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlternateUsernameAllowed(boolean alternateUsernameAllowed) {
        getPoolProperties().setAlternateUsernameAllowed(alternateUsernameAllowed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommitOnReturn(boolean commitOnReturn) {
        getPoolProperties().setCommitOnReturn(commitOnReturn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCommitOnReturn() {
        return getPoolProperties().getCommitOnReturn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRollbackOnReturn(boolean rollbackOnReturn) {
        getPoolProperties().setRollbackOnReturn(rollbackOnReturn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRollbackOnReturn() {
        return getPoolProperties().getRollbackOnReturn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUseDisposableConnectionFacade(boolean useDisposableConnectionFacade) {
        getPoolProperties().setUseDisposableConnectionFacade(useDisposableConnectionFacade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getUseDisposableConnectionFacade() {
        return getPoolProperties().getUseDisposableConnectionFacade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLogValidationErrors(boolean logValidationErrors) {
        getPoolProperties().setLogValidationErrors(logValidationErrors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLogValidationErrors() {
        return getPoolProperties().getLogValidationErrors();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getPropagateInterruptState() {
        return getPoolProperties().getPropagateInterruptState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPropagateInterruptState(boolean propagateInterruptState) {
        getPoolProperties().setPropagateInterruptState(propagateInterruptState);
    }

    @Override
    public boolean isIgnoreExceptionOnPreLoad() {
        return getPoolProperties().isIgnoreExceptionOnPreLoad();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIgnoreExceptionOnPreLoad(boolean ignoreExceptionOnPreLoad) {
        getPoolProperties().setIgnoreExceptionOnPreLoad(ignoreExceptionOnPreLoad);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getUseStatementFacade() {
        return getPoolProperties().getUseStatementFacade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUseStatementFacade(boolean useStatementFacade) {
        getPoolProperties().setUseStatementFacade(useStatementFacade);
    }

    public void purge()  {
        try {
            createPool().purge();
        }catch (SQLException x) {
            log.error("Unable to purge pool.",x);
        }
    }

    public void purgeOnReturn() {
        try {
            createPool().purgeOnReturn();
        }catch (SQLException x) {
            log.error("Unable to purge pool.",x);
        }
    }
}
