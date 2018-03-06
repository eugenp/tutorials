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

package org.apache.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagInfo;

import org.apache.jasper.compiler.Compiler;
import org.apache.jasper.compiler.JarResource;
import org.apache.jasper.compiler.JspRuntimeContext;
import org.apache.jasper.compiler.JspUtil;
import org.apache.jasper.compiler.Localizer;
import org.apache.jasper.compiler.ServletWriter;
import org.apache.jasper.compiler.TldLocation;
import org.apache.jasper.servlet.JasperLoader;
import org.apache.jasper.servlet.JspServletWrapper;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * A place holder for various things that are used through out the JSP
 * engine. This is a per-request/per-context data structure. Some of
 * the instance variables are set at different points.
 *
 * Most of the path-related stuff is here - mangling names, versions, dirs,
 * loading resources and dealing with uris.
 *
 * @author Anil K. Vijendran
 * @author Harish Prabandham
 * @author Pierre Delisle
 * @author Costin Manolache
 * @author Kin-man Chung
 */
public class JspCompilationContext {

    private final Log log = LogFactory.getLog(JspCompilationContext.class); // must not be static

    protected Map<String, JarResource> tagFileJarUrls;

    protected String className;
    protected String jspUri;
    protected String basePackageName;
    protected String derivedPackageName;
    protected String servletJavaFileName;
    protected String javaPath;
    protected String classFileName;
    protected ServletWriter writer;
    protected Options options;
    protected JspServletWrapper jsw;
    protected Compiler jspCompiler;
    protected String classPath;

    protected String baseURI;
    protected String outputDir;
    protected ServletContext context;
    protected ClassLoader loader;

    protected JspRuntimeContext rctxt;

    protected volatile boolean removed = false;

    protected URLClassLoader jspLoader;
    protected URL baseUrl;
    protected Class<?> servletClass;

    protected boolean isTagFile;
    protected boolean protoTypeMode;
    protected TagInfo tagInfo;
    protected JarResource tagJarResource;

    // jspURI _must_ be relative to the context
    public JspCompilationContext(String jspUri,
                                 Options options,
                                 ServletContext context,
                                 JspServletWrapper jsw,
                                 JspRuntimeContext rctxt) {

        this.jspUri = canonicalURI(jspUri);
        this.options = options;
        this.jsw = jsw;
        this.context = context;

        this.baseURI = jspUri.substring(0, jspUri.lastIndexOf('/') + 1);
        // hack fix for resolveRelativeURI
        if (baseURI.isEmpty()) {
            baseURI = "/";
        } else if (baseURI.charAt(0) != '/') {
            // strip the base slash since it will be combined with the
            // uriBase to generate a file
            baseURI = "/" + baseURI;
        }
        if (baseURI.charAt(baseURI.length() - 1) != '/') {
            baseURI += '/';
        }

        this.rctxt = rctxt;
        this.tagFileJarUrls = new HashMap<String, JarResource>();
        this.basePackageName = Constants.JSP_PACKAGE_NAME;
    }

    public JspCompilationContext(String tagfile,
                                 TagInfo tagInfo,
                                 Options options,
                                 ServletContext context,
                                 JspServletWrapper jsw,
                                 JspRuntimeContext rctxt,
                                 JarResource tagJarResource) {
        this(tagfile, options, context, jsw, rctxt);
        this.isTagFile = true;
        this.tagInfo = tagInfo;
        this.tagJarResource = tagJarResource;
    }

    /* ==================== Methods to override ==================== */

    /** ---------- Class path and loader ---------- */

    /**
     * The classpath that is passed off to the Java compiler.
     */
    public String getClassPath() {
        if( classPath != null ) {
            return classPath;
        }
        return rctxt.getClassPath();
    }

    /**
     * The classpath that is passed off to the Java compiler.
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * What class loader to use for loading classes while compiling
     * this JSP?
     */
    public ClassLoader getClassLoader() {
        if( loader != null ) {
            return loader;
        }
        return rctxt.getParentClassLoader();
    }

    public void setClassLoader(ClassLoader loader) {
        this.loader = loader;
    }

