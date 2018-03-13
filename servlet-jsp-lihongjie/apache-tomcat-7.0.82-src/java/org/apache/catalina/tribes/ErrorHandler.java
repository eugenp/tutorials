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
 * The <code>ErrorHandler</code> class is used when sending messages
 * that are sent asynchronously and the application still needs to get 
 * confirmation when the message was sent successfully or when a message errored out.
 * @author Filip Hanik
 * @version 1.0
 */
public interface ErrorHandler {
    
    /**
     * Invoked if the message is dispatched async, and an error occurs
     * @param x ChannelException - the error that happened
     * @param id - the unique id for the message
     * @see Channel#send(Member[], java.io.Serializable, int, ErrorHandler)
     */
    public void handleError(ChannelException x, UniqueId id);
    
    /**
     * Invoked when the message has been sent successfully.
     * @param id - the unique id for the message
     * @see Channel#send(Member[], java.io.Serializable, int, ErrorHandler)
     */
    public void handleCompletion(UniqueId id);
    
}