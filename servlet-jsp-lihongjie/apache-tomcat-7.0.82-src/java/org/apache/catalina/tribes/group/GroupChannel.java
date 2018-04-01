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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.catalina.tribes.ByteMessage;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelInterceptor;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.ChannelReceiver;
import org.apache.catalina.tribes.ChannelSender;
import org.apache.catalina.tribes.ErrorHandler;
import org.apache.catalina.tribes.Heartbeat;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.MembershipService;
import org.apache.catalina.tribes.RemoteProcessException;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.group.interceptors.MessageDispatchInterceptor;
import org.apache.catalina.tribes.io.BufferPool;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.catalina.tribes.util.Logs;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * The default implementation of a Channel.<br>
 * The GroupChannel manages the replication channel. It coordinates
 * message being sent and received with membership announcements.
 * The channel has an chain of interceptors that can modify the message or perform other logic.<br>
 * It manages a complete group, both membership and replication.
 * @author Filip Hanik
 */
public class GroupChannel extends ChannelInterceptorBase implements ManagedChannel {
    private static final Log log = LogFactory.getLog(GroupChannel.class);

    /**
     * Flag to determine if the channel manages its own heartbeat
     * If set to true, the channel will start a local thread for the heart beat.
     */
    protected boolean heartbeat = true;
    /**
     * If <code>heartbeat == true</code> then how often do we want this
     * heartbeat to run. default is one minute
     */
    protected long heartbeatSleeptime = 5*1000;//every 5 seconds

    /**
     * Internal heartbeat thread
     */
    protected HeartbeatThread hbthread = null;

    /**
     * The  <code>ChannelCoordinator</code> coordinates the bottom layer components:<br>
     * - MembershipService<br>
     * - ChannelSender <br>
     * - ChannelReceiver<br>
     */
    protected ChannelCoordinator coordinator = new ChannelCoordinator();

    /**
     * The first interceptor in the interceptor stack.
     * The interceptors are chained in a linked list, so we only need a reference to the
     * first one
     */
    protected ChannelInterceptor interceptors = null;

    /**
     * A list of membership listeners that subscribe to membership announcements
     */
    protected List<Object> membershipListeners = new CopyOnWriteArrayList<Object>();

    /**
     * A list of channel listeners that subscribe to incoming messages
     */
    protected List<Object> channelListeners = new CopyOnWriteArrayList<Object>();

    /**
     * If set to true, the GroupChannel will check to make sure that
     */
    protected boolean optionCheck = false;

    /**
     * the name of this channel.
     */
    protected String name = null;

    /**
     * Creates a GroupChannel. This constructor will also
     * add the first interceptor in the GroupChannel.<br>
     * The first interceptor is always the channel itself.
     */
    public GroupChannel() {
        addInterceptor(this);
    }


    /**
     * Adds an interceptor to the stack for message processing<br>
     * Interceptors are ordered in the way they are added.<br>
     * <code>channel.addInterceptor(A);</code><br>
     * <code>channel.addInterceptor(C);</code><br>
     * <code>channel.addInterceptor(B);</code><br>
     * Will result in a interceptor stack like this:<br>
     * <code>A -> C -> B</code><br>
     * The complete stack will look like this:<br>
     * <code>Channel -> A -> C -> B -> ChannelCoordinator</code><br>
     * @param interceptor ChannelInterceptorBase
     */
    @Override
    public void addInterceptor(ChannelInterceptor interceptor) {
        if ( interceptors == null ) {
            interceptors = interceptor;
            interceptors.setNext(coordinator);
            interceptors.setPrevious(null);
            coordinator.setPrevious(interceptors);
        } else {
            ChannelInterceptor last = interceptors;
            while ( last.getNext() != coordinator ) {
                last = last.getNext();
            }
            last.setNext(interceptor);
            interceptor.setNext(coordinator);
            interceptor.setPrevious(last);
            coordinator.setPrevious(interceptor);
        }
    }

    /**
     * Sends a heartbeat through the interceptor stack.<br>
     * Invoke this method from the application on a periodic basis if
     * you have turned off internal heartbeats <code>channel.setHeartbeat(false)</code>
     */
    @Override
    public void heartbeat() {
        super.heartbeat();
        Iterator<Object> i = membershipListeners.iterator();
        while ( i.hasNext() ) {
            Object o = i.next();
            if ( o instanceof Heartbeat ) ((Heartbeat)o).heartbeat();
        }
        i = channelListeners.iterator();
        while ( i.hasNext() ) {
            Object o = i.next();
            if ( o instanceof Heartbeat ) ((Heartbeat)o).heartbeat();
        }

    }


