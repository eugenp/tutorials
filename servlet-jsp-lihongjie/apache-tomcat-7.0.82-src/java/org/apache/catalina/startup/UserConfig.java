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


import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.tomcat.util.res.StringManager;


/**
 * Startup event listener for a <b>Host</b> that configures Contexts (web
 * applications) for all defined "users" who have a web application in a
 * directory with the specified name in their home directories.  The context
 * path of each deployed application will be set to <code>~xxxxx</code>, where
 * xxxxx is the username of the owning user for that web application
 *
 * @author Craig R. McClanahan
 */
public final class UserConfig
    implements LifecycleListener {


    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( UserConfig.class );

    
    // ----------------------------------------------------- Instance Variables


    /**
     * The Java class name of the Context configuration class we should use.
     */
    private String configClass = "org.apache.catalina.startup.ContextConfig";


    /**
     * The Java class name of the Context implementation we should use.
     */
    private String contextClass = "org.apache.catalina.core.StandardContext";


    /**
     * The directory name to be searched for within each user home directory.
     */
    private String directoryName = "public_html";


    /**
     * The base directory containing user home directories.
     */
    private String homeBase = null;


    /**
     * The Host we are associated with.
     */
    private Host host = null;


    /**
     * The string resources for this package.
     */
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * The Java class name of the user database class we should use.
     */
    private String userClass =
        "org.apache.catalina.startup.PasswdUserDatabase";

    /**
     * A regular expression defining user who deployment is allowed.
     */
    Pattern allow = null;

    /**
     * A regular expression defining user who deployment is denied.
     */
    Pattern deny = null;

    // ------------------------------------------------------------- Properties


    /**
     * Return the Context configuration class name.
     */
    public String getConfigClass() {

        return (this.configClass);

    }


    /**
     * Set the Context configuration class name.
     *
     * @param configClass The new Context configuration class name.
     */
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

        this.contextClass = contextClass;

    }


    /**
     * Return the directory name for user web applications.
     */
    public String getDirectoryName() {

        return (this.directoryName);

    }


    /**
     * Set the directory name for user web applications.
     *
     * @param directoryName The new directory name
     */
    public void setDirectoryName(String directoryName) {

        this.directoryName = directoryName;

    }


    /**
     * Return the base directory containing user home directories.
     */
    public String getHomeBase() {

        return (this.homeBase);

    }


    /**
     * Set the base directory containing user home directories.
     *
     * @param homeBase The new base directory
     */
    public void setHomeBase(String homeBase) {

        this.homeBase = homeBase;

    }


    /**
     * Return the user database class name for this component.
     */
    public String getUserClass() {

        return (this.userClass);

    }


    /**
     * Set the user database class name for this component.
     */
    public void setUserClass(String userClass) {

        this.userClass = userClass;

    }

    /**
     * Return the regular expression used to test for user who deployment is allowed. 
     */
    public String getAllow() {
        if (allow == null) return null;
        return allow.toString();
    }


    /**
     * Set the regular expression used to test for user who deployment is allowed.
     *
     * @param allow The new allow expression
     */
    public void setAllow(String allow) {
        if (allow == null || allow.length() == 0) {
            this.allow = null;
        } else {
            this.allow = Pattern.compile(allow);
        }
    }


    /**
     * Return the regular expression used to test for user who deployment is denied.
     */
    public String getDeny() {
        if (deny == null) return null;
        return deny.toString();
    }


    /**
     * Set the regular expression used to test for user who deployment is denied.
     *
     * @param deny The new deny expression
     */
    public void setDeny(String deny) {
        if (deny == null || deny.length() == 0) {
            this.deny = null;
        } else {
            this.deny = Pattern.compile(deny);
        }
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
        } catch (ClassCastException e) {
            log.error(sm.getString("hostConfig.cce", event.getLifecycle()), e);
            return;
        }

        // Process the event that has occurred
        if (event.getType().equals(Lifecycle.START_EVENT))
            start();
        else if (event.getType().equals(Lifecycle.STOP_EVENT))
            stop();

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Deploy a web application for any user who has a web application present
     * in a directory with a specified name within their home directory.
     */
    private void deploy() {

        if (host.getLogger().isDebugEnabled())
            host.getLogger().debug(sm.getString("userConfig.deploying"));

        // Load the user database object for this host
        UserDatabase database = null;
        try {
            Class<?> clazz = Class.forName(userClass);
            database = (UserDatabase) clazz.newInstance();
            database.setUserConfig(this);
        } catch (Exception e) {
            host.getLogger().error(sm.getString("userConfig.database"), e);
            return;
        }

        ExecutorService executor = host.getStartStopExecutor();
        List<Future<?>> results = new ArrayList<Future<?>>();

        // Deploy the web application (if any) for each defined user
        Enumeration<String> users = database.getUsers();
        while (users.hasMoreElements()) {
            String user = users.nextElement();
            if (!isDeployAllowed(user)) continue;
            String home = database.getHome(user);
            results.add(executor.submit(new DeployUserDirectory(this, user, home)));
        }

        for (Future<?> result : results) {
            try {
                result.get();
            } catch (Exception e) {
                host.getLogger().error(sm.getString("userConfig.deploy.threaded.error"), e);
            }
        }
    }


    /**
     * Deploy a web application for the specified user if they have such an
     * application in the defined directory within their home directory.
     *
     * @param user Username owning the application to be deployed
     * @param home Home directory of this user
     */
    private void deploy(String user, String home) {

        // Does this user have a web application to be deployed?
        String contextPath = "/~" + user;
        if (host.findChild(contextPath) != null)
            return;
        File app = new File(home, directoryName);
        if (!app.exists() || !app.isDirectory())
            return;
        /*
        File dd = new File(app, "/WEB-INF/web.xml");
        if (!dd.exists() || !dd.isFile() || !dd.canRead())
            return;
        */
        host.getLogger().info(sm.getString("userConfig.deploy", user));

        // Deploy the web application for this user
        try {
            Class<?> clazz = Class.forName(contextClass);
            Context context =
              (Context) clazz.newInstance();
            context.setPath(contextPath);
            context.setDocBase(app.toString());
            clazz = Class.forName(configClass);
            LifecycleListener listener =
                (LifecycleListener) clazz.newInstance();
            context.addLifecycleListener(listener);
            host.addChild(context);
        } catch (Exception e) {
            host.getLogger().error(sm.getString("userConfig.error", user), e);
        }

    }


    /**
     * Process a "start" event for this Host.
     */
    private void start() {

        if (host.getLogger().isDebugEnabled())
            host.getLogger().debug(sm.getString("userConfig.start"));

        deploy();

    }


    /**
     * Process a "stop" event for this Host.
     */
    private void stop() {

        if (host.getLogger().isDebugEnabled())
            host.getLogger().debug(sm.getString("userConfig.stop"));

    }

    /**
     * Test allow and deny rules for the provided user.
     *
     * @return <code>true</code> if this user is allowed to deploy,
     *         <code>false</code> otherwise
     */
    private boolean isDeployAllowed(String user) {
        if (deny != null && deny.matcher(user).matches()) {
            return false;
        }
        if (allow != null) {
            if (allow.matcher(user).matches()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private static class DeployUserDirectory implements Runnable {

        private UserConfig config;
        private String user;
        private String home;

        public DeployUserDirectory(UserConfig config, String user, String home) {
            this.config = config;
            this.user = user;
            this.home= home;
        }

        @Override
        public void run() {
            config.deploy(user, home);
        }
    }

}
