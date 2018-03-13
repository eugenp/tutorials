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

package org.apache.catalina.mbeans;

import java.io.File;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Valve;
import org.apache.catalina.authenticator.SingleSignOn;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.realm.DataSourceRealm;
import org.apache.catalina.realm.JDBCRealm;
import org.apache.catalina.realm.JNDIRealm;
import org.apache.catalina.realm.MemoryRealm;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.HostConfig;
import org.apache.catalina.util.LifecycleMBeanBase;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.catalina.valves.RemoteAddrValve;
import org.apache.catalina.valves.RemoteHostValve;
import org.apache.catalina.valves.ValveBase;


/**
 * <p>A <strong>ModelMBean</strong> implementation for the
 * <code>org.apache.catalina.core.StandardServer</code> component.</p>
 *
 * @author Amy Roh
 */
public class MBeanFactory {

    private static final org.apache.juli.logging.Log log = 
        org.apache.juli.logging.LogFactory.getLog(MBeanFactory.class);

    /**
     * The <code>MBeanServer</code> for this application.
     */
    private static MBeanServer mserver = MBeanUtils.createServer();


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a <code>ModelMBean</code> with default
     * <code>ModelMBeanInfo</code> information.
     *
     * @exception javax.management.RuntimeOperationsException if an
     *  IllegalArgumentException occurs
     */
    public MBeanFactory() {

        super();
        
    }


    // ------------------------------------------------------------- Attributes

    /**
     * The container (Server/Service) for which this factory was created.
     */
    private Object container;


    // ------------------------------------------------------------- Operations

    /**
     * Set the container that this factory was created for.
     */
    public void setContainer(Object container) {
        this.container = container;
    }

    /**
     * Return the managed bean definition for the specified bean type
     *
     * @param type MBean type
     *
     * @deprecated  Unused
     */
    @Deprecated
    public String findObjectName(String type) {

        if (type.equals("org.apache.catalina.core.StandardContext")) {
            return "StandardContext";
        } else if (type.equals("org.apache.catalina.core.StandardEngine")) {
            return "Engine";
        } else if (type.equals("org.apache.catalina.core.StandardHost")) {
            return "Host";
        } else {
            return null;
        }

    }


    /**
     * Little convenience method to remove redundant code
     * when retrieving the path string
     *
     * @param t path string
     * @return empty string if t==null || t.equals("/")
     */
    private final String getPathStr(String t) {
        if (t == null || t.equals("/")) {
            return "";
        }
        return t;
    }
    
   /**
     * Get Parent ContainerBase to add its child component 
     * from parent's ObjectName
     */
    private ContainerBase getParentContainerFromParent(ObjectName pname) 
        throws Exception {
        
        String type = pname.getKeyProperty("type");
        String j2eeType = pname.getKeyProperty("j2eeType");
        Service service = getService(pname);
        StandardEngine engine = (StandardEngine) service.getContainer();
        if ((j2eeType!=null) && (j2eeType.equals("WebModule"))) {
            String name = pname.getKeyProperty("name");
            name = name.substring(2);
            int i = name.indexOf('/');
            String hostName = name.substring(0,i);
            String path = name.substring(i);
            Host host = (Host) engine.findChild(hostName);
            String pathStr = getPathStr(path);
            StandardContext context = (StandardContext)host.findChild(pathStr);
            return context;
        } else if (type != null) {
            if (type.equals("Engine")) {
                return engine;
            } else if (type.equals("Host")) {
                String hostName = pname.getKeyProperty("host");
                StandardHost host = (StandardHost) engine.findChild(hostName);
                return host;
            }
        }
        return null;
        
    }


