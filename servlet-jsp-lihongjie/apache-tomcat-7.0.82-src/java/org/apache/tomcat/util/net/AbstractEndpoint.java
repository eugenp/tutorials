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
package org.apache.tomcat.util.net;

import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;

import org.apache.juli.logging.Log;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.compat.JreCompat;
import org.apache.tomcat.util.net.AbstractEndpoint.Acceptor.AcceptorState;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.threads.LimitLatch;
import org.apache.tomcat.util.threads.ResizableExecutor;
import org.apache.tomcat.util.threads.TaskQueue;
import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
/**
 *
 * @author fhanik
 * @author Mladen Turk
 * @author Remy Maucherat
 */
public abstract class AbstractEndpoint<S> {

    // -------------------------------------------------------------- Constants
    protected static final StringManager sm = StringManager.getManager("org.apache.tomcat.util.net.res");

    public static interface Handler {
        /**
         * Different types of socket states to react upon.
         */
        public enum SocketState {
            // TODO Add a new state to the AsyncStateMachine and remove
            //      ASYNC_END (if possible)
            OPEN, CLOSED, LONG, ASYNC_END, SENDFILE, UPGRADING_TOMCAT,
            UPGRADING, UPGRADED
        }


        /**
         * Obtain the GlobalRequestProcessor associated with the handler.
         */
        public Object getGlobal();


        /**
         * Recycle resources associated with the handler.
         */
        public void recycle();
    }

    protected enum BindState {
        UNBOUND, BOUND_ON_INIT, BOUND_ON_START
    }

    public abstract static class Acceptor implements Runnable {
        public enum AcceptorState {
            NEW, RUNNING, PAUSED, ENDED
        }

        protected volatile AcceptorState state = AcceptorState.NEW;
        public final AcceptorState getState() {
            return state;
        }

        private String threadName;
        protected final void setThreadName(final String threadName) {
            this.threadName = threadName;
        }
        protected final String getThreadName() {
            return threadName;
        }
    }

    private static final int INITIAL_ERROR_DELAY = 50;
    private static final int MAX_ERROR_DELAY = 1600;

    // ----------------------------------------------------------------- Fields


    /**
     * Running state of the endpoint.
     */
    protected volatile boolean running = false;


    /**
     * Will be set to true whenever the endpoint is paused.
     */
    protected volatile boolean paused = false;

    /**
     * Are we using an internal executor
     */
    protected volatile boolean internalExecutor = true;


    /**
     * counter for nr of connections handled by an endpoint
     */
    private volatile LimitLatch connectionLimitLatch = null;

    /**
     * Socket properties
     */
    protected SocketProperties socketProperties = new SocketProperties();
    public SocketProperties getSocketProperties() {
        return socketProperties;
    }

    /**
     * Threads used to accept new connections and pass them to worker threads.
     */
    protected Acceptor[] acceptors;


    // ----------------------------------------------------------------- Properties

    /**
     * Time to wait for the internal executor (if used) to terminate when the
     * endpoint is stopped in milliseconds. Defaults to 5000 (5 seconds).
     */
    private long executorTerminationTimeoutMillis = 5000;

    public long getExecutorTerminationTimeoutMillis() {
        return executorTerminationTimeoutMillis;
    }

    public void setExecutorTerminationTimeoutMillis(
            long executorTerminationTimeoutMillis) {
        this.executorTerminationTimeoutMillis = executorTerminationTimeoutMillis;
    }


    /**
     * Acceptor thread count.
     */
    protected int acceptorThreadCount = 0;

    public void setAcceptorThreadCount(int acceptorThreadCount) {
        this.acceptorThreadCount = acceptorThreadCount;
    }
    public int getAcceptorThreadCount() { return acceptorThreadCount; }


