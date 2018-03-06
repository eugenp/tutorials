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

package org.apache.catalina.tribes.transport.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.io.ObjectReader;
import org.apache.catalina.tribes.transport.AbstractRxTask;
import org.apache.catalina.tribes.transport.Constants;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.RxTaskPool;
import org.apache.catalina.tribes.util.StringManager;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * @author Filip Hanik
 */
public class NioReceiver extends ReceiverBase implements Runnable {

    private static final Log log = LogFactory.getLog(NioReceiver.class);

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    /**
     * The descriptive information about this implementation.
     */
    private static final String info = "NioReceiver/1.0";

    private volatile boolean running = false;

    private AtomicReference<Selector> selector = new AtomicReference<Selector>();

    private ServerSocketChannel serverChannel = null;
    private DatagramChannel datagramChannel = null;

    protected LinkedList<Runnable> events = new LinkedList<Runnable>();
//    private Object interestOpsMutex = new Object();

    public NioReceiver() {
    }

    /**
     * Return descriptive information about this implementation and the
     * corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {
        return (info);
    }

//    public Object getInterestOpsMutex() {
//        return interestOpsMutex;
//    }

    @Override
    public void stop() {
        this.stopListening();
        super.stop();
    }

    /**
     * start cluster receiver
     * @throws IOException
     * @see org.apache.catalina.tribes.ChannelReceiver#start()
     */
    @Override
    public void start() throws IOException {
        super.start();
        try {
            setPool(new RxTaskPool(getMaxThreads(),getMinThreads(),this));
        } catch (Exception x) {
            log.fatal("ThreadPool can initilzed. Listener not started", x);
            if ( x instanceof IOException ) throw (IOException)x;
            else throw new IOException(x.getMessage());
        }
        try {
            getBind();
            bind();
            String channelName = "";
            if (getChannel() instanceof GroupChannel
                    && ((GroupChannel)getChannel()).getName() != null) {
                channelName = "[" + ((GroupChannel)getChannel()).getName() + "]";
            }
            Thread t = new Thread(this, "NioReceiver" + channelName);
            t.setDaemon(true);
            t.start();
        } catch (Exception x) {
            log.fatal("Unable to start cluster receiver", x);
            if ( x instanceof IOException ) throw (IOException)x;
            else throw new IOException(x.getMessage());
        }
    }

    @Override
    public AbstractRxTask createRxTask() {
        NioReplicationTask thread = new NioReplicationTask(this,this);
        thread.setUseBufferPool(this.getUseBufferPool());
        thread.setRxBufSize(getRxBufSize());
        thread.setOptions(getWorkerThreadOptions());
        return thread;
    }



    protected void bind() throws IOException {
        // allocate an unbound server socket channel
        serverChannel = ServerSocketChannel.open();
        // Get the associated ServerSocket to bind it with
        ServerSocket serverSocket = serverChannel.socket();
        // create a new Selector for use below
        synchronized (Selector.class) {
            // Selector.open() isn't thread safe
            // http://bugs.sun.com/view_bug.do?bug_id=6427854
            // Affects 1.6.0_29, fixed in 1.7.0_01
            this.selector.set(Selector.open());
        }
        // set the port the server channel will listen to
        //serverSocket.bind(new InetSocketAddress(getBind(), getTcpListenPort()));
        bind(serverSocket,getPort(),getAutoBind());
        // set non-blocking mode for the listening socket
        serverChannel.configureBlocking(false);
        // register the ServerSocketChannel with the Selector
        serverChannel.register(this.selector.get(), SelectionKey.OP_ACCEPT);

        //set up the datagram channel
        if (this.getUdpPort()>0) {
            datagramChannel = DatagramChannel.open();
            configureDatagraChannel();
            //bind to the address to avoid security checks
            bindUdp(datagramChannel.socket(),getUdpPort(),getAutoBind());
        }
    }

    private void configureDatagraChannel() throws IOException {
        datagramChannel.configureBlocking(false);
        datagramChannel.socket().setSendBufferSize(getUdpTxBufSize());
        datagramChannel.socket().setReceiveBufferSize(getUdpRxBufSize());
        datagramChannel.socket().setReuseAddress(getSoReuseAddress());
        datagramChannel.socket().setSoTimeout(getTimeout());
        datagramChannel.socket().setTrafficClass(getSoTrafficClass());
    }

    public void addEvent(Runnable event) {
        Selector selector = this.selector.get();
        if ( selector != null ) {
            synchronized (events) {
                events.add(event);
            }
            if ( log.isTraceEnabled() ) log.trace("Adding event to selector:"+event);
            if ( isListening() ) selector.wakeup();
        }
    }

