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

import java.util.Map;

import org.apache.catalina.ha.ClusterListener;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.ClusterMessage;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Receive replicated SessionMessage form other cluster node.
 * @author Filip Hanik
 * @author Peter Rossbach
 */
public class ClusterSessionListener extends ClusterListener {

    private static final Log log =
        LogFactory.getLog(ClusterSessionListener.class);
    
    /**
     * The descriptive information about this implementation.
     */
    protected static final String info = "org.apache.catalina.ha.session.ClusterSessionListener/1.1";

    //--Constructor---------------------------------------------

    public ClusterSessionListener() {
        // NO-OP
    }

    //--Logic---------------------------------------------------

    /**
     * Return descriptive information about this implementation.
     */
    public String getInfo() {

        return (info);

    }

    /**
     * Callback from the cluster, when a message is received, The cluster will
     * broadcast it invoking the messageReceived on the receiver.
     * 
     * @param myobj
     *            ClusterMessage - the message received from the cluster
     */
    @Override
    public void messageReceived(ClusterMessage myobj) {
        if (myobj != null && myobj instanceof SessionMessage) {
            SessionMessage msg = (SessionMessage) myobj;
            String ctxname = msg.getContextName();
            //check if the message is a EVT_GET_ALL_SESSIONS,
            //if so, wait until we are fully started up
            Map<String,ClusterManager> managers = cluster.getManagers() ;
            if (ctxname == null) {
                for (Map.Entry<String, ClusterManager> entry :
                        managers.entrySet()) {
                    if (entry.getValue() != null)
                        entry.getValue().messageDataReceived(msg);
                    else {
                        //this happens a lot before the system has started
                        // up
                        if (log.isDebugEnabled())
                            log.debug("Context manager doesn't exist:"
                                    + entry.getKey());
                    }
                }
            } else {
                ClusterManager mgr = managers.get(ctxname);
                if (mgr != null) {
                    mgr.messageDataReceived(msg);
                } else {
                    if (log.isWarnEnabled())
                        log.warn("Context manager doesn't exist:" + ctxname);

                    // A no context manager message is replied in order to avoid
                    // timeout of GET_ALL_SESSIONS sync phase.
                    if (msg.getEventType() == SessionMessage.EVT_GET_ALL_SESSIONS) {
                        SessionMessage replymsg = new SessionMessageImpl(ctxname,
                                SessionMessage.EVT_ALL_SESSION_NOCONTEXTMANAGER,
                                null, "NO-CONTEXT-MANAGER","NO-CONTEXT-MANAGER-" + ctxname);
                        cluster.send(replymsg, msg.getAddress());
                    }
                }
            }
        }
        return;
    }

    /**
     * Accept only SessionMessage
     * 
     * @param msg
     *            ClusterMessage
     * @return boolean - returns true to indicate that messageReceived should be
     *         invoked. If false is returned, the messageReceived method will
     *         not be invoked.
     */
    @Override
    public boolean accept(ClusterMessage msg) {
        return (msg instanceof SessionMessage);
    }
}

