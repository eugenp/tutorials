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


import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;

/**
 * Represents a pooled connection
 * and holds a reference to the {@link java.sql.Connection} object
 * @author Filip Hanik
 * @version 1.0
 */
public class PooledConnection {
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(PooledConnection.class);

    public static final String PROP_USER = PoolUtilities.PROP_USER;

    public static final String PROP_PASSWORD = PoolUtilities.PROP_PASSWORD;

    /**
     * Validate when connection is borrowed flag
     */
    public static final int VALIDATE_BORROW = 1;
    /**
     * Validate when connection is returned flag
     */
    public static final int VALIDATE_RETURN = 2;
    /**
     * Validate when connection is idle flag
     */
    public static final int VALIDATE_IDLE = 3;
    /**
     * Validate when connection is initialized flag
     */
    public static final int VALIDATE_INIT = 4;
    /**
     * The properties for the connection pool
     */
    protected PoolConfiguration poolProperties;
    /**
     * The underlying database connection
     */
    private volatile java.sql.Connection connection;

    /**
     * If using a XAConnection underneath.
     */
    protected volatile javax.sql.XAConnection xaConnection;
    /**
     * When we track abandon traces, this string holds the thread dump
     */
    private String abandonTrace = null;
    /**
     * Timestamp the connection was last 'touched' by the pool
     */
    private volatile long timestamp;
    /**
     * Lock for this connection only
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    /**
     * Set to true if this connection has been discarded by the pool
     */
    private volatile boolean discarded = false;
    /**
     * The Timestamp when the last time the connect() method was called successfully
     */
    private volatile long lastConnected = -1;
    /**
     * timestamp to keep track of validation intervals
     */
    private volatile long lastValidated = System.currentTimeMillis();
    /**
     * The parent
     */
    protected ConnectionPool parent;

    private HashMap<Object, Object> attributes = new HashMap<Object, Object>();

    private volatile long connectionVersion=0;

    /**
     * Weak reference to cache the list of interceptors for this connection
     * so that we don't create a new list of interceptors each time we borrow
     * the connection
     */
    private volatile JdbcInterceptor handler = null;

    private AtomicBoolean released = new AtomicBoolean(false);

    private volatile boolean suspect = false;

    private java.sql.Driver driver = null;

    /**
     * Constructor
     * @param prop - pool properties
     * @param parent - the parent connection pool
     */
    public PooledConnection(PoolConfiguration prop, ConnectionPool parent) {
        poolProperties = prop;
        this.parent = parent;
        connectionVersion = parent.getPoolVersion();
    }

    public long getConnectionVersion() {
        return connectionVersion;
    }

    public boolean checkUser(String username, String password) {
        if (!getPoolProperties().isAlternateUsernameAllowed()) return true;

        if (username==null) username = poolProperties.getUsername();
        if (password==null) password = poolProperties.getPassword();

        String storedUsr = (String)getAttributes().get(PROP_USER);
        String storedPwd = (String)getAttributes().get(PROP_PASSWORD);

        boolean result = (username==null && storedUsr==null);
        result = (result || (username!=null && username.equals(storedUsr)));

        result = result && ((password==null && storedPwd==null) || (password!=null && password.equals(storedPwd)));

        if (username==null)  getAttributes().remove(PROP_USER); else getAttributes().put(PROP_USER, username);
        if (password==null)  getAttributes().remove(PROP_PASSWORD); else getAttributes().put(PROP_PASSWORD, password);

        return result;
    }

