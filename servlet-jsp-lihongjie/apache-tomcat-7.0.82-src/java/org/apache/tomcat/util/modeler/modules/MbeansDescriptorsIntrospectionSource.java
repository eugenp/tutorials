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

package org.apache.tomcat.util.modeler.modules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import javax.management.ObjectName;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.modeler.AttributeInfo;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.OperationInfo;
import org.apache.tomcat.util.modeler.ParameterInfo;
import org.apache.tomcat.util.modeler.Registry;

public class MbeansDescriptorsIntrospectionSource extends ModelerSource
{
    private static final Log log = LogFactory.getLog(MbeansDescriptorsIntrospectionSource.class);

    Registry registry;
    String type;
    List<ObjectName> mbeans = new ArrayList<ObjectName>();

    public void setRegistry(Registry reg) {
        this.registry=reg;
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public void setLocation( String loc ) {
        this.location=loc;
    }

    /** Used if a single component is loaded
     *
     * @param type
     */
    public void setType( String type ) {
       this.type=type;
    }

    public void setSource( Object source ) {
        this.source=source;
    }

    @Override
    public List<ObjectName> loadDescriptors(Registry registry, String type,
            Object source) throws Exception {
        setRegistry(registry);
        setType(type);
        setSource(source);
        execute();
        return mbeans;
    }

    public void execute() throws Exception {
        if( registry==null ) registry=Registry.getRegistry(null, null);
        try {
            ManagedBean managed = createManagedBean(registry, null,
                    (Class<?>)source, type);
            if( managed==null ) return;
            managed.setName( type );

            registry.addManagedBean(managed);

        } catch( Exception ex ) {
            log.error( "Error reading descriptors ", ex);
        }
    }



    // ------------ Implementation for non-declared introspection classes

    static Hashtable<String,String> specialMethods =
        new Hashtable<String,String>();
    static {
        specialMethods.put( "preDeregister", "");
        specialMethods.put( "postDeregister", "");
    }

    private static String strArray[]=new String[0];
    private static ObjectName objNameArray[]=new ObjectName[0];
    // createMBean == registerClass + registerMBean

    private static Class<?>[] supportedTypes  = new Class[] {
        Boolean.class,
        Boolean.TYPE,
        Byte.class,
        Byte.TYPE,
        Character.class,
        Character.TYPE,
        Short.class,
        Short.TYPE,
        Integer.class,
        Integer.TYPE,
        Long.class,
        Long.TYPE,
        Float.class, 
        Float.TYPE,
        Double.class,
        Double.TYPE,
        String.class,
        strArray.getClass(),
        BigDecimal.class,
        BigInteger.class,
        ObjectName.class,
        objNameArray.getClass(),
        java.io.File.class,
    };
    
    /**
     * Check if this class is one of the supported types.
     * If the class is supported, returns true.  Otherwise,
     * returns false.
     * @param ret The class to check
     * @return boolean True if class is supported
     */ 
    private boolean supportedType(Class<?> ret) {
        for (int i = 0; i < supportedTypes.length; i++) {
            if (ret == supportedTypes[i]) {
                return true;
            }
        }
        if (isBeanCompatible(ret)) {
            return true;
        }
        return false;
    }

    /**
     * Check if this class conforms to JavaBeans specifications.
     * If the class is conformant, returns true.
     *
     * @param javaType The class to check
     * @return boolean True if the class is compatible.
     */
    protected boolean isBeanCompatible(Class<?> javaType) {
        // Must be a non-primitive and non array
        if (javaType.isArray() || javaType.isPrimitive()) {
            return false;
        }

        // Anything in the java or javax package that
        // does not have a defined mapping is excluded.
        if (javaType.getName().startsWith("java.") || 
            javaType.getName().startsWith("javax.")) {
            return false;
        }

        try {
            javaType.getConstructor(new Class[]{});
        } catch (java.lang.NoSuchMethodException e) {
            return false;
        }

        // Make sure superclass is compatible
        Class<?> superClass = javaType.getSuperclass();
        if (superClass != null && 
            superClass != java.lang.Object.class && 
            superClass != java.lang.Exception.class && 
            superClass != java.lang.Throwable.class) {
            if (!isBeanCompatible(superClass)) {
                return false;
            }
        }
        return true;
    }
    
    /** 
     * Process the methods and extract 'attributes', methods, etc
     *
     * @param realClass The class to process
     * @param methods The methods to process
     * @param attMap The attribute map (complete)
     * @param getAttMap The readable attributes map
     * @param setAttMap The settable attributes map
     * @param invokeAttMap The invokable attributes map
     */
    private void initMethods(Class<?> realClass,
                             Method methods[],
                             Hashtable<String,Method> attMap,
                             Hashtable<String,Method> getAttMap,
                             Hashtable<String,Method> setAttMap,
                             Hashtable<String,Method> invokeAttMap)
    {
        for (int j = 0; j < methods.length; ++j) {
            String name=methods[j].getName();

            if( Modifier.isStatic(methods[j].getModifiers()))
                continue;
            if( ! Modifier.isPublic( methods[j].getModifiers() ) ) {
                if( log.isDebugEnabled())
                    log.debug("Not public " + methods[j] );
                continue;
            }
            if( methods[j].getDeclaringClass() == Object.class )
                continue;
            Class<?> params[] = methods[j].getParameterTypes();

            if( name.startsWith( "get" ) && params.length==0) {
                Class<?> ret = methods[j].getReturnType();
                if( ! supportedType( ret ) ) {
                    if( log.isDebugEnabled() )
                        log.debug("Unsupported type " + methods[j]);
                    continue;
                }
                name=unCapitalize( name.substring(3));

                getAttMap.put( name, methods[j] );
                // just a marker, we don't use the value
                attMap.put( name, methods[j] );
            } else if( name.startsWith( "is" ) && params.length==0) {
                Class<?> ret = methods[j].getReturnType();
                if( Boolean.TYPE != ret  ) {
                    if( log.isDebugEnabled() )
                        log.debug("Unsupported type " + methods[j] + " " + ret );
                    continue;
                }
                name=unCapitalize( name.substring(2));

                getAttMap.put( name, methods[j] );
                // just a marker, we don't use the value
                attMap.put( name, methods[j] );

            } else if( name.startsWith( "set" ) && params.length==1) {
                if( ! supportedType( params[0] ) ) {
                    if( log.isDebugEnabled() )
                        log.debug("Unsupported type " + methods[j] + " " + params[0]);
                    continue;
                }
                name=unCapitalize( name.substring(3));
                setAttMap.put( name, methods[j] );
                attMap.put( name, methods[j] );
            } else {
                if( params.length == 0 ) {
                    if( specialMethods.get( methods[j].getName() ) != null )
                        continue;
                    invokeAttMap.put( name, methods[j]);
                } else {
                    boolean supported=true;
                    for( int i=0; i<params.length; i++ ) {
                        if( ! supportedType( params[i])) {
                            supported=false;
                            break;
                        }
                    }
                    if( supported )
                        invokeAttMap.put( name, methods[j]);
                }
            }
        }
    }

    /**
     * XXX Find if the 'className' is the name of the MBean or
     *       the real class ( I suppose first )
     * XXX Read (optional) descriptions from a .properties, generated
     *       from source
     * XXX Deal with constructors
     *
     * @param registry The Bean registry (not used)
     * @param domain The bean domain (not used)
     * @param realClass The class to analyze
     * @param type The bean type
     * @return ManagedBean The create MBean
     */
    public ManagedBean createManagedBean(Registry registry, String domain,
                                         Class<?> realClass, String type)
    {
        ManagedBean mbean= new ManagedBean();

        Method methods[]=null;

        Hashtable<String,Method> attMap = new Hashtable<String,Method>();
        // key: attribute val: getter method
        Hashtable<String,Method> getAttMap = new Hashtable<String,Method>();
        // key: attribute val: setter method
        Hashtable<String,Method> setAttMap = new Hashtable<String,Method>();
        // key: operation val: invoke method
        Hashtable<String,Method> invokeAttMap = new Hashtable<String,Method>();

        methods = realClass.getMethods();

        initMethods(realClass, methods, attMap, getAttMap, setAttMap, invokeAttMap );

        try {

            Enumeration<String> en = attMap.keys();
            while( en.hasMoreElements() ) {
                String name = en.nextElement();
                AttributeInfo ai=new AttributeInfo();
                ai.setName( name );
                Method gm = getAttMap.get(name);
                if( gm!=null ) {
                    //ai.setGetMethodObj( gm );
                    ai.setGetMethod( gm.getName());
                    Class<?> t=gm.getReturnType();
                    if( t!=null )
                        ai.setType( t.getName() );
                }
                Method sm = setAttMap.get(name);
                if( sm!=null ) {
                    //ai.setSetMethodObj(sm);
                    Class<?> t = sm.getParameterTypes()[0];
                    if( t!=null )
                        ai.setType( t.getName());
                    ai.setSetMethod( sm.getName());
                }
                ai.setDescription("Introspected attribute " + name);
                if( log.isDebugEnabled()) log.debug("Introspected attribute " +
                        name + " " + gm + " " + sm);
                if( gm==null )
                    ai.setReadable(false);
                if( sm==null )
                    ai.setWriteable(false);
                if( sm!=null || gm!=null )
                    mbean.addAttribute(ai);
            }

            for (Entry<String,Method> entry : invokeAttMap.entrySet()) {
                String name = entry.getKey();
                Method m = entry.getValue();
                if(m != null) {
                    OperationInfo op=new OperationInfo();
                    op.setName(name);
                    op.setReturnType(m.getReturnType().getName());
                    op.setDescription("Introspected operation " + name);
                    Class<?> parms[] = m.getParameterTypes();
                    for(int i=0; i<parms.length; i++ ) {
                        ParameterInfo pi=new ParameterInfo();
                        pi.setType(parms[i].getName());
                        pi.setName( "param" + i);
                        pi.setDescription("Introspected parameter param" + i);
                        op.addParameter(pi);
                    }
                    mbean.addOperation(op);
                } else {
                    log.error("Null arg method for [" + name + "]");
                }
            }

            /*Constructor[] constructors = realClass.getConstructors();
            for(int i=0;i<constructors.length;i++) {
                ConstructorInfo info = new ConstructorInfo();
                String className = realClass.getName();
                int nIndex = -1;
                if((nIndex = className.lastIndexOf('.'))!=-1) {
                    className = className.substring(nIndex+1);
                }
                info.setName(className);
                info.setDescription(constructors[i].getName());
                Class classes[] = constructors[i].getParameterTypes();
                for(int j=0;j<classes.length;j++) {
                    ParameterInfo pi = new ParameterInfo();
                    pi.setType(classes[j].getName());
                    pi.setName("param" + j);
                    pi.setDescription("Introspected parameter param" + j);
                    info.addParameter(pi);
                }
                mbean.addConstructor(info);
            }
            */
            
            if( log.isDebugEnabled())
                log.debug("Setting name: " + type );
            mbean.setName( type );

            return mbean;
        } catch( Exception ex ) {
            ex.printStackTrace();
            return null;
        }
    }


    // -------------------- Utils --------------------
    /**
     * Converts the first character of the given
     * String into lower-case.
     *
     * @param name The string to convert
     * @return String
     */
    private static String unCapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

}

// End of class: MbeanDescriptorsIntrospectionSource