    public void events() {
        if ( events.size() == 0 ) return;
        synchronized (events) {
            Runnable r = null;
            while ( (events.size() > 0) && (r = events.removeFirst()) != null ) {
                try {
                    if ( log.isTraceEnabled() ) log.trace("Processing event in selector:"+r);
                    r.run();
                } catch ( Exception x ) {
                    log.error("",x);
                }
            }
            events.clear();
        }
    }

    public static void cancelledKey(SelectionKey key) {
        ObjectReader reader = (ObjectReader)key.attachment();
        if ( reader != null ) {
            reader.setCancelled(true);
            reader.finish();
        }
        key.cancel();
        key.attach(null);
        if (key.channel() instanceof SocketChannel)
            try { ((SocketChannel)key.channel()).socket().close(); } catch (IOException e) { if (log.isDebugEnabled()) log.debug("", e); }
        if (key.channel() instanceof DatagramChannel)
            try { ((DatagramChannel)key.channel()).socket().close(); } catch (Exception e) { if (log.isDebugEnabled()) log.debug("", e); }
        try { key.channel().close(); } catch (IOException e) { if (log.isDebugEnabled()) log.debug("", e); }

    }
    protected long lastCheck = System.currentTimeMillis();
    protected void socketTimeouts() {
        long now = System.currentTimeMillis();
        if ( (now-lastCheck) < getSelectorTimeout() ) return;
        //timeout
        Selector tmpsel = this.selector.get();
        Set<SelectionKey> keys =  (isListening()&&tmpsel!=null)?tmpsel.keys():null;
        if ( keys == null ) return;
        for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext();) {
            SelectionKey key = iter.next();
            try {
//                if (key.interestOps() == SelectionKey.OP_READ) {
//                    //only timeout sockets that we are waiting for a read from
//                    ObjectReader ka = (ObjectReader) key.attachment();
//                    long delta = now - ka.getLastAccess();
//                    if (delta > (long) getTimeout()) {
//                        cancelledKey(key);
//                    }
//                }
//                else
                if ( key.interestOps() == 0 ) {
                    //check for keys that didn't make it in.
                    ObjectReader ka = (ObjectReader) key.attachment();
                    if ( ka != null ) {
                        long delta = now - ka.getLastAccess();
                        if (delta > getTimeout() && (!ka.isAccessed())) {
                            if (log.isWarnEnabled())
                                log.warn("Channel key is registered, but has had no interest ops for the last "+getTimeout()+" ms. (cancelled:"+ka.isCancelled()+"):"+key+" last access:"+new java.sql.Timestamp(ka.getLastAccess())+" Possible cause: all threads used, perform thread dump");
                            ka.setLastAccess(now);
                            //key.interestOps(SelectionKey.OP_READ);
                        }//end if
                    } else {
                        cancelledKey(key);
                    }//end if
                }//end if
            }catch ( CancelledKeyException ckx ) {
                cancelledKey(key);
            }
        }
        lastCheck = System.currentTimeMillis();
    }


    /**
     * get data from channel and store in byte array
     * send it to cluster
     * @throws IOException
     * @throws java.nio.channels.ClosedChannelException
     */
    protected void listen() throws Exception {
        if (doListen()) {
            log.warn("ServerSocketChannel already started");
            return;
        }

        setListen(true);

        // Avoid NPEs if selector is set to null on stop.
        Selector selector = this.selector.get();

        if (selector!=null && datagramChannel!=null) {
            ObjectReader oreader = new ObjectReader(MAX_UDP_SIZE); //max size for a datagram packet
            registerChannel(selector,datagramChannel,SelectionKey.OP_READ,oreader);
        }

        while (doListen() && selector != null) {
            // this may block for a long time, upon return the
            // selected set contains keys of the ready channels
            try {
                events();
                socketTimeouts();
                int n = selector.select(getSelectorTimeout());
                if (n == 0) {
                    //there is a good chance that we got here
                    //because the TcpReplicationThread called
                    //selector wakeup().
                    //if that happens, we must ensure that that
                    //thread has enough time to call interestOps
//                    synchronized (interestOpsMutex) {
                        //if we got the lock, means there are no
                        //keys trying to register for the
                        //interestOps method
//                    }
                    continue; // nothing to do
                }
                // get an iterator over the set of selected keys
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                // look at each key in the selected set
                while (it!=null && it.hasNext()) {
                    SelectionKey key = it.next();
                    // Is a new connection coming in?
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel channel = server.accept();
                        channel.socket().setReceiveBufferSize(getTxBufSize());
                        channel.socket().setSendBufferSize(getTxBufSize());
                        channel.socket().setTcpNoDelay(getTcpNoDelay());
                        channel.socket().setKeepAlive(getSoKeepAlive());
                        channel.socket().setOOBInline(getOoBInline());
                        channel.socket().setReuseAddress(getSoReuseAddress());
                        channel.socket().setSoLinger(getSoLingerOn(),getSoLingerTime());
                        channel.socket().setSoTimeout(getTimeout());
                        Object attach = new ObjectReader(channel);
                        registerChannel(selector,
                                        channel,
                                        SelectionKey.OP_READ,
                                        attach);
                    }
                    // is there data to read on this channel?
                    if (key.isReadable()) {
                        readDataFromSocket(key);
                    } else {
                        key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                    }

                    // remove key from selected set, it's been handled
                    it.remove();
                }
            } catch (java.nio.channels.ClosedSelectorException cse) {
                // ignore is normal at shutdown or stop listen socket
            } catch (java.nio.channels.CancelledKeyException nx) {
                log.warn("Replication client disconnected, error when polling key. Ignoring client.");
            } catch (Throwable t) {
                if (t instanceof ThreadDeath) {
                    throw (ThreadDeath) t;
                }
                if (t instanceof VirtualMachineError) {
                    throw (VirtualMachineError) t;
                }
                log.error("Unable to process request in NioReceiver", t);
            }

        }
        serverChannel.close();
        if (datagramChannel!=null) {
            try {
                datagramChannel.close();
            }catch (Exception iox) {
                if (log.isDebugEnabled()) log.debug("Unable to close datagram channel.",iox);
            }
            datagramChannel=null;
        }
        closeSelector();
    }



    /**
     * Close Selector.
     *
     * @see org.apache.catalina.tribes.transport.ReceiverBase#stop()
     */
    protected void stopListening() {
        setListen(false);
        Selector selector = this.selector.get();
        if (selector != null) {
            try {
                // Unlock the thread if is is blocked waiting for input
                selector.wakeup();
                // Wait for the receiver thread to finish
                int count = 0;
                while (running && count < 50) {
                    Thread.sleep(100);
                    count ++;
                }
                if (running) {
                    log.warn(sm.getString("NioReceiver.stop.threadRunning"));
                }
                closeSelector();
            } catch (Exception x) {
                log.error("Unable to close cluster receiver selector.", x);
            } finally {
                this.selector.set(null);
            }
        }
    }

    private void closeSelector() throws IOException {
        Selector selector = this.selector.getAndSet(null);
        if (selector==null) return;
        try {
            Iterator<SelectionKey> it = selector.keys().iterator();
            // look at each key in the selected set
            while (it.hasNext()) {
                SelectionKey key = it.next();
                key.channel().close();
                key.attach(null);
                key.cancel();
            }
        }catch ( IOException ignore ){
            if (log.isWarnEnabled()) {
                log.warn("Unable to cleanup on selector close.",ignore);
            }
        }catch ( ClosedSelectorException ignore){}
        selector.close();
    }

    // ----------------------------------------------------------

    /**
     * Register the given channel with the given selector for
     * the given operations of interest
     */
    protected void registerChannel(Selector selector,
                                   SelectableChannel channel,
                                   int ops,
                                   Object attach) throws Exception {
        if (channel == null)return; // could happen
        // set the new channel non-blocking
        channel.configureBlocking(false);
        // register it with the selector
        channel.register(selector, ops, attach);
    }

    /**
     * Start thread and listen
     */
    @Override
    public void run() {
        running = true;
        try {
            listen();
        } catch (Exception x) {
            log.error("Unable to run replication listener.", x);
        } finally {
            running = false;
        }
    }

    // ----------------------------------------------------------

    /**
     * Sample data handler method for a channel with data ready to read.
     * @param key A SelectionKey object associated with a channel
     *  determined by the selector to be ready for reading.  If the
     *  channel returns an EOF condition, it is closed here, which
     *  automatically invalidates the associated key.  The selector
     *  will then de-register the channel on the next select call.
     */
    protected void readDataFromSocket(SelectionKey key) throws Exception {
        NioReplicationTask task = (NioReplicationTask) getTaskPool().getRxTask();
        if (task == null) {
            // No threads/tasks available, do nothing, the selection
            // loop will keep calling this method until a
            // thread becomes available, the thread pool itself has a waiting mechanism
            // so we will not wait here.
            if (log.isDebugEnabled()) log.debug("No TcpReplicationThread available");
        } else {
            // invoking this wakes up the worker thread then returns
            //add task to thread pool
            task.serviceChannel(key);
            getExecutor().execute(task);
        }
    }


}
