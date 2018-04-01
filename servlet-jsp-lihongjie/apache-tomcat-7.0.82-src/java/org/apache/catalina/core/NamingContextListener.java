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
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import org.apache.catalina.Container;
import org.apache.catalina.ContainerEvent;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Server;
import org.apache.catalina.deploy.ContextEjb;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextHandler;
import org.apache.catalina.deploy.ContextLocalEjb;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.ContextResourceEnvRef;
import org.apache.catalina.deploy.ContextResourceLink;
import org.apache.catalina.deploy.ContextService;
import org.apache.catalina.deploy.ContextTransaction;
import org.apache.catalina.deploy.NamingResources;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.naming.ContextAccessController;
import org.apache.naming.ContextBindings;
import org.apache.naming.EjbRef;
import org.apache.naming.HandlerRef;
import org.apache.naming.NamingContext;
import org.apache.naming.ResourceEnvRef;
import org.apache.naming.ResourceLinkRef;
import org.apache.naming.ResourceRef;
import org.apache.naming.ServiceRef;
import org.apache.naming.TransactionRef;
import org.apache.naming.factory.ResourceLinkFactory;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.res.StringManager;


/**
 * Helper class used to initialize and populate the JNDI context associated
 * with each context and server.
 *
 * @author Remy Maucherat
 */