    /**
     * Priority of the acceptor threads.
     */
    protected int acceptorThreadPriority = Thread.NORM_PRIORITY;
    public void setAcceptorThreadPriority(int acceptorThreadPriority) {
        this.acceptorThreadPriority = acceptorThreadPriority;
    }
    public int getAcceptorThreadPriority() { return acceptorThreadPriority; }


    private int maxConnections = 10000;
    public void setMaxConnections(int maxCon) {
        this.maxConnections = maxCon;
        LimitLatch latch = this.connectionLimitLatch;
        if (latch != null) {
            // Update the latch that enforces this
            if (maxCon == -1) {
                releaseConnectionLatch();
            } else {
                latch.setLimit(maxCon);
            }
        } else if (maxCon > 0) {
            initializeConnectionLatch();
        }
    }

    public int  getMaxConnections() { return this.maxConnections; }

    /**
     * Return the current count of connections handled by this endpoint, if the
     * connections are counted (which happens when the maximum count of
     * connections is limited), or <code>-1</code> if they are not. This
     * property is added here so that this value can be inspected through JMX.
     * It is visible on "ThreadPool" MBean.
     *
     * <p>The count is incremented by the Acceptor before it tries to accept a
     * new connection. Until the limit is reached and thus the count cannot be
     * incremented,  this value is more by 1 (the count of acceptors) than the
     * actual count of connections that are being served.
     *
     * @return The count
     */
    public long getConnectionCount() {
        LimitLatch latch = connectionLimitLatch;
        if (latch != null) {
            return latch.getCount();
        }
        return -1;
    }

    /**
     * External Executor based thread pool.
     */
    private Executor executor = null;
    public void setExecutor(Executor executor) {
        this.executor = executor;
        this.internalExecutor = (executor==null);
    }
    public Executor getExecutor() { return executor; }


    /**
     * Server socket port.
     */
    private int port;
    public int getPort() { return port; }
    public void setPort(int port ) { this.port=port; }

    public abstract int getLocalPort();

    /**
     * Address for the server socket.
     */
    private InetAddress address;
    public InetAddress getAddress() { return address; }
    public void setAddress(InetAddress address) { this.address = address; }

    /**
     * Allows the server developer to specify the backlog that
     * should be used for server sockets. By default, this value
     * is 100.
     */
    private int backlog = 100;
    public void setBacklog(int backlog) { if (backlog > 0) this.backlog = backlog; }
    public int getBacklog() { return backlog; }

    /**
     * Controls when the Endpoint binds the port. <code>true</code>, the default
     * binds the port on {@link #init()} and unbinds it on {@link #destroy()}.
     * If set to <code>false</code> the port is bound on {@link #start()} and
     * unbound on {@link #stop()}.
     */
    private boolean bindOnInit = true;
    public boolean getBindOnInit() { return bindOnInit; }
    public void setBindOnInit(boolean b) { this.bindOnInit = b; }
    private BindState bindState = BindState.UNBOUND;

    /**
     * Keepalive timeout, if not set the soTimeout is used.
     */
    private Integer keepAliveTimeout = null;
    public int getKeepAliveTimeout() {
        if (keepAliveTimeout == null) {
            return getSoTimeout();
        } else {
            return keepAliveTimeout.intValue();
        }
    }
    public void setKeepAliveTimeout(int keepAliveTimeout) {
        this.keepAliveTimeout = Integer.valueOf(keepAliveTimeout);
    }


    /**
     * Socket TCP no delay.
     */
    public boolean getTcpNoDelay() { return socketProperties.getTcpNoDelay();}
    public void setTcpNoDelay(boolean tcpNoDelay) { socketProperties.setTcpNoDelay(tcpNoDelay); }


    /**
     * Socket linger.
     */
    public int getSoLinger() { return socketProperties.getSoLingerTime(); }
    public void setSoLinger(int soLinger) {
        socketProperties.setSoLingerTime(soLinger);
        socketProperties.setSoLingerOn(soLinger>=0);
    }


