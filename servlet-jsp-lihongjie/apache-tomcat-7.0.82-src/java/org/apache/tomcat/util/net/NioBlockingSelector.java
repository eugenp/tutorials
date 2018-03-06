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
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.net.NioEndpoint.KeyAttachment;

public class NioBlockingSelector {

    private static final Log log = LogFactory.getLog(NioBlockingSelector.class);

    private static int threadCounter = 0;

    private Queue<KeyReference> keyReferenceQueue =
            new ConcurrentLinkedQueue<KeyReference>();

    protected Selector sharedSelector;

    protected BlockPoller poller;
    public NioBlockingSelector() {

    }

    public void open(Selector selector) {
        sharedSelector = selector;
        poller = new BlockPoller();
        poller.selector = sharedSelector;
        poller.setDaemon(true);
        poller.setName("NioBlockingSelector.BlockPoller-"+(++threadCounter));
        poller.start();
    }

    public void close() {
        if (poller!=null) {
            poller.disable();
            poller.interrupt();
            poller = null;
        }
    }

    /**
     * Performs a blocking write using the bytebuffer for data to be written
     * If the <code>selector</code> parameter is null, then it will perform a busy write that could
     * take up a lot of CPU cycles.
     * @param buf ByteBuffer - the buffer containing the data, we will write as long as <code>(buf.hasRemaining()==true)</code>
     * @param socket SocketChannel - the socket to write data to
     * @param writeTimeout long - the timeout for this write operation in milliseconds, -1 means no timeout
     * @return int - returns the number of bytes written
     * @throws EOFException if write returns -1
     * @throws SocketTimeoutException if the write times out
     * @throws IOException if an IO Exception occurs in the underlying socket logic
     */
    public int write(ByteBuffer buf, NioChannel socket, long writeTimeout)
            throws IOException {
        SelectionKey key = socket.getIOChannel().keyFor(socket.getPoller().getSelector());
        if ( key == null ) throw new IOException("Key no longer registered");
        KeyReference reference = keyReferenceQueue.poll();
        if (reference == null) {
            reference = new KeyReference();
        }
        KeyAttachment att = (KeyAttachment) key.attachment();
        int written = 0;
        boolean timedout = false;
        int keycount = 1; //assume we can write
        long time = System.currentTimeMillis(); //start the timeout timer
        try {
            while ( (!timedout) && buf.hasRemaining()) {
                if (keycount > 0) { //only write if we were registered for a write
                    int cnt = socket.write(buf); //write the data
                    if (cnt == -1)
                        throw new EOFException();
                    written += cnt;
                    if (cnt > 0) {
                        time = System.currentTimeMillis(); //reset our timeout timer
                        continue; //we successfully wrote, try again without a selector
                    }
                }
                try {
                    if ( att.getWriteLatch()==null || att.getWriteLatch().getCount()==0) att.startWriteLatch(1);
                    poller.add(att,SelectionKey.OP_WRITE,reference);
                    if (writeTimeout < 0) {
                        att.awaitWriteLatch(Long.MAX_VALUE,TimeUnit.MILLISECONDS);
                    } else {
                        att.awaitWriteLatch(writeTimeout,TimeUnit.MILLISECONDS);
                    }
                }catch (InterruptedException ignore) {
                    Thread.interrupted();
                }
                if ( att.getWriteLatch()!=null && att.getWriteLatch().getCount()> 0) {
                    //we got interrupted, but we haven't received notification from the poller.
                    keycount = 0;
                }else {
                    //latch countdown has happened
                    keycount = 1;
                    att.resetWriteLatch();
                }

                if (writeTimeout > 0 && (keycount == 0))
                    timedout = (System.currentTimeMillis() - time) >= writeTimeout;
            } //while
            if (timedout)
                throw new SocketTimeoutException();
        } finally {
            poller.remove(att,SelectionKey.OP_WRITE);
            if (timedout && reference.key!=null) {
                poller.cancelKey(reference.key);
            }
            reference.key = null;
            keyReferenceQueue.add(reference);
        }
        return written;
    }

