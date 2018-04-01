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

package org.apache.catalina.ha;

import java.util.Map;

import org.apache.catalina.Cluster;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Manager;
import org.apache.catalina.Valve;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.Member;
import org.apache.juli.logging.Log;



/**
 * A <b>CatalinaCluster</b> interface allows to plug in and out the 
 * different cluster implementations
 *
 * @author Filip Hanik
 */
public interface CatalinaCluster extends Cluster {
    // ----------------------------------------------------- Instance Variables

    /**
     * Descriptive information about this component implementation.
     */
    public String info = "CatalinaCluster/2.0";
    
    /**
     * Start the cluster, the owning container will invoke this
     * @throws Exception - if failure to start cluster
     */
    public void start() throws Exception;
    
    /**
     * Stops the cluster, the owning container will invoke this
     * @throws LifecycleException
     */
    public void stop() throws LifecycleException;
    
    /**
     * Returns the associates logger with this cluster.
     *
     * @return Log
     */
    public Log getLogger();
    
    /**
     * Sends a message to all the members in the cluster
     * @param msg ClusterMessage
     */
    public void send(ClusterMessage msg);
    
    /**
     * Sends a message to a specific member in the cluster.
     *
     * @param msg ClusterMessage
     * @param dest Member
     */
    public void send(ClusterMessage msg, Member dest);

    /**
     * Returns that cluster has members.
     */
    public boolean hasMembers();

    /**
     * Returns all the members currently participating in the cluster.
     *
     * @return Member[]
     */
    public Member[] getMembers();
    
    /**
     * Return the member that represents this node.
     *
     * @return Member
     */
    public Member getLocalMember();
    
    public void addValve(Valve valve);
    
    public void addClusterListener(ClusterListener listener);
    
    public void removeClusterListener(ClusterListener listener);
    
    public void setClusterDeployer(ClusterDeployer deployer);
    
    public ClusterDeployer getClusterDeployer();
    
    /**
     * @return The map of managers
     */
    public Map<String,ClusterManager> getManagers();

    public Manager getManager(String name);
    public String getManagerName(String name, Manager manager);
    public Valve[] getValves();
    
    public void setChannel(Channel channel);
    public Channel getChannel();
    

}
