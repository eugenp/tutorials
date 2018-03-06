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
package org.apache.catalina.tribes;

import java.io.Serializable;

/**
 * Channel interface<br>
 * A channel is a representation of a group of nodes all participating in some sort of
 * communication with each other.<br>
 * The channel is the main API class for Tribes, this is essentially the only class
 * that an application needs to be aware of. Through the channel the application can:<br>
 * 1. send messages<br>
 * 2. receive message (by registering a <code>ChannelListener</code><br>
 * 3. get all members of the group <code>getMembers()</code><br>
 * 4. receive notifications of members added and members disappeared by
 *    registering a <code>MembershipListener</code><br>
 * <br>
 * The channel has 5 major components:<br>
 * 1. Data receiver, with a built in thread pool to receive messages from other peers<br>
 * 2. Data sender, an implementation for sending data using NIO or java.io<br>
 * 3. Membership listener,listens for membership broadcasts<br>
 * 4. Membership broadcaster, broadcasts membership pings.<br>
 * 5. Channel interceptors, the ability to manipulate messages as they are sent or arrive<br><br>
 * The channel layout is:
 * <pre><code>
 *  ChannelListener_1..ChannelListener_N MembershipListener_1..MembershipListener_N [Application Layer]
 *            \          \                  /                   /
 *             \          \                /                   /
 *              \          \              /                   /
 *               \          \            /                   /
 *                \          \          /                   /
 *                 \          \        /                   /
 *                  ---------------------------------------
 *                                  |
 *                                  |
 *                               Channel
 *                                  |
 *                         ChannelInterceptor_1
 *                                  |                                               [Channel stack]
 *                         ChannelInterceptor_N
 *                                  |
 *                             Coordinator (implements MessageListener,MembershipListener,ChannelInterceptor)
 *                          --------------------
 *                         /        |           \
 *                        /         |            \
 *                       /          |             \
 *                      /           |              \
 *                     /            |               \
 *           MembershipService ChannelSender ChannelReceiver                        [IO layer]
 * </code></pre>
 *
 * For example usage @see org.apache.catalina.tribes.group.GroupChannel
 * @author Filip Hanik
 */
public interface Channel {

    /**
     * Start and stop sequences can be controlled by these constants
     * This allows you to start separate components of the channel <br>
     * DEFAULT - starts or stops all components in the channel
     * @see #start(int)
     * @see #stop(int)
     */
    public static final int DEFAULT = 15;

    /**
     * Start and stop sequences can be controlled by these constants
     * This allows you to start separate components of the channel <br>
     * SND_RX_SEQ - starts or stops the data receiver. Start means opening a server socket
     * in case of a TCP implementation
     * @see #start(int)
     * @see #stop(int)
     */
    public static final int SND_RX_SEQ = 1;

    /**
     * Start and stop sequences can be controlled by these constants
     * This allows you to start separate components of the channel <br>
     * SND_TX_SEQ - starts or stops the data sender. This should not open any sockets,
     * as sockets are opened on demand when a message is being sent
     * @see #start(int)
     * @see #stop(int)
     */
    public static final int SND_TX_SEQ = 2;

    /**
     * Start and stop sequences can be controlled by these constants
     * This allows you to start separate components of the channel <br>
     * MBR_RX_SEQ - starts or stops the membership listener. In a multicast implementation
     * this will open a datagram socket and join a group and listen for membership messages
     * members joining
     * @see #start(int)
     * @see #stop(int)
     */
    public static final int MBR_RX_SEQ = 4;

    /**
     * Start and stop sequences can be controlled by these constants
     * This allows you to start separate components of the channel <br>
     * MBR_TX_SEQ - starts or stops the membership broadcaster. In a multicast implementation
     * this will open a datagram socket and join a group and broadcast the local member information
     * @see #start(int)
     * @see #stop(int)
     */
    public static final int MBR_TX_SEQ = 8;

