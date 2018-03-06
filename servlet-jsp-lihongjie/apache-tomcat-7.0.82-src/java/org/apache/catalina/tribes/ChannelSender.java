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

import java.io.IOException;


/**
 * ChannelReceiver Interface<br>
 * The <code>ChannelSender</code> interface is the data sender component 
 * at the bottom layer, the IO layer (for layers see the javadoc for the {@link Channel} interface).<br>
 * The channel sender must support "silent" members, ie, be able to send a message to a member
 * that is not in the membership, but is part of the destination parameter
 * @author Filip Hanik
 */
public interface ChannelSender extends Heartbeat
{
    /**
     * Notify the sender of a member being added to the group.<br>
     * Optional. This can be an empty implementation, that does nothing
     * @param member Member
     */
    public void add(Member member);
    /**
     * Notification that a member has been removed or crashed.
     * Can be used to clean up open connections etc
     * @param member Member
     */
    public void remove(Member member);
    
    /**
     * Start the channel sender
     * @throws IOException if preprocessing takes place and an error happens
     */
    public void start() throws IOException;

    /**
     * Stop the channel sender
     */
    public void stop();
    
    /**
     * A channel heartbeat, use this method to clean up resources
     */
    @Override
    public void heartbeat() ;
    
    /**
     * Send a message to one or more recipients.
     * @param message ChannelMessage - the message to be sent
     * @param destination Member[] - the destinations
     * @throws ChannelException - if an error happens, the ChannelSender MUST report
     * individual send failures on a per member basis, using ChannelException.addFaultyMember
     * @see ChannelException#addFaultyMember(Member,java.lang.Exception)
     */
    public void sendMessage(ChannelMessage message, Member[] destination) throws ChannelException;
}
