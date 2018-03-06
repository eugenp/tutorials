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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.descriptor.TaglibDescriptor;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.ApplicationListener;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.JarScannerCallback;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.descriptor.DigesterFactory;
import org.apache.tomcat.util.descriptor.XmlErrorHandler;
import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.scan.Jar;
import org.apache.tomcat.util.scan.JarFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Startup event listener for a <b>Context</b> that configures application
 * listeners configured in any TLD files.
 *
 * @author Craig R. McClanahan
 * @author Jean-Francois Arcand
 * @author Costin Manolache
 */
public final class TldConfig  implements LifecycleListener {

    private static final String TLD_EXT = ".tld";
    private static final String WEB_INF = "/WEB-INF/";
    private static final String WEB_INF_LIB = "/WEB-INF/lib/";
    
    // Names of JARs that are known not to contain any TLDs
    private static volatile Set<String> noTldJars = null;

    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( TldConfig.class );

    /**
     * The string resources for this package.
     */
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);

    /**
     * The <code>Digester</code>s available to process tld files.
     */
    private static Digester[] tldDigesters = new Digester[4];

    /**
     * Create (if necessary) and return a Digester configured to process the
     * tld.
     */
    private static synchronized Digester createTldDigester(boolean validation,
            boolean blockExternal) {

        Digester digester;
        int cacheIndex = 0;
        if (validation) {
            cacheIndex += 1;
        }
        if (blockExternal) {
            cacheIndex += 2;
        }
        digester = tldDigesters[cacheIndex];
        if (digester == null) {
            digester = DigesterFactory.newDigester(validation,
                    true, new TldRuleSet(), blockExternal);
            digester.getParser();
            tldDigesters[cacheIndex] = digester;
        }
        return digester;
    }


    static {
        // Set the default list of JARs to skip for TLDs
        StringBuilder jarList = new StringBuilder(System.getProperty(
                Constants.DEFAULT_JARS_TO_SKIP, ""));

        String tldJars = System.getProperty(Constants.TLD_JARS_TO_SKIP, "");
        if (tldJars.length() > 0) {
            if (jarList.length() > 0) {
                jarList.append(',');
            }
            jarList.append(tldJars);
        }

        if (jarList.length() > 0) {
            setNoTldJars(jarList.toString());
        }
    }

    /**
     * Sets the list of JARs that are known not to contain any TLDs.
     *
     * @param jarNames List of comma-separated names of JAR files that are
     * known not to contain any TLDs.
     */
    public static synchronized void setNoTldJars(String jarNames) {
        if (jarNames == null) {
            noTldJars = null;
        } else {
            if (noTldJars == null) {
                noTldJars = new HashSet<String>();
            } else {
                noTldJars.clear();
            }
            StringTokenizer tokenizer = new StringTokenizer(jarNames, ",");
            while (tokenizer.hasMoreElements()) {
                String token = tokenizer.nextToken().trim();
                if (token.length() > 0) {
                    noTldJars.add(token);
                }
            }
        }
    }


    // ----------------------------------------------------- Instance Variables

    /**
     * The Context we are associated with.
     */
    private Context context = null;


    /**
     * The <code>Digester</code> we will use to process tag library
     * descriptor files.
     */
    private Digester tldDigester = null;


    /**
     * Set of URIs discovered for the associated context. Used to enforce the
     * correct processing priority. Only the TLD associated with the first
     * instance of any URI will be processed.
     */
    private Set<String> taglibUris = new HashSet<String>();

    private Set<String> webxmlTaglibUris = new HashSet<String>();

    private ArrayList<String> listeners = new ArrayList<String>();

    // --------------------------------------------------------- Public Methods

    /**
     * Adds a taglib URI to the list of known URIs.
     */
    public void addTaglibUri(String uri) {
        taglibUris.add(uri);
    }

    /**
     * Determines if the provided URI is a known taglib URI.
     */
    public boolean isKnownTaglibUri(String uri) {
        return taglibUris.contains(uri);
    }

    /**
     * Determines if the provided URI is a known taglib URI.
     */
    public boolean isKnownWebxmlTaglibUri(String uri) {
        return webxmlTaglibUris.contains(uri);
    }

    /**
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public Context getContext() {
        return context;
    }

    /**
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public void setContext(Context context) {
        this.context = context;
    }

    public void addApplicationListener( String s ) {
        if(log.isDebugEnabled())
            log.debug( "Add tld listener " + s);
        listeners.add(s);
    }

    public String[] getTldListeners() {
        String result[]=new String[listeners.size()];
        listeners.toArray(result);
        return result;
    }


    /**
     * Scan for and configure all tag library descriptors found in this
     * web application.
     * 
     * This supports a Tomcat-specific extension to the TLD search
     * order defined in the JSP spec. It allows tag libraries packaged as JAR
     * files to be shared by web applications by simply dropping them in a 
     * location that all web applications have access to (e.g.,
     * <CATALINA_HOME>/lib). It also supports some of the weird and
     * wonderful arrangements present when Tomcat gets embedded.
     *
     * The set of shared JARs to be scanned for TLDs is narrowed down by
     * the <tt>noTldJars</tt> class variable, which contains the names of JARs
     * that are known not to contain any TLDs.
     */
    @SuppressWarnings("deprecation") // Context.addApplicationListener(ApplicationListener) is deprecated.
    public void execute() {
        long t1=System.currentTimeMillis();

        /*
         * Priority order of URIs required by spec is:
         * 1. J2EE platform taglibs - Tomcat doesn't provide these
         * 2. web.xml entries
         * 3. JARS in WEB-INF/lib & TLDs under WEB-INF (equal priority)
         * 4. Additional entries from the container
         * 
         * Keep processing order in sync with o.a.j.compiler.TldLocationsCache
         */
        
        // Stage 2 - web.xml entries
        tldScanWebXml();
        
        // Stage 3a - TLDs under WEB-INF (not lib or classes)
        tldScanResourcePaths(WEB_INF);

        // Stages 3b & 4
        JarScanner jarScanner = context.getJarScanner();
        
        TldJarScannerCallback tldCallBack = new TldJarScannerCallback();
        jarScanner.scan(context.getServletContext(), context.getLoader().getClassLoader(),
                tldCallBack, noTldJars);
        if(tldCallBack.scanFoundNoTLDs()){
            log.info(sm.getString("tldConfig.noTldSummary"));
        }
        // Now add all the listeners we found to the listeners for this context
        String list[] = getTldListeners();

        if( log.isDebugEnabled() )
            log.debug(sm.getString("tldConfig.addListeners",
                    Integer.valueOf(list.length)));

        for( int i=0; i<list.length; i++ ) {
            context.addApplicationListener(
                    new ApplicationListener(list[i], true));
        }

        long t2=System.currentTimeMillis();
        if( context instanceof StandardContext ) {
            ((StandardContext)context).setTldScanTime(t2-t1);
        }

    }

    private class TldJarScannerCallback implements JarScannerCallback {
        boolean tldFound = true;
        
        @Override
        public void scan(JarURLConnection urlConn) throws IOException {
            tldFound = tldScanJar(urlConn);
        }

        @Override
        public void scan(File file) {
            File metaInf = new File(file, "META-INF");
            if (metaInf.isDirectory()) {
                tldFound = tldScanDir(metaInf);
            }
        }
        
        private boolean scanFoundNoTLDs() {
            return !tldFound;
        }
    }

    // -------------------------------------------------------- Private Methods


    /**
     * Get the taglib entries from web.xml and add them to the map.
     * 
     * This is not kept in sync with o.a.j.compiler.TldLocationsCache as this
     * code needs to scan the TLDs listed in web.xml whereas Jasper only needs
     * the URI to TLD mappings.
     */
    private void tldScanWebXml() {
        
        if (log.isTraceEnabled()) {
            log.trace(sm.getString("tldConfig.webxmlStart"));
        }

        Collection<TaglibDescriptor> descriptors =
            context.getJspConfigDescriptor().getTaglibs();

        for (TaglibDescriptor descriptor : descriptors) {
            String resourcePath = descriptor.getTaglibLocation();
            // Note: Whilst the Servlet 2.4 DTD implies that the location must
            // be a context-relative path starting with '/', JSP.7.3.6.1 states
            // explicitly how paths that do not start with '/' should be
            // handled.
            if (!resourcePath.startsWith("/")) {
                resourcePath = WEB_INF + resourcePath;
            }
            if (taglibUris.contains(descriptor.getTaglibURI())) {
                log.warn(sm.getString("tldConfig.webxmlSkip", resourcePath,
                        descriptor.getTaglibURI()));
            } else {
                if (log.isTraceEnabled()) {
                    log.trace(sm.getString("tldConfig.webxmlAdd", resourcePath,
                            descriptor.getTaglibURI()));
                }
                InputStream stream = null;
                try {
                    stream = context.getServletContext().getResourceAsStream(
                            resourcePath);
                    if (stream != null) {
                        XmlErrorHandler handler = tldScanStream(stream);
                        handler.logFindings(log, resourcePath);
                        taglibUris.add(descriptor.getTaglibURI());
                        webxmlTaglibUris.add(descriptor.getTaglibURI());
                    } else {
                        log.warn(sm.getString("tldConfig.webxmlFailPathDoesNotExist", resourcePath,
                                descriptor.getTaglibURI()));
                    }
                } catch (IOException ioe) {
                    log.warn(sm.getString("tldConfig.webxmlFail", resourcePath,
                            descriptor.getTaglibURI()), ioe);
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (Throwable t) {
                            ExceptionUtils.handleThrowable(t);
                        }
                    }
                }
            }
        }
    }
    
    /*
     * Scans the web application's sub-directory identified by startPath,
     * along with its sub-directories, for TLDs.
     *
     * Initially, rootPath equals /WEB-INF/. The /WEB-INF/classes and
     * /WEB-INF/lib sub-directories are excluded from the search, as per the
     * JSP 2.0 spec.
     * 
     * Keep in sync with o.a.j.comiler.TldLocationsCache
     */
    private void tldScanResourcePaths(String startPath) {

        if (log.isTraceEnabled()) {
            log.trace(sm.getString("tldConfig.webinfScan", startPath));
        }

        ServletContext ctxt = context.getServletContext();

        Set<String> dirList = ctxt.getResourcePaths(startPath);
        if (dirList != null) {
            Iterator<String> it = dirList.iterator();
            while (it.hasNext()) {
                String path = it.next();
                if (!path.endsWith(TLD_EXT)
                        && (path.startsWith(WEB_INF_LIB)
                                || path.startsWith("/WEB-INF/classes/"))) {
                    continue;
                }
                if (path.endsWith(TLD_EXT)) {
                    if (path.startsWith("/WEB-INF/tags/") &&
                            !path.endsWith("implicit.tld")) {
                        continue;
                    }
                    InputStream stream = ctxt.getResourceAsStream(path);
                    try {
                        XmlErrorHandler handler = tldScanStream(stream);
                        handler.logFindings(log, path);
                    } catch (IOException ioe) {
                        log.warn(sm.getString("tldConfig.webinfFail", path),
                                ioe);
                    } finally {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable t) {
                                ExceptionUtils.handleThrowable(t);
                            }
                        }
                    }
                } else {
                    tldScanResourcePaths(path);
                }
            }
        }
    }
    
    /*
     * Scans the directory identified by startPath, along with its
     * sub-directories, for TLDs.
     *
     * Keep in sync with o.a.j.comiler.TldLocationsCache
     */
    private boolean tldScanDir(File start) {
        boolean isFound = false;
        
        if (log.isTraceEnabled()) {
            log.trace(sm.getString("tldConfig.dirScan", start.getAbsolutePath()));
        }

        File[] fileList = start.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                // Scan recursively
                if (fileList[i].isDirectory()) {
                    tldScanDir(fileList[i]);
                } else if (fileList[i].getAbsolutePath().endsWith(TLD_EXT)) {
                    InputStream stream = null;
                    isFound = true;
                    try {
                        stream = new FileInputStream(fileList[i]);
                        XmlErrorHandler handler = tldScanStream(stream);
                        handler.logFindings(log, fileList[i].getAbsolutePath());
                    } catch (IOException ioe) {
                        log.warn(sm.getString("tldConfig.dirFail",
                                fileList[i].getAbsolutePath()),
                                ioe);
                    } finally {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable t) {
                                ExceptionUtils.handleThrowable(t);
                            }
                        }
                    }
                }
            }
        }
        if(!isFound){
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("tldConfig.noTldInDir", start.getAbsolutePath()));
            }
        }
        return isFound;
    }

    /*
     * Scans the given JarURLConnection for TLD files located in META-INF
     * (or a sub-directory of it).
     *
     * @param jarConn The JarURLConnection to the JAR file to scan
     * 
     * Keep in sync with o.a.j.comiler.TldLocationsCache
     */
    private boolean tldScanJar(JarURLConnection jarConn) {

        Jar jar = null;
        InputStream is;
        boolean isFound = false;
        
        try {
            jar = JarFactory.newInstance(jarConn.getURL());
            
            jar.nextEntry();
            String entryName = jar.getEntryName();
            while (entryName != null) {
                if (entryName.startsWith("META-INF/") &&
                        entryName.endsWith(".tld")) {
                    isFound = true;
                    is = null;
                    try {
                        is = jar.getEntryInputStream();
                        XmlErrorHandler handler = tldScanStream(is);
                        handler.logFindings(log, jarConn.getURL() + entryName);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException ioe) {
                                // Ignore
                            }
                        }
                    }
                }
                jar.nextEntry();
                entryName = jar.getEntryName();
            }
            if(!isFound){
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("tldConfig.noTldInJar",
                            jarConn.getURL().getFile()));
                }
            }
        } catch (IOException ioe) {
            log.warn(sm.getString("tldConfig.jarFail", jarConn.getURL()), ioe);
        } finally {
            if (jar != null) {
                jar.close();
            }
        }
        return isFound;
    }


    /*
     * Scan the TLD contents in the specified input stream, and register
     * any application event listeners found there.  <b>NOTE</b> - This 
     * method ensure that the InputStream is correctly closed.
     *
     * @param resourceStream InputStream containing a tag library descriptor
     *
     * @throws IOException  If the file cannot be read
     */
    private XmlErrorHandler tldScanStream(InputStream resourceStream)
            throws IOException {
        
        InputSource source = new InputSource(resourceStream);
        
        XmlErrorHandler result = new XmlErrorHandler();
        
        synchronized (tldDigester) {
            try {
                tldDigester.setErrorHandler(result);
                tldDigester.push(this);
                tldDigester.parse(source);
            } catch (SAXException s) {
                // Hack - makes exception handling simpler
                throw new IOException(s);
            } finally {
                tldDigester.reset();
            }
            return result;
        }
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        // Identify the context we are associated with
        try {
            context = (Context) event.getLifecycle();
        } catch (ClassCastException e) {
            log.error(sm.getString("tldConfig.cce", event.getLifecycle()), e);
            return;
        }
        
        if (event.getType().equals(Lifecycle.AFTER_INIT_EVENT)) {
            init();
        } else if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
            try {
                execute();
            } catch (Exception e) {
                log.error(sm.getString(
                        "tldConfig.execute", context.getName()), e);
            }
        } else if (event.getType().equals(Lifecycle.STOP_EVENT)) {
            taglibUris.clear();
            webxmlTaglibUris.clear();
            listeners.clear();
        }
    }
    
    private void init() {
        if (tldDigester == null){
            tldDigester = createTldDigester(context.getTldValidation(),
                    context.getXmlBlockExternal());
        }
    }

}