    /**
     * Get Parent ContainerBase to add its child component 
     * from child component's ObjectName  as a String
     */    
    private ContainerBase getParentContainerFromChild(ObjectName oname) 
        throws Exception {
        
        String hostName = oname.getKeyProperty("host");
        String path = oname.getKeyProperty("path");
        Service service = getService(oname);
        StandardEngine engine = (StandardEngine) service.getContainer();
        if (hostName == null) {             
            // child's container is Engine
            return engine;
        } else if (path == null) {      
            // child's container is Host
            StandardHost host = (StandardHost) engine.findChild(hostName);
            return host;
        } else {                
            // child's container is Context
            StandardHost host = (StandardHost) engine.findChild(hostName);
            path = getPathStr(path);
            StandardContext context = (StandardContext) host.findChild(path);
            return context;
        }
    }

    
    private Service getService(ObjectName oname) throws Exception {
    
        if (container instanceof Service) {
            // Don't bother checking the domain - this is the only option
            return (Service) container;
        }

        StandardService service = null;
        String domain = oname.getDomain();
        if (container instanceof Server) {
            Service[] services = ((Server)container).findServices();
            for (int i = 0; i < services.length; i++) {
                service = (StandardService) services[i];
                if (domain.equals(service.getObjectName().getDomain())) {
                    break;
                }
            }
        }
        if (service == null ||
                !service.getObjectName().getDomain().equals(domain)) {
            throw new Exception("Service with the domain is not found");
        }        
        return service;

    }
    
    
    /**
     * Create a new AccessLoggerValve.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated  Will be removed in Tomcat 8.0.x. Replaced by {@link
     *              #createValve(String, String)}.
     */
    @Deprecated
    public String createAccessLoggerValve(String parent)
        throws Exception {

        ObjectName pname = new ObjectName(parent);
        // Create a new AccessLogValve instance
        AccessLogValve accessLogger = new AccessLogValve();
        ContainerBase containerBase = getParentContainerFromParent(pname);
        // Add the new instance to its parent component
        containerBase.getPipeline().addValve(accessLogger);
        ObjectName oname = accessLogger.getObjectName();
        return (oname.toString());

    }
        

