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
import java.nio.channels.SocketChannel;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 *
 * Implementation of a secure socket channel
 * @author Filip Hanik
 * @version 1.0
 */

public class SecureNioChannel extends NioChannel  {

    protected static final Log log = LogFactory.getLog(SecureNioChannel.class);

    protected ByteBuffer netInBuffer;
    protected ByteBuffer netOutBuffer;

    protected SSLEngine sslEngine;

    protected boolean handshakeComplete = false;
    protected HandshakeStatus handshakeStatus; //gets set by handshake

    protected boolean closed = false;
    protected boolean closing = false;

    protected NioSelectorPool pool;

    public SecureNioChannel(SocketChannel channel, SSLEngine engine,
                            ApplicationBufferHandler bufHandler, NioSelectorPool pool) throws IOException {
        super(channel,bufHandler);
        this.sslEngine = engine;
        int appBufSize = sslEngine.getSession().getApplicationBufferSize();
        int netBufSize = sslEngine.getSession().getPacketBufferSize();
        //allocate network buffers - TODO, add in optional direct non-direct buffers
        if ( netInBuffer == null ) netInBuffer = ByteBuffer.allocateDirect(netBufSize);
        if ( netOutBuffer == null ) netOutBuffer = ByteBuffer.allocateDirect(netBufSize);

        //selector pool for blocking operations
        this.pool = pool;

        //ensure that the application has a large enough read/write buffers
        //by doing this, we should not encounter any buffer overflow errors
        bufHandler.expand(bufHandler.getReadBuffer(), appBufSize);
        bufHandler.expand(bufHandler.getWriteBuffer(), appBufSize);
        reset();
    }

    public void reset(SSLEngine engine) throws IOException {
        this.sslEngine = engine;
        reset();
    }
    @Override
    public void reset() throws IOException {
        super.reset();
        netOutBuffer.position(0);
        netOutBuffer.limit(0);
        netInBuffer.position(0);
        netInBuffer.limit(0);
        handshakeComplete = false;
        closed = false;
        closing = false;
        //initiate handshake
        sslEngine.beginHandshake();
        handshakeStatus = sslEngine.getHandshakeStatus();
    }

    @Override
    public int getBufferSize() {
        int size = super.getBufferSize();
        size += netInBuffer!=null?netInBuffer.capacity():0;
        size += netOutBuffer!=null?netOutBuffer.capacity():0;
        return size;
    }


//===========================================================================================
//                  NIO SSL METHODS
//===========================================================================================
    /**
     * Flush the channel.
     *
     * @param block     Should a blocking write be used?
     * @param s
     * @param timeout
     * @return <code>true</code> if the network buffer has been flushed out and
     *         is empty else <code>false</code>
     * @throws IOException
     */
    @Override
    public boolean flush(boolean block, Selector s, long timeout)
            throws IOException {
        if (!block) {
            flush(netOutBuffer);
        } else {
            pool.write(netOutBuffer, this, s, timeout,block);
        }
        return !netOutBuffer.hasRemaining();
    }

    /**
     * Flushes the buffer to the network, non blocking
     * @param buf ByteBuffer
     * @return boolean true if the buffer has been emptied out, false otherwise
     * @throws IOException
     */
    protected boolean flush(ByteBuffer buf) throws IOException {
        int remaining = buf.remaining();
        if ( remaining > 0 ) {
            int written = sc.write(buf);
            return written >= remaining;
        }else {
            return true;
        }
    }

