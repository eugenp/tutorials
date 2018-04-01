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

import java.io.File;
import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.tribes.ChannelListener;

/**
 * A <b>ClusterDeployer</b> interface allows to plug in and out the
 * different deployment implementations
 *
 * @author Filip Hanik
 */
public interface ClusterDeployer extends ChannelListener {
    /**
     * Descriptive information about this component implementation.
     */
    public String info = "ClusterDeployer/1.0";
    /**
     * Start the cluster deployer, the owning container will invoke this
     * @throws Exception - if failure to start cluster
     */
    public void start() throws Exception;

    /**
     * Stops the cluster deployer, the owning container will invoke this
     * @throws LifecycleException
     */
    public void stop() throws LifecycleException;

    /**
     * Install a new web application, whose web application archive is at the
     * specified URL, into this container and all the other
     * members of the cluster with the specified context name.
     * <p>
     * If this application is successfully installed locally, 
     * a ContainerEvent of type
     * <code>INSTALL_EVENT</code> will be sent to all registered listeners,
     * with the newly created <code>Context</code> as an argument.
     *
     * @param contextName The context name to which this application should
     *  be installed (must be unique)
     * @param webapp    A WAR file or unpacked directory structure containing
     *                  the web application to be installed
     *
     * @exception IllegalArgumentException if the specified context name
     *  is malformed
     * @exception IllegalStateException if the specified context name
     *  is already attached to an existing web application
     * @exception IOException if an input/output error was encountered
     *  during installation
     */
    public void install(String contextName, File webapp) throws IOException;

    /**
     * Remove an existing web application, attached to the specified context
     * name.  If this application is successfully removed, a
     * ContainerEvent of type <code>REMOVE_EVENT</code> will be sent to all
     * registered listeners, with the removed <code>Context</code> as
     * an argument. Deletes the web application war file and/or directory
     * if they exist in the Host's appBase.
     *
     * @param contextName The context name of the application to be removed
     * @param undeploy boolean flag to remove web application from server
     *
     * @exception IllegalArgumentException if the specified context name
     *  is malformed
     * @exception IllegalArgumentException if the specified context name does
     *  not identify a currently installed web application
     * @exception IOException if an input/output error occurs during
     *  removal
     */
    public void remove(String contextName, boolean undeploy) throws IOException;

    /**
     * call from container Background Process
     */
    public void backgroundProcess();
    
    /**
     * Returns the cluster the cluster deployer is associated with
     * @return CatalinaCluster
     */
    public CatalinaCluster getCluster();

    /**
     * Associates the cluster deployer with a cluster
     * @param cluster CatalinaCluster
     */
    public void setCluster(CatalinaCluster cluster);

}
