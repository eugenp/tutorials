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
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.ChannelReceiver;
import org.apache.catalina.tribes.RemoteProcessException;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.io.BufferPool;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.ListenCallback;
import org.apache.catalina.tribes.io.ObjectReader;
import org.apache.catalina.tribes.transport.AbstractRxTask;
import org.apache.catalina.tribes.transport.Constants;
import org.apache.catalina.tribes.util.Logs;

/**
 * A worker thread class which can drain channels and echo-back the input. Each
 * instance is constructed with a reference to the owning thread pool object.
 * When started, the thread loops forever waiting to be awakened to service the
 * channel associated with a SelectionKey object. The worker is tasked by
 * calling its serviceChannel() method with a SelectionKey object. The
 * serviceChannel() method stores the key reference in the thread object then
 * calls notify() to wake it up. When the channel has been drained, the worker
 * thread returns itself to its parent pool.
 *
 * @author Filip Hanik
 */
public class NioReplicationTask extends AbstractRxTask {

    private static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory.getLog( NioReplicationTask.class );

    private ByteBuffer buffer = null;
    private SelectionKey key;
    private int rxBufSize;
    private NioReceiver receiver;
    public NioReplicationTask (ListenCallback callback, NioReceiver receiver)
    {
        super(callback);
        this.receiver = receiver;
    }

    // loop forever waiting for work to do
    @Override
    public synchronized void run() {
        if ( buffer == null ) {
            int size = getRxBufSize();
            if (key.channel() instanceof DatagramChannel) {
                size = ChannelReceiver.MAX_UDP_SIZE;
            }
            if ( (getOptions() & OPTION_DIRECT_BUFFER) == OPTION_DIRECT_BUFFER) {
                buffer = ByteBuffer.allocateDirect(size);
            } else {
                buffer = ByteBuffer.allocate(size);
            }
        } else {
            buffer.clear();
        }
        if (key == null) {
            return; // just in case
        }
        if ( log.isTraceEnabled() )
            log.trace("Servicing key:"+key);

        try {
            ObjectReader reader = (ObjectReader)key.attachment();
            if ( reader == null ) {
                if ( log.isTraceEnabled() )
                    log.trace("No object reader, cancelling:"+key);
                cancelKey(key);
            } else {
                if ( log.isTraceEnabled() )
                    log.trace("Draining channel:"+key);

                drainChannel(key, reader);
            }
        } catch (Exception e) {
            //this is common, since the sockets on the other
            //end expire after a certain time.
            if ( e instanceof CancelledKeyException ) {
                //do nothing
            } else if ( e instanceof IOException ) {
                //dont spew out stack traces for IO exceptions unless debug is enabled.
                if (log.isDebugEnabled()) log.debug ("IOException in replication worker, unable to drain channel. Probable cause: Keep alive socket closed["+e.getMessage()+"].", e);
                else log.warn ("IOException in replication worker, unable to drain channel. Probable cause: Keep alive socket closed["+e.getMessage()+"].");
            } else if ( log.isErrorEnabled() ) {
                //this is a real error, log it.
                log.error("Exception caught in TcpReplicationThread.drainChannel.",e);
            }
            cancelKey(key);
        } finally {

        }
        key = null;
        // done, ready for more, return to pool
        getTaskPool().returnWorker (this);
    }

    /**
     * Called to initiate a unit of work by this worker thread
     * on the provided SelectionKey object.  This method is
     * synchronized, as is the run() method, so only one key
     * can be serviced at a given time.
     * Before waking the worker thread, and before returning
     * to the main selection loop, this key's interest set is
     * updated to remove OP_READ.  This will cause the selector
     * to ignore read-readiness for this channel while the
     * worker thread is servicing it.
     */
    public synchronized void serviceChannel (SelectionKey key) {
        if ( log.isTraceEnabled() ) log.trace("About to service key:"+key);
        ObjectReader reader = (ObjectReader)key.attachment();
        if ( reader != null ) reader.setLastAccess(System.currentTimeMillis());
        this.key = key;
        key.interestOps (key.interestOps() & (~SelectionKey.OP_READ));
        key.interestOps (key.interestOps() & (~SelectionKey.OP_WRITE));
    }

