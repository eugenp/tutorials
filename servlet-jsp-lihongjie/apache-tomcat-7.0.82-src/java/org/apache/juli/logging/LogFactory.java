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

package org.apache.juli.logging;


import java.util.Properties;
import java.util.logging.LogManager;


/**
 * Modified LogFactory: removed all discovery, hardcode a specific implementation
 * If you like a different logging implementation - use either the discovery-based
 * commons-logging, or better - another implementation hardcoded to your favourite
 * logging impl.
 * 
 * Why ? Each application and deployment can choose a logging implementation - 
 * that involves configuration, installing the logger jar and optional plugins, etc.
 * As part of this process - they can as well install the commons-logging implementation
 * that corresponds to their logger of choice. This completely avoids any discovery
 * problem, while still allowing the user to switch. 
 * 
 * Note that this implementation is not just a wrapper around JDK logging ( like
 * the original commons-logging impl ). It adds 2 features - a simpler configuration
 * ( which is in fact a subset of log4j.properties ) and a formatter that is 
 * less ugly.   
 * 
 * The removal of 'abstract' preserves binary backward compatibility. It is possible
 * to preserve the abstract - and introduce another ( hardcoded ) factory - but I 
 * see no benefit. 
 * 
 * Since this class is not intended to be extended - and provides
 * no plugin for other LogFactory implementation - all protected methods are removed.
 * This can be changed - but again, there is little value in keeping dead code.
 * Just take a quick look at the removed code ( and it's complexity)  
 * 
 * --------------
 * 
 * Original comment:
 * <p>Factory for creating {@link Log} instances, with discovery and
 * configuration features similar to that employed by standard Java APIs
 * such as JAXP.</p>
 * 
 * <p><strong>IMPLEMENTATION NOTE</strong> - This implementation is heavily
 * based on the SAXParserFactory and DocumentBuilderFactory implementations
 * (corresponding to the JAXP pluggability APIs) found in Apache Xerces.</p>
 * 
 *
 * @author Craig R. McClanahan
 * @author Costin Manolache
 * @author Richard A. Sitze
 */