    /**
     * Send options, when a message is sent, it can have an option flag
     * to trigger certain behavior. Most flags are used to trigger channel interceptors
     * as the message passes through the channel stack. <br>
     * However, there are five default flags that every channel implementation must implement<br>
     * SEND_OPTIONS_BYTE_MESSAGE - The message is a pure byte message and no marshaling or unmarshaling will
     * be performed.<br>
     *
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_BYTE_MESSAGE = 0x0001;

    /**
     * Send options, when a message is sent, it can have an option flag
     * to trigger certain behavior. Most flags are used to trigger channel interceptors
     * as the message passes through the channel stack. <br>
     * However, there are five default flags that every channel implementation must implement<br>
     * SEND_OPTIONS_USE_ACK - Message is sent and an ACK is received when the message has been received by the recipient<br>
     * If no ack is received, the message is not considered successful<br>
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_USE_ACK = 0x0002;

    /**
     * Send options, when a message is sent, it can have an option flag
     * to trigger certain behavior. Most flags are used to trigger channel interceptors
     * as the message passes through the channel stack. <br>
     * However, there are five default flags that every channel implementation must implement<br>
     * SEND_OPTIONS_SYNCHRONIZED_ACK - Message is sent and an ACK is received when the message has been received and
     * processed by the recipient<br>
     * If no ack is received, the message is not considered successful<br>
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_SYNCHRONIZED_ACK = 0x0004;

    /**
     * Send options, when a message is sent, it can have an option flag
     * to trigger certain behavior. Most flags are used to trigger channel interceptors
     * as the message passes through the channel stack. <br>
     * However, there are five default flags that every channel implementation must implement<br>
     * SEND_OPTIONS_ASYNCHRONOUS - Message is sent and an ACK is received when the message has been received and
     * processed by the recipient<br>
     * If no ack is received, the message is not considered successful<br>
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_ASYNCHRONOUS = 0x0008;

    /**
     * Send options, when a message is sent, it can have an option flag
     * to trigger certain behavior. Most flags are used to trigger channel interceptors
     * as the message passes through the channel stack. <br>
     * However, there are five default flags that every channel implementation must implement<br>
     * SEND_OPTIONS_SECURE - Message is sent over an encrypted channel<br>
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_SECURE = 0x0010;

    /**
     * Send options. When a message is sent with this flag on
     * the system sends the message using UDP instead of TCP
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_UDP =  0x0020;

    /**
     * Send options. When a message is sent with this flag on
     * the system sends a UDP message on the Multicast address instead of UDP or TCP to individual addresses
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_MULTICAST =  0x0040;

    /**
     * Send options, when a message is sent, it can have an option flag
     * to trigger certain behavior. Most flags are used to trigger channel interceptors
     * as the message passes through the channel stack. <br>
     * However, there are five default flags that every channel implementation must implement<br>
     * SEND_OPTIONS_DEFAULT - the default sending options, just a helper variable. <br>
     * The default is <code>int SEND_OPTIONS_DEFAULT = SEND_OPTIONS_USE_ACK;</code><br>
     * @see #SEND_OPTIONS_USE_ACK
     * @see #send(Member[], Serializable , int)
     * @see #send(Member[], Serializable, int, ErrorHandler)
     */
    public static final int SEND_OPTIONS_DEFAULT = SEND_OPTIONS_USE_ACK;


    /**
     * Adds an interceptor to the channel message chain.
     * @param interceptor ChannelInterceptor
     */
    public void addInterceptor(ChannelInterceptor interceptor);

    /**
     * Starts up the channel. This can be called multiple times for individual services to start
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will start all services <BR>
     * MBR_RX_SEQ - starts the membership receiver <BR>
     * MBR_TX_SEQ - starts the membership broadcaster <BR>
     * SND_TX_SEQ - starts the replication transmitter<BR>
     * SND_RX_SEQ - starts the replication receiver<BR>
     * <b>Note:</b> In order for the membership broadcaster to
     * transmit the correct information, it has to be started after the replication receiver.
     * @throws ChannelException if a startup error occurs or the service is already started or an error occurs.
     */
    public void start(int svc) throws ChannelException;

    /**
     * Shuts down the channel. This can be called multiple times for individual services to shutdown
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will shutdown all services <BR>
     * MBR_RX_SEQ - stops the membership receiver <BR>
     * MBR_TX_SEQ - stops the membership broadcaster <BR>
     * SND_TX_SEQ - stops the replication transmitter<BR>
     * SND_RX_SEQ - stops the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already stopped or an error occurs.
     */
    public void stop(int svc) throws ChannelException;

