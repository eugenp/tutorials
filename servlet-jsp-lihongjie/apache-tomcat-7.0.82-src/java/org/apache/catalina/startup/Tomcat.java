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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Realm;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Wrapper;
import org.apache.catalina.authenticator.NonLoginAuthenticator;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.NamingContextListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.buf.UriUtil;

// TODO: lazy init for the temp dir - only when a JSP is compiled or
// get temp dir is called we need to create it. This will avoid the
// need for the baseDir

// TODO: allow contexts without a base dir - i.e.
// only programmatic. This would disable the default servlet.

/**
 * Minimal tomcat starter for embedding/unit tests.
 *
 * <p>
 * Tomcat supports multiple styles of configuration and
 * startup - the most common and stable is server.xml-based,
 * implemented in org.apache.catalina.startup.Bootstrap.
 *
 * <p>
 * This class is for use in apps that embed tomcat.
 *
 * <p>
 * Requirements:
 * <ul>
 *   <li>all tomcat classes and possibly servlets are in the classpath.
 *      ( for example all is in one big jar, or in eclipse CP, or in
 *        any other combination )</li>
 *
 *   <li>we need one temporary directory for work files</li>
 *
 *   <li>no config file is required. This class provides methods to
 *       use if you have a webapp with a web.xml file, but it is
 *       optional - you can use your own servlets.</li>
 * </ul>
 *
 * <p>
 * There are a variety of 'add' methods to configure servlets and webapps. These
 * methods, by default, create a simple in-memory security realm and apply it.
 * If you need more complex security processing, you can define a subclass of
 * this class.
 *
 * <p>
 * This class provides a set of convenience methods for configuring webapp
 * contexts, all overloads of the method <pre>addWebapp</pre>. These methods
 * create a webapp context, configure it, and then add it to a {@link Host}.
 * They do not use a global default web.xml; rather, they add a lifecycle
 * listener that adds the standard DefaultServlet, JSP processing, and welcome
 * files.
 *
 * <p>
 * In complex cases, you may prefer to use the ordinary Tomcat API to create
 * webapp contexts; for example, you might need to install a custom Loader
 * before the call to {@link Host#addChild(Container)}. To replicate the basic
 * behavior of the <pre>addWebapp</pre> methods, you may want to call two
 * methods of this class: {@link #noDefaultWebXmlPath()} and
 * {@link #getDefaultWebXmlListener()}.
 *
 * <p>
 * {@link #getDefaultRealm()} returns the simple security realm.
 *
 * <p>
 * {@link #getDefaultWebXmlListener()} returns a {@link LifecycleListener} that
 * adds the standard DefaultServlet, JSP processing, and welcome files. If you
 * add this listener, you must prevent Tomcat from applying any standard global
 * web.xml with ...
 *
 * <p>
 * {@link #noDefaultWebXmlPath()} returns a dummy pathname to configure to
 * prevent {@link ContextConfig} from trying to apply a global web.xml file.
 *
 * <p>
 * This class provides a main() and few simple CLI arguments,
 * see setters for doc. It can be used for simple tests and
 * demo.
 *
 * @see <a href="http://svn.apache.org/repos/asf/tomcat/trunk/test/org/apache/catalina/startup/TestTomcat.java">TestTomcat</a>
 * @author Costin Manolache
 */
public class Tomcat {
    // Some logging implementations use weak references for loggers so there is
    // the possibility that logging configuration could be lost if GC runs just
    // after Loggers are configured but before they are used. The purpose of
    // this Map is to retain strong references to explicitly configured loggers
    // so that configuration is not lost.
    private final Map<String, Logger> pinnedLoggers = new HashMap<String, Logger>();

    // Single engine, service, server, connector - few cases need more,
    // they can use server.xml
    protected Server server;
    protected Service service;
    protected Engine engine;
    protected Connector connector; // for more - customize the classes

    // To make it a bit easier to config for the common case
    // ( one host, one context ).
    protected Host host;

    // TODO: it's easy to add support for more hosts - but is it
    // really needed ?

    // TODO: allow use of in-memory connector

    protected int port = 8080;
    protected String hostname = "localhost";
    protected String basedir;

    // Default in-memory realm, will be set by default on the Engine. Can be
    // replaced at engine level or over-ridden at Host or Context level
    @Deprecated // Will be removed in Tomcat 8.0.x.
    protected Realm defaultRealm;
    private final Map<String, String> userPass = new HashMap<String, String>();
    private final Map<String, List<String>> userRoles =
        new HashMap<String, List<String>>();
    private final Map<String, Principal> userPrincipals =
        new HashMap<String, Principal>();