    /**
     * Create a new AjpConnector
     *
     * @param parent MBean Name of the associated parent component
     * @param address The IP address on which to bind
     * @param port TCP port number to listen on
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createAjpConnector(String parent, String address, int port)
        throws Exception {

        return createConnector(parent, address, port, true, false);
    }
    
    /**
     * Create a new DataSource Realm.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createDataSourceRealm(String parent, String dataSourceName, 
        String roleNameCol, String userCredCol, String userNameCol, 
        String userRoleTable, String userTable) throws Exception {

        // Create a new DataSourceRealm instance
        DataSourceRealm realm = new DataSourceRealm();
        realm.setDataSourceName(dataSourceName);
        realm.setRoleNameCol(roleNameCol);
        realm.setUserCredCol(userCredCol);
        realm.setUserNameCol(userNameCol);
        realm.setUserRoleTable(userRoleTable);
        realm.setUserTable(userTable);

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        // Add the new instance to its parent component
        containerBase.setRealm(realm);
        // Return the corresponding MBean name
        ObjectName oname = realm.getObjectName();
        if (oname != null) {
            return (oname.toString());
        } else {
            return null;
        }   

    }

    /**
     * Create a new HttpConnector
     *
     * @param parent MBean Name of the associated parent component
     * @param address The IP address on which to bind
     * @param port TCP port number to listen on
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createHttpConnector(String parent, String address, int port)
            throws Exception {
        return createConnector(parent, address, port, false, false);
    }

    /**
     * Create a new Connector
     *
     * @param parent MBean Name of the associated parent component
     * @param address The IP address on which to bind
     * @param port TCP port number to listen on
     * @param isAjp Create a AJP/1.3 Connector
     * @param isSSL Create a secure Connector
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    private String createConnector(String parent, String address, int port, boolean isAjp, boolean isSSL)
        throws Exception {
        Connector retobj = new Connector();
        if ((address!=null) && (address.length()>0)) {
            retobj.setProperty("address", address);
        }
        // Set port number
        retobj.setPort(port);
        // Set the protocol
        retobj.setProtocol(isAjp ? "AJP/1.3" : "HTTP/1.1");
        // Set SSL
        retobj.setSecure(isSSL);
        retobj.setScheme(isSSL ? "https" : "http");
        // Add the new instance to its parent component
        // FIX ME - addConnector will fail
        ObjectName pname = new ObjectName(parent);
        Service service = getService(pname);
        service.addConnector(retobj);
        
        // Return the corresponding MBean name
        ObjectName coname = retobj.getObjectName();
        
        return (coname.toString());
    }


    /**
     * Create a new HttpsConnector
     *
     * @param parent MBean Name of the associated parent component
     * @param address The IP address on which to bind
     * @param port TCP port number to listen on
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createHttpsConnector(String parent, String address, int port)
        throws Exception {
        return createConnector(parent, address, port, false, true);
    }

    /**
     * Create a new JDBC Realm.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createJDBCRealm(String parent, String driverName, 
        String connectionName, String connectionPassword, String connectionURL)
        throws Exception {

        // Create a new JDBCRealm instance
        JDBCRealm realm = new JDBCRealm();
        realm.setDriverName(driverName);
        realm.setConnectionName(connectionName);
        realm.setConnectionPassword(connectionPassword);
        realm.setConnectionURL(connectionURL);

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        // Add the new instance to its parent component
        containerBase.setRealm(realm);
        // Return the corresponding MBean name
        ObjectName oname = realm.getObjectName();

        if (oname != null) {
            return (oname.toString());
        } else {
            return null;
        }   

    }


    /**
     * Create a new JNDI Realm.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createJNDIRealm(String parent)
        throws Exception {

         // Create a new JNDIRealm instance
        JNDIRealm realm = new JNDIRealm();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        // Add the new instance to its parent component
        containerBase.setRealm(realm);
        // Return the corresponding MBean name
        ObjectName oname = realm.getObjectName();

        if (oname != null) {
            return (oname.toString());
        } else {
            return null;
        }   


    }


    /**
     * Create a new Memory Realm.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createMemoryRealm(String parent)
        throws Exception {

         // Create a new MemoryRealm instance
        MemoryRealm realm = new MemoryRealm();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        // Add the new instance to its parent component
        containerBase.setRealm(realm);
        // Return the corresponding MBean name
        ObjectName oname = realm.getObjectName();
        if (oname != null) {
            return (oname.toString());
        } else {
            return null;
        }   

    }


    /**
     * Create a new Remote Address Filter Valve.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated  Will be removed in Tomcat 8.0.x. Replaced by {@link
     *              #createValve(String, String)}.
     */
    @Deprecated
    public String createRemoteAddrValve(String parent)
        throws Exception {

        // Create a new RemoteAddrValve instance
        RemoteAddrValve valve = new RemoteAddrValve();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        containerBase.getPipeline().addValve(valve);
        ObjectName oname = valve.getObjectName();
        return (oname.toString());

    }


     /**
     * Create a new Remote Host Filter Valve.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated  Will be removed in Tomcat 8.0.x. Replaced by {@link
     *              #createValve(String, String)}.
     */
    @Deprecated
    public String createRemoteHostValve(String parent)
        throws Exception {

        // Create a new RemoteHostValve instance
        RemoteHostValve valve = new RemoteHostValve();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        containerBase.getPipeline().addValve(valve);
        ObjectName oname = valve.getObjectName();
        return (oname.toString());
        
    }


    /**
     * Create a new Single Sign On Valve.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated  Will be removed in Tomcat 8.0.x. Replaced by {@link
     *              #createValve(String, String)}.
     */
    @Deprecated