    /**
     * Socket timeout.
     */
    public int getSoTimeout() { return socketProperties.getSoTimeout(); }
    public void setSoTimeout(int soTimeout) { socketProperties.setSoTimeout(soTimeout); }

    /**
     * SSL engine.
     */
    private boolean SSLEnabled = false;
    public boolean isSSLEnabled() { return SSLEnabled; }
    public void setSSLEnabled(boolean SSLEnabled) { this.SSLEnabled = SSLEnabled; }


    private int minSpareThreads = 10;
    public void setMinSpareThreads(int minSpareThreads) {
        this.minSpareThreads = minSpareThreads;
        Executor executor = this.executor;
        if (internalExecutor && executor instanceof java.util.concurrent.ThreadPoolExecutor) {
            // The internal executor should always be an instance of
            // j.u.c.ThreadPoolExecutor but it may be null if the endpoint is
            // not running.
            // This check also avoids various threading issues.
            ((java.util.concurrent.ThreadPoolExecutor) executor).setCorePoolSize(minSpareThreads);
        }
    }
    public int getMinSpareThreads() {
        return Math.min(getMinSpareThreadsInternal(), getMaxThreads());
    }
    private int getMinSpareThreadsInternal() {
        if (internalExecutor) {
            return minSpareThreads;
        } else {
            return -1;
        }
    }


