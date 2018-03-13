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


import java.util.Hashtable;
import java.util.Set;

import javax.management.DynamicMBean;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Group;
import org.apache.catalina.Host;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.Realm;
import org.apache.catalina.Role;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.ContextResourceLink;
import org.apache.catalina.deploy.NamingResources;
import org.apache.catalina.util.ContextName;
import org.apache.catalina.valves.ValveBase;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.ajp.AjpAprProtocol;
import org.apache.coyote.ajp.AjpProtocol;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.coyote.http11.Http11Protocol;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;


/**
 * Public utility methods in support of the server side MBeans implementation.
 *
 * @author Craig R. McClanahan
 * @author Amy Roh
 */
public class MBeanUtils {

    // ------------------------------------------------------- Static Variables

    /**
     * The set of exceptions to the normal rules used by
     * <code>createManagedBean()</code>.  The first element of each pair
     * is a class name, and the second element is the managed bean name.
     */
    private static String exceptions[][] = {
        { "org.apache.catalina.users.MemoryGroup",
          "Group" },
        { "org.apache.catalina.users.MemoryRole",
          "Role" },
        { "org.apache.catalina.users.MemoryUser",
          "User" },
    };


    /**
     * The configuration information registry for our managed beans.
     */
    private static Registry registry = createRegistry();


    /**
     * The <code>MBeanServer</code> for this application.
     */
    private static MBeanServer mserver = createServer();


    // --------------------------------------------------------- Static Methods

    /**
     * Create and return the name of the <code>ManagedBean</code> that
     * corresponds to this Catalina component.
     *
     * @param component The component for which to create a name
     */
    static String createManagedName(Object component) {

        // Deal with exceptions to the standard rule
        String className = component.getClass().getName();
        for (int i = 0; i < exceptions.length; i++) {
            if (className.equals(exceptions[i][0])) {
                return (exceptions[i][1]);
            }
        }

        // Perform the standard transformation
        int period = className.lastIndexOf('.');
        if (period >= 0)
            className = className.substring(period + 1);
        return (className);

    }

    
    /**
     * Create, register, and return an MBean for this
     * <code>ContextEnvironment</code> object.
     *
     * @param environment The ContextEnvironment to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public static DynamicMBean createMBean(ContextEnvironment environment)
        throws Exception {

        String mname = createManagedName(environment);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(environment);
        ObjectName oname = createObjectName(domain, environment);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>ContextResource</code> object.
     *
     * @param resource The ContextResource to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public static DynamicMBean createMBean(ContextResource resource)
        throws Exception {

        String mname = createManagedName(resource);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(resource);
        ObjectName oname = createObjectName(domain, resource);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>ContextResourceLink</code> object.
     *
     * @param resourceLink The ContextResourceLink to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    public static DynamicMBean createMBean(ContextResourceLink resourceLink)
        throws Exception {

        String mname = createManagedName(resourceLink);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(resourceLink);
        ObjectName oname = createObjectName(domain, resourceLink);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }    
 

    /**
     * Create, register, and return an MBean for this
     * <code>Group</code> object.
     *
     * @param group The Group to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    static DynamicMBean createMBean(Group group)
        throws Exception {

        String mname = createManagedName(group);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(group);
        ObjectName oname = createObjectName(domain, group);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Loader</code> object.
     *
     * @param loader The Loader to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static DynamicMBean createMBean(Loader loader)
        throws Exception {

        String mname = createManagedName(loader);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(loader);
        ObjectName oname = createObjectName(domain, loader);
        if( mserver.isRegistered( oname ))  {
            // side effect: stop it
            mserver.unregisterMBean( oname );
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>MBeanFactory</code> object.
     *
     * @param factory The MBeanFactory to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static DynamicMBean createMBean(MBeanFactory factory)
        throws Exception {

        String mname = createManagedName(factory);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(factory);
        ObjectName oname = createObjectName(domain, factory);
        if( mserver.isRegistered(oname )) {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>NamingResources</code> object.
     *
     * @param resource The NamingResources to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     *
     * @deprecated Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static DynamicMBean createMBean(NamingResources resource)
        throws Exception {

        String mname = createManagedName(resource);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(resource);
        ObjectName oname = createObjectName(domain, resource);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }

    
    /**
     * Create, register, and return an MBean for this
     * <code>Role</code> object.
     *
     * @param role The Role to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    static DynamicMBean createMBean(Role role)
        throws Exception {

        String mname = createManagedName(role);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(role);
        ObjectName oname = createObjectName(domain, role);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>User</code> object.
     *
     * @param user The User to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    static DynamicMBean createMBean(User user)
        throws Exception {

        String mname = createManagedName(user);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(user);
        ObjectName oname = createObjectName(domain, user);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>UserDatabase</code> object.
     *
     * @param userDatabase The UserDatabase to be managed
     *
     * @exception Exception if an MBean cannot be created or registered
     */
    static DynamicMBean createMBean(UserDatabase userDatabase)
        throws Exception {

        String mname = createManagedName(userDatabase);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with "+mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        DynamicMBean mbean = managed.createMBean(userDatabase);
        ObjectName oname = createObjectName(domain, userDatabase);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Connector</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param connector The Connector to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                        Connector connector)
        throws MalformedObjectNameException {

        ObjectName name = null;
        try {
            Object addressObj = IntrospectionUtils.getProperty(connector, "address");            
            Integer port = (Integer)
                IntrospectionUtils.getProperty(connector, "port");

            StringBuilder sb = new StringBuilder(domain);
            sb.append(":type=Connector");
            sb.append(",port=");
            sb.append(port);
            if (addressObj != null) {
                String address = addressObj.toString();
                if (address.length() > 0) {
                    sb.append(",address=");
                    sb.append(ObjectName.quote(address));
                }
            }
            name = new ObjectName(sb.toString());
            return (name);
        } catch (Exception e) {
            MalformedObjectNameException mone =
                new MalformedObjectNameException
                ("Cannot create object name for " + connector);
            mone.initCause(e);
            throw mone;
        }
    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Context</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param context The Context to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                              Context context)
        throws MalformedObjectNameException {

        ObjectName name = null;
        Host host = (Host)context.getParent();
        ContextName cn = new ContextName(context.getName(), false);
        name = new ObjectName(domain + ":j2eeType=WebModule,name=//" +
                              host.getName()+ cn.getDisplayName() +
                              ",J2EEApplication=none,J2EEServer=none");
    
        return (name);

    }

    
    /**
     * Create an <code>ObjectName</code> for this
     * <code>Service</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param environment The ContextEnvironment to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              ContextEnvironment environment)
        throws MalformedObjectNameException {

        ObjectName name = null;
        Object container = 
                environment.getNamingResources().getContainer();
        if (container instanceof Server) {
            name = new ObjectName(domain + ":type=Environment" + 
                        ",resourcetype=Global,name=" + environment.getName());
        } else if (container instanceof Context) {        
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=Environment" + 
                        ",resourcetype=Context,context=" + cn.getDisplayName() + 
                        ",host=" + host.getName() +
                        ",name=" + environment.getName());
        }        
        return (name);

    }
    
    
    /**
     * Create an <code>ObjectName</code> for this
     * <code>ContextResource</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param resource The ContextResource to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              ContextResource resource)
        throws MalformedObjectNameException {

        ObjectName name = null;
        String quotedResourceName = ObjectName.quote(resource.getName());
        Object container = 
                resource.getNamingResources().getContainer();
        if (container instanceof Server) {        
            name = new ObjectName(domain + ":type=Resource" +
                        ",resourcetype=Global,class=" + resource.getType() + 
                        ",name=" + quotedResourceName);
        } else if (container instanceof Context) {                    
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=Resource" +
                        ",resourcetype=Context,context=" + cn.getDisplayName() + 
                        ",host=" + host.getName() +
                        ",class=" + resource.getType() +
                        ",name=" + quotedResourceName);
        }
        
        return (name);

    }
  
    
     /**
     * Create an <code>ObjectName</code> for this
     * <code>ContextResourceLink</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param resourceLink The ContextResourceLink to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              ContextResourceLink resourceLink)
        throws MalformedObjectNameException {

        ObjectName name = null;
        String quotedResourceLinkName
                = ObjectName.quote(resourceLink.getName());        
        Object container = 
                resourceLink.getNamingResources().getContainer();
        if (container instanceof Server) {        
            name = new ObjectName(domain + ":type=ResourceLink" +
                        ",resourcetype=Global" + 
                        ",name=" + quotedResourceLinkName);
        } else if (container instanceof Context) {                    
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=ResourceLink" +
                        ",resourcetype=Context,context=" + cn.getDisplayName() + 
                        ",host=" + host.getName() +
                        ",name=" + quotedResourceLinkName);
        }
        
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Engine</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param engine The Engine to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain, Engine engine)
        throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Engine");
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Group</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param group The Group to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    static ObjectName createObjectName(String domain,
                                              Group group)
        throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Group,groupname=" +
                              ObjectName.quote(group.getGroupname()) +
                              ",database=" + group.getUserDatabase().getId());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Host</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param host The Host to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                              Host host)
        throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Host,host=" +
                              host.getName());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Loader</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param loader The Loader to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    static ObjectName createObjectName(String domain,
                                              Loader loader)
        throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = loader.getContainer();

        if (container instanceof Engine) {
            name = new ObjectName(domain + ":type=Loader");
        } else if (container instanceof Host) {
            name = new ObjectName(domain + ":type=Loader,host=" +
                              container.getName());
        } else if (container instanceof Context) {
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=Loader,context=" +
                    cn.getDisplayName() + ",host=" + host.getName());
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Manager</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param manager The Manager to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                              Manager manager)
        throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = manager.getContainer();

        if (container instanceof Engine) {
            name = new ObjectName(domain + ":type=Manager");
        } else if (container instanceof Host) {
            name = new ObjectName(domain + ":type=Manager,host=" +
                              container.getName());
        } else if (container instanceof Context) {
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=Manager,context=" +
                    cn.getDisplayName() + ",host=" + host.getName());
        }

        return (name);

    }
    
    
    /**
     * Create an <code>ObjectName</code> for this
     * <code>Server</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param resources The NamingResources to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                              NamingResources resources)
        throws MalformedObjectNameException {

        ObjectName name = null;
        Object container = resources.getContainer();        
        if (container instanceof Server) {        
            name = new ObjectName(domain + ":type=NamingResources" + 
                        ",resourcetype=Global");
        } else if (container instanceof Context) {        
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=NamingResources" + 
                        ",resourcetype=Context,context=" + cn.getDisplayName() + 
                        ",host=" + host.getName());
        }
        
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>MBeanFactory</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param factory The MBeanFactory to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain, MBeanFactory factory)
        throws MalformedObjectNameException {

        ObjectName name = new ObjectName(domain + ":type=MBeanFactory");

        return (name);

    }

    
    /**
     * Create an <code>ObjectName</code> for this
     * <code>Realm</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param realm The Realm to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                              Realm realm)
        throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = realm.getContainer();

        if (container instanceof Engine) {
            name = new ObjectName(domain + ":type=Realm");
        } else if (container instanceof Host) {
            name = new ObjectName(domain + ":type=Realm,host=" +
                              container.getName());
        } else if (container instanceof Context) {
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            name = new ObjectName(domain + ":type=Realm,context=" +
                    cn.getDisplayName() + ",host=" + host.getName());
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Role</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param role The Role to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    static ObjectName createObjectName(String domain, Role role)
            throws MalformedObjectNameException {

         ObjectName name = new ObjectName(domain + ":type=Role,rolename=" +
                 ObjectName.quote(role.getRolename()) +
                 ",database=" + role.getUserDatabase().getId());
        return name;
    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Server</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param server The Server to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain, Server server)
        throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Server");
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Service</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param service The Service to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                              Service service)
        throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Service,serviceName=" + 
                            service.getName());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>User</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param user The User to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    static ObjectName createObjectName(String domain, User user)
            throws MalformedObjectNameException {

        ObjectName name = new ObjectName(domain + ":type=User,username=" +
                ObjectName.quote(user.getUsername()) +
                ",database=" + user.getUserDatabase().getId());
        return name;
    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>UserDatabase</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param userDatabase The UserDatabase to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     */
    static ObjectName createObjectName(String domain,
                                              UserDatabase userDatabase)
        throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=UserDatabase,database=" +
                              userDatabase.getId());
        return (name);

    }

    /**
     * Create an <code>ObjectName</code> for this
     * <code>Valve</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param valve The Valve to be named
     *
     * @exception MalformedObjectNameException if a name cannot be created
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static ObjectName createObjectName(String domain,
                                       Valve valve)
        throws MalformedObjectNameException {
        if( valve instanceof ValveBase ) {
            ObjectName name=((ValveBase)valve).getObjectName();
            if( name != null )
                return name;
        }

        ObjectName name = null;
        Container container = null;
        String className=valve.getClass().getName();
        int period = className.lastIndexOf('.');
        if (period >= 0)
            className = className.substring(period + 1);
        if( valve instanceof Contained ) {
            container = ((Contained)valve).getContainer();
        }
        if( container == null ) {
            throw new MalformedObjectNameException(
                               "Cannot create mbean for non-contained valve " +
                               valve);
        }        
        if (container instanceof Engine) {
            String local="";
            int seq = getSeq(local);
            String ext="";
            if( seq > 0 ) {
                ext=",seq=" + seq;
            }
            name = new ObjectName(domain + ":type=Valve,name=" + className + 
                                    ext + local );
        } else if (container instanceof Host) {
            String local=",host=" +container.getName();
            int seq = getSeq(local);
            String ext="";
            if( seq > 0 ) {
                ext=",seq=" + seq;
            }
            name = new ObjectName(domain + ":type=Valve,name=" + className + 
                                    ext + local );
        } else if (container instanceof Context) {
            Context context = ((Context)container);
            ContextName cn = new ContextName(context.getName(), false);
            Container host = context.getParent();
            String local=",context=" + cn.getDisplayName() + ",host=" +
                    host.getName();
            int seq = getSeq(local);
            String ext="";
            if( seq > 0 ) {
                ext=",seq=" + seq;
            }
            name = new ObjectName(domain + ":type=Valve,name=" + className + 
                                    ext + local );
        }

        return (name);

    }

    /*
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static Hashtable<String,int[]> seq = new Hashtable<String,int[]>();
    /*
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static int getSeq( String key ) {
        int i[]=seq.get( key );
        if (i == null ) {
            i=new int[1];
            i[0]=0;
            seq.put( key, i);
        } else {
            i[0]++;
        }
        return i[0];
    }

    /**
     * Create and configure (if necessary) and return the registry of
     * managed object descriptions.
     */
    public static synchronized Registry createRegistry() {

        if (registry == null) {
            registry = Registry.getRegistry(null, null);
            ClassLoader cl = MBeanUtils.class.getClassLoader();

            registry.loadDescriptors("org.apache.catalina.mbeans",  cl);
            registry.loadDescriptors("org.apache.catalina.authenticator", cl);
            registry.loadDescriptors("org.apache.catalina.core", cl);
            registry.loadDescriptors("org.apache.catalina", cl);
            registry.loadDescriptors("org.apache.catalina.deploy", cl);
            registry.loadDescriptors("org.apache.catalina.loader", cl);
            registry.loadDescriptors("org.apache.catalina.realm", cl);
            registry.loadDescriptors("org.apache.catalina.session", cl);
            registry.loadDescriptors("org.apache.catalina.startup", cl);
            registry.loadDescriptors("org.apache.catalina.users", cl);
            registry.loadDescriptors("org.apache.catalina.ha", cl);
            registry.loadDescriptors("org.apache.catalina.connector", cl);
            registry.loadDescriptors("org.apache.catalina.valves",  cl);
        }
        return (registry);

    }


    /**
     * Create and configure (if necessary) and return the
     * <code>MBeanServer</code> with which we will be
     * registering our <code>DynamicMBean</code> implementations.
     */
    public static synchronized MBeanServer createServer() {

        if (mserver == null) {
            mserver = Registry.getRegistry(null, null).getMBeanServer();
        }
        return (mserver);

    }


    /**
     * Deregister the MBean for this
     * <code>Connector</code> object.
     *
     * @param connector The Connector to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Connector connector, Service service)
        throws Exception {

        // domain is engine name
        String domain = service.getContainer().getName();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, connector);
        if( mserver.isRegistered( oname ))  {
            mserver.unregisterMBean(oname);
        }
        // Unregister associated request processor
        String worker = null;
        ProtocolHandler handler = connector.getProtocolHandler();
        if (handler instanceof Http11Protocol) {
            worker = ((Http11Protocol)handler).getName();
        } else if (handler instanceof Http11NioProtocol) {
            worker = ((Http11NioProtocol)handler).getName();
        } else if (handler instanceof Http11AprProtocol) {
            worker = ((Http11AprProtocol)handler).getName();
        } else if (handler instanceof AjpProtocol) {
            worker = ((AjpProtocol)handler).getName();
        } else if (handler instanceof AjpAprProtocol) {
            worker = ((AjpAprProtocol)handler).getName();
        }
        ObjectName query = new ObjectName(
                domain + ":type=RequestProcessor,worker=" + worker + ",*");
        Set<ObjectName> results = mserver.queryNames(query, null);
        for(ObjectName result : results) {
            mserver.unregisterMBean(result);
        }
    }


    /**
     * Deregister the MBean for this
     * <code>Context</code> object.
     *
     * @param context The Context to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Context context)
        throws Exception {

        String domain = context.getParent().getParent().getName();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, context);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }

    
    /**
     * Deregister the MBean for this
     * <code>ContextEnvironment</code> object.
     *
     * @param environment The ContextEnvironment to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(ContextEnvironment environment)
        throws Exception {

        String mname = createManagedName(environment);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, environment);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }
    
    
    /**
     * Deregister the MBean for this
     * <code>ContextResource</code> object.
     *
     * @param resource The ContextResource to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(ContextResource resource)
        throws Exception {

        // If this is a user database resource need to destroy groups, roles,
        // users and UserDatabase mbean
        if ("org.apache.catalina.UserDatabase".equals(resource.getType())) {
            destroyMBeanUserDatabase(resource.getName());
        }

        String mname = createManagedName(resource);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, resource);
        if( mserver.isRegistered(oname ))
            mserver.unregisterMBean(oname);

    }
     
    
    /**
     * Deregister the MBean for this
     * <code>ContextResourceLink</code> object.
     *
     * @param resourceLink The ContextResourceLink to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(ContextResourceLink resourceLink)
        throws Exception {

        String mname = createManagedName(resourceLink);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, resourceLink);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }   
    
    /**
     * Deregister the MBean for this
     * <code>Engine</code> object.
     *
     * @param engine The Engine to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     *
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Engine engine)
        throws Exception {
        String domain = engine.getName();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, engine);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Group</code> object.
     *
     * @param group The Group to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    static void destroyMBean(Group group)
        throws Exception {

        String mname = createManagedName(group);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, group);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Host</code> object.
     *
     * @param host The Host to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Host host)
        throws Exception {

        String domain = host.getParent().getName();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, host);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Loader</code> object.
     *
     * @param loader The Loader to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Loader loader)
        throws Exception {

        String mname = createManagedName(loader);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, loader);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Manager</code> object.
     *
     * @param manager The Manager to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Manager manager)
        throws Exception {

        String mname = createManagedName(manager);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, manager);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }
    
    
   /**
     * Deregister the MBean for this
     * <code>NamingResources</code> object.
     *
     * @param resources The NamingResources to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(NamingResources resources)
        throws Exception {

        String mname = createManagedName(resources);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, resources);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }
    
    
    /**
     * Deregister the MBean for this
     * <code>Realm</code> object.
     *
     * @param realm The Realm to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Realm realm)
        throws Exception {

        String mname = createManagedName(realm);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, realm);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Role</code> object.
     *
     * @param role The Role to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    static void destroyMBean(Role role)
        throws Exception {

        String mname = createManagedName(role);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, role);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Server</code> object.
     *
     * @param server The Server to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Server server)
        throws Exception {

        String mname = createManagedName(server);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, server);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);
        
        // Global String cache - fixed name
        oname = new ObjectName("Catalina:type=StringCache");
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

        // MBean Factory - fixed name
        oname = new ObjectName("Catalina:type=MBeanFactory");
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Service</code> object.
     *
     * @param service The Service to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Service service)
        throws Exception {

        String mname = createManagedName(service);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, service);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>User</code> object.
     *
     * @param user The User to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    static void destroyMBean(User user)
        throws Exception {

        String mname = createManagedName(user);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, user);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>UserDatabase</code> object.
     *
     * @param userDatabase The UserDatabase to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(UserDatabase userDatabase)
        throws Exception {

        String mname = createManagedName(userDatabase);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, userDatabase);
        if( mserver.isRegistered(oname) )
            mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for the
     * <code>UserDatabase</code> object with this name.
     *
     * @param userDatabase The UserDatabase to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     */
    static void destroyMBeanUserDatabase(String userDatabase)
        throws Exception {

        ObjectName query = null;
        Set<ObjectName> results = null;
        
        // Groups
        query = new ObjectName(
                "Users:type=Group,database=" + userDatabase + ",*");
        results = mserver.queryNames(query, null);
        for(ObjectName result : results) {
            mserver.unregisterMBean(result);
        }
        
        // Roles
        query = new ObjectName(
                "Users:type=Role,database=" + userDatabase + ",*");
        results = mserver.queryNames(query, null);
        for(ObjectName result : results) {
            mserver.unregisterMBean(result);
        }
        
        // Users
        query = new ObjectName(
                "Users:type=User,database=" + userDatabase + ",*");
        results = mserver.queryNames(query, null);
        for(ObjectName result : results) {
            mserver.unregisterMBean(result);
        }

        // The database itself
        ObjectName db = new ObjectName(
                "Users:type=UserDatabase,database=" + userDatabase);
        if(mserver.isRegistered(db)) {
            mserver.unregisterMBean(db);
        }
    }


    /**
     * Deregister the MBean for this
     * <code>Valve</code> object.
     *
     * @param valve The Valve to be managed
     *
     * @exception Exception if an MBean cannot be deregistered
     * @deprecated  Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    static void destroyMBean(Valve valve, Container container)
        throws Exception {

        ((Contained)valve).setContainer(container);
        String mname = createManagedName(valve);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, valve);
        try {
            ((Contained)valve).setContainer(null);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
        }
        if( mserver.isRegistered(oname) ) {
            mserver.unregisterMBean(oname);
        }

    }

    
    /**
     * Determine the name of the domain to register MBeans for from a given
     * Service.
     * 
     * @param service 
     *
     * @deprecated  To be removed since to creates a circular dependency. Will
     *              be replaced in Tomcat 8 by a new method on {@link
     *              Service}.
     */
    @Deprecated
    public static String getDomain(Service service) {
        
        // Null service -> return null
        if (service == null) {
            return null;
        }
        
        String domain = null;
        
        Container engine = service.getContainer();
        
        // Use the engine name first
        if (engine != null) {
            domain = engine.getName();
        }
        
        // No engine or no engine name, use the service name 
        if (domain == null) {
            domain = service.getName();
        }
        
        // No service name, use null
        return domain;
    }
    

    /**
     * Determine the name of the domain to register MBeans for from a given
     * Container.
     * 
     * @param container
     *
     * @deprecated  To be removed since to creates a circular dependency. Will
     *              be replaced in Tomcat 8 by a new method on {@link
     *              Container}.
     */
    @Deprecated
    public static String getDomain(Container container) {
        
        String domain = null;
        
        Container c = container;
        
        while (!(c instanceof Engine) && c != null) {
            c = c.getParent();
        }
        
        if (c != null) {
            domain = c.getName();
        }
        
        return domain;
    }

    
    /**
     * Calculate the key properties string to be added to an object's
     * {@link ObjectName} to indicate that it is associated with that container.
     * 
     * @param container The container the object is associated with 
     * @return          A string suitable for appending to the ObjectName
     * @deprecated  To be removed since to creates a circular dependency. Will
     *              be replaced in Tomcat 8 by a new method on {@link
     *              Container}.
     */
    @Deprecated
    public static String getContainerKeyProperties(Container container) {
        
        Container c = container;
        StringBuilder keyProperties = new StringBuilder();
        int containerCount = 0;
        
        // Work up container hierarchy, add a component to the name for
        // each container
        while (!(c instanceof Engine)) {
            if (c instanceof Wrapper) {
                keyProperties.append(",servlet=");
                keyProperties.append(c.getName());
            } else if (c instanceof Context) {
                keyProperties.append(",context=");
                ContextName cn = new ContextName(c.getName(), false);
                keyProperties.append(cn.getDisplayName());
            } else if (c instanceof Host) {
                keyProperties.append(",host=");
                keyProperties.append(c.getName());
            } else if (c == null) {
                // May happen in unit testing and/or some embedding scenarios
                keyProperties.append(",container");
                keyProperties.append(containerCount++);
                keyProperties.append("=null");
                break;
            } else {
                // Should never happen...
                keyProperties.append(",container");
                keyProperties.append(containerCount++);
                keyProperties.append('=');
                keyProperties.append(c.getName());
            }
            c = c.getParent();
        }

        return keyProperties.toString();
    }
}
