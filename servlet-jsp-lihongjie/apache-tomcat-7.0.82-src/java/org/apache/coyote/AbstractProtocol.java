/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.coyote;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.coyote.http11.upgrade.servlet31.WebConnection;
import org.apache.juli.logging.Log;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;
import org.apache.tomcat.util.res.StringManager;

public abstract class AbstractProtocol<S> implements ProtocolHandler,
        MBeanRegistration {

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * Counter used to generate unique JMX names for connectors using automatic
     * port binding.
     */
    private static final AtomicInteger nameCounter = new AtomicInteger(0);


    /**
     * Name of MBean for the Global Request Processor.
     */
    protected ObjectName rgOname = null;


    /**
     * Name of MBean for the ThreadPool.
     */
    protected ObjectName tpOname = null;


    /**
     * Unique ID for this connector. Only used if the connector is configured
     * to use a random port as the port will change if stop(), start() is
     * called.
     */
    private int nameIndex = 0;


    /**
     * Endpoint that provides low-level network I/O - must be matched to the
     * ProtocolHandler implementation (ProtocolHandler using BIO, requires BIO
     * Endpoint etc.).
     */
    protected AbstractEndpoint<S> endpoint = null;


    /**
     * The maximum number of cookies permitted for a request. Use a value less
     * than zero for no limit. Defaults to 200.
     */
    private int maxCookieCount = 200;


    // ----------------------------------------------- Generic property handling

    /**
     * Generic property setter used by the digester. Other code should not need
     * to use this. The digester will only use this method if it can't find a
     * more specific setter. That means the property belongs to the Endpoint,
     * the ServerSocketFactory or some other lower level component. This method
     * ensures that it is visible to both.
     */
    public boolean setProperty(String name, String value) {
        return endpoint.setProperty(name, value);
    }


    /**
     * Generic property getter used by the digester. Other code should not need
     * to use this.
     */
    public String getProperty(String name) {
        return endpoint.getProperty(name);
    }


    // ------------------------------- Properties managed by the ProtocolHandler

    /**
     * The adapter provides the link between the ProtocolHandler and the
     * connector.
     */
    protected Adapter adapter;
    @Override
    public void setAdapter(Adapter adapter) { this.adapter = adapter; }
    @Override
    public Adapter getAdapter() { return adapter; }


    /**
     * The maximum number of idle processors that will be retained in the cache
     * and re-used with a subsequent request. The default is 200. A value of -1
     * means unlimited. In the unlimited case, the theoretical maximum number of
     * cached Processor objects is {@link #getMaxConnections()} although it will
     * usually be closer to {@link #getMaxThreads()}.
     */
    protected int processorCache = 200;
    public int getProcessorCache() { return this.processorCache; }
    public void setProcessorCache(int processorCache) {
        this.processorCache = processorCache;
    }


    /**
     * When client certificate information is presented in a form other than
     * instances of {@link java.security.cert.X509Certificate} it needs to be
     * converted before it can be used and this property controls which JSSE
     * provider is used to perform the conversion. For example it is used with
     * the AJP connectors, the HTTP APR connector and with the
     * {@link org.apache.catalina.valves.SSLValve}. If not specified, the
     * default provider will be used.
     */
    protected String clientCertProvider = null;
    public String getClientCertProvider() { return clientCertProvider; }
    public void setClientCertProvider(String s) { this.clientCertProvider = s; }


    @Override
    public boolean isAprRequired() {
        return false;
    }


    public int getMaxCookieCount() {
        return maxCookieCount;
    }


    public void setMaxCookieCount(int maxCookieCount) {
        this.maxCookieCount = maxCookieCount;
    }


    // ---------------------- Properties that are passed through to the EndPoint

    @Override
    public Executor getExecutor() { return endpoint.getExecutor(); }
    public void setExecutor(Executor executor) {
        endpoint.setExecutor(executor);
    }


    public int getMaxThreads() { return endpoint.getMaxThreads(); }
    public void setMaxThreads(int maxThreads) {
        endpoint.setMaxThreads(maxThreads);
    }

    public int getMaxConnections() { return endpoint.getMaxConnections(); }
    public void setMaxConnections(int maxConnections) {
        endpoint.setMaxConnections(maxConnections);
    }


    public int getMinSpareThreads() { return endpoint.getMinSpareThreads(); }
    public void setMinSpareThreads(int minSpareThreads) {
        endpoint.setMinSpareThreads(minSpareThreads);
    }


    public int getThreadPriority() { return endpoint.getThreadPriority(); }
    public void setThreadPriority(int threadPriority) {
        endpoint.setThreadPriority(threadPriority);
    }


    public int getBacklog() { return endpoint.getBacklog(); }
    public void setBacklog(int backlog) { endpoint.setBacklog(backlog); }


    public boolean getTcpNoDelay() { return endpoint.getTcpNoDelay(); }
    public void setTcpNoDelay(boolean tcpNoDelay) {
        endpoint.setTcpNoDelay(tcpNoDelay);
    }


    public int getSoLinger() { return endpoint.getSoLinger(); }
    public void setSoLinger(int soLinger) { endpoint.setSoLinger(soLinger); }


    public int getKeepAliveTimeout() { return endpoint.getKeepAliveTimeout(); }
    public void setKeepAliveTimeout(int keepAliveTimeout) {
        endpoint.setKeepAliveTimeout(keepAliveTimeout);
    }

    public InetAddress getAddress() { return endpoint.getAddress(); }
    public void setAddress(InetAddress ia) {
        endpoint.setAddress(ia);
    }


    public int getPort() { return endpoint.getPort(); }
    public void setPort(int port) {
        endpoint.setPort(port);
    }


    public int getLocalPort() { return endpoint.getLocalPort(); }

    /*
     * When Tomcat expects data from the client, this is the time Tomcat will
     * wait for that data to arrive before closing the connection.
     */
    public int getConnectionTimeout() {
        // Note that the endpoint uses the alternative name
        return endpoint.getSoTimeout();
    }
    public void setConnectionTimeout(int timeout) {
        // Note that the endpoint uses the alternative name
        endpoint.setSoTimeout(timeout);
    }

    /*
     * Alternative name for connectionTimeout property
     */
    public int getSoTimeout() {
        return getConnectionTimeout();
    }
    public void setSoTimeout(int timeout) {
        setConnectionTimeout(timeout);
    }

    public int getMaxHeaderCount() {
        return endpoint.getMaxHeaderCount();
    }
    public void setMaxHeaderCount(int maxHeaderCount) {
        endpoint.setMaxHeaderCount(maxHeaderCount);
    }

    public long getConnectionCount() {
        return endpoint.getConnectionCount();
    }


    // ---------------------------------------------------------- Public methods

    public synchronized int getNameIndex() {
        if (nameIndex == 0) {
            nameIndex = nameCounter.incrementAndGet();
        }

        return nameIndex;
    }


    /**
     * The name will be prefix-address-port if address is non-null and
     * prefix-port if the address is null. The name will be appropriately quoted
     * so it can be used directly in an ObjectName.
     */
    public String getName() {
        StringBuilder name = new StringBuilder(getNamePrefix());
        name.append('-');
        if (getAddress() != null) {
            name.append(getAddress().getHostAddress());
            name.append('-');
        }
        int port = getPort();
        if (port == 0) {
            // Auto binding is in use. Check if port is known
            name.append("auto-");
            name.append(getNameIndex());
            port = getLocalPort();
            if (port != -1) {
                name.append('-');
                name.append(port);
            }
        } else {
            name.append(port);
        }
        return ObjectName.quote(name.toString());
    }


    // -------------------------------------------------------- Abstract methods

    /**
     * Concrete implementations need to provide access to their logger to be
     * used by the abstract classes.
     */
    protected abstract Log getLog();


    /**
     * Obtain the prefix to be used when construction a name for this protocol
     * handler. The name will be prefix-address-port.
     */
    protected abstract String getNamePrefix();


    /**
     * Obtain the name of the protocol, (Http, Ajp, etc.). Used with JMX.
     */
    protected abstract String getProtocolName();


    /**
     * Obtain the handler associated with the underlying Endpoint
     */
    protected abstract Handler getHandler();


    // ----------------------------------------------------- JMX related methods

    protected String domain;
    protected ObjectName oname;
    protected MBeanServer mserver;

    public ObjectName getObjectName() {
        return oname;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName name)
            throws Exception {
        oname = name;
        mserver = server;
        domain = name.getDomain();
        return name;
    }

    @Override
    public void postRegister(Boolean registrationDone) {
        // NOOP
    }

    @Override
    public void preDeregister() throws Exception {
        // NOOP
    }

    @Override
    public void postDeregister() {
        // NOOP
    }

    private ObjectName createObjectName() throws MalformedObjectNameException {
        // Use the same domain as the connector
        domain = adapter.getDomain();

        if (domain == null) {
            return null;
        }

        StringBuilder name = new StringBuilder(getDomain());
        name.append(":type=ProtocolHandler,port=");
        int port = getPort();
        if (port > 0) {
            name.append(getPort());
        } else {
            name.append("auto-");
            name.append(getNameIndex());
        }
        InetAddress address = getAddress();
        if (address != null) {
            name.append(",address=");
            name.append(ObjectName.quote(address.getHostAddress()));
        }
        return new ObjectName(name.toString());
    }


    // ------------------------------------------------------- Lifecycle methods

    /*
     * NOTE: There is no maintenance of state or checking for valid transitions
     * within this class. It is expected that the connector will maintain state
     * and prevent invalid state transitions.
     */

    @Override
    public void init() throws Exception {
        if (getLog().isInfoEnabled())
            getLog().info(sm.getString("abstractProtocolHandler.init",
                    getName()));

        if (oname == null) {
            // Component not pre-registered so register it
            oname = createObjectName();
            if (oname != null) {
                Registry.getRegistry(null, null).registerComponent(this, oname,
                    null);
            }
        }

        if (this.domain != null) {
            try {
                tpOname = new ObjectName(domain + ":" +
                        "type=ThreadPool,name=" + getName());
                Registry.getRegistry(null, null).registerComponent(endpoint,
                        tpOname, null);
            } catch (Exception e) {
                getLog().error(sm.getString(
                        "abstractProtocolHandler.mbeanRegistrationFailed",
                        tpOname, getName()), e);
            }
            rgOname=new ObjectName(domain +
                    ":type=GlobalRequestProcessor,name=" + getName());
            Registry.getRegistry(null, null).registerComponent(
                    getHandler().getGlobal(), rgOname, null );
        }

        String endpointName = getName();
        endpoint.setName(endpointName.substring(1, endpointName.length()-1));

        try {
            endpoint.init();
        } catch (Exception ex) {
            getLog().error(sm.getString("abstractProtocolHandler.initError",
                    getName()), ex);
            throw ex;
        }
    }


    @Override
    public void start() throws Exception {
        if (getLog().isInfoEnabled())
            getLog().info(sm.getString("abstractProtocolHandler.start",
                    getName()));
        try {
            endpoint.start();
        } catch (Exception ex) {
            getLog().error(sm.getString("abstractProtocolHandler.startError",
                    getName()), ex);
            throw ex;
        }
    }


    @Override
    public void pause() throws Exception {
        if(getLog().isInfoEnabled())
            getLog().info(sm.getString("abstractProtocolHandler.pause",
                    getName()));
        try {
            endpoint.pause();
        } catch (Exception ex) {
            getLog().error(sm.getString("abstractProtocolHandler.pauseError",
                    getName()), ex);
            throw ex;
        }
    }

    @Override
    public void resume() throws Exception {
        if(getLog().isInfoEnabled())
            getLog().info(sm.getString("abstractProtocolHandler.resume",
                    getName()));
        try {
            endpoint.resume();
        } catch (Exception ex) {
            getLog().error(sm.getString("abstractProtocolHandler.resumeError",
                    getName()), ex);
            throw ex;
        }
    }


    @Override
    public void stop() throws Exception {
        if(getLog().isInfoEnabled())
            getLog().info(sm.getString("abstractProtocolHandler.stop",
                    getName()));
        try {
            endpoint.stop();
        } catch (Exception ex) {
            getLog().error(sm.getString("abstractProtocolHandler.stopError",
                    getName()), ex);
            throw ex;
        }
    }


    @Override
    public void destroy() {
        if(getLog().isInfoEnabled()) {
            getLog().info(sm.getString("abstractProtocolHandler.destroy",
                    getName()));
        }
        try {
            endpoint.destroy();
        } catch (Exception e) {
            getLog().error(sm.getString("abstractProtocolHandler.destroyError",
                    getName()), e);
        }

        if (oname != null) {
            Registry.getRegistry(null, null).unregisterComponent(oname);
        }

        if (tpOname != null)
            Registry.getRegistry(null, null).unregisterComponent(tpOname);
        if (rgOname != null)
            Registry.getRegistry(null, null).unregisterComponent(rgOname);
    }


    // ------------------------------------------- Connection handler base class

    protected abstract static class AbstractConnectionHandler<S,P extends Processor<S>>
            implements AbstractEndpoint.Handler {

        protected abstract Log getLog();

        protected RequestGroupInfo global = new RequestGroupInfo();
        protected AtomicLong registerCount = new AtomicLong(0);

        protected final Map<S,Processor<S>> connections = new ConcurrentHashMap<S,Processor<S>>();

        protected RecycledProcessors<P,S> recycledProcessors =
            new RecycledProcessors<P,S>(this);


        protected abstract AbstractProtocol<S> getProtocol();


        @Override
        public Object getGlobal() {
            return global;
        }

        @Override
        public void recycle() {
            recycledProcessors.clear();
        }


        @SuppressWarnings("deprecation") // Old HTTP upgrade method has been deprecated
        public SocketState process(SocketWrapper<S> wrapper,
                SocketStatus status) {
            if (wrapper == null) {
                // Nothing to do. Socket has been closed.
                return SocketState.CLOSED;
            }

            S socket = wrapper.getSocket();
            if (socket == null) {
                // Nothing to do. Socket has been closed.
                return SocketState.CLOSED;
            }

            Processor<S> processor = connections.get(socket);
            if (status == SocketStatus.DISCONNECT && processor == null) {
                // Nothing to do. Endpoint requested a close and there is no
                // longer a processor associated with this socket.
                return SocketState.CLOSED;
            }

            wrapper.setAsync(false);
            ContainerThreadMarker.markAsContainerThread();

            try {
                if (processor == null) {
                    processor = recycledProcessors.poll();
                }
                if (processor == null) {
                    processor = createProcessor();
                }

                initSsl(wrapper, processor);

                SocketState state = SocketState.CLOSED;
                do {
                    if (status == SocketStatus.DISCONNECT &&
                            !processor.isComet()) {
                        // Do nothing here, just wait for it to get recycled
                        // Don't do this for Comet we need to generate an end
                        // event (see BZ 54022)
                    } else if (processor.isAsync() || state == SocketState.ASYNC_END) {
                        state = processor.asyncDispatch(status);
                        if (state == SocketState.OPEN) {
                            // release() won't get called so in case this request
                            // takes a long time to process, remove the socket from
                            // the waiting requests now else the async timeout will
                            // fire
                            getProtocol().endpoint.removeWaitingRequest(wrapper);
                            // There may be pipe-lined data to read. If the data
                            // isn't processed now, execution will exit this
                            // loop and call release() which will recycle the
                            // processor (and input buffer) deleting any
                            // pipe-lined data. To avoid this, process it now.
                            state = processor.process(wrapper);
                        }
                    } else if (processor.isComet()) {
                        state = processor.event(status);
                    } else if (processor.getUpgradeInbound() != null) {
                        state = processor.upgradeDispatch();
                    } else if (processor.isUpgrade()) {
                        state = processor.upgradeDispatch(status);
                    } else {
                        state = processor.process(wrapper);
                    }

                    if (state != SocketState.CLOSED && processor.isAsync()) {
                        state = processor.asyncPostProcess();
                    }

                    if (state == SocketState.UPGRADING) {
                        // Get the HTTP upgrade handler
                        HttpUpgradeHandler httpUpgradeHandler =
                                processor.getHttpUpgradeHandler();
                        // Release the Http11 processor to be re-used
                        release(wrapper, processor, false, false);
                        // Create the upgrade processor
                        processor = createUpgradeProcessor(
                                wrapper, httpUpgradeHandler);
                        // Mark the connection as upgraded
                        wrapper.setUpgraded(true);
                        // Associate with the processor with the connection
                        connections.put(socket, processor);
                        // Initialise the upgrade handler (which may trigger
                        // some IO using the new protocol which is why the lines
                        // above are necessary)
                        // This cast should be safe. If it fails the error
                        // handling for the surrounding try/catch will deal with
                        // it.
                        httpUpgradeHandler.init((WebConnection) processor);
                    } else if (state == SocketState.UPGRADING_TOMCAT) {
                        // Get the UpgradeInbound handler
                        org.apache.coyote.http11.upgrade.UpgradeInbound inbound =
                                processor.getUpgradeInbound();
                        // Release the Http11 processor to be re-used
                        release(wrapper, processor, false, false);
                        // Create the light-weight upgrade processor
                        processor = createUpgradeProcessor(wrapper, inbound);
                        inbound.onUpgradeComplete();
                    }
                    if (getLog().isDebugEnabled()) {
                        getLog().debug("Socket: [" + wrapper +
                                "], Status in: [" + status +
                                "], State out: [" + state + "]");
                    }
                } while (state == SocketState.ASYNC_END ||
                        state == SocketState.UPGRADING ||
                        state == SocketState.UPGRADING_TOMCAT);

                if (state == SocketState.LONG) {
                    // In the middle of processing a request/response. Keep the
                    // socket associated with the processor. Exact requirements
                    // depend on type of long poll
                    connections.put(socket, processor);
                    longPoll(wrapper, processor);
                } else if (state == SocketState.OPEN) {
                    // In keep-alive but between requests. OK to recycle
                    // processor. Continue to poll for the next request.
                    connections.remove(socket);
                    release(wrapper, processor, false, true);
                } else if (state == SocketState.SENDFILE) {
                    // Sendfile in progress. If it fails, the socket will be
                    // closed. If it works, the socket either be added to the
                    // poller (or equivalent) to await more data or processed
                    // if there are any pipe-lined requests remaining.
                    connections.put(socket, processor);
                } else if (state == SocketState.UPGRADED) {
                    // Need to keep the connection associated with the processor
                    connections.put(socket, processor);
                    // Don't add sockets back to the poller if this was a
                    // non-blocking write otherwise the poller may trigger
                    // multiple read events which may lead to thread starvation
                    // in the connector. The write() method will add this socket
                    // to the poller if necessary.
                    if (status != SocketStatus.OPEN_WRITE) {
                        longPoll(wrapper, processor);
                    }
                } else {
                    // Connection closed. OK to recycle the processor. Upgrade
                    // processors are not recycled.
                    connections.remove(socket);
                    if (processor.isUpgrade()) {
                        processor.getHttpUpgradeHandler().destroy();
                    } else if (processor instanceof org.apache.coyote.http11.upgrade.UpgradeProcessor) {
                        // NO-OP
                    } else {
                        release(wrapper, processor, true, false);
                    }
                }
                return state;
            } catch(java.net.SocketException e) {
                // SocketExceptions are normal
                getLog().debug(sm.getString(
                        "abstractConnectionHandler.socketexception.debug"), e);
            } catch (java.io.IOException e) {
                // IOExceptions are normal
                getLog().debug(sm.getString(
                        "abstractConnectionHandler.ioexception.debug"), e);
            }
            // Future developers: if you discover any other
            // rare-but-nonfatal exceptions, catch them here, and log as
            // above.
            catch (Throwable e) {
                ExceptionUtils.handleThrowable(e);
                // any other exception or error is odd. Here we log it
                // with "ERROR" level, so it will show up even on
                // less-than-verbose logs.
                getLog().error(
                        sm.getString("abstractConnectionHandler.error"), e);
            }
            // Make sure socket/processor is removed from the list of current
            // connections
            connections.remove(socket);
            // Don't try to add upgrade processors back into the pool
            if (!(processor instanceof org.apache.coyote.http11.upgrade.UpgradeProcessor)
                    && !processor.isUpgrade()) {
                release(wrapper, processor, true, false);
            }
            return SocketState.CLOSED;
        }

        protected abstract P createProcessor();
        protected abstract void initSsl(SocketWrapper<S> socket,
                Processor<S> processor);
        protected abstract void longPoll(SocketWrapper<S> socket,
                Processor<S> processor);
        protected abstract void release(SocketWrapper<S> socket,
                Processor<S> processor, boolean socketClosing,
                boolean addToPoller);
        /**
         * @deprecated  Will be removed in Tomcat 8.0.x.
         */
        @Deprecated
        protected abstract Processor<S> createUpgradeProcessor(
                SocketWrapper<S> socket,
                org.apache.coyote.http11.upgrade.UpgradeInbound inbound) throws IOException;
        protected abstract Processor<S> createUpgradeProcessor(
                SocketWrapper<S> socket,
                HttpUpgradeHandler httpUpgradeProcessor) throws IOException;

        protected void register(AbstractProcessor<S> processor) {
            if (getProtocol().getDomain() != null) {
                synchronized (this) {
                    try {
                        long count = registerCount.incrementAndGet();
                        RequestInfo rp =
                            processor.getRequest().getRequestProcessor();
                        rp.setGlobalProcessor(global);
                        ObjectName rpName = new ObjectName(
                                getProtocol().getDomain() +
                                ":type=RequestProcessor,worker="
                                + getProtocol().getName() +
                                ",name=" + getProtocol().getProtocolName() +
                                "Request" + count);
                        if (getLog().isDebugEnabled()) {
                            getLog().debug("Register " + rpName);
                        }
                        Registry.getRegistry(null, null).registerComponent(rp,
                                rpName, null);
                        rp.setRpName(rpName);
                    } catch (Exception e) {
                        getLog().warn("Error registering request");
                    }
                }
            }
        }

        protected void unregister(Processor<S> processor) {
            if (getProtocol().getDomain() != null) {
                synchronized (this) {
                    try {
                        Request r = processor.getRequest();
                        if (r == null) {
                            // Probably an UpgradeProcessor
                            return;
                        }
                        RequestInfo rp = r.getRequestProcessor();
                        rp.setGlobalProcessor(null);
                        ObjectName rpName = rp.getRpName();
                        if (getLog().isDebugEnabled()) {
                            getLog().debug("Unregister " + rpName);
                        }
                        Registry.getRegistry(null, null).unregisterComponent(
                                rpName);
                        rp.setRpName(null);
                    } catch (Exception e) {
                        getLog().warn("Error unregistering request", e);
                    }
                }
            }
        }
    }

    protected static class RecycledProcessors<P extends Processor<S>, S>
            extends ConcurrentLinkedQueue<Processor<S>> {

        private static final long serialVersionUID = 1L;
        private transient AbstractConnectionHandler<S,P> handler;
        protected AtomicInteger size = new AtomicInteger(0);

        public RecycledProcessors(AbstractConnectionHandler<S,P> handler) {
            this.handler = handler;
        }

        @Override
        public boolean offer(Processor<S> processor) {
            int cacheSize = handler.getProtocol().getProcessorCache();
            boolean offer = cacheSize == -1 ? true : size.get() < cacheSize;
            //avoid over growing our cache or add after we have stopped
            boolean result = false;
            if (offer) {
                result = super.offer(processor);
                if (result) {
                    size.incrementAndGet();
                }
            }
            if (!result) handler.unregister(processor);
            return result;
        }

        @Override
        public Processor<S> poll() {
            Processor<S> result = super.poll();
            if (result != null) {
                size.decrementAndGet();
            }
            return result;
        }

        @Override
        public void clear() {
            Processor<S> next = poll();
            while (next != null) {
                handler.unregister(next);
                next = poll();
            }
            super.clear();
            size.set(0);
        }
    }
}
