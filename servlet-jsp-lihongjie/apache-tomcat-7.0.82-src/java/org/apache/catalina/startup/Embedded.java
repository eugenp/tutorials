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
import java.net.InetAddress;
import java.util.HashMap;

import org.apache.catalina.Authenticator;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.Realm;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.security.SecurityConfig;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.log.SystemLogHandler;
import org.apache.tomcat.util.res.StringManager;


/**
 * Convenience class to embed a Catalina servlet container environment
 * inside another application.  You must call the methods of this class in the
 * following order to ensure correct operation.
 *
 * <ul>
 * <li>Instantiate a new instance of this class.</li>
 * <li>Set the relevant properties of this object itself.  In particular,
 *     you will want to establish the default Logger to be used, as well
 *     as the default Realm if you are using container-managed security.</li>
 * <li>Call <code>createEngine()</code> to create an Engine object, and then
 *     call its property setters as desired.</li>
 * <li>Call <code>createHost()</code> to create at least one virtual Host
 *     associated with the newly created Engine, and then call its property
 *     setters as desired.  After you customize this Host, add it to the
 *     corresponding Engine with <code>engine.addChild(host)</code>.</li>
 * <li>Call <code>createContext()</code> to create at least one Context
 *     associated with each newly created Host, and then call its property
 *     setters as desired.  You <strong>SHOULD</strong> create a Context with
 *     a pathname equal to a zero-length string, which will be used to process
 *     all requests not mapped to some other Context.  After you customize
 *     this Context, add it to the corresponding Host with
 *     <code>host.addChild(context)</code>.</li>
 * <li>Call <code>addEngine()</code> to attach this Engine to the set of
 *     defined Engines for this object.</li>
 * <li>Call <code>createConnector()</code> to create at least one TCP/IP
 *     connector, and then call its property setters as desired.</li>
 * <li>Call <code>addConnector()</code> to attach this Connector to the set
 *     of defined Connectors for this object.  The added Connector will use
 *     the most recently added Engine to process its received requests.</li>
 * <li>Repeat the above series of steps as often as required (although there
 *     will typically be only one Engine instance created).</li>
 * <li>Call <code>start()</code> to initiate normal operations of all the
 *     attached components.</li>
 * </ul>
 *
 * After normal operations have begun, you can add and remove Connectors,
 * Engines, Hosts, and Contexts on the fly.  However, once you have removed
 * a particular component, it must be thrown away -- you can create a new one
 * with the same characteristics if you merely want to do a restart.
 * <p>
 * To initiate a normal shutdown, call the <code>stop()</code> method of
 * this object.
 * <p>
 * @see org.apache.catalina.startup.Bootstrap#main For a complete example
 * of how Tomcat is set up and launched as an Embedded application.
 *
 * @author Craig R. McClanahan
 * 
 * @deprecated Use {@link Tomcat} instead.
 */

@Deprecated
public class Embedded  extends StandardService {
    private static final Log log = LogFactory.getLog(Embedded.class);

    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this class with default properties.
     */
    public Embedded() {

        this(null);

    }