    public ClassLoader getJspLoader() {
        if( jspLoader == null ) {
            jspLoader = new JasperLoader
            (new URL[] {baseUrl},
                    getClassLoader(),
                    rctxt.getPermissionCollection());
        }
        return jspLoader;
    }

    public void clearJspLoader() {
        jspLoader = null;
    }


    /** ---------- Input/Output  ---------- */

    /**
     * The output directory to generate code into.  The output directory
     * is make up of the scratch directory, which is provide in Options,
     * plus the directory derived from the package name.
     */
    public String getOutputDir() {
        if (outputDir == null) {
            createOutputDir();
        }

        return outputDir;
    }

    /**
     * Create a "Compiler" object based on some init param data. This
     * is not done yet. Right now we're just hardcoding the actual
     * compilers that are created.
     */
    public Compiler createCompiler() {
        if (jspCompiler != null ) {
            return jspCompiler;
        }
        jspCompiler = null;
        if (options.getCompilerClassName() != null) {
            jspCompiler = createCompiler(options.getCompilerClassName());
        } else {
            if (options.getCompiler() == null) {
                jspCompiler = createCompiler("org.apache.jasper.compiler.JDTCompiler");
                if (jspCompiler == null) {
                    jspCompiler = createCompiler("org.apache.jasper.compiler.AntCompiler");
                }
            } else {
                jspCompiler = createCompiler("org.apache.jasper.compiler.AntCompiler");
                if (jspCompiler == null) {
                    jspCompiler = createCompiler("org.apache.jasper.compiler.JDTCompiler");
                }
            }
        }
        if (jspCompiler == null) {
            throw new IllegalStateException(Localizer.getMessage("jsp.error.compiler.config",
                    options.getCompilerClassName(), options.getCompiler()));
        }
        jspCompiler.init(this, jsw);
        return jspCompiler;
    }