    /**
     * Connects the underlying connection to the database.
     * @throws SQLException if the method {@link #release()} has been called.
     * @throws SQLException if driver instantiation fails
     * @throws SQLException if a call to {@link java.sql.Driver#connect(String, java.util.Properties)} fails.
     * @throws SQLException if default properties are configured and a call to
     * {@link java.sql.Connection#setAutoCommit(boolean)}, {@link java.sql.Connection#setCatalog(String)},
     * {@link java.sql.Connection#setTransactionIsolation(int)} or {@link java.sql.Connection#setReadOnly(boolean)} fails.
     */
    public void connect() throws SQLException {
        if (released.get()) throw new SQLException("A connection once released, can't be reestablished.");
        if (connection != null) {
            try {
                this.disconnect(false);
            } catch (Exception x) {
                log.debug("Unable to disconnect previous connection.", x);
            } //catch
        } //end if
        if (poolProperties.getDataSource()==null && poolProperties.getDataSourceJNDI()!=null) {
            //TODO lookup JNDI name
        }

        if (poolProperties.getDataSource()!=null) {
            connectUsingDataSource();
        } else {
            connectUsingDriver();
        }

        //set up the default state, unless we expect the interceptor to do it
        if (poolProperties.getJdbcInterceptors()==null || poolProperties.getJdbcInterceptors().indexOf(ConnectionState.class.getName())<0 ||
                poolProperties.getJdbcInterceptors().indexOf(ConnectionState.class.getSimpleName())<0) {
            if (poolProperties.getDefaultTransactionIsolation()!=DataSourceFactory.UNKNOWN_TRANSACTIONISOLATION) connection.setTransactionIsolation(poolProperties.getDefaultTransactionIsolation());
            if (poolProperties.getDefaultReadOnly()!=null) connection.setReadOnly(poolProperties.getDefaultReadOnly().booleanValue());
            if (poolProperties.getDefaultAutoCommit()!=null) connection.setAutoCommit(poolProperties.getDefaultAutoCommit().booleanValue());
            if (poolProperties.getDefaultCatalog()!=null) connection.setCatalog(poolProperties.getDefaultCatalog());
        }
        this.discarded = false;
        this.lastConnected = System.currentTimeMillis();
    }

    protected void connectUsingDataSource() throws SQLException {
        String usr = null;
        String pwd = null;
        if (getAttributes().containsKey(PROP_USER)) {
            usr = (String) getAttributes().get(PROP_USER);
        } else {
            usr = poolProperties.getUsername();
            getAttributes().put(PROP_USER, usr);
        }
        if (getAttributes().containsKey(PROP_PASSWORD)) {
            pwd = (String) getAttributes().get(PROP_PASSWORD);
        } else {
            pwd = poolProperties.getPassword();
            getAttributes().put(PROP_PASSWORD, pwd);
        }
        if (poolProperties.getDataSource() instanceof javax.sql.XADataSource) {
            javax.sql.XADataSource xds = (javax.sql.XADataSource)poolProperties.getDataSource();
            if (usr!=null && pwd!=null) {
                xaConnection = xds.getXAConnection(usr, pwd);
                connection = xaConnection.getConnection();
            } else {
                xaConnection = xds.getXAConnection();
                connection = xaConnection.getConnection();
            }
        } else if (poolProperties.getDataSource() instanceof javax.sql.DataSource){
            javax.sql.DataSource ds = (javax.sql.DataSource)poolProperties.getDataSource();
            if (usr!=null && pwd!=null) {
                connection = ds.getConnection(usr, pwd);
            } else {
                connection = ds.getConnection();
            }
        } else if (poolProperties.getDataSource() instanceof javax.sql.ConnectionPoolDataSource){
            javax.sql.ConnectionPoolDataSource ds = (javax.sql.ConnectionPoolDataSource)poolProperties.getDataSource();
            if (usr!=null && pwd!=null) {
                connection = ds.getPooledConnection(usr, pwd).getConnection();
            } else {
                connection = ds.getPooledConnection().getConnection();
            }
        } else {
            throw new SQLException("DataSource is of unknown class:"+(poolProperties.getDataSource()!=null?poolProperties.getDataSource().getClass():"null"));
        }
    }
    protected void connectUsingDriver() throws SQLException {

        try {
            if (driver==null) {
                if (log.isDebugEnabled()) {
                    log.debug("Instantiating driver using class: "+poolProperties.getDriverClassName()+" [url="+poolProperties.getUrl()+"]");
                }
                driver = (java.sql.Driver) Class.forName(poolProperties.getDriverClassName(),
                                                         true, PooledConnection.class.getClassLoader()
                                                         ).newInstance();
            }
        } catch (java.lang.Exception cn) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to instantiate JDBC driver.", cn);
            }
            SQLException ex = new SQLException(cn.getMessage());
            ex.initCause(cn);
            throw ex;
        }
        String driverURL = poolProperties.getUrl();
        String usr = null;
        String pwd = null;
        if (getAttributes().containsKey(PROP_USER)) {
            usr = (String) getAttributes().get(PROP_USER);
        } else {
            usr = poolProperties.getUsername();
            getAttributes().put(PROP_USER, usr);
        }
        if (getAttributes().containsKey(PROP_PASSWORD)) {
            pwd = (String) getAttributes().get(PROP_PASSWORD);
        } else {
            pwd = poolProperties.getPassword();
            getAttributes().put(PROP_PASSWORD, pwd);
        }
        Properties properties = PoolUtilities.clone(poolProperties.getDbProperties());
        if (usr != null) properties.setProperty(PROP_USER, usr);
        if (pwd != null) properties.setProperty(PROP_PASSWORD, pwd);

        try {
            connection = driver.connect(driverURL, properties);
        } catch (Exception x) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to connect to database.", x);
            }
            if (parent.jmxPool!=null) {
                parent.jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.NOTIFY_CONNECT,
                        ConnectionPool.getStackTrace(x));
            }
            if (x instanceof SQLException) {
                throw (SQLException)x;
            } else {
                SQLException ex = new SQLException(x.getMessage());
                ex.initCause(x);
                throw ex;
            }
        }
        if (connection==null) {
            throw new SQLException("Driver:"+driver+" returned null for URL:"+driverURL);
        }
    }

    /**
     *
     * @return true if connect() was called successfully and disconnect has not yet been called
     */
    public boolean isInitialized() {
        return connection!=null;
    }

    /**
     * Issues a call to {@link #disconnect(boolean)} with the argument false followed by a call to
     * {@link #connect()}
     * @throws SQLException if the call to {@link #connect()} fails.
     */
    public void reconnect() throws SQLException {
        this.disconnect(false);
        this.connect();
    } //reconnect

    /**
     * Disconnects the connection. All exceptions are logged using debug level.
     * @param finalize if set to true, a call to {@link ConnectionPool#finalize(PooledConnection)} is called.
     */
    private void disconnect(boolean finalize) {
        if (isDiscarded() && connection == null) {
            return;
        }
        setDiscarded(true);
        if (connection != null) {
            try {
                parent.disconnectEvent(this, finalize);
                if (xaConnection == null) {
                    connection.close();
                } else {
                    xaConnection.close();
                }
            }catch (Exception ignore) {
                if (log.isDebugEnabled()) {
                    log.debug("Unable to close underlying SQL connection",ignore);
                }
            }
        }
        connection = null;
        xaConnection = null;
        lastConnected = -1;
        if (finalize) parent.finalize(this);
    }


