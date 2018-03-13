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
package org.apache.catalina.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.catalina.AccessLog;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerEvent;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Realm;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.NullRealm;
import org.apache.catalina.util.ServerInfo;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Standard implementation of the <b>Engine</b> interface.  Each
 * child container must be a Host implementation to process the specific
 * fully qualified host name of that virtual host. <br>
 * You can set the jvmRoute direct or with the System.property <b>jvmRoute</b>.
 *
 * @author Craig R. McClanahan
 */
public class StandardEngine extends ContainerBase implements Engine {

    private static final Log log = LogFactory.getLog(StandardEngine.class);

    // ----------------------------------------------------------- Constructors


    /**
     * Create a new StandardEngine component with the default basic Valve.
     */
    public StandardEngine() {

        super();
        pipeline.setBasic(new StandardEngineValve());
        /* Set the jmvRoute using the system property jvmRoute */
        try {
            setJvmRoute(System.getProperty("jvmRoute"));
        } catch(Exception ex) {
            log.warn(sm.getString("standardEngine.jvmRouteFail"));
        }
        // By default, the engine will hold the reloading thread
        backgroundProcessorDelay = 10;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Host name to use when no server host, or an unknown host,
     * is specified in the request.
     */
    private String defaultHost = null;


    /**
     * The descriptive information string for this implementation.
     */
    private static final String info =
        "org.apache.catalina.core.StandardEngine/1.0";


    /**
     * The <code>Service</code> that owns this Engine, if any.
     */
    private Service service = null;

    /** Allow the base dir to be specified explicitly for
     * each engine. In time we should stop using catalina.base property -
     * otherwise we loose some flexibility.
     */
    private String baseDir = null;

    /**
     * The JVM Route ID for this Tomcat instance. All Route ID's must be unique
     * across the cluster.
     */
    private String jvmRouteId;

    /**
     * Default access log to use for request/response pairs where we can't ID
     * the intended host and context.
     */
    private final AtomicReference<AccessLog> defaultAccessLog =
        new AtomicReference<AccessLog>();

    // ------------------------------------------------------------- Properties

    /**
     * Obtain the configured Realm and provide a default Realm implementation
     * when no explicit configuration is set.
     *
     * @return configured realm, or a {@link NullRealm} by default
     */
    @Override
    public Realm getRealm() {
        Realm configured = super.getRealm();
        // If no set realm has been called - default to NullRealm
        // This can be overridden at engine, context and host level  
        if (configured == null) {
            configured = new NullRealm();
            this.setRealm(configured);
        }
        return configured;
    }


    /**
     * Return the default host.
     */
    @Override
    public String getDefaultHost() {

        return (defaultHost);

    }


    /**
     * Set the default host.
     *
     * @param host The new default host
     */
    @Override
    public void setDefaultHost(String host) {

        String oldDefaultHost = this.defaultHost;
        if (host == null) {
            this.defaultHost = null;
        } else {
            this.defaultHost = host.toLowerCase(Locale.ENGLISH);
        }
        support.firePropertyChange("defaultHost", oldDefaultHost,
                                   this.defaultHost);

    }
    

    /**
     * Set the cluster-wide unique identifier for this Engine.
     * This value is only useful in a load-balancing scenario.
     * <p>
     * This property should not be changed once it is set.
     */
    @Override
    public void setJvmRoute(String routeId) {
        jvmRouteId = routeId;
    }


    /**
     * Retrieve the cluster-wide unique identifier for this Engine.
     * This value is only useful in a load-balancing scenario.
     */
    @Override
    public String getJvmRoute() {
        return jvmRouteId;
    }


    /**
     * Return the <code>Service</code> with which we are associated (if any).
     */
    @Override
    public Service getService() {

        return (this.service);

    }


    /**
     * Set the <code>Service</code> with which we are associated (if any).
     *
     * @param service The service that owns this Engine
     */
    @Override
    public void setService(Service service) {
        this.service = service;
    }

    public String getBaseDir() {
        if( baseDir==null ) {
            baseDir=System.getProperty(Globals.CATALINA_BASE_PROP);
        }
        if( baseDir==null ) {
            baseDir=System.getProperty(Globals.CATALINA_HOME_PROP);
        }
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    // --------------------------------------------------------- Public Methods


    /**
     * Add a child Container, only if the proposed child is an implementation
     * of Host.
     *
     * @param child Child container to be added
     */
    @Override
    public void addChild(Container child) {

        if (!(child instanceof Host))
            throw new IllegalArgumentException
                (sm.getString("standardEngine.notHost"));
        super.addChild(child);

    }


    /**
     * Return descriptive information about this Container implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return (info);

    }

    /**
     * Disallow any attempt to set a parent for this Container, since an
     * Engine is supposed to be at the top of the Container hierarchy.
     *
     * @param container Proposed parent Container
     */
    @Override
    public void setParent(Container container) {

        throw new IllegalArgumentException
            (sm.getString("standardEngine.notParent"));

    }


    @Override
    protected void initInternal() throws LifecycleException {
        // Ensure that a Realm is present before any attempt is made to start
        // one. This will create the default NullRealm if necessary.
        getRealm();
        super.initInternal();
    }


    /**
     * Start this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {
        
        // Log our server identification information
        if(log.isInfoEnabled())
            log.info( "Starting Servlet Engine: " + ServerInfo.getServerInfo());

        // Standard container startup
        super.startInternal();
    }

    
    /**
     * Return a String representation of this component.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("StandardEngine[");
        sb.append(getName());
        sb.append("]");
        return (sb.toString());

    }

    /**
     * Override the default implementation. If no access log is defined for the
     * Engine, look for one in the Engine's default host and then the default
     * host's ROOT context. If still none is found, return the default NoOp
     * access log.
     */
    @Override
    public void logAccess(Request request, Response response, long time,
            boolean useDefault) {

        boolean logged = false;
        
        if (getAccessLog() != null) {
            accessLog.log(request, response, time);
            logged = true;
        }

        if (!logged && useDefault) {
            AccessLog newDefaultAccessLog = defaultAccessLog.get();
            if (newDefaultAccessLog == null) {
                // If we reached this point, this Engine can't have an AccessLog
                // Look in the defaultHost
                Host host = (Host) findChild(getDefaultHost());
                Context context = null;
                if (host != null && host.getState().isAvailable()) {
                    newDefaultAccessLog = host.getAccessLog();

                    if (newDefaultAccessLog != null) {
                        if (defaultAccessLog.compareAndSet(null,
                                newDefaultAccessLog)) {
                            AccessLogListener l = new AccessLogListener(this,
                                    host, null);
                            l.install();
                        }
                    } else {
                        // Try the ROOT context of default host
                        context = (Context) host.findChild("");
                        if (context != null &&
                                context.getState().isAvailable()) {
                            newDefaultAccessLog = context.getAccessLog();
                            if (newDefaultAccessLog != null) {
                                if (defaultAccessLog.compareAndSet(null,
                                        newDefaultAccessLog)) {
                                    AccessLogListener l = new AccessLogListener(
                                            this, null, context);
                                    l.install();
                                }
                            }
                        }
                    }
                }

                if (newDefaultAccessLog == null) {
                    newDefaultAccessLog = new NoopAccessLog();
                    if (defaultAccessLog.compareAndSet(null,
                            newDefaultAccessLog)) {
                        AccessLogListener l = new AccessLogListener(this, host,
                                context);
                        l.install();
                    }
                }
            }

            newDefaultAccessLog.log(request, response, time);
        }
    }


    /**
     * Return the parent class loader for this component.
     */
    @Override
    public ClassLoader getParentClassLoader() {
        if (parentClassLoader != null)
            return (parentClassLoader);
        if (service != null) {
            return (service.getParentClassLoader());
        }
        return (ClassLoader.getSystemClassLoader());
    }

    // -------------------- JMX registration  --------------------

    @Override
    protected String getObjectNameKeyProperties() {
        return "type=Engine";
    }

    // ----------------------------------------------------------- Inner classes
    protected static final class NoopAccessLog implements AccessLog {

        @Override
        public void log(Request request, Response response, long time) {
            // NOOP
        }

        @Override
        public void setRequestAttributesEnabled(
                boolean requestAttributesEnabled) {
            // NOOP
            
        }

        @Override
        public boolean getRequestAttributesEnabled() {
            // NOOP
            return false;
        }
    }
    
    protected static final class AccessLogListener
            implements PropertyChangeListener, LifecycleListener,
            ContainerListener {

        private StandardEngine engine;
        private Host host;
        private Context context;
        private volatile boolean disabled = false;

        public AccessLogListener(StandardEngine engine, Host host,
                Context context) {
            this.engine = engine;
            this.host = host;
            this.context = context;
        }

        public void install() {
            engine.addPropertyChangeListener(this);
            if (host != null) {
                host.addContainerListener(this);
                host.addLifecycleListener(this);
            }
            if (context != null) {
                context.addLifecycleListener(this);
            }
        }

        private void uninstall() {
            disabled = true;
            if (context != null) {
                context.removeLifecycleListener(this);
            }
            if (host != null) {
                host.removeLifecycleListener(this);
                host.removeContainerListener(this);
            }
            engine.removePropertyChangeListener(this);
        }

        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            if (disabled) return;

            String type = event.getType();
            if (Lifecycle.AFTER_START_EVENT.equals(type) ||
                    Lifecycle.BEFORE_STOP_EVENT.equals(type) ||
                    Lifecycle.BEFORE_DESTROY_EVENT.equals(type)) {
                // Container is being started/stopped/removed
                // Force re-calculation and disable listener since it won't
                // be re-used
                engine.defaultAccessLog.set(null);
                uninstall();
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (disabled) return;
            if ("defaultHost".equals(evt.getPropertyName())) {
                // Force re-calculation and disable listener since it won't
                // be re-used
                engine.defaultAccessLog.set(null);
                uninstall();
            }
        }

        @Override
        public void containerEvent(ContainerEvent event) {
            // Only useful for hosts
            if (disabled) return;
            if (Container.ADD_CHILD_EVENT.equals(event.getType())) {
                Context context = (Context) event.getData();
                if ("".equals(context.getPath())) {
                    // Force re-calculation and disable listener since it won't
                    // be re-used
                    engine.defaultAccessLog.set(null);
                    uninstall();
                }
            }
        }
    }
}