    public Tomcat() {
        ExceptionUtils.preload();
    }

    /**
     * Tomcat needs a directory for temp files. This should be the
     * first method called.
     *
     * <p>
     * By default, if this method is not called, we use:
     * <ul>
     *  <li>system properties - catalina.base, catalina.home</li>
     *  <li>$PWD/tomcat.$PORT</li>
     * </ul>
     * ( /tmp doesn't seem a good choice for security ).
     *
     * <p>
     * TODO: better default ? Maybe current dir ?
     * TODO: disable work dir if not needed ( no jsp, etc ).
     */
    public void setBaseDir(String basedir) {
        this.basedir = basedir;
    }

    /**
     * Set the port for the default connector. Must
     * be called before start().
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * The the hostname of the default host, default is
     * 'localhost'.
     */
    public void setHostname(String s) {
        hostname = s;
    }

    /**
     * This is equivalent to adding a web application to Tomcat&apos;s webapps
     * directory. The equivalent of the default web.xml will be applied  to the
     * web application and any WEB-INF/web.xml and META-INF/context.xml packaged
     * with the application will be processed normally. Normal web fragment and
     * {@link javax.servlet.ServletContainerInitializer} processing will be
     * applied.
     *
     * @throws ServletException
     */
    public Context addWebapp(String contextPath, String docBase) throws ServletException {
        return addWebapp(getHost(), contextPath, docBase);
    }


    /**
     * Add a context - programmatic mode, no web.xml used.
     *
     * <p>
     * API calls equivalent with web.xml:
     *
     * <pre>{@code
     *  // context-param
     *  ctx.addParameter("name", "value");
     *
     *
     *  // error-page
     *  ErrorPage ep = new ErrorPage();
     *  ep.setErrorCode(500);
     *  ep.setLocation("/error.html");
     *  ctx.addErrorPage(ep);
     *
     *  ctx.addMimeMapping("ext", "type");
     * }</pre>
     *
     *
     * <p>
     * Note: If you reload the Context, all your configuration will be lost. If
     * you need reload support, consider using a LifecycleListener to provide
     * your configuration.
     *
     * <p>
     * TODO: add the rest
     *
     *  @param contextPath "" for root context.
     *  @param docBase base dir for the context, for static files. Must exist,
     *  relative to the server home
     */
    public Context addContext(String contextPath, String docBase) {
        return addContext(getHost(), contextPath, docBase);
    }

    /**
     * Equivalent with
     *  <servlet><servlet-name><servlet-class>.
     *
     * <p>
     * In general it is better/faster to use the method that takes a
     * Servlet as param - this one can be used if the servlet is not
     * commonly used, and want to avoid loading all deps.
     * ( for example: jsp servlet )
     *
     * You can customize the returned servlet, ex:
     *  <pre>
     *    wrapper.addInitParameter("name", "value");
     *  </pre>
     *
     * @param contextPath   Context to add Servlet to
     * @param servletName   Servlet name (used in mappings)
     * @param servletClass  The class to be used for the Servlet
     * @return The wrapper for the servlet
     */
    public Wrapper addServlet(String contextPath,
            String servletName,
            String servletClass) {
        Container ctx = getHost().findChild(contextPath);
        return addServlet((Context) ctx, servletName, servletClass);
    }

    /**
     * Static version of {@link #addServlet(String, String, String)}
     * @param ctx           Context to add Servlet to
     * @param servletName   Servlet name (used in mappings)
     * @param servletClass  The class to be used for the Servlet
     * @return The wrapper for the servlet
     */
    public static Wrapper addServlet(Context ctx,
                                      String servletName,
                                      String servletClass) {
        // will do class for name and set init params
        Wrapper sw = ctx.createWrapper();
        sw.setServletClass(servletClass);
        sw.setName(servletName);
        ctx.addChild(sw);

        return sw;
    }

    /**
     * Add an existing Servlet to the context with no class.forName or
     * initialisation.
     * @param contextPath   Context to add Servlet to
     * @param servletName   Servlet name (used in mappings)
     * @param servlet       The Servlet to add
     * @return The wrapper for the servlet
     */
    public Wrapper addServlet(String contextPath,
            String servletName,
            Servlet servlet) {
        Container ctx = getHost().findChild(contextPath);
        return addServlet((Context) ctx, servletName, servlet);
    }

