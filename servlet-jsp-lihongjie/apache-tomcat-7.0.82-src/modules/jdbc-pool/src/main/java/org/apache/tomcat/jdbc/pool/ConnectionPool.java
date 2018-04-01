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

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Implementation of simple connection pool.
 * The ConnectionPool uses a {@link PoolProperties} object for storing all the meta information about the connection pool.
 * As the underlying implementation, the connection pool uses {@link java.util.concurrent.BlockingQueue} to store active and idle connections.
 * A custom implementation of a fair {@link FairBlockingQueue} blocking queue is provided with the connection pool itself.
 * @author Filip Hanik
 * @version 1.0
 */

public class ConnectionPool {

    /**
     * Default domain for objects registering with an mbean server
     */
    public static final String POOL_JMX_DOMAIN = "tomcat.jdbc";
    /**
     * Prefix type for JMX registration
     */
    public static final String POOL_JMX_TYPE_PREFIX = POOL_JMX_DOMAIN+":type=";

    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(ConnectionPool.class);

    //===============================================================================
    //         INSTANCE/QUICK ACCESS VARIABLE
    //===============================================================================
    /**
     * Carries the size of the pool, instead of relying on a queue implementation
     * that usually iterates over to get an exact count
     */
    private AtomicInteger size = new AtomicInteger(0);

    /**
     * All the information about the connection pool
     * These are the properties the pool got instantiated with
     */
    private PoolConfiguration poolProperties;

    /**
     * Contains all the connections that are in use
     * TODO - this shouldn't be a blocking queue, simply a list to hold our objects
     */
    private BlockingQueue<PooledConnection> busy;

    /**
     * Contains all the idle connections
     */
    private BlockingQueue<PooledConnection> idle;

    /**
     * The thread that is responsible for checking abandoned and idle threads
     */
    private volatile PoolCleaner poolCleaner;

    /**
     * Pool closed flag
     */
    private volatile boolean closed = false;

    /**
     * Since newProxyInstance performs the same operation, over and over
     * again, it is much more optimized if we simply store the constructor ourselves.
     */
    private Constructor<?> proxyClassConstructor;

    /**
     * Executor service used to cancel Futures
     */
    private ThreadPoolExecutor cancellator = new ThreadPoolExecutor(0,1,1000,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());

    /**
     * reference to the JMX mbean
     */
    protected org.apache.tomcat.jdbc.pool.jmx.ConnectionPool jmxPool = null;

    /**
     * counter to track how many threads are waiting for a connection
     */
    private AtomicInteger waitcount = new AtomicInteger(0);

    private AtomicLong poolVersion = new AtomicLong(Long.MIN_VALUE);

    /**
     * The counters for statistics of the pool.
     */
    private final AtomicLong borrowedCount = new AtomicLong(0);
    private final AtomicLong returnedCount = new AtomicLong(0);
    private final AtomicLong createdCount = new AtomicLong(0);
    private final AtomicLong releasedCount = new AtomicLong(0);
    private final AtomicLong reconnectedCount = new AtomicLong(0);
    private final AtomicLong removeAbandonedCount = new AtomicLong(0);
    private final AtomicLong releasedIdleCount = new AtomicLong(0);

    //===============================================================================
    //         PUBLIC METHODS
    //===============================================================================

    /**
     * Instantiate a connection pool. This will create connections if initialSize is larger than 0.
     * The {@link PoolProperties} should not be reused for another connection pool.
     * @param prop PoolProperties - all the properties for this connection pool
     * @throws SQLException
     */
    public ConnectionPool(PoolConfiguration prop) throws SQLException {
        //setup quick access variables and pools
        init(prop);
    }


    /**
     * Retrieves a Connection future. If a connection is not available, one can block using future.get()
     * until a connection has become available.
     * If a connection is not retrieved, the Future must be cancelled in order for the connection to be returned
     * to the pool.
     * @return a Future containing a reference to the connection or the future connection
     * @throws SQLException
     */
    public Future<Connection> getConnectionAsync() throws SQLException {
        try {
            PooledConnection pc = borrowConnection(0, null, null);
            if (pc!=null) {
                return new ConnectionFuture(pc);
            }
        }catch (SQLException x) {
            if (x.getMessage().indexOf("NoWait")<0) {
                throw x;
            }
        }
        //we can only retrieve a future if the underlying queue supports it.
        if (idle instanceof FairBlockingQueue<?>) {
            Future<PooledConnection> pcf = ((FairBlockingQueue<PooledConnection>)idle).pollAsync();
            return new ConnectionFuture(pcf);
        } else if (idle instanceof MultiLockFairBlockingQueue<?>) {
                Future<PooledConnection> pcf = ((MultiLockFairBlockingQueue<PooledConnection>)idle).pollAsync();
                return new ConnectionFuture(pcf);
        } else {
            throw new SQLException("Connection pool is misconfigured, doesn't support async retrieval. Set the 'fair' property to 'true'");
        }
    }

    /**
     * Borrows a connection from the pool. If a connection is available (in the idle queue) or the pool has not reached
     * {@link PoolProperties#maxActive maxActive} connections a connection is returned immediately.
     * If no connection is available, the pool will attempt to fetch a connection for {@link PoolProperties#maxWait maxWait} milliseconds.
     * @return Connection - a java.sql.Connection/javax.sql.PooledConnection reflection proxy, wrapping the underlying object.
     * @throws SQLException - if the wait times out or a failure occurs creating a connection
     */
    public Connection getConnection() throws SQLException {
        //check out a connection
        PooledConnection con = borrowConnection(-1,null,null);
        return setupConnection(con);
    }


