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
package org.apache.catalina.startup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.ObjectName;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.DistributedManager;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Manager;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.security.DeployXmlPermission;
import org.apache.catalina.util.ContextName;
import org.apache.catalina.util.IOTools;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.buf.UriUtil;
import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.res.StringManager;


/**
 * Startup event listener for a <b>Host</b> that configures the properties
 * of that Host, and the associated defined contexts.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class HostConfig
    implements LifecycleListener {

    private static final Log log = LogFactory.getLog( HostConfig.class );

    /**
     * The resolution, in milliseconds, of file modification times.
     */
    protected static final long FILE_MODIFICATION_RESOLUTION_MS = 1000;


    // ----------------------------------------------------- Instance Variables

    /**
     * App base.
     */
    protected File appBase = null;


    /**
     * Config base.
     */
    protected File configBase = null;


    /**
     * The Java class name of the Context configuration class we should use.
     * @deprecated Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected String configClass = "org.apache.catalina.startup.ContextConfig";


    /**
     * The Java class name of the Context implementation we should use.
     */
    protected String contextClass = "org.apache.catalina.core.StandardContext";


    /**
     * The Host we are associated with.
     */
    protected Host host = null;


    /**
     * The JMX ObjectName of this component.
     */
    protected ObjectName oname = null;


    /**
     * The string resources for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * Should we deploy XML Context config files packaged with WAR files and
     * directories?
     */
    protected boolean deployXML = false;


    /**
     * Should XML files be copied to
     * $CATALINA_BASE/conf/&lt;engine&gt;/&lt;host&gt; by default when
     * a web application is deployed?
     */
    protected boolean copyXML = false;


    /**
     * Should we unpack WAR files when auto-deploying applications in the
     * <code>appBase</code> directory?
     */
    protected boolean unpackWARs = false;


    /**
     * Map of deployed applications.
     */
    protected Map<String, DeployedApplication> deployed =
        new ConcurrentHashMap<String, DeployedApplication>();


    /**
     * List of applications which are being serviced, and shouldn't be
     * deployed/undeployed/redeployed at the moment.
     */
    protected ArrayList<String> serviced = new ArrayList<String>();


    /**
     * The <code>Digester</code> instance used to parse context descriptors.
     */
    protected Digester digester = createDigester(contextClass);
    private final Object digesterLock = new Object();

    /**
     * The list of Wars in the appBase to be ignored because they are invalid
     * (e.g. contain /../ sequences).
     */
    protected Set<String> invalidWars = new HashSet<String>();

    // ------------------------------------------------------------- Properties


    /**
     * Return the Context configuration class name.
     * @deprecated Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public String getConfigClass() {

        return (this.configClass);

    }


    /**
     * Set the Context configuration class name.
     *
     * @param configClass The new Context configuration class name.
     * @deprecated Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public void setConfigClass(String configClass) {

        this.configClass = configClass;

    }


    /**
     * Return the Context implementation class name.
     */
    public String getContextClass() {

        return (this.contextClass);

    }


    /**
     * Set the Context implementation class name.
     *
     * @param contextClass The new Context implementation class name.
     */
    public void setContextClass(String contextClass) {

        String oldContextClass = this.contextClass;
        this.contextClass = contextClass;

        if (!oldContextClass.equals(contextClass)) {
            synchronized (digesterLock) {
                digester = createDigester(getContextClass());
            }
        }
    }


    /**
     * Return the deploy XML config file flag for this component.
     */
    public boolean isDeployXML() {

        return (this.deployXML);

    }


    /**
     * Set the deploy XML config file flag for this component.
     *
     * @param deployXML The new deploy XML flag
     */
    public void setDeployXML(boolean deployXML) {
        this.deployXML = deployXML;
    }


    private boolean isDeployThisXML(File docBase, ContextName cn) {
        boolean deployThisXML = isDeployXML();
        if (Globals.IS_SECURITY_ENABLED && !deployThisXML) {
            // When running under a SecurityManager, deployXML may be overridden
            // on a per Context basis by the granting of a specific permission
            Policy currentPolicy = Policy.getPolicy();
            if (currentPolicy != null) {
                URL contextRootUrl;
                try {
                    contextRootUrl = docBase.toURI().toURL();
                    CodeSource cs = new CodeSource(contextRootUrl, (Certificate[]) null);
                    PermissionCollection pc = currentPolicy.getPermissions(cs);
                    Permission p = new DeployXmlPermission(cn.getBaseName());
                    if (pc.implies(p)) {
                        deployThisXML = true;
                    }
                } catch (MalformedURLException e) {
                    // Should never happen
                    log.warn("hostConfig.docBaseUrlInvalid", e);
                }
            }
        }

        return deployThisXML;
    }


    /**
     * Return the copy XML config file flag for this component.
     */
    public boolean isCopyXML() {

        return (this.copyXML);

    }


    /**
     * Set the copy XML config file flag for this component.
     *
     * @param copyXML The new copy XML flag
     */
    public void setCopyXML(boolean copyXML) {

        this.copyXML= copyXML;

    }


    /**
     * Return the unpack WARs flag.
     */
    public boolean isUnpackWARs() {

        return (this.unpackWARs);

    }


    /**
     * Set the unpack WARs flag.
     *
     * @param unpackWARs The new unpack WARs flag
     */
    public void setUnpackWARs(boolean unpackWARs) {

        this.unpackWARs = unpackWARs;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Process the START event for an associated Host.
     *
     * @param event The lifecycle event that has occurred
     */
    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        // Identify the host we are associated with
        try {
            host = (Host) event.getLifecycle();
            if (host instanceof StandardHost) {
                setCopyXML(((StandardHost) host).isCopyXML());
                setDeployXML(((StandardHost) host).isDeployXML());
                setUnpackWARs(((StandardHost) host).isUnpackWARs());
                setContextClass(((StandardHost) host).getContextClass());
            }
        } catch (ClassCastException e) {
            log.error(sm.getString("hostConfig.cce", event.getLifecycle()), e);
            return;
        }

        // Process the event that has occurred
        if (event.getType().equals(Lifecycle.PERIODIC_EVENT)) {
            check();
        } else if (event.getType().equals(Lifecycle.BEFORE_START_EVENT)) {
            beforeStart();
        } else if (event.getType().equals(Lifecycle.START_EVENT)) {
            start();
        } else if (event.getType().equals(Lifecycle.STOP_EVENT)) {
            stop();
        }
    }


    /**
     * Add a serviced application to the list.
     */
    public synchronized void addServiced(String name) {
        serviced.add(name);
    }


    /**
     * Is application serviced ?
     * @return state of the application
     */
    public synchronized boolean isServiced(String name) {
        return (serviced.contains(name));
    }


    /**
     * Removed a serviced application from the list.
     */
    public synchronized void removeServiced(String name) {
        serviced.remove(name);
    }


    /**
     * Get the instant where an application was deployed.
     * @return 0L if no application with that name is deployed, or the instant
     * on which the application was deployed
     */
    public long getDeploymentTime(String name) {
        DeployedApplication app = deployed.get(name);
        if (app == null) {
            return 0L;
        }

        return app.timestamp;
    }


    /**
     * Has the specified application been deployed? Note applications defined
     * in server.xml will not have been deployed.
     * @return <code>true</code> if the application has been deployed and
     * <code>false</code> if the application has not been deployed or does not
     * exist
     */
    public boolean isDeployed(String name) {
        DeployedApplication app = deployed.get(name);
        if (app == null) {
            return false;
        }

        return true;
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Create the digester which will be used to parse context config files.
     */
    protected static Digester createDigester(String contextClassName) {
        Digester digester = new Digester();
        digester.setValidating(false);
        // Add object creation rule
        digester.addObjectCreate("Context", contextClassName, "className");
        // Set the properties on that object (it doesn't matter if extra
        // properties are set)
        digester.addSetProperties("Context");
        return (digester);
    }

    protected File returnCanonicalPath(String path) {
        File file = new File(path);
        File base = new File(System.getProperty(Globals.CATALINA_BASE_PROP));
        if (!file.isAbsolute())
            file = new File(base,path);
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            return file;
        }
    }


    /**
     * Return a File object representing the "application root" directory
     * for our associated Host.
     */
    protected File appBase() {

        if (appBase != null) {
            return appBase;
        }

        appBase = returnCanonicalPath(host.getAppBase());
        return appBase;

    }


    /**
     * Return a File object representing the "configuration root" directory
     * for our associated Host.
     */
    protected File configBase() {

        if (configBase != null) {
            return configBase;
        }

        if (host.getXmlBase()!=null) {
            configBase = returnCanonicalPath(host.getXmlBase());
        } else {
            StringBuilder xmlDir = new StringBuilder("conf");
            Container parent = host.getParent();
            if (parent instanceof Engine) {
                xmlDir.append('/');
                xmlDir.append(parent.getName());
            }
            xmlDir.append('/');
            xmlDir.append(host.getName());
            configBase = returnCanonicalPath(xmlDir.toString());
        }
        return (configBase);

    }

    /**
     * Get the name of the configBase.
     * For use with JMX management.
     */
    public String getConfigBaseName() {
        return configBase().getAbsolutePath();
    }


    /**
     * Deploy applications for any directories or WAR files that are found
     * in our "application root" directory.
     */
    protected void deployApps() {

        File appBase = appBase();
        File configBase = configBase();
        String[] filteredAppPaths = filterAppPaths(appBase.list());
        // Deploy XML descriptors from configBase
        deployDescriptors(configBase, configBase.list());
        // Deploy WARs
        deployWARs(appBase, filteredAppPaths);
        // Deploy expanded folders
        deployDirectories(appBase, filteredAppPaths);

    }


    /**
     * Filter the list of application file paths to remove those that match
     * the regular expression defined by {@link Host#getDeployIgnore()}.
     *
     * @param unfilteredAppPaths    The list of application paths to filter
     *
     * @return  The filtered list of application paths
     */
    protected String[] filterAppPaths(String[] unfilteredAppPaths) {
        Pattern filter = host.getDeployIgnorePattern();
        if (filter == null || unfilteredAppPaths == null) {
            return unfilteredAppPaths;
        }

        List<String> filteredList = new ArrayList<String>();
        Matcher matcher = null;
        for (String appPath : unfilteredAppPaths) {
            if (matcher == null) {
                matcher = filter.matcher(appPath);
            } else {
                matcher.reset(appPath);
            }
            if (matcher.matches()) {
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("hostConfig.ignorePath", appPath));
                }
            } else {
                filteredList.add(appPath);
            }
        }
        return filteredList.toArray(new String[filteredList.size()]);
    }


    /**
     * Deploy applications for any directories or WAR files that are found
     * in our "application root" directory.
     */
    protected void deployApps(String name) {

        File appBase = appBase();
        File configBase = configBase();
        ContextName cn = new ContextName(name, false);
        String baseName = cn.getBaseName();

        if (deploymentExists(cn.getName())) {
            return;
        }

        // Deploy XML descriptor from configBase
        File xml = new File(configBase, baseName + ".xml");
        if (xml.exists()) {
            deployDescriptor(cn, xml);
            return;
        }
        // Deploy WAR
        File war = new File(appBase, baseName + ".war");
        if (war.exists()) {
            deployWAR(cn, war);
            return;
        }
        // Deploy expanded folder
        File dir = new File(appBase, baseName);
        if (dir.exists())
            deployDirectory(cn, dir);
    }


    /**
     * Deploy XML context descriptors.
     */
    protected void deployDescriptors(File configBase, String[] files) {

        if (files == null)
            return;

        ExecutorService es = host.getStartStopExecutor();
        List<Future<?>> results = new ArrayList<Future<?>>();

        for (int i = 0; i < files.length; i++) {
            File contextXml = new File(configBase, files[i]);

            if (files[i].toLowerCase(Locale.ENGLISH).endsWith(".xml")) {
                ContextName cn = new ContextName(files[i], true);

                if (isServiced(cn.getName()) || deploymentExists(cn.getName()))
                    continue;

                results.add(
                        es.submit(new DeployDescriptor(this, cn, contextXml)));
            }
        }

        for (Future<?> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                log.error(sm.getString(
                        "hostConfig.deployDescriptor.threaded.error"), e);
            }
        }
    }


    /**
     * @param cn
     * @param contextXml
     */
    @SuppressWarnings("null") // context is not null
    protected void deployDescriptor(ContextName cn, File contextXml) {

        DeployedApplication deployedApp =
                new DeployedApplication(cn.getName(), true);

        long startTime = 0;
        // Assume this is a configuration descriptor and deploy it
        if(log.isInfoEnabled()) {
           startTime = System.currentTimeMillis();
           log.info(sm.getString("hostConfig.deployDescriptor",
                    contextXml.getAbsolutePath()));
        }

        Context context = null;
        boolean isExternalWar = false;
        boolean isExternal = false;
        File expandedDocBase = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(contextXml);
            synchronized (digesterLock) {
                try {
                    context = (Context) digester.parse(fis);
                } catch (Exception e) {
                    log.error(sm.getString(
                            "hostConfig.deployDescriptor.error",
                            contextXml.getAbsolutePath()), e);
                    context = new FailedContext();
                } finally {
                    digester.reset();
                }
            }

            Class<?> clazz = Class.forName(host.getConfigClass());
            LifecycleListener listener =
                (LifecycleListener) clazz.newInstance();
            context.addLifecycleListener(listener);

            context.setConfigFile(contextXml.toURI().toURL());
            context.setName(cn.getName());
            context.setPath(cn.getPath());
            context.setWebappVersion(cn.getVersion());
            // Add the associated docBase to the redeployed list if it's a WAR
            if (context.getDocBase() != null) {
                File docBase = new File(context.getDocBase());
                if (!docBase.isAbsolute()) {
                    docBase = new File(appBase(), context.getDocBase());
                }
                // If external docBase, register .xml as redeploy first
                if (!docBase.getCanonicalPath().startsWith(
                        appBase().getAbsolutePath() + File.separator)) {
                    isExternal = true;
                    deployedApp.redeployResources.put(
                            contextXml.getAbsolutePath(),
                            Long.valueOf(contextXml.lastModified()));
                    deployedApp.redeployResources.put(docBase.getAbsolutePath(),
                            Long.valueOf(docBase.lastModified()));
                    if (docBase.getAbsolutePath().toLowerCase(Locale.ENGLISH).endsWith(".war")) {
                        isExternalWar = true;
                    }
                } else {
                    log.warn(sm.getString("hostConfig.deployDescriptor.localDocBaseSpecified",
                             docBase));
                    // Ignore specified docBase
                    context.setDocBase(null);
                }
            }

            host.addChild(context);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error(sm.getString("hostConfig.deployDescriptor.error",
                                   contextXml.getAbsolutePath()), t);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
            // Get paths for WAR and expanded WAR in appBase

            // default to appBase dir + name
            expandedDocBase = new File(appBase(), cn.getBaseName());
            if (context.getDocBase() != null
                    && !context.getDocBase().toLowerCase(Locale.ENGLISH).endsWith(".war")) {
                // first assume docBase is absolute
                expandedDocBase = new File(context.getDocBase());
                if (!expandedDocBase.isAbsolute()) {
                    // if docBase specified and relative, it must be relative to appBase
                    expandedDocBase = new File(appBase(), context.getDocBase());
                }
            }

            boolean unpackWAR = unpackWARs;
            if (unpackWAR && context instanceof StandardContext) {
                unpackWAR = ((StandardContext) context).getUnpackWAR();
            }

            // Add the eventual unpacked WAR and all the resources which will be
            // watched inside it
            if (isExternalWar) {
                if (unpackWAR) {
                    deployedApp.redeployResources.put(expandedDocBase.getAbsolutePath(),
                            Long.valueOf(expandedDocBase.lastModified()));
                    addWatchedResources(deployedApp, expandedDocBase.getAbsolutePath(), context);
                } else {
                    addWatchedResources(deployedApp, null, context);
                }
            } else {
                // Find an existing matching war and expanded folder
                if (!isExternal) {
                    File warDocBase = new File(expandedDocBase.getAbsolutePath() + ".war");
                    if (warDocBase.exists()) {
                        deployedApp.redeployResources.put(warDocBase.getAbsolutePath(),
                                Long.valueOf(warDocBase.lastModified()));
                    } else {
                        // Trigger a redeploy if a WAR is added
                        deployedApp.redeployResources.put(
                                warDocBase.getAbsolutePath(),
                                Long.valueOf(0));
                    }
                }
                if (unpackWAR) {
                    deployedApp.redeployResources.put(expandedDocBase.getAbsolutePath(),
                            Long.valueOf(expandedDocBase.lastModified()));
                    addWatchedResources(deployedApp,
                            expandedDocBase.getAbsolutePath(), context);
                } else {
                    addWatchedResources(deployedApp, null, context);
                }
                if (!isExternal) {
                    // For external docBases, the context.xml will have been
                    // added above.
                    deployedApp.redeployResources.put(
                            contextXml.getAbsolutePath(),
                            Long.valueOf(contextXml.lastModified()));
                }
            }
            // Add the global redeploy resources (which are never deleted) at
            // the end so they don't interfere with the deletion process
            addGlobalRedeployResources(deployedApp);
        }

        if (host.findChild(context.getName()) != null) {
            deployed.put(context.getName(), deployedApp);
        }

        if (log.isInfoEnabled()) {
            log.info(sm.getString("hostConfig.deployDescriptor.finished",
                contextXml.getAbsolutePath(), Long.valueOf(System.currentTimeMillis() - startTime)));
        }
    }


    /**
     * Deploy WAR files.
     */
    protected void deployWARs(File appBase, String[] files) {

        if (files == null)
            return;

        ExecutorService es = host.getStartStopExecutor();
        List<Future<?>> results = new ArrayList<Future<?>>();

        for (int i = 0; i < files.length; i++) {

            if (files[i].equalsIgnoreCase("META-INF"))
                continue;
            if (files[i].equalsIgnoreCase("WEB-INF"))
                continue;
            File war = new File(appBase, files[i]);
            if (files[i].toLowerCase(Locale.ENGLISH).endsWith(".war") &&
                    war.isFile() && !invalidWars.contains(files[i]) ) {

                ContextName cn = new ContextName(files[i], true);

                if (isServiced(cn.getName())) {
                    continue;
                }
                if (deploymentExists(cn.getName())) {
                    DeployedApplication app = deployed.get(cn.getName());
                    boolean unpackWAR = unpackWARs;
                    if (unpackWAR && host.findChild(cn.getName()) instanceof StandardContext) {
                        unpackWAR = ((StandardContext) host.findChild(cn.getName())).getUnpackWAR();
                    }
                    if (!unpackWAR && app != null) {
                        // Need to check for a directory that should not be
                        // there
                        File dir = new File(appBase, cn.getBaseName());
                        if (dir.exists()) {
                            if (!app.loggedDirWarning) {
                                log.warn(sm.getString(
                                        "hostConfig.deployWar.hiddenDir",
                                        dir.getAbsoluteFile(),
                                        war.getAbsoluteFile()));
                                app.loggedDirWarning = true;
                            }
                        } else {
                            app.loggedDirWarning = false;
                        }
                    }
                    continue;
                }

                // Check for WARs with /../ /./ or similar sequences in the name
                if (!validateContextPath(appBase, cn.getBaseName())) {
                    log.error(sm.getString(
                            "hostConfig.illegalWarName", files[i]));
                    invalidWars.add(files[i]);
                    continue;
                }

                results.add(es.submit(new DeployWar(this, cn, war)));
            }
        }

        for (Future<?> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                log.error(sm.getString(
                        "hostConfig.deployWar.threaded.error"), e);
            }
        }
    }


    private boolean validateContextPath(File appBase, String contextPath) {
        // More complicated than the ideal as the canonical path may or may
        // not end with File.separator for a directory

        StringBuilder docBase;
        String canonicalDocBase = null;

        try {
            String canonicalAppBase = appBase.getCanonicalPath();
            docBase = new StringBuilder(canonicalAppBase);
            if (canonicalAppBase.endsWith(File.separator)) {
                docBase.append(contextPath.substring(1).replace(
                        '/', File.separatorChar));
            } else {
                docBase.append(contextPath.replace('/', File.separatorChar));
            }
            // At this point docBase should be canonical but will not end
            // with File.separator

            canonicalDocBase =
                (new File(docBase.toString())).getCanonicalPath();

            // If the canonicalDocBase ends with File.separator, add one to
            // docBase before they are compared
            if (canonicalDocBase.endsWith(File.separator)) {
                docBase.append(File.separator);
            }
        } catch (IOException ioe) {
            return false;
        }

        // Compare the two. If they are not the same, the contextPath must
        // have /../ like sequences in it
        return canonicalDocBase.equals(docBase.toString());
    }

    /**
     * @param cn
     * @param war
     */
    protected void deployWAR(ContextName cn, File war) {

        // Checking for a nested /META-INF/context.xml
        JarFile jar = null;
        InputStream istream = null;
        FileOutputStream fos = null;
        BufferedOutputStream ostream = null;

        File xml = new File(appBase(),
                cn.getBaseName() + "/META-INF/context.xml");

        boolean xmlInWar = false;
        try {
            jar = new JarFile(war);
            JarEntry entry = jar.getJarEntry(Constants.ApplicationContextXml);
            if (entry != null) {
                xmlInWar = true;
            }
        } catch (IOException e) {
            /* Ignore */
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (IOException ioe) {
                    // Ignore;
                }
                jar = null;
            }
        }

        Context context = null;
        boolean deployThisXML = isDeployThisXML(war, cn);

        try {
            if (deployThisXML && xml.exists() && unpackWARs && !copyXML) {
                synchronized (digesterLock) {
                    try {
                        context = (Context) digester.parse(xml);
                    } catch (Exception e) {
                        log.error(sm.getString(
                                "hostConfig.deployDescriptor.error",
                                war.getAbsolutePath()), e);
                    } finally {
                        digester.reset();
                        if (context == null) {
                            context = new FailedContext();
                        }
                    }
                }
                context.setConfigFile(xml.toURI().toURL());
            } else if (deployThisXML && xmlInWar) {
                synchronized (digesterLock) {
                    try {
                        jar = new JarFile(war);
                        JarEntry entry =
                            jar.getJarEntry(Constants.ApplicationContextXml);
                        istream = jar.getInputStream(entry);
                        context = (Context) digester.parse(istream);
                    } catch (Exception e) {
                        log.error(sm.getString(
                                "hostConfig.deployDescriptor.error",
                                war.getAbsolutePath()), e);
                    } finally {
                        digester.reset();
                        if (istream != null) {
                            try {
                                istream.close();
                            } catch (IOException e) {
                                /* Ignore */
                            }
                            istream = null;
                        }
                        if (jar != null) {
                            try {
                                jar.close();
                            } catch (IOException e) {
                                /* Ignore */
                            }
                            jar = null;
                        }
                        if (context == null) {
                            context = new FailedContext();
                        }
                        context.setConfigFile(
                                UriUtil.buildJarUrl(war, Constants.ApplicationContextXml));
                    }
                }
            } else if (!deployThisXML && xmlInWar) {
                // Block deployment as META-INF/context.xml may contain security
                // configuration necessary for a secure deployment.
                log.error(sm.getString("hostConfig.deployDescriptor.blocked",
                        cn.getPath(), Constants.ApplicationContextXml,
                        new File(configBase(), cn.getBaseName() + ".xml")));
            } else {
                context = (Context) Class.forName(contextClass).newInstance();
            }
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error(sm.getString("hostConfig.deployWar.error",
                    war.getAbsolutePath()), t);
        } finally {
            if (context == null) {
                context = new FailedContext();
            }
        }

        boolean copyThisXml = false;
        if (deployThisXML) {
            if (host instanceof StandardHost) {
                copyThisXml = ((StandardHost) host).isCopyXML();
            }

            // If Host is using default value Context can override it.
            if (!copyThisXml && context instanceof StandardContext) {
                copyThisXml = ((StandardContext) context).getCopyXML();
            }

            if (xmlInWar && copyThisXml) {
                // Change location of XML file to config base
                xml = new File(configBase(), cn.getBaseName() + ".xml");
                try {
                    jar = new JarFile(war);
                    JarEntry entry =
                        jar.getJarEntry(Constants.ApplicationContextXml);
                    istream = jar.getInputStream(entry);

                    fos = new FileOutputStream(xml);
                    ostream = new BufferedOutputStream(fos, 1024);
                    byte buffer[] = new byte[1024];
                    while (true) {
                        int n = istream.read(buffer);
                        if (n < 0) {
                            break;
                        }
                        ostream.write(buffer, 0, n);
                    }
                    ostream.flush();
                } catch (IOException e) {
                    /* Ignore */
                } finally {
                    if (ostream != null) {
                        try {
                            ostream.close();
                        } catch (IOException ioe) {
                            // Ignore
                        }
                        ostream = null;
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException ioe) {
                            // Ignore
                        }
                        fos = null;
                    }
                    if (istream != null) {
                        try {
                            istream.close();
                        } catch (IOException ioe) {
                            // Ignore
                        }
                        istream = null;
                    }
                    if (jar != null) {
                        try {
                            jar.close();
                        } catch (IOException ioe) {
                            // Ignore;
                        }
                        jar = null;
                    }
                }
            }
        }

        DeployedApplication deployedApp = new DeployedApplication(cn.getName(),
                xml.exists() && deployThisXML && copyThisXml);

        long startTime = 0;
        // Deploy the application in this WAR file
        if(log.isInfoEnabled()) {
            startTime = System.currentTimeMillis();
            log.info(sm.getString("hostConfig.deployWar",
                    war.getAbsolutePath()));
        }

        try {
            // Populate redeploy resources with the WAR file
            deployedApp.redeployResources.put
                (war.getAbsolutePath(), Long.valueOf(war.lastModified()));

            if (deployThisXML && xml.exists() && copyThisXml) {
                deployedApp.redeployResources.put(xml.getAbsolutePath(),
                        Long.valueOf(xml.lastModified()));
            } else {
                // In case an XML file is added to the config base later
                deployedApp.redeployResources.put(
                        (new File(configBase(),
                                cn.getBaseName() + ".xml")).getAbsolutePath(),
                        Long.valueOf(0));
            }

            Class<?> clazz = Class.forName(host.getConfigClass());
            LifecycleListener listener =
                (LifecycleListener) clazz.newInstance();
            context.addLifecycleListener(listener);

            context.setName(cn.getName());
            context.setPath(cn.getPath());
            context.setWebappVersion(cn.getVersion());
            context.setDocBase(cn.getBaseName() + ".war");
            host.addChild(context);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error(sm.getString("hostConfig.deployWar.error",
                    war.getAbsolutePath()), t);
        } finally {
            // If we're unpacking WARs, the docBase will be mutated after
            // starting the context
            boolean unpackWAR = unpackWARs;
            if (unpackWAR && context instanceof StandardContext) {
                unpackWAR = ((StandardContext) context).getUnpackWAR();
            }
            if (unpackWAR && context.getDocBase() != null) {
                File docBase = new File(appBase(), cn.getBaseName());
                deployedApp.redeployResources.put(docBase.getAbsolutePath(),
                        Long.valueOf(docBase.lastModified()));
                addWatchedResources(deployedApp, docBase.getAbsolutePath(),
                        context);
                if (deployThisXML && !copyThisXml && (xmlInWar || xml.exists())) {
                    deployedApp.redeployResources.put(xml.getAbsolutePath(),
                            Long.valueOf(xml.lastModified()));
                }
            } else {
                // Passing null for docBase means that no resources will be
                // watched. This will be logged at debug level.
                addWatchedResources(deployedApp, null, context);
            }
            // Add the global redeploy resources (which are never deleted) at
            // the end so they don't interfere with the deletion process
            addGlobalRedeployResources(deployedApp);
        }

        deployed.put(cn.getName(), deployedApp);

        if (log.isInfoEnabled()) {
            log.info(sm.getString("hostConfig.deployWar.finished",
                war.getAbsolutePath(), Long.valueOf(System.currentTimeMillis() - startTime)));
        }
    }


    /**
     * Deploy directories.
     */
    protected void deployDirectories(File appBase, String[] files) {

        if (files == null)
            return;

        ExecutorService es = host.getStartStopExecutor();
        List<Future<?>> results = new ArrayList<Future<?>>();

        for (int i = 0; i < files.length; i++) {

            if (files[i].equalsIgnoreCase("META-INF"))
                continue;
            if (files[i].equalsIgnoreCase("WEB-INF"))
                continue;
            File dir = new File(appBase, files[i]);
            if (dir.isDirectory()) {
                ContextName cn = new ContextName(files[i], false);

                if (isServiced(cn.getName()) || deploymentExists(cn.getName()))
                    continue;

                results.add(es.submit(new DeployDirectory(this, cn, dir)));
            }
        }

        for (Future<?> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                log.error(sm.getString(
                        "hostConfig.deployDir.threaded.error"), e);
            }
        }
    }


    /**
     * @param cn
     * @param dir
     */
    protected void deployDirectory(ContextName cn, File dir) {


        long startTime = 0;
        // Deploy the application in this directory
        if( log.isInfoEnabled() ) {
            startTime = System.currentTimeMillis();
            log.info(sm.getString("hostConfig.deployDir",
                    dir.getAbsolutePath()));
        }

        Context context = null;
        File xml = new File(dir, Constants.ApplicationContextXml);
        File xmlCopy = new File(configBase(), cn.getBaseName() + ".xml");

        DeployedApplication deployedApp;
        boolean copyThisXml = isCopyXML();
        boolean deployThisXML = isDeployThisXML(dir, cn);

        try {
            if (deployThisXML && xml.exists()) {
                synchronized (digesterLock) {
                    try {
                        context = (Context) digester.parse(xml);
                    } catch (Exception e) {
                        log.error(sm.getString(
                                "hostConfig.deployDescriptor.error",
                                xml), e);
                        context = new FailedContext();
                    } finally {
                        digester.reset();
                        if (context == null) {
                            context = new FailedContext();
                        }
                    }
                }

                if (copyThisXml == false && context instanceof StandardContext) {
                    // Host is using default value. Context may override it.
                    copyThisXml = ((StandardContext) context).getCopyXML();
                }

                if (copyThisXml) {
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = new FileInputStream(xml);
                        os = new FileOutputStream(xmlCopy);
                        IOTools.flow(is, os);
                        // Don't catch IOE - let the outer try/catch handle it
                    } finally {
                        try {
                            if (is != null) is.close();
                        } catch (IOException e){
                            // Ignore
                        }
                        try {
                            if (os != null) os.close();
                        } catch (IOException e){
                            // Ignore
                        }
                    }
                    context.setConfigFile(xmlCopy.toURI().toURL());
                } else {
                    context.setConfigFile(xml.toURI().toURL());
                }
            } else if (!deployThisXML && xml.exists()) {
                // Block deployment as META-INF/context.xml may contain security
                // configuration necessary for a secure deployment.
                log.error(sm.getString("hostConfig.deployDescriptor.blocked",
                        cn.getPath(), xml, xmlCopy));
                context = new FailedContext();
            } else {
                context = (Context) Class.forName(contextClass).newInstance();
            }

            Class<?> clazz = Class.forName(host.getConfigClass());
            LifecycleListener listener =
                (LifecycleListener) clazz.newInstance();
            context.addLifecycleListener(listener);

            context.setName(cn.getName());
            context.setPath(cn.getPath());
            context.setWebappVersion(cn.getVersion());
            context.setDocBase(cn.getBaseName());
            host.addChild(context);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error(sm.getString("hostConfig.deployDir.error",
                    dir.getAbsolutePath()), t);
        } finally {
            deployedApp = new DeployedApplication(cn.getName(),
                    xml.exists() && deployThisXML && copyThisXml);

            // Fake re-deploy resource to detect if a WAR is added at a later
            // point
            deployedApp.redeployResources.put(dir.getAbsolutePath() + ".war",
                    Long.valueOf(0));
            deployedApp.redeployResources.put(dir.getAbsolutePath(),
                    Long.valueOf(dir.lastModified()));
            if (deployThisXML && xml.exists()) {
                if (copyThisXml) {
                    deployedApp.redeployResources.put(
                            xmlCopy.getAbsolutePath(),
                            Long.valueOf(xmlCopy.lastModified()));
                } else {
                    deployedApp.redeployResources.put(
                            xml.getAbsolutePath(),
                            Long.valueOf(xml.lastModified()));
                    // Fake re-deploy resource to detect if a context.xml file is
                    // added at a later point
                    deployedApp.redeployResources.put(
                            xmlCopy.getAbsolutePath(),
                            Long.valueOf(0));
                }
            } else {
                // Fake re-deploy resource to detect if a context.xml file is
                // added at a later point
                deployedApp.redeployResources.put(
                        xmlCopy.getAbsolutePath(),
                        Long.valueOf(0));
                if (!xml.exists()) {
                    deployedApp.redeployResources.put(
                            xml.getAbsolutePath(),
                            Long.valueOf(0));
                }
            }
            addWatchedResources(deployedApp, dir.getAbsolutePath(), context);
            // Add the global redeploy resources (which are never deleted) at
            // the end so they don't interfere with the deletion process
            addGlobalRedeployResources(deployedApp);
        }

        deployed.put(cn.getName(), deployedApp);

        if( log.isInfoEnabled() ) {
            log.info(sm.getString("hostConfig.deployDir.finished",
                    dir.getAbsolutePath(), Long.valueOf(System.currentTimeMillis() - startTime)));
        }
    }


    /**
     * Check if a webapp is already deployed in this host.
     *
     * @param contextName of the context which will be checked
     */
    protected boolean deploymentExists(String contextName) {
        return (deployed.containsKey(contextName) ||
                (host.findChild(contextName) != null));
    }


    /**
     * Add watched resources to the specified Context.
     * @param app HostConfig deployed app
     * @param docBase web app docBase
     * @param context web application context
     */
    protected void addWatchedResources(DeployedApplication app, String docBase,
            Context context) {
        // FIXME: Feature idea. Add support for patterns (ex: WEB-INF/*,
        //        WEB-INF/*.xml), where we would only check if at least one
        //        resource is newer than app.timestamp
        File docBaseFile = null;
        if (docBase != null) {
            docBaseFile = new File(docBase);
            if (!docBaseFile.isAbsolute()) {
                docBaseFile = new File(appBase(), docBase);
            }
        }
        String[] watchedResources = context.findWatchedResources();
        for (int i = 0; i < watchedResources.length; i++) {
            File resource = new File(watchedResources[i]);
            if (!resource.isAbsolute()) {
                if (docBase != null) {
                    resource = new File(docBaseFile, watchedResources[i]);
                } else {
                    if(log.isDebugEnabled())
                        log.debug("Ignoring non-existent WatchedResource '" +
                                resource.getAbsolutePath() + "'");
                    continue;
                }
            }
            if(log.isDebugEnabled())
                log.debug("Watching WatchedResource '" +
                        resource.getAbsolutePath() + "'");
            app.reloadResources.put(resource.getAbsolutePath(),
                    Long.valueOf(resource.lastModified()));
        }
    }


    protected void addGlobalRedeployResources(DeployedApplication app) {
        // Redeploy resources processing is hard-coded to never delete this file
        File hostContextXml =
                new File(getConfigBaseName(), Constants.HostContextXml);
        if (hostContextXml.isFile()) {
            app.redeployResources.put(hostContextXml.getAbsolutePath(),
                    Long.valueOf(hostContextXml.lastModified()));
        }

        // Redeploy resources in CATALINA_BASE/conf are never deleted
        File globalContextXml =
                returnCanonicalPath(Constants.DefaultContextXml);
        if (globalContextXml.isFile()) {
            app.redeployResources.put(globalContextXml.getAbsolutePath(),
                    Long.valueOf(globalContextXml.lastModified()));
        }
    }


    /**
     * Check resources for redeployment and reloading.
     *
     * @deprecated Use {@link #checkResources(DeployedApplication, boolean)}.
     *             Will be removed in Tomcat 9.0.x
     */
    @Deprecated
    protected synchronized void checkResources(DeployedApplication app) {
        checkResources(app, false);
    }

    /**
     * Check resources for redeployment and reloading.
     *
     * @param app   The web application to check
     * @param skipFileModificationResolutionCheck
     *              When checking files for modification should the check that
     *              requires that any file modification must have occurred at
     *              least as long ago as the resolution of the file time stamp
     *              be skipped
     */
    protected synchronized void checkResources(DeployedApplication app,
            boolean skipFileModificationResolutionCheck) {
        String[] resources =
            app.redeployResources.keySet().toArray(new String[0]);
        // Offset the current time by the resolution of File.lastModified()
        long currentTimeWithResolutionOffset =
                System.currentTimeMillis() - FILE_MODIFICATION_RESOLUTION_MS;
        for (int i = 0; i < resources.length; i++) {
            File resource = new File(resources[i]);
            if (log.isDebugEnabled())
                log.debug("Checking context[" + app.name +
                        "] redeploy resource " + resource);
            long lastModified =
                    app.redeployResources.get(resources[i]).longValue();
            if (resource.exists() || lastModified == 0) {
                // File.lastModified() has a resolution of 1s (1000ms). The last
                // modified time has to be more than 1000ms ago to ensure that
                // modifications that take place in the same second are not
                // missed. See Bug 57765.
                if (resource.lastModified() != lastModified && (!host.getAutoDeploy() ||
                        resource.lastModified() < currentTimeWithResolutionOffset ||
                        skipFileModificationResolutionCheck)) {
                    if (resource.isDirectory()) {
                        // No action required for modified directory
                        app.redeployResources.put(resources[i],
                                Long.valueOf(resource.lastModified()));
                    } else if (app.hasDescriptor &&
                            resource.getName().toLowerCase(
                                    Locale.ENGLISH).endsWith(".war")) {
                        // Modified WAR triggers a reload if there is an XML
                        // file present
                        // The only resource that should be deleted is the
                        // expanded WAR (if any)
                        Context context = (Context) host.findChild(app.name);
                        String docBase = context.getDocBase();
                        if (!docBase.toLowerCase(Locale.ENGLISH).endsWith(".war")) {
                            // This is an expanded directory
                            File docBaseFile = new File(docBase);
                            if (!docBaseFile.isAbsolute()) {
                                docBaseFile = new File(appBase(), docBase);
                            }
                            reload(app, docBaseFile, resource.getAbsolutePath());
                        } else {
                            reload(app, null, null);
                        }
                        // Update times
                        app.redeployResources.put(resources[i],
                                Long.valueOf(resource.lastModified()));
                        app.timestamp = System.currentTimeMillis();
                        boolean unpackWAR = unpackWARs;
                        if (unpackWAR && context instanceof StandardContext) {
                            unpackWAR = ((StandardContext) context).getUnpackWAR();
                        }
                        if (unpackWAR) {
                            addWatchedResources(app, context.getDocBase(), context);
                        } else {
                            addWatchedResources(app, null, context);
                        }
                        return;
                    } else {
                        // Everything else triggers a redeploy
                        // (just need to undeploy here, deploy will follow)
                        undeploy(app);
                        deleteRedeployResources(app, resources, i, false);
                        return;
                    }
                }
            } else {
                // There is a chance the the resource was only missing
                // temporarily eg renamed during a text editor save
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    // Ignore
                }
                // Recheck the resource to see if it was really deleted
                if (resource.exists()) {
                    continue;
                }
                // Undeploy application
                undeploy(app);
                deleteRedeployResources(app, resources, i, true);
                return;
            }
        }
        resources = app.reloadResources.keySet().toArray(new String[0]);
        boolean update = false;
        for (int i = 0; i < resources.length; i++) {
            File resource = new File(resources[i]);
            if (log.isDebugEnabled()) {
                log.debug("Checking context[" + app.name + "] reload resource " + resource);
            }
            long lastModified = app.reloadResources.get(resources[i]).longValue();
            // File.lastModified() has a resolution of 1s (1000ms). The last
            // modified time has to be more than 1000ms ago to ensure that
            // modifications that take place in the same second are not
            // missed. See Bug 57765.
            if ((resource.lastModified() != lastModified &&
                    (!host.getAutoDeploy() ||
                            resource.lastModified() < currentTimeWithResolutionOffset ||
                            skipFileModificationResolutionCheck)) ||
                    update) {
                if (!update) {
                    // Reload application
                    reload(app, null, null);
                    update = true;
                }
                // Update times. More than one file may have been updated. We
                // don't want to trigger a series of reloads.
                app.reloadResources.put(resources[i],
                        Long.valueOf(resource.lastModified()));
            }
            app.timestamp = System.currentTimeMillis();
        }
    }


    /*
     * Note: If either of fileToRemove and newDocBase are null, both will be
     *       ignored.
     */
    private void reload(DeployedApplication app, File fileToRemove, String newDocBase) {
        if(log.isInfoEnabled())
            log.info(sm.getString("hostConfig.reload", app.name));
        Context context = (Context) host.findChild(app.name);
        if (context.getState().isAvailable()) {
            if (fileToRemove != null && newDocBase != null) {
                context.addLifecycleListener(
                        new ExpandedDirectoryRemovalListener(fileToRemove, newDocBase));
            }
            // Reload catches and logs exceptions
            context.reload();
        } else {
            // If the context was not started (for example an error
            // in web.xml) we'll still get to try to start
            if (fileToRemove != null && newDocBase != null) {
                ExpandWar.delete(fileToRemove);
                context.setDocBase(newDocBase);
            }
            try {
                context.start();
            } catch (Exception e) {
                log.warn(sm.getString
                         ("hostConfig.context.restart", app.name), e);
            }
        }
    }


    private void undeploy(DeployedApplication app) {
        if (log.isInfoEnabled())
            log.info(sm.getString("hostConfig.undeploy", app.name));
        Container context = host.findChild(app.name);
        try {
            host.removeChild(context);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.warn(sm.getString
                     ("hostConfig.context.remove", app.name), t);
        }
        deployed.remove(app.name);
    }


    private void deleteRedeployResources(DeployedApplication app, String[] resources, int i,
            boolean deleteReloadResources) {

        // Delete other redeploy resources
        for (int j = i + 1; j < resources.length; j++) {
            File current = new File(resources[j]);
            // Never delete per host context.xml defaults
            if (Constants.HostContextXml.equals(current.getName())) {
                continue;
            }
            // Only delete resources in the appBase or the
            // host's configBase
            if (isDeletableResource(app, current)) {
                if (log.isDebugEnabled()) {
                    log.debug("Delete " + current);
                }
                ExpandWar.delete(current);
            }
        }

        // Delete reload resources (to remove any remaining .xml descriptor)
        if (deleteReloadResources) {
            String[] resources2 = app.reloadResources.keySet().toArray(new String[0]);
            for (int j = 0; j < resources2.length; j++) {
                File current = new File(resources2[j]);
                // Never delete per host context.xml defaults
                if (Constants.HostContextXml.equals(current.getName())) {
                    continue;
                }
                // Only delete resources in the appBase or the host's
                // configBase
                if (isDeletableResource(app, current)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Delete " + current);
                    }
                    ExpandWar.delete(current);
                }
            }
        }
    }


    /*
     * Delete any resource that would trigger the automatic deployment code to
     * re-deploy the application. This means deleting:
     * - any resource located in the appBase
     * - any deployment descriptor located under the configBase
     * - symlinks in the appBase or configBase for either of the above
     */
    private boolean isDeletableResource(DeployedApplication app, File resource) {
        // The resource may be a file, a directory or a symlink to a file or
        // directory.

        // Check that the resource is absolute. This should always be the case.
        if (!resource.isAbsolute()) {
            log.warn(sm.getString("hostConfig.resourceNotAbsolute", app.name, resource));
            return false;
        }

        // Determine where the resource is located
        String canonicalLocation;
        try {
            canonicalLocation = resource.getParentFile().getCanonicalPath();
        } catch (IOException e) {
            log.warn(sm.getString(
                    "hostConfig.canonicalizing", resource.getParentFile(), app.name), e);
            return false;
        }

        String canonicalAppBase;
        try {
            canonicalAppBase = appBase().getCanonicalPath();
        } catch (IOException e) {
            log.warn(sm.getString(
                    "hostConfig.canonicalizing", appBase(), app.name), e);
            return false;
        }

        if (canonicalLocation.equals(canonicalAppBase)) {
            // Resource is located in the appBase so it may be deleted
            return true;
        }

        String canonicalConfigBase;
        try {
            canonicalConfigBase = configBase().getCanonicalPath();
        } catch (IOException e) {
            log.warn(sm.getString(
                    "hostConfig.canonicalizing", configBase(), app.name), e);
            return false;
        }

        if (canonicalLocation.equals(canonicalConfigBase) &&
                resource.getName().endsWith(".xml")) {
            // Resource is an xml file in the configBase so it may be deleted
            return true;
        }

        // All other resources should not be deleted
        return false;
    }


    public void beforeStart() {
        if (host.getCreateDirs()) {
            File[] dirs = new File[] {appBase(),configBase()};
            for (int i=0; i<dirs.length; i++) {
                if (!dirs[i].mkdirs() && !dirs[i].isDirectory()) {
                    log.error(sm.getString("hostConfig.createDirs",dirs[i]));
                }
            }
        }
    }


    /**
     * Process a "start" event for this Host.
     */
    public void start() {

        if (log.isDebugEnabled())
            log.debug(sm.getString("hostConfig.start"));

        try {
            ObjectName hostON = host.getObjectName();
            oname = new ObjectName
                (hostON.getDomain() + ":type=Deployer,host=" + host.getName());
            Registry.getRegistry(null, null).registerComponent
                (this, oname, this.getClass().getName());
        } catch (Exception e) {
            log.error(sm.getString("hostConfig.jmx.register", oname), e);
        }

        if (!appBase().isDirectory()) {
            log.error(sm.getString(
                    "hostConfig.appBase", host.getName(), appBase().getPath()));
            host.setDeployOnStartup(false);
            host.setAutoDeploy(false);
        }

        if (host.getDeployOnStartup())
            deployApps();

    }


    /**
     * Process a "stop" event for this Host.
     */
    public void stop() {

        if (log.isDebugEnabled())
            log.debug(sm.getString("hostConfig.stop"));

        if (oname != null) {
            try {
                Registry.getRegistry(null, null).unregisterComponent(oname);
            } catch (Exception e) {
                log.error(sm.getString("hostConfig.jmx.unregister", oname), e);
            }
        }
        oname = null;
    }


    /**
     * Check status of all webapps.
     */
    protected void check() {

        if (host.getAutoDeploy()) {
            // Check for resources modification to trigger redeployment
            DeployedApplication[] apps =
                deployed.values().toArray(new DeployedApplication[0]);
            for (int i = 0; i < apps.length; i++) {
                if (!isServiced(apps[i].name))
                    checkResources(apps[i], false);
            }

            // Check for old versions of applications that can now be undeployed
            if (host.getUndeployOldVersions()) {
                checkUndeploy();
            }

            // Hotdeploy applications
            deployApps();
        }
    }


    /**
     * Check status of a specific web application and reload, redeploy or deploy
     * it as necessary. This method is for use with functionality such as
     * management web applications that upload new/updated web applications and
     * need to trigger the appropriate action to deploy them. This method
     * assumes that the web application is currently marked as serviced and that
     * any uploading/updating has been completed before this method is called.
     * Any action taken as a result of the checks will complete before this
     * method returns.
     *
     * @param name The name of the web application to check
     */
    public void check(String name) {
        DeployedApplication app = deployed.get(name);
        if (app != null) {
            checkResources(app, true);
        }
        deployApps(name);
    }

    /**
     * Check for old versions of applications using parallel deployment that are
     * now unused (have no active sessions) and undeploy any that are found.
     */
    public synchronized void checkUndeploy() {
        // Need ordered set of names
        SortedSet<String> sortedAppNames = new TreeSet<String>();
        sortedAppNames.addAll(deployed.keySet());

        if (sortedAppNames.size() < 2) {
            return;
        }
        Iterator<String> iter = sortedAppNames.iterator();

        ContextName previous = new ContextName(iter.next(), false);
        do {
            ContextName current = new ContextName(iter.next(), false);

            if (current.getPath().equals(previous.getPath())) {
                // Current and previous are same path - current will always
                // be a later version
                Context previousContext = (Context) host.findChild(previous.getName());
                Context currentContext = (Context) host.findChild(current.getName());
                if (previousContext != null && currentContext != null &&
                        currentContext.getState().isAvailable() &&
                        !isServiced(previous.getName())) {
                    Manager manager = previousContext.getManager();
                    if (manager != null) {
                        int sessionCount;
                        if (manager instanceof DistributedManager) {
                            sessionCount = ((DistributedManager) manager).getActiveSessionsFull();
                        } else {
                            sessionCount = manager.getActiveSessions();
                        }
                        if (sessionCount == 0) {
                            if (log.isInfoEnabled()) {
                                log.info(sm.getString(
                                        "hostConfig.undeployVersion", previous.getName()));
                            }
                            DeployedApplication app = deployed.get(previous.getName());
                            String[] resources = app.redeployResources.keySet().toArray(new String[0]);
                            // Version is unused - undeploy it completely
                            // The -1 is a 'trick' to ensure all redeploy
                            // resources are removed
                            undeploy(app);
                            deleteRedeployResources(app, resources, -1, true);
                        }
                    }
                }
            }
            previous = current;
        } while (iter.hasNext());
    }

    /**
     * Add a new Context to be managed by us.
     * Entry point for the admin webapp, and other JMX Context controllers.
     */
    public void manageApp(Context context)  {

        String contextName = context.getName();

        if (deployed.containsKey(contextName))
            return;

        DeployedApplication deployedApp =
                new DeployedApplication(contextName, false);

        // Add the associated docBase to the redeployed list if it's a WAR
        boolean isWar = false;
        if (context.getDocBase() != null) {
            File docBase = new File(context.getDocBase());
            if (!docBase.isAbsolute()) {
                docBase = new File(appBase(), context.getDocBase());
            }
            deployedApp.redeployResources.put(docBase.getAbsolutePath(),
                    Long.valueOf(docBase.lastModified()));
            if (docBase.getAbsolutePath().toLowerCase(Locale.ENGLISH).endsWith(".war")) {
                isWar = true;
            }
        }
        host.addChild(context);
        // Add the eventual unpacked WAR and all the resources which will be
        // watched inside it
        boolean unpackWAR = unpackWARs;
        if (unpackWAR && context instanceof StandardContext) {
            unpackWAR = ((StandardContext) context).getUnpackWAR();
        }
        if (isWar && unpackWAR) {
            File docBase = new File(appBase(), context.getBaseName());
            deployedApp.redeployResources.put(docBase.getAbsolutePath(),
                        Long.valueOf(docBase.lastModified()));
            addWatchedResources(deployedApp, docBase.getAbsolutePath(), context);
        } else {
            addWatchedResources(deployedApp, null, context);
        }
        deployed.put(contextName, deployedApp);
    }

    /**
     * Remove a webapp from our control.
     * Entry point for the admin webapp, and other JMX Context controllers.
     */
    public void unmanageApp(String contextName) {
        if(isServiced(contextName)) {
            deployed.remove(contextName);
            host.removeChild(host.findChild(contextName));
        }
    }

    // ----------------------------------------------------- Instance Variables


    /**
     * This class represents the state of a deployed application, as well as
     * the monitored resources.
     */
    protected static class DeployedApplication {
        public DeployedApplication(String name, boolean hasDescriptor) {
            this.name = name;
            this.hasDescriptor = hasDescriptor;
        }

        /**
         * Application context path. The assertion is that
         * (host.getChild(name) != null).
         */
        public String name;

        /**
         * Does this application have a context.xml descriptor file on the
         * host's configBase?
         */
        public final boolean hasDescriptor;

        /**
         * Any modification of the specified (static) resources will cause a
         * redeployment of the application. If any of the specified resources is
         * removed, the application will be undeployed. Typically, this will
         * contain resources like the context.xml file, a compressed WAR path.
         * The value is the last modification time.
         */
        public LinkedHashMap<String, Long> redeployResources =
            new LinkedHashMap<String, Long>();

        /**
         * Any modification of the specified (static) resources will cause a
         * reload of the application. This will typically contain resources
         * such as the web.xml of a webapp, but can be configured to contain
         * additional descriptors.
         * The value is the last modification time.
         */
        public HashMap<String, Long> reloadResources =
            new HashMap<String, Long>();

        /**
         * Instant where the application was last put in service.
         */
        public long timestamp = System.currentTimeMillis();

        /**
         * In some circumstances, such as when unpackWARs is true, a directory
         * may be added to the appBase that is ignored. This flag indicates that
         * the user has been warned so that the warning is not logged on every
         * run of the auto deployer.
         */
        public boolean loggedDirWarning = false;
    }

    private static class DeployDescriptor implements Runnable {

        private HostConfig config;
        private ContextName cn;
        private File descriptor;

        public DeployDescriptor(HostConfig config, ContextName cn,
                File descriptor) {
            this.config = config;
            this.cn = cn;
            this.descriptor= descriptor;
        }

        @Override
        public void run() {
            config.deployDescriptor(cn, descriptor);
        }
    }

    private static class DeployWar implements Runnable {

        private HostConfig config;
        private ContextName cn;
        private File war;

        public DeployWar(HostConfig config, ContextName cn, File war) {
            this.config = config;
            this.cn = cn;
            this.war = war;
        }

        @Override
        public void run() {
            config.deployWAR(cn, war);
        }
    }

    private static class DeployDirectory implements Runnable {

        private HostConfig config;
        private ContextName cn;
        private File dir;

        public DeployDirectory(HostConfig config, ContextName cn, File dir) {
            this.config = config;
            this.cn = cn;
            this.dir = dir;
        }

        @Override
        public void run() {
            config.deployDirectory(cn, dir);
        }
    }


    /*
     * The purpose of this class is to provide a way for HostConfig to get
     * a Context to delete an expanded WAR after the Context stops. This is to
     * resolve this issue described in Bug 57772. The alternative solutions
     * require either duplicating a lot of the Context.reload() code in
     * HostConfig or adding a new reload(boolean) method to Context that allows
     * the caller to optionally delete any expanded WAR.
     *
     * The LifecycleListener approach offers greater flexibility and enables the
     * behaviour to be changed / extended / removed in future without changing
     * the Context API.
     */
    private static class ExpandedDirectoryRemovalListener implements LifecycleListener {

        private final File toDelete;
        private final String newDocBase;

        /**
         * Create a listener that will ensure that any expanded WAR is removed
         * and the docBase set to the specified WAR.
         *
         * @param toDelete The file (a directory representing an expanded WAR)
         *                 to be deleted
         * @param newDocBase The new docBase for the Context
         */
        public ExpandedDirectoryRemovalListener(File toDelete, String newDocBase) {
            this.toDelete = toDelete;
            this.newDocBase = newDocBase;
        }

        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            if (Lifecycle.AFTER_STOP_EVENT.equals(event.getType())) {
                // The context has stopped.
                Context context = (Context) event.getLifecycle();

                // Remove the old expanded WAR.
                ExpandWar.delete(toDelete);

                // Reset the docBase to trigger re-expansion of the WAR.
                context.setDocBase(newDocBase);

                // Remove this listener from the Context else it will run every
                // time the Context is stopped.
                context.removeLifecycleListener(this);
            }
        }
    }
}