    /**
     * Static version of {@link #addServlet(String, String, Servlet)}.
     * @param ctx           Context to add Servlet to
     * @param servletName   Servlet name (used in mappings)
     * @param servlet       The Servlet to add
     * @return The wrapper for the servlet
     */
    public static Wrapper addServlet(Context ctx,
                                      String servletName,
                                      Servlet servlet) {
        // will do class for name and set init params
        Wrapper sw = new ExistingStandardWrapper(servlet);
        sw.setName(servletName);
        ctx.addChild(sw);

        return sw;
    }


    /**
     * Initialise the server.
     *
     * @throws LifecycleException
     */
    public void init() throws LifecycleException {
        getServer();
        getConnector();
        server.init();
    }


    /**
     * Start the server.
     *
     * @throws LifecycleException
     */
    public void start() throws LifecycleException {
        getServer();
        getConnector();
        server.start();
    }

    /**
     * Stop the server.
     *
     * @throws LifecycleException
     */
    public void stop() throws LifecycleException {
        getServer();
        server.stop();
    }


    /**
     * Destroy the server. This object cannot be used once this method has been
     * called.
     */
    public void destroy() throws LifecycleException {
        getServer();
        server.destroy();
        // Could null out objects here
    }

    /**
     * Add a user for the in-memory realm. All created apps use this
     * by default, can be replaced using setRealm().
     *
     */
    public void addUser(String user, String pass) {
        userPass.put(user, pass);
    }

    /**
     * @see #addUser(String, String)
     */
    public void addRole(String user, String role) {
        List<String> roles = userRoles.get(user);
        if (roles == null) {
            roles = new ArrayList<String>();
            userRoles.put(user, roles);
        }
        roles.add(role);
    }

    // ------- Extra customization -------
    // You can tune individual tomcat objects, using internal APIs