    /**
     * Send a message to one or more members in the cluster
     * @param destination Member[] - the destinations, can not be null or zero length, the reason for that
     * is that a membership change can occur and at that time the application is uncertain what group the message
     * actually got sent to.
     * @param msg Serializable - the message to send, has to be serializable, or a <code>ByteMessage</code> to
     * send a pure byte array
     * @param options int - sender options, see class documentation for each interceptor that is configured in order to trigger interceptors
     * @return a unique Id that identifies the message that is sent
     * @see ByteMessage
     * @see #SEND_OPTIONS_USE_ACK
     * @see #SEND_OPTIONS_ASYNCHRONOUS
     * @see #SEND_OPTIONS_SYNCHRONIZED_ACK
     */
    public UniqueId send(Member[] destination, Serializable msg, int options) throws ChannelException;

    /**
     * Send a message to one or more members in the cluster
     * @param destination Member[] - the destinations, null or zero length means all
     * @param msg ClusterMessage - the message to send
     * @param options int - sender options, see class documentation
     * @param handler ErrorHandler - handle errors through a callback, rather than throw it
     * @return a unique Id that identifies the message that is sent
     * @exception ChannelException - if a serialization error happens.
     */
    public UniqueId send(Member[] destination, Serializable msg, int options, ErrorHandler handler) throws ChannelException;

    /**
     * Sends a heart beat through the interceptor stacks
     * Use this method to alert interceptors and other components to
     * clean up garbage, timed out messages etc.<br>
     * If you application has a background thread, then you can save one thread,
     * by configuring your channel to not use an internal heartbeat thread
     * and invoking this method.
     * @see #setHeartbeat(boolean)
     */
    public void heartbeat();

    /**
     * Enables or disables internal heartbeat.
     * @param enable boolean - default value is implementation specific
     * @see #heartbeat()
     */
    public void setHeartbeat(boolean enable);

    /**
     * Add a membership listener, will get notified when a new member joins, leaves or crashes
     * <br>If the membership listener implements the Heartbeat interface
     * the <code>heartbeat()</code> method will be invoked when the heartbeat runs on the channel
     * @param listener MembershipListener
     * @see MembershipListener
     */
    public void addMembershipListener(MembershipListener listener);

    /**
     * Add a channel listener, this is a callback object when messages are received
     * <br>If the channel listener implements the Heartbeat interface
     * the <code>heartbeat()</code> method will be invoked when the heartbeat runs on the channel
     * @param listener ChannelListener
     * @see ChannelListener
     * @see Heartbeat
     */
    public void addChannelListener(ChannelListener listener);

    /**
     * remove a membership listener, listeners are removed based on Object.hashCode and Object.equals
     * @param listener MembershipListener
     * @see MembershipListener
     */
    public void removeMembershipListener(MembershipListener listener);
    /**
     * remove a channel listener, listeners are removed based on Object.hashCode and Object.equals
     * @param listener ChannelListener
     * @see ChannelListener
     */
    public void removeChannelListener(ChannelListener listener);

    /**
     * Returns true if there are any members in the group,
     * this call is the same as <code>getMembers().length>0</code>
     * @return boolean - true if there are any members automatically discovered
     */
    public boolean hasMembers() ;

    /**
     * Get all current group members
     * @return all members or empty array, never null
     */
    public Member[] getMembers() ;

    /**
     * Return the member that represents this node. This is also the data
     * that gets broadcasted through the membership broadcaster component
     * @param incAlive - optimization, true if you want it to calculate alive time
     * since the membership service started.
     * @return Member
     */
    public Member getLocalMember(boolean incAlive);

    /**
     * Returns the member from the membership service with complete and
     * recent data. Some implementations might serialize and send
     * membership information along with a message, and instead of sending
     * complete membership details, only send the primary identifier for the member
     * but not the payload or other information. When such message is received
     * the application can retrieve the cached member through this call.<br>
     * In most cases, this is not necessary.
     * @param mbr Member
     * @return Member
     */
    public Member getMember(Member mbr);


}