//============================================================================
//
//============================================================================

    /**
     * Returns abandon timeout in milliseconds
     * @return abandon timeout in milliseconds
     */
    public long getAbandonTimeout() {
        if (poolProperties.getRemoveAbandonedTimeout() <= 0) {
            return Long.MAX_VALUE;
        } else {
            return poolProperties.getRemoveAbandonedTimeout() * 1000L;
        } //end if
    }

    /**
     * Returns true if the connection pool is configured
     * to do validation for a certain action.
     * @param action
     */
    private boolean doValidate(int action) {
        if (action == PooledConnection.VALIDATE_BORROW &&
            poolProperties.isTestOnBorrow())
            return true;
        else if (action == PooledConnection.VALIDATE_RETURN &&
                 poolProperties.isTestOnReturn())
            return true;
        else if (action == PooledConnection.VALIDATE_IDLE &&
                 poolProperties.isTestWhileIdle())
            return true;
        else if (action == PooledConnection.VALIDATE_INIT &&
                 poolProperties.isTestOnConnect())
            return true;
        else if (action == PooledConnection.VALIDATE_INIT &&
                 poolProperties.getInitSQL()!=null)
           return true;
        else
            return false;
    }

    /**Returns true if the object is still valid. if not
     * the pool will call the getExpiredAction() and follow up with one
     * of the four expired methods
     */
    public boolean validate(int validateAction) {
        return validate(validateAction,null);
    }

    /**
     * Validates a connection.
     * @param validateAction the action used. One of {@link #VALIDATE_BORROW}, {@link #VALIDATE_IDLE},
     * {@link #VALIDATE_INIT} or {@link #VALIDATE_RETURN}
     * @param sql the SQL to be used during validation. If the {@link PoolConfiguration#setInitSQL(String)} has been called with a non null
     * value and the action is {@link #VALIDATE_INIT} the init SQL will be used for validation.
     *
     * @return true if the connection was validated successfully. It returns true even if validation was not performed, such as when
     * {@link PoolConfiguration#setValidationInterval(long)} has been called with a positive value.
     * <p>
     * false if the validation failed. The caller should close the connection if false is returned since a session could have been left in
     * an unknown state during initialization.
     */
    public boolean validate(int validateAction,String sql) {
        if (this.isDiscarded()) {
            return false;
        }

        if (!doValidate(validateAction)) {
            //no validation required, no init sql and props not set
            return true;
        }

        //Don't bother validating if already have recently enough
        long now = System.currentTimeMillis();
        if (validateAction!=VALIDATE_INIT &&
            poolProperties.getValidationInterval() > 0 &&
            (now - this.lastValidated) <
            poolProperties.getValidationInterval()) {
            return true;
        }

        if (poolProperties.getValidator() != null) {
            if (poolProperties.getValidator().validate(connection, validateAction)) {
                this.lastValidated = now;
                return true;
            } else {
                if (getPoolProperties().getLogValidationErrors()) {
                    log.error("Custom validation through "+poolProperties.getValidator()+" failed.");
                }
                return false;
            }
        }

        String query = sql;

        if (validateAction == VALIDATE_INIT && poolProperties.getInitSQL() != null) {
            query = poolProperties.getInitSQL();
        }

        if (query == null) {
            query = poolProperties.getValidationQuery();
        }

        if (query == null) {
            int validationQueryTimeout = poolProperties.getValidationQueryTimeout();
            if (validationQueryTimeout < 0) validationQueryTimeout = 0;
            try {
                if (connection.isValid(validationQueryTimeout)) {
                    this.lastValidated = now;
                    return true;
                } else {
                    if (getPoolProperties().getLogValidationErrors()) {
                        log.error("isValid() returned false.");
                    }
                    return false;
                }
            } catch (SQLException e) {
                if (getPoolProperties().getLogValidationErrors()) {
                    log.error("isValid() failed.", e);
                } else if (log.isDebugEnabled()) {
                    log.debug("isValid() failed.", e);
                }
                return false;
            }
        }

        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            int validationQueryTimeout = poolProperties.getValidationQueryTimeout();
            if (validationQueryTimeout > 0) {
                stmt.setQueryTimeout(validationQueryTimeout);
            }

            stmt.execute(query);
            stmt.close();
            this.lastValidated = now;
            return true;
        } catch (Exception ex) {
            if (getPoolProperties().getLogValidationErrors()) {
                log.warn("SQL Validation error", ex);
            } else if (log.isDebugEnabled()) {
                log.debug("Unable to validate object:",ex);
            }
            if (stmt!=null)
                try { stmt.close();} catch (Exception ignore2){/*NOOP*/}

            try {
                if(!connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                // do nothing
            }
        } finally {
            try {
                if(!connection.getAutoCommit()) {
                    connection.commit();
                }
            } catch (SQLException e) {
                // do nothing
            }
        }
        return false;
    } //validate

    /**
     * The time limit for how long the object
     * can remain unused before it is released
     * @return {@link PoolConfiguration#getMinEvictableIdleTimeMillis()}
     */
    public long getReleaseTime() {
        return this.poolProperties.getMinEvictableIdleTimeMillis();
    }

    /**
     * This method is called if (Now - timeCheckedIn > getReleaseTime())
     * This method disconnects the connection, logs an error in debug mode if it happens
     * then sets the {@link #released} flag to false. Any attempts to connect this cached object again
     * will fail per {@link #connect()}
     * The connection pool uses the atomic return value to decrement the pool size counter.
     * @return true if this is the first time this method has been called. false if this method has been called before.
     */
    public boolean release() {
        try {
            disconnect(true);
        } catch (Exception x) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to close SQL connection",x);
            }
        }
        return released.compareAndSet(false, true);

    }

    /**
     * The pool will set the stack trace when it is check out and
     * checked in
     * @param trace the stack trace for this connection
     */

    public void setStackTrace(String trace) {
        abandonTrace = trace;
    }

    /**
     * Returns the stack trace from when this connection was borrowed. Can return null if no stack trace was set.
     * @return the stack trace or null of no trace was set
     */
    public String getStackTrace() {
        return abandonTrace;
    }

    /**
     * Sets a timestamp on this connection. A timestamp usually means that some operation
     * performed successfully.
     * @param timestamp the timestamp as defined by {@link System#currentTimeMillis()}
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        setSuspect(false);
    }


    public boolean isSuspect() {
        return suspect;
    }

    public void setSuspect(boolean suspect) {
        this.suspect = suspect;
    }

    /**
     * An interceptor can call this method with the value true, and the connection will be closed when it is returned to the pool.
     * @param discarded - only valid value is true
     * @throws IllegalStateException if this method is called with the value false and the value true has already been set.
     */
    public void setDiscarded(boolean discarded) {
        if (this.discarded && !discarded) throw new IllegalStateException("Unable to change the state once the connection has been discarded");
        this.discarded = discarded;
    }

    /**
     * Set the timestamp the connection was last validated.
     * This flag is used to keep track when we are using a {@link PoolConfiguration#setValidationInterval(long) validation-interval}.
     * @param lastValidated a timestamp as defined by {@link System#currentTimeMillis()}
     */
    public void setLastValidated(long lastValidated) {
        this.lastValidated = lastValidated;
    }

    /**
     * Sets the pool configuration for this connection and connection pool.
     * Object is shared with the {@link ConnectionPool}
     * @param poolProperties
     */
    public void setPoolProperties(PoolConfiguration poolProperties) {
        this.poolProperties = poolProperties;
    }

    /**
     * Return the timestamps of last pool action. Timestamps are typically set when connections
     * are borrowed from the pool. It is used to keep track of {@link PoolConfiguration#setRemoveAbandonedTimeout(int) abandon-timeouts}.
     * This timestamp can also be reset by the {@link org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer#invoke(Object, java.lang.reflect.Method, Object[])}
     * @return the timestamp of the last pool action as defined by {@link System#currentTimeMillis()}
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the discarded flag.
     * @return the discarded flag. If the value is true,
     * either {@link #disconnect(boolean)} has been called or it will be called when the connection is returned to the pool.
     */
    public boolean isDiscarded() {
        return discarded;
    }

    /**
     * Returns the timestamp of the last successful validation query execution.
     * @return the timestamp of the last successful validation query execution as defined by {@link System#currentTimeMillis()}
     */
    public long getLastValidated() {
        return lastValidated;
    }

    /**
     * Returns the configuration for this connection and pool
     * @return the configuration for this connection and pool
     */
    public PoolConfiguration getPoolProperties() {
        return poolProperties;
    }

    /**
     * Locks the connection only if either {@link PoolConfiguration#isPoolSweeperEnabled()} or
     * {@link PoolConfiguration#getUseLock()} return true. The per connection lock ensures thread safety is
     * multiple threads are performing operations on the connection.
     * Otherwise this is a noop for performance
     */
    public void lock() {
        if (poolProperties.getUseLock() || this.poolProperties.isPoolSweeperEnabled()) {
            //optimized, only use a lock when there is concurrency
            lock.writeLock().lock();
        }
    }

    /**
     * Unlocks the connection only if the sweeper is enabled
     * Otherwise this is a noop for performance
     */
    public void unlock() {
        if (poolProperties.getUseLock() || this.poolProperties.isPoolSweeperEnabled()) {
          //optimized, only use a lock when there is concurrency
            lock.writeLock().unlock();
        }
    }

    /**
     * Returns the underlying connection
     * @return the underlying JDBC connection as it was returned from the JDBC driver
     * @see javax.sql.PooledConnection#getConnection()
     */
    public java.sql.Connection getConnection() {
        return this.connection;
    }

    /**
     * Returns the underlying XA connection
     * @return the underlying XA connection as it was returned from the Datasource
     */
    public javax.sql.XAConnection getXAConnection() {
        return this.xaConnection;
    }


    /**
     * Returns the timestamp of when the connection was last connected to the database.
     * ie, a successful call to {@link java.sql.Driver#connect(String, java.util.Properties)}.
     * @return the timestamp when this connection was created as defined by {@link System#currentTimeMillis()}
     */
    public long getLastConnected() {
        return lastConnected;
    }

    /**
     * Returns the first handler in the interceptor chain
     * @return the first interceptor for this connection
     */
    public JdbcInterceptor getHandler() {
        return handler;
    }

    public void setHandler(JdbcInterceptor handler) {
        if (this.handler!=null && this.handler!=handler) {
            JdbcInterceptor interceptor = this.handler;
            while (interceptor!=null) {
                interceptor.reset(null, null);
                interceptor = interceptor.getNext();
            }//while
        }//end if
        this.handler = handler;
    }

    @Override
    public String toString() {
        return "PooledConnection["+(connection!=null?connection.toString():"null")+"]";
    }

    /**
     * Returns true if this connection has been released and wont be reused.
     * @return true if the method {@link #release()} has been called
     */
    public boolean isReleased() {
        return released.get();
    }

    public HashMap<Object,Object> getAttributes() {
        return attributes;
    }

}