    /**
     * Get the default http connector. You can set more
     * parameters - the port is already initialized.
     *
     * <p>
     * Alternatively, you can construct a Connector and set any params,
     * then call addConnector(Connector)
     *
     * @return A connector object that can be customized
     */
    public Connector getConnector() {
        getServer();
        if (connector != null) {
            return connector;
        }

        // The same as in standard Tomcat configuration.
        // This creates an APR HTTP connector if AprLifecycleListener has been
        // configured (created) and Tomcat Native library is available.
        // Otherwise it creates a BIO HTTP connector (Http11Protocol).
        connector = new Connector("HTTP/1.1");
        connector.setPort(port);
        service.addConnector( connector );
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * Get the service object. Can be used to add more
     * connectors and few other global settings.
     */
    public Service getService() {
        getServer();
        return service;
    }

    /**
     * Sets the current host - all future webapps will
     * be added to this host. When tomcat starts, the
     * host will be the default host.
     *
     * @param host
     */
    public void setHost(Host host) {
        this.host = host;
    }

    public Host getHost() {
        if (host == null) {
            host = new StandardHost();
            host.setName(hostname);

            getEngine().addChild( host );
        }
        return host;
    }

    /**
     * Set a custom realm for auth. If not called, a simple
     * default will be used, using an internal map.
     *
     * Must be called before adding a context.
     *
     * @deprecated Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public void setDefaultRealm(Realm realm) {
        defaultRealm = realm;
    }


    /**
     * Access to the engine, for further customization.
     */
    public Engine getEngine() {
        if(engine == null ) {
            getServer();
            engine = new StandardEngine();
            engine.setName( "Tomcat" );
            engine.setDefaultHost(hostname);
            if (defaultRealm == null) {
                initSimpleAuth();
            }
            engine.setRealm(defaultRealm);
            service.setContainer(engine);
        }
        return engine;
    }

    /**
     * Get the server object. You can add listeners and few more
     * customizations. JNDI is disabled by default.
     */
    public Server getServer() {

        if (server != null) {
            return server;
        }

        initBaseDir();

        System.setProperty("catalina.useNaming", "false");

        server = new StandardServer();
        server.setPort( -1 );

        service = new StandardService();
        service.setName("Tomcat");
        server.addService( service );
        return server;
    }

    public Context addContext(Host host, String contextPath, String dir) {
        return addContext(host, contextPath, contextPath, dir);
    }

    public Context addContext(Host host, String contextPath, String contextName,
            String dir) {
        silence(host, contextName);
        Context ctx = createContext(host, contextPath);
        ctx.setName(contextName);
        ctx.setPath(contextPath);
        ctx.setDocBase(dir);
        ctx.addLifecycleListener(new FixContextListener());

        if (host == null) {
            getHost().addChild(ctx);
        } else {
            host.addChild(ctx);
        }
        return ctx;
    }

    public Context addWebapp(Host host, String contextPath, String docBase) {
        LifecycleListener listener = null;
        try {
            Class<?> clazz = Class.forName(getHost().getConfigClass());
            listener = (LifecycleListener) clazz.newInstance();
        } catch (Exception e) {
            // Wrap in IAE since we can't easily change the method signature to
            // to throw the specific checked exceptions
            throw new IllegalArgumentException(e);
        }

        return addWebapp(host, contextPath, docBase, listener);
    }

    /**
     * @see #addWebapp(String, String)
     *
     * @param name Ignored. The contextPath will be used
     *
     * @deprecated Use {@link #addWebapp(Host, String, String)}
     */
    @Deprecated
    public Context addWebapp(Host host, String contextPath, String name, String docBase) {
        return addWebapp(host,  contextPath, docBase);
    }

    /**
     * @param host The host in which the context will be deployed
     * @param contextPath The context mapping to use, "" for root context.
     * @param docBase Base directory for the context, for static files.
     *  Must exist, relative to the server home
     * @param config Custom context configurator helper
     * @return the deployed context
     * @see #addWebapp(String, String)
     */
    public Context addWebapp(Host host, String contextPath, String docBase,
            LifecycleListener config) {

        silence(host, contextPath);

        Context ctx = createContext(host, contextPath);
        ctx.setPath(contextPath);
        ctx.setDocBase(docBase);
        ctx.addLifecycleListener(getDefaultWebXmlListener());
        ctx.setConfigFile(getWebappConfigFile(docBase, contextPath));

        ctx.addLifecycleListener(config);

        if (config instanceof ContextConfig) {
            // prevent it from looking ( if it finds one - it'll have dup error )
            ((ContextConfig) config).setDefaultWebXml(noDefaultWebXmlPath());
        }

        if (host == null) {
            getHost().addChild(ctx);
        } else {
            host.addChild(ctx);
        }

        return ctx;
    }

    /**
     * Return a listener that provides the required configuration items for JSP
     * processing. From the standard Tomcat global web.xml. Pass this to
     * {@link Context#addLifecycleListener(LifecycleListener)} and then pass the
     * result of {@link #noDefaultWebXmlPath()} to
     * {@link ContextConfig#setDefaultWebXml(String)}.
     * @return a listener object that configures default JSP processing.
     */
    public LifecycleListener getDefaultWebXmlListener() {
        return new DefaultWebXmlListener();
    }

    /**
     * @return a pathname to pass to
     * {@link ContextConfig#setDefaultWebXml(String)} when using
     * {@link #getDefaultWebXmlListener()}.
     */
    public String noDefaultWebXmlPath() {
        return Constants.NoDefaultWebXml;
    }

    /**
     * For complex configurations, this accessor allows callers of this class
     * to obtain the simple realm created by default.
     * @return the simple in-memory realm created by default.
     * @deprecated Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public Realm getDefaultRealm() {
        if (defaultRealm == null) {
            initSimpleAuth();
        }
        return defaultRealm;
    }


    // ---------- Helper methods and classes -------------------

    /**
     * Create an in-memory realm. You can replace it for contexts with a real
     * one. The Realm created here will be added to the Engine by default and
     * may be replaced at the Engine level or over-ridden (as per normal Tomcat
     * behaviour) at the Host or Context level.
     * @deprecated Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected void initSimpleAuth() {
        defaultRealm = new RealmBase() {
            @Override
            protected String getName() {
                return "Simple";
            }

            @Override
            protected String getPassword(String username) {
                return userPass.get(username);
            }

            @Override
            protected Principal getPrincipal(String username) {
                Principal p = userPrincipals.get(username);
                if (p == null) {
                    String pass = userPass.get(username);
                    if (pass != null) {
                        p = new GenericPrincipal(username, pass,
                                userRoles.get(username));
                        userPrincipals.put(username, p);
                    }
                }
                return p;
            }

        };
    }

    protected void initBaseDir() {
        String catalinaHome = System.getProperty(Globals.CATALINA_HOME_PROP);
        if (basedir == null) {
            basedir = System.getProperty(Globals.CATALINA_BASE_PROP);
        }
        if (basedir == null) {
            basedir = catalinaHome;
        }
        if (basedir == null) {
            // Create a temp dir.
            basedir = System.getProperty("user.dir") +
                "/tomcat." + port;
            File home = new File(basedir);
            home.mkdir();
            if (!home.isAbsolute()) {
                try {
                    basedir = home.getCanonicalPath();
                } catch (IOException e) {
                    basedir = home.getAbsolutePath();
                }
            }
        }
        if (catalinaHome == null) {
            System.setProperty(Globals.CATALINA_HOME_PROP, basedir);
        }
        System.setProperty(Globals.CATALINA_BASE_PROP, basedir);
    }

    static final String[] silences = new String[] {
        "org.apache.coyote.http11.Http11Protocol",
        "org.apache.catalina.core.StandardService",
        "org.apache.catalina.core.StandardEngine",
        "org.apache.catalina.startup.ContextConfig",
        "org.apache.catalina.core.ApplicationContext",
        "org.apache.catalina.core.AprLifecycleListener"
    };

    private boolean silent = false;

    /**
     * Controls if the loggers will be silenced or not.
     * @param silent    <code>true</code> sets the log level to WARN for the
     *                  loggers that log information on Tomcat start up. This
     *                  prevents the usual startup information being logged.
     *                  <code>false</code> sets the log level to the default
     *                  level of INFO.
     */
    public void setSilent(boolean silent) {
        this.silent = silent;
        for (String s : silences) {
            Logger logger = Logger.getLogger(s);
            pinnedLoggers.put(s, logger);
            if (silent) {
                logger.setLevel(Level.WARNING);
            } else {
                logger.setLevel(Level.INFO);
            }
        }
    }

    private void silence(Host host, String contextPath) {
        String loggerName = getLoggerName(host, contextPath);
        Logger logger = Logger.getLogger(loggerName);
        pinnedLoggers.put(loggerName, logger);
        if (silent) {
            logger.setLevel(Level.WARNING);
        } else {
            logger.setLevel(Level.INFO);
        }
    }


    /*
     * Uses essentially the same logic as {@link ContainerBase#logName()}.
     */
    private String getLoggerName(Host host, String contextName) {
        if (host == null) {
            host = getHost();
        }
        StringBuilder loggerName = new StringBuilder();
        loggerName.append(ContainerBase.class.getName());
        loggerName.append(".[");
        // Engine name
        loggerName.append(host.getParent().getName());
        loggerName.append("].[");
        // Host name
        loggerName.append(host.getName());
        loggerName.append("].[");
        // Context name
        if (contextName == null || contextName.equals("")) {
            loggerName.append("/");
        } else if (contextName.startsWith("##")) {
            loggerName.append("/");
            loggerName.append(contextName);
        }
        loggerName.append(']');

        return loggerName.toString();
    }

    /**
     * Create the configured {@link Context} for the given <code>host</code>.
     * The default constructor of the class that was configured with
     * {@link StandardHost#setContextClass(String)} will be used
     *
     * @param host
     *            host for which the {@link Context} should be created, or
     *            <code>null</code> if default host should be used
     * @param url
     *            path of the webapp which should get the {@link Context}
     * @return newly created {@link Context}
     */
    private Context createContext(Host host, String url) {
        String contextClass = StandardContext.class.getName();
        if (host == null) {
            host = this.getHost();
        }
        if (host instanceof StandardHost) {
            contextClass = ((StandardHost) host).getContextClass();
        }
        try {
            return (Context) Class.forName(contextClass).getConstructor()
                    .newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "Can't instantiate context-class " + contextClass
                            + " for host " + host + " and url "
                            + url, e);
        }
    }