public /* abstract */ class LogFactory {

    // ----------------------------------------------------- Manifest Constants

    /**
     * The name of the property used to identify the LogFactory implementation
     * class name.
     */
    public static final String FACTORY_PROPERTY =
        "org.apache.commons.logging.LogFactory";

    /**
     * The fully qualified class name of the fallback <code>LogFactory</code>
     * implementation class to use, if no other can be found.
     */
    public static final String FACTORY_DEFAULT =
        "org.apache.commons.logging.impl.LogFactoryImpl";

    /**
     * The name of the properties file to search for.
     */
    public static final String FACTORY_PROPERTIES =
        "commons-logging.properties";
    
    /**
     * <p>Setting this system property value allows the <code>Hashtable</code> used to store
     * classloaders to be substituted by an alternative implementation.
     * </p>
     * <p>
     * <strong>Note:</strong> <code>LogFactory</code> will print:
     * <code><pre>
     * [ERROR] LogFactory: Load of custom hashtable failed</em>
     * </code></pre>
     * to system error and then continue using a standard Hashtable.
     * </p>
     * <p>
     * <strong>Usage:</strong> Set this property when Java is invoked
     * and <code>LogFactory</code> will attempt to load a new instance 
     * of the given implementation class.
     * For example, running the following ant scriptlet:
     * <code><pre>
     *  &lt;java classname="${test.runner}" fork="yes" failonerror="${test.failonerror}"&gt;
     *     ...
     *     &lt;sysproperty 
     *        key="org.apache.commons.logging.LogFactory.HashtableImpl"
     *        value="org.apache.commons.logging.AltHashtable"/&gt;
     *  &lt;/java&gt;
     * </pre></code>
     * will mean that <code>LogFactory</code> will load an instance of
     * <code>org.apache.commons.logging.AltHashtable</code>.
     * </p>
     * <p>
     * A typical use case is to allow a custom
     * Hashtable implementation using weak references to be substituted.
     * This will allow classloaders to be garbage collected without
     * the need to release them (on 1.3+ JVMs only, of course ;)
     * </p>
     */
    public static final String HASHTABLE_IMPLEMENTATION_PROPERTY =
        "org.apache.commons.logging.LogFactory.HashtableImpl";
    
    private static LogFactory singleton=new LogFactory();

    Properties logConfig;
    
    // ----------------------------------------------------------- Constructors


    /**
     * Protected constructor that is not available for public use.
     */
    private LogFactory() {
        logConfig=new Properties();
    }
    
    // hook for syserr logger - class level
    void setLogConfig( Properties p ) {
        this.logConfig=p;
    }
    // --------------------------------------------------------- Public Methods

    // only those 2 methods need to change to use a different direct logger.
    
    /**
     * <p>Construct (if necessary) and return a <code>Log</code> instance,
     * using the factory's current set of configuration attributes.</p>
     *
     * <p><strong>NOTE</strong> - Depending upon the implementation of
     * the <code>LogFactory</code> you are using, the <code>Log</code>
     * instance you are returned may or may not be local to the current
     * application, and may or may not be returned again on a subsequent
     * call with the same name argument.</p>
     *
     * @param name Logical name of the <code>Log</code> instance to be
     *  returned (the meaning of this name is only known to the underlying
     *  logging implementation that is being wrapped)
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public Log getInstance(String name)
        throws LogConfigurationException {
        return DirectJDKLog.getInstance(name);
    }


    /**
     * Release any internal references to previously created {@link Log}
     * instances returned by this factory.  This is useful in environments
     * like servlet containers, which implement application reloading by
     * throwing away a ClassLoader.  Dangling references to objects in that
     * class loader would prevent garbage collection.
     */
    public void release() {
        DirectJDKLog.release();
    }

    /**
     * Return the configuration attribute with the specified name (if any),
     * or <code>null</code> if there is no such attribute.
     *
     * @param name Name of the attribute to return
     */
    public Object getAttribute(String name) {
        return logConfig.get(name);
    }


    /**
     * Return an array containing the names of all currently defined
     * configuration attributes.  If there are no such attributes, a zero
     * length array is returned.
     */
    public String[] getAttributeNames() {
        String result[] = new String[logConfig.size()];
        return logConfig.keySet().toArray(result);
    }

    /**
     * Remove any configuration attribute associated with the specified name.
     * If there is no such attribute, no action is taken.
     *
     * @param name Name of the attribute to remove
     */
    public void removeAttribute(String name) {
        logConfig.remove(name);
     }   


    /**
     * Set the configuration attribute with the specified name.  Calling
     * this with a <code>null</code> value is equivalent to calling
     * <code>removeAttribute(name)</code>.
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set, or <code>null</code>
     *  to remove any setting for this attribute
     */
    public void setAttribute(String name, Object value) {
        logConfig.put(name, value);
    }


    /**
     * Convenience method to derive a name from the specified class and
     * call <code>getInstance(String)</code> with it.
     *
     * @param clazz Class for which a suitable Log name will be derived
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public Log getInstance(Class<?> clazz)
        throws LogConfigurationException {
        return getInstance( clazz.getName());
    }


    // ------------------------------------------------------- Static Variables


    // --------------------------------------------------------- Static Methods


    /**
     * <p>Construct (if necessary) and return a <code>LogFactory</code>
     * instance, using the following ordered lookup procedure to determine
     * the name of the implementation class to be loaded.</p>
     * <ul>
     * <li>The <code>org.apache.commons.logging.LogFactory</code> system
     *     property.</li>
     * <li>The JDK 1.3 Service Discovery mechanism</li>
     * <li>Use the properties file <code>commons-logging.properties</code>
     *     file, if found in the class path of this class.  The configuration
     *     file is in standard <code>java.util.Properties</code> format and
     *     contains the fully qualified name of the implementation class
     *     with the key being the system property defined above.</li>
     * <li>Fall back to a default implementation class
     *     (<code>org.apache.commons.logging.impl.LogFactoryImpl</code>).</li>
     * </ul>
     *
     * <p><em>NOTE</em> - If the properties file method of identifying the
     * <code>LogFactory</code> implementation class is utilized, all of the
     * properties defined in this file will be set as configuration attributes
     * on the corresponding <code>LogFactory</code> instance.</p>
     *
     * @exception LogConfigurationException if the implementation class is not
     *  available or cannot be instantiated.
     */
    public static LogFactory getFactory() throws LogConfigurationException {
        return singleton;
    }


    /**
     * Convenience method to return a named logger, without the application
     * having to care about factories.
     *
     * @param clazz Class from which a log name will be derived
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public static Log getLog(Class<?> clazz)
        throws LogConfigurationException {
        return (getFactory().getInstance(clazz));

    }


    /**
     * Convenience method to return a named logger, without the application
     * having to care about factories.
     *
     * @param name Logical name of the <code>Log</code> instance to be
     *  returned (the meaning of this name is only known to the underlying
     *  logging implementation that is being wrapped)
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public static Log getLog(String name)
        throws LogConfigurationException {
        return (getFactory().getInstance(name));

    }


    /**
     * Release any internal references to previously created {@link LogFactory}
     * instances that have been associated with the specified class loader
     * (if any), after calling the instance method <code>release()</code> on
     * each of them.
     *
     * @param classLoader ClassLoader for which to release the LogFactory
     */
    public static void release(ClassLoader classLoader) {
        // JULI's log manager looks at the current classLoader so there is no
        // need to use the passed in classLoader, the default implementation
        // does not so calling reset in that case will break things
        if (!LogManager.getLogManager().getClass().getName().equals(
                "java.util.logging.LogManager")) {
            LogManager.getLogManager().reset();
        }
    }


    /**
     * Release any internal references to previously created {@link LogFactory}
     * instances, after calling the instance method <code>release()</code> on
     * each of them.  This is useful in environments like servlet containers,
     * which implement application reloading by throwing away a ClassLoader.
     * Dangling references to objects in that class loader would prevent
     * garbage collection.
     */
    public static void releaseAll() {
        singleton.release();
    }

    /**
     * Returns a string that uniquely identifies the specified object, including
     * its class.
     * <p>
     * The returned string is of form "classname@hashcode", ie is the same as
     * the return value of the Object.toString() method, but works even when
     * the specified object's class has overridden the toString method.
     * 
     * @param o may be null.
     * @return a string of form classname@hashcode, or "null" if param o is null.
     */
    public static String objectId(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.getClass().getName() + "@" + System.identityHashCode(o);
        }
    }
}
