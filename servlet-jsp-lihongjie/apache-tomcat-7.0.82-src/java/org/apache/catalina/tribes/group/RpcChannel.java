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
package org.apache.catalina.tribes.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ErrorHandler;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.util.UUIDGenerator;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * A channel to handle RPC messaging
 * @author Filip Hanik
 */
public class RpcChannel implements ChannelListener {
    private static final Log log = LogFactory.getLog(RpcChannel.class);
    
    public static final int FIRST_REPLY = 1;
    public static final int MAJORITY_REPLY = 2;
    public static final int ALL_REPLY = 3;
    public static final int NO_REPLY = 4;
    
    private Channel channel;
    private RpcCallback callback;
    private byte[] rpcId;
    private int replyMessageOptions = 0;

    private ConcurrentMap<RpcCollectorKey, RpcCollector> responseMap = new ConcurrentHashMap<RpcCollectorKey, RpcCollector>();

    /**
     * Create an RPC channel. You can have several RPC channels attached to a group
     * all separated out by the uniqueness
     * @param rpcId - the unique Id for this RPC group
     * @param channel Channel
     * @param callback RpcCallback
     */
    public RpcChannel(byte[] rpcId, Channel channel, RpcCallback callback) {
        this.channel = channel;
        this.callback = callback;
        this.rpcId = rpcId;
        channel.addChannelListener(this);
    }
    
    
    /**
     * Send a message and wait for the response.
     * @param destination Member[] - the destination for the message, and the members you request a reply from
     * @param message Serializable - the message you are sending out
     * @param rpcOptions int - FIRST_REPLY, MAJORITY_REPLY or ALL_REPLY
     * @param channelOptions channel sender options
     * @param timeout long - timeout in milliseconds, if no reply is received within this time null is returned
     * @return Response[] - an array of response objects.
     * @throws ChannelException
     */
    public Response[] send(Member[] destination, 
                           Serializable message,
                           int rpcOptions, 
                           int channelOptions,
                           long timeout) throws ChannelException {
        
        if ( destination==null || destination.length == 0 ) return new Response[0];
        
        //avoid dead lock
        int sendOptions =
            channelOptions & ~Channel.SEND_OPTIONS_SYNCHRONIZED_ACK;
        
        RpcCollectorKey key = new RpcCollectorKey(UUIDGenerator.randomUUID(false));
        RpcCollector collector = new RpcCollector(key,rpcOptions,destination.length);
        try {
            synchronized (collector) {
                if ( rpcOptions != NO_REPLY ) responseMap.put(key, collector);
                RpcMessage rmsg = new RpcMessage(rpcId, key.id, message);
                channel.send(destination, rmsg, sendOptions);
                if ( rpcOptions != NO_REPLY ) collector.wait(timeout);
            }
        } catch ( InterruptedException ix ) {
            Thread.currentThread().interrupt();
        } finally {
            responseMap.remove(key);
        }
        return collector.getResponses();
    }
    
    @Override
    public void messageReceived(Serializable msg, Member sender) {
        RpcMessage rmsg = (RpcMessage)msg;
        RpcCollectorKey key = new RpcCollectorKey(rmsg.uuid);
        if ( rmsg.reply ) {
            RpcCollector collector = responseMap.get(key);
            if (collector == null) {
                if (!(rmsg instanceof RpcMessage.NoRpcChannelReply))
                    callback.leftOver(rmsg.message, sender);
            } else {
                synchronized (collector) {
                    //make sure it hasn't been removed
                    if ( responseMap.containsKey(key) ) {
                        if ( (rmsg instanceof RpcMessage.NoRpcChannelReply) ) 
                            collector.destcnt--;
                        else 
                            collector.addResponse(rmsg.message, sender);
                        if (collector.isComplete()) collector.notifyAll();
                    } else {
                        if (! (rmsg instanceof RpcMessage.NoRpcChannelReply) ) 
                            callback.leftOver(rmsg.message, sender);
                    }
                }//synchronized
            }//end if
        } else {
            boolean finished = false;
            final ExtendedRpcCallback excallback = (callback instanceof ExtendedRpcCallback)?((ExtendedRpcCallback)callback) : null;
            boolean asyncReply = ((replyMessageOptions & Channel.SEND_OPTIONS_ASYNCHRONOUS) == Channel.SEND_OPTIONS_ASYNCHRONOUS);
            Serializable reply = callback.replyRequest(rmsg.message,sender);
            ErrorHandler handler = null;
            final Serializable request = msg;
            final Serializable response = reply;
            final Member fsender = sender;
            if (excallback!=null && asyncReply) {
                handler = new ErrorHandler() {
                    @Override
                    public void handleError(ChannelException x, UniqueId id) {
                        excallback.replyFailed(request, response, fsender, x);
                    }
                    @Override
                    public void handleCompletion(UniqueId id) {
                        excallback.replySucceeded(request, response, fsender);
                    }
                };
            }
            rmsg.reply = true;
            rmsg.message = reply;
            try {
                if (handler!=null) {
                    channel.send(new Member[] {sender}, rmsg,replyMessageOptions & ~Channel.SEND_OPTIONS_SYNCHRONIZED_ACK, handler);
                } else {
                    channel.send(new Member[] {sender}, rmsg,replyMessageOptions & ~Channel.SEND_OPTIONS_SYNCHRONIZED_ACK);
                }
                finished = true;
            } catch ( Exception x )  {
                if (excallback != null && !asyncReply) {
                    excallback.replyFailed(rmsg.message, reply, sender, x);
                } else {
                    log.error("Unable to send back reply in RpcChannel.",x);
                }
            }
            if (finished && excallback != null && !asyncReply) {
                excallback.replySucceeded(rmsg.message, reply, sender);
            }
        }//end if
    }
    