    public String createSingleSignOn(String parent)
        throws Exception {

        // Create a new SingleSignOn instance
        SingleSignOn valve = new SingleSignOn();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        containerBase.getPipeline().addValve(valve);
        ObjectName oname = valve.getObjectName();
        return (oname.toString());

    }
    
    
   /**
     * Create a new StandardContext.
     *
     * @param parent MBean Name of the associated parent component
     * @param path The context path for this Context
     * @param docBase Document base directory (or WAR) for this Context
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createStandardContext(String parent, 
                                        String path,
                                        String docBase)
        throws Exception {
                                            
        return createStandardContext(parent, path, docBase, false, false,
                false, false);                                  
    }


   /**
     * Create a new StandardContext.
     *
     * @param parent MBean Name of the associated parent component
     * @param path The context path for this Context
     * @param docBase Document base directory (or WAR) for this Context
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createStandardContext(String parent, 
                                        String path,
                                        String docBase,
                                        boolean xmlValidation,
                                        boolean xmlNamespaceAware,
                                        boolean tldValidation,
                                        boolean tldNamespaceAware)
        throws Exception {

        // Create a new StandardContext instance
        StandardContext context = new StandardContext();
        path = getPathStr(path);
        context.setPath(path);
        context.setDocBase(docBase);
        context.setXmlValidation(xmlValidation);
        context.setXmlNamespaceAware(xmlNamespaceAware);
        context.setTldValidation(tldValidation);
        context.setTldNamespaceAware(tldNamespaceAware);
        
        ContextConfig contextConfig = new ContextConfig();
        context.addLifecycleListener(contextConfig);

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ObjectName deployer = new ObjectName(pname.getDomain()+
                                             ":type=Deployer,host="+
                                             pname.getKeyProperty("host"));
        if(mserver.isRegistered(deployer)) {
            String contextName = context.getName();
            mserver.invoke(deployer, "addServiced",
                           new Object [] {contextName},
                           new String [] {"java.lang.String"});
            String configPath = (String)mserver.getAttribute(deployer,
                                                             "configBaseName");
            String baseName = context.getBaseName();
            File configFile = new File(new File(configPath), baseName+".xml");
            if (configFile.isFile()) {
                context.setConfigFile(configFile.toURI().toURL());
            }
            mserver.invoke(deployer, "manageApp",
                           new Object[] {context},
                           new String[] {"org.apache.catalina.Context"});
            mserver.invoke(deployer, "removeServiced",
                           new Object [] {contextName},
                           new String [] {"java.lang.String"});
        } else {
            log.warn("Deployer not found for "+pname.getKeyProperty("host"));
            Service service = getService(pname);
            Engine engine = (Engine) service.getContainer();
            Host host = (Host) engine.findChild(pname.getKeyProperty("host"));
            host.addChild(context);
        }

        // Return the corresponding MBean name
        return context.getObjectName().toString();

    }


    /**
     * Create a new StandardHost.
     *
     * @param parent MBean Name of the associated parent component
     * @param name Unique name of this Host
     * @param appBase Application base directory name
     * @param autoDeploy Should we auto deploy?
     * @param deployOnStartup Deploy on server startup?
     * @param deployXML Should we deploy Context XML config files property?
     * @param unpackWARs Should we unpack WARs when auto deploying?
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createStandardHost(String parent, String name,
                                     String appBase,
                                     boolean autoDeploy,
                                     boolean deployOnStartup,
                                     boolean deployXML,                                       
                                     boolean unpackWARs)
        throws Exception {

        // Create a new StandardHost instance
        StandardHost host = new StandardHost();
        host.setName(name);
        host.setAppBase(appBase);
        host.setAutoDeploy(autoDeploy);
        host.setDeployOnStartup(deployOnStartup);
        host.setDeployXML(deployXML);
        host.setUnpackWARs(unpackWARs);
    
        // add HostConfig for active reloading
        HostConfig hostConfig = new HostConfig();
        host.addLifecycleListener(hostConfig);

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        Service service = getService(pname);
        Engine engine = (Engine) service.getContainer();
        engine.addChild(host);

        // Return the corresponding MBean name
        return (host.getObjectName().toString());

    }


    /**
     * Creates a new StandardService and StandardEngine.
     *
     * @param domain       Domain name for the container instance
     * @param defaultHost  Name of the default host to be used in the Engine
     * @param baseDir      Base directory value for Engine 
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createStandardServiceEngine(String domain,
            String defaultHost, String baseDir) throws Exception{

        if (!(container instanceof Server)) {
            throw new Exception("Container not Server");
        }
        
        StandardEngine engine = new StandardEngine();
        engine.setDomain(domain);
        engine.setName(domain);
        engine.setDefaultHost(defaultHost);
        engine.setBaseDir(baseDir);

        Service service = new StandardService();
        service.setContainer(engine);
        service.setName(domain);
        
        ((Server) container).addService(service);
        
        return engine.getObjectName().toString();
    }
    
    
    /**
     * Create a new StandardManager.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createStandardManager(String parent)
        throws Exception {

        // Create a new StandardManager instance
        StandardManager manager = new StandardManager();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        if (containerBase != null) {
            containerBase.setManager(manager);
        } 
        ObjectName oname = manager.getObjectName();
        if (oname != null) {
            return (oname.toString());
        } else {
            return null;
        }
        
    }


    /**
     * Create a new  UserDatabaseRealm.
     *
     * @param parent MBean Name of the associated parent component
     * @param resourceName Global JNDI resource name of the associated
     *  UserDatabase
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createUserDatabaseRealm(String parent, String resourceName)
        throws Exception {

         // Create a new UserDatabaseRealm instance
        UserDatabaseRealm realm = new UserDatabaseRealm();
        realm.setResourceName(resourceName);
        
        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        // Add the new instance to its parent component
        containerBase.setRealm(realm);
        // Return the corresponding MBean name
        ObjectName oname = realm.getObjectName();
        // FIXME getObjectName() returns null
        //ObjectName oname = 
        //    MBeanUtils.createObjectName(pname.getDomain(), realm);
        if (oname != null) {
            return (oname.toString());
        } else {
            return null;
        }   

    }


    /**
     * Create a new Valve and associate it with a {@link Container}.
     *
     * @param className The fully qualified class name of the {@link Valve} to
     *                  create
     * @param parent    The MBean name of the associated parent
     *                  {@link Container}.
     *
     * @return  The MBean name of the {@link Valve} that was created or
     *          <code>null</code> if the {@link Valve} does not implement
     *          {@link LifecycleMBeanBase}.
     */
    public String createValve(String className, String parent)
            throws Exception {

        // Look for the parent
        ObjectName parentName = new ObjectName(parent);
        Container container = getParentContainerFromParent(parentName);

        if (container == null) {
            // TODO
            throw new IllegalArgumentException();
        }

        Valve valve = (Valve) Class.forName(className).newInstance();

        container.getPipeline().addValve(valve);

        if (valve instanceof LifecycleMBeanBase) {
            return ((LifecycleMBeanBase) valve).getObjectName().toString();
        } else {
            return null;
        }
    }


