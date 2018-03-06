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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.sql.DriverManager;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.compat.JreVendor;
import org.apache.tomcat.util.res.StringManager;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;

/**
 * Provide a workaround for known places where the Java Runtime environment can
 * cause a memory leak or lock files.
 * <p>
 * Memory leaks occur when JRE code uses
 * the context class loader to load a singleton as this will cause a memory leak
 * if a web application class loader happens to be the context class loader at
 * the time. The work-around is to initialise these singletons when Tomcat's
 * common class loader is the context class loader.
 * <p>
 * Locked files usually occur when a resource inside a JAR is accessed without
 * first disabling Jar URL connection caching. The workaround is to disable this
 * caching by default.
 */
public class JreMemoryLeakPreventionListener implements LifecycleListener {

    private static final Log log =
        LogFactory.getLog(JreMemoryLeakPreventionListener.class);
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);

    private static final boolean IS_JAVA_7_OR_LATER;
    private static final boolean IS_JAVA_8_OR_LATER;

    static {
        boolean isJava7OrLater;
        try {
            Class.forName("java.util.Objects");
            isJava7OrLater = true;
        } catch (ClassNotFoundException e) {
            isJava7OrLater = false;
        }
        IS_JAVA_7_OR_LATER = isJava7OrLater;

        boolean isJava8OrLater;
        try {
            Class.forName("java.util.Optional");
            isJava8OrLater = true;
        } catch (ClassNotFoundException e) {
            isJava8OrLater = false;
        }
        IS_JAVA_8_OR_LATER = isJava8OrLater;

    }

    private static final String FORK_JOIN_POOL_THREAD_FACTORY_PROPERTY =
            "java.util.concurrent.ForkJoinPool.common.threadFactory";

    /**
     * Protect against the memory leak caused when the first call to
     * <code>sun.awt.AppContext.getAppContext()</code> is triggered by a web
     * application. Defaults to <code>true</code> for Java 6 and earlier (since
     * it is used by {@link java.beans.Introspector#flushCaches()}) but defaults
     * to <code>false</code> for Java 7 and later since
     * {@link java.beans.Introspector#flushCaches()} no longer uses AppContext
     * from 1.7.0_02 onwards. Also, from 1.7.0_25 onwards, calling this method
     * requires a graphical environment and starts an AWT thread.
     */
    private boolean appContextProtection = !IS_JAVA_7_OR_LATER;
    public boolean isAppContextProtection() { return appContextProtection; }
    public void setAppContextProtection(boolean appContextProtection) {
        this.appContextProtection = appContextProtection;
    }

    /**
     * Protect against the memory leak caused when the first call to
     * <code>java.awt.Toolkit.getDefaultToolkit()</code> is triggered
     * by a web application. Defaults to <code>false</code> because a new
     * Thread is launched.
     */
    private boolean awtThreadProtection = false;
    public boolean isAWTThreadProtection() { return awtThreadProtection; }
    public void setAWTThreadProtection(boolean awtThreadProtection) {
      this.awtThreadProtection = awtThreadProtection;
    }

    /**
     * Protect against the memory leak caused when the
     * <code>sun.java2d.Disposer</code> class is loaded by a web application.
     * Defaults to <code>false</code> because a new Thread is launched.
     */
    private boolean java2dDisposerProtection = false;
    public boolean isJava2DDisposerProtection() {
        return java2dDisposerProtection;
    }
    public void setJava2DDisposerProtection(boolean java2dDisposerProtection) {
        this.java2dDisposerProtection = java2dDisposerProtection;
    }

    /**
     * Protect against the memory leak caused when the first call to
     * <code>sun.misc.GC.requestLatency(long)</code> is triggered by a web
     * application. This first call will start a GC Daemon thread with the
     * thread's context class loader configured to be the web application class
     * loader. Defaults to <code>true</code>.
     */
    private boolean gcDaemonProtection = true;
    public boolean isGcDaemonProtection() { return gcDaemonProtection; }
    public void setGcDaemonProtection(boolean gcDaemonProtection) {
        this.gcDaemonProtection = gcDaemonProtection;
    }

     /**
      * Protect against the memory leak caused when the first call to
      * <code>javax.security.auth.Policy</code> is triggered by a web
      * application. This first call populate a static variable with a reference
      * to the context class loader. Defaults to <code>true</code>.
      */
     private boolean securityPolicyProtection = true;
     public boolean isSecurityPolicyProtection() {
         return securityPolicyProtection;
     }
     public void setSecurityPolicyProtection(boolean securityPolicyProtection) {
         this.securityPolicyProtection = securityPolicyProtection;
     }

    /**
     * Protects against the memory leak caused when the first call to
     * <code>javax.security.auth.login.Configuration</code> is triggered by a
     * web application. This first call populate a static variable with a
     * reference to the context class loader. Defaults to <code>true</code>.
     */
    private boolean securityLoginConfigurationProtection = true;
    public boolean isSecurityLoginConfigurationProtection() {
        return securityLoginConfigurationProtection;
    }
    public void setSecurityLoginConfigurationProtection(
            boolean securityLoginConfigurationProtection) {
        this.securityLoginConfigurationProtection = securityLoginConfigurationProtection;
    }

     /**
     * Protect against the memory leak, when the initialization of the
     * Java Cryptography Architecture is triggered by initializing
     * a MessageDigest during web application deployment.
     * This will occasionally start a Token Poller thread with the thread's
     * context class loader equal to the web application class loader.
     * Instead we initialize JCA early.
     * Defaults to <code>true</code>.
     */
    private boolean tokenPollerProtection = true;
    public boolean isTokenPollerProtection() { return tokenPollerProtection; }
    public void setTokenPollerProtection(boolean tokenPollerProtection) {
        this.tokenPollerProtection = tokenPollerProtection;
    }

    /**
     * Protect against resources being read for JAR files and, as a side-effect,
     * the JAR file becoming locked. Note this disables caching for all
     * {@link URLConnection}s, regardless of type. Defaults to
     * <code>true</code>.
     */
    private boolean urlCacheProtection = true;
    public boolean isUrlCacheProtection() { return urlCacheProtection; }
    public void setUrlCacheProtection(boolean urlCacheProtection) {
        this.urlCacheProtection = urlCacheProtection;
    }

    /**
     * XML parsing can pin a web application class loader in memory. There are
     * multiple root causes for this. Some of these are particularly nasty as
     * profilers may not identify any GC roots related to the leak. For example,
     * with YourKit you need to ensure that HPROF format memory snapshots are
     * used to be able to trace some of the leaks.
     */
    private boolean xmlParsingProtection = true;
    public boolean isXmlParsingProtection() { return xmlParsingProtection; }
    public void setXmlParsingProtection(boolean xmlParsingProtection) {
        this.xmlParsingProtection = xmlParsingProtection;
    }

    /**
     * <code>com.sun.jndi.ldap.LdapPoolManager</code> class spawns a thread when
     * it is initialized if the system property
     * <code>com.sun.jndi.ldap.connect.pool.timeout</code> is greater than 0.
     * That thread inherits the context class loader of the current thread, so
     * that there may be a web application class loader leak if the web app
     * is the first to use <code>LdapPoolManager</code>.
     */
    private boolean ldapPoolProtection = true;
    public boolean isLdapPoolProtection() { return ldapPoolProtection; }
    public void setLdapPoolProtection(boolean ldapPoolProtection) {
        this.ldapPoolProtection = ldapPoolProtection;
    }

    /**
     * The first access to {@link DriverManager} will trigger the loading of
     * all {@link java.sql.Driver}s in the the current class loader. The web
     * application level memory leak protection can take care of this in most
     * cases but triggering the loading here has fewer side-effects.
     */
    private boolean driverManagerProtection = true;
    public boolean isDriverManagerProtection() {
        return driverManagerProtection;
    }
    public void setDriverManagerProtection(boolean driverManagerProtection) {
        this.driverManagerProtection = driverManagerProtection;
    }

    /**
     * {@link java.util.concurrent.ForkJoinPool#commonPool()} creates a thread
     * pool that, by default, creates threads that retain references to the
     * thread context class loader.
     */
    private boolean forkJoinCommonPoolProtection = true;
    public boolean getForkJoinCommonPoolProtection() {
        return forkJoinCommonPoolProtection;
    }
    public void setForkJoinCommonPoolProtection(boolean forkJoinCommonPoolProtection) {
        this.forkJoinCommonPoolProtection = forkJoinCommonPoolProtection;
    }

    /**
     * List of comma-separated fully qualified class names to load and initialize during
     * the startup of this Listener. This allows to pre-load classes that are known to
     * provoke classloader leaks if they are loaded during a request processing.
     */
    private String classesToInitialize = null;
    public String getClassesToInitialize() {
        return classesToInitialize;
    }
    public void setClassesToInitialize(String classesToInitialize) {
        this.classesToInitialize = classesToInitialize;
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        // Initialise these classes when Tomcat starts
        if (Lifecycle.BEFORE_INIT_EVENT.equals(event.getType())) {

            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            try
            {
                // Use the system classloader as the victim for all this
                // ClassLoader pinning we're about to do.
                Thread.currentThread().setContextClassLoader(
                        ClassLoader.getSystemClassLoader());

                /*
                 * First call to this loads all drivers in the current class
                 * loader
                 */
                if (driverManagerProtection) {
                    DriverManager.getDrivers();
                }

                /*
                 * Several components end up calling:
                 * sun.awt.AppContext.getAppContext()
                 *
                 * Those libraries / components known to trigger memory leaks
                 * due to eventual calls to getAppContext() are:
                 * - Google Web Toolkit via its use of javax.imageio
                 * - Tomcat via its use of java.beans.Introspector.flushCaches()
                 *   in 1.6.0_15 to 1.7.0_01. From 1.7.0_02 onwards use of
                 *   AppContext by Introspector.flushCaches() was replaced with
                 *   ThreadGroupContext
                 * - others TBD
                 *
                 * From 1.7.0_25 onwards, a call to
                 * sun.awt.AppContext.getAppContext() results in a thread being
                 * started named AWT-AppKit that requires a graphic environment
                 * to be available.
                 */

                // Trigger a call to sun.awt.AppContext.getAppContext(). This
                // will pin the system class loader in memory but that shouldn't
                // be an issue.
                if (appContextProtection) {
                    ImageIO.getCacheDirectory();
                }

                // Trigger the creation of the AWT (AWT-Windows, AWT-XAWT,
                // etc.) thread
                if (awtThreadProtection) {
                  java.awt.Toolkit.getDefaultToolkit();
                }

                // Trigger the creation of the "Java2D Disposer" thread.
                // See https://bz.apache.org/bugzilla/show_bug.cgi?id=51687
                if(java2dDisposerProtection) {
                    try {
                        Class.forName("sun.java2d.Disposer");
                    }
                    catch (ClassNotFoundException cnfe) {
                        // Ignore this case: we must be running on a
                        // non-Sun-based JRE.
                    }
                }

                /*
                 * Several components end up calling
                 * sun.misc.GC.requestLatency(long) which creates a daemon
                 * thread without setting the TCCL.
                 *
                 * Those libraries / components known to trigger memory leaks
                 * due to eventual calls to requestLatency(long) are:
                 * - javax.management.remote.rmi.RMIConnectorServer.start()
                 *
                 * Note: Long.MAX_VALUE is a special case that causes the thread
                 *       to terminate
                 *
                 */
                if (gcDaemonProtection) {
                    try {
                        Class<?> clazz = Class.forName("sun.misc.GC");
                        Method method = clazz.getDeclaredMethod(
                                "requestLatency",
                                new Class[] {long.class});
                        method.invoke(null, Long.valueOf(Long.MAX_VALUE - 1));
                    } catch (ClassNotFoundException e) {
                        if (JreVendor.IS_ORACLE_JVM) {
                            log.error(sm.getString(
                                    "jreLeakListener.gcDaemonFail"), e);
                        } else {
                            log.debug(sm.getString(
                                    "jreLeakListener.gcDaemonFail"), e);
                        }
                    } catch (SecurityException e) {
                        log.error(sm.getString("jreLeakListener.gcDaemonFail"),
                                e);
                    } catch (NoSuchMethodException e) {
                        log.error(sm.getString("jreLeakListener.gcDaemonFail"),
                                e);
                    } catch (IllegalArgumentException e) {
                        log.error(sm.getString("jreLeakListener.gcDaemonFail"),
                                e);
                    } catch (IllegalAccessException e) {
                        log.error(sm.getString("jreLeakListener.gcDaemonFail"),
                                e);
                    } catch (InvocationTargetException e) {
                        ExceptionUtils.handleThrowable(e.getCause());
                        log.error(sm.getString("jreLeakListener.gcDaemonFail"),
                                e);
                    }
                }

                /*
                 * Calling getPolicy retains a static reference to the context
                 * class loader.
                 */
                if (securityPolicyProtection) {
                    try {
                        // Policy.getPolicy();
                        Class<?> policyClass = Class
                                .forName("javax.security.auth.Policy");
                        Method method = policyClass.getMethod("getPolicy");
                        method.invoke(null);
                    } catch(ClassNotFoundException e) {
                        // Ignore. The class is deprecated.
                    } catch(SecurityException e) {
                        // Ignore. Don't need call to getPolicy() to be
                        // successful, just need to trigger static initializer.
                    } catch (NoSuchMethodException e) {
                        log.warn(sm.getString("jreLeakListener.authPolicyFail"),
                                e);
                    } catch (IllegalArgumentException e) {
                        log.warn(sm.getString("jreLeakListener.authPolicyFail"),
                                e);
                    } catch (IllegalAccessException e) {
                        log.warn(sm.getString("jreLeakListener.authPolicyFail"),
                                e);
                    } catch (InvocationTargetException e) {
                        ExceptionUtils.handleThrowable(e.getCause());
                        log.warn(sm.getString("jreLeakListener.authPolicyFail"),
                                e);
                    }
                }


                /*
                 * Initializing javax.security.auth.login.Configuration retains a static reference to the context
                 * class loader.
                 */
                if (securityLoginConfigurationProtection) {
                    try {
                        Class.forName("javax.security.auth.login.Configuration", true, ClassLoader.getSystemClassLoader());
                    } catch(ClassNotFoundException e) {
                        // Ignore
                    }
                }

                /*
                 * Creating a MessageDigest during web application startup
                 * initializes the Java Cryptography Architecture. Under certain
                 * conditions this starts a Token poller thread with TCCL equal
                 * to the web application class loader.
                 *
                 * Instead we initialize JCA right now.
                 */
                if (tokenPollerProtection) {
                    java.security.Security.getProviders();
                }

                /*
                 * Several components end up opening JarURLConnections without
                 * first disabling caching. This effectively locks the file.
                 * Whilst more noticeable and harder to ignore on Windows, it
                 * affects all operating systems.
                 *
                 * Those libraries/components known to trigger this issue
                 * include:
                 * - log4j versions 1.2.15 and earlier
                 * - javax.xml.bind.JAXBContext.newInstance()
                 */

                // Set the default URL caching policy to not to cache
                if (urlCacheProtection) {
                    try {
                        // Doesn't matter that this JAR doesn't exist - just as
                        // long as the URL is well-formed
                        URL url = new URL("jar:file://dummy.jar!/");
                        URLConnection uConn = url.openConnection();
                        uConn.setDefaultUseCaches(false);
                    } catch (IOException e) {
                        log.error(sm.getString(
                                "jreLeakListener.jarUrlConnCacheFail"), e);
                    }
                }

                /*
                 * Various leaks related to the use of XML parsing.
                 */
                if (xmlParsingProtection) {
                    // There are three known issues with XML parsing
                    // 1. DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6916498
                    // This issue is fixed in Java 7 onwards
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    try {
                        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

                        // The 2nd and 3rd links both relate to cached Exception
                        // instances that retain a link to the TCCL via the
                        // backtrace field. Note that YourKit only shows this
                        // field when using the HPROF format memory snapshots.
                        // https://bz.apache.org/bugzilla/show_bug.cgi?id=58486
                        // These issues are currently present in all current
                        // versions of Java

                        // 2. com.sun.org.apache.xml.internal.serialize.DOMSerializerImpl
                        Document document = documentBuilder.newDocument();
                        document.createElement("dummy");
                        DOMImplementationLS implementation =
                                (DOMImplementationLS)document.getImplementation();
                        implementation.createLSSerializer().writeToString(document);
                        // 3. com.sun.org.apache.xerces.internal.dom.DOMNormalizer
                        document.normalize();
                    } catch (ParserConfigurationException e) {
                        log.error(sm.getString("jreLeakListener.xmlParseFail"),
                                e);
                    }
                }

                if (ldapPoolProtection) {
                    try {
                        Class.forName("com.sun.jndi.ldap.LdapPoolManager");
                    } catch (ClassNotFoundException e) {
                        if (JreVendor.IS_ORACLE_JVM) {
                            log.error(sm.getString(
                                    "jreLeakListener.ldapPoolManagerFail"), e);
                        } else {
                            log.debug(sm.getString(
                                    "jreLeakListener.ldapPoolManagerFail"), e);
                        }
                    }
                }

                /*
                 * Present in Java 8 onwards
                 */
                if (forkJoinCommonPoolProtection && IS_JAVA_8_OR_LATER) {
                    // Don't override any explicitly set property
                    if (System.getProperty(FORK_JOIN_POOL_THREAD_FACTORY_PROPERTY) == null) {
                        System.setProperty(FORK_JOIN_POOL_THREAD_FACTORY_PROPERTY,
                                "org.apache.catalina.startup.SafeForkJoinWorkerThreadFactory");
                    }
                }

                if (classesToInitialize != null) {
                    StringTokenizer strTok =
                        new StringTokenizer(classesToInitialize, ", \r\n\t");
                    while (strTok.hasMoreTokens()) {
                        String classNameToLoad = strTok.nextToken();
                        try {
                            Class.forName(classNameToLoad);
                        } catch (ClassNotFoundException e) {
                            log.error(
                                sm.getString("jreLeakListener.classToInitializeFail",
                                    classNameToLoad), e);
                            // continue with next class to load
                        }
                    }
                }

            } finally {
                Thread.currentThread().setContextClassLoader(loader);
            }
        }
    }
}