    /**
     * Performs a blocking read using the bytebuffer for data to be read
     * If the <code>selector</code> parameter is null, then it will perform a busy read that could
     * take up a lot of CPU cycles.
     * @param buf ByteBuffer - the buffer containing the data, we will read as until we have read at least one byte or we timed out
     * @param socket SocketChannel - the socket to write data to
     * @param readTimeout long - the timeout for this read operation in milliseconds, -1 means no timeout
     * @return int - returns the number of bytes read
     * @throws EOFException if read returns -1
     * @throws SocketTimeoutException if the read times out
     * @throws IOException if an IO Exception occurs in the underlying socket logic
     */
    public int read(ByteBuffer buf, NioChannel socket, long readTimeout) throws IOException {
        SelectionKey key = socket.getIOChannel().keyFor(socket.getPoller().getSelector());
        if ( key == null ) throw new IOException("Key no longer registered");
        KeyReference reference = keyReferenceQueue.poll();
        if (reference == null) {
            reference = new KeyReference();
        }
        KeyAttachment att = (KeyAttachment) key.attachment();
        int read = 0;
        boolean timedout = false;
        int keycount = 1; //assume we can read
        long time = System.currentTimeMillis(); //start the timeout timer
        try {
            while(!timedout) {
                if (keycount > 0) { //only read if we were registered for a read
                    read = socket.read(buf);
                    if (read == -1)
                        throw new EOFException();
                    if (read > 0)
                        break;
                }
                try {
                    if ( att.getReadLatch()==null || att.getReadLatch().getCount()==0) att.startReadLatch(1);
                    poller.add(att,SelectionKey.OP_READ, reference);
                    if (readTimeout < 0) {
                        att.awaitReadLatch(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                    } else {
                        att.awaitReadLatch(readTimeout, TimeUnit.MILLISECONDS);
                    }
                }catch (InterruptedException ignore) {
                    Thread.interrupted();
                }
                if ( att.getReadLatch()!=null && att.getReadLatch().getCount()> 0) {
                    //we got interrupted, but we haven't received notification from the poller.
                    keycount = 0;
                }else {
                    //latch countdown has happened
                    keycount = 1;
                    att.resetReadLatch();
                }
                if (readTimeout >= 0 && (keycount == 0))
                    timedout = (System.currentTimeMillis() - time) >= readTimeout;
            } //while
            if (timedout)
                throw new SocketTimeoutException();
        } finally {
            poller.remove(att,SelectionKey.OP_READ);
            if (timedout && reference.key!=null) {
                poller.cancelKey(reference.key);
            }
            reference.key = null;
            keyReferenceQueue.add(reference);
        }
        return read;
    }


    protected static class BlockPoller extends Thread {
        protected volatile boolean run = true;
        protected Selector selector = null;
        protected ConcurrentLinkedQueue<Runnable> events = new ConcurrentLinkedQueue<Runnable>();
        public void disable() { run = false; selector.wakeup();}
        protected AtomicInteger wakeupCounter = new AtomicInteger(0);
        public void cancelKey(final SelectionKey key) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    key.cancel();
                }
            };
            events.offer(r);
            wakeup();
        }

        public void wakeup() {
            if (wakeupCounter.addAndGet(1)==0) selector.wakeup();
        }

        public void cancel(SelectionKey sk, KeyAttachment key, int ops){
            if (sk!=null) {
                sk.cancel();
                sk.attach(null);
                if (SelectionKey.OP_WRITE==(ops&SelectionKey.OP_WRITE)) countDown(key.getWriteLatch());
                if (SelectionKey.OP_READ==(ops&SelectionKey.OP_READ))countDown(key.getReadLatch());
            }
        }

        public void add(final KeyAttachment key, final int ops, final KeyReference ref) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if ( key == null ) return;
                    NioChannel nch = key.getChannel();
                    if ( nch == null ) return;
                    SocketChannel ch = nch.getIOChannel();
                    if ( ch == null ) return;
                    SelectionKey sk = ch.keyFor(selector);
                    try {
                        if (sk == null) {
                            sk = ch.register(selector, ops, key);
                            ref.key = sk;
                        } else if (!sk.isValid()) {
                            cancel(sk,key,ops);
                        } else {
                            sk.interestOps(sk.interestOps() | ops);
                        }
                    }catch (CancelledKeyException cx) {
                        cancel(sk,key,ops);
                    }catch (ClosedChannelException cx) {
                        cancel(sk,key,ops);
                    }
                }
            };
            events.offer(r);
            wakeup();
        }

        public void remove(final KeyAttachment key, final int ops) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if ( key == null ) return;
                    NioChannel nch = key.getChannel();
                    if ( nch == null ) return;
                    SocketChannel ch = nch.getIOChannel();
                    if ( ch == null ) return;
                    SelectionKey sk = ch.keyFor(selector);
                    try {
                        if (sk == null) {
                            if (SelectionKey.OP_WRITE==(ops&SelectionKey.OP_WRITE)) countDown(key.getWriteLatch());
                            if (SelectionKey.OP_READ==(ops&SelectionKey.OP_READ))countDown(key.getReadLatch());
                        } else {
                            if (sk.isValid()) {
                                sk.interestOps(sk.interestOps() & (~ops));
                                if (SelectionKey.OP_WRITE==(ops&SelectionKey.OP_WRITE)) countDown(key.getWriteLatch());
                                if (SelectionKey.OP_READ==(ops&SelectionKey.OP_READ))countDown(key.getReadLatch());
                                if (sk.interestOps()==0) {
                                    sk.cancel();
                                    sk.attach(null);
                                }
                            }else {
                                sk.cancel();
                                sk.attach(null);
                            }
                        }
                    }catch (CancelledKeyException cx) {
                        if (sk!=null) {
                            sk.cancel();
                            sk.attach(null);
                        }
                    }
                }
            };
            events.offer(r);
            wakeup();
        }


        public boolean events() {
            boolean result = false;
            Runnable r = null;
            result = (events.size() > 0);
            while ( (r = events.poll()) != null ) {
                r.run();
                result = true;
            }
            return result;
        }

        @Override
        public void run() {
            while (run) {
                try {
                    events();
                    int keyCount = 0;
                    try {
                        int i = wakeupCounter.get();
                        if (i>0)
                            keyCount = selector.selectNow();
                        else {
                            wakeupCounter.set(-1);
                            keyCount = selector.select(1000);
                        }
                        wakeupCounter.set(0);
                        if (!run) break;
                    }catch ( NullPointerException x ) {
                        //sun bug 5076772 on windows JDK 1.5
                        if (selector==null) throw x;
                        if ( log.isDebugEnabled() ) log.debug("Possibly encountered sun bug 5076772 on windows JDK 1.5",x);
                        continue;
                    } catch ( CancelledKeyException x ) {
                        //sun bug 5076772 on windows JDK 1.5
                        if ( log.isDebugEnabled() ) log.debug("Possibly encountered sun bug 5076772 on windows JDK 1.5",x);
                        continue;
                    } catch (Throwable x) {
                        ExceptionUtils.handleThrowable(x);
                        log.error("",x);
                        continue;
                    }

                    Iterator<SelectionKey> iterator = keyCount > 0 ? selector.selectedKeys().iterator() : null;

                    // Walk through the collection of ready keys and dispatch
                    // any active event.
                    while (run && iterator != null && iterator.hasNext()) {
                        SelectionKey sk = iterator.next();
                        KeyAttachment attachment = (KeyAttachment)sk.attachment();
                        try {
                            attachment.access();
                            iterator.remove();
                            sk.interestOps(sk.interestOps() & (~sk.readyOps()));
                            if ( sk.isReadable() ) {
                                countDown(attachment.getReadLatch());
                            }
                            if (sk.isWritable()) {
                                countDown(attachment.getWriteLatch());
                            }
                        }catch (CancelledKeyException ckx) {
                            sk.cancel();
                            countDown(attachment.getReadLatch());
                            countDown(attachment.getWriteLatch());
                        }
                    }//while
                }catch ( Throwable t ) {
                    log.error("",t);
                }
            }
            events.clear();
            // If using a shared selector, the NioSelectorPool will also try and
            // close the selector. Try and avoid the ClosedSelectorException
            // although because multiple threads are involved there is always
            // the possibility of an Exception here.
            if (selector.isOpen()) {
                try {
                    // Cancels all remaining keys
                    selector.selectNow();
                }catch( Exception ignore ) {
                    if (log.isDebugEnabled())log.debug("",ignore);
                }
            }
            try {
                selector.close();
            }catch( Exception ignore ) {
                if (log.isDebugEnabled())log.debug("",ignore);
            }
        }

        public void countDown(CountDownLatch latch) {
            if ( latch == null ) return;
            latch.countDown();
        }
    }

    public static class KeyReference {
        SelectionKey key = null;

        @Override
        public void finalize() throws Throwable {
            if (key!=null && key.isValid()) {
                log.warn("Possible key leak, cancelling key in the finalizer.");
                try {key.cancel();}catch (Exception ignore){}
            }
            key = null;
            
            super.finalize();
        }
    }

}