    /**
     * Maximum amount of worker threads.
     */
    private int maxThreads = 200;
    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
        Executor executor = this.executor;
        if (internalExecutor && executor instanceof java.util.concurrent.ThreadPoolExecutor) {
            // The internal executor should always be an instance of
            // j.u.c.ThreadPoolExecutor but it may be null if the endpoint is
            // not running.
            // This check also avoids various threading issues.
            ((java.util.concurrent.ThreadPoolExecutor) executor).setMaximumPoolSize(maxThreads);
        }
    }
    public int getMaxThreads() {
        if (internalExecutor) {
            return maxThreads;
        } else {
            return -1;
        }
    }
    protected int getMaxThreadsInternal() {
        return maxThreads;
    }
    public int getMaxThreadsWithExecutor() {
        Executor executor = this.executor;
        if (internalExecutor) {
            return maxThreads;
        } else {
            if (executor instanceof java.util.concurrent.ThreadPoolExecutor) {
                return ((java.util.concurrent.ThreadPoolExecutor) executor).getMaximumPoolSize();
            } else if (executor instanceof ResizableExecutor) {
                return ((ResizableExecutor) executor).getMaxThreads();
            }
            return -1;
        }
    }


    /**
     * Priority of the worker threads.
     */
    protected int threadPriority = Thread.NORM_PRIORITY;
    public void setThreadPriority(int threadPriority) {
        // Can't change this once the executor has started
        this.threadPriority = threadPriority;
    }
    public int getThreadPriority() {
        if (internalExecutor) {
            return threadPriority;
        } else {
            return -1;
        }
    }


    /**
     * Max keep alive requests
     */
    private int maxKeepAliveRequests=100; // as in Apache HTTPD server
    public int getMaxKeepAliveRequests() {
        return maxKeepAliveRequests;
    }
    public void setMaxKeepAliveRequests(int maxKeepAliveRequests) {
        this.maxKeepAliveRequests = maxKeepAliveRequests;
    }

    /**
     * The maximum number of headers in a request that are allowed.
     * 100 by default. A value of less than 0 means no limit.
     */
    private int maxHeaderCount = 100; // as in Apache HTTPD server
    public int getMaxHeaderCount() {
        return maxHeaderCount;
    }
    public void setMaxHeaderCount(int maxHeaderCount) {
        this.maxHeaderCount = maxHeaderCount;
    }

    /**
     * Name of the thread pool, which will be used for naming child threads.
     */
    private String name = "TP";
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    /**
     * The default is true - the created threads will be
     *  in daemon mode. If set to false, the control thread
     *  will not be daemon - and will keep the process alive.
     */
    private boolean daemon = true;
    public void setDaemon(boolean b) { daemon = b; }
    public boolean getDaemon() { return daemon; }


    protected abstract boolean getDeferAccept();


    /**
     * Attributes provide a way for configuration to be passed to sub-components
     * without the {@link org.apache.coyote.ProtocolHandler} being aware of the
     * properties available on those sub-components. One example of such a
     * sub-component is the
     * {@link org.apache.tomcat.util.net.ServerSocketFactory}.
     */
    protected HashMap<String, Object> attributes =
        new HashMap<String, Object>();
    /**
     * Generic property setter called when a property for which a specific
     * setter already exists within the
     * {@link org.apache.coyote.ProtocolHandler} needs to be made available to
     * sub-components. The specific setter will call this method to populate the
     * attributes.
     */
    public void setAttribute(String name, Object value) {
        if (getLog().isTraceEnabled()) {
            getLog().trace(sm.getString("abstractProtocolHandler.setAttribute",
                    name, value));
        }
        attributes.put(name, value);
    }
    /**
     * Used by sub-components to retrieve configuration information.
     */
    public Object getAttribute(String key) {
        Object value = attributes.get(key);
        if (getLog().isTraceEnabled()) {
            getLog().trace(sm.getString("abstractProtocolHandler.getAttribute",
                    key, value));
        }
        return value;
    }



    public boolean setProperty(String name, String value) {
        setAttribute(name, value);
        final String socketName = "socket.";
        try {
            if (name.startsWith(socketName)) {
                return IntrospectionUtils.setProperty(socketProperties, name.substring(socketName.length()), value);
            } else {
                return IntrospectionUtils.setProperty(this,name,value,false);
            }
        }catch ( Exception x ) {
            getLog().error("Unable to set attribute \""+name+"\" to \""+value+"\"",x);
            return false;
        }
    }
    public String getProperty(String name) {
        return (String) getAttribute(name);
    }

    /**
     * Return the amount of threads that are managed by the pool.
     *
     * @return the amount of threads that are managed by the pool
     */
    public int getCurrentThreadCount() {
        if (executor!=null) {
            if (executor instanceof ThreadPoolExecutor) {
                return ((ThreadPoolExecutor)executor).getPoolSize();
            } else if (executor instanceof ResizableExecutor) {
                return ((ResizableExecutor)executor).getPoolSize();
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    /**
     * Return the amount of threads that are in use
     *
     * @return the amount of threads that are in use
     */
    public int getCurrentThreadsBusy() {
        if (executor!=null) {
            if (executor instanceof ThreadPoolExecutor) {
                return ((ThreadPoolExecutor)executor).getActiveCount();
            } else if (executor instanceof ResizableExecutor) {
                return ((ResizableExecutor)executor).getActiveCount();
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }


    public void createExecutor() {
        internalExecutor = true;
        TaskQueue taskqueue = new TaskQueue();
        TaskThreadFactory tf = new TaskThreadFactory(getName() + "-exec-", daemon, getThreadPriority());
        executor = new ThreadPoolExecutor(getMinSpareThreads(), getMaxThreads(), 60, TimeUnit.SECONDS,taskqueue, tf);
        taskqueue.setParent( (ThreadPoolExecutor) executor);
    }

    public void shutdownExecutor() {
        if ( executor!=null && internalExecutor ) {
            if ( executor instanceof ThreadPoolExecutor ) {
                //this is our internal one, so we need to shut it down
                ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;
                tpe.shutdownNow();
                long timeout = getExecutorTerminationTimeoutMillis();
                if (timeout > 0) {
                    try {
                        tpe.awaitTermination(timeout, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                    if (tpe.isTerminating()) {
                        getLog().warn(sm.getString("endpoint.warn.executorShutdown", getName()));
                    }
                }
                TaskQueue queue = (TaskQueue) tpe.getQueue();
                queue.setParent(null);
            }
            executor = null;
        }
    }

    /**
     * Unlock the server socket accept using a bogus connection.
     */
    protected void unlockAccept() {
        // Only try to unlock the acceptor if it is necessary
        boolean unlockRequired = false;
        for (Acceptor acceptor : acceptors) {
            if (acceptor.getState() == AcceptorState.RUNNING) {
                unlockRequired = true;
                break;
            }
        }
        if (!unlockRequired) {
            return;
        }

        java.net.Socket s = null;
        InetSocketAddress saddr = null;
        try {
            // Need to create a connection to unlock the accept();
            if (address == null) {
                saddr = new InetSocketAddress("localhost", getLocalPort());
            } else if (address.isAnyLocalAddress()) {
                saddr = new InetSocketAddress(getUnlockAddress(address), getLocalPort());
            } else {
                saddr = new InetSocketAddress(address, getLocalPort());
            }
            s = new java.net.Socket();
            int stmo = 2 * 1000;
            int utmo = 2 * 1000;
            if (getSocketProperties().getSoTimeout() > stmo)
                stmo = getSocketProperties().getSoTimeout();
            if (getSocketProperties().getUnlockTimeout() > utmo)
                utmo = getSocketProperties().getUnlockTimeout();
            s.setSoTimeout(stmo);
            // TODO Consider hard-coding to s.setSoLinger(true,0)
            s.setSoLinger(getSocketProperties().getSoLingerOn(),getSocketProperties().getSoLingerTime());
            if (getLog().isDebugEnabled()) {
                getLog().debug("About to unlock socket for:"+saddr);
            }
            s.connect(saddr,utmo);
            if (getDeferAccept()) {
                /*
                 * In the case of a deferred accept / accept filters we need to
                 * send data to wake up the accept. Send OPTIONS * to bypass
                 * even BSD accept filters. The Acceptor will discard it.
                 */
                OutputStreamWriter sw;

                sw = new OutputStreamWriter(s.getOutputStream(), "ISO-8859-1");
                sw.write("OPTIONS * HTTP/1.0\r\n" +
                         "User-Agent: Tomcat wakeup connection\r\n\r\n");
                sw.flush();
            }
            if (getLog().isDebugEnabled()) {
                getLog().debug("Socket unlock completed for:"+saddr);
            }

            // Wait for upto 1000ms acceptor threads to unlock
            long waitLeft = 1000;
            for (Acceptor acceptor : acceptors) {
                while (waitLeft > 0 &&
                        acceptor.getState() == AcceptorState.RUNNING) {
                    Thread.sleep(50);
                    waitLeft -= 50;
                }
            }
        } catch(Exception e) {
            if (getLog().isDebugEnabled()) {
                getLog().debug(sm.getString("endpoint.debug.unlock", "" + getPort()), e);
            }
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }


    private static InetAddress getUnlockAddress(InetAddress localAddress)
            throws SocketException, UnknownHostException {
        // Need a local address of the same type (IPv4 or IPV6) as the
        // configured bind address since the connector may be configured
        // to not map between types.
        InetAddress loopbackUnlockAddress = null;
        InetAddress linkLocalUnlockAddress = null;

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (localAddress.getAddress().getClass().isAssignableFrom(inetAddress.getClass())) {
                    if (inetAddress.isLoopbackAddress()) {
                        if (loopbackUnlockAddress == null) {
                            loopbackUnlockAddress = inetAddress;
                        }
                    } else if (inetAddress.isLinkLocalAddress()) {
                        if (linkLocalUnlockAddress == null) {
                            linkLocalUnlockAddress = inetAddress;
                        }
                    } else {
                        // Use a non-link local, non-loop back address by default
                        return inetAddress;
                    }
                }
            }
        }
        // Prefer loop back over link local since on some platforms (e.g.
        // OSX) some link local addresses are not included when listening on
        // all local addresses.
        if (loopbackUnlockAddress != null) {
            return loopbackUnlockAddress;
        }
        if (linkLocalUnlockAddress != null) {
            return linkLocalUnlockAddress;
        }
        // Fallback
        return InetAddress.getByName("localhost");
    }


    // ---------------------------------------------- Request processing methods

    public abstract void processSocketAsync(SocketWrapper<S> socketWrapper,
            SocketStatus socketStatus);

    public abstract void removeWaitingRequest(SocketWrapper<S> socketWrapper);


    // ------------------------------------------------------- Lifecycle methods

    /*
     * NOTE: There is no maintenance of state or checking for valid transitions
     * within this class other than ensuring that bind/unbind are called in the
     * right place. It is expected that the calling code will maintain state and
     * prevent invalid state transitions.
     */

    public abstract void bind() throws Exception;
    public abstract void unbind() throws Exception;
    public abstract void startInternal() throws Exception;
    public abstract void stopInternal() throws Exception;

    public final void init() throws Exception {
        testServerCipherSuitesOrderSupport();
        if (bindOnInit) {
            bind();
            bindState = BindState.BOUND_ON_INIT;
        }
    }

    private void testServerCipherSuitesOrderSupport() {
        // Only test this feature if the user explicitly requested its use.
        if(!"".equals(getUseServerCipherSuitesOrder().trim())) {
            if (!JreCompat.isJre8Available()) {
                throw new UnsupportedOperationException(
                        sm.getString("endpoint.jsse.cannotHonorServerCipherOrder"));
            }
        }
    }

    public final void start() throws Exception {
        if (bindState == BindState.UNBOUND) {
            bind();
            bindState = BindState.BOUND_ON_START;
        }
        startInternal();
    }

    protected final void startAcceptorThreads() {
        int count = getAcceptorThreadCount();
        acceptors = new Acceptor[count];

        for (int i = 0; i < count; i++) {
            acceptors[i] = createAcceptor();
            String threadName = getName() + "-Acceptor-" + i;
            acceptors[i].setThreadName(threadName);
            Thread t = new Thread(acceptors[i], threadName);
            t.setPriority(getAcceptorThreadPriority());
            t.setDaemon(getDaemon());
            t.start();
        }
    }


    /**
     * Hook to allow Endpoints to provide a specific Acceptor implementation.
     */
    protected abstract Acceptor createAcceptor();


    /**
     * Pause the endpoint, which will stop it accepting new connections.
     */
    public void pause() {
        if (running && !paused) {
            paused = true;
            unlockAccept();
        }
    }

    /**
     * Resume the endpoint, which will make it start accepting new connections
     * again.
     */
    public void resume() {
        if (running) {
            paused = false;
        }
    }

    public final void stop() throws Exception {
        stopInternal();
        if (bindState == BindState.BOUND_ON_START) {
            unbind();
            bindState = BindState.UNBOUND;
        }
    }

    public final void destroy() throws Exception {
        if (bindState == BindState.BOUND_ON_INIT) {
            unbind();
            bindState = BindState.UNBOUND;
        }
    }

    protected abstract Log getLog();
    // Flags to indicate optional feature support
    // Some of these are always hard-coded, some are hard-coded to false (i.e.
    // the endpoint does not support them) and some are configurable.
    public abstract boolean getUseSendfile();
    public abstract boolean getUseComet();
    public abstract boolean getUseCometTimeout();
    public abstract boolean getUsePolling();

    protected LimitLatch initializeConnectionLatch() {
        if (maxConnections==-1) return null;
        if (connectionLimitLatch==null) {
            connectionLimitLatch = new LimitLatch(getMaxConnections());
        }
        return connectionLimitLatch;
    }

    protected void releaseConnectionLatch() {
        LimitLatch latch = connectionLimitLatch;
        if (latch!=null) latch.releaseAll();
        connectionLimitLatch = null;
    }

    protected void countUpOrAwaitConnection() throws InterruptedException {
        if (maxConnections==-1) return;
        LimitLatch latch = connectionLimitLatch;
        if (latch!=null) latch.countUpOrAwait();
    }

    protected long countDownConnection() {
        if (maxConnections==-1) return -1;
        LimitLatch latch = connectionLimitLatch;
        if (latch!=null) {
            long result = latch.countDown();
            if (result<0) {
                getLog().warn("Incorrect connection count, multiple socket.close called on the same socket." );
            }
            return result;
        } else return -1;
    }

    /**
     * Provides a common approach for sub-classes to handle exceptions where a
     * delay is required to prevent a Thread from entering a tight loop which
     * will consume CPU and may also trigger large amounts of logging. For
     * example, this can happen with the Acceptor thread if the ulimit for open
     * files is reached.
     *
     * @param currentErrorDelay The current delay being applied on failure
     * @return  The delay to apply on the next failure
     */
    protected int handleExceptionWithDelay(int currentErrorDelay) {
        // Don't delay on first exception
        if (currentErrorDelay > 0) {
            try {
                Thread.sleep(currentErrorDelay);
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        // On subsequent exceptions, start the delay at 50ms, doubling the delay
        // on every subsequent exception until the delay reaches 1.6 seconds.
        if (currentErrorDelay == 0) {
            return INITIAL_ERROR_DELAY;
        } else if (currentErrorDelay < MAX_ERROR_DELAY) {
            return currentErrorDelay * 2;
        } else {
            return MAX_ERROR_DELAY;
        }

    }

    // --------------------  SSL related properties --------------------

    private String algorithm = KeyManagerFactory.getDefaultAlgorithm();
    public String getAlgorithm() { return algorithm;}
    public void setAlgorithm(String s ) { this.algorithm = s;}

    private String clientAuth = "false";
    public String getClientAuth() { return clientAuth;}
    public void setClientAuth(String s ) { this.clientAuth = s;}

    private String keystoreFile = System.getProperty("user.home")+"/.keystore";
    public String getKeystoreFile() { return keystoreFile;}
    public void setKeystoreFile(String s ) {
        keystoreFile = s;
    }

    private String keystorePass = null;
    public String getKeystorePass() { return keystorePass;}
    public void setKeystorePass(String s ) { this.keystorePass = s;}

    private String keystoreType = "JKS";
    public String getKeystoreType() { return keystoreType;}
    public void setKeystoreType(String s ) { this.keystoreType = s;}

    private String keystoreProvider = null;
    public String getKeystoreProvider() { return keystoreProvider;}
    public void setKeystoreProvider(String s ) { this.keystoreProvider = s;}

    private String sslProtocol = Constants.SSL_PROTO_TLS;
    public String getSslProtocol() { return sslProtocol;}
    public void setSslProtocol(String s) { sslProtocol = s;}

    private String ciphers = null;
    public String getCiphers() { return ciphers;}
    public void setCiphers(String s) {
        ciphers = s;
    }

    private String useServerCipherSuitesOrder = "";
    public String getUseServerCipherSuitesOrder() { return useServerCipherSuitesOrder;}
    public void setUseServerCipherSuitesOrder(String s) { this.useServerCipherSuitesOrder = s;}

    private String keyAlias = null;
    public String getKeyAlias() { return keyAlias;}
    public void setKeyAlias(String s ) { keyAlias = s;}

    private String keyPass = null;
    public String getKeyPass() { return keyPass;}
    public void setKeyPass(String s ) { this.keyPass = s;}

    private String truststoreFile = System.getProperty("javax.net.ssl.trustStore");
    public String getTruststoreFile() {return truststoreFile;}
    public void setTruststoreFile(String s) {
        truststoreFile = s;
    }

    private String truststorePass =
        System.getProperty("javax.net.ssl.trustStorePassword");
    public String getTruststorePass() {return truststorePass;}
    public void setTruststorePass(String truststorePass) {
        this.truststorePass = truststorePass;
    }

    private String truststoreType =
        System.getProperty("javax.net.ssl.trustStoreType");
    public String getTruststoreType() {return truststoreType;}
    public void setTruststoreType(String truststoreType) {
        this.truststoreType = truststoreType;
    }

    private String truststoreProvider = null;
    public String getTruststoreProvider() {return truststoreProvider;}
    public void setTruststoreProvider(String truststoreProvider) {
        this.truststoreProvider = truststoreProvider;
    }

    private String truststoreAlgorithm = null;
    public String getTruststoreAlgorithm() {return truststoreAlgorithm;}
    public void setTruststoreAlgorithm(String truststoreAlgorithm) {
        this.truststoreAlgorithm = truststoreAlgorithm;
    }

    private String trustManagerClassName = null;
    public String getTrustManagerClassName() {return trustManagerClassName;}
    public void setTrustManagerClassName(String trustManagerClassName) {
        this.trustManagerClassName = trustManagerClassName;
    }

    private String crlFile = null;
    public String getCrlFile() {return crlFile;}
    public void setCrlFile(String crlFile) {
        this.crlFile = crlFile;
    }

    private String trustMaxCertLength = null;
    public String getTrustMaxCertLength() {return trustMaxCertLength;}
    public void setTrustMaxCertLength(String trustMaxCertLength) {
        this.trustMaxCertLength = trustMaxCertLength;
    }

    private String sessionCacheSize = null;
    public String getSessionCacheSize() { return sessionCacheSize;}
    public void setSessionCacheSize(String s) { sessionCacheSize = s;}

    private String sessionTimeout = "86400";
    public String getSessionTimeout() { return sessionTimeout;}
    public void setSessionTimeout(String s) { sessionTimeout = s;}

    private String allowUnsafeLegacyRenegotiation = null;
    public String getAllowUnsafeLegacyRenegotiation() {
        return allowUnsafeLegacyRenegotiation;
    }
    public void setAllowUnsafeLegacyRenegotiation(String s) {
        allowUnsafeLegacyRenegotiation = s;
    }


    private String[] sslEnabledProtocolsarr =  new String[0];
    public String[] getSslEnabledProtocolsArray() {
        return this.sslEnabledProtocolsarr;
    }
    public void setSslEnabledProtocols(String s) {
        if (s == null) {
            this.sslEnabledProtocolsarr = new String[0];
        } else {
            ArrayList<String> sslEnabledProtocols = new ArrayList<String>();
            StringTokenizer t = new StringTokenizer(s,",");
            while (t.hasMoreTokens()) {
                String p = t.nextToken().trim();
                if (p.length() > 0) {
                    sslEnabledProtocols.add(p);
                }
            }
            sslEnabledProtocolsarr = sslEnabledProtocols.toArray(
                    new String[sslEnabledProtocols.size()]);
        }
    }


    /**
     * Configures SSLEngine to honor cipher suites ordering based upon
     * endpoint configuration.
     *
     * @throws InvalidAlgorithmParameterException If the runtime JVM doesn't
     *         support this setting.
     */
    protected void configureUseServerCipherSuitesOrder(SSLEngine engine) {
        String useServerCipherSuitesOrderStr = this
                .getUseServerCipherSuitesOrder().trim();

        // Only use this feature if the user explicitly requested its use.
        if(!"".equals(useServerCipherSuitesOrderStr)) {
            boolean useServerCipherSuitesOrder =
                    ("true".equalsIgnoreCase(useServerCipherSuitesOrderStr)
                            || "yes".equalsIgnoreCase(useServerCipherSuitesOrderStr));
            JreCompat jreCompat = JreCompat.getInstance();
            jreCompat.setUseServerCipherSuitesOrder(engine, useServerCipherSuitesOrder);
        }
    }
}

