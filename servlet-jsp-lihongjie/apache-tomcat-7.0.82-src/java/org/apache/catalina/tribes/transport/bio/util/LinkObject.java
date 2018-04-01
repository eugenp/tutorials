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

package org.apache.catalina.tribes.transport.bio.util;

import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.ErrorHandler;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.InterceptorPayload;

/**
 * The class <b>LinkObject</b> implements an element
 * for a linked list, consisting of a general
 * data object and a pointer to the next element.
 *
 * @author Rainer Jung
 * @author Peter Rossbach
 * @author Filip Hanik
 */
public class LinkObject {

    private ChannelMessage msg;
    private LinkObject next;
    private byte[] key ;
    private Member[] destination;
    private InterceptorPayload payload;

    /**
     * Construct a new element from the data object.
     * Sets the pointer to null.
     *
     * @param msg the message
     * @param destination TBA
     * @param payload The data object.
     */
    public LinkObject(ChannelMessage msg, Member[] destination, InterceptorPayload payload) {
        this.msg = msg;
        this.next = null;
        this.key = msg.getUniqueId();
        this.payload = payload;
        this.destination = destination;
    }

    /**
     * Set the next element.
     * @param next The next element.
     */
    public void append(LinkObject next) {
        this.next = next;
    }

    /**
     * Get the next element.
     * @return The next element.
     */
    public LinkObject next() {
        return next;
    }
    
    public void setNext(LinkObject next) {
        this.next = next;
    }

    /**
     * Get the data object from the element.
     * @return The data object from the element.
     */
    public ChannelMessage data() {
        return msg;
    }

    /**
     * Get the unique message id
     * @return the unique message id
     */
    public byte[] getKey() {
        return key;
    }

    public ErrorHandler getHandler() {
        return payload!=null?payload.getErrorHandler():null;
    }

    public InterceptorPayload getPayload() {
        return payload;
    }

    public Member[] getDestination() {
        return destination;
    }

}
