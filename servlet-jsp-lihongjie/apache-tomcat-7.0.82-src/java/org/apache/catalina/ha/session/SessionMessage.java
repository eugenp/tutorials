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
package org.apache.catalina.ha.session;
import org.apache.catalina.ha.ClusterMessage;

/**
 *
 * <B>Class Description:</B><BR>
 * The SessionMessage class is a class that is used when a session has been
 * created, modified, expired in a Tomcat cluster node.<BR>
 *
 * The following events are currently available:
 * <ul>
 *   <li><pre>public static final int EVT_SESSION_CREATED</pre><li>
 *   <li><pre>public static final int EVT_SESSION_EXPIRED</pre><li>
 *   <li><pre>public static final int EVT_SESSION_ACCESSED</pre><li>
 *   <li><pre>public static final int EVT_GET_ALL_SESSIONS</pre><li>
 *   <li><pre>public static final int EVT_SESSION_DELTA</pre><li>
 *   <li><pre>public static final int EVT_ALL_SESSION_DATA</pre><li>
 *   <li><pre>public static final int EVT_ALL_SESSION_TRANSFERCOMPLETE</pre><li>
 *   <li><pre>public static final int EVT_CHANGE_SESSION_ID</pre><li>
 *   <li><pre>public static final int EVT_ALL_SESSION_NOCONTEXTMANAGER</pre><li>
 * </ul>
 *
 */

public interface SessionMessage extends ClusterMessage {

    /**
     * Event type used when a session has been created on a node
     */
    public static final int EVT_SESSION_CREATED = 1;
    /**
     * Event type used when a session has expired
     */
    public static final int EVT_SESSION_EXPIRED = 2;

    /**
     * Event type used when a session has been accessed (ie, last access time
     * has been updated. This is used so that the replicated sessions will not expire
     * on the network
     */
    public static final int EVT_SESSION_ACCESSED = 3;
    /**
     * Event type used when a server comes online for the first time.
     * The first thing the newly started server wants to do is to grab the
     * all the sessions from one of the nodes and keep the same state in there
     */
    public static final int EVT_GET_ALL_SESSIONS = 4;
    /**
     * Event type used when an attribute has been added to a session,
     * the attribute will be sent to all the other nodes in the cluster
     */
    public static final int EVT_SESSION_DELTA  = 13;

    /**
     * When a session state is transferred, this is the event.
     */
    public static final int EVT_ALL_SESSION_DATA = 12;
    
    /**
     * When a session state is complete transferred, this is the event.
     */
    public static final int EVT_ALL_SESSION_TRANSFERCOMPLETE = 14;

    /**
     * Event type used when a sessionID has been changed.
     */
    public static final int EVT_CHANGE_SESSION_ID = 15;

    /**
     * Event type used when context manager doesn't exist.
     * This is used when the manager which send a session state does not exist.
     */
    public static final int EVT_ALL_SESSION_NOCONTEXTMANAGER = 16;

    public String getContextName();
    
    public String getEventTypeString();
    
    /**
     * returns the event type
     * @return one of the event types EVT_XXXX
     */
    public int getEventType(); 
    /**
     * @return the serialized data for the session
     */
    public byte[] getSession();
    /**
     * @return the session ID for the session
     */
    public String getSessionID();


}//SessionMessage
