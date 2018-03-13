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
package org.apache.catalina.ha.authenticator;

import java.security.Principal;

import org.apache.catalina.Container;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Session;
import org.apache.catalina.SessionListener;
import org.apache.catalina.authenticator.SingleSignOn;
import org.apache.catalina.authenticator.SingleSignOnEntry;
import org.apache.catalina.ha.CatalinaCluster;
import org.apache.catalina.ha.ClusterValve;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.tipis.AbstractReplicatedMap.MapOwner;
import org.apache.catalina.tribes.tipis.ReplicatedMap;
import org.apache.tomcat.util.ExceptionUtils;

/**
 * A <strong>Valve</strong> that supports a "single sign on" user experience on
 * each nodes of a cluster, where the security identity of a user who successfully
 * authenticates to one web application is propagated to other web applications and
 * to other nodes cluster in the same security domain.  For successful use, the following
 * requirements must be met:
 * <ul>
 * <li>This Valve must be configured on the Container that represents a
 *     virtual host (typically an implementation of <code>Host</code>).</li>
 * <li>The <code>Realm</code> that contains the shared user and role
 *     information must be configured on the same Container (or a higher
 *     one), and not overridden at the web application level.</li>
 * <li>The web applications themselves must use one of the standard
 *     Authenticators found in the
 *     <code>org.apache.catalina.authenticator</code> package.</li>
 * </ul>
 *
 * @author Fabien Carrion
 */
public class ClusterSingleSignOn extends SingleSignOn implements ClusterValve, MapOwner {

    /**
     * Descriptive information about this Valve implementation.
     */
    protected static final String info =
        "org.apache.catalina.ha.authenticator.ClusterSingleSignOn";


    // -------------------------------------------------------------- Properties

    private CatalinaCluster cluster = null;



    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {

        return (info);

    }

    @Override
    public CatalinaCluster getCluster() { return cluster; }
    @Override
    public void setCluster(CatalinaCluster cluster) {
        this.cluster = cluster;
    }


    private long rpcTimeout = 15000;
    public long getRpcTimeout() {
        return rpcTimeout;
    }
    public void setRpcTimeout(long rpcTimeout) {
        this.rpcTimeout = rpcTimeout;
    }


    private int mapSendOptions =
            Channel.SEND_OPTIONS_SYNCHRONIZED_ACK | Channel.SEND_OPTIONS_USE_ACK;
    public int getMapSendOptions() {
        return mapSendOptions;
    }
    public void setMapSendOptions(int mapSendOptions) {
        this.mapSendOptions = mapSendOptions;
    }


    private boolean terminateOnStartFailure = false;
    public boolean getTerminateOnStartFailure() {
        return terminateOnStartFailure;
    }

    public void setTerminateOnStartFailure(boolean terminateOnStartFailure) {
        this.terminateOnStartFailure = terminateOnStartFailure;
    }

    private long accessTimeout = 5000;
    public long getAccessTimeout() {
        return accessTimeout;
    }

    public void setAccessTimeout(long accessTimeout) {
        this.accessTimeout = accessTimeout;
    }

    // ---------------------------------------------------- SingleSignOn Methods

    @Override
    protected boolean associate(String ssoId, Session session) {
        boolean result = super.associate(ssoId, session);
        if (result) {
            ((ReplicatedMap<String,SingleSignOnEntry>) cache).replicate(ssoId, true);
        }
        return result;
    }

    @Override
    protected boolean update(String ssoId, Principal principal, String authType,
            String username, String password) {
        boolean result = super.update(ssoId, principal, authType, username, password);
        if (result) {
            ((ReplicatedMap<String,SingleSignOnEntry>) cache).replicate(ssoId, true);
        }
        return result;
    }

    @Override
    protected SessionListener getSessionListener(String ssoId) {
        return new ClusterSingleSignOnListener(ssoId);
    }


    // -------------------------------------------------------- MapOwner Methods

    @Override
    public void objectMadePrimay(Object key, Object value) {
        // NO-OP
    }


    // ------------------------------------------------------- Lifecycle Methods

    /**
     * Start this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {
        
        // Load the cluster component, if any
        try {
            if(cluster == null) {
                Container host = getContainer();
                if(host instanceof Host) {
                    if(host.getCluster() instanceof CatalinaCluster) {
                        setCluster((CatalinaCluster) host.getCluster());
                    }
                }
            }
            if (cluster == null) {
                throw new LifecycleException(
                        "There is no Cluster for ClusterSingleSignOn");
            }

            ClassLoader[] cls = new ClassLoader[] { this.getClass().getClassLoader() };

            ReplicatedMap<String,SingleSignOnEntry> cache =
                    new ReplicatedMap<String,SingleSignOnEntry>(
                    this, cluster.getChannel(), rpcTimeout, cluster.getClusterName() + "-SSO-cache",
                    cls, terminateOnStartFailure);
            cache.setChannelSendOptions(mapSendOptions);
            cache.setAccessTimeout(accessTimeout);
            this.cache = cache;
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            throw new LifecycleException(
                    "ClusterSingleSignOn exception during clusterLoad " + t);
        }

        super.startInternal();
    }


    /**
     * Stop this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void stopInternal() throws LifecycleException {

        super.stopInternal();

        if (getCluster() != null) {
            ((ReplicatedMap<?,?>) cache).breakdown();
        }
    }
}