    /**
     * Borrows a connection from the pool. If a connection is available (in the
     * idle queue) or the pool has not reached {@link PoolProperties#maxActive
     * maxActive} connections a connection is returned immediately. If no
     * connection is available, the pool will attempt to fetch a connection for
     * {@link PoolProperties#maxWait maxWait} milliseconds.
     *
     * @return Connection - a java.sql.Connection/javax.sql.PooledConnection
     *         reflection proxy, wrapping the underlying object.
     * @throws SQLException
     *             - if the wait times out or a failure occurs creating a
     *             connection
     */
    public Connection getConnection(String username, String password) throws SQLException {
        // check out a connection
        PooledConnection con = borrowConnection(-1, username, password);
        return setupConnection(con);
    }

    /**
     * Returns the name of this pool
     * @return String - the name of the pool
     */
    public String getName() {
        return getPoolProperties().getPoolName();
    }

    /**
     * Return the number of threads waiting for a connection
     * @return number of threads waiting for a connection
     */
    public int getWaitCount() {
        return waitcount.get();
    }

    /**
     * Returns the pool properties associated with this connection pool
     * @return PoolProperties
     *
     */
    public PoolConfiguration getPoolProperties() {
        return this.poolProperties;
    }

    /**
     * Returns the total size of this pool, this includes both busy and idle connections
     * @return int - number of established connections to the database
     */
    public int getSize() {
        return size.get();
    }

    /**
     * Returns the number of connections that are in use
     * @return int - number of established connections that are being used by the application
     */
    public int getActive() {
        return busy.size();
    }

    /**
     * Returns the number of idle connections
     * @return int - number of established connections not being used
     */
    public int getIdle() {
        return idle.size();
    }

    /**
     * Returns true if {@link #close close} has been called, and the connection pool is unusable
     * @return boolean
     */
    public  boolean isClosed() {
        return this.closed;
    }

    //===============================================================================
    //         PROTECTED METHODS
    //===============================================================================


    /**
     * configures a pooled connection as a proxy.
     * This Proxy implements {@link java.sql.Connection} and {@link javax.sql.PooledConnection} interfaces.
     * All calls on {@link java.sql.Connection} methods will be propagated down to the actual JDBC connection except for the
     * {@link java.sql.Connection#close()} method.
     * @param con a {@link PooledConnection} to wrap in a Proxy
     * @return a {@link java.sql.Connection} object wrapping a pooled connection.
     * @throws SQLException if an interceptor can't be configured, if the proxy can't be instantiated
     */
    protected Connection setupConnection(PooledConnection con) throws SQLException {
        //fetch previously cached interceptor proxy - one per connection
        JdbcInterceptor handler = con.getHandler();
        if (handler==null) {
            //build the proxy handler
            handler = new ProxyConnection(this,con,getPoolProperties().isUseEquals());
            //set up the interceptor chain
            PoolProperties.InterceptorDefinition[] proxies = getPoolProperties().getJdbcInterceptorsAsArray();
            for (int i=proxies.length-1; i>=0; i--) {
                try {
                    //create a new instance
                    JdbcInterceptor interceptor = proxies[i].getInterceptorClass().newInstance();
                    //configure properties
                    interceptor.setProperties(proxies[i].getProperties());
                    //setup the chain
                    interceptor.setNext(handler);
                    //call reset
                    interceptor.reset(this, con);
                    //configure the last one to be held by the connection
                    handler = interceptor;
                }catch(Exception x) {
                    SQLException sx = new SQLException("Unable to instantiate interceptor chain.");
                    sx.initCause(x);
                    throw sx;
                }
            }
            //cache handler for the next iteration
            con.setHandler(handler);
        } else {
            JdbcInterceptor next = handler;
            //we have a cached handler, reset it
            while (next!=null) {
                next.reset(this, con);
                next = next.getNext();
            }
        }
        // setup statement proxy
        if (getPoolProperties().getUseStatementFacade()) {
            handler = new StatementFacade(handler);
        }
        try {
            getProxyConstructor(con.getXAConnection() != null);
            //create the proxy
            //TODO possible optimization, keep track if this connection was returned properly, and don't generate a new facade
            Connection connection = null;
            if (getPoolProperties().getUseDisposableConnectionFacade() ) {
                connection = (Connection)proxyClassConstructor.newInstance(new Object[] { new DisposableConnectionFacade(handler) });
            } else {
                connection = (Connection)proxyClassConstructor.newInstance(new Object[] {handler});
            }
            //return the connection
            return connection;
        }catch (Exception x) {
            SQLException s = new SQLException();
            s.initCause(x);
            throw s;
        }

    }

    /**
     * Creates and caches a {@link java.lang.reflect.Constructor} used to instantiate the proxy object.
     * We cache this, since the creation of a constructor is fairly slow.
     * @return constructor used to instantiate the wrapper object
     * @throws NoSuchMethodException
     */
    public Constructor<?> getProxyConstructor(boolean xa) throws NoSuchMethodException {
        //cache the constructor
        if (proxyClassConstructor == null ) {
            Class<?> proxyClass = xa ?
                Proxy.getProxyClass(ConnectionPool.class.getClassLoader(), new Class[] {java.sql.Connection.class,javax.sql.PooledConnection.class, javax.sql.XAConnection.class}) :
                Proxy.getProxyClass(ConnectionPool.class.getClassLoader(), new Class[] {java.sql.Connection.class,javax.sql.PooledConnection.class});
            proxyClassConstructor = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
        }
        return proxyClassConstructor;
    }