    /**
     * Enables JNDI naming which is disabled by default. Server must implement
     * {@link Lifecycle} in order for the {@link NamingContextListener} to be
     * used.
     *
     */
    public void enableNaming() {
        // Make sure getServer() has been called as that is where naming is
        // disabled
        getServer();
        server.addLifecycleListener(new NamingContextListener());

        System.setProperty("catalina.useNaming", "true");

        String value = "org.apache.naming";
        String oldValue =
            System.getProperty(javax.naming.Context.URL_PKG_PREFIXES);
        if (oldValue != null) {
            if (oldValue.contains(value)) {
                value = oldValue;
            } else {
                value = value + ":" + oldValue;
            }
        }
        System.setProperty(javax.naming.Context.URL_PKG_PREFIXES, value);

        value = System.getProperty
            (javax.naming.Context.INITIAL_CONTEXT_FACTORY);
        if (value == null) {
            System.setProperty
                (javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                 "org.apache.naming.java.javaURLContextFactory");
        }
    }

    /**
     * Provide default configuration for a context. This is the programmatic
     * equivalent of the default web.xml.
     *
     *  TODO: in normal Tomcat, if default-web.xml is not found, use this
     *  method
     *
     * @param contextPath   The context to set the defaults for
     */
    public void initWebappDefaults(String contextPath) {
        Container ctx = getHost().findChild(contextPath);
        initWebappDefaults((Context) ctx);
    }

