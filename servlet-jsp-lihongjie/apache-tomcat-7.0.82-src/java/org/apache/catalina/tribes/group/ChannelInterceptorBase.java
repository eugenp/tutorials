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

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelInterceptor;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;

/**
 * Abstract class for the interceptor base class.
 * @author Filip Hanik
 */
public abstract class ChannelInterceptorBase implements ChannelInterceptor {

    private ChannelInterceptor next;
    private ChannelInterceptor previous;
    private Channel channel;
    //default value, always process
    protected int optionFlag = 0;

    public ChannelInterceptorBase() {

    }
    
    public boolean okToProcess(int messageFlags) { 
        if (this.optionFlag == 0 ) return true;
        return ((optionFlag&messageFlags) == optionFlag);
    }

    @Override
    public final void setNext(ChannelInterceptor next) {
        this.next = next;
    }

    @Override
    public final ChannelInterceptor getNext() {
        return next;
    }

    @Override
    public final void setPrevious(ChannelInterceptor previous) {
        this.previous = previous;
    }

    @Override
    public void setOptionFlag(int optionFlag) {
        this.optionFlag = optionFlag;
    }

    @Override
    public final ChannelInterceptor getPrevious() {
        return previous;
    }

    @Override
    public int getOptionFlag() {
        return optionFlag;
    }

    @Override
    public void sendMessage(Member[] destination, ChannelMessage msg, InterceptorPayload payload) throws
        ChannelException {
        if (getNext() != null) getNext().sendMessage(destination, msg, payload);
    }

    @Override
    public void messageReceived(ChannelMessage msg) {
        if (getPrevious() != null) getPrevious().messageReceived(msg);
    }

    public boolean accept(ChannelMessage msg) {
        return true;
    }

    @Override
    public void memberAdded(Member member) {
        //notify upwards
        if (getPrevious() != null) getPrevious().memberAdded(member);
    }

    @Override
    public void memberDisappeared(Member member) {
        //notify upwards
        if (getPrevious() != null) getPrevious().memberDisappeared(member);
    }

    @Override
    public void heartbeat() {
        if (getNext() != null) getNext().heartbeat();
    }

    /**
     * has members
     */
    @Override
    public boolean hasMembers() {
        if ( getNext()!=null )return getNext().hasMembers();
        else return false;
    }

    /**
     * Get all current cluster members
     * @return all members or empty array
     */
    @Override
    public Member[] getMembers() {
        if ( getNext()!=null ) return getNext().getMembers();
        else return null;
    }

    /**
     *
     * @param mbr Member
     * @return Member
     */
    @Override
    public Member getMember(Member mbr) {
        if ( getNext()!=null) return getNext().getMember(mbr);
        else return null;
    }

    /**
     * Return the member that represents this node.
     *
     * @return Member
     */
    @Override
    public Member getLocalMember(boolean incAlive) {
        if ( getNext()!=null ) return getNext().getLocalMember(incAlive);
        else return null;
    }
    
    /**
     * Starts up the channel. This can be called multiple times for individual services to start
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will start all services <BR>
     * MBR_RX_SEQ - starts the membership receiver <BR>
     * MBR_TX_SEQ - starts the membership broadcaster <BR>
     * SND_TX_SEQ - starts the replication transmitter<BR>
     * SND_RX_SEQ - starts the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already started.
     */
    @Override
    public void start(int svc) throws ChannelException {
        if ( getNext()!=null ) getNext().start(svc);
    }

    /**
     * Shuts down the channel. This can be called multiple times for individual services to shutdown
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will shutdown all services <BR>
     * MBR_RX_SEQ - stops the membership receiver <BR>
     * MBR_TX_SEQ - stops the membership broadcaster <BR>
     * SND_TX_SEQ - stops the replication transmitter<BR>
     * SND_RX_SEQ - stops the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already started.
     */
    @Override
    public void stop(int svc) throws ChannelException {
        if (getNext() != null) getNext().stop(svc);
        channel = null;
    }
    
    @Override
    public void fireInterceptorEvent(InterceptorEvent event) {
        //empty operation
    }

    /**
     * Return the channel that is related to this interceptor
     * @return Channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * Set the channel that is related to this interceptor
     * @param channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
