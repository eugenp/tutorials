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

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 *
 * Thread safe non blocking selector pool
 * @author Filip Hanik
 * @version 1.0
 * @since 6.0
 */

public class NioSelectorPool {

    public NioSelectorPool() {
    }

    private static final Log log = LogFactory.getLog(NioSelectorPool.class);

    protected static final boolean SHARED =
        Boolean.parseBoolean(System.getProperty("org.apache.tomcat.util.net.NioSelectorShared", "true"));

    protected NioBlockingSelector blockingSelector;

    protected volatile Selector SHARED_SELECTOR;

    protected int maxSelectors = 200;
    protected long sharedSelectorTimeout = 30000;
    protected int maxSpareSelectors = -1;
    protected boolean enabled = true;
    protected AtomicInteger active = new AtomicInteger(0);
    protected AtomicInteger spare = new AtomicInteger(0);
    protected ConcurrentLinkedQueue<Selector> selectors =
        new ConcurrentLinkedQueue<Selector>();

    protected Selector getSharedSelector() throws IOException {
        if (SHARED && SHARED_SELECTOR == null) {
            synchronized ( NioSelectorPool.class ) {
                if ( SHARED_SELECTOR == null )  {
                    synchronized (Selector.class) {
                        // Selector.open() isn't thread safe
                        // http://bugs.sun.com/view_bug.do?bug_id=6427854
                        // Affects 1.6.0_29, fixed in 1.7.0_01
                        SHARED_SELECTOR = Selector.open();
                    }
                    log.info("Using a shared selector for servlet write/read");
                }
            }
        }
        return  SHARED_SELECTOR;
    }

    public Selector get() throws IOException{
        if ( SHARED ) {
            return getSharedSelector();
        }
        if ( (!enabled) || active.incrementAndGet() >= maxSelectors ) {
            if ( enabled ) active.decrementAndGet();
            return null;
        }
        Selector s = null;
        try {
            s = selectors.size()>0?selectors.poll():null;
            if (s == null) {
                synchronized (Selector.class) {
                    // Selector.open() isn't thread safe
                    // http://bugs.sun.com/view_bug.do?bug_id=6427854
                    // Affects 1.6.0_29, fixed in 1.7.0_01
                    s = Selector.open();
                }
            }
            else spare.decrementAndGet();

        }catch (NoSuchElementException x ) {
            try {
                synchronized (Selector.class) {
                    // Selector.open() isn't thread safe
                    // http://bugs.sun.com/view_bug.do?bug_id=6427854
                    // Affects 1.6.0_29, fixed in 1.7.0_01
                    s = Selector.open();
                }
            } catch (IOException iox) {
            }
        } finally {
            if ( s == null ) active.decrementAndGet();//we were unable to find a selector
        }
        return s;
    }



    public void put(Selector s) throws IOException {
        if ( SHARED ) return;
        if ( enabled ) active.decrementAndGet();
        if ( enabled && (maxSpareSelectors==-1 || spare.get() < Math.min(maxSpareSelectors,maxSelectors)) ) {
            spare.incrementAndGet();
            selectors.offer(s);
        }
        else s.close();
    }

    public void close() throws IOException {
        enabled = false;
        Selector s;
        while ( (s = selectors.poll()) != null ) s.close();
        spare.set(0);
        active.set(0);
        if (blockingSelector!=null) {
            blockingSelector.close();
        }
        if ( SHARED && getSharedSelector()!=null ) {
            getSharedSelector().close();
            SHARED_SELECTOR = null;
        }
    }

    public void open() throws IOException {
        enabled = true;
        getSharedSelector();
        if (SHARED) {
            blockingSelector = new NioBlockingSelector();
            blockingSelector.open(getSharedSelector());
        }

    }

    /**
     * Performs a blocking write using the bytebuffer for data to be written and a selector to block.
     * If the <code>selector</code> parameter is null, then it will perform a busy write that could
     * take up a lot of CPU cycles.
     * @param buf ByteBuffer - the buffer containing the data, we will write as long as <code>(buf.hasRemaining()==true)</code>
     * @param socket SocketChannel - the socket to write data to
     * @param selector Selector - the selector to use for blocking, if null then a busy write will be initiated
     * @param writeTimeout long - the timeout for this write operation in milliseconds, -1 means no timeout
     * @return int - returns the number of bytes written
     * @throws EOFException if write returns -1
     * @throws SocketTimeoutException if the write times out
     * @throws IOException if an IO Exception occurs in the underlying socket logic
     */
    public int write(ByteBuffer buf, NioChannel socket, Selector selector, long writeTimeout) throws IOException {
        return write(buf,socket,selector,writeTimeout,true);
    }