    /**
     * Static version of {@link #initWebappDefaults(String)}
     * @param ctx   The context to set the defaults for
     */
    public static void initWebappDefaults(Context ctx) {
        // Default servlet
        Wrapper servlet = addServlet(
                ctx, "default", "org.apache.catalina.servlets.DefaultServlet");
        servlet.setLoadOnStartup(1);
        servlet.setOverridable(true);

        // JSP servlet (by class name - to avoid loading all deps)
        servlet = addServlet(
                ctx, "jsp", "org.apache.jasper.servlet.JspServlet");
        servlet.addInitParameter("fork", "false");
        servlet.setLoadOnStartup(3);
        servlet.setOverridable(true);

        // Servlet mappings
        ctx.addServletMapping("/", "default");
        ctx.addServletMapping("*.jsp", "jsp");
        ctx.addServletMapping("*.jspx", "jsp");

        // Sessions
        ctx.setSessionTimeout(30);

        // MIME mappings
        for (int i = 0; i < DEFAULT_MIME_MAPPINGS.length;) {
            ctx.addMimeMapping(DEFAULT_MIME_MAPPINGS[i++],
                    DEFAULT_MIME_MAPPINGS[i++]);
        }

        // Welcome files
        ctx.addWelcomeFile("index.html");
        ctx.addWelcomeFile("index.htm");
        ctx.addWelcomeFile("index.jsp");
    }