    /**
     * Create a new Web Application Loader.
     *
     * @param parent MBean Name of the associated parent component
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public String createWebappLoader(String parent)
        throws Exception {

        // Create a new WebappLoader instance
        WebappLoader loader = new WebappLoader();

        // Add the new instance to its parent component
        ObjectName pname = new ObjectName(parent);
        ContainerBase containerBase = getParentContainerFromParent(pname);
        if (containerBase != null) {
            containerBase.setLoader(loader);
        } 
        // FIXME add Loader.getObjectName
        //ObjectName oname = loader.getObjectName();
        ObjectName oname = 
            MBeanUtils.createObjectName(pname.getDomain(), loader);
        return (oname.toString());
        
    }


    /**
     * Remove an existing Connector.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeConnector(String name) throws Exception {

        // Acquire a reference to the component to be removed
        ObjectName oname = new ObjectName(name);
        Service service = getService(oname);
        String port = oname.getKeyProperty("port");
        //String address = oname.getKeyProperty("address");

        Connector conns[] = service.findConnectors();

        for (int i = 0; i < conns.length; i++) {
            String connAddress = String.valueOf(conns[i].getProperty("address"));
            String connPort = ""+conns[i].getPort();

            // if (((address.equals("null")) &&
            if ((connAddress==null) && port.equals(connPort)) {
                service.removeConnector(conns[i]);
                conns[i].destroy();
                break;
            }
            // } else if (address.equals(connAddress))
            if (port.equals(connPort)) {
                // Remove this component from its parent component
                service.removeConnector(conns[i]);
                conns[i].destroy();
                break;
            }
        }

    }


    /**
     * Remove an existing Context.
     *
     * @param contextName MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeContext(String contextName) throws Exception {

        // Acquire a reference to the component to be removed
        ObjectName oname = new ObjectName(contextName);
        String domain = oname.getDomain();
        StandardService service = (StandardService) getService(oname);

        Engine engine = (Engine) service.getContainer();
        String name = oname.getKeyProperty("name");
        name = name.substring(2);
        int i = name.indexOf('/');
        String hostName = name.substring(0,i);
        String path = name.substring(i);
        ObjectName deployer = new ObjectName(domain+":type=Deployer,host="+
                                             hostName);
        String pathStr = getPathStr(path);
        if(mserver.isRegistered(deployer)) {
            mserver.invoke(deployer,"addServiced",
                           new Object[]{pathStr},
                           new String[] {"java.lang.String"});
            mserver.invoke(deployer,"unmanageApp",
                           new Object[] {pathStr},
                           new String[] {"java.lang.String"});
            mserver.invoke(deployer,"removeServiced",
                           new Object[] {pathStr},
                           new String[] {"java.lang.String"});
        } else {
            log.warn("Deployer not found for "+hostName);
            Host host = (Host) engine.findChild(hostName);
            Context context = (Context) host.findChild(pathStr);
            // Remove this component from its parent component
            host.removeChild(context);
            if(context instanceof StandardContext)
            try {
                ((StandardContext)context).destroy();
            } catch (Exception e) {
                log.warn("Error during context [" + context.getName() + "] destroy ", e);
           }
   
        }

    }


    /**
     * Remove an existing Host.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeHost(String name) throws Exception {

        // Acquire a reference to the component to be removed
        ObjectName oname = new ObjectName(name);
        String hostName = oname.getKeyProperty("host");
        Service service = getService(oname);
        Engine engine = (Engine) service.getContainer();
        Host host = (Host) engine.findChild(hostName);

        // Remove this component from its parent component
        if(host!=null) {
            engine.removeChild(host);
        }
    }


    /**
     * Remove an existing Loader.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeLoader(String name) throws Exception {

        ObjectName oname = new ObjectName(name);
        // Acquire a reference to the component to be removed
        ContainerBase container = getParentContainerFromChild(oname);    
        container.setLoader(null);
        
    }


    /**
     * Remove an existing Manager.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeManager(String name) throws Exception {

        ObjectName oname = new ObjectName(name);
        // Acquire a reference to the component to be removed
        ContainerBase container = getParentContainerFromChild(oname);    
        container.setManager(null);

    }


    /**
     * Remove an existing Realm.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeRealm(String name) throws Exception {

        ObjectName oname = new ObjectName(name);
        // Acquire a reference to the component to be removed
        ContainerBase container = getParentContainerFromChild(oname); 
        container.setRealm(null);
    }


    /**
     * Remove an existing Service.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeService(String name) throws Exception {

        if (!(container instanceof Server)) {
            throw new Exception();
        }
        
        // Acquire a reference to the component to be removed
        ObjectName oname = new ObjectName(name);
        Service service = getService(oname); 
        ((Server) container).removeService(service);
    }


    /**
     * Remove an existing Valve.
     *
     * @param name MBean Name of the component to remove
     *
     * @exception Exception if a component cannot be removed
     */
    public void removeValve(String name) throws Exception {

        // Acquire a reference to the component to be removed
        ObjectName oname = new ObjectName(name);
        ContainerBase container = getParentContainerFromChild(oname);
        Valve[] valves = container.getPipeline().getValves();
        for (int i = 0; i < valves.length; i++) {
            ObjectName voname = ((ValveBase) valves[i]).getObjectName();
            if (voname.equals(oname)) {
                container.getPipeline().removeValve(valves[i]);
            }
        }
    }

}