    /**
     * Send a message to the destinations specified
     * @param destination Member[] - destination.length > 0
     * @param msg Serializable - the message to send
     * @param options int - sender options, options can trigger guarantee levels and different interceptors to
     * react to the message see class documentation for the <code>Channel</code> object.<br>
     * @return UniqueId - the unique Id that was assigned to this message
     * @throws ChannelException - if an error occurs processing the message
     * @see org.apache.catalina.tribes.Channel
     */
    @Override
    public UniqueId send(Member[] destination, Serializable msg, int options) throws ChannelException {
        return send(destination,msg,options,null);
    }

    /**
     *
     * @param destination Member[] - destination.length > 0
     * @param msg Serializable - the message to send
     * @param options int - sender options, options can trigger guarantee levels and different interceptors to
     * react to the message see class documentation for the <code>Channel</code> object.<br>
     * @param handler - callback object for error handling and completion notification, used when a message is
     * sent asynchronously using the <code>Channel.SEND_OPTIONS_ASYNCHRONOUS</code> flag enabled.
     * @return UniqueId - the unique Id that was assigned to this message
     * @throws ChannelException - if an error occurs processing the message
     * @see org.apache.catalina.tribes.Channel
     */
    @Override
    public UniqueId send(Member[] destination, Serializable msg, int options, ErrorHandler handler) throws ChannelException {
        if ( msg == null ) throw new ChannelException("Cant send a NULL message");
        XByteBuffer buffer = null;
        try {
            if ( destination == null || destination.length == 0) throw new ChannelException("No destination given");
            ChannelData data = new ChannelData(true);//generates a unique Id
            data.setAddress(getLocalMember(false));
            data.setTimestamp(System.currentTimeMillis());
            byte[] b = null;
            if ( msg instanceof ByteMessage ){
                b = ((ByteMessage)msg).getMessage();
                options = options | SEND_OPTIONS_BYTE_MESSAGE;
            } else {
                b = XByteBuffer.serialize(msg);
                options = options & (~SEND_OPTIONS_BYTE_MESSAGE);
            }
            data.setOptions(options);
            //XByteBuffer buffer = new XByteBuffer(b.length+128,false);
            buffer = BufferPool.getBufferPool().getBuffer(b.length+128, false);
            buffer.append(b,0,b.length);
            data.setMessage(buffer);
            InterceptorPayload payload = null;
            if ( handler != null ) {
                payload = new InterceptorPayload();
                payload.setErrorHandler(handler);
            }
            getFirstInterceptor().sendMessage(destination, data, payload);
            if ( Logs.MESSAGES.isTraceEnabled() ) {
                Logs.MESSAGES.trace("GroupChannel - Sent msg:" + new UniqueId(data.getUniqueId()) + " at " +new java.sql.Timestamp(System.currentTimeMillis())+ " to "+Arrays.toNameString(destination));
                Logs.MESSAGES.trace("GroupChannel - Send Message:" + new UniqueId(data.getUniqueId()) + " is " +msg);
            }

            return new UniqueId(data.getUniqueId());
        }catch ( Exception x ) {
            if ( x instanceof ChannelException ) throw (ChannelException)x;
            throw new ChannelException(x);
        } finally {
            if ( buffer != null ) BufferPool.getBufferPool().returnBuffer(buffer);
        }
    }