    /**
     * Performs SSL handshake, non blocking, but performs NEED_TASK on the same thread.<br>
     * Hence, you should never call this method using your Acceptor thread, as you would slow down
     * your system significantly.<br>
     * The return for this operation is 0 if the handshake is complete and a positive value if it is not complete.
     * In the event of a positive value coming back, reregister the selection key for the return values interestOps.
     * @param read boolean - true if the underlying channel is readable
     * @param write boolean - true if the underlying channel is writable
     * @return int - 0 if hand shake is complete, otherwise it returns a SelectionKey interestOps value
     * @throws IOException
     */
    @Override
    public int handshake(boolean read, boolean write) throws IOException {
        if ( handshakeComplete ) return 0; //we have done our initial handshake

        if (!flush(netOutBuffer)) return SelectionKey.OP_WRITE; //we still have data to write

        SSLEngineResult handshake = null;

        while (!handshakeComplete) {
            switch ( handshakeStatus ) {
                case NOT_HANDSHAKING: {
                    //should never happen
                    throw new IOException("NOT_HANDSHAKING during handshake");
                }
                case FINISHED: {
                    //we are complete if we have delivered the last package
                    handshakeComplete = !netOutBuffer.hasRemaining();
                    //return 0 if we are complete, otherwise we still have data to write
                    return handshakeComplete?0:SelectionKey.OP_WRITE;
                }
                case NEED_WRAP: {
                    //perform the wrap function
                    try {
                        handshake = handshakeWrap(write);
                    } catch (SSLException e) {
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString("channel.nio.ssl.wrapException"), e);
                        }
                        handshake = handshakeWrap(write);
                    }
                    if (handshake.getStatus() == Status.OK) {
                        if (handshakeStatus == HandshakeStatus.NEED_TASK)
                            handshakeStatus = tasks();
                    } else if (handshake.getStatus() == Status.CLOSED) {
                        flush(netOutBuffer);
                        return -1;
                    } else {
                        //wrap should always work with our buffers
                        throw new IOException("Unexpected status:" + handshake.getStatus() + " during handshake WRAP.");
                    }
                    if ( handshakeStatus != HandshakeStatus.NEED_UNWRAP || (!flush(netOutBuffer)) ) {
                        //should actually return OP_READ if we have NEED_UNWRAP
                        return SelectionKey.OP_WRITE;
                    }
                    //fall down to NEED_UNWRAP on the same call, will result in a
                    //BUFFER_UNDERFLOW if it needs data
                }
                //$FALL-THROUGH$
                case NEED_UNWRAP: {
                    //perform the unwrap function
                    handshake = handshakeUnwrap(read);
                    if ( handshake.getStatus() == Status.OK ) {
                        if (handshakeStatus == HandshakeStatus.NEED_TASK)
                            handshakeStatus = tasks();
                    } else if ( handshake.getStatus() == Status.BUFFER_UNDERFLOW ){
                        //read more data, reregister for OP_READ
                        return SelectionKey.OP_READ;
                    } else {
                        throw new IOException("Invalid handshake status:"+handshakeStatus+" during handshake UNWRAP.");
                    }//switch
                    break;
                }
                case NEED_TASK: {
                    handshakeStatus = tasks();
                    break;
                }
                default: throw new IllegalStateException("Invalid handshake status:"+handshakeStatus);
            }
        }
        // Handshake is complete if this point is reached
        return 0;
    }

    /**
     * Force a blocking handshake to take place for this key.
     * This requires that both network and application buffers have been emptied out prior to this call taking place, or a
     * IOException will be thrown.
     * @param timeout - timeout in milliseconds for each socket operation
     * @throws IOException - if an IO exception occurs or if application or network buffers contain data
     * @throws SocketTimeoutException - if a socket operation timed out
     */
    public void rehandshake(long timeout) throws IOException {
        //validate the network buffers are empty
        if (netInBuffer.position() > 0 && netInBuffer.position()<netInBuffer.limit()) throw new IOException("Network input buffer still contains data. Handshake will fail.");
        if (netOutBuffer.position() > 0 && netOutBuffer.position()<netOutBuffer.limit()) throw new IOException("Network output buffer still contains data. Handshake will fail.");
        if (getBufHandler().getReadBuffer().position()>0 && getBufHandler().getReadBuffer().position()<getBufHandler().getReadBuffer().limit()) throw new IOException("Application input buffer still contains data. Data would have been lost.");
        if (getBufHandler().getWriteBuffer().position()>0 && getBufHandler().getWriteBuffer().position()<getBufHandler().getWriteBuffer().limit()) throw new IOException("Application output buffer still contains data. Data would have been lost.");
        reset();
        boolean isReadable = true;
        boolean isWriteable = true;
        boolean handshaking = true;
        Selector selector = null;
        SelectionKey key = null;
        try {
            while (handshaking) {
                int hsStatus = this.handshake(isReadable, isWriteable);
                switch (hsStatus) {
                    case -1 : throw new EOFException("EOF during handshake.");
                    case  0 : handshaking = false; break;
                    default : {
                        long now = System.currentTimeMillis();
                        if (selector==null) {
                            synchronized (Selector.class) {
                                // Selector.open() isn't thread safe
                                // http://bugs.sun.com/view_bug.do?bug_id=6427854
                                // Affects 1.6.0_29, fixed in 1.7.0_01
                                selector = Selector.open();
                            }
                            key = getIOChannel().register(selector, hsStatus);
                        } else {
                            key.interestOps(hsStatus);
                        }
                        int keyCount = selector.select(timeout);
                        if (keyCount == 0 && ((System.currentTimeMillis()-now) >= timeout)) {
                            throw new SocketTimeoutException("Handshake operation timed out.");
                        }
                        isReadable = key.isReadable();
                        isWriteable = key.isWritable();
                    }
                }
            }
        } catch (IOException x) {
            throw x;
        } catch (Exception cx) {
            IOException x = new IOException(cx);
            throw x;
        } finally {
            if (key!=null) try {key.cancel();} catch (Exception ignore) {}
            if (selector!=null) try {selector.close();} catch (Exception ignore) {}
        }
    }



    /**
     * Executes all the tasks needed on the same thread.
     * @return HandshakeStatus
     */
    protected SSLEngineResult.HandshakeStatus tasks() {
        Runnable r = null;
        while ( (r = sslEngine.getDelegatedTask()) != null) {
            r.run();
        }
        return sslEngine.getHandshakeStatus();
    }

    /**
     * Performs the WRAP function
     * @param doWrite boolean
     * @return SSLEngineResult
     * @throws IOException
     */
    protected SSLEngineResult handshakeWrap(boolean doWrite) throws IOException {
        //this should never be called with a network buffer that contains data
        //so we can clear it here.
        netOutBuffer.clear();
        //perform the wrap
        SSLEngineResult result = sslEngine.wrap(bufHandler.getWriteBuffer(), netOutBuffer);
        //prepare the results to be written
        netOutBuffer.flip();
        //set the status
        handshakeStatus = result.getHandshakeStatus();
        //optimization, if we do have a writable channel, write it now
        if ( doWrite ) flush(netOutBuffer);
        return result;
    }

    /**
     * Perform handshake unwrap
     * @param doread boolean
     * @return SSLEngineResult
     * @throws IOException
     */
    protected SSLEngineResult handshakeUnwrap(boolean doread) throws IOException {

        if (netInBuffer.position() == netInBuffer.limit()) {
            //clear the buffer if we have emptied it out on data
            netInBuffer.clear();
        }
        if ( doread )  {
            //if we have data to read, read it
            int read = sc.read(netInBuffer);
            if (read == -1) throw new IOException("EOF encountered during handshake.");
        }
        SSLEngineResult result;
        boolean cont = false;
        //loop while we can perform pure SSLEngine data
        do {
            //prepare the buffer with the incoming data
            netInBuffer.flip();
            //call unwrap
            result = sslEngine.unwrap(netInBuffer, bufHandler.getReadBuffer());
            //compact the buffer, this is an optional method, wonder what would happen if we didn't
            netInBuffer.compact();
            //read in the status
            handshakeStatus = result.getHandshakeStatus();
            if ( result.getStatus() == SSLEngineResult.Status.OK &&
                 result.getHandshakeStatus() == HandshakeStatus.NEED_TASK ) {
                //execute tasks if we need to
                handshakeStatus = tasks();
            }
            //perform another unwrap?
            cont = result.getStatus() == SSLEngineResult.Status.OK &&
                   handshakeStatus == HandshakeStatus.NEED_UNWRAP;
        }while ( cont );
        return result;
    }

    /**
     * Sends a SSL close message, will not physically close the connection here.<br>
     * To close the connection, you could do something like
     * <pre><code>
     *   close();
     *   while (isOpen() && !myTimeoutFunction()) Thread.sleep(25);
     *   if ( isOpen() ) close(true); //forces a close if you timed out
     * </code></pre>
     * @throws IOException if an I/O error occurs
     * @throws IOException if there is data on the outgoing network buffer and we are unable to flush it
     * TODO Implement this java.io.Closeable method
     */
    @Override
    public void close() throws IOException {
        if (closing) return;
        closing = true;
        sslEngine.closeOutbound();

        if (!flush(netOutBuffer)) {
            throw new IOException("Remaining data in the network buffer, can't send SSL close message, force a close with close(true) instead");
        }
        //prep the buffer for the close message
        netOutBuffer.clear();
        //perform the close, since we called sslEngine.closeOutbound
        SSLEngineResult handshake = sslEngine.wrap(getEmptyBuf(), netOutBuffer);
        //we should be in a close state
        if (handshake.getStatus() != SSLEngineResult.Status.CLOSED) {
            throw new IOException("Invalid close state, will not send network data.");
        }
        //prepare the buffer for writing
        netOutBuffer.flip();
        //if there is data to be written
        flush(netOutBuffer);

        //is the channel closed?
        closed = (!netOutBuffer.hasRemaining() && (handshake.getHandshakeStatus() != HandshakeStatus.NEED_WRAP));
    }

    /**
     * Force a close, can throw an IOException
     * @param force boolean
     * @throws IOException
     */
    @Override
    public void close(boolean force) throws IOException {
        try {
            close();
        }finally {
            if ( force || closed ) {
                closed = true;
                sc.socket().close();
                sc.close();
            }
        }
    }

    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * @param dst The buffer into which bytes are to be transferred
     * @return The number of bytes read, possibly zero, or <tt>-1</tt> if the channel has reached end-of-stream
     * @throws IOException If some other I/O error occurs
     * @throws IllegalArgumentException if the destination buffer is different than bufHandler.getReadBuffer()
     * TODO Implement this java.nio.channels.ReadableByteChannel method
     */
    @Override
    public int read(ByteBuffer dst) throws IOException {
        //if we want to take advantage of the expand function, make sure we only use the ApplicationBufferHandler's buffers
        if ( dst != bufHandler.getReadBuffer() ) throw new IllegalArgumentException("You can only read using the application read buffer provided by the handler.");
        //are we in the middle of closing or closed?
        if ( closing || closed) return -1;
        //did we finish our handshake?
        if (!handshakeComplete) throw new IllegalStateException("Handshake incomplete, you must complete handshake before reading data.");

        //read from the network
        int netread = sc.read(netInBuffer);
        //did we reach EOF? if so send EOF up one layer.
        if (netread == -1) return -1;

        //the data read
        int read = 0;
        //the SSL engine result
        SSLEngineResult unwrap;
        do {
            //prepare the buffer
            netInBuffer.flip();
            //unwrap the data
            unwrap = sslEngine.unwrap(netInBuffer, dst);
            //compact the buffer
            netInBuffer.compact();

            if ( unwrap.getStatus()==Status.OK || unwrap.getStatus()==Status.BUFFER_UNDERFLOW ) {
                //we did receive some data, add it to our total
                read += unwrap.bytesProduced();
                //perform any tasks if needed
                if (unwrap.getHandshakeStatus() == HandshakeStatus.NEED_TASK) tasks();
                //if we need more network data, then bail out for now.
                if ( unwrap.getStatus() == Status.BUFFER_UNDERFLOW ) break;
            }else if ( unwrap.getStatus()==Status.BUFFER_OVERFLOW && read>0 ) {
                //buffer overflow can happen, if we have read data, then
                //empty out the dst buffer before we do another read
                break;
            }else {
                //here we should trap BUFFER_OVERFLOW and call expand on the buffer
                //for now, throw an exception, as we initialized the buffers
                //in the constructor
                throw new IOException("Unable to unwrap data, invalid status: " + unwrap.getStatus());
            }
        } while ( (netInBuffer.position() != 0)); //continue to unwrapping as long as the input buffer has stuff
        return (read);
    }

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     *
     * @param src The buffer from which bytes are to be retrieved
     * @return The number of bytes written, possibly zero
     * @throws IOException If some other I/O error occurs
     * TODO Implement this java.nio.channels.WritableByteChannel method
     */
    @Override
    public int write(ByteBuffer src) throws IOException {
        checkInterruptStatus();
        if ( src == this.netOutBuffer ) {
            //we can get here through a recursive call
            //by using the NioBlockingSelector
            int written = sc.write(src);
            return written;
        } else {
            //are we closing or closed?
            if ( closing || closed) throw new IOException("Channel is in closing state.");

            //the number of bytes written
            int written = 0;

            if (!flush(netOutBuffer)) {
                //we haven't emptied out the buffer yet
                return written;
            }

            /*
             * The data buffer is empty, we can reuse the entire buffer.
             */
            netOutBuffer.clear();

            SSLEngineResult result = sslEngine.wrap(src, netOutBuffer);
            written = result.bytesConsumed();
            netOutBuffer.flip();

            if (result.getStatus() == Status.OK) {
                if (result.getHandshakeStatus() == HandshakeStatus.NEED_TASK) tasks();
            } else {
                throw new IOException("Unable to wrap data, invalid engine state: " +result.getStatus());
            }

            //force a flush
            flush(netOutBuffer);

            return written;
        }
    }

    @Override
    public int getOutboundRemaining() {
        return netOutBuffer.remaining();
    }

    @Override
    public boolean flushOutbound() throws IOException {
        int remaining = netOutBuffer.remaining();
        flush(netOutBuffer);
        int remaining2= netOutBuffer.remaining();
        return remaining2 < remaining;
    }


    /**
     * Callback interface to be able to expand buffers
     * when buffer overflow exceptions happen
     */
    public static interface ApplicationBufferHandler {
        public ByteBuffer expand(ByteBuffer buffer, int remaining);
        public ByteBuffer getReadBuffer();
        public ByteBuffer getWriteBuffer();
    }

    @Override
    public ApplicationBufferHandler getBufHandler() {
        return bufHandler;
    }

    @Override
    public boolean isHandshakeComplete() {
        return handshakeComplete;
    }

    @Override
    public boolean isClosing() {
        return closing;
    }

    public SSLEngine getSslEngine() {
        return sslEngine;
    }

    public ByteBuffer getEmptyBuf() {
        return emptyBuf;
    }

    public void setBufHandler(ApplicationBufferHandler bufHandler) {
        this.bufHandler = bufHandler;
    }
}