    /**
     * The actual code which drains the channel associated with
     * the given key.  This method assumes the key has been
     * modified prior to invocation to turn off selection
     * interest in OP_READ.  When this method completes it
     * re-enables OP_READ and calls wakeup() on the selector
     * so the selector will resume watching this channel.
     */
    protected void drainChannel (final SelectionKey key, ObjectReader reader) throws Exception {
        reader.setLastAccess(System.currentTimeMillis());
        reader.access();
        ReadableByteChannel channel = (ReadableByteChannel) key.channel();
        int count=-1;
        buffer.clear();         // make buffer empty
        SocketAddress saddr = null;

        if (channel instanceof SocketChannel) {
            // loop while data available, channel is non-blocking
            while ((count = channel.read (buffer)) > 0) {
                buffer.flip();      // make buffer readable
                if ( buffer.hasArray() )
                    reader.append(buffer.array(),0,count,false);
                else
                    reader.append(buffer,count,false);
                buffer.clear();     // make buffer empty
                //do we have at least one package?
                if ( reader.hasPackage() ) break;
            }
        } else if (channel instanceof DatagramChannel) {
            DatagramChannel dchannel = (DatagramChannel)channel;
            saddr = dchannel.receive(buffer);
            buffer.flip();      // make buffer readable
            if ( buffer.hasArray() )
                reader.append(buffer.array(),0,buffer.limit()-buffer.position(),false);
            else
                reader.append(buffer,buffer.limit()-buffer.position(),false);
            buffer.clear();     // make buffer empty
            //did we get a package
            count = reader.hasPackage()?1:-1;
        }

        int pkgcnt = reader.count();

        if (count < 0 && pkgcnt == 0 ) {
            //end of stream, and no more packages to process
            remoteEof(key);
            return;
        }

        ChannelMessage[] msgs = pkgcnt == 0? ChannelData.EMPTY_DATA_ARRAY : reader.execute();

        registerForRead(key,reader);//register to read new data, before we send it off to avoid dead locks

        for ( int i=0; i<msgs.length; i++ ) {
            /**
             * Use send ack here if you want to ack the request to the remote
             * server before completing the request
             * This is considered an asynchronous request
             */
            if (ChannelData.sendAckAsync(msgs[i].getOptions())) sendAck(key,(WritableByteChannel)channel,Constants.ACK_COMMAND,saddr);
            try {
                if ( Logs.MESSAGES.isTraceEnabled() ) {
                    try {
                        Logs.MESSAGES.trace("NioReplicationThread - Received msg:" + new UniqueId(msgs[i].getUniqueId()) + " at " + new java.sql.Timestamp(System.currentTimeMillis()));
                    }catch ( Throwable t ) {}
                }
                //process the message
                getCallback().messageDataReceived(msgs[i]);
                /**
                 * Use send ack here if you want the request to complete on this
                 * server before sending the ack to the remote server
                 * This is considered a synchronized request
                 */
                if (ChannelData.sendAckSync(msgs[i].getOptions())) sendAck(key,(WritableByteChannel)channel,Constants.ACK_COMMAND,saddr);
            }catch ( RemoteProcessException e ) {
                if ( log.isDebugEnabled() ) log.error("Processing of cluster message failed.",e);
                if (ChannelData.sendAckSync(msgs[i].getOptions())) sendAck(key,(WritableByteChannel)channel,Constants.FAIL_ACK_COMMAND,saddr);
            }catch ( Exception e ) {
                log.error("Processing of cluster message failed.",e);
                if (ChannelData.sendAckSync(msgs[i].getOptions())) sendAck(key,(WritableByteChannel)channel,Constants.FAIL_ACK_COMMAND,saddr);
            }
            if ( getUseBufferPool() ) {
                BufferPool.getBufferPool().returnBuffer(msgs[i].getMessage());
                msgs[i].setMessage(null);
            }
        }

        if (count < 0) {
            remoteEof(key);
            return;
        }
    }

    private void remoteEof(SelectionKey key) {
        // close channel on EOF, invalidates the key
        if ( log.isDebugEnabled() ) log.debug("Channel closed on the remote end, disconnecting");
        cancelKey(key);
    }

    protected void registerForRead(final SelectionKey key, ObjectReader reader) {
        if ( log.isTraceEnabled() )
            log.trace("Adding key for read event:"+key);
        reader.finish();
        //register our OP_READ interest
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    if (key.isValid()) {
                        // cycle the selector so this key is active again
                        key.selector().wakeup();
                        // resume interest in OP_READ, OP_WRITE
                        int resumeOps = key.interestOps() | SelectionKey.OP_READ;
                        key.interestOps(resumeOps);
                        if ( log.isTraceEnabled() )
                            log.trace("Registering key for read:"+key);
                    }
                } catch (CancelledKeyException ckx ) {
                    NioReceiver.cancelledKey(key);
                    if ( log.isTraceEnabled() )
                        log.trace("CKX Cancelling key:"+key);

                } catch (Exception x) {
                    log.error("Error registering key for read:"+key,x);
                }
            }
        };
        receiver.addEvent(r);
    }

    private void cancelKey(final SelectionKey key) {
        if ( log.isTraceEnabled() )
            log.trace("Adding key for cancel event:"+key);

        ObjectReader reader = (ObjectReader)key.attachment();
        if ( reader != null ) {
            reader.setCancelled(true);
            reader.finish();
        }
        Runnable cx = new Runnable() {
            @Override
            public void run() {
                if ( log.isTraceEnabled() )
                    log.trace("Cancelling key:"+key);

                NioReceiver.cancelledKey(key);
            }
        };
        receiver.addEvent(cx);
    }


    /**
     * send a reply-acknowledgement (6,2,3), sends it doing a busy write, the ACK is so small
     * that it should always go to the buffer
     * @param key
     * @param channel
     */
    protected void sendAck(SelectionKey key, WritableByteChannel channel, byte[] command, SocketAddress udpaddr) {
        try {

            ByteBuffer buf = ByteBuffer.wrap(command);
            int total = 0;
            if (channel instanceof DatagramChannel) {
                DatagramChannel dchannel = (DatagramChannel)channel;
                //were using a shared channel, document says its thread safe
                //TODO check optimization, one channel per thread?
                while ( total < command.length ) {
                    total += dchannel.send(buf, udpaddr);
                }
            } else {
                while ( total < command.length ) {
                    total += channel.write(buf);
                }
            }
            if (log.isTraceEnabled()) {
                log.trace("ACK sent to " +
                        ( (channel instanceof SocketChannel) ?
                          ((SocketChannel)channel).socket().getInetAddress() :
                          ((DatagramChannel)channel).socket().getInetAddress()));
            }
        } catch ( java.io.IOException x ) {
            log.warn("Unable to send ACK back through channel, channel disconnected?: "+x.getMessage());
        }
    }

    public void setRxBufSize(int rxBufSize) {
        this.rxBufSize = rxBufSize;
    }

    public int getRxBufSize() {
        return rxBufSize;
    }
}
