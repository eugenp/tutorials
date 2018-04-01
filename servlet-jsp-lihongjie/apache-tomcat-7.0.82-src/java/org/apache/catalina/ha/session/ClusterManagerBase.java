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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.SessionIdGenerator;
import org.apache.catalina.Valve;
import org.apache.catalina.ha.CatalinaCluster;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.tcp.ReplicationValve;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.tribes.io.ReplicationStream;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * 
 * @author Filip Hanik
 */
public abstract class ClusterManagerBase extends ManagerBase
        implements ClusterManager {

    private final Log log = LogFactory.getLog(ClusterManagerBase.class);

    /**
     * A reference to the cluster
     */
    protected CatalinaCluster cluster = null;

    /**
     * Should listeners be notified?
     */
    private boolean notifyListenersOnReplication = true;

    /**
     * cached replication valve cluster container!
     */
    private volatile ReplicationValve replicationValve = null ;

    /**
     * send all actions of session attributes.
     */
    private boolean recordAllActions = false;

    @Override
    public CatalinaCluster getCluster() {
        return cluster;
    }

    @Override
    public void setCluster(CatalinaCluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public boolean isNotifyListenersOnReplication() {
        return notifyListenersOnReplication;
    }

    public void setNotifyListenersOnReplication(boolean notifyListenersOnReplication) {
        this.notifyListenersOnReplication = notifyListenersOnReplication;
    }

    /**
     * Return the string pattern used for including session attributes
     * to replication.
     *
     * @return the sessionAttributeFilter
     *
     * @deprecated Use {@link #getSessionAttributeNameFilter()}. Will be removed
     *             in Tomcat 9.0.x
     */
    @Deprecated
    public String getSessionAttributeFilter() {
        return getSessionAttributeNameFilter();
    }

    /**
     * Set the pattern used for including session attributes to replication.
     * If not set, all session attributes will be eligible for replication.
     * <p>
     * E.g. <code>^(userName|sessionHistory)$</code>
     * </p>
     *
     * @param sessionAttributeFilter
     *            the filter name pattern to set
     *
     * @deprecated Use {@link #setSessionAttributeNameFilter(String)}. Will be
     *             removed in Tomcat 9.0.x
     */
    @Deprecated
    public void setSessionAttributeFilter(String sessionAttributeFilter) {
        setSessionAttributeNameFilter(sessionAttributeFilter);
    }

    public boolean isRecordAllActions() {
        return recordAllActions;
    }

    public void setRecordAllActions(boolean recordAllActions) {
        this.recordAllActions = recordAllActions;
    }

    /**
     * Check whether the given session attribute should be distributed based on
     * attribute name only.
     *
     * @return true if the attribute should be distributed
     *
     * @deprecated Use {@link #willAttributeDistribute(String, Object)}. Will be
     *             removed in Tomcat 9.0.x
     */
    @Deprecated
    public boolean willAttributeDistribute(String name) {
        return willAttributeDistribute(name, null);
    }


    public static ClassLoader[] getClassLoaders(Container container) {
        Loader loader = null;
        ClassLoader classLoader = null;
        if (container != null) loader = container.getLoader();
        if (loader != null) classLoader = loader.getClassLoader();
        else classLoader = Thread.currentThread().getContextClassLoader();
        if ( classLoader == Thread.currentThread().getContextClassLoader() ) {
            return new ClassLoader[] {classLoader};
        } else {
            return new ClassLoader[] {classLoader,Thread.currentThread().getContextClassLoader()};
        }
    }


    public ClassLoader[] getClassLoaders() {
        return getClassLoaders(container);
    }

    @Override
    public ReplicationStream getReplicationStream(byte[] data) throws IOException {
        return getReplicationStream(data,0,data.length);
    }

    @Override
    public ReplicationStream getReplicationStream(byte[] data, int offset, int length) throws IOException {
        ByteArrayInputStream fis = new ByteArrayInputStream(data, offset, length);
        return new ReplicationStream(fis, getClassLoaders());
    }    


    //  ---------------------------------------------------- persistence handler

    /**
     * {@link org.apache.catalina.Manager} implementations that also implement
     * {@link ClusterManager} do not support local session persistence.
     */
    @Override
    public void load() {
        // NOOP 
    }

    /**
     * {@link org.apache.catalina.Manager} implementations that also implement
     * {@link ClusterManager} do not support local session persistence.
     */
    @Override
    public void unload() {
        // NOOP
    }

    protected void clone(ClusterManagerBase copy) {
        copy.setName("Clone-from-" + getName());
        copy.setMaxActiveSessions(getMaxActiveSessions());
        copy.setProcessExpiresFrequency(getProcessExpiresFrequency());
        copy.setNotifyListenersOnReplication(isNotifyListenersOnReplication());
        copy.setSessionAttributeNameFilter(getSessionAttributeNameFilter());
        copy.setSessionAttributeValueClassNameFilter(getSessionAttributeValueClassNameFilter());
        copy.setWarnOnSessionAttributeFilterFailure(getWarnOnSessionAttributeFilterFailure());
        copy.setSecureRandomClass(getSecureRandomClass());
        copy.setSecureRandomProvider(getSecureRandomProvider());
        copy.setSecureRandomAlgorithm(getSecureRandomAlgorithm());
        if (getSessionIdGenerator() != null) {
            try {
                SessionIdGenerator copyIdGenerator = sessionIdGeneratorClass.newInstance();
                copyIdGenerator.setSessionIdLength(getSessionIdGenerator().getSessionIdLength());
                copyIdGenerator.setJvmRoute(getSessionIdGenerator().getJvmRoute());
                copy.setSessionIdGenerator(copyIdGenerator);
            } catch (InstantiationException e) {
             // Ignore
            } catch (IllegalAccessException e) {
             // Ignore
            }
        }
        copy.setRecordAllActions(isRecordAllActions());
    }

    /**
     * Register cross context session at replication valve thread local
     * @param session cross context session
     */
    protected void registerSessionAtReplicationValve(DeltaSession session) {
        if(replicationValve == null) {
            CatalinaCluster cluster = getCluster() ;
            if(cluster != null) {
                Valve[] valves = cluster.getValves();
                if(valves != null && valves.length > 0) {
                    for(int i=0; replicationValve == null && i < valves.length ; i++ ){
                        if(valves[i] instanceof ReplicationValve) replicationValve = (ReplicationValve)valves[i] ;
                    }//for

                    if(replicationValve == null && log.isDebugEnabled()) {
                        log.debug("no ReplicationValve found for CrossContext Support");
                    }//endif 
                }//end if
            }//endif
        }//end if
        if(replicationValve != null) {
            replicationValve.registerReplicationSession(session);
        }
    }

    @Override
    protected void startInternal() throws LifecycleException {
        super.startInternal();
        if (getCluster() == null) {
            Cluster cluster = getContainer().getCluster();
            if (cluster instanceof CatalinaCluster) {
                setCluster((CatalinaCluster)cluster);
            }
        }
        if (cluster != null) cluster.registerManager(this);
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        if (cluster != null) cluster.removeManager(this);
        replicationValve = null;
        super.stopInternal();
    }
}