    /**
     * Closes the pool and all disconnects all idle connections
     * Active connections will be closed upon the {@link java.sql.Connection#close close} method is called
     * on the underlying connection instead of being returned to the pool
     * @param force - true to even close the active connections
     */
    protected void close(boolean force) {
        //are we already closed
        if (this.closed) return;
        //prevent other threads from entering
        this.closed = true;
        //stop background thread
        if (poolCleaner!=null) {
            poolCleaner.stopRunning();
        }

        /* release all idle connections */
        BlockingQueue<PooledConnection> pool = (idle.size()>0)?idle:(force?busy:idle);
        while (pool.size()>0) {
            try {
                //retrieve the next connection
                PooledConnection con = pool.poll(1000, TimeUnit.MILLISECONDS);
                //close it and retrieve the next one, if one is available
                while (con != null) {
                    //close the connection
                    if (pool==idle)
                        release(con);
                    else
                        abandon(con);
                    if (pool.size()>0) {
                        con = pool.poll(1000, TimeUnit.MILLISECONDS);
                    } else {
                        break;
                    }
                } //while
            } catch (InterruptedException ex) {
                if (getPoolProperties().getPropagateInterruptState()) {
                    Thread.currentThread().interrupt();
                }
            }
            if (pool.size()==0 && force && pool!=busy) pool = busy;
        }
        if (this.getPoolProperties().isJmxEnabled()) this.jmxPool = null;
        PoolProperties.InterceptorDefinition[] proxies = getPoolProperties().getJdbcInterceptorsAsArray();
        for (int i=0; i<proxies.length; i++) {
            try {
                JdbcInterceptor interceptor = proxies[i].getInterceptorClass().newInstance();
                interceptor.setProperties(proxies[i].getProperties());
                interceptor.poolClosed(this);
            }catch (Exception x) {
                log.debug("Unable to inform interceptor of pool closure.",x);
            }
        }
    } //closePool


    /**
     * Initialize the connection pool - called from the constructor
     * @param properties PoolProperties - properties used to initialize the pool with
     * @throws SQLException if initialization fails
     */
    protected void init(PoolConfiguration properties) throws SQLException {
        poolProperties = properties;

        //make sure the pool is properly configured
        checkPoolConfiguration(properties);

        //make space for 10 extra in case we flow over a bit
        busy = new LinkedBlockingQueue<PooledConnection>();
        //busy = new FairBlockingQueue<PooledConnection>();
        //make space for 10 extra in case we flow over a bit
        if (properties.isFairQueue()) {
            idle = new FairBlockingQueue<PooledConnection>();
            //idle = new MultiLockFairBlockingQueue<PooledConnection>();
            //idle = new LinkedTransferQueue<PooledConnection>();
            //idle = new ArrayBlockingQueue<PooledConnection>(properties.getMaxActive(),false);
        } else {
            idle = new LinkedBlockingQueue<PooledConnection>();
        }

        initializePoolCleaner(properties);

        //create JMX MBean
        if (this.getPoolProperties().isJmxEnabled()) createMBean();

        //Parse and create an initial set of interceptors. Letting them know the pool has started.
        //These interceptors will not get any connection.
        PoolProperties.InterceptorDefinition[] proxies = getPoolProperties().getJdbcInterceptorsAsArray();
        for (int i=0; i<proxies.length; i++) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Creating interceptor instance of class:"+proxies[i].getInterceptorClass());
                }
                JdbcInterceptor interceptor = proxies[i].getInterceptorClass().newInstance();
                interceptor.setProperties(proxies[i].getProperties());
                interceptor.poolStarted(this);
            }catch (Exception x) {
                log.error("Unable to inform interceptor of pool start.",x);
                if (jmxPool!=null) jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.NOTIFY_INIT, getStackTrace(x));
                close(true);
                SQLException ex = new SQLException();
                ex.initCause(x);
                throw ex;
            }
        }

        //initialize the pool with its initial set of members
        PooledConnection[] initialPool = new PooledConnection[poolProperties.getInitialSize()];
        try {
            for (int i = 0; i < initialPool.length; i++) {
                initialPool[i] = this.borrowConnection(0, null, null); //don't wait, should be no contention
            } //for

        } catch (SQLException x) {
            log.error("Unable to create initial connections of pool.", x);
            if (!poolProperties.isIgnoreExceptionOnPreLoad()) {
                if (jmxPool!=null) jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.NOTIFY_INIT, getStackTrace(x));
                close(true);
                throw x;
            }
        } finally {
            //return the members as idle to the pool
            for (int i = 0; i < initialPool.length; i++) {
                if (initialPool[i] != null) {
                    try {this.returnConnection(initialPool[i]);}catch(Exception x){/*NOOP*/}
                } //end if
            } //for
        } //catch

        closed = false;
    }

    public void checkPoolConfiguration(PoolConfiguration properties) {
        //make sure the pool is properly configured
        if (properties.getMaxActive()<1) {
            log.warn("maxActive is smaller than 1, setting maxActive to: "+PoolProperties.DEFAULT_MAX_ACTIVE);
            properties.setMaxActive(PoolProperties.DEFAULT_MAX_ACTIVE);
        }
        if (properties.getMaxActive()<properties.getInitialSize()) {
            log.warn("initialSize is larger than maxActive, setting initialSize to: "+properties.getMaxActive());
            properties.setInitialSize(properties.getMaxActive());
        }
        if (properties.getMinIdle()>properties.getMaxActive()) {
            log.warn("minIdle is larger than maxActive, setting minIdle to: "+properties.getMaxActive());
            properties.setMinIdle(properties.getMaxActive());
        }
        if (properties.getMaxIdle()>properties.getMaxActive()) {
            log.warn("maxIdle is larger than maxActive, setting maxIdle to: "+properties.getMaxActive());
            properties.setMaxIdle(properties.getMaxActive());
        }
        if (properties.getMaxIdle()<properties.getMinIdle()) {
            log.warn("maxIdle is smaller than minIdle, setting maxIdle to: "+properties.getMinIdle());
            properties.setMaxIdle(properties.getMinIdle());
        }
    }

    public void initializePoolCleaner(PoolConfiguration properties) {
        //if the evictor thread is supposed to run, start it now
        if (properties.isPoolSweeperEnabled()) {
            poolCleaner = new PoolCleaner(this, properties.getTimeBetweenEvictionRunsMillis());
            poolCleaner.start();
        } //end if
    }

    public void terminatePoolCleaner() {
        if (poolCleaner!= null) {
            poolCleaner.stopRunning();
            poolCleaner = null;
        }
    }

