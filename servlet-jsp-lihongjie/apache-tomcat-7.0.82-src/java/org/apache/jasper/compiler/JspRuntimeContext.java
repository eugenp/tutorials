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

package org.apache.jasper.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.cert.Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspFactory;

import org.apache.jasper.Constants;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.Options;
import org.apache.jasper.runtime.ExceptionUtils;
import org.apache.jasper.runtime.JspFactoryImpl;
import org.apache.jasper.security.SecurityClassLoad;
import org.apache.jasper.servlet.JspServletWrapper;
import org.apache.jasper.util.FastRemovalDequeue;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;


/**
 * Class for tracking JSP compile time file dependencies when the
 * &060;%@include file="..."%&062; directive is used.
 *
 * A background thread periodically checks the files a JSP page
 * is dependent upon.  If a dependent file changes the JSP page
 * which included it is recompiled.
 *
 * Only used if a web application context is a directory.
 *
 * @author Glenn L. Nielsen
 */
public final class JspRuntimeContext {

    // Logger
    private final Log log = LogFactory.getLog(JspRuntimeContext.class);

    /*
     * Counts how many times the webapp's JSPs have been reloaded.
     */
    private AtomicInteger jspReloadCount = new AtomicInteger(0);

    /*
     * Counts how many times JSPs have been unloaded in this webapp.
     */
    private AtomicInteger jspUnloadCount = new AtomicInteger(0);

    /**
     * Preload classes required at runtime by a JSP servlet so that
     * we don't get a defineClassInPackage security exception.
     */
    static {
        JspFactoryImpl factory = new JspFactoryImpl();
        SecurityClassLoad.securityClassLoad(factory.getClass().getClassLoader());
        if( System.getSecurityManager() != null ) {
            String basePackage = "org.apache.jasper.";
            try {
                factory.getClass().getClassLoader().loadClass( basePackage +
                                                               "runtime.JspFactoryImpl$PrivilegedGetPageContext");
                factory.getClass().getClassLoader().loadClass( basePackage +
                                                               "runtime.JspFactoryImpl$PrivilegedReleasePageContext");
                factory.getClass().getClassLoader().loadClass( basePackage +
                                                               "runtime.JspRuntimeLibrary");
                factory.getClass().getClassLoader().loadClass( basePackage +
                                                               "runtime.ServletResponseWrapperInclude");
                factory.getClass().getClassLoader().loadClass( basePackage +
                                                               "servlet.JspServletWrapper");
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException(ex);
            }
        }

        JspFactory.setDefaultFactory(factory);
    }

    // ----------------------------------------------------------- Constructors

    /**
     * Create a JspRuntimeContext for a web application context.
     *
     * Loads in any previously generated dependencies from file.
     *
     * @param context ServletContext for web application
     */
    public JspRuntimeContext(ServletContext context, Options options) {

        this.context = context;
        this.options = options;

        // Get the parent class loader
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = this.getClass().getClassLoader();
        }

        if (log.isDebugEnabled()) {
            if (loader != null) {
                log.debug(Localizer.getMessage("jsp.message.parent_class_loader_is",
                                               loader.toString()));
            } else {
                log.debug(Localizer.getMessage("jsp.message.parent_class_loader_is",
                                               "<none>"));
            }
        }

        parentClassLoader =  loader;
        classpath = initClassPath();

        if (context instanceof org.apache.jasper.servlet.JspCServletContext) {
            codeSource = null;
            permissionCollection = null;
            return;
        }

        if (Constants.IS_SECURITY_ENABLED) {
            SecurityHolder holder = initSecurity();
            codeSource = holder.cs;
            permissionCollection = holder.pc;
        } else {
            codeSource = null;
            permissionCollection = null;
        }

        // If this web application context is running from a
        // directory, start the background compilation thread
        String appBase = context.getRealPath("/");         
        if (!options.getDevelopment()
                && appBase != null
                && options.getCheckInterval() > 0) {
            lastCompileCheck = System.currentTimeMillis();
        }                                            

        if (options.getMaxLoadedJsps() > 0) {
            jspQueue = new FastRemovalDequeue<JspServletWrapper>(options.getMaxLoadedJsps());
            if (log.isDebugEnabled()) {
                log.debug(Localizer.getMessage("jsp.message.jsp_queue_created",
                                               "" + options.getMaxLoadedJsps(), context.getContextPath()));
            }
        }

