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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.catalina.DistributedManager;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Session;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.ClusterMessage;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.tipis.AbstractReplicatedMap.MapOwner;
import org.apache.catalina.tribes.tipis.LazyReplicatedMap;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

/**
 *@author Filip Hanik
 *@version 1.0
 */
public class BackupManager extends ClusterManagerBase
        implements MapOwner, DistributedManager {

    private final Log log = LogFactory.getLog(BackupManager.class);

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    protected static long DEFAULT_REPL_TIMEOUT = 15000;//15 seconds

    /**
     * Set to true if we don't want the sessions to expire on shutdown
     * @deprecated  Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected boolean mExpireSessionsOnShutdown = true;

    /**
     * The name of this manager
     */
    protected String name;

    /**
     *
     */
    private int mapSendOptions = Channel.SEND_OPTIONS_SYNCHRONIZED_ACK|Channel.SEND_OPTIONS_USE_ACK;

    /**
     * Timeout for RPC messages.
     */
    private long rpcTimeout = DEFAULT_REPL_TIMEOUT;

    /**
     * Flag for whether to terminate this map that failed to start.
     */
    private boolean terminateOnStartFailure = false;

    /**
     * The timeout for a ping message in replication map.
     */
    private long accessTimeout = 5000;

    /**
     * Constructor, just calls super()
     *
     */
    public BackupManager() {
        super();
    }


//******************************************************************************/
//      ClusterManager Interface
//******************************************************************************/

    @Override
    public void messageDataReceived(ClusterMessage msg) {
    }

    /**
     * @deprecated  Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public void setExpireSessionsOnShutdown(boolean expireSessionsOnShutdown)
    {
        mExpireSessionsOnShutdown = expireSessionsOnShutdown;
    }

    /**
     * @deprecated  Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public boolean getExpireSessionsOnShutdown()
    {
        return mExpireSessionsOnShutdown;
    }


    @Override
    public ClusterMessage requestCompleted(String sessionId) {
        if (!getState().isAvailable()) return null;
        LazyReplicatedMap<String,Session> map =
                (LazyReplicatedMap<String,Session>)sessions;
        map.replicate(sessionId,false);
        return null;
    }


//=========================================================================
// OVERRIDE THESE METHODS TO IMPLEMENT THE REPLICATION
//=========================================================================
    @Override
    public void objectMadePrimay(Object key, Object value) {
        if (value!=null && value instanceof DeltaSession) {
            DeltaSession session = (DeltaSession)value;
            synchronized (session) {
                session.access();
                session.setPrimarySession(true);
                session.endAccess();
            }
        }
    }

    @Override
    public Session createEmptySession() {
        return new DeltaSession(this);
    }


    @Override
    public String getName() {
        return this.name;
    }


    /**
     * Start this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * Starts the cluster communication channel, this will connect with the
     * other nodes in the cluster, and request the current session state to be
     * transferred to this node.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {

        super.startInternal();

        try {
            if (cluster == null) throw new LifecycleException(sm.getString("backupManager.noCluster", getName()));
            LazyReplicatedMap<String,Session> map =
                    new LazyReplicatedMap<String,Session>(this,
                            cluster.getChannel(), rpcTimeout, getMapName(),
                            getClassLoaders(), terminateOnStartFailure);
            map.setChannelSendOptions(mapSendOptions);
            map.setAccessTimeout(accessTimeout);
            this.sessions = map;
        }  catch ( Exception x ) {
            log.error(sm.getString("backupManager.startUnable", getName()),x);
            throw new LifecycleException(sm.getString("backupManager.startFailed", getName()),x);
        }
        setState(LifecycleState.STARTING);
    }

    public String getMapName() {
        String name = cluster.getManagerName(getName(),this)+"-"+"map";
        if ( log.isDebugEnabled() ) log.debug("Backup manager, Setting map name to:"+name);
        return name;
    }


    /**
     * Stop this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * This will disconnect the cluster communication channel and stop the
     * listener thread.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void stopInternal() throws LifecycleException {

        if (log.isDebugEnabled())
            log.debug(sm.getString("backupManager.stopped", getName()));

        setState(LifecycleState.STOPPING);

        if (sessions instanceof LazyReplicatedMap) {
            LazyReplicatedMap<String,Session> map =
                    (LazyReplicatedMap<String,Session>)sessions;
            map.breakdown();
        }

        super.stopInternal();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setMapSendOptions(int mapSendOptions) {
        this.mapSendOptions = mapSendOptions;
    }

    public int getMapSendOptions() {
        return mapSendOptions;
    }

    public void setRpcTimeout(long rpcTimeout) {
        this.rpcTimeout = rpcTimeout;
    }

    public long getRpcTimeout() {
        return rpcTimeout;
    }

    public void setTerminateOnStartFailure(boolean terminateOnStartFailure) {
        this.terminateOnStartFailure = terminateOnStartFailure;
    }

    public boolean isTerminateOnStartFailure() {
        return terminateOnStartFailure;
    }

    public long getAccessTimeout() {
        return accessTimeout;
    }

    public void setAccessTimeout(long accessTimeout) {
        this.accessTimeout = accessTimeout;
    }

    @Override
    public String[] getInvalidatedSessions() {
        return new String[0];
    }

    @Override
    public ClusterManager cloneFromTemplate() {
        BackupManager result = new BackupManager();
        clone(result);
        result.mExpireSessionsOnShutdown = mExpireSessionsOnShutdown;
        result.mapSendOptions = mapSendOptions;
        result.rpcTimeout = rpcTimeout;
        result.terminateOnStartFailure = terminateOnStartFailure;
        result.accessTimeout = accessTimeout;
        return result;
    }

    @Override
    public int getActiveSessionsFull() {
        LazyReplicatedMap<String,Session> map =
                (LazyReplicatedMap<String,Session>)sessions;
        return map.sizeFull();
    }

    @Override
    public Set<String> getSessionIdsFull() {
        Set<String> sessionIds = new HashSet<String>();
        LazyReplicatedMap<String,Session> map =
                (LazyReplicatedMap<String,Session>)sessions;
        Iterator<String> keys = map.keySetFull().iterator();
        while (keys.hasNext()) {
            sessionIds.add(keys.next());
        }
        return sessionIds;
    }

}