    /**
     * Fix startup sequence - required if you don't use web.xml.
     *
     * <p>
     * The start() method in context will set 'configured' to false - and
     * expects a listener to set it back to true.
     */
    public static class FixContextListener implements LifecycleListener {

        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            try {
                Context context = (Context) event.getLifecycle();
                if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
                    context.setConfigured(true);
                }
                // LoginConfig is required to process @ServletSecurity
                // annotations
                if (context.getLoginConfig() == null) {
                    context.setLoginConfig(
                            new LoginConfig("NONE", null, null, null));
                    context.getPipeline().addValve(new NonLoginAuthenticator());
                }
            } catch (ClassCastException e) {
                return;
            }
        }

    }


    /**
     * Fix reload - required if reloading and using programmatic configuration.
     * When a context is reloaded, any programmatic configuration is lost. This
     * listener sets the equivalent of conf/web.xml when the context starts.
     */
    public static class DefaultWebXmlListener implements LifecycleListener {
        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            if (Lifecycle.BEFORE_START_EVENT.equals(event.getType())) {
                initWebappDefaults((Context) event.getLifecycle());
            }
        }
    }


    /**
     * Helper class for wrapping existing servlets. This disables servlet
     * lifecycle and normal reloading, but also reduces overhead and provide
     * more direct control over the servlet.
     */
    public static class ExistingStandardWrapper extends StandardWrapper {
        private final Servlet existing;

        @SuppressWarnings("deprecation")
        public ExistingStandardWrapper( Servlet existing ) {
            this.existing = existing;
            if (existing instanceof javax.servlet.SingleThreadModel) {
                singleThreadModel = true;
                instancePool = new Stack<Servlet>();
            }
        }
        @Override
        public synchronized Servlet loadServlet() throws ServletException {
            if (singleThreadModel) {
                Servlet instance;
                try {
                    instance = existing.getClass().newInstance();
                } catch (InstantiationException e) {
                    throw new ServletException(e);
                } catch (IllegalAccessException e) {
                    throw new ServletException(e);
                }
                instance.init(facade);
                return instance;
            } else {
                if (!instanceInitialized) {
                    existing.init(facade);
                    instanceInitialized = true;
                }
                return existing;
            }
        }
        @Override
        public long getAvailable() {
            return 0;
        }
        @Override
        public boolean isUnavailable() {
            return false;
        }
        @Override
        public Servlet getServlet() {
            return existing;
        }
        @Override
        public String getServletClass() {
            return existing.getClass().getName();
        }
    }

    /**
     * TODO: would a properties resource be better ? Or just parsing
     * /etc/mime.types ?
     * This is needed because we don't use the default web.xml, where this
     * is encoded.
     */
    private static final String[] DEFAULT_MIME_MAPPINGS = {
        "abs", "audio/x-mpeg",
        "ai", "application/postscript",
        "aif", "audio/x-aiff",
        "aifc", "audio/x-aiff",
        "aiff", "audio/x-aiff",
        "aim", "application/x-aim",
        "art", "image/x-jg",
        "asf", "video/x-ms-asf",
        "asx", "video/x-ms-asf",
        "au", "audio/basic",
        "avi", "video/x-msvideo",
        "avx", "video/x-rad-screenplay",
        "bcpio", "application/x-bcpio",
        "bin", "application/octet-stream",
        "bmp", "image/bmp",
        "body", "text/html",
        "cdf", "application/x-cdf",
        "cer", "application/pkix-cert",
        "class", "application/java",
        "cpio", "application/x-cpio",
        "csh", "application/x-csh",
        "css", "text/css",
        "dib", "image/bmp",
        "doc", "application/msword",
        "dtd", "application/xml-dtd",
        "dv", "video/x-dv",
        "dvi", "application/x-dvi",
        "eps", "application/postscript",
        "etx", "text/x-setext",
        "exe", "application/octet-stream",
        "gif", "image/gif",
        "gtar", "application/x-gtar",
        "gz", "application/x-gzip",
        "hdf", "application/x-hdf",
        "hqx", "application/mac-binhex40",
        "htc", "text/x-component",
        "htm", "text/html",
        "html", "text/html",
        "ief", "image/ief",
        "jad", "text/vnd.sun.j2me.app-descriptor",
        "jar", "application/java-archive",
        "java", "text/x-java-source",
        "jnlp", "application/x-java-jnlp-file",
        "jpe", "image/jpeg",
        "jpeg", "image/jpeg",
        "jpg", "image/jpeg",
        "js", "application/javascript",
        "jsf", "text/plain",
        "jspf", "text/plain",
        "kar", "audio/midi",
        "latex", "application/x-latex",
        "m3u", "audio/x-mpegurl",
        "mac", "image/x-macpaint",
        "man", "text/troff",
        "mathml", "application/mathml+xml",
        "me", "text/troff",
        "mid", "audio/midi",
        "midi", "audio/midi",
        "mif", "application/x-mif",
        "mov", "video/quicktime",
        "movie", "video/x-sgi-movie",
        "mp1", "audio/mpeg",
        "mp2", "audio/mpeg",
        "mp3", "audio/mpeg",
        "mp4", "video/mp4",
        "mpa", "audio/mpeg",
        "mpe", "video/mpeg",
        "mpeg", "video/mpeg",
        "mpega", "audio/x-mpeg",
        "mpg", "video/mpeg",
        "mpv2", "video/mpeg2",
        "nc", "application/x-netcdf",
        "oda", "application/oda",
        "odb", "application/vnd.oasis.opendocument.database",
        "odc", "application/vnd.oasis.opendocument.chart",
        "odf", "application/vnd.oasis.opendocument.formula",
        "odg", "application/vnd.oasis.opendocument.graphics",
        "odi", "application/vnd.oasis.opendocument.image",
        "odm", "application/vnd.oasis.opendocument.text-master",
        "odp", "application/vnd.oasis.opendocument.presentation",
        "ods", "application/vnd.oasis.opendocument.spreadsheet",
        "odt", "application/vnd.oasis.opendocument.text",
        "otg", "application/vnd.oasis.opendocument.graphics-template",
        "oth", "application/vnd.oasis.opendocument.text-web",
        "otp", "application/vnd.oasis.opendocument.presentation-template",
        "ots", "application/vnd.oasis.opendocument.spreadsheet-template ",
        "ott", "application/vnd.oasis.opendocument.text-template",
        "ogx", "application/ogg",
        "ogv", "video/ogg",
        "oga", "audio/ogg",
        "ogg", "audio/ogg",
        "spx", "audio/ogg",
        "flac", "audio/flac",
        "anx", "application/annodex",
        "axa", "audio/annodex",
        "axv", "video/annodex",
        "xspf", "application/xspf+xml",
        "pbm", "image/x-portable-bitmap",
        "pct", "image/pict",
        "pdf", "application/pdf",
        "pgm", "image/x-portable-graymap",
        "pic", "image/pict",
        "pict", "image/pict",
        "pls", "audio/x-scpls",
        "png", "image/png",
        "pnm", "image/x-portable-anymap",
        "pnt", "image/x-macpaint",
        "ppm", "image/x-portable-pixmap",
        "ppt", "application/vnd.ms-powerpoint",
        "pps", "application/vnd.ms-powerpoint",
        "ps", "application/postscript",
        "psd", "image/vnd.adobe.photoshop",
        "qt", "video/quicktime",
        "qti", "image/x-quicktime",
        "qtif", "image/x-quicktime",
        "ras", "image/x-cmu-raster",
        "rdf", "application/rdf+xml",
        "rgb", "image/x-rgb",
        "rm", "application/vnd.rn-realmedia",
        "roff", "text/troff",
        "rtf", "application/rtf",
        "rtx", "text/richtext",
        "sh", "application/x-sh",
        "shar", "application/x-shar",
        /*"shtml", "text/x-server-parsed-html",*/
        "sit", "application/x-stuffit",
        "snd", "audio/basic",
        "src", "application/x-wais-source",
        "sv4cpio", "application/x-sv4cpio",
        "sv4crc", "application/x-sv4crc",
        "svg", "image/svg+xml",
        "svgz", "image/svg+xml",
        "swf", "application/x-shockwave-flash",
        "t", "text/troff",
        "tar", "application/x-tar",
        "tcl", "application/x-tcl",
        "tex", "application/x-tex",
        "texi", "application/x-texinfo",
        "texinfo", "application/x-texinfo",
        "tif", "image/tiff",
        "tiff", "image/tiff",
        "tr", "text/troff",
        "tsv", "text/tab-separated-values",
        "txt", "text/plain",
        "ulw", "audio/basic",
        "ustar", "application/x-ustar",
        "vxml", "application/voicexml+xml",
        "xbm", "image/x-xbitmap",
        "xht", "application/xhtml+xml",
        "xhtml", "application/xhtml+xml",
        "xls", "application/vnd.ms-excel",
        "xml", "application/xml",
        "xpm", "image/x-xpixmap",
        "xsl", "application/xml",
        "xslt", "application/xslt+xml",
        "xul", "application/vnd.mozilla.xul+xml",
        "xwd", "image/x-xwindowdump",
        "vsd", "application/vnd.visio",
        "wav", "audio/x-wav",
        "wbmp", "image/vnd.wap.wbmp",
        "wml", "text/vnd.wap.wml",
        "wmlc", "application/vnd.wap.wmlc",
        "wmls", "text/vnd.wap.wmlsc",
        "wmlscriptc", "application/vnd.wap.wmlscriptc",
        "wmv", "video/x-ms-wmv",
        "wrl", "model/vrml",
        "wspolicy", "application/wspolicy+xml",
        "Z", "application/x-compress",
        "z", "application/x-compress",
        "zip", "application/zip"
    };

    protected URL getWebappConfigFile(String path, String contextName) {
        File docBase = new File(path);
        if (docBase.isDirectory()) {
            return getWebappConfigFileFromDirectory(docBase, contextName);
        } else {
            return getWebappConfigFileFromJar(docBase, contextName);
        }
    }

    private URL getWebappConfigFileFromDirectory(File docBase, String contextName) {
        URL result = null;
        File webAppContextXml = new File(docBase, Constants.ApplicationContextXml);
        if (webAppContextXml.exists()) {
            try {
                result = webAppContextXml.toURI().toURL();
            } catch (MalformedURLException e) {
                Logger.getLogger(getLoggerName(getHost(), contextName)).log(Level.WARNING,
                        "Unable to determine web application context.xml " + docBase, e);
            }
        }
        return result;
    }

    private URL getWebappConfigFileFromJar(File docBase, String contextName) {
        URL result = null;
        JarFile jar = null;
        try {
            jar = new JarFile(docBase);
            JarEntry entry = jar.getJarEntry(Constants.ApplicationContextXml);
            if (entry != null) {
                result = UriUtil.buildJarUrl(docBase, Constants.ApplicationContextXml);
            }
        } catch (IOException e) {
            Logger.getLogger(getLoggerName(getHost(), contextName)).log(Level.WARNING,
                    "Unable to determine web application context.xml " + docBase, e);
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return result;
    }
}
