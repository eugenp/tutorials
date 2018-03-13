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


/**
 * ChannelReceiver Interface<br>
 * The <code>ChannelReceiver</code> interface is the data receiver component 
 * at the bottom layer, the IO layer (for layers see the javadoc for the {@link Channel} interface).
 * This class may optionally implement a thread pool for parallel processing of incoming messages.
 * @author Filip Hanik
 */
public interface ChannelReceiver extends Heartbeat {
    public static final int MAX_UDP_SIZE = 65535;
    
    /**
     * Start listening for incoming messages on the host/port
     * @throws java.io.IOException
     */
    public void start() throws java.io.IOException;

    /**
     * Stop listening for messages
     */
    public void stop();

    /**
     * String representation of the IPv4 or IPv6 address that this host is listening
     * to.
     * @return the host that this receiver is listening to
     */
    public String getHost();
    
    
    /**
     * Returns the listening port
     * @return port
     */
    public int getPort();
    
    /**
     * Returns the secure listening port
     * @return port, -1 if a secure port is not activated
     */
    public int getSecurePort();
    
    /**
     * Returns the UDP port
     * @return port, -1 if the UDP port is not activated.
     */
    public int getUdpPort();
    
    /**
     * Sets the message listener to receive notification of incoming
     * @param listener MessageListener
     * @see MessageListener
     */
    public void setMessageListener(MessageListener listener);
    
    /**
     * Returns the message listener that is associated with this receiver
     * @return MessageListener
     * @see MessageListener
     */
    public MessageListener getMessageListener();

}
