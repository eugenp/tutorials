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
package org.apache.catalina.tribes.transport;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.ChannelReceiver;
import org.apache.catalina.tribes.MessageListener;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.io.ListenCallback;
import org.apache.catalina.tribes.util.ExecutorFactory;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class ReceiverBase implements ChannelReceiver, ListenCallback, RxTaskPool.TaskCreator {

    public static final int OPTION_DIRECT_BUFFER = 0x0004;

    private static final Log log = LogFactory.getLog(ReceiverBase.class);

    private static final Object bindLock = new Object();

    private MessageListener listener;
    private String host = "auto";
    private InetAddress bind;
    private int port  = 4000;
    private int udpPort = -1;
    private int securePort = -1;
    private int rxBufSize = 43800;
    private int txBufSize = 25188;
    private int udpRxBufSize = 43800;
    private int udpTxBufSize = 25188;

    private volatile boolean listen = false;
    private RxTaskPool pool;
    private boolean direct = true;
    private long tcpSelectorTimeout = 5000;
    //how many times to search for an available socket
    private int autoBind = 100;
    private int maxThreads = 15;
    private int minThreads = 6;
    private int maxTasks = 100;
    private int minTasks = 10;
    private boolean tcpNoDelay = true;
    private boolean soKeepAlive = false;
    private boolean ooBInline = true;
    private boolean soReuseAddress = true;
    private boolean soLingerOn = true;
    private int soLingerTime = 3;
    private int soTrafficClass = 0x04 | 0x08 | 0x010;
    private int timeout = 3000; //3 seconds
    private boolean useBufferPool = true;
    private boolean daemon = true;
    private long maxIdleTime = 60000;
    
    private ExecutorService executor;
    private Channel channel;


    public ReceiverBase() {
    }

    @Override
    public void start() throws IOException {
        if ( executor == null ) {
            //executor = new ThreadPoolExecutor(minThreads,maxThreads,60,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
            String channelName = "";
            if (channel instanceof GroupChannel && ((GroupChannel)channel).getName() != null) {
                channelName = "[" + ((GroupChannel)channel).getName() + "]";
            }
            TaskThreadFactory tf = new TaskThreadFactory("Tribes-Task-Receiver" + channelName + "-");
            executor = ExecutorFactory.newThreadPool(minThreads, maxThreads, maxIdleTime, TimeUnit.MILLISECONDS, tf);
        }
    }

    @Override
    public void stop() {
        if ( executor != null ) executor.shutdownNow();//ignore left overs
        executor = null;
        channel = null;
    }

    /**
     * getMessageListener
     *
     * @return MessageListener
     */
    @Override
    public MessageListener getMessageListener() {
        return listener;
    }

    /**
     *
     * @return The port
     */
    @Override
    public int getPort() {
        return port;
    }

    public int getRxBufSize() {
        return rxBufSize;
    }

    public int getTxBufSize() {
        return txBufSize;
    }

    /**
     * @deprecated use getMinThreads()/getMaxThreads()
     * @return int
     */
    @Deprecated
    public int getTcpThreadCount() {
        return getMaxThreads();
    }

    /**
     * setMessageListener
     *
     * @param listener MessageListener
     */
    @Override
    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    /**
     * @deprecated use setPort
     * @param tcpListenPort int
     */
    @Deprecated
    public void setTcpListenPort(int tcpListenPort) {
        setPort(tcpListenPort);
    }

    /**
     * @deprecated use setAddress
     * @param tcpListenHost String
     */
    @Deprecated
    public void setTcpListenAddress(String tcpListenHost) {
        setAddress(tcpListenHost);
    }

    public void setRxBufSize(int rxBufSize) {
        this.rxBufSize = rxBufSize;
    }

    public void setTxBufSize(int txBufSize) {
        this.txBufSize = txBufSize;
    }

    /**
     * @deprecated use setMaxThreads/setMinThreads
     * @param tcpThreadCount int
     */
    @Deprecated
    public void setTcpThreadCount(int tcpThreadCount) {
        setMaxThreads(tcpThreadCount);
        setMinThreads(tcpThreadCount);
    }

    /**
     * @return Returns the bind.
     */
    public InetAddress getBind() {
        if (bind == null) {
            try {
                if ("auto".equals(host)) {
                    host = java.net.InetAddress.getLocalHost().getHostAddress();
                }
                if (log.isDebugEnabled())
                    log.debug("Starting replication listener on address:"+ host);
                bind = java.net.InetAddress.getByName(host);
            } catch (IOException ioe) {
                log.error("Failed bind replication listener on address:"+ host, ioe);
            }
        }
        return bind;
    }

    /**
     * Attempts to bind using the provided port and if that fails attempts to
     * bind to each of the ports from portstart to (portstart + retries -1)
     * until either there are no more ports or the bind is successful. The
     * address to bind to is obtained via a call to {link {@link #getBind()}.
     * @param socket        The socket to bind
     * @param portstart     Starting port for bind attempts
     * @param retries       Number of times to attempt to bind (port incremented
     *                      between attempts)
     * @throws IOException
     */
    protected void bind(ServerSocket socket, int portstart, int retries) throws IOException {
        synchronized (bindLock) {
            InetSocketAddress addr = null;
            int port = portstart;
            while (retries > 0) {
                try {
                    addr = new InetSocketAddress(getBind(), port);
                    socket.bind(addr);
                    setPort(port);
                    log.info("Receiver Server Socket bound to:"+addr);
                    retries = 0;
                } catch ( IOException x) {
                    retries--;
                    if ( retries <= 0 ) {
                        log.info("Unable to bind server socket to:" + addr +
                                " throwing error.");
                        throw x;
                    }
                    port++;
                }
            }
        }
    }

    /**
     * Same as bind() except it does it for the UDP port
     * @param socket
     * @param portstart
     * @param retries
     * @return int
     * @throws IOException
     */
    protected int bindUdp(DatagramSocket socket, int portstart, int retries) throws IOException {
        InetSocketAddress addr = null;
        while ( retries > 0 ) {
            try {
                addr = new InetSocketAddress(getBind(), portstart);
                socket.bind(addr);
                setUdpPort(portstart);
                log.info("UDP Receiver Server Socket bound to:"+addr);
                return 0;
            }catch ( IOException x) {
                retries--;
                if ( retries <= 0 ) {
                    log.info("Unable to bind UDP socket to:"+addr+" throwing error.");
                    throw x;
                }
                portstart++;
                try {
                    Thread.sleep(25);
                } catch (InterruptedException ti) {
                    Thread.currentThread().interrupt();
                }
                retries = bindUdp(socket,portstart,retries);
            }
        }
        return retries;
    }


    @Override
    public void messageDataReceived(ChannelMessage data) {
        if ( this.listener != null ) {
            if ( listener.accept(data) ) listener.messageReceived(data);
        }
    }

    public int getWorkerThreadOptions() {
        int options = 0;
        if ( getDirect() ) options = options | OPTION_DIRECT_BUFFER;
        return options;
    }


    /**
     * @param bind The bind to set.
     */
    public void setBind(java.net.InetAddress bind) {
        this.bind = bind;
    }

    /**
     * @deprecated use getPort
     * @return int
     */
    @Deprecated
    public int getTcpListenPort() {
        return getPort();
    }


    public boolean getDirect() {
        return direct;
    }



    public void setDirect(boolean direct) {
        this.direct = direct;
    }


    public String getAddress() {
        getBind();
        return this.host;
    }

    @Override
    public String getHost() {
        return getAddress();
    }

    public long getSelectorTimeout() {
        return tcpSelectorTimeout;
    }
    /**
     * @deprecated use getSelectorTimeout
     * @return long
     */
    @Deprecated
    public long getTcpSelectorTimeout() {
        return getSelectorTimeout();
    }

    public boolean doListen() {
        return listen;
    }

    public MessageListener getListener() {
        return listener;
    }

    public RxTaskPool getTaskPool() {
        return pool;
    }

    /**
     * @deprecated use getAddress
     * @return String
     */
    @Deprecated
    public String getTcpListenAddress() {
        return getAddress();
    }

    public int getAutoBind() {
        return autoBind;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public boolean getTcpNoDelay() {
        return tcpNoDelay;
    }

    public boolean getSoKeepAlive() {
        return soKeepAlive;
    }

    public boolean getOoBInline() {
        return ooBInline;
    }


    public boolean getSoLingerOn() {
        return soLingerOn;
    }

    public int getSoLingerTime() {
        return soLingerTime;
    }

    public boolean getSoReuseAddress() {
        return soReuseAddress;
    }

    public int getSoTrafficClass() {
        return soTrafficClass;
    }

    public int getTimeout() {
        return timeout;
    }

    public boolean getUseBufferPool() {
        return useBufferPool;
    }

    @Override
    public int getSecurePort() {
        return securePort;
    }

    public int getMinTasks() {
        return minTasks;
    }

    public int getMaxTasks() {
        return maxTasks;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public boolean isListening() {
        return listen;
    }

    /**
     * @deprecated use setSelectorTimeout
     * @param selTimeout long
     */
    @Deprecated
    public void setTcpSelectorTimeout(long selTimeout) {
        setSelectorTimeout(selTimeout);
    }

    public void setSelectorTimeout(long selTimeout) {
        tcpSelectorTimeout = selTimeout;
    }

    public void setListen(boolean doListen) {
        this.listen = doListen;
    }


    public void setAddress(String host) {
        this.host = host;
    }
    public void setHost(String host) {
        setAddress(host);
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    public void setPool(RxTaskPool pool) {
        this.pool = pool;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAutoBind(int autoBind) {
        this.autoBind = autoBind;
        if ( this.autoBind <= 0 ) this.autoBind = 1;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
    }

    public void setOoBInline(boolean ooBInline) {
        this.ooBInline = ooBInline;
    }


    public void setSoLingerOn(boolean soLingerOn) {
        this.soLingerOn = soLingerOn;
    }

    public void setSoLingerTime(int soLingerTime) {
        this.soLingerTime = soLingerTime;
    }

    public void setSoReuseAddress(boolean soReuseAddress) {
        this.soReuseAddress = soReuseAddress;
    }

    public void setSoTrafficClass(int soTrafficClass) {
        this.soTrafficClass = soTrafficClass;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setUseBufferPool(boolean useBufferPool) {
        this.useBufferPool = useBufferPool;
    }

    public void setSecurePort(int securePort) {
        this.securePort = securePort;
    }

    public void setMinTasks(int minTasks) {
        this.minTasks = minTasks;
    }

    public void setMaxTasks(int maxTasks) {
        this.maxTasks = maxTasks;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void heartbeat() {
        //empty operation
    }

    @Override
    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public int getUdpRxBufSize() {
        return udpRxBufSize;
    }

    public void setUdpRxBufSize(int udpRxBufSize) {
        this.udpRxBufSize = udpRxBufSize;
    }

    public int getUdpTxBufSize() {
        return udpTxBufSize;
    }

    public void setUdpTxBufSize(int udpTxBufSize) {
        this.udpTxBufSize = udpTxBufSize;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    // ---------------------------------------------- ThreadFactory Inner Class
    class TaskThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        TaskThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(daemon);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    public boolean isDaemon() {
        return daemon;
    }

    public long getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public void setMaxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }    
    
}