        /* Init parameter is in seconds, locally we use milliseconds */
        jspIdleTimeout = options.getJspIdleTimeout() * 1000;
    }

    // ----------------------------------------------------- Instance Variables

    /**
     * This web applications ServletContext
     */
    private final ServletContext context;
    private final Options options;
    private final ClassLoader parentClassLoader;
    private final PermissionCollection permissionCollection;
    private final CodeSource codeSource;                    
    private final String classpath;
    private volatile long lastCompileCheck = -1L;
    private volatile long lastJspQueueUpdate = System.currentTimeMillis();
    /* JSP idle timeout in milliseconds */
    private long jspIdleTimeout;

    /**
     * Maps JSP pages to their JspServletWrapper's
     */
    private final Map<String, JspServletWrapper> jsps =
            new ConcurrentHashMap<String, JspServletWrapper>();

    /**
     * Keeps JSP pages ordered by last access. 
     */
    private FastRemovalDequeue<JspServletWrapper> jspQueue = null;

    // ------------------------------------------------------ Public Methods

    /**
     * Add a new JspServletWrapper.
     *
     * @param jspUri JSP URI
     * @param jsw Servlet wrapper for JSP
     */
    public void addWrapper(String jspUri, JspServletWrapper jsw) {
        jsps.put(jspUri, jsw);
    }

    /**
     * Get an already existing JspServletWrapper.
     *
     * @param jspUri JSP URI
     * @return JspServletWrapper for JSP
     */
    public JspServletWrapper getWrapper(String jspUri) {
        return jsps.get(jspUri);
    }

    /**
     * Remove a  JspServletWrapper.
     *
     * @param jspUri JSP URI of JspServletWrapper to remove
     */
    public void removeWrapper(String jspUri) {
        jsps.remove(jspUri);
    }

    /**
     * Push a newly compiled JspServletWrapper into the queue at first
     * execution of jsp. Destroy any JSP that has been replaced in the queue.
     *
     * @param jsw Servlet wrapper for jsp.
     * @return an unloadHandle that can be pushed to front of queue at later execution times.
     * */
    public FastRemovalDequeue<JspServletWrapper>.Entry push(JspServletWrapper jsw) {
        if (log.isTraceEnabled()) {
            log.trace(Localizer.getMessage("jsp.message.jsp_added",
                                           jsw.getJspUri(), context.getContextPath()));
        }
        FastRemovalDequeue<JspServletWrapper>.Entry entry = jspQueue.push(jsw);
        JspServletWrapper replaced = entry.getReplaced();
        if (replaced != null) {
            if (log.isDebugEnabled()) {
                log.debug(Localizer.getMessage("jsp.message.jsp_removed_excess",
                                               replaced.getJspUri(), context.getContextPath()));
            }
            unloadJspServletWrapper(replaced);
            entry.clearReplaced();
        }
        return entry;
    }
    
    /**
     * Push unloadHandle for JspServletWrapper to front of the queue.
     *
     * @param unloadHandle the unloadHandle for the jsp.
     * */
    public void makeYoungest(FastRemovalDequeue<JspServletWrapper>.Entry unloadHandle) {
        if (log.isTraceEnabled()) {
            JspServletWrapper jsw = unloadHandle.getContent();
            log.trace(Localizer.getMessage("jsp.message.jsp_queue_update",
                                           jsw.getJspUri(), context.getContextPath()));
        }
        jspQueue.moveFirst(unloadHandle);
    }
    
    /**
     * Returns the number of JSPs for which JspServletWrappers exist, i.e.,
     * the number of JSPs that have been loaded into the webapp.
     *
     * @return The number of JSPs that have been loaded into the webapp
     */
    public int getJspCount() {
        return jsps.size();
    }

    /**
     * Get the SecurityManager Policy CodeSource for this web
     * application context.
     *
     * @return CodeSource for JSP
     */
    public CodeSource getCodeSource() {
        return codeSource;
    }

    /**
     * Get the parent ClassLoader.
     *
     * @return ClassLoader parent
     */
    public ClassLoader getParentClassLoader() {
        return parentClassLoader;
    }

    /**
     * Get the SecurityManager PermissionCollection for this
     * web application context.
     *
     * @return PermissionCollection permissions
     */
    public PermissionCollection getPermissionCollection() {
        return permissionCollection;
    }

    /**
     * Process a "destroy" event for this web application context.
     */                                                        
    public void destroy() {
        Iterator<JspServletWrapper> servlets = jsps.values().iterator();
        while (servlets.hasNext()) {
            servlets.next().destroy();
        }
    }

    /**
     * Increments the JSP reload counter.
     */
    public void incrementJspReloadCount() {
        jspReloadCount.incrementAndGet();
    }

    /**
     * Resets the JSP reload counter.
     *
     * @param count Value to which to reset the JSP reload counter
     */
    public void setJspReloadCount(int count) {
        jspReloadCount.set(count);
    }

    /**
     * Gets the current value of the JSP reload counter.
     *
     * @return The current value of the JSP reload counter
     */
    public int getJspReloadCount() {
        return jspReloadCount.intValue();
    }

    /**
     * Gets the number of JSPs that are in the JSP limiter queue
     *
     * @return The number of JSPs (in the webapp with which this JspServlet is
     * associated) that are in the JSP limiter queue
     */
    public int getJspQueueLength() {
        if (jspQueue != null) {
            return jspQueue.getSize();
        }
        return -1;
    }

    /**
     * Increments the JSP unload counter.
     */
    public void incrementJspUnloadCount() {
        jspUnloadCount.incrementAndGet();
    }

    /**
     * Gets the number of JSPs that have been unloaded.
     *
     * @return The number of JSPs (in the webapp with which this JspServlet is
     * associated) that have been unloaded
     */
    public int getJspUnloadCount() {
        return jspUnloadCount.intValue();
    }


    /**
     * Method used by background thread to check the JSP dependencies
     * registered with this class for JSP's.
     */
    public void checkCompile() {

        if (lastCompileCheck < 0) {
            // Checking was disabled
            return;
        }
        long now = System.currentTimeMillis();
        if (now > (lastCompileCheck + (options.getCheckInterval() * 1000L))) {
            lastCompileCheck = now;
        } else {
            return;
        }
        
        Object [] wrappers = jsps.values().toArray();
        for (int i = 0; i < wrappers.length; i++ ) {
            JspServletWrapper jsw = (JspServletWrapper)wrappers[i];
            JspCompilationContext ctxt = jsw.getJspEngineContext();
            // JspServletWrapper also synchronizes on this when
            // it detects it has to do a reload
            synchronized(jsw) {
                try {
                    ctxt.compile();
                } catch (FileNotFoundException ex) {
                    ctxt.incrementRemoved();
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    jsw.getServletContext().log("Background compile failed",
                                                t);
                }
            }
        }

    }

    /**
     * The classpath that is passed off to the Java compiler.
     */
    public String getClassPath() {
        return classpath;
    }

    /**
     * Last time the update background task has run
     */
    public long getLastJspQueueUpdate() {
        return lastJspQueueUpdate;
    }


    // -------------------------------------------------------- Private Methods


    /**
     * Method used to initialize classpath for compiles.
     */
    private String initClassPath() {

        StringBuilder cpath = new StringBuilder();

        if (parentClassLoader instanceof URLClassLoader) {
            URL [] urls = ((URLClassLoader)parentClassLoader).getURLs();
    
            for(int i = 0; i < urls.length; i++) {
                // Tomcat 4 can use URL's other than file URL's,
                // a protocol other than file: will generate a
                // bad file system path, so only add file:
                // protocol URL's to the classpath.
                
                if( urls[i].getProtocol().equals("file") ) {
                    cpath.append(urls[i].getFile()+File.pathSeparator);
                }
            }
        }

        cpath.append(options.getScratchDir() + File.pathSeparator);

        String cp = (String) context.getAttribute(Constants.SERVLET_CLASSPATH);
        if (cp == null || cp.equals("")) {
            cp = options.getClassPath();
        }

        String path = cpath.toString() + cp;

        if(log.isDebugEnabled()) {
            log.debug("Compilation classpath initialized: " + path);
        }
        return path;
    }

    // Helper class to allow initSecurity() to return two items
    private static class SecurityHolder{
        private final CodeSource cs;
        private final PermissionCollection pc;
        private SecurityHolder(CodeSource cs, PermissionCollection pc){
            this.cs = cs;
            this.pc = pc;
        }
    }
    /**
     * Method used to initialize SecurityManager data.
     */
    private SecurityHolder initSecurity() {

        // Setup the PermissionCollection for this web app context
        // based on the permissions configured for the root of the
        // web app context directory, then add a file read permission
        // for that directory.
        Policy policy = Policy.getPolicy();
        CodeSource source = null;
        PermissionCollection permissions = null;
        if( policy != null ) {
            try {          
                // Get the permissions for the web app context
                String docBase = context.getRealPath("/");
                if( docBase == null ) {
                    docBase = options.getScratchDir().toString();
                }
                String codeBase = docBase;
                if (!codeBase.endsWith(File.separator)){
                    codeBase = codeBase + File.separator;
                }
                File contextDir = new File(codeBase);
                URL url = contextDir.getCanonicalFile().toURI().toURL();
                source = new CodeSource(url,(Certificate[])null);
                permissions = policy.getPermissions(source);

                // Create a file read permission for web app context directory
                if (!docBase.endsWith(File.separator)){
                    permissions.add
                        (new FilePermission(docBase,"read"));
                    docBase = docBase + File.separator;
                } else {
                    permissions.add
                        (new FilePermission
                            (docBase.substring(0,docBase.length() - 1),"read"));
                }
                docBase = docBase + "-";
                permissions.add(new FilePermission(docBase,"read"));

                // Spec says apps should have read/write for their temp
                // directory. This is fine, as no security sensitive files, at
                // least any that the app doesn't have full control of anyway,
                // will be written here.
                String workDir = options.getScratchDir().toString();
                if (!workDir.endsWith(File.separator)){
                    permissions.add
                        (new FilePermission(workDir,"read,write"));
                    workDir = workDir + File.separator;
                }
                workDir = workDir + "-";
                permissions.add(new FilePermission(
                        workDir,"read,write,delete"));

                // Allow the JSP to access org.apache.jasper.runtime.HttpJspBase
                permissions.add( new RuntimePermission(
                    "accessClassInPackage.org.apache.jasper.runtime") );

                if (parentClassLoader instanceof URLClassLoader) {
                    URL [] urls = ((URLClassLoader)parentClassLoader).getURLs();
                    String jarUrl = null;
                    String jndiUrl = null;
                    for (int i=0; i<urls.length; i++) {
                        if (jndiUrl == null
                                && urls[i].toString().startsWith("jndi:") ) {
                            jndiUrl = urls[i].toString() + "-";
                        }
                        if (jarUrl == null
                                && urls[i].toString().startsWith("jar:jndi:")
                                ) {
                            jarUrl = urls[i].toString();
                            jarUrl = jarUrl.substring(0,jarUrl.length() - 2);
                            jarUrl = jarUrl.substring(0,
                                     jarUrl.lastIndexOf('/')) + "/-";
                        }
                    }
                    if (jarUrl != null) {
                        permissions.add(
                                new FilePermission(jarUrl,"read"));
                        permissions.add(
                                new FilePermission(jarUrl.substring(4),"read"));
                    }
                    if (jndiUrl != null)
                        permissions.add(
                                new FilePermission(jndiUrl,"read") );
                }
            } catch(Exception e) {
                context.log("Security Init for context failed",e);
            }
        }
        return new SecurityHolder(source, permissions);
    }

    private void unloadJspServletWrapper(JspServletWrapper jsw) {
        removeWrapper(jsw.getJspUri());
        synchronized(jsw) {
            jsw.destroy();
        }
        jspUnloadCount.incrementAndGet();
    }


    /**
     * Method used by background thread to check if any JSP's should be unloaded.
     */
    public void checkUnload() {

        if (log.isTraceEnabled()) {
            int queueLength = -1;
            if (jspQueue != null) {
                queueLength = jspQueue.getSize();
            }
            log.trace(Localizer.getMessage("jsp.message.jsp_unload_check",
                                           context.getContextPath(), "" + jsps.size(), "" + queueLength));
        }
        long now = System.currentTimeMillis();
        if (jspIdleTimeout > 0) {
            long unloadBefore = now - jspIdleTimeout;
            Object [] wrappers = jsps.values().toArray();
            for (int i = 0; i < wrappers.length; i++ ) {
                JspServletWrapper jsw = (JspServletWrapper)wrappers[i];
                synchronized(jsw) {
                    if (jsw.getLastUsageTime() < unloadBefore) {
                        if (log.isDebugEnabled()) {
                            log.debug(Localizer.getMessage("jsp.message.jsp_removed_idle",
                                                           jsw.getJspUri(), context.getContextPath(),
                                                           "" + (now-jsw.getLastUsageTime())));
                        }
                        if (jspQueue != null) {
                            jspQueue.remove(jsw.getUnloadHandle());
                        }
                        unloadJspServletWrapper(jsw);
                    }
                }
            }
        }
        lastJspQueueUpdate = now;
    }
}
