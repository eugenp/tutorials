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

package org.apache.catalina.ha.deploy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.ha.ClusterDeployer;
import org.apache.catalina.ha.ClusterListener;
import org.apache.catalina.ha.ClusterMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.util.ContextName;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.res.StringManager;


/**
 * <p>
 * A farm war deployer is a class that is able to deploy/undeploy web
 * applications in WAR from within the cluster.
 * </p>
 * Any host can act as the admin, and will have three directories
 * <ul>
 * <li>watchDir - the directory where we watch for changes</li>
 * <li>deployDir - the directory where we install applications</li>
 * <li>tempDir - a temporaryDirectory to store binary data when downloading a
 * war from the cluster</li>
 * </ul>
 * Currently we only support deployment of WAR files since they are easier to
 * send across the wire.
 * 
 * @author Filip Hanik
 * @author Peter Rossbach
 */
public class FarmWarDeployer extends ClusterListener
        implements ClusterDeployer, FileChangeListener {
    /*--Static Variables----------------------------------------*/
    private static final Log log = LogFactory.getLog(FarmWarDeployer.class);
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);

    /**
     * The descriptive information about this implementation.
     */
    private static final String info = "FarmWarDeployer/1.2";

    /*--Instance Variables--------------------------------------*/
    protected boolean started = false;

    protected HashMap<String, FileMessageFactory> fileFactories =
        new HashMap<String, FileMessageFactory>();

    /**
     * Deployment directory.
     */
    protected String deployDir;
    private File deployDirFile = null;

    /**
     * Temporary directory.
     */
    protected String tempDir;
    private File tempDirFile = null;

    /**
     * Watch directory.
     */
    protected String watchDir;
    private File watchDirFile = null;

    protected boolean watchEnabled = false;

    protected WarWatcher watcher = null;

    /**
     * Iteration count for background processing.
     */
    private int count = 0;

    /**
     * Frequency of the Farm watchDir check. Cluster wide deployment will be
     * done once for the specified amount of backgroundProcess calls (ie, the
     * lower the amount, the most often the checks will occur).
     */
    protected int processDeployFrequency = 2;

    /**
     * Path where context descriptors should be deployed.
     */
    protected File configBase = null;

    /**
     * The associated host.
     */
    protected Host host = null;

    /**
     * The host appBase.
     */
    protected File appBase = null;

    /**
     * MBean server.
     */
    protected MBeanServer mBeanServer = null;

    /**
     * The associated deployer ObjectName.
     */
    protected ObjectName oname = null;

    /**
     * The maximum valid time(in seconds) for FileMessageFactory.
     */
    protected int maxValidTime = 5 * 60;

    /*--Constructor---------------------------------------------*/
    public FarmWarDeployer() {
    }

    /**
     * Return descriptive information about this deployer implementation and the
     * corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {

        return (info);

    }

    /*--Logic---------------------------------------------------*/
    @Override
    public void start() throws Exception {
        if (started)
            return;
        Container hcontainer = getCluster().getContainer();
        if(!(hcontainer instanceof Host)) {
            log.error(sm.getString("farmWarDeployer.hostOnly"));
            return ;
        }
        host = (Host) hcontainer;
    
        // Check to correct engine and host setup
        Container econtainer = host.getParent();
        if(!(econtainer instanceof Engine)) {
            log.error(sm.getString("farmWarDeployer.hostParentEngine",
                    host.getName())); 
            return ;
        }
        Engine engine = (Engine) econtainer;
        String hostname = null;
        hostname = host.getName();
        try {
            oname = new ObjectName(engine.getName() + ":type=Deployer,host="
                    + hostname);
        } catch (Exception e) {
            log.error(sm.getString("farmWarDeployer.mbeanNameFail",
                    engine.getName(), hostname),e);
            return;
        }
        if (watchEnabled) {
            watcher = new WarWatcher(this, getWatchDirFile());
            if (log.isInfoEnabled()) {
                log.info(sm.getString(
                        "farmWarDeployer.watchDir", getWatchDir()));
            }
        }

        if (host.getXmlBase()!=null) {
            configBase = getAbsolutePath(host.getXmlBase());
        } else {
            StringBuilder xmlDir = new StringBuilder("conf");
            xmlDir.append('/');
            xmlDir.append(engine.getName());
            xmlDir.append('/');
            xmlDir.append(host.getName());
            configBase = getAbsolutePath(xmlDir.toString());
        }

        // Retrieve the MBean server
        mBeanServer = Registry.getRegistry(null, null).getMBeanServer();

        started = true;
        count = 0;

        getCluster().addClusterListener(this);

        if (log.isInfoEnabled())
            log.info(sm.getString("farmWarDeployer.started"));
    }

    /*
     * stop cluster wide deployments
     * 
     * @see org.apache.catalina.ha.ClusterDeployer#stop()
     */
    @Override
    public void stop() throws LifecycleException {
        started = false;
        getCluster().removeClusterListener(this);
        count = 0;
        if (watcher != null) {
            watcher.clear();
            watcher = null;

        }
        if (log.isInfoEnabled())
            log.info(sm.getString("farmWarDeployer.stopped"));
    }

    public void cleanDeployDir() {
        throw new java.lang.UnsupportedOperationException(sm.getString(
                "farmWarDeployer.notImplemented", "cleanDeployDir()"));
    }

    /**
     * Callback from the cluster, when a message is received, The cluster will
     * broadcast it invoking the messageReceived on the receiver.
     * 
     * @param msg
     *            ClusterMessage - the message received from the cluster
     */
    @Override
    public void messageReceived(ClusterMessage msg) {
        try {
            if (msg instanceof FileMessage) {
                FileMessage fmsg = (FileMessage) msg;
                if (log.isDebugEnabled())
                    log.debug(sm.getString("farmWarDeployer.msgRxDeploy",
                            fmsg.getContextName(), fmsg.getFileName()));
                FileMessageFactory factory = getFactory(fmsg);
                // TODO correct second try after app is in service!
                if (factory.writeMessage(fmsg)) {
                    //last message received war file is completed
                    String name = factory.getFile().getName();
                    if (!name.endsWith(".war"))
                        name = name + ".war";
                    File deployable = new File(getDeployDirFile(), name);
                    try {
                        String contextName = fmsg.getContextName();
                        if (!isServiced(contextName)) {
                            addServiced(contextName);
                            try {
                                remove(contextName);
                                if (!factory.getFile().renameTo(deployable)) {
                                    log.error(sm.getString(
                                            "farmWarDeployer.renameFail",
                                            factory.getFile(), deployable));
                                }
                                check(contextName);
                            } finally {
                                removeServiced(contextName);
                            }
                            if (log.isDebugEnabled())
                                log.debug(sm.getString(
                                        "farmWarDeployer.deployEnd",
                                        contextName));
                        } else
                            log.error(sm.getString(
                                    "farmWarDeployer.servicingDeploy",
                                    contextName, name));
                    } catch (Exception ex) {
                        log.error(ex);
                    } finally {
                        removeFactory(fmsg);
                    }
                }
            } else if (msg instanceof UndeployMessage) {
                try {
                    UndeployMessage umsg = (UndeployMessage) msg;
                    String contextName = umsg.getContextName();
                    if (log.isDebugEnabled())
                        log.debug(sm.getString("farmWarDeployer.msgRxUndeploy",
                                contextName));
                    if (!isServiced(contextName)) {
                        addServiced(contextName);
                        try {
                            remove(contextName);
                        } finally {
                            removeServiced(contextName);
                        }
                        if (log.isDebugEnabled())
                            log.debug(sm.getString(
                                    "farmWarDeployer.undeployEnd",
                                    contextName));
                    } else
                        log.error(sm.getString(
                                "farmWarDeployer.servicingUndeploy",
                                contextName));
                } catch (Exception ex) {
                    log.error(ex);
                }
            }
        } catch (java.io.IOException x) {
            log.error(sm.getString("farmWarDeployer.msgIoe"), x);
        }
    }

    /**
     * create factory for all transported war files
     * 
     * @param msg
     * @return Factory for all app message (war files)
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public synchronized FileMessageFactory getFactory(FileMessage msg)
            throws java.io.FileNotFoundException, java.io.IOException {
        File writeToFile = new File(getTempDirFile(), msg.getFileName());
        FileMessageFactory factory = fileFactories.get(msg.getFileName());
        if (factory == null) {
            factory = FileMessageFactory.getInstance(writeToFile, true);
            factory.setMaxValidTime(maxValidTime);
            fileFactories.put(msg.getFileName(), factory);
        }
        return factory;
    }

    /**
     * Remove file (war) from messages)
     * 
     * @param msg
     */
    public void removeFactory(FileMessage msg) {
        fileFactories.remove(msg.getFileName());
    }

    /**
     * Before the cluster invokes messageReceived the cluster will ask the
     * receiver to accept or decline the message, In the future, when messages
     * get big, the accept method will only take a message header
     * 
     * @param msg
     *            ClusterMessage
     * @return boolean - returns true to indicate that messageReceived should be
     *         invoked. If false is returned, the messageReceived method will
     *         not be invoked.
     */
    @Override
    public boolean accept(ClusterMessage msg) {
        return (msg instanceof FileMessage) || (msg instanceof UndeployMessage);
    }

    /**
     * Install a new web application, whose web application archive is at the
     * specified URL, into this container and all the other members of the
     * cluster with the specified context name.
     * <p>
     * If this application is successfully installed locally, a ContainerEvent
     * of type <code>INSTALL_EVENT</code> will be sent to all registered
     * listeners, with the newly created <code>Context</code> as an argument.
     * 
     * @param contextName
     *            The context name to which this application should be installed
     *            (must be unique)
     * @param webapp
     *            A WAR file or unpacked directory structure containing the web
     *            application to be installed
     * 
     * @exception IllegalArgumentException
     *                if the specified context name is malformed
     * @exception IllegalStateException
     *                if the specified context name is already deployed
     * @exception IOException
     *                if an input/output error was encountered during
     *                installation
     */
    @Override
    public void install(String contextName, File webapp) throws IOException {
        Member[] members = getCluster().getMembers();
        if (members.length == 0) return;

        Member localMember = getCluster().getLocalMember();
        FileMessageFactory factory =
            FileMessageFactory.getInstance(webapp, false);
        FileMessage msg = new FileMessage(localMember, webapp.getName(),
                contextName);
        if(log.isDebugEnabled())
            log.debug(sm.getString("farmWarDeployer.sendStart", contextName,
                    webapp));
        msg = factory.readMessage(msg);
        while (msg != null) {
            for (int i = 0; i < members.length; i++) {
                if (log.isDebugEnabled())
                    log.debug(sm.getString("farmWarDeployer.sendFragment",
                            contextName, webapp, members[i]));
                getCluster().send(msg, members[i]);
            }
            msg = factory.readMessage(msg);
        }
        if(log.isDebugEnabled())
            log.debug(sm.getString(
                    "farmWarDeployer.sendEnd", contextName, webapp));
    }

    /**
     * Remove an existing web application, attached to the specified context
     * name. If this application is successfully removed, a ContainerEvent of
     * type <code>REMOVE_EVENT</code> will be sent to all registered
     * listeners, with the removed <code>Context</code> as an argument.
     * Deletes the web application war file and/or directory if they exist in
     * the Host's appBase.
     * 
     * @param contextName
     *            The context name of the application to be removed
     * @param undeploy
     *            boolean flag to remove web application from server
     * 
     * @exception IllegalArgumentException
     *                if the specified context name is malformed
     * @exception IllegalArgumentException
     *                if the specified context name does not identify a
     *                currently installed web application
     * @exception IOException
     *                if an input/output error occurs during removal
     */
    @Override
    public void remove(String contextName, boolean undeploy)
            throws IOException {
        if (getCluster().getMembers().length > 0) {
            if (log.isInfoEnabled())
                log.info(sm.getString("farmWarDeployer.removeStart", contextName));
            Member localMember = getCluster().getLocalMember();
            UndeployMessage msg = new UndeployMessage(localMember, System
                    .currentTimeMillis(), "Undeploy:" + contextName + ":"
                    + System.currentTimeMillis(), contextName, undeploy);
            if (log.isDebugEnabled())
                log.debug(sm.getString("farmWarDeployer.removeTxMsg", contextName));
            cluster.send(msg);
        }
        // remove locally
        if (undeploy) {
            try {
                if (!isServiced(contextName)) {
                    addServiced(contextName);
                    try {
                        remove(contextName);
                    } finally {
                        removeServiced(contextName);
                    }
                } else
                    log.error(sm.getString("farmWarDeployer.removeFailRemote",
                            contextName));

            } catch (Exception ex) {
                log.error(sm.getString("farmWarDeployer.removeFailLocal",
                        contextName), ex);
            }
        }

    }

    /*
     * Modification from watchDir war detected!
     * 
     * @see org.apache.catalina.ha.deploy.FileChangeListener#fileModified(File)
     */
    @Override
    public void fileModified(File newWar) {
        try {
            File deployWar = new File(getDeployDirFile(), newWar.getName());
            ContextName cn = new ContextName(deployWar.getName(), true);
            if (deployWar.exists() && deployWar.lastModified() > newWar.lastModified()) {
                if (log.isInfoEnabled())
                    log.info(sm.getString("farmWarDeployer.alreadyDeployed", cn.getName()));
                return;
            }
            if (log.isInfoEnabled())
                log.info(sm.getString("farmWarDeployer.modInstall",
                        cn.getName(), deployWar.getAbsolutePath()));
            // install local
            if (!isServiced(cn.getName())) {
                addServiced(cn.getName());
                try {
                    copy(newWar, deployWar);
                    check(cn.getName());
                } finally {
                    removeServiced(cn.getName());
                }
            } else {
                log.error(sm.getString("farmWarDeployer.servicingDeploy",
                        cn.getName(), deployWar.getName()));
            }
            install(cn.getName(), deployWar);
        } catch (Exception x) {
            log.error(sm.getString("farmWarDeployer.modInstallFail"), x);
        }
    }

    /*
     * War remove from watchDir
     * 
     * @see org.apache.catalina.ha.deploy.FileChangeListener#fileRemoved(File)
     */
    @Override
    public void fileRemoved(File removeWar) {
        try {
            ContextName cn = new ContextName(removeWar.getName(), true);
            if (log.isInfoEnabled())
                log.info(sm.getString("farmWarDeployer.removeLocal",
                        cn.getName()));
            remove(cn.getName(), true);
        } catch (Exception x) {
            log.error(sm.getString("farmWarDeployer.removeLocalFail"), x);
        }
    }

    /**
     * Return a File object representing the "application root" directory for
     * our associated Host.
     */
    protected File getAppBase() {

        if (appBase != null) {
            return appBase;
        }

        File file = new File(host.getAppBase());
        if (!file.isAbsolute())
            file = new File(System.getProperty(Globals.CATALINA_BASE_PROP), host
                    .getAppBase());
        try {
            appBase = file.getCanonicalFile();
        } catch (IOException e) {
            appBase = file;
        }
        return (appBase);

    }

    /**
     * Invoke the remove method on the deployer.
     */
    protected void remove(String contextName) throws Exception {
        // TODO Handle remove also work dir content !
        // Stop the context first to be nicer
        Context context = (Context) host.findChild(contextName);
        if (context != null) {
            if(log.isDebugEnabled())
                log.debug(sm.getString("farmWarDeployer.undeployLocal",
                        contextName));
            context.stop();
            String baseName = context.getBaseName();
            File war = new File(getAppBase(), baseName + ".war");
            File dir = new File(getAppBase(), baseName);
            File xml = new File(configBase, baseName + ".xml");
            if (war.exists()) {
                if (!war.delete()) {
                    log.error(sm.getString("farmWarDeployer.deleteFail", war));
                }
            } else if (dir.exists()) {
                undeployDir(dir);
            } else {
                if (!xml.delete()) {
                    log.error(sm.getString("farmWarDeployer.deleteFail", xml));
                }
            }
            // Perform new deployment and remove internal HostConfig state
            check(contextName);
        }

    }

    /**
     * Delete the specified directory, including all of its contents and
     * subdirectories recursively.
     * 
     * @param dir
     *            File object representing the directory to be deleted
     */
    protected void undeployDir(File dir) {

        String files[] = dir.list();
        if (files == null) {
            files = new String[0];
        }
        for (int i = 0; i < files.length; i++) {
            File file = new File(dir, files[i]);
            if (file.isDirectory()) {
                undeployDir(file);
            } else {
                if (!file.delete()) {
                    log.error(sm.getString("farmWarDeployer.deleteFail", file));
                }
            }
        }
        if (!dir.delete()) {
            log.error(sm.getString("farmWarDeployer.deleteFail", dir));
        }
    }

    /*
     * Call watcher to check for deploy changes
     * 
     * @see org.apache.catalina.ha.ClusterDeployer#backgroundProcess()
     */
    @Override
    public void backgroundProcess() {
        if (started) {
            if (watchEnabled) {
                count = (count + 1) % processDeployFrequency;
                if (count == 0) {
                    watcher.check();
                }
            }
            removeInvalidFileFactories();
        }

    }

    /*--Deployer Operations ------------------------------------*/

    /**
     * Invoke the check method on the deployer.
     */
    protected void check(String name) throws Exception {
        String[] params = { name };
        String[] signature = { "java.lang.String" };
        mBeanServer.invoke(oname, "check", params, signature);
    }

    /**
     * Invoke the check method on the deployer.
     */
    protected boolean isServiced(String name) throws Exception {
        String[] params = { name };
        String[] signature = { "java.lang.String" };
        Boolean result = (Boolean) mBeanServer.invoke(oname, "isServiced",
                params, signature);
        return result.booleanValue();
    }

    /**
     * Invoke the check method on the deployer.
     */
    protected void addServiced(String name) throws Exception {
        String[] params = { name };
        String[] signature = { "java.lang.String" };
        mBeanServer.invoke(oname, "addServiced", params, signature);
    }

    /**
     * Invoke the check method on the deployer.
     */
    protected void removeServiced(String name) throws Exception {
        String[] params = { name };
        String[] signature = { "java.lang.String" };
        mBeanServer.invoke(oname, "removeServiced", params, signature);
    }

    /*--Instance Getters/Setters--------------------------------*/
    @Override
    public boolean equals(Object listener) {
        return super.equals(listener);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getDeployDir() {
        return deployDir;
    }

    public File getDeployDirFile() {
        if (deployDirFile != null) return deployDirFile;

        File dir = getAbsolutePath(getDeployDir());
        this.deployDirFile = dir;
        return dir;
    }

    public void setDeployDir(String deployDir) {
        this.deployDir = deployDir;
    }

    public String getTempDir() {
        return tempDir;
    }

    public File getTempDirFile() {
        if (tempDirFile != null) return tempDirFile;

        File dir = getAbsolutePath(getTempDir());
        this.tempDirFile = dir;
        return dir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public String getWatchDir() {
        return watchDir;
    }

    public File getWatchDirFile() {
        if (watchDirFile != null) return watchDirFile;

        File dir = getAbsolutePath(getWatchDir());
        this.watchDirFile = dir;
        return dir;
    }

    public void setWatchDir(String watchDir) {
        this.watchDir = watchDir;
    }

    public boolean isWatchEnabled() {
        return watchEnabled;
    }

    public boolean getWatchEnabled() {
        return watchEnabled;
    }

    public void setWatchEnabled(boolean watchEnabled) {
        this.watchEnabled = watchEnabled;
    }

    /**
     * Return the frequency of watcher checks.
     */
    public int getProcessDeployFrequency() {

        return (this.processDeployFrequency);

    }

    /**
     * Set the watcher checks frequency.
     * 
     * @param processExpiresFrequency
     *            the new manager checks frequency
     */
    public void setProcessDeployFrequency(int processExpiresFrequency) {

        if (processExpiresFrequency <= 0) {
            return;
        }
        this.processDeployFrequency = processExpiresFrequency;
    }

    public int getMaxValidTime() {
        return maxValidTime;
    }

    public void setMaxValidTime(int maxValidTime) {
        this.maxValidTime = maxValidTime;
    }

    /**
     * Copy a file to the specified temp directory.
     * @param from copy from temp
     * @param to   to host appBase directory
     * @return true, copy successful
     */
    protected boolean copy(File from, File to) {
        java.io.FileInputStream is = null;
        java.io.FileOutputStream os = null;
        try {
            if (!to.exists()) {
                if (!to.createNewFile()) {
                    log.error(sm.getString("fileNewFail", to));
                    return false;
                }
            }
            is = new java.io.FileInputStream(from);
            os = new java.io.FileOutputStream(to, false);
            byte[] buf = new byte[4096];
            while (true) {
                int len = is.read(buf);
                if (len < 0)
                    break;
                os.write(buf, 0, len);
            }
        } catch (IOException e) {
            log.error(sm.getString("farmWarDeployer.fileCopyFail",
                    from, to), e);
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.debug(sm.getString(
                            "farmWarDeployer.streamCannotBeClosed",
                            "InputStream", from), e);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.debug(sm.getString(
                            "farmWarDeployer.streamCannotBeClosed",
                            "OutputStream", to), e);
                }
            }
        }
        return true;
    }

    protected void removeInvalidFileFactories() {
        String[] fileNames = fileFactories.keySet().toArray(new String[0]);
        for (String fileName : fileNames) {
            FileMessageFactory factory = fileFactories.get(fileName);
            if (!factory.isValid()) {
                fileFactories.remove(fileName);
            }
        }
    }

    private File getAbsolutePath(String path) {
        File dir = new File(path);
        File base = new File(System.getProperty(Globals.CATALINA_BASE_PROP));
        if (!dir.isAbsolute()) {
            dir = new File(base, dir.getPath());
        }
        try {
            dir = dir.getCanonicalFile();
        } catch (IOException e) {// ignore
        }
        return dir;
    }
}