    public int write(ByteBuffer buf, NioChannel socket, Selector selector,
                     long writeTimeout, boolean block) throws IOException {
        if ( SHARED && block ) {
            return blockingSelector.write(buf,socket,writeTimeout);
        }
        SelectionKey key = null;
        int written = 0;
        boolean timedout = false;
        int keycount = 1; //assume we can write
        long time = System.currentTimeMillis(); //start the timeout timer
        try {
            while ( (!timedout) && buf.hasRemaining() ) {
                int cnt = 0;
                if ( keycount > 0 ) { //only write if we were registered for a write
                    cnt = socket.write(buf); //write the data
                    if (cnt == -1) throw new EOFException();

                    written += cnt;
                    if (cnt > 0) {
                        time = System.currentTimeMillis(); //reset our timeout timer
                        continue; //we successfully wrote, try again without a selector
                    }
                    if (cnt==0 && (!block)) break; //don't block
                }
                if ( selector != null ) {
                    //register OP_WRITE to the selector
                    if (key==null) key = socket.getIOChannel().register(selector, SelectionKey.OP_WRITE);
                    else key.interestOps(SelectionKey.OP_WRITE);
                    keycount = selector.select(writeTimeout);
                }
                if (writeTimeout > 0 && (selector == null || keycount == 0) ) timedout = (System.currentTimeMillis()-time)>=writeTimeout;
            }//while
            if ( timedout ) throw new SocketTimeoutException();
        } finally {
            if (key != null) {
                key.cancel();
                if (selector != null) selector.selectNow();//removes the key from this selector
            }
        }
        return written;
    }

    /**
     * Performs a blocking read using the bytebuffer for data to be read and a selector to block.
     * If the <code>selector</code> parameter is null, then it will perform a busy read that could
     * take up a lot of CPU cycles.
     * @param buf ByteBuffer - the buffer containing the data, we will read as until we have read at least one byte or we timed out
     * @param socket SocketChannel - the socket to write data to
     * @param selector Selector - the selector to use for blocking, if null then a busy read will be initiated
     * @param readTimeout long - the timeout for this read operation in milliseconds, -1 means no timeout
     * @return int - returns the number of bytes read
     * @throws EOFException if read returns -1
     * @throws SocketTimeoutException if the read times out
     * @throws IOException if an IO Exception occurs in the underlying socket logic
     */
    public int read(ByteBuffer buf, NioChannel socket, Selector selector, long readTimeout) throws IOException {
        return read(buf,socket,selector,readTimeout,true);
    }
    /**
     * Performs a read using the bytebuffer for data to be read and a selector to register for events should
     * you have the block=true.
     * If the <code>selector</code> parameter is null, then it will perform a busy read that could
     * take up a lot of CPU cycles.
     * @param buf ByteBuffer - the buffer containing the data, we will read as until we have read at least one byte or we timed out
     * @param socket SocketChannel - the socket to write data to
     * @param selector Selector - the selector to use for blocking, if null then a busy read will be initiated
     * @param readTimeout long - the timeout for this read operation in milliseconds, -1 means no timeout
     * @param block - true if you want to block until data becomes available or timeout time has been reached
     * @return int - returns the number of bytes read
     * @throws EOFException if read returns -1
     * @throws SocketTimeoutException if the read times out
     * @throws IOException if an IO Exception occurs in the underlying socket logic
     */
    public int read(ByteBuffer buf, NioChannel socket, Selector selector, long readTimeout, boolean block) throws IOException {
        if ( SHARED && block ) {
            return blockingSelector.read(buf,socket,readTimeout);
        }
        SelectionKey key = null;
        int read = 0;
        boolean timedout = false;
        int keycount = 1; //assume we can write
        long time = System.currentTimeMillis(); //start the timeout timer
        try {
            while ( (!timedout) ) {
                int cnt = 0;
                if ( keycount > 0 ) { //only read if we were registered for a read
                    cnt = socket.read(buf);
                    if (cnt == -1) throw new EOFException();
                    read += cnt;
                    if (cnt > 0) continue; //read some more
                    if (cnt==0 && (read>0 || (!block) ) ) break; //we are done reading
                }
                if ( selector != null ) {//perform a blocking read
                    //register OP_WRITE to the selector
                    if (key==null) key = socket.getIOChannel().register(selector, SelectionKey.OP_READ);
                    else key.interestOps(SelectionKey.OP_READ);
                    keycount = selector.select(readTimeout);
                }
                if (readTimeout > 0 && (selector == null || keycount == 0) ) timedout = (System.currentTimeMillis()-time)>=readTimeout;
            }//while
            if ( timedout ) throw new SocketTimeoutException();
        } finally {
            if (key != null) {
                key.cancel();
                if (selector != null) selector.selectNow();//removes the key from this selector
            }
        }
        return read;
    }

    public void setMaxSelectors(int maxSelectors) {
        this.maxSelectors = maxSelectors;
    }

    public void setMaxSpareSelectors(int maxSpareSelectors) {
        this.maxSpareSelectors = maxSpareSelectors;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setSharedSelectorTimeout(long sharedSelectorTimeout) {
        this.sharedSelectorTimeout = sharedSelectorTimeout;
    }

    public int getMaxSelectors() {
        return maxSelectors;
    }

    public int getMaxSpareSelectors() {
        return maxSpareSelectors;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public long getSharedSelectorTimeout() {
        return sharedSelectorTimeout;
    }

    public ConcurrentLinkedQueue<Selector> getSelectors() {
        return selectors;
    }

    public AtomicInteger getSpare() {
        return spare;
    }
}