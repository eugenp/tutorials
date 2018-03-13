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


package org.apache.tomcat.util.modeler;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.management.DynamicMBean;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.modeler.modules.ModelerSource;

/*
   Issues:
   - exceptions - too many "throws Exception"
   - double check the interfaces 
   - start removing the use of the experimental methods in tomcat, then remove
     the methods ( before 1.1 final )
   - is the security enough to prevent Registry being used to avoid the permission
    checks in the mbean server ?
*/ 

/**
 * Registry for modeler MBeans. 
 *
 * This is the main entry point into modeler. It provides methods to create
 * and manipulate model mbeans and simplify their use.
 *
 * Starting with version 1.1, this is no longer a singleton and the static
 * methods are strongly deprecated. In a container environment we can expect
 * different applications to use different registries.
 * 
 * This class is itself an mbean.
 * 
 * IMPORTANT: public methods not marked with @since x.x are experimental or 
 * internal. Should not be used.  
 * 
 * @author Craig R. McClanahan
 * @author Costin Manolache
 */
public class Registry implements RegistryMBean, MBeanRegistration  {
    /**
     * The Log instance to which we will write our log messages.
     */
    private static final Log log = LogFactory.getLog(Registry.class);

    // Support for the factory methods
    
    /** Will be used to isolate different apps and enhance security.
     */
    private static HashMap<Object,Registry> perLoaderRegistries = null;

    /**
     * The registry instance created by our factory method the first time
     * it is called.
     */
    private static Registry registry = null;

    // Per registry fields
    
    /**
     * The <code>MBeanServer</code> instance that we will use to register
     * management beans.
     */
    private MBeanServer server = null;

    /**
     * The set of ManagedBean instances for the beans this registry
     * knows about, keyed by name.
     */
    private HashMap<String,ManagedBean> descriptors =
        new HashMap<String,ManagedBean>();

    /** List of managed beans, keyed by class name
     */
    private HashMap<String,ManagedBean> descriptorsByClass =
        new HashMap<String,ManagedBean>();

    // map to avoid duplicated searching or loading descriptors 
    private HashMap<String,URL> searchedPaths=new HashMap<String,URL>();
    
    private Object guard;

    // Id - small ints to use array access. No reset on stop()
    // Used for notifications
    private Hashtable<String,Hashtable<String,Integer>> idDomains =
        new Hashtable<String,Hashtable<String,Integer>>();
    private Hashtable<String,int[]> ids = new Hashtable<String,int[]>();

    
    // ----------------------------------------------------------- Constructors

    /**
     */
     public Registry() {
        super();
    }

    // -------------------- Static methods  --------------------
    // Factories
    
    /**
     * Factory method to create (if necessary) and return our
     * <code>Registry</code> instance.
     *
     * Use this method to obtain a Registry - all other static methods
     * are deprecated and shouldn't be used.
     *
     * The current version uses a static - future versions could use
     * the thread class loader.
     * 
     * @param key Support for application isolation. If null, the context class
     * loader will be used ( if setUseContextClassLoader is called ) or the 
     * default registry is returned. 
     * @param guard Prevent access to the registry by untrusted components
     *
     * @since 1.1
     */
    public static synchronized Registry getRegistry(Object key, Object guard) {
        Registry localRegistry;
        if( perLoaderRegistries!=null ) {
            if( key==null ) 
                key=Thread.currentThread().getContextClassLoader();
            if( key != null ) {
                localRegistry = perLoaderRegistries.get(key);
                if( localRegistry == null ) {
                    localRegistry=new Registry();
//                    localRegistry.key=key;
                    localRegistry.guard=guard;
                    perLoaderRegistries.put( key, localRegistry );
                    return localRegistry;
                }
                if( localRegistry.guard != null &&
                        localRegistry.guard != guard ) {
                    return null; // XXX Should I throw a permission ex ? 
                }
                return localRegistry;
            }
        }

        // static 
        if (registry == null) {
            registry = new Registry();
        }
        if( registry.guard != null &&
                registry.guard != guard ) {
            return null;
        }
        return (registry);
    }
    
    /** 
     * Allow containers to isolate apps. Can be called only once.
     * It  is highly recommended you call this method if using Registry in
     * a container environment. The default is false for backward compatibility
     * 
     * @param enable
     * @since 1.1
     */
    public static void setUseContextClassLoader( boolean enable ) {
        if( enable ) {
            perLoaderRegistries = new HashMap<Object,Registry>();
        }
    }
    
    // -------------------- Generic methods  --------------------

    /** Lifecycle method - clean up the registry metadata.
     *  Called from resetMetadata().
     * 
     * @since 1.1
     */ 
    @Override
    public void stop() {
        descriptorsByClass = new HashMap<String,ManagedBean>();
        descriptors = new HashMap<String,ManagedBean>();
        searchedPaths=new HashMap<String,URL>();
    }
    
    /** 
     * Load an extended mlet file. The source can be an URL, File or
     * InputStream. 
     * 
     * All mbeans will be instantiated, registered and the attributes will be 
     * set. The result is a list of ObjectNames.
     *
     * @param source InputStream or URL of the file
     * @param cl ClassLoader to be used to load the mbeans, or null to use the
     *        default JMX mechanism ( i.e. all registered loaders )
     * @return List of ObjectName for the loaded mbeans
     * @throws Exception
     * 
     * @since 1.1
     */ 
    @Override
    public List<ObjectName> loadMBeans( Object source, ClassLoader cl )
            throws Exception
    {
        return load("MbeansSource", source, null );
    }    


    /** Load descriptors. The source can be a File or URL or InputStream for the 
     * descriptors file. In the case of File and URL, if the extension is ".ser"
     * a serialized version will be loaded. 
     * 
     * This method should be used to explicitly load metadata - but this is not
     * required in most cases. The registerComponent() method will find metadata
     * in the same package.
     * 
     * @param source
     */ 
    @Override
    public void loadMetadata(Object source ) throws Exception {
        loadDescriptors( null, source, null );
    }

    /** Register a bean by creating a modeler mbean and adding it to the 
     * MBeanServer.
     * 
     * If metadata is not loaded, we'll look up and read a file named
     * "mbeans-descriptors.ser" or "mbeans-descriptors.xml" in the same package
     * or parent.
     *
     * If the bean is an instance of DynamicMBean. it's metadata will be converted
     * to a model mbean and we'll wrap it - so modeler services will be supported
     *
     * If the metadata is still not found, introspection will be used to extract
     * it automatically. 
     * 
     * If an mbean is already registered under this name, it'll be first
     * unregistered.
     * 
     * If the component implements MBeanRegistration, the methods will be called.
     * If the method has a method "setRegistry" that takes a RegistryMBean as
     * parameter, it'll be called with the current registry.
     * 
     *
     * @param bean Object to be registered
     * @param oname Name used for registration
     * @param type The type of the mbean, as declared in mbeans-descriptors. If
     * null, the name of the class will be used. This can be used as a hint or
     * by subclasses.
     *
     * @since 1.1
     */ 
    @Override
    public void registerComponent(Object bean, String oname, String type)
           throws Exception
    {
        registerComponent(bean, new ObjectName(oname), type);        
    }    

    /** Unregister a component. We'll first check if it is registered,
     * and mask all errors. This is mostly a helper.
     * 
     * @param oname
     * 
     * @since 1.1
     */ 
    @Override
    public void unregisterComponent( String oname ) {
        try {
            unregisterComponent(new ObjectName(oname));
        } catch (MalformedObjectNameException e) {
            log.info("Error creating object name " + e );
        }
    }    
    

    /** Invoke a operation on a list of mbeans. Can be used to implement
     * lifecycle operations.
     *
     * @param mbeans list of ObjectName on which we'll invoke the operations
     * @param operation  Name of the operation ( init, start, stop, etc)
     * @param failFirst  If false, exceptions will be ignored
     * @throws Exception
     * @since 1.1
     */
    @Override
    public void invoke(List<ObjectName> mbeans, String operation,
            boolean failFirst ) throws Exception {
        if( mbeans==null ) {
            return;
        }
        Iterator<ObjectName> itr = mbeans.iterator();
        while(itr.hasNext()) {
            ObjectName current = itr.next();
            try {
                if(current == null) {
                    continue;
                }
                if(getMethodInfo(current, operation) == null) {
                    continue;
                }
                getMBeanServer().invoke(current, operation,
                        new Object[] {}, new String[] {});

            } catch( Exception t ) {
                if( failFirst ) throw t;
                log.info("Error initializing " + current + " " + t.toString());
            }
        }
    }

    // -------------------- ID registry --------------------

    /** Return an int ID for faster access. Will be used for notifications
     * and for other operations we want to optimize. 
     *
     * @param domain Namespace 
     * @param name  Type of the notification
     * @return  An unique id for the domain:name combination
     * @since 1.1
     */
    @Override
    public synchronized int getId( String domain, String name) {
        if( domain==null) {
            domain="";
        }
        Hashtable<String,Integer> domainTable = idDomains.get(domain);
        if( domainTable == null ) {
            domainTable = new Hashtable<String,Integer>();
            idDomains.put( domain, domainTable); 
        }
        if( name==null ) {
            name="";
        }
        Integer i = domainTable.get(name);
        
        if( i!= null ) {
            return i.intValue();
        }

        int id[] = ids.get(domain);
        if( id == null ) {
            id=new int[1];
            ids.put( domain, id); 
        }
        int code=id[0]++;
        domainTable.put( name, Integer.valueOf( code ));
        return code;
    }
    
    // -------------------- Metadata   --------------------
    // methods from 1.0

    /**
     * Add a new bean metadata to the set of beans known to this registry.
     * This is used by internal components.
     *
     * @param bean The managed bean to be added
     * @since 1.0
     */
    public void addManagedBean(ManagedBean bean) {
        // XXX Use group + name
        descriptors.put(bean.getName(), bean);
        if( bean.getType() != null ) {
            descriptorsByClass.put( bean.getType(), bean );
        }
    }


    /**
     * Find and return the managed bean definition for the specified
     * bean name, if any; otherwise return <code>null</code>.
     *
     * @param name Name of the managed bean to be returned. Since 1.1, both
     *   short names or the full name of the class can be used.
     * @since 1.0
     */
    public ManagedBean findManagedBean(String name) {
        // XXX Group ?? Use Group + Type
        ManagedBean mb = descriptors.get(name);
        if( mb==null )
            mb = descriptorsByClass.get(name);
        return mb;
    }
    
    /**
     * Return the set of bean names for all managed beans known to
     * this registry.
     *
     * @since 1.0
     */
    public String[] findManagedBeans() {
        return descriptors.keySet().toArray(new String[0]);
    }


    /**
     * Return the set of bean names for all managed beans known to
     * this registry that belong to the specified group.
     *
     * @param group Name of the group of interest, or <code>null</code>
     *  to select beans that do <em>not</em> belong to a group
     * @since 1.0
     */
    public String[] findManagedBeans(String group) {

        ArrayList<String> results = new ArrayList<String>();
        Iterator<ManagedBean> items = descriptors.values().iterator();
        while (items.hasNext()) {
            ManagedBean item = items.next();
            if ((group == null)) {
                if (item.getGroup() == null){
                    results.add(item.getName());
                }
            } else if (group.equals(item.getGroup())) {
                results.add(item.getName());
            }
        }
        String values[] = new String[results.size()];
        return results.toArray(values);

    }


    /**
     * Remove an existing bean from the set of beans known to this registry.
     *
     * @param bean The managed bean to be removed
     * @since 1.0
     */
    public void removeManagedBean(ManagedBean bean) {
       // TODO: change this to use group/name
        descriptors.remove(bean.getName());
        descriptorsByClass.remove( bean.getType());
    }

    // -------------------- Helpers  --------------------

    /** Get the type of an attribute of the object, from the metadata.
     *
     * @param oname
     * @param attName
     * @return null if metadata about the attribute is not found
     * @since 1.1
     */
    public String getType( ObjectName oname, String attName )
    {
        String type=null;
        MBeanInfo info=null;
        try {
            info=server.getMBeanInfo(oname);
        } catch (Exception e) {
            log.info( "Can't find metadata for object" + oname );
            return null;
        }

        MBeanAttributeInfo attInfo[]=info.getAttributes();
        for( int i=0; i<attInfo.length; i++ ) {
            if( attName.equals(attInfo[i].getName())) {
                type=attInfo[i].getType();
                return type;
            }
        }
        return null;
    }

    /** Find the operation info for a method
     * 
     * @param oname
     * @param opName
     * @return the operation info for the specified operation
     */ 
    public MBeanOperationInfo getMethodInfo( ObjectName oname, String opName )
    {
        MBeanInfo info=null;
        try {
            info=server.getMBeanInfo(oname);
        } catch (Exception e) {
            log.info( "Can't find metadata " + oname );
            return null;
        }
        MBeanOperationInfo attInfo[]=info.getOperations();
        for( int i=0; i<attInfo.length; i++ ) {
            if( opName.equals(attInfo[i].getName())) {
                return attInfo[i];
            }
        }
        return null;
    }

    /** Unregister a component. This is just a helper that
     * avoids exceptions by checking if the mbean is already registered
     *
     * @param oname
     */
    public void unregisterComponent( ObjectName oname ) {
        try {
            if( getMBeanServer().isRegistered(oname)) {
                getMBeanServer().unregisterMBean(oname);
            }
        } catch( Throwable t ) {
            log.error( "Error unregistering mbean ", t);
        }
    }

    /**
     * Factory method to create (if necessary) and return our
     * <code>MBeanServer</code> instance.
     *
     */
    public synchronized MBeanServer getMBeanServer() {
        long t1=System.currentTimeMillis();

        if (server == null) {
            if( MBeanServerFactory.findMBeanServer(null).size() > 0 ) {
                server = MBeanServerFactory.findMBeanServer(null).get(0);
                if( log.isDebugEnabled() ) {
                    log.debug("Using existing MBeanServer " + (System.currentTimeMillis() - t1 ));
                }
            } else {
                server = ManagementFactory.getPlatformMBeanServer();
                if( log.isDebugEnabled() ) {
                    log.debug("Creating MBeanServer"+ (System.currentTimeMillis() - t1 ));
                }
            }
        }
        return (server);
    }

    /** Find or load metadata. 
     */ 
    public ManagedBean findManagedBean(Object bean, Class<?> beanClass,
            String type) throws Exception {
        if( bean!=null && beanClass==null ) {
            beanClass=bean.getClass();
        }
        
        if( type==null ) {
            type=beanClass.getName();
        }
        
        // first look for existing descriptor
        ManagedBean managed = findManagedBean(type);

        // Search for a descriptor in the same package
        if( managed==null ) {
            // check package and parent packages
            if( log.isDebugEnabled() ) {
                log.debug( "Looking for descriptor ");
            }
            findDescriptor( beanClass, type );

            managed=findManagedBean(type);
        }
        
        // Still not found - use introspection
        if( managed==null ) {
            if( log.isDebugEnabled() ) {
                log.debug( "Introspecting ");
            }

            // introspection
            loadDescriptors("MbeansDescriptorsIntrospectionSource",
                    beanClass, type);

            managed=findManagedBean(type);
            if( managed==null ) {
                log.warn( "No metadata found for " + type );
                return null;
            }
            managed.setName( type );
            addManagedBean(managed);
        }
        return managed;
    }
    