    /**
     * Construct a new instance of this class with specified properties.
     *
     * @param realm Realm implementation to be inherited by all components
     *  (unless overridden further down the container hierarchy)
     */
    public Embedded(Realm realm) {

        super();
        setRealm(realm);
        setSecurityProtection();
        
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Is naming enabled ?
     */
    protected boolean useNaming = true;


    /**
     * Is standard streams redirection enabled ?
     */
    protected boolean redirectStreams = true;


    /**
     * The set of Engines that have been deployed in this server.  Normally
     * there will only be one.
     */
    protected Engine engines[] = new Engine[0];


    /**
     * Custom mappings of login methods to authenticators
     */
    protected volatile HashMap<String,Authenticator> authenticators;


    /**
     * Descriptive information about this server implementation.
     */
    protected static final String info =
        "org.apache.catalina.startup.Embedded/1.0";


    /**
     * The default realm to be used by all containers associated with
     * this component.
     */
    protected Realm realm = null;


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * Use await.
     */
    protected boolean await = false;


    // ------------------------------------------------------------- Properties


    /**
     * Return true if naming is enabled.
     */
    public boolean isUseNaming() {

        return (this.useNaming);

    }


    /**
     * Enables or disables naming support.
     *
     * @param useNaming The new use naming value
     */
    public void setUseNaming(boolean useNaming) {

        boolean oldUseNaming = this.useNaming;
        this.useNaming = useNaming;
        support.firePropertyChange("useNaming", Boolean.valueOf(oldUseNaming),
                                   Boolean.valueOf(this.useNaming));

    }


    /**
     * Return true if redirection of standard streams is enabled.
     */
    public boolean isRedirectStreams() {

        return (this.redirectStreams);

    }


    /**
     * Enables or disables redirection.
     *
     * @param redirectStreams The new redirection value
     */
    public void setRedirectStreams(boolean redirectStreams) {

        boolean oldRedirectStreams = this.redirectStreams;
        this.redirectStreams = redirectStreams;
        support.firePropertyChange("redirectStreams",
                                   Boolean.valueOf(oldRedirectStreams),
                                   Boolean.valueOf(this.redirectStreams));

    }


    /**
     * Return the default Realm for our Containers.
     */
    public Realm getRealm() {

        return (this.realm);

    }


    /**
     * Set the default Realm for our Containers.
     *
     * @param realm The new default realm
     */
    public void setRealm(Realm realm) {

        Realm oldRealm = this.realm;
        this.realm = realm;
        support.firePropertyChange("realm", oldRealm, this.realm);

    }

    public void setAwait(boolean b) {
        await = b;
    }

    public boolean isAwait() {
        return await;
    }

    public void setCatalinaHome(String s) {
        System.setProperty(Globals.CATALINA_HOME_PROP, s);
    }

    public void setCatalinaBase(String s) {
        System.setProperty(Globals.CATALINA_BASE_PROP, s);
    }

    public String getCatalinaHome() {
        return System.getProperty(Globals.CATALINA_HOME_PROP);
    }

    public String getCatalinaBase() {
        return System.getProperty(Globals.CATALINA_BASE_PROP);
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Add a new Connector to the set of defined Connectors.  The newly
     * added Connector will be associated with the most recently added Engine.
     *
     * @param connector The connector to be added
     *
     * @exception IllegalStateException if no engines have been added yet
     */
    @Override
    public synchronized void addConnector(Connector connector) {

        if( log.isDebugEnabled() ) {
            log.debug("Adding connector (" + connector.getInfo() + ")");
        }

        // Make sure we have a Container to send requests to
        if (engines.length < 1)
            throw new IllegalStateException
                (sm.getString("embedded.noEngines"));

        /*
         * Add the connector. This will set the connector's container to the
         * most recently added Engine
         */
        super.addConnector(connector);
    }


    /**
     * Add a new Engine to the set of defined Engines.
     *
     * @param engine The engine to be added
     */
    public synchronized void addEngine(Engine engine) {

        if( log.isDebugEnabled() )
            log.debug("Adding engine (" + engine.getInfo() + ")");

        // Add this Engine to our set of defined Engines
        Engine results[] = new Engine[engines.length + 1];
        for (int i = 0; i < engines.length; i++)
            results[i] = engines[i];
        results[engines.length] = engine;
        engines = results;

        // Start this Engine if necessary
        if (getState().isAvailable()) {
            try {
                engine.start();
            } catch (LifecycleException e) {
                log.error("Engine.start", e);
            }
        }

        this.container = engine;
    }


    /**
     * Create, configure, and return a new TCP/IP socket connector
     * based on the specified properties.
     *
     * @param address InetAddress to bind to, or <code>null</code> if the
     * connector is supposed to bind to all addresses on this server
     * @param port Port number to listen to
     * @param secure true if the generated connector is supposed to be
     * SSL-enabled, and false otherwise
     */
    public Connector createConnector(InetAddress address, int port,
                                     boolean secure) {
        return createConnector(address != null? address.toString() : null,
                port, secure);
    }

    public Connector createConnector(String address, int port,
                                     boolean secure) {
        String protocol = "http";
        if (secure) {
            protocol = "https";
        }

        return createConnector(address, port, protocol);
    }


    public Connector createConnector(InetAddress address, int port,
                                     String protocol) {
        return createConnector(address != null? address.toString() : null,
                port, protocol);
    }

    public Connector createConnector(String address, int port,
            String protocol) {

        Connector connector = null;

        if (address != null) {
            /*
             * InetAddress.toString() returns a string of the form
             * "<hostname>/<literal_IP>". Get the latter part, so that the
             * address can be parsed (back) into an InetAddress using
             * InetAddress.getByName().
             */
            int index = address.indexOf('/');
            if (index != -1) {
                address = address.substring(index + 1);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Creating connector for address='" +
                      ((address == null) ? "ALL" : address) +
                      "' port='" + port + "' protocol='" + protocol + "'");
        }

        try {

            if (protocol.equals("ajp")) {
                connector = new Connector("org.apache.coyote.ajp.AjpProtocol");
            } else if (protocol.equals("memory")) {
                connector = new Connector("org.apache.coyote.memory.MemoryProtocolHandler");
            } else if (protocol.equals("http")) {
                connector = new Connector();
            } else if (protocol.equals("https")) {
                connector = new Connector();
                connector.setScheme("https");
                connector.setSecure(true);
                connector.setProperty("SSLEnabled","true");
                // FIXME !!!! SET SSL PROPERTIES
            } else {
                connector = new Connector(protocol);
            }

            if (address != null) {
                IntrospectionUtils.setProperty(connector, "address", 
                                               "" + address);
            }
            IntrospectionUtils.setProperty(connector, "port", "" + port);

        } catch (Exception e) {
            log.error("Couldn't create connector.");
        } 

        return (connector);

    }

    /**
     * Create, configure, and return a Context that will process all
     * HTTP requests received from one of the associated Connectors,
     * and directed to the specified context path on the virtual host
     * to which this Context is connected.
     * <p>
     * After you have customized the properties, listeners, and Valves
     * for this Context, you must attach it to the corresponding Host
     * by calling:
     * <pre>
     *   host.addChild(context);
     * </pre>
     * which will also cause the Context to be started if the Host has
     * already been started.
     *
     * @param path Context path of this application ("" for the default
     *  application for this host, must start with a slash otherwise)
     * @param docBase Absolute pathname to the document base directory
     *  for this web application
     *
     * @exception IllegalArgumentException if an invalid parameter
     *  is specified
     */
    public Context createContext(String path, String docBase) {

        if( log.isDebugEnabled() )
            log.debug("Creating context '" + path + "' with docBase '" +
                       docBase + "'");

        StandardContext context = new StandardContext();

        context.setDocBase(docBase);
        context.setPath(path);

        ContextConfig config = new ContextConfig();
        config.setCustomAuthenticators(authenticators);
        context.addLifecycleListener(config);

        return (context);

    }


    /**
     * Create, configure, and return an Engine that will process all
     * HTTP requests received from one of the associated Connectors,
     * based on the specified properties.
     */
    public Engine createEngine() {

        if( log.isDebugEnabled() )
            log.debug("Creating engine");

        StandardEngine engine = new StandardEngine();

        // Default host will be set to the first host added
        engine.setRealm(realm);         // Inherited by all children

        return (engine);

    }


    /**
     * Create, configure, and return a Host that will process all
     * HTTP requests received from one of the associated Connectors,
     * and directed to the specified virtual host.
     * <p>
     * After you have customized the properties, listeners, and Valves
     * for this Host, you must attach it to the corresponding Engine
     * by calling:
     * <pre>
     *   engine.addChild(host);
     * </pre>
     * which will also cause the Host to be started if the Engine has
     * already been started.  If this is the default (or only) Host you
     * will be defining, you may also tell the Engine to pass all requests
     * not assigned to another virtual host to this one:
     * <pre>
     *   engine.setDefaultHost(host.getName());
     * </pre>
     *
     * @param name Canonical name of this virtual host
     * @param appBase Absolute pathname to the application base directory
     *  for this virtual host
     *
     * @exception IllegalArgumentException if an invalid parameter
     *  is specified
     */
    public Host createHost(String name, String appBase) {

        if( log.isDebugEnabled() )
            log.debug("Creating host '" + name + "' with appBase '" +
                       appBase + "'");

        StandardHost host = new StandardHost();

        host.setAppBase(appBase);
        host.setName(name);

        return (host);

    }


    /**
     * Create and return a class loader manager that can be customized, and
     * then attached to a Context, before it is started.
     *
     * @param parent ClassLoader that will be the parent of the one
     *  created by this Loader
     */
    public Loader createLoader(ClassLoader parent) {

        if( log.isDebugEnabled() )
            log.debug("Creating Loader with parent class loader '" +
                       parent + "'");

        WebappLoader loader = new WebappLoader(parent);
        return (loader);

    }


    /**
     * Return descriptive information about this Server implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return (info);

    }


    /**
     * Remove the specified Context from the set of defined Contexts for its
     * associated Host.  If this is the last Context for this Host, the Host
     * will also be removed.
     *
     * @param context The Context to be removed
     */
    public synchronized void removeContext(Context context) {

        if( log.isDebugEnabled() )
            log.debug("Removing context[" + context.getName() + "]");

        // Is this Context actually among those that are defined?
        boolean found = false;
        for (int i = 0; i < engines.length; i++) {
            Container hosts[] = engines[i].findChildren();
            for (int j = 0; j < hosts.length; j++) {
                Container contexts[] = hosts[j].findChildren();
                for (int k = 0; k < contexts.length; k++) {
                    if (context == (Context) contexts[k]) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }
            if (found)
                break;
        }
        if (!found)
            return;

        // Remove this Context from the associated Host
        if( log.isDebugEnabled() )
            log.debug(" Removing this Context");
        context.getParent().removeChild(context);

    }


    /**
     * Remove the specified Engine from the set of defined Engines, along with
     * all of its related Hosts and Contexts.  All associated Connectors are
     * also removed.
     *
     * @param engine The Engine to be removed
     */
    public synchronized void removeEngine(Engine engine) {

        if( log.isDebugEnabled() )
            log.debug("Removing engine (" + engine.getInfo() + ")");

        // Is the specified Engine actually defined?
        int j = -1;
        for (int i = 0; i < engines.length; i++) {
            if (engine == engines[i]) {
                j = i;
                break;
            }
        }
        if (j < 0)
            return;

        // Remove any Connector that is using this Engine
        if( log.isDebugEnabled() )
            log.debug(" Removing related Containers");
        while (true) {
            int n = -1;
            for (int i = 0; i < connectors.length; i++) {
                if (connectors[i].getService().getContainer() == engine) {
                    n = i;
                    break;
                }
            }
            if (n < 0)
                break;
            removeConnector(connectors[n]);
        }

        // Stop this Engine if necessary
        if( log.isDebugEnabled() )
            log.debug(" Stopping this Engine");
        try {
            engine.stop();
        } catch (LifecycleException e) {
            log.error("Engine.stop", e);
        }

        // Remove this Engine from our set of defined Engines
        if( log.isDebugEnabled() )
            log.debug(" Removing this Engine");
        int k = 0;
        Engine results[] = new Engine[engines.length - 1];
        for (int i = 0; i < engines.length; i++) {
            if (i != j)
                results[k++] = engines[i];
        }
        engines = results;

    }


    /**
     * Remove the specified Host, along with all of its related Contexts,
     * from the set of defined Hosts for its associated Engine.  If this is
     * the last Host for this Engine, the Engine will also be removed.
     *
     * @param host The Host to be removed
     */
    public synchronized void removeHost(Host host) {

        if( log.isDebugEnabled() )
            log.debug("Removing host[" + host.getName() + "]");

        // Is this Host actually among those that are defined?
        boolean found = false;
        for (int i = 0; i < engines.length; i++) {
            Container hosts[] = engines[i].findChildren();
            for (int j = 0; j < hosts.length; j++) {
                if (host == (Host) hosts[j]) {
                    found = true;
                    break;

                }
            }
            if (found)
                break;
        }
        if (!found)
            return;

        // Remove this Host from the associated Engine
        if( log.isDebugEnabled() )
            log.debug(" Removing this Host");
        host.getParent().removeChild(host);

    }


    /*
     * Maps the specified login method to the specified authenticator, allowing
     * the mappings in org/apache/catalina/startup/Authenticators.properties
     * to be overridden.
     *
     * @param authenticator Authenticator to handle authentication for the
     * specified login method
     * @param loginMethod Login method that maps to the specified authenticator
     *
     * @throws IllegalArgumentException if the specified authenticator does not
     * implement the org.apache.catalina.Valve interface
     */
    public void addAuthenticator(Authenticator authenticator,
                                 String loginMethod) {
        if (!(authenticator instanceof Valve)) {
            throw new IllegalArgumentException(
                sm.getString("embedded.authenticatorNotInstanceOfValve"));
        }
        if (authenticators == null) {
            synchronized (this) {
                if (authenticators == null) {
                    authenticators = new HashMap<String,Authenticator>();
                }
            }
        }
        authenticators.put(loginMethod, authenticator);
    }


    // ------------------------------------------------------ Lifecycle Methods


    /**
     * Start nested components ({@link Connector}s and {@link Engine}s) and
     * implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected void startInternal() throws LifecycleException {

        if( log.isInfoEnabled() )
            log.info("Starting tomcat server");

        // Validate the setup of our required system properties
        initDirs();

        // Initialize some naming specific properties
        initNaming();

        setState(LifecycleState.STARTING);

        // Start our defined Engines first
        for (int i = 0; i < engines.length; i++) {
            engines[i].start();
        }

        // Start our defined Connectors second
        for (int i = 0; i < connectors.length; i++) {
            ((Lifecycle) connectors[i]).start();
        }

    }


    /**
     * Stop nested components ({@link Connector}s and {@link Engine}s) and
     * implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that needs to be reported
     */
    @Override
    protected void stopInternal() throws LifecycleException {

        if( log.isDebugEnabled() )
            log.debug("Stopping embedded server");

        setState(LifecycleState.STOPPING);

        // Stop our defined Connectors first
        for (int i = 0; i < connectors.length; i++) {
            ((Lifecycle) connectors[i]).stop();
        }

        // Stop our defined Engines second
        for (int i = 0; i < engines.length; i++) {
            engines[i].stop();
        }

    }


    // ------------------------------------------------------ Protected Methods


    /** Initialize naming - this should only enable java:env and root naming.
     * If tomcat is embedded in an application that already defines those -
     * it shouldn't do it.
     *
     * XXX The 2 should be separated, you may want to enable java: but not
     * the initial context and the reverse
     * XXX Can we "guess" - i.e. lookup java: and if something is returned assume
     * false ?
     * XXX We have a major problem with the current setting for java: url
     */
    protected void initNaming() {
        // Setting additional variables
        if (!useNaming) {
            log.info( "Catalina naming disabled");
            System.setProperty("catalina.useNaming", "false");
        } else {
            System.setProperty("catalina.useNaming", "true");
            String value = "org.apache.naming";
            String oldValue =
                System.getProperty(javax.naming.Context.URL_PKG_PREFIXES);
            if (oldValue != null) {
                value = value + ":" + oldValue;
            }
            System.setProperty(javax.naming.Context.URL_PKG_PREFIXES, value);
            if( log.isDebugEnabled() )
                log.debug("Setting naming prefix=" + value);
            value = System.getProperty
                (javax.naming.Context.INITIAL_CONTEXT_FACTORY);
            if (value == null) {
                System.setProperty
                    (javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                     "org.apache.naming.java.javaURLContextFactory");
            } else {
                log.debug( "INITIAL_CONTEXT_FACTORY already set " + value );
            }
        }
    }


    protected void initDirs() {

        String catalinaHome = System.getProperty(Globals.CATALINA_HOME_PROP);
        if (catalinaHome == null) {
            // Backwards compatibility patch for J2EE RI 1.3
            String j2eeHome = System.getProperty("com.sun.enterprise.home");
            if (j2eeHome != null) {
                catalinaHome=System.getProperty("com.sun.enterprise.home");
            } else if (System.getProperty(Globals.CATALINA_BASE_PROP) != null) {
                catalinaHome = System.getProperty(Globals.CATALINA_BASE_PROP);
            } else {
                // Use IntrospectionUtils and guess the dir
                catalinaHome = IntrospectionUtils.guessInstall
                    (Globals.CATALINA_HOME_PROP, Globals.CATALINA_BASE_PROP, "catalina.jar");
                if (catalinaHome == null) {
                    catalinaHome = IntrospectionUtils.guessInstall
                        ("tomcat.install", Globals.CATALINA_HOME_PROP, "tomcat.jar");
                }
            }
        }
        // last resort - for minimal/embedded cases. 
        if(catalinaHome==null) {
            catalinaHome=System.getProperty("user.dir");
        }
        if (catalinaHome != null) {
            File home = new File(catalinaHome);
            if (!home.isAbsolute()) {
                try {
                    catalinaHome = home.getCanonicalPath();
                } catch (IOException e) {
                    catalinaHome = home.getAbsolutePath();
                }
            }
            System.setProperty(Globals.CATALINA_HOME_PROP, catalinaHome);
        }

        if (System.getProperty(Globals.CATALINA_BASE_PROP) == null) {
            System.setProperty(Globals.CATALINA_BASE_PROP,
                               catalinaHome);
        } else {
            String catalinaBase = System.getProperty(Globals.CATALINA_BASE_PROP);
            File base = new File(catalinaBase);
            if (!base.isAbsolute()) {
                try {
                    catalinaBase = base.getCanonicalPath();
                } catch (IOException e) {
                    catalinaBase = base.getAbsolutePath();
                }
            }
            System.setProperty(Globals.CATALINA_BASE_PROP, catalinaBase);
        }
        
        String temp = System.getProperty("java.io.tmpdir");
        if (temp == null || (!(new File(temp)).exists())
                || (!(new File(temp)).isDirectory())) {
            log.error(sm.getString("embedded.notmp", temp));
        }

    }

    
    protected void initStreams() {
        if (redirectStreams) {
            // Replace System.out and System.err with a custom PrintStream
            System.setOut(new SystemLogHandler(System.out));
            System.setErr(new SystemLogHandler(System.err));
        }
    }
    

    // -------------------------------------------------------- Private Methods

    /**
     * Set the security package access/protection.
     */
    protected void setSecurityProtection(){
        SecurityConfig securityConfig = SecurityConfig.newInstance();
        securityConfig.setPackageDefinition();
        securityConfig.setPackageAccess();
    }

}