    /**
     * Callback from the interceptor stack. <br>
     * When a message is received from a remote node, this method will be invoked by
     * the previous interceptor.<br>
     * This method can also be used to send a message to other components within the same application,
     * but its an extreme case, and you're probably better off doing that logic between the applications itself.
     * @param msg ChannelMessage
     */
    @Override
    public void messageReceived(ChannelMessage msg) {
        if ( msg == null ) return;
        try {
            if ( Logs.MESSAGES.isTraceEnabled() ) {
                Logs.MESSAGES.trace("GroupChannel - Received msg:" + new UniqueId(msg.getUniqueId()) + " at " +new java.sql.Timestamp(System.currentTimeMillis())+ " from "+msg.getAddress().getName());
            }

            Serializable fwd = null;
            if ( (msg.getOptions() & SEND_OPTIONS_BYTE_MESSAGE) == SEND_OPTIONS_BYTE_MESSAGE ) {
                fwd = new ByteMessage(msg.getMessage().getBytes());
            } else {
                try {
                    fwd = XByteBuffer.deserialize(msg.getMessage().getBytesDirect(), 0, msg.getMessage().getLength());
                }catch (Exception sx) {
                    log.error("Unable to deserialize message:"+msg,sx);
                    return;
                }
            }
            if ( Logs.MESSAGES.isTraceEnabled() ) {
                Logs.MESSAGES.trace("GroupChannel - Receive Message:" + new UniqueId(msg.getUniqueId()) + " is " +fwd);
            }

            //get the actual member with the correct alive time
            Member source = msg.getAddress();
            boolean rx = false;
            boolean delivered = false;
            for ( int i=0; i<channelListeners.size(); i++ ) {
                ChannelListener channelListener = (ChannelListener)channelListeners.get(i);
                if (channelListener != null && channelListener.accept(fwd, source)) {
                    channelListener.messageReceived(fwd, source);
                    delivered = true;
                    //if the message was accepted by an RPC channel, that channel
                    //is responsible for returning the reply, otherwise we send an absence reply
                    if ( channelListener instanceof RpcChannel ) rx = true;
                }
            }//for
            if ((!rx) && (fwd instanceof RpcMessage)) {
                //if we have a message that requires a response,
                //but none was given, send back an immediate one
                sendNoRpcChannelReply((RpcMessage)fwd,source);
            }
            if ( Logs.MESSAGES.isTraceEnabled() ) {
                Logs.MESSAGES.trace("GroupChannel delivered["+delivered+"] id:"+new UniqueId(msg.getUniqueId()));
            }

        } catch ( Exception x ) {
            //this could be the channel listener throwing an exception, we should log it 
            //as a warning.
            if ( log.isWarnEnabled() ) log.warn("Error receiving message:",x);
            throw new RemoteProcessException("Exception:"+x.getMessage(),x);
        }
    }

    /**
     * Sends a <code>NoRpcChannelReply</code> message to a member<br>
     * This method gets invoked by the channel if a RPC message comes in
     * and no channel listener accepts the message. This avoids timeout
     * @param msg RpcMessage
     * @param destination Member - the destination for the reply
     */
    protected void sendNoRpcChannelReply(RpcMessage msg, Member destination) {
        try {
            //avoid circular loop
            if ( msg instanceof RpcMessage.NoRpcChannelReply) return;
            RpcMessage.NoRpcChannelReply reply = new RpcMessage.NoRpcChannelReply(msg.rpcId,msg.uuid);
            send(new Member[]{destination},reply,Channel.SEND_OPTIONS_ASYNCHRONOUS);
        } catch ( Exception x ) {
            log.error("Unable to find rpc channel, failed to send NoRpcChannelReply.",x);
        }
    }

    /**
     * memberAdded gets invoked by the interceptor below the channel
     * and the channel will broadcast it to the membership listeners
     * @param member Member - the new member
     */
    @Override
    public void memberAdded(Member member) {
        //notify upwards
        for (int i=0; i<membershipListeners.size(); i++ ) {
            MembershipListener membershipListener = (MembershipListener)membershipListeners.get(i);
            if (membershipListener != null) membershipListener.memberAdded(member);
        }
    }

    /**
     * memberDisappeared gets invoked by the interceptor below the channel
     * and the channel will broadcast it to the membership listeners
     * @param member Member - the member that left or crashed
     */
    @Override
    public void memberDisappeared(Member member) {
        //notify upwards
        for (int i=0; i<membershipListeners.size(); i++ ) {
            MembershipListener membershipListener = (MembershipListener)membershipListeners.get(i);
            if (membershipListener != null) membershipListener.memberDisappeared(member);
        }
    }

    /**
     * Sets up the default implementation interceptor stack
     * if no interceptors have been added
     * @throws ChannelException
     */
    protected synchronized void setupDefaultStack() throws ChannelException {

        if ( getFirstInterceptor() != null &&
             ((getFirstInterceptor().getNext() instanceof ChannelCoordinator))) {
            ChannelInterceptor interceptor = null;
            Class<?> clazz = null;
            try {
                clazz = Class.forName("org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor",
                                      true,GroupChannel.class.getClassLoader());
                clazz.newInstance();
            } catch ( Throwable x ) {
                clazz = MessageDispatchInterceptor.class;
            }//catch
            try {
                interceptor = (ChannelInterceptor) clazz.newInstance();
            } catch (Exception x) {
                throw new ChannelException("Unable to add MessageDispatchInterceptor to interceptor chain.",x);
            }
            this.addInterceptor(interceptor);
        }
        Iterator<ChannelInterceptor> interceptors = getInterceptors();
        while (interceptors.hasNext()) {
            ChannelInterceptor channelInterceptor = interceptors.next();
            if (channelInterceptor instanceof ChannelInterceptorBase)
                ((ChannelInterceptorBase)channelInterceptor).setChannel(this);
        }
        coordinator.setChannel(this);
    }