    /** EXPERIMENTAL Convert a string to object, based on type. Used by several
     * components. We could provide some pluggability. It is here to keep
     * things consistent and avoid duplication in other tasks 
     * 
     * @param type Fully qualified class name of the resulting value
     * @param value String value to be converted
     * @return Converted value
     */ 
    public Object convertValue(String type, String value)
    {
        Object objValue=value;
        
        if( type==null || "java.lang.String".equals( type )) {
            // string is default
            objValue=value;
        } else if( "javax.management.ObjectName".equals( type ) ||
                "ObjectName".equals( type )) {
            try {
                objValue=new ObjectName( value );
            } catch (MalformedObjectNameException e) {
                return null;
            }
        } else if( "java.lang.Integer".equals( type ) ||
                "int".equals( type )) {
            objValue=Integer.valueOf( value );
        } else if( "java.lang.Long".equals( type ) ||
                "long".equals( type )) {
            objValue=Long.valueOf( value );
        } else if( "java.lang.Boolean".equals( type ) ||
                "boolean".equals( type )) {
            objValue=Boolean.valueOf( value );
        }
        return objValue;
    }
    
    /** Experimental.
     *
     * @param sourceType
     * @param source
     * @param param
     * @return List of descriptors
     * @throws Exception
     */
    public List<ObjectName> load( String sourceType, Object source,
            String param) throws Exception {
        if( log.isTraceEnabled()) {
            log.trace("load " + source );
        }
        String location=null;
        String type=null;
        Object inputsource=null;

        if( source instanceof URL ) {
            URL url=(URL)source;
            location=url.toString();
            type=param;
            inputsource=url.openStream();
            if( sourceType == null ) {
                sourceType = sourceTypeFromExt(location);
            }
        } else if( source instanceof File ) {
            location=((File)source).getAbsolutePath();
            inputsource=new FileInputStream((File)source);            
            type=param;
            if( sourceType == null ) {
                sourceType = sourceTypeFromExt(location);
            }
        } else if( source instanceof InputStream ) {
            type=param;
            inputsource=source;
        } else if( source instanceof Class<?> ) {
            location=((Class<?>)source).getName();
            type=param;
            inputsource=source;
            if( sourceType== null ) {
                sourceType="MbeansDescriptorsIntrospectionSource";
            }
        }
        
        if( sourceType==null ) {
            sourceType="MbeansDescriptorsDigesterSource";
        }
        ModelerSource ds=getModelerSource(sourceType);
        List<ObjectName> mbeans =
            ds.loadDescriptors(this, type, inputsource);

        return mbeans;
    }

    private String sourceTypeFromExt( String s ) {
        if( s.endsWith( ".ser")) {
            return "MbeansDescriptorsSerSource";
        }
        else if( s.endsWith(".xml")) {
            return "MbeansDescriptorsDigesterSource";
        }
        return null;
    }

    /** Register a component 
     * XXX make it private 
     * 
     * @param bean
     * @param oname
     * @param type
     * @throws Exception
     */ 
    public void registerComponent(Object bean, ObjectName oname, String type)
           throws Exception
    {
        if( log.isDebugEnabled() ) {
            log.debug( "Managed= "+ oname);
        }

        if( bean ==null ) {
            log.error("Null component " + oname );
            return;
        }

        try {
            if( type==null ) {
                type=bean.getClass().getName();
            }

            ManagedBean managed = findManagedBean(bean.getClass(), type);

            // The real mbean is created and registered
            DynamicMBean mbean = managed.createMBean(bean);

            if(  getMBeanServer().isRegistered( oname )) {
                if( log.isDebugEnabled()) {
                    log.debug("Unregistering existing component " + oname );
                }
                getMBeanServer().unregisterMBean( oname );
            }

            getMBeanServer().registerMBean( mbean, oname);
        } catch( Exception ex) {
            log.error("Error registering " + oname, ex );
            throw ex;
        }
    }