public class NamingContextListener
    implements LifecycleListener, ContainerListener, PropertyChangeListener {

    private static final Log log = LogFactory.getLog(NamingContextListener.class);


    // ----------------------------------------------------- Instance Variables


    protected Log logger = log;


    /**
     * Name of the associated naming context.
     */
    protected String name = "/";


    /**
     * Associated container.
     */
    protected Object container = null;


    /**
     * Initialized flag.
     */
    protected boolean initialized = false;


    /**
     * Associated naming resources.
     */
    protected NamingResources namingResources = null;


    /**
     * Associated JNDI context.
     */
    protected NamingContext namingContext = null;


    /**
     * Comp context.
     */
    protected javax.naming.Context compCtx = null;


    /**
     * Env context.
     */
    protected javax.naming.Context envCtx = null;


    /**
     * Objectnames hashtable.
     */
    protected HashMap<String, ObjectName> objectNames =
        new HashMap<String, ObjectName>();


    /**
     * Determines if an attempt to write to a read-only context results in an
     * exception or if the request is ignored.
     */
    private boolean exceptionOnFailedWrite = true;


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    // ------------------------------------------------------------- Properties

    /**
     * Returns whether or not an attempt to modify the JNDI context will trigger
     * an exception or if the request will be ignored.
     */
    public boolean getExceptionOnFailedWrite() {
        return exceptionOnFailedWrite;
    }


    /**
     * Controls whether or not an attempt to modify the JNDI context will
     * trigger an exception or if the request will be ignored.
     *
     * @param exceptionOnFailedWrite    The new value
     */
    public void setExceptionOnFailedWrite(boolean exceptionOnFailedWrite) {
        this.exceptionOnFailedWrite = exceptionOnFailedWrite;
    }


    /**
     * Return the "name" property.
     */
    public String getName() {
        return (this.name);
    }


    /**
     * Set the "name" property.
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Return the comp context.
     */
    @Deprecated
    public javax.naming.Context getCompContext() {
        return this.compCtx;
    }


    /**
     * Return the env context.
     */
    public javax.naming.Context getEnvContext() {
        return this.envCtx;
    }


    /**
     * Return the associated naming context.
     */
    @Deprecated
    public NamingContext getNamingContext() {
        return (this.namingContext);
    }


    // ---------------------------------------------- LifecycleListener Methods


    /**
     * Acknowledge the occurrence of the specified event.
     *
     * @param event LifecycleEvent that has occurred
     */
    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        container = event.getLifecycle();

        if (container instanceof Context) {
            namingResources = ((Context) container).getNamingResources();
            logger = log;
        } else if (container instanceof Server) {
            namingResources = ((Server) container).getGlobalNamingResources();
        } else {
            return;
        }

        if (Lifecycle.CONFIGURE_START_EVENT.equals(event.getType())) {

            if (initialized)
                return;

            try {
                Hashtable<String, Object> contextEnv = new Hashtable<String, Object>();
                try {
                    namingContext = new NamingContext(contextEnv, getName());
                } catch (NamingException e) {
                    // Never happens
                }
                ContextAccessController.setSecurityToken(getName(), container);
                ContextAccessController.setSecurityToken(container, container);
                ContextBindings.bindContext(container, namingContext, container);
                if( log.isDebugEnabled() ) {
                    log.debug("Bound " + container );
                }

                // Configure write when read-only behaviour
                namingContext.setExceptionOnFailedWrite(
                        getExceptionOnFailedWrite());

                // Setting the context in read/write mode
                ContextAccessController.setWritable(getName(), container);

                try {
                    createNamingContext();
                } catch (NamingException e) {
                    logger.error
                    (sm.getString("naming.namingContextCreationFailed", e));
                }

                namingResources.addPropertyChangeListener(this);

                // Binding the naming context to the class loader
                if (container instanceof Context) {
                    // Setting the context in read only mode
                    ContextAccessController.setReadOnly(getName());
                    try {
                        ContextBindings.bindClassLoader
                        (container, container,
                                ((Container) container).getLoader().getClassLoader());
                    } catch (NamingException e) {
                        logger.error(sm.getString("naming.bindFailed", e));
                    }
                }

                if (container instanceof Server) {
                    org.apache.naming.factory.ResourceLinkFactory.setGlobalContext
                    (namingContext);
                    try {
                        ContextBindings.bindClassLoader
                        (container, container,
                                this.getClass().getClassLoader());
                    } catch (NamingException e) {
                        logger.error(sm.getString("naming.bindFailed", e));
                    }
                    if (container instanceof StandardServer) {
                        ((StandardServer) container).setGlobalNamingContext
                        (namingContext);
                    }
                }

            } finally {
                // Regardless of success, so that we can do cleanup on configure_stop
                initialized = true;
            }

        } else if (Lifecycle.CONFIGURE_STOP_EVENT.equals(event.getType())) {

            if (!initialized)
                return;

            try {
                // Setting the context in read/write mode
                ContextAccessController.setWritable(getName(), container);
                ContextBindings.unbindContext(container, container);

                if (container instanceof Context) {
                    ContextBindings.unbindClassLoader
                    (container, container,
                            ((Container) container).getLoader().getClassLoader());
                }

                if (container instanceof Server) {
                    namingResources.removePropertyChangeListener(this);
                    ContextBindings.unbindClassLoader
                    (container, container,
                            this.getClass().getClassLoader());
                }

                ContextAccessController.unsetSecurityToken(getName(), container);
                ContextAccessController.unsetSecurityToken(container, container);

                // unregister mbeans.
                if (!objectNames.isEmpty()) {
                    Collection<ObjectName> names = objectNames.values();
                    Registry registry = Registry.getRegistry(null, null);
                    for (ObjectName objectName : names) {
                        registry.unregisterComponent(objectName);
                    }
                }

                javax.naming.Context global = getGlobalNamingContext();
                if (global != null) {
                    ResourceLinkFactory.deregisterGlobalResourceAccess(global);
                }
            } finally {
                objectNames.clear();

                namingContext = null;
                envCtx = null;
                compCtx = null;
                initialized = false;
            }

        }

    }


    // ---------------------------------------------- ContainerListener Methods


    /**
     * Acknowledge the occurrence of the specified event.
     * Note: Will never be called when the listener is associated to a Server,
     * since it is not a Container.
     *
     * @param event ContainerEvent that has occurred
     */
    @Override
    public void containerEvent(ContainerEvent event) {

        if (!initialized)
            return;

        // Setting the context in read/write mode
        ContextAccessController.setWritable(getName(), container);

        String type = event.getType();

        if (type.equals("addEjb")) {

            String ejbName = (String) event.getData();
            if (ejbName != null) {
                ContextEjb ejb = namingResources.findEjb(ejbName);
                addEjb(ejb);
            }

        } else if (type.equals("addEnvironment")) {

            String environmentName = (String) event.getData();
            if (environmentName != null) {
                ContextEnvironment env =
                    namingResources.findEnvironment(environmentName);
                addEnvironment(env);
            }

        } else if (type.equals("addLocalEjb")) {

            String localEjbName = (String) event.getData();
            if (localEjbName != null) {
                ContextLocalEjb localEjb =
                    namingResources.findLocalEjb(localEjbName);
                addLocalEjb(localEjb);
            }

        } else if (type.equals("addResource")) {

            String resourceName = (String) event.getData();
            if (resourceName != null) {
                ContextResource resource =
                    namingResources.findResource(resourceName);
                addResource(resource);
            }

        } else if (type.equals("addResourceLink")) {

            String resourceLinkName = (String) event.getData();
            if (resourceLinkName != null) {
                ContextResourceLink resourceLink =
                    namingResources.findResourceLink(resourceLinkName);
                addResourceLink(resourceLink);
            }

        } else if (type.equals("addResourceEnvRef")) {

            String resourceEnvRefName = (String) event.getData();
            if (resourceEnvRefName != null) {
                ContextResourceEnvRef resourceEnvRef =
                    namingResources.findResourceEnvRef(resourceEnvRefName);
                addResourceEnvRef(resourceEnvRef);
            }

        } else if (type.equals("addService")) {

            String serviceName = (String) event.getData();
            if (serviceName != null) {
                ContextService service =
                    namingResources.findService(serviceName);
                addService(service);
            }

        } else if (type.equals("removeEjb")) {

            String ejbName = (String) event.getData();
            if (ejbName != null) {
                removeEjb(ejbName);
            }

        } else if (type.equals("removeEnvironment")) {

            String environmentName = (String) event.getData();
            if (environmentName != null) {
                removeEnvironment(environmentName);
            }

        } else if (type.equals("removeLocalEjb")) {

            String localEjbName = (String) event.getData();
            if (localEjbName != null) {
                removeLocalEjb(localEjbName);
            }

        } else if (type.equals("removeResource")) {

            String resourceName = (String) event.getData();
            if (resourceName != null) {
                removeResource(resourceName);
            }

        } else if (type.equals("removeResourceLink")) {

            String resourceLinkName = (String) event.getData();
            if (resourceLinkName != null) {
                removeResourceLink(resourceLinkName);
            }

        } else if (type.equals("removeResourceEnvRef")) {

            String resourceEnvRefName = (String) event.getData();
            if (resourceEnvRefName != null) {
                removeResourceEnvRef(resourceEnvRefName);
            }

        } else if (type.equals("removeService")) {

            String serviceName = (String) event.getData();
            if (serviceName != null) {
                removeService(serviceName);
            }

        }

        // Setting the context in read only mode
        ContextAccessController.setReadOnly(getName());

    }


    // ----------------------------------------- PropertyChangeListener Methods


    /**
     * Process property change events.
     *
     * @param event The property change event that has occurred
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {

        if (!initialized)
            return;

        Object source = event.getSource();
        if (source == namingResources) {

            // Setting the context in read/write mode
            ContextAccessController.setWritable(getName(), container);

            processGlobalResourcesChange(event.getPropertyName(),
                                         event.getOldValue(),
                                         event.getNewValue());

            // Setting the context in read only mode
            ContextAccessController.setReadOnly(getName());

        }

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Process a property change on the naming resources, by making the
     * corresponding addition or removal to the associated JNDI context.
     *
     * @param name Property name of the change to be processed
     * @param oldValue The old value (or <code>null</code> if adding)
     * @param newValue The new value (or <code>null</code> if removing)
     */
    private void processGlobalResourcesChange(String name,
                                              Object oldValue,
                                              Object newValue) {

        if (name.equals("ejb")) {
            if (oldValue != null) {
                ContextEjb ejb = (ContextEjb) oldValue;
                if (ejb.getName() != null) {
                    removeEjb(ejb.getName());
                }
            }
            if (newValue != null) {
                ContextEjb ejb = (ContextEjb) newValue;
                if (ejb.getName() != null) {
                    addEjb(ejb);
                }
            }
        } else if (name.equals("environment")) {
            if (oldValue != null) {
                ContextEnvironment env = (ContextEnvironment) oldValue;
                if (env.getName() != null) {
                    removeEnvironment(env.getName());
                }
            }
            if (newValue != null) {
                ContextEnvironment env = (ContextEnvironment) newValue;
                if (env.getName() != null) {
                    addEnvironment(env);
                }
            }
        } else if (name.equals("localEjb")) {
            if (oldValue != null) {
                ContextLocalEjb ejb = (ContextLocalEjb) oldValue;
                if (ejb.getName() != null) {
                    removeLocalEjb(ejb.getName());
                }
            }
            if (newValue != null) {
                ContextLocalEjb ejb = (ContextLocalEjb) newValue;
                if (ejb.getName() != null) {
                    addLocalEjb(ejb);
                }
            }
        } else if (name.equals("resource")) {
            if (oldValue != null) {
                ContextResource resource = (ContextResource) oldValue;
                if (resource.getName() != null) {
                    removeResource(resource.getName());
                }
            }
            if (newValue != null) {
                ContextResource resource = (ContextResource) newValue;
                if (resource.getName() != null) {
                    addResource(resource);
                }
            }
        } else if (name.equals("resourceEnvRef")) {
            if (oldValue != null) {
                ContextResourceEnvRef resourceEnvRef =
                    (ContextResourceEnvRef) oldValue;
                if (resourceEnvRef.getName() != null) {
                    removeResourceEnvRef(resourceEnvRef.getName());
                }
            }
            if (newValue != null) {
                ContextResourceEnvRef resourceEnvRef =
                    (ContextResourceEnvRef) newValue;
                if (resourceEnvRef.getName() != null) {
                    addResourceEnvRef(resourceEnvRef);
                }
            }
        } else if (name.equals("resourceLink")) {
            if (oldValue != null) {
                ContextResourceLink rl = (ContextResourceLink) oldValue;
                if (rl.getName() != null) {
                    removeResourceLink(rl.getName());
                }
            }
            if (newValue != null) {
                ContextResourceLink rl = (ContextResourceLink) newValue;
                if (rl.getName() != null) {
                    addResourceLink(rl);
                }
            }
        } else if (name.equals("service")) {
            if (oldValue != null) {
                ContextService service = (ContextService) oldValue;
                if (service.getName() != null) {
                    removeService(service.getName());
                }
            }
            if (newValue != null) {
                ContextService service = (ContextService) newValue;
                if (service.getName() != null) {
                    addService(service);
                }
            }
        }


    }


    /**
     * Create and initialize the JNDI naming context.
     */
    private void createNamingContext()
        throws NamingException {

        // Creating the comp subcontext
        if (container instanceof Server) {
            compCtx = namingContext;
            envCtx = namingContext;
        } else {
            compCtx = namingContext.createSubcontext("comp");
            envCtx = compCtx.createSubcontext("env");
        }

        int i;

        if (log.isDebugEnabled())
            log.debug("Creating JNDI naming context");

        if (namingResources == null) {
            namingResources = new NamingResources();
            namingResources.setContainer(container);
        }

        // Resource links
        ContextResourceLink[] resourceLinks =
            namingResources.findResourceLinks();
        for (i = 0; i < resourceLinks.length; i++) {
            addResourceLink(resourceLinks[i]);
        }

        // Resources
        ContextResource[] resources = namingResources.findResources();
        for (i = 0; i < resources.length; i++) {
            addResource(resources[i]);
        }

        // Resources Env
        ContextResourceEnvRef[] resourceEnvRefs = namingResources.findResourceEnvRefs();
        for (i = 0; i < resourceEnvRefs.length; i++) {
            addResourceEnvRef(resourceEnvRefs[i]);
        }

        // Environment entries
        ContextEnvironment[] contextEnvironments =
            namingResources.findEnvironments();
        for (i = 0; i < contextEnvironments.length; i++) {
            addEnvironment(contextEnvironments[i]);
        }

        // EJB references
        ContextEjb[] ejbs = namingResources.findEjbs();
        for (i = 0; i < ejbs.length; i++) {
            addEjb(ejbs[i]);
        }

        // WebServices references
        ContextService[] services = namingResources.findServices();
        for (i = 0; i < services.length; i++) {
            addService(services[i]);
        }

        // Binding a User Transaction reference
        if (container instanceof Context) {
            try {
                Reference ref = new TransactionRef();
                compCtx.bind("UserTransaction", ref);
                ContextTransaction transaction = namingResources.getTransaction();
                if (transaction != null) {
                    Iterator<String> params = transaction.listProperties();
                    while (params.hasNext()) {
                        String paramName = params.next();
                        String paramValue = (String) transaction.getProperty(paramName);
                        StringRefAddr refAddr = new StringRefAddr(paramName, paramValue);
                        ref.add(refAddr);
                    }
                }
            } catch (NameAlreadyBoundException e) {
                // Ignore because UserTransaction was obviously
                // added via ResourceLink
            } catch (NamingException e) {
                logger.error(sm.getString("naming.bindFailed", e));
            }
        }

        // Binding the resources directory context
        if (container instanceof Context) {
            try {
                compCtx.bind("Resources",
                             ((Container) container).getResources());
            } catch (NamingException e) {
                logger.error(sm.getString("naming.bindFailed", e));
            }
        }

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>ContextResource</code> object.
     *
     * @param resource The resource
     * @return ObjectName The object name
     * @exception MalformedObjectNameException if a name cannot be created
     */
    protected ObjectName createObjectName(ContextResource resource)
        throws MalformedObjectNameException {

        String domain = null;
        if (container instanceof StandardServer) {
            domain = ((StandardServer) container).getDomain();
        } else if (container instanceof ContainerBase) {
            domain = ((ContainerBase) container).getDomain();
        }
        if (domain == null) {
            domain = "Catalina";
        }

        ObjectName name = null;
        String quotedResourceName = ObjectName.quote(resource.getName());
        if (container instanceof Server) {
            name = new ObjectName(domain + ":type=DataSource" +
                        ",class=" + resource.getType() +
                        ",name=" + quotedResourceName);
        } else if (container instanceof Context) {
            String contextName = ((Context)container).getName();
            if (!contextName.startsWith("/"))
                contextName = "/" + contextName;
            Host host = (Host) ((Context)container).getParent();
            name = new ObjectName(domain + ":type=DataSource" +
                        ",context=" + contextName +
                        ",host=" + host.getName() +
                        ",class=" + resource.getType() +
                        ",name=" + quotedResourceName);
        }

        return (name);

    }


    /**
     * Set the specified EJBs in the naming context.
     */
    public void addEjb(ContextEjb ejb) {

        // Create a reference to the EJB.
        Reference ref = new EjbRef
            (ejb.getType(), ejb.getHome(), ejb.getRemote(), ejb.getLink());
        // Adding the additional parameters, if any
        Iterator<String> params = ejb.listProperties();
        while (params.hasNext()) {
            String paramName = params.next();
            String paramValue = (String) ejb.getProperty(paramName);
            StringRefAddr refAddr = new StringRefAddr(paramName, paramValue);
            ref.add(refAddr);
        }
        try {
            createSubcontexts(envCtx, ejb.getName());
            envCtx.bind(ejb.getName(), ref);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.bindFailed", e));
        }

    }


    /**
     * Set the specified environment entries in the naming context.
     */
    public void addEnvironment(ContextEnvironment env) {

        Object value = null;
        // Instantiating a new instance of the correct object type, and
        // initializing it.
        String type = env.getType();
        try {
            if (type.equals("java.lang.String")) {
                value = env.getValue();
            } else if (type.equals("java.lang.Byte")) {
                if (env.getValue() == null) {
                    value = Byte.valueOf((byte) 0);
                } else {
                    value = Byte.decode(env.getValue());
                }
            } else if (type.equals("java.lang.Short")) {
                if (env.getValue() == null) {
                    value = Short.valueOf((short) 0);
                } else {
                    value = Short.decode(env.getValue());
                }
            } else if (type.equals("java.lang.Integer")) {
                if (env.getValue() == null) {
                    value = Integer.valueOf(0);
                } else {
                    value = Integer.decode(env.getValue());
                }
            } else if (type.equals("java.lang.Long")) {
                if (env.getValue() == null) {
                    value = Long.valueOf(0);
                } else {
                    value = Long.decode(env.getValue());
                }
            } else if (type.equals("java.lang.Boolean")) {
                value = Boolean.valueOf(env.getValue());
            } else if (type.equals("java.lang.Double")) {
                if (env.getValue() == null) {
                    value = Double.valueOf(0);
                } else {
                    value = Double.valueOf(env.getValue());
                }
            } else if (type.equals("java.lang.Float")) {
                if (env.getValue() == null) {
                    value = Float.valueOf(0);
                } else {
                    value = Float.valueOf(env.getValue());
                }
            } else if (type.equals("java.lang.Character")) {
                if (env.getValue() == null) {
                    value = Character.valueOf((char) 0);
                } else {
                    if (env.getValue().length() == 1) {
                        value = Character.valueOf(env.getValue().charAt(0));
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } else {
                value = constructEnvEntry(env.getType(), env.getValue());
                if (value == null) {
                    logger.error(sm.getString(
                            "naming.invalidEnvEntryType", env.getName()));
                }
            }
        } catch (NumberFormatException e) {
            logger.error(sm.getString("naming.invalidEnvEntryValue", env.getName()));
        } catch (IllegalArgumentException e) {
            logger.error(sm.getString("naming.invalidEnvEntryValue", env.getName()));
        }

        // Binding the object to the appropriate name
        if (value != null) {
            try {
                if (logger.isDebugEnabled())
                    logger.debug("  Adding environment entry " + env.getName());
                createSubcontexts(envCtx, env.getName());
                envCtx.bind(env.getName(), value);
            } catch (NamingException e) {
                logger.error(sm.getString("naming.invalidEnvEntryValue", e));
            }
        }

    }


    private Object constructEnvEntry(String type, String value) {
        try {
            Class<?> clazz = Class.forName(type);
            Constructor<?> c = null;
            try {
                 c = clazz.getConstructor(String.class);
                 return c.newInstance(value);
            } catch (NoSuchMethodException e) {
                // Ignore
            }

            if (value.length() != 1) {
                return null;
            }

            try {
                c = clazz.getConstructor(char.class);
                return c.newInstance(Character.valueOf(value.charAt(0)));
            } catch (NoSuchMethodException e) {
                // Ignore
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }

    /**
     * Set the specified local EJBs in the naming context.
     */
    public void addLocalEjb(
            @SuppressWarnings("unused") ContextLocalEjb localEjb) {
        // NO-OP
    }


    /**
     * Set the specified web service in the naming context.
     */
    public void addService(ContextService service) {

        if (service.getWsdlfile() != null) {
            URL wsdlURL = null;

            try {
                wsdlURL = new URL(service.getWsdlfile());
            } catch (MalformedURLException e) {
                // Ignore and carry on
            }
            if (wsdlURL == null) {
                try {
                    wsdlURL = ((Context) container).
                                                    getServletContext().
                                                    getResource(service.getWsdlfile());
                } catch (MalformedURLException e) {
                    // Ignore and carry on
                }
            }
            if (wsdlURL == null) {
                try {
                    wsdlURL = ((Context) container).
                                                    getServletContext().
                                                    getResource("/" + service.getWsdlfile());
                    logger.debug("  Changing service ref wsdl file for /"
                                + service.getWsdlfile());
                } catch (MalformedURLException e) {
                    logger.error(sm.getString("naming.wsdlFailed", e));
                }
            }
            if (wsdlURL == null)
                service.setWsdlfile(null);
            else
                service.setWsdlfile(wsdlURL.toString());
        }

        if (service.getJaxrpcmappingfile() != null) {
            URL jaxrpcURL = null;

            try {
                jaxrpcURL = new URL(service.getJaxrpcmappingfile());
            } catch (MalformedURLException e) {
                // Ignore and carry on
            }
            if (jaxrpcURL == null) {
                try {
                    jaxrpcURL = ((Context) container).
                                                    getServletContext().
                                                    getResource(service.getJaxrpcmappingfile());
                } catch (MalformedURLException e) {
                    // Ignore and carry on
                }
            }
            if (jaxrpcURL == null) {
                try {
                    jaxrpcURL = ((Context) container).
                                                    getServletContext().
                                                    getResource("/" + service.getJaxrpcmappingfile());
                    logger.debug("  Changing service ref jaxrpc file for /"
                                + service.getJaxrpcmappingfile());
                } catch (MalformedURLException e) {
                    logger.error(sm.getString("naming.wsdlFailed", e));
                }
            }
            if (jaxrpcURL == null)
                service.setJaxrpcmappingfile(null);
            else
                service.setJaxrpcmappingfile(jaxrpcURL.toString());
        }

        // Create a reference to the resource.
        Reference ref = new ServiceRef
            (service.getName(), service.getType(), service.getServiceqname(),
             service.getWsdlfile(), service.getJaxrpcmappingfile());
        // Adding the additional port-component-ref, if any
        Iterator<String> portcomponent = service.getServiceendpoints();
        while (portcomponent.hasNext()) {
            String serviceendpoint = portcomponent.next();
            StringRefAddr refAddr = new StringRefAddr(ServiceRef.SERVICEENDPOINTINTERFACE, serviceendpoint);
            ref.add(refAddr);
            String portlink = service.getPortlink(serviceendpoint);
            refAddr = new StringRefAddr(ServiceRef.PORTCOMPONENTLINK, portlink);
            ref.add(refAddr);
        }
        // Adding the additional parameters, if any
        Iterator<String> handlers = service.getHandlers();
        while (handlers.hasNext()) {
            String handlername = handlers.next();
            ContextHandler handler = service.getHandler(handlername);
            HandlerRef handlerRef = new HandlerRef(handlername, handler.getHandlerclass());
            Iterator<String> localParts = handler.getLocalparts();
            while (localParts.hasNext()) {
                String localPart = localParts.next();
                String namespaceURI = handler.getNamespaceuri(localPart);
                handlerRef.add(new StringRefAddr(HandlerRef.HANDLER_LOCALPART, localPart));
                handlerRef.add(new StringRefAddr(HandlerRef.HANDLER_NAMESPACE, namespaceURI));
            }
            Iterator<String> params = handler.listProperties();
            while (params.hasNext()) {
                String paramName = params.next();
                String paramValue = (String) handler.getProperty(paramName);
                handlerRef.add(new StringRefAddr(HandlerRef.HANDLER_PARAMNAME, paramName));
                handlerRef.add(new StringRefAddr(HandlerRef.HANDLER_PARAMVALUE, paramValue));
            }
            for (int i = 0; i < handler.getSoapRolesSize(); i++) {
                handlerRef.add(new StringRefAddr(HandlerRef.HANDLER_SOAPROLE, handler.getSoapRole(i)));
            }
            for (int i = 0; i < handler.getPortNamesSize(); i++) {
                handlerRef.add(new StringRefAddr(HandlerRef.HANDLER_PORTNAME, handler.getPortName(i)));
            }
            ((ServiceRef) ref).addHandler(handlerRef);
        }

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("  Adding service ref "
                             + service.getName() + "  " + ref);
            }
            createSubcontexts(envCtx, service.getName());
            envCtx.bind(service.getName(), ref);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.bindFailed", e));
        }

    }


    /**
     * Set the specified resources in the naming context.
     */
    public void addResource(ContextResource resource) {

        // Create a reference to the resource.
        Reference ref = new ResourceRef
            (resource.getType(), resource.getDescription(),
             resource.getScope(), resource.getAuth(),
             resource.getSingleton());
        // Adding the additional parameters, if any
        Iterator<String> params = resource.listProperties();
        while (params.hasNext()) {
            String paramName = params.next();
            String paramValue = (String) resource.getProperty(paramName);
            StringRefAddr refAddr = new StringRefAddr(paramName, paramValue);
            ref.add(refAddr);
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("  Adding resource ref "
                             + resource.getName() + "  " + ref);
            }
            createSubcontexts(envCtx, resource.getName());
            envCtx.bind(resource.getName(), ref);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.bindFailed", e));
        }

        if ("javax.sql.DataSource".equals(ref.getClassName()) &&
                resource.getSingleton()) {
            try {
                ObjectName on = createObjectName(resource);
                Object actualResource = envCtx.lookup(resource.getName());
                Registry.getRegistry(null, null).registerComponent(actualResource, on, null);
                objectNames.put(resource.getName(), on);
            } catch (Exception e) {
                logger.warn(sm.getString("naming.jmxRegistrationFailed", e));
            }
        }

    }


    /**
     * Set the specified resources in the naming context.
     */
    public void addResourceEnvRef(ContextResourceEnvRef resourceEnvRef) {

        // Create a reference to the resource env.
        Reference ref = new ResourceEnvRef(resourceEnvRef.getType());
        // Adding the additional parameters, if any
        Iterator<String> params = resourceEnvRef.listProperties();
        while (params.hasNext()) {
            String paramName = params.next();
            String paramValue = (String) resourceEnvRef.getProperty(paramName);
            StringRefAddr refAddr = new StringRefAddr(paramName, paramValue);
            ref.add(refAddr);
        }
        try {
            if (logger.isDebugEnabled())
                log.debug("  Adding resource env ref " + resourceEnvRef.getName());
            createSubcontexts(envCtx, resourceEnvRef.getName());
            envCtx.bind(resourceEnvRef.getName(), ref);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.bindFailed", e));
        }

    }


    /**
     * Set the specified resource link in the naming context.
     */
    public void addResourceLink(ContextResourceLink resourceLink) {

        // Create a reference to the resource.
        Reference ref = new ResourceLinkRef
            (resourceLink.getType(), resourceLink.getGlobal(), resourceLink.getFactory(), null);
        Iterator<String> i = resourceLink.listProperties();
        while (i.hasNext()) {
            String key = i.next();
            Object val = resourceLink.getProperty(key);
            if (val!=null) {
                StringRefAddr refAddr = new StringRefAddr(key, val.toString());
                ref.add(refAddr);
            }
        }
        javax.naming.Context ctx =
            "UserTransaction".equals(resourceLink.getName())
            ? compCtx : envCtx;
        try {
            if (logger.isDebugEnabled())
                log.debug("  Adding resource link " + resourceLink.getName());
            createSubcontexts(envCtx, resourceLink.getName());
            ctx.bind(resourceLink.getName(), ref);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.bindFailed", e));
        }

        ResourceLinkFactory.registerGlobalResourceAccess(
                getGlobalNamingContext(), resourceLink.getName(), resourceLink.getGlobal());
    }


    private javax.naming.Context getGlobalNamingContext() {
        if (container instanceof Context) {
            Engine e = (Engine) ((Context) container).getParent().getParent();
            return e.getService().getServer().getGlobalNamingContext();
        }
        return null;
    }


    /**
     * Set the specified EJBs in the naming context.
     */
    public void removeEjb(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

    }


    /**
     * Set the specified environment entries in the naming context.
     */
    public void removeEnvironment(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

    }


    /**
     * Set the specified local EJBs in the naming context.
     */
    public void removeLocalEjb(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

    }


    /**
     * Set the specified web services in the naming context.
     */
    public void removeService(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

    }


    /**
     * Set the specified resources in the naming context.
     */
    public void removeResource(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

        ObjectName on = objectNames.get(name);
        if (on != null) {
            Registry.getRegistry(null, null).unregisterComponent(on);
        }

    }


    /**
     * Set the specified resources in the naming context.
     */
    public void removeResourceEnvRef(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

    }


    /**
     * Set the specified resources in the naming context.
     */
    public void removeResourceLink(String name) {

        try {
            envCtx.unbind(name);
        } catch (NamingException e) {
            logger.error(sm.getString("naming.unbindFailed", e));
        }

        ResourceLinkFactory.deregisterGlobalResourceAccess(getGlobalNamingContext(), name);
    }


    /**
     * Create all intermediate subcontexts.
     */
    private void createSubcontexts(javax.naming.Context ctx, String name)
        throws NamingException {
        javax.naming.Context currentContext = ctx;
        StringTokenizer tokenizer = new StringTokenizer(name, "/");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if ((!token.equals("")) && (tokenizer.hasMoreTokens())) {
                try {
                    currentContext = currentContext.createSubcontext(token);
                } catch (NamingException e) {
                    // Silent catch. Probably an object is already bound in
                    // the context.
                    currentContext =
                        (javax.naming.Context) currentContext.lookup(token);
                }
            }
        }
    }


}