    public void breakdown() {
        channel.removeChannelListener(this);
    }
    
    @Override
    public void finalize() throws Throwable {
        breakdown();
        super.finalize();
    }
    
    @Override
    public boolean accept(Serializable msg, Member sender) {
        if ( msg instanceof RpcMessage ) {
            RpcMessage rmsg = (RpcMessage)msg;
            return Arrays.equals(rmsg.rpcId,rpcId);
        } else return false;
    }
    
    public Channel getChannel() {
        return channel;
    }

    public RpcCallback getCallback() {
        return callback;
    }

    public byte[] getRpcId() {
        return rpcId;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setCallback(RpcCallback callback) {
        this.callback = callback;
    }

    public void setRpcId(byte[] rpcId) {
        this.rpcId = rpcId;
    }

    public int getReplyMessageOptions() {
        return replyMessageOptions;
    }

    public void setReplyMessageOptions(int replyMessageOptions) {
        this.replyMessageOptions = replyMessageOptions;
    }
        
    /**
     * 
     * Class that holds all response.
     * @author not attributable
     * @version 1.0
     */
    public static class RpcCollector {
        public ArrayList<Response> responses = new ArrayList<Response>(); 
        public RpcCollectorKey key;
        public int options;
        public int destcnt;
        /**
         * @deprecated  Unused - will be removed in Tomcat 8.0.x
         */
        @Deprecated
        public long timeout;

        /**
         * @deprecated  Use {@link
         *              RpcChannel.RpcCollector#RpcChannel.RpcCollector(
         *              RpcChannel.RpcCollectorKey, int, int)}
         */
        @Deprecated
        public RpcCollector(RpcCollectorKey key, int options, int destcnt,
                long timeout) {
            this.key = key;
            this.options = options;
            this.destcnt = destcnt;
            this.timeout = timeout;
        }
        
        public RpcCollector(RpcCollectorKey key, int options, int destcnt) {
            this(key, options, destcnt, 0);
        }

        public void addResponse(Serializable message, Member sender) {
            Response resp = new Response(sender,message);
            responses.add(resp);
        }
        
        public boolean isComplete() {
            if ( destcnt <= 0 ) return true;
            switch (options) {
                case ALL_REPLY:
                    return destcnt == responses.size();
                case MAJORITY_REPLY:
                {
                    float perc = ((float)responses.size()) / ((float)destcnt);
                    return perc >= 0.50f;
                }
                case FIRST_REPLY:
                    return responses.size()>0;
                default:
                    return false;
            }
        }
        
        @Override
        public int hashCode() {
            return key.hashCode();
        }
        
        @Override
        public boolean equals(Object o) {
            if ( o instanceof RpcCollector ) {
                RpcCollector r = (RpcCollector)o;
                return r.key.equals(this.key);
            } else return false;
        }
        
        public Response[] getResponses() {
            return responses.toArray(new Response[responses.size()]);
        }
    }
    
    public static class RpcCollectorKey {
        byte[] id;
        public RpcCollectorKey(byte[] id) {
            this.id = id;
        }
        
        @Override
        public int hashCode() {
            return id[0]+id[1]+id[2]+id[3];
        }

        @Override
        public boolean equals(Object o) {
            if ( o instanceof RpcCollectorKey ) {
                RpcCollectorKey r = (RpcCollectorKey)o;
                return Arrays.equals(id,r.id);
            } else return false;
        }
        
    }
    
    /**
     * @deprecated  Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected static String bToS(byte[] data) {
        StringBuilder buf = new StringBuilder(4*16);
        buf.append("{");
        for (int i=0; data!=null && i<data.length; i++ ) buf.append(String.valueOf(data[i])).append(" ");
        buf.append("}");
        return buf.toString();
    }


}