    protected Compiler createCompiler(String className) {
        Compiler compiler = null;
        try {
            compiler = (Compiler) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            log.warn(Localizer.getMessage("jsp.error.compiler"), e);
        } catch (IllegalAccessException e) {
            log.warn(Localizer.getMessage("jsp.error.compiler"), e);
        } catch (NoClassDefFoundError e) {
            if (log.isDebugEnabled()) {
                log.debug(Localizer.getMessage("jsp.error.compiler"), e);
            }
        } catch (ClassNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug(Localizer.getMessage("jsp.error.compiler"), e);
            }
        }
        return compiler;
    }

    public Compiler getCompiler() {
        return jspCompiler;
    }

    /** ---------- Access resources in the webapp ---------- */

    /**
     * Get the full value of a URI relative to this compilations context
     * uses current file as the base.
     */
    public String resolveRelativeUri(String uri) {
        // sometimes we get uri's massaged from File(String), so check for
        // a root directory separator char
        if (uri.startsWith("/") || uri.startsWith(File.separator)) {
            return uri;
        } else {
            return baseURI + uri;
        }
    }

    /**
     * Gets a resource as a stream, relative to the meanings of this
     * context's implementation.
     * @return a null if the resource cannot be found or represented
     *         as an InputStream.
     */
    public java.io.InputStream getResourceAsStream(String res) {
        return context.getResourceAsStream(canonicalURI(res));
    }


    public URL getResource(String res) throws MalformedURLException {
        URL result = null;

        if (res.startsWith("/META-INF/")) {
            // This is a tag file packaged in a jar that is being compiled
            JarResource jarResource = tagFileJarUrls.get(res);
            if (jarResource == null) {
                jarResource = tagJarResource;
            }
            if (jarResource != null) {
                result = jarResource.getEntry(res.substring(1));
            } else {
                // May not be in a JAR in some IDE environments
                result = context.getResource(canonicalURI(res));
            }
        } else if (res.startsWith("jar:jndi:")) {
                // This is a tag file packaged in a jar that is being checked
                // for a dependency
                result = new URL(res);

        } else {
            result = context.getResource(canonicalURI(res));
        }
        return result;
    }


    public Set<String> getResourcePaths(String path) {
        return context.getResourcePaths(canonicalURI(path));
    }

    /**
     * Gets the actual path of a URI relative to the context of
     * the compilation.
     */
    public String getRealPath(String path) {
        if (context != null) {
            return context.getRealPath(path);
        }
        return path;
    }

    /**
     * Returns the tag-file-name-to-JAR-file map of this compilation unit,
     * which maps tag file names to the JAR files in which the tag files are
     * packaged.
     *
     * The map is populated when parsing the tag-file elements of the TLDs
     * of any imported taglibs.
     */
    public JarResource getTagFileJarResource(String tagFile) {
        return this.tagFileJarUrls.get(tagFile);
    }

    public void setTagFileJarResource(String tagFile, JarResource jarResource) {
        this.tagFileJarUrls.put(tagFile, jarResource);
    }

    /**
     * Returns the JAR file in which the tag file for which this
     * JspCompilationContext was created is packaged, or null if this
     * JspCompilationContext does not correspond to a tag file, or if the
     * corresponding tag file is not packaged in a JAR.
     */
    public JarResource getTagFileJarResource() {
        return this.tagJarResource;
    }

    /* ==================== Common implementation ==================== */

    /**
     * Just the class name (does not include package name) of the
     * generated class.
     */
    public String getServletClassName() {

        if (className != null) {
            return className;
        }

        if (isTagFile) {
            className = tagInfo.getTagClassName();
            int lastIndex = className.lastIndexOf('.');
            if (lastIndex != -1) {
                className = className.substring(lastIndex + 1);
            }
        } else {
            int iSep = jspUri.lastIndexOf('/') + 1;
            className = JspUtil.makeJavaIdentifier(jspUri.substring(iSep));
        }
        return className;
    }

    public void setServletClassName(String className) {
        this.className = className;
    }

    /**
     * Path of the JSP URI. Note that this is not a file name. This is
     * the context rooted URI of the JSP file.
     */
    public String getJspFile() {
        return jspUri;
    }

    /**
     * @deprecated Will be removed in Tomcat 8.0.x. Use
     * {@link #getLastModified(String)} instead.
     */
    @Deprecated
    public long getJspLastModified() {
        long result = -1;
        URLConnection uc = null;
        try {
            URL jspUrl = getResource(getJspFile());
            if (jspUrl == null) {
                incrementRemoved();
                return result;
            }
            uc = jspUrl.openConnection();
            if (uc instanceof JarURLConnection) {
                result = ((JarURLConnection) uc).getJarEntry().getTime();
            } else {
                result = uc.getLastModified();
            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(Localizer.getMessage(
                        "jsp.error.lastModified", getJspFile()), e);
            }
            result = -1;
        } finally {
            if (uc != null) {
                try {
                    uc.getInputStream().close();
                } catch (IOException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(Localizer.getMessage(
                                "jsp.error.lastModified", getJspFile()), e);
                    }
                    result = -1;
                }
            }
        }
        return result;
    }


    public Long getLastModified(String resource) {
        long result = -1;
        if (resource.startsWith("file:/")) {
            File f;
            try {
                f = new File(new URI(resource));
            } catch (URISyntaxException e) {
                return Long.valueOf(-1);
            }
            return Long.valueOf(f.lastModified());
        }
        URLConnection uc = null;
        try {
            URL jspUrl = getResource(resource);
            if (jspUrl == null) {
                incrementRemoved();
                return Long.valueOf(result);
            }
            uc = jspUrl.openConnection();
            if (uc instanceof JarURLConnection) {
                result = ((JarURLConnection) uc).getJarEntry().getTime();
            } else {
                result = uc.getLastModified();
            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(Localizer.getMessage(
                        "jsp.error.lastModified", getJspFile()), e);
            }
            result = -1;
        } finally {
            if (uc != null) {
                try {
                    uc.getInputStream().close();
                } catch (IOException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(Localizer.getMessage(
                                "jsp.error.lastModified", getJspFile()), e);
                    }
                    result = -1;
                }
            }
        }
        return Long.valueOf(result);
    }

    public boolean isTagFile() {
        return isTagFile;
    }

    public TagInfo getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(TagInfo tagi) {
        tagInfo = tagi;
    }

    /**
     * True if we are compiling a tag file in prototype mode.
     * ie we only generate codes with class for the tag handler with empty
     * method bodies.
     */
    public boolean isPrototypeMode() {
        return protoTypeMode;
    }

    public void setPrototypeMode(boolean pm) {
        protoTypeMode = pm;
    }

    /**
     * Package name for the generated class is make up of the base package
     * name, which is user settable, and the derived package name.  The
     * derived package name directly mirrors the file hierarchy of the JSP page.
     */
    public String getServletPackageName() {
        if (isTagFile()) {
            String className = tagInfo.getTagClassName();
            int lastIndex = className.lastIndexOf('.');
            String pkgName = "";
            if (lastIndex != -1) {
                pkgName = className.substring(0, lastIndex);
            }
            return pkgName;
        } else {
            String dPackageName = getDerivedPackageName();
            if (dPackageName.length() == 0) {
                return basePackageName;
            }
            return basePackageName + '.' + getDerivedPackageName();
        }
    }

    protected String getDerivedPackageName() {
        if (derivedPackageName == null) {
            int iSep = jspUri.lastIndexOf('/');
            derivedPackageName = (iSep > 0) ?
                    JspUtil.makeJavaPackage(jspUri.substring(1,iSep)) : "";
        }
        return derivedPackageName;
    }

    /**
     * The package name into which the servlet class is generated.
     */
    public void setServletPackageName(String servletPackageName) {
        this.basePackageName = servletPackageName;
    }

    /**
     * Full path name of the Java file into which the servlet is being
     * generated.
     */
    public String getServletJavaFileName() {
        if (servletJavaFileName == null) {
            servletJavaFileName = getOutputDir() + getServletClassName() + ".java";
        }
        return servletJavaFileName;
    }

    /**
     * Get hold of the Options object for this context.
     */
    public Options getOptions() {
        return options;
    }

    public ServletContext getServletContext() {
        return context;
    }

    public JspRuntimeContext getRuntimeContext() {
        return rctxt;
    }

    /**
     * Path of the Java file relative to the work directory.
     */
    public String getJavaPath() {

        if (javaPath != null) {
            return javaPath;
        }

        if (isTagFile()) {
            String tagName = tagInfo.getTagClassName();
            javaPath = tagName.replace('.', '/') + ".java";
        } else {
            javaPath = getServletPackageName().replace('.', '/') + '/' +
                       getServletClassName() + ".java";
        }
        return javaPath;
    }

    public String getClassFileName() {
        if (classFileName == null) {
            classFileName = getOutputDir() + getServletClassName() + ".class";
        }
        return classFileName;
    }

    /**
     * Where is the servlet being generated?
     */
    public ServletWriter getWriter() {
        return writer;
    }

    public void setWriter(ServletWriter writer) {
        this.writer = writer;
    }

    /**
     * Gets the 'location' of the TLD associated with the given taglib 'uri'.
     *
     * @return An array of two Strings: The first element denotes the real
     * path to the TLD. If the path to the TLD points to a jar file, then the
     * second element denotes the name of the TLD entry in the jar file.
     * Returns null if the given uri is not associated with any tag library
     * 'exposed' in the web application.
     */
    public TldLocation getTldLocation(String uri) throws JasperException {
        TldLocation location =
            getOptions().getTldLocationsCache().getLocation(uri);
        return location;
    }

    /**
     * Are we keeping generated code around?
     */
    public boolean keepGenerated() {
        return getOptions().getKeepGenerated();
    }

    // ==================== Removal ====================

    public void incrementRemoved() {
        if (removed == false && rctxt != null) {
            rctxt.removeWrapper(jspUri);
        }
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    // ==================== Compile and reload ====================

    public void compile() throws JasperException, FileNotFoundException {
        createCompiler();
        if (jspCompiler.isOutDated()) {
            if (isRemoved()) {
                throw new FileNotFoundException(jspUri);
            }
            try {
                jspCompiler.removeGeneratedFiles();
                jspLoader = null;
                jspCompiler.compile();
                jsw.setReload(true);
                jsw.setCompilationException(null);
            } catch (JasperException ex) {
                // Cache compilation exception
                jsw.setCompilationException(ex);
                if (options.getDevelopment() && options.getRecompileOnFail()) {
                    // Force a recompilation attempt on next access
                    jsw.setLastModificationTest(-1);
                }
                throw ex;
            } catch (FileNotFoundException fnfe) {
                // Re-throw to let caller handle this - will result in a 404
                throw fnfe;
            } catch (Exception ex) {
                JasperException je = new JasperException(
                            Localizer.getMessage("jsp.error.unable.compile"),
                            ex);
                // Cache compilation exception
                jsw.setCompilationException(je);
                throw je;
            }
        }
    }

    // ==================== Manipulating the class ====================

    public Class<?> load() throws JasperException {
        try {
            getJspLoader();

            String name = getFQCN();
            servletClass = jspLoader.loadClass(name);
        } catch (ClassNotFoundException cex) {
            throw new JasperException(Localizer.getMessage("jsp.error.unable.load"),
                                      cex);
        } catch (Exception ex) {
            throw new JasperException(Localizer.getMessage("jsp.error.unable.compile"),
                                      ex);
        }
        removed = false;
        return servletClass;
    }

    public String getFQCN() {
        String name;
        if (isTagFile()) {
            name = tagInfo.getTagClassName();
        } else {
            name = getServletPackageName() + "." + getServletClassName();
        }
        return name;
    }

    // ==================== protected methods ====================

    static Object outputDirLock = new Object();

    public void checkOutputDir() {
        if (outputDir != null) {
            if (!(new File(outputDir)).exists()) {
                makeOutputDir();
            }
        } else {
            createOutputDir();
        }
    }

    protected boolean makeOutputDir() {
        synchronized(outputDirLock) {
            File outDirFile = new File(outputDir);
            return (outDirFile.mkdirs() || outDirFile.isDirectory());
        }
    }

    protected void createOutputDir() {
        String path = null;
        if (isTagFile()) {
            String tagName = tagInfo.getTagClassName();
            path = tagName.replace('.', File.separatorChar);
            path = path.substring(0, path.lastIndexOf(File.separatorChar));
        } else {
            path = getServletPackageName().replace('.',File.separatorChar);
        }

            // Append servlet or tag handler path to scratch dir
            try {
                File base = options.getScratchDir();
                baseUrl = base.toURI().toURL();
                outputDir = base.getAbsolutePath() + File.separator + path +
                    File.separator;
                if (!makeOutputDir()) {
                    throw new IllegalStateException(Localizer.getMessage("jsp.error.outputfolder"));
                }
            } catch (MalformedURLException e) {
                throw new IllegalStateException(Localizer.getMessage("jsp.error.outputfolder"), e);
            }
    }

    protected static final boolean isPathSeparator(char c) {
       return (c == '/' || c == '\\');
    }

    protected static final String canonicalURI(String s) {
       if (s == null) {
        return null;
    }
       StringBuilder result = new StringBuilder();
       final int len = s.length();
       int pos = 0;
       while (pos < len) {
           char c = s.charAt(pos);
           if ( isPathSeparator(c) ) {
               /*
                * multiple path separators.
                * 'foo///bar' -> 'foo/bar'
                */
               while (pos+1 < len && isPathSeparator(s.charAt(pos+1))) {
                   ++pos;
               }

               if (pos+1 < len && s.charAt(pos+1) == '.') {
                   /*
                    * a single dot at the end of the path - we are done.
                    */
                   if (pos+2 >= len) {
                    break;
                }

                   switch (s.charAt(pos+2)) {
                       /*
                        * self directory in path
                        * foo/./bar -> foo/bar
                        */
                   case '/':
                   case '\\':
                       pos += 2;
                       continue;

                       /*
                        * two dots in a path: go back one hierarchy.
                        * foo/bar/../baz -> foo/baz
                        */
                   case '.':
                       // only if we have exactly _two_ dots.
                       if (pos+3 < len && isPathSeparator(s.charAt(pos+3))) {
                           pos += 3;
                           int separatorPos = result.length()-1;
                           while (separatorPos >= 0 &&
                                  ! isPathSeparator(result
                                                    .charAt(separatorPos))) {
                               --separatorPos;
                           }
                           if (separatorPos >= 0) {
                            result.setLength(separatorPos);
                        }
                           continue;
                       }
                   }
               }
           }
           result.append(c);
           ++pos;
       }
       return result.toString();
    }
}

