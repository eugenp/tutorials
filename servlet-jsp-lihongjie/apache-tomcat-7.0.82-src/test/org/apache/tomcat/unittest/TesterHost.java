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
package org.apache.tomcat.unittest;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

import javax.management.ObjectName;
import javax.naming.directory.DirContext;
import javax.servlet.ServletException;

import org.apache.catalina.AccessLog;
import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.juli.logging.Log;

public class TesterHost implements Host {

    @Override
    public Log getLogger() {
        return null;
    }

    @Override
    public ObjectName getObjectName() {
        return null;
    }

    @Override
    public Pipeline getPipeline() {
        return null;
    }

    @Override
    public Cluster getCluster() {
        return null;
    }

    @Override
    public void setCluster(Cluster cluster) {
        // NO-OP
    }

    @Override
    public int getBackgroundProcessorDelay() {
        return 0;
    }

    @Override
    public void setBackgroundProcessorDelay(int delay) {
        // NO-OP
    }

    private String name = "TestHost";
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container container) {
        // NO-OP
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {
        // NO-OP
    }

    @Override
    public Realm getRealm() {
        return null;
    }

    @Override
    public void setRealm(Realm realm) {
        // NO-OP
    }

    @Override
    public void backgroundProcess() {
        // NO-OP
    }

    @Override
    public void addChild(Container child) {
        // NO-OP
    }

    @Override
    public void addContainerListener(ContainerListener listener) {
        // NO-OP
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        // NO-OP
    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return null;
    }

    @Override
    public ContainerListener[] findContainerListeners() {
        return null;
    }

    @Override
    public void removeChild(Container child) {
        // NO-OP
    }

    @Override
    public void removeContainerListener(ContainerListener listener) {
        // NO-OP
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        // NO-OP
    }

    @Override
    public void fireContainerEvent(String type, Object data) {
        // NO-OP
    }

    @Override
    public void logAccess(Request request, Response response, long time, boolean useDefault) {
        // NO-OP
    }

    @Override
    public AccessLog getAccessLog() {
        return null;
    }

    @Override
    public int getStartStopThreads() {
        return 0;
    }

    @Override
    public void setStartStopThreads(int startStopThreads) {
        // NO-OP
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        // NO-OP
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return null;
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        // NO-OP
    }

    @Override
    public void init() throws LifecycleException {
        // NO-OP
    }

    @Override
    public void start() throws LifecycleException {
        // NO-OP
    }

    @Override
    public void stop() throws LifecycleException {
        // NO-OP
    }

    @Override
    public void destroy() throws LifecycleException {
        // NO-OP
    }

    @Override
    public LifecycleState getState() {
        return null;
    }

    @Override
    public String getStateName() {
        return null;
    }

    @Override
    public String getXmlBase() {
        return null;
    }

    @Override
    public void setXmlBase(String xmlBase) {
        // NO-OP
    }

    @Override
    public String getAppBase() {
        return null;
    }

    @Override
    public void setAppBase(String appBase) {
        // NO-OP
    }

    @Override
    public boolean getAutoDeploy() {
        return false;
    }

    @Override
    public void setAutoDeploy(boolean autoDeploy) {
        // NO-OP
    }

    @Override
    public String getConfigClass() {
        return null;
    }

    @Override
    public void setConfigClass(String configClass) {
        // NO-OP
    }

    @Override
    public boolean getDeployOnStartup() {
        return false;
    }

    @Override
    public void setDeployOnStartup(boolean deployOnStartup) {
        // NO-OP
    }

    @Override
    public String getDeployIgnore() {
        return null;
    }

    @Override
    public Pattern getDeployIgnorePattern() {
        return null;
    }

    @Override
    public void setDeployIgnore(String deployIgnore) {
        // NO-OP
    }

    @Override
    public ExecutorService getStartStopExecutor() {
        return null;
    }

    @Override
    public boolean getCreateDirs() {
        return false;
    }

    @Override
    public void setCreateDirs(boolean createDirs) {
        // NO-OP
    }

    @Override
    public boolean getUndeployOldVersions() {
        return false;
    }

    @Override
    public void setUndeployOldVersions(boolean undeployOldVersions) {
        // NO-OP
    }

    @Override
    public void addAlias(String alias) {
        // NO-OP
    }

    @Override
    public String[] findAliases() {
        return null;
    }

    @Override
    public void removeAlias(String alias) {
        // NO-OP
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        return null;
    }

    @Override
    public void setLoader(Loader loader) {
        // NO-OP
    }

    @Override
    public Manager getManager() {
        return null;
    }

    @Override
    public void setManager(Manager manager) {
        // NO-OP
    }

    @Override
    public Object getMappingObject() {
        return null;
    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext resources) {
        // NO-OP
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        // NO-OP
    }
}