    /** Lookup the component descriptor in the package and
     * in the parent packages.
     *
     * @param packageName
     */
    public void loadDescriptors( String packageName, ClassLoader classLoader  ) {
        String res=packageName.replace( '.', '/');

        if( log.isTraceEnabled() ) {
            log.trace("Finding descriptor " + res );
        }

        if( searchedPaths.get( packageName ) != null ) {
            return;
        }
        String descriptors=res + "/mbeans-descriptors.ser";

        URL dURL=classLoader.getResource( descriptors );

        if( dURL == null ) {
            descriptors=res + "/mbeans-descriptors.xml";
            dURL=classLoader.getResource( descriptors );
        }
        if( dURL == null ) {
            return;
        }

        log.debug( "Found " + dURL);
        searchedPaths.put( packageName,  dURL );
        try {
            if( descriptors.endsWith(".xml" ))
                loadDescriptors("MbeansDescriptorsDigesterSource", dURL, null);
            else
                loadDescriptors("MbeansDescriptorsSerSource", dURL, null);
            return;
        } catch(Exception ex ) {
            log.error("Error loading " + dURL);
        }

        return;
    }

    /**
     * @param sourceType
     * @param source
     * @param param
     * @throws Exception

     */
    private void loadDescriptors(String sourceType, Object source,
            String param) throws Exception {
        load(sourceType, source, param);
    }

    /** Lookup the component descriptor in the package and
     * in the parent packages.
     *
     * @param beanClass
     * @param type
     */
    private void findDescriptor(Class<?> beanClass, String type) {
        if( type==null ) {
            type=beanClass.getName();
        }
        ClassLoader classLoader=null;
        if( beanClass!=null ) {
            classLoader=beanClass.getClassLoader();
        }
        if( classLoader==null ) {
            classLoader=Thread.currentThread().getContextClassLoader();
        }
        if( classLoader==null ) {
            classLoader=this.getClass().getClassLoader();
        }
        
        String className=type;
        String pkg=className;
        while( pkg.indexOf( ".") > 0 ) {
            int lastComp=pkg.lastIndexOf( ".");
            if( lastComp <= 0 ) return;
            pkg=pkg.substring(0, lastComp);
            if( searchedPaths.get( pkg ) != null ) {
                return;
            }
            loadDescriptors(pkg, classLoader);
        }
        return;
    }

    private ModelerSource getModelerSource( String type )
            throws Exception
    {
        if( type==null ) type="MbeansDescriptorsDigesterSource";
        if( type.indexOf( ".") < 0 ) {
            type="org.apache.tomcat.util.modeler.modules." + type;
        }

        Class<?> c = Class.forName(type);
        ModelerSource ds=(ModelerSource)c.newInstance();
        return ds;
    }


    // -------------------- Registration  --------------------
    
    @Override
    public ObjectName preRegister(MBeanServer server,
                                  ObjectName name) throws Exception 
    {
        this.server=server;
        return name;
    }

    @Override
    public void postRegister(Boolean registrationDone) {
    }

    @Override
    public void preDeregister() throws Exception {
    }

    @Override
    public void postDeregister() {
    }


    // -------------------- DEPRECATED METHODS  --------------------
    // May still be used in tomcat 
    // Never part of an official release
    
    /**
     * @deprecated
     */
    @Deprecated
    public ManagedBean findManagedBean(Class<?> beanClass, String type)
        throws Exception
    {
        return findManagedBean(null, beanClass, type);        
    }
    
    /**
     * Set the <code>MBeanServer</code> to be utilized for our
     * registered management beans.
     *
     * @param server The new <code>MBeanServer</code> instance
     */
    public void setMBeanServer( MBeanServer server ) {
        this.server=server;
    }

    public void resetMetadata() {
        stop();
    }

}
