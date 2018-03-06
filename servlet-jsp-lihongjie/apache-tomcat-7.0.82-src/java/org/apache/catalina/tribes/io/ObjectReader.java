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
package org.apache.catalina.tribes.io;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.catalina.tribes.ChannelMessage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;



/**
 * The object reader object is an object used in conjunction with
 * java.nio TCP messages. This object stores the message bytes in a
 * <code>XByteBuffer</code> until a full package has been received.
 * This object uses an XByteBuffer which is an extendable object buffer that also allows
 * for message encoding and decoding.
 *
 * @author Filip Hanik
 */
public class ObjectReader {

    private static final Log log = LogFactory.getLog(ObjectReader.class);

    private XByteBuffer buffer;

    protected long lastAccess = System.currentTimeMillis();

    protected boolean accessed = false;
    private boolean cancelled;

    public ObjectReader(int packetSize) {
        this.buffer = new XByteBuffer(packetSize, true);
    }
    /**
     * Creates an <code>ObjectReader</code> for a TCP NIO socket channel
     * @param channel - the channel to be read.
     */
    public ObjectReader(SocketChannel channel) {
        this(channel.socket());
    }

    /**
     * Creates an <code>ObjectReader</code> for a TCP socket
     * @param socket Socket
     */
    public ObjectReader(Socket socket) {
        try{
            this.buffer = new XByteBuffer(socket.getReceiveBufferSize(), true);
        }catch ( IOException x ) {
            //unable to get buffer size
            log.warn("Unable to retrieve the socket receiver buffer size, setting to default 43800 bytes.");
            this.buffer = new XByteBuffer(43800,true);
        }
    }

    public synchronized void access() {
        this.accessed = true;
        this.lastAccess = System.currentTimeMillis();
    }

    public synchronized void finish() {
        this.accessed = false;
        this.lastAccess = System.currentTimeMillis();
    }

    public boolean isAccessed() {
        return this.accessed;
    }

    /**
     * Append new bytes to buffer.
     * @see XByteBuffer#countPackages()
     * @param data new transfer buffer
     * @param len length in buffer
     * @param count whether to return the count
     * @return number of messages that was sent to callback (or -1 if count == false)
     * @throws java.io.IOException
     */
    public int append(ByteBuffer data, int len, boolean count) throws java.io.IOException {
       buffer.append(data,len);
       int pkgCnt = -1;
       if ( count ) pkgCnt = buffer.countPackages();
       return pkgCnt;
   }

     public int append(byte[] data,int off,int len, boolean count) throws java.io.IOException {
        buffer.append(data,off,len);
        int pkgCnt = -1;
        if ( count ) pkgCnt = buffer.countPackages();
        return pkgCnt;
    }

    /**
     * Send buffer to cluster listener (callback).
     * Is message complete receiver send message to callback?
     *
     * @see org.apache.catalina.tribes.transport.ReceiverBase#messageDataReceived(ChannelMessage)
     * @see XByteBuffer#doesPackageExist()
     * @see XByteBuffer#extractPackage(boolean)
     *
     * @return number of received packages/messages
     * @throws java.io.IOException
     */
    public ChannelMessage[] execute() throws java.io.IOException {
        int pkgCnt = buffer.countPackages();
        ChannelMessage[] result = new ChannelMessage[pkgCnt];
        for (int i=0; i<pkgCnt; i++)  {
            ChannelMessage data = buffer.extractPackage(true);
            result[i] = data;
        }
        return result;
    }

    public int bufferSize() {
        return buffer.getLength();
    }


    public boolean hasPackage() {
        return buffer.countPackages(true)>0;
    }
    /**
     * Returns the number of packages that the reader has read
     * @return int
     */
    public int count() {
        return buffer.countPackages();
    }

    public void close() {
        this.buffer = null;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