    /**
     * Validates the option flags that each interceptor is using and reports
     * an error if two interceptor share the same flag.
     * @throws ChannelException
     */
    protected void checkOptionFlags() throws ChannelException {
        StringBuilder conflicts = new StringBuilder();
        ChannelInterceptor first = interceptors;
        while ( first != null ) {
            int flag = first.getOptionFlag();
            if ( flag != 0 ) {
                ChannelInterceptor next = first.getNext();
                while ( next != null ) {
                    int nflag = next.getOptionFlag();
                    if (nflag!=0 && (((flag & nflag) == flag ) || ((flag & nflag) == nflag)) ) {
                        conflicts.append("[");
                        conflicts.append(first.getClass().getName());
                        conflicts.append(":");
                        conflicts.append(flag);
                        conflicts.append(" == ");
                        conflicts.append(next.getClass().getName());
                        conflicts.append(":");
                        conflicts.append(nflag);
                        conflicts.append("] ");
                    }//end if
                    next = next.getNext();
                }//while
            }//end if
            first = first.getNext();
        }//while
        if ( conflicts.length() > 0 ) throw new ChannelException("Interceptor option flag conflict: "+conflicts.toString());

    }

    /**
     * Starts the channel
     * @param svc int - what service to start
     * @throws ChannelException
     * @see org.apache.catalina.tribes.Channel#start(int)
     */
    @Override
    public synchronized void start(int svc) throws ChannelException {
        setupDefaultStack();
        if (optionCheck) checkOptionFlags();
        super.start(svc);
        if ( hbthread == null && heartbeat ) {
            hbthread = new HeartbeatThread(this,heartbeatSleeptime);
            hbthread.start();
        }
    }

    /**
     * Stops the channel
     * @param svc int
     * @throws ChannelException
     * @see org.apache.catalina.tribes.Channel#stop(int)
     */
    @Override
    public synchronized void stop(int svc) throws ChannelException {
        if (hbthread != null) {
            hbthread.stopHeartbeat();
            hbthread = null;
        }
        super.stop(svc);
    }

    /**
     * Returns the first interceptor of the stack. Useful for traversal.
     * @return ChannelInterceptor
     */
    public ChannelInterceptor getFirstInterceptor() {
        if (interceptors != null) return interceptors;
        else return coordinator;
    }

    /**
     * Returns the channel receiver component
     * @return ChannelReceiver
     */
    @Override
    public ChannelReceiver getChannelReceiver() {
        return coordinator.getClusterReceiver();
    }

    /**
     * Returns the channel sender component
     * @return ChannelSender
     */
    @Override
    public ChannelSender getChannelSender() {
        return coordinator.getClusterSender();
    }

    /**
     * Returns the membership service component
     * @return MembershipService
     */
    @Override
    public MembershipService getMembershipService() {
        return coordinator.getMembershipService();
    }

    /**
     * Sets the channel receiver component
     * @param clusterReceiver ChannelReceiver
     */
    @Override
    public void setChannelReceiver(ChannelReceiver clusterReceiver) {
        coordinator.setClusterReceiver(clusterReceiver);
    }

    /**
     * Sets the channel sender component
     * @param clusterSender ChannelSender
     */
    @Override
    public void setChannelSender(ChannelSender clusterSender) {
        coordinator.setClusterSender(clusterSender);
    }

    /**
     * Sets the membership component
     * @param membershipService MembershipService
     */
    @Override
    public void setMembershipService(MembershipService membershipService) {
        coordinator.setMembershipService(membershipService);
    }

    /**
     * Adds a membership listener to the channel.<br>
     * Membership listeners are uniquely identified using the equals(Object) method
     * @param membershipListener MembershipListener
     */
    @Override
    public void addMembershipListener(MembershipListener membershipListener) {
        if (!this.membershipListeners.contains(membershipListener) )
            this.membershipListeners.add(membershipListener);
    }

    /**
     * Removes a membership listener from the channel.<br>
     * Membership listeners are uniquely identified using the equals(Object) method
     * @param membershipListener MembershipListener
     */

    @Override
    public void removeMembershipListener(MembershipListener membershipListener) {
        membershipListeners.remove(membershipListener);
    }

    /**
     * Adds a channel listener to the channel.<br>
     * Channel listeners are uniquely identified using the equals(Object) method
     * @param channelListener ChannelListener
     */
    @Override
    public void addChannelListener(ChannelListener channelListener) {
        if (!this.channelListeners.contains(channelListener) ) {
            this.channelListeners.add(channelListener);
        } else {
            throw new IllegalArgumentException("Listener already exists:"+channelListener+"["+channelListener.getClass().getName()+"]");
        }
    }