//===============================================================================
//         CONNECTION POOLING IMPL LOGIC
//===============================================================================

    /**
     * thread safe way to abandon a connection
     * signals a connection to be abandoned.
     * this will disconnect the connection, and log the stack trace if logAbandoned=true
     * @param con PooledConnection
     */
    protected void abandon(PooledConnection con) {
        if (con == null)
            return;
        try {
            con.lock();
            String trace = con.getStackTrace();
            if (getPoolProperties().isLogAbandoned()) {
                log.warn("Connection has been abandoned " + con + ":" + trace);
            }
            if (jmxPool!=null) {
                jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.NOTIFY_ABANDON, trace);
            }
            //release the connection
            removeAbandonedCount.incrementAndGet();
            release(con);
        } finally {
            con.unlock();
        }
    }

    /**
     * Thread safe way to suspect a connection. Similar to
     * {@link #abandon(PooledConnection)}, but instead of actually abandoning
     * the connection, this will log a warning and set the suspect flag on the
     * {@link PooledConnection} if logAbandoned=true
     *
     * @param con PooledConnection
     */
    protected void suspect(PooledConnection con) {
        if (con == null)
            return;
        if (con.isSuspect())
            return;
        try {
            con.lock();
            String trace = con.getStackTrace();
            if (getPoolProperties().isLogAbandoned()) {
                log.warn("Connection has been marked suspect, possibly abandoned " + con + "["+(System.currentTimeMillis()-con.getTimestamp())+" ms.]:" + trace);
            }
            if (jmxPool!=null) {
                jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.SUSPECT_ABANDONED_NOTIFICATION, trace);
            }
            con.setSuspect(true);
        } finally {
            con.unlock();
        }
    }

    /**
     * thread safe way to release a connection
     * @param con PooledConnection
     */
    protected void release(PooledConnection con) {
        if (con == null)
            return;
        try {
            con.lock();
            if (con.release()) {
                //counter only decremented once
                size.addAndGet(-1);
                con.setHandler(null);
            }
            releasedCount.incrementAndGet();
        } finally {
            con.unlock();
        }
        // we've asynchronously reduced the number of connections
        // we could have threads stuck in idle.poll(timeout) that will never be
        // notified
        if (waitcount.get() > 0) {
            idle.offer(create(true));
        }
    }

    /**
     * Thread safe way to retrieve a connection from the pool
     * @param wait - time to wait, overrides the maxWait from the properties,
     * set to -1 if you wish to use maxWait, 0 if you wish no wait time.
     * @return PooledConnection
     * @throws SQLException
     */
    private PooledConnection borrowConnection(int wait, String username, String password) throws SQLException {

        if (isClosed()) {
            throw new SQLException("Connection pool closed.");
        } //end if

        //get the current time stamp
        long now = System.currentTimeMillis();
        //see if there is one available immediately
        PooledConnection con = idle.poll();

        while (true) {
            if (con!=null) {
                //configure the connection and return it
                PooledConnection result = borrowConnection(now, con, username, password);
                borrowedCount.incrementAndGet();
                if (result!=null) return result;
            }

            //if we get here, see if we need to create one
            //this is not 100% accurate since it doesn't use a shared
            //atomic variable - a connection can become idle while we are creating
            //a new connection
            if (size.get() < getPoolProperties().getMaxActive()) {
                //atomic duplicate check
                if (size.addAndGet(1) > getPoolProperties().getMaxActive()) {
                    //if we got here, two threads passed through the first if
                    size.decrementAndGet();
                } else {
                    //create a connection, we're below the limit
                    return createConnection(now, con, username, password);
                }
            } //end if

            //calculate wait time for this iteration
            long maxWait = wait;
            //if the passed in wait time is -1, means we should use the pool property value
            if (wait==-1) {
                maxWait = (getPoolProperties().getMaxWait()<=0)?Long.MAX_VALUE:getPoolProperties().getMaxWait();
            }

            long timetowait = Math.max(0, maxWait - (System.currentTimeMillis() - now));
            waitcount.incrementAndGet();
            try {
                //retrieve an existing connection
                con = idle.poll(timetowait, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ex) {
                if (getPoolProperties().getPropagateInterruptState()) {
                    Thread.currentThread().interrupt();
                }
                SQLException sx = new SQLException("Pool wait interrupted.");
                sx.initCause(ex);
                throw sx;
            } finally {
                waitcount.decrementAndGet();
            }
            if (maxWait==0 && con == null) { //no wait, return one if we have one
                if (jmxPool!=null) {
                    jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.POOL_EMPTY, "Pool empty - no wait.");
                }
                throw new PoolExhaustedException("[" + Thread.currentThread().getName()+"] " +
                        "NoWait: Pool empty. Unable to fetch a connection, none available["+busy.size()+" in use].");
            }
            //we didn't get a connection, lets see if we timed out
            if (con == null) {
                if ((System.currentTimeMillis() - now) >= maxWait) {
                    if (jmxPool!=null) {
                        jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.POOL_EMPTY, "Pool empty - timeout.");
                    }
                    throw new PoolExhaustedException("[" + Thread.currentThread().getName()+"] " +
                        "Timeout: Pool empty. Unable to fetch a connection in " + (maxWait / 1000) +
                        " seconds, none available[size:"+size.get() +"; busy:"+busy.size()+"; idle:"+idle.size()+"; lastwait:"+timetowait+"].");
                } else {
                    //no timeout, lets try again
                    continue;
                }
            }
        } //while
    }

    /**
     * Creates a JDBC connection and tries to connect to the database.
     * @param now timestamp of when this was called
     * @param notUsed Argument not used
     * @return a PooledConnection that has been connected
     * @throws SQLException
     */
    protected PooledConnection createConnection(long now, PooledConnection notUsed, String username, String password) throws SQLException {
        //no connections where available we'll create one
        PooledConnection con = create(false);
        if (username!=null) con.getAttributes().put(PooledConnection.PROP_USER, username);
        if (password!=null) con.getAttributes().put(PooledConnection.PROP_PASSWORD, password);
        boolean error = false;
        try {
            //connect and validate the connection
            con.lock();
            con.connect();
            if (con.validate(PooledConnection.VALIDATE_INIT)) {
                //no need to lock a new one, its not contented
                con.setTimestamp(now);
                if (getPoolProperties().isLogAbandoned()) {
                    con.setStackTrace(getThreadDump());
                }
                if (!busy.offer(con)) {
                    log.debug("Connection doesn't fit into busy array, connection will not be traceable.");
                }
                createdCount.incrementAndGet();
                return con;
            } else {
                //validation failed, make sure we disconnect
                //and clean up
                throw new SQLException("Validation Query Failed, enable logValidationErrors for more details.");
            } //end if
        } catch (Exception e) {
            error = true;
            if (log.isDebugEnabled())
                log.debug("Unable to create a new JDBC connection.", e);
            if (e instanceof SQLException) {
                throw (SQLException)e;
            } else {
                SQLException ex = new SQLException(e.getMessage());
                ex.initCause(e);
                throw ex;
            }
        } finally {
            // con can never be null here
            if (error ) {
                release(con);
            }
            con.unlock();
        }//catch
    }

    /**
     * Validates and configures a previously idle connection
     * @param now - timestamp
     * @param con - the connection to validate and configure
     * @return con
     * @throws SQLException if a validation error happens
     */
    protected PooledConnection borrowConnection(long now, PooledConnection con, String username, String password) throws SQLException {
        //we have a connection, lets set it up

        //flag to see if we need to nullify
        boolean setToNull = false;
        try {
            con.lock();
            boolean usercheck = con.checkUser(username, password);

            if (con.isReleased()) {
                return null;
            }

            if (!con.isDiscarded() && !con.isInitialized()) {
                //here it states that the connection not discarded, but the connection is null
                //don't attempt a connect here. It will be done during the reconnect.
                usercheck = false;
            }

            if (usercheck) {
                if ((!con.isDiscarded()) && con.validate(PooledConnection.VALIDATE_BORROW)) {
                    //set the timestamp
                    con.setTimestamp(now);
                    if (getPoolProperties().isLogAbandoned()) {
                        //set the stack trace for this pool
                        con.setStackTrace(getThreadDump());
                    }
                    if (!busy.offer(con)) {
                        log.debug("Connection doesn't fit into busy array, connection will not be traceable.");
                    }
                    return con;
                }
            }
            //if we reached here, that means the connection
            //is either has another principal, is discarded or validation failed.
            //we will make one more attempt
            //in order to guarantee that the thread that just acquired
            //the connection shouldn't have to poll again.
            try {
                con.reconnect();
                reconnectedCount.incrementAndGet();
                int validationMode = getPoolProperties().isTestOnConnect() || getPoolProperties().getInitSQL()!=null ?
                        PooledConnection.VALIDATE_INIT :
                        PooledConnection.VALIDATE_BORROW;
                if (con.validate(validationMode)) {
                    //set the timestamp
                    con.setTimestamp(now);
                    if (getPoolProperties().isLogAbandoned()) {
                        //set the stack trace for this pool
                        con.setStackTrace(getThreadDump());
                    }
                    if (!busy.offer(con)) {
                        log.debug("Connection doesn't fit into busy array, connection will not be traceable.");
                    }
                    return con;
                } else {
                    //validation failed.
                    throw new SQLException("Failed to validate a newly established connection.");
                }
            } catch (Exception x) {
                release(con);
                setToNull = true;
                if (x instanceof SQLException) {
                    throw (SQLException)x;
                } else {
                    SQLException ex  = new SQLException(x.getMessage());
                    ex.initCause(x);
                    throw ex;
                }
            }
        } finally {
            con.unlock();
            if (setToNull) {
                con = null;
            }
        }
    }
    /**
     * Terminate the current transaction for the given connection.
     * @param con
     * @return <code>true</code> if the connection TX termination succeeded
     *         otherwise <code>false</code>
     */
    protected boolean terminateTransaction(PooledConnection con) {
        try {
            if (Boolean.FALSE.equals(con.getPoolProperties().getDefaultAutoCommit())) {
                if (this.getPoolProperties().getRollbackOnReturn()) {
                    boolean autocommit = con.getConnection().getAutoCommit();
                    if (!autocommit) con.getConnection().rollback();
                } else if (this.getPoolProperties().getCommitOnReturn()) {
                    boolean autocommit = con.getConnection().getAutoCommit();
                    if (!autocommit) con.getConnection().commit();
                }
            }
            return true;
        } catch (SQLException x) {
            log.warn("Unable to terminate transaction, connection will be closed.",x);
            return false;
        }

    }

    /**
     * Determines if a connection should be closed upon return to the pool.
     * @param con - the connection
     * @param action - the validation action that should be performed
     * @return true if the connection should be closed
     */
    protected boolean shouldClose(PooledConnection con, int action) {
        if (con.getConnectionVersion() < getPoolVersion()) return true;
        if (con.isDiscarded()) return true;
        if (isClosed()) return true;
        if (!con.validate(action)) return true;
        if (!terminateTransaction(con)) return true;
        if (getPoolProperties().getMaxAge()>0 ) {
            return (System.currentTimeMillis()-con.getLastConnected()) > getPoolProperties().getMaxAge();
        } else {
            return false;
        }
    }

    /**
     * Returns a connection to the pool
     * If the pool is closed, the connection will be released
     * If the connection is not part of the busy queue, it will be released.
     * If {@link PoolProperties#testOnReturn} is set to true it will be validated
     * @param con PooledConnection to be returned to the pool
     */
    protected void returnConnection(PooledConnection con) {
        if (isClosed()) {
            //if the connection pool is closed
            //close the connection instead of returning it
            release(con);
            return;
        } //end if

        if (con != null) {
            try {
                returnedCount.incrementAndGet();
                con.lock();
                if (con.isSuspect()) {
                    if (poolProperties.isLogAbandoned() && log.isInfoEnabled()) {
                        log.info("Connection(" + con + ") that has been marked suspect was returned."
                                + " The processing time is " + (System.currentTimeMillis()-con.getTimestamp()) + " ms.");
                    }
                    if (jmxPool!=null) {
                        jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.SUSPECT_RETURNED_NOTIFICATION,
                                "Connection(" + con + ") that has been marked suspect was returned.");
                    }
                }
                if (busy.remove(con)) {

                    if (!shouldClose(con,PooledConnection.VALIDATE_RETURN)) {
                        con.setStackTrace(null);
                        con.setTimestamp(System.currentTimeMillis());
                        if (((idle.size()>=poolProperties.getMaxIdle()) && !poolProperties.isPoolSweeperEnabled()) || (!idle.offer(con))) {
                            if (log.isDebugEnabled()) {
                                log.debug("Connection ["+con+"] will be closed and not returned to the pool, idle["+idle.size()+"]>=maxIdle["+poolProperties.getMaxIdle()+"] idle.offer failed.");
                            }
                            release(con);
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("Connection ["+con+"] will be closed and not returned to the pool.");
                        }
                        release(con);
                    } //end if
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Connection ["+con+"] will be closed and not returned to the pool, busy.remove failed.");
                    }
                    release(con);
                }
            } finally {
                con.unlock();
            }
        } //end if
    } //checkIn

    /**
     * Determines if a connection should be abandoned based on
     * {@link PoolProperties#abandonWhenPercentageFull} setting.
     * @return true if the connection should be abandoned
     */
    protected boolean shouldAbandon() {
        if (!poolProperties.isRemoveAbandoned()) return false;
        if (poolProperties.getAbandonWhenPercentageFull()==0) return true;
        float used = busy.size();
        float max  = poolProperties.getMaxActive();
        float perc = poolProperties.getAbandonWhenPercentageFull();
        return (used/max*100f)>=perc;
    }

    /**
     * Iterates through all the busy connections and checks for connections that have timed out
     */
    public void checkAbandoned() {
        try {
            if (busy.size()==0) return;
            Iterator<PooledConnection> locked = busy.iterator();
            int sto = getPoolProperties().getSuspectTimeout();
            while (locked.hasNext()) {
                PooledConnection con = locked.next();
                boolean setToNull = false;
                try {
                    con.lock();
                    //the con has been returned to the pool or released
                    //ignore it
                    if (idle.contains(con) || con.isReleased())
                        continue;
                    long time = con.getTimestamp();
                    long now = System.currentTimeMillis();
                    if (shouldAbandon() && (now - time) > con.getAbandonTimeout()) {
                        busy.remove(con);
                        abandon(con);
                        setToNull = true;
                    } else if (sto > 0 && (now - time) > (sto * 1000L)) {
                        suspect(con);
                    } else {
                        //do nothing
                    } //end if
                } finally {
                    con.unlock();
                    if (setToNull)
                        con = null;
                }
            } //while
        } catch (ConcurrentModificationException e) {
            log.debug("checkAbandoned failed." ,e);
        } catch (Exception e) {
            log.warn("checkAbandoned failed, it will be retried.",e);
        }
    }

    /**
     * Iterates through the idle connections and resizes the idle pool based on parameters
     * {@link PoolProperties#maxIdle}, {@link PoolProperties#minIdle}, {@link PoolProperties#minEvictableIdleTimeMillis}
     */
    public void checkIdle() {
        checkIdle(false);
    }

    public void checkIdle(boolean ignoreMinSize) {

        try {
            if (idle.size()==0) return;
            long now = System.currentTimeMillis();
            Iterator<PooledConnection> unlocked = idle.iterator();
            while ( (ignoreMinSize || (idle.size()>=getPoolProperties().getMinIdle())) && unlocked.hasNext()) {
                PooledConnection con = unlocked.next();
                boolean setToNull = false;
                try {
                    con.lock();
                    //the con been taken out, we can't clean it up
                    if (busy.contains(con))
                        continue;
                    long time = con.getTimestamp();
                    if (shouldReleaseIdle(now, con, time)) {
                        releasedIdleCount.incrementAndGet();
                        release(con);
                        idle.remove(con);
                        setToNull = true;
                    } else {
                        //do nothing
                    } //end if
                } finally {
                    con.unlock();
                    if (setToNull)
                        con = null;
                }
            } //while
        } catch (ConcurrentModificationException e) {
            log.debug("checkIdle failed." ,e);
        } catch (Exception e) {
            log.warn("checkIdle failed, it will be retried.",e);
        }

    }


    protected boolean shouldReleaseIdle(long now, PooledConnection con, long time) {
        if (con.getConnectionVersion() < getPoolVersion()) return true;
        else return (con.getReleaseTime()>0) && ((now - time) > con.getReleaseTime()) && (getSize()>getPoolProperties().getMinIdle());
    }

    /**
     * Forces a validation of all idle connections if {@link PoolProperties#testWhileIdle} is set.
     */
    public void testAllIdle() {
        try {
            if (idle.size()==0) return;
            Iterator<PooledConnection> unlocked = idle.iterator();
            while (unlocked.hasNext()) {
                PooledConnection con = unlocked.next();
                try {
                    con.lock();
                    //the con been taken out, we can't clean it up
                    if (busy.contains(con))
                        continue;
                    if (!con.validate(PooledConnection.VALIDATE_IDLE)) {
                        idle.remove(con);
                        release(con);
                    }
                } finally {
                    con.unlock();
                }
            } //while
        } catch (ConcurrentModificationException e) {
            log.debug("testAllIdle failed." ,e);
        } catch (Exception e) {
            log.warn("testAllIdle failed, it will be retried.",e);
        }

    }

    /**
     * Creates a stack trace representing the existing thread's current state.
     * @return a string object representing the current state.
     * TODO investigate if we simply should store {@link java.lang.Thread#getStackTrace()} elements
     */
    protected static String getThreadDump() {
        Exception x = new Exception();
        x.fillInStackTrace();
        return getStackTrace(x);
    }

    /**
     * Convert an exception into a String
     * @param x - the throwable
     * @return a string representing the stack trace
     */
    public static String getStackTrace(Throwable x) {
        if (x == null) {
            return null;
        } else {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            java.io.PrintStream writer = new java.io.PrintStream(bout);
            x.printStackTrace(writer);
            String result = bout.toString();
            return (x.getMessage()!=null && x.getMessage().length()>0)? x.getMessage()+";"+result:result;
        } //end if
    }


    /**
     * Create a new pooled connection object. Not connected nor validated.
     * @return a pooled connection object
     */
    protected PooledConnection create(boolean incrementCounter) {
        if (incrementCounter) size.incrementAndGet();
        PooledConnection con = new PooledConnection(getPoolProperties(), this);
        return con;
    }

    /**
     * Purges all connections in the pool.
     * For connections currently in use, these connections will be
     * purged when returned on the pool. This call also
     * purges connections that are idle and in the pool
     * To only purge used/active connections see {@link #purgeOnReturn()}
     */
    public void purge() {
        purgeOnReturn();
        checkIdle(true);
    }

    /**
     * Purges connections when they are returned from the pool.
     * This call does not purge idle connections until they are used.
     * To purge idle connections see {@link #purge()}
     */
    public void purgeOnReturn() {
        poolVersion.incrementAndGet();
    }

    /**
     * Hook to perform final actions on a pooled connection object once it has been disconnected and will be discarded
     * @param con
     */
    protected void finalize(PooledConnection con) {
        JdbcInterceptor handler = con.getHandler();
        while (handler!=null) {
            handler.reset(null, null);
            handler=handler.getNext();
        }
    }

    /**
     * Hook to perform final actions on a pooled connection object once it has been disconnected and will be discarded
     * @param con
     */
    protected void disconnectEvent(PooledConnection con, boolean finalizing) {
        JdbcInterceptor handler = con.getHandler();
        while (handler!=null) {
            handler.disconnected(this, con, finalizing);
            handler=handler.getNext();
        }
    }

    /**
     * Return the object that is potentially registered in JMX for notifications
     * @return the object implementing the {@link org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean} interface
     */
    public org.apache.tomcat.jdbc.pool.jmx.ConnectionPool getJmxPool() {
        return jmxPool;
    }

    /**
     * Create MBean object that can be registered.
     */
    protected void createMBean() {
        try {
            jmxPool = new org.apache.tomcat.jdbc.pool.jmx.ConnectionPool(this);
        } catch (Exception x) {
            log.warn("Unable to start JMX integration for connection pool. Instance["+getName()+"] can't be monitored.",x);
        }
    }

    /**
     * The total number of connections borrowed from this pool.
     * @return the borrowed connection count
     */
    public long getBorrowedCount() {
        return borrowedCount.get();
    }

    /**
     * The total number of connections returned to this pool.
     * @return the returned connection count
     */
    public long getReturnedCount() {
        return returnedCount.get();
    }

    /**
     * The total number of connections created by this pool.
     * @return the created connection count
     */
    public long getCreatedCount() {
        return createdCount.get();
    }

    /**
     * The total number of connections released from this pool.
     * @return the released connection count
     */
    public long getReleasedCount() {
        return releasedCount.get();
    }

    /**
     * The total number of connections reconnected by this pool.
     * @return the reconnected connection count
     */
    public long getReconnectedCount() {
        return reconnectedCount.get();
    }

    /**
     * The total number of connections released by remove abandoned.
     * @return the PoolCleaner removed abandoned connection count
     */
    public long getRemoveAbandonedCount() {
        return removeAbandonedCount.get();
    }

    /**
     * The total number of connections released by eviction.
     * @return the PoolCleaner evicted idle connection count
     */
    public long getReleasedIdleCount() {
        return releasedIdleCount.get();
    }

    /**
     * reset the statistics of this pool.
     */
    public void resetStats() {
        borrowedCount.set(0);
        returnedCount.set(0);
        createdCount.set(0);
        releasedCount.set(0);
        reconnectedCount.set(0);
        removeAbandonedCount.set(0);
        releasedIdleCount.set(0);
    }

    /**
     * Tread safe wrapper around a future for the regular queue
     * This one retrieves the pooled connection object
     * and performs the initialization according to
     * interceptors and validation rules.
     * This class is thread safe and is cancellable
     * @author fhanik
     *
     */
    protected class ConnectionFuture implements Future<Connection>, Runnable {
        Future<PooledConnection> pcFuture = null;
        AtomicBoolean configured = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);
        volatile Connection result = null;
        SQLException cause = null;
        AtomicBoolean cancelled = new AtomicBoolean(false);
        volatile PooledConnection pc = null;
        public ConnectionFuture(Future<PooledConnection> pcf) {
            this.pcFuture = pcf;
        }

        public ConnectionFuture(PooledConnection pc) throws SQLException {
            this.pc = pc;
            result = ConnectionPool.this.setupConnection(pc);
            configured.set(true);
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            if (pc!=null) {
                return false;
            } else if ((!cancelled.get()) && cancelled.compareAndSet(false, true)) {
                //cancel by retrieving the connection and returning it to the pool
                ConnectionPool.this.cancellator.execute(this);
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Connection get() throws InterruptedException, ExecutionException {
            try {
                return get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            }catch (TimeoutException x) {
                throw new ExecutionException(x);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Connection get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            PooledConnection pc = this.pc!=null?this.pc:pcFuture.get(timeout,unit);
            if (pc!=null) {
                if (result!=null) return result;
                if (configured.compareAndSet(false, true)) {
                    try {
                        pc = borrowConnection(System.currentTimeMillis(),pc, null, null);
                        result = ConnectionPool.this.setupConnection(pc);
                    } catch (SQLException x) {
                        cause = x;
                    } finally {
                        latch.countDown();
                    }
                } else {
                    //if we reach here, another thread is configuring the actual connection
                    latch.await(timeout,unit); //this shouldn't block for long
                }
                if (result==null) throw new ExecutionException(cause);
                return result;
            } else {
                return null;
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isCancelled() {
            return pc==null && (pcFuture.isCancelled() || cancelled.get());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isDone() {
            return pc!=null || pcFuture.isDone();
        }

        /**
         * run method to be executed when cancelled by an executor
         */
        @Override
        public void run() {
            try {
                Connection con = get(); //complete this future
                con.close(); //return to the pool
            }catch (ExecutionException ex) {
                //we can ignore this
            }catch (Exception x) {
                ConnectionPool.log.error("Unable to cancel ConnectionFuture.",x);
            }
        }

    }



    private static volatile Timer poolCleanTimer = null;
    private static HashSet<PoolCleaner> cleaners = new HashSet<PoolCleaner>();

    private static synchronized void registerCleaner(PoolCleaner cleaner) {
        unregisterCleaner(cleaner);
        cleaners.add(cleaner);
        if (poolCleanTimer == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(ConnectionPool.class.getClassLoader());
                // Create the timer thread in a PrivilegedAction so that a
                // reference to the web application class loader is not created
                // via Thread.inheritedAccessControlContext
                PrivilegedAction<Timer> pa = new PrivilegedNewTimer();
                poolCleanTimer = AccessController.doPrivileged(pa);
            } finally {
                Thread.currentThread().setContextClassLoader(loader);
            }
        }
        poolCleanTimer.schedule(cleaner, cleaner.sleepTime,cleaner.sleepTime);
    }

    private static synchronized void unregisterCleaner(PoolCleaner cleaner) {
        boolean removed = cleaners.remove(cleaner);
        if (removed) {
            cleaner.cancel();
            if (poolCleanTimer != null) {
                poolCleanTimer.purge();
                if (cleaners.size() == 0) {
                    poolCleanTimer.cancel();
                    poolCleanTimer = null;
                }
            }
        }
    }

    private static class PrivilegedNewTimer implements PrivilegedAction<Timer> {
        @Override
        public Timer run() {
            return new Timer("Tomcat JDBC Pool Cleaner["+ System.identityHashCode(ConnectionPool.class.getClassLoader()) + ":"+
                    System.currentTimeMillis() + "]", true);
        }
    }

    public static Set<TimerTask> getPoolCleaners() {
        return Collections.<TimerTask>unmodifiableSet(cleaners);
    }

    public long getPoolVersion() {
        return poolVersion.get();
    }

    public static Timer getPoolTimer() {
        return poolCleanTimer;
    }

    protected static class PoolCleaner extends TimerTask {
        protected WeakReference<ConnectionPool> pool;
        protected long sleepTime;

        PoolCleaner(ConnectionPool pool, long sleepTime) {
            this.pool = new WeakReference<ConnectionPool>(pool);
            this.sleepTime = sleepTime;
            if (sleepTime <= 0) {
                log.warn("Database connection pool evicter thread interval is set to 0, defaulting to 30 seconds");
                this.sleepTime = 1000 * 30;
            } else if (sleepTime < 1000) {
                log.warn("Database connection pool evicter thread interval is set to lower than 1 second.");
            }
        }

        @Override
        public void run() {
            ConnectionPool pool = this.pool.get();
            if (pool == null) {
                stopRunning();
            } else if (!pool.isClosed()) {
                try {
                    if (pool.getPoolProperties().isRemoveAbandoned()
                            || pool.getPoolProperties().getSuspectTimeout() > 0)
                        pool.checkAbandoned();
                    if (pool.getPoolProperties().getMinIdle() < pool.idle
                            .size())
                        pool.checkIdle();
                    if (pool.getPoolProperties().isTestWhileIdle())
                        pool.testAllIdle();
                } catch (Exception x) {
                    log.error("", x);
                }
            }
        }

        public void start() {
            registerCleaner(this);
        }

        public void stopRunning() {
            unregisterCleaner(this);
        }
    }
}