    /**
     *
     * Removes a channel listener from the channel.<br>
     * Channel listeners are uniquely identified using the equals(Object) method
     * @param channelListener ChannelListener
     */
    @Override
    public void removeChannelListener(ChannelListener channelListener) {
        channelListeners.remove(channelListener);
    }

    /**
     * Returns an iterator of all the interceptors in this stack
     * @return Iterator
     */
    @Override
    public Iterator<ChannelInterceptor> getInterceptors() {
        return new InterceptorIterator(this.getNext(),this.coordinator);
    }

    /**
     * Enables/disables the option check<br>
     * Setting this to true, will make the GroupChannel perform a conflict check
     * on the interceptors. If two interceptors are using the same option flag
     * and throw an error upon start.
     * @param optionCheck boolean
     */
    public void setOptionCheck(boolean optionCheck) {
        this.optionCheck = optionCheck;
    }

    /**
     * Configure local heartbeat sleep time<br>
     * Only used when <code>getHeartbeat()==true</code>
     * @param heartbeatSleeptime long - time in milliseconds to sleep between heartbeats
     */
    public void setHeartbeatSleeptime(long heartbeatSleeptime) {
        this.heartbeatSleeptime = heartbeatSleeptime;
    }

    /**
     * Enables or disables local heartbeat.
     * if <code>setHeartbeat(true)</code> is invoked then the channel will start an internal
     * thread to invoke <code>Channel.heartbeat()</code> every <code>getHeartbeatSleeptime</code> milliseconds
     * @param heartbeat boolean
     */
    @Override
    public void setHeartbeat(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }

    /**
     * @see #setOptionCheck(boolean)
     * @return boolean
     */
    public boolean getOptionCheck() {
        return optionCheck;
    }

    /**
     * @see #setHeartbeat(boolean)
     * @return boolean
     */
    public boolean getHeartbeat() {
        return heartbeat;
    }

    /**
     * Returns the sleep time in milliseconds that the internal heartbeat will
     * sleep in between invocations of <code>Channel.heartbeat()</code>
     * @return long
     */
    public long getHeartbeatSleeptime() {
        return heartbeatSleeptime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * <p>Title: Interceptor Iterator</p>
     *
     * <p>Description: An iterator to loop through the interceptors in a channel</p>
     *
     * @version 1.0
     */
    public static class InterceptorIterator implements Iterator<ChannelInterceptor> {
        private ChannelInterceptor end;
        private ChannelInterceptor start;
        public InterceptorIterator(ChannelInterceptor start, ChannelInterceptor end) {
            this.end = end;
            this.start = start;
        }

        @Override
        public boolean hasNext() {
            return start!=null && start != end;
        }

        @Override
        public ChannelInterceptor next() {
            ChannelInterceptor result = null;
            if ( hasNext() ) {
                result = start;
                start = start.getNext();
            }
            return result;
        }

        @Override
        public void remove() {
            //empty operation
        }
    }

    /**
     *
     * <p>Title: Internal heartbeat thread</p>
     *
     * <p>Description: if <code>Channel.getHeartbeat()==true</code> then a thread of this class
     * is created</p>
     *
     * @version 1.0
     */
    public static class HeartbeatThread extends Thread {
        private static final Log log = LogFactory.getLog(HeartbeatThread.class);
        protected static int counter = 1;
        protected static synchronized int inc() {
            return counter++;
        }

        protected volatile boolean doRun = true;
        protected GroupChannel channel;
        protected long sleepTime;
        public HeartbeatThread(GroupChannel channel, long sleepTime) {
            super();
            this.setPriority(MIN_PRIORITY);
            String channelName = "";
            if (channel.getName() != null) channelName = "[" + channel.getName() + "]";
            setName("GroupChannel-Heartbeat" + channelName + "-" +inc());
            setDaemon(true);
            this.channel = channel;
            this.sleepTime = sleepTime;
        }
        public void stopHeartbeat() {
            doRun = false;
            interrupt();
        }

        @Override
        public void run() {
            while (doRun) {
                try {
                    Thread.sleep(sleepTime);
                    channel.heartbeat();
                } catch ( InterruptedException x ) {
                    interrupted();
                } catch ( Exception x ) {
                    log.error("Unable to send heartbeat through Tribes interceptor stack. Will try to sleep again.",x);
                }//catch
            }//while
        }//run
    }//HeartbeatThread



}
