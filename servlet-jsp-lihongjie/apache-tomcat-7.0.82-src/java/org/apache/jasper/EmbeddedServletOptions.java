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
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagLibraryInfo;

import org.apache.jasper.compiler.JspConfig;
import org.apache.jasper.compiler.Localizer;
import org.apache.jasper.compiler.TagPluginManager;
import org.apache.jasper.compiler.TldLocationsCache;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * A class to hold all init parameters specific to the JSP engine. 
 *
 * @author Anil K. Vijendran
 * @author Hans Bergsten
 * @author Pierre Delisle
 */
public final class EmbeddedServletOptions implements Options {
    
    // Logger
    private final Log log = LogFactory.getLog(EmbeddedServletOptions.class);
    
    private Properties settings = new Properties();
    
    /**
     * Is Jasper being used in development mode?
     */
    private boolean development = true;
    
    /**
     * Should Ant fork its java compiles of JSP pages.
     */
    public boolean fork = true;
    
    /**
     * Do you want to keep the generated Java files around?
     */
    private boolean keepGenerated = true;
    
    /**
     * Should template text that consists entirely of whitespace be removed?
     */
    private boolean trimSpaces = false;
    
    /**
     * Determines whether tag handler pooling is enabled.
     */
    private boolean isPoolingEnabled = true;
    
    /**
     * Do you want support for "mapped" files? This will generate
     * servlet that has a print statement per line of the JSP file.
     * This seems like a really nice feature to have for debugging.
     */
    private boolean mappedFile = true;
    
    /**
     * Do we want to include debugging information in the class file?
     */
    private boolean classDebugInfo = true;
    
    /**
     * Background compile thread check interval in seconds.
     */
    private int checkInterval = 0;
    
    /**
     * Is the generation of SMAP info for JSR45 debugging suppressed?
     */
    private boolean isSmapSuppressed = false;
    
    /**
     * Should SMAP info for JSR45 debugging be dumped to a file?
     */
    private boolean isSmapDumped = false;
    
    /**
     * Are Text strings to be generated as char arrays?
     */
    private boolean genStringAsCharArray = false;
    
    private boolean errorOnUseBeanInvalidClassAttribute = true;
    
    /**
     * I want to see my generated servlets. Which directory are they
     * in?
     */
    private File scratchDir;
    
    /**
     * Need to have this as is for versions 4 and 5 of IE. Can be set from
     * the initParams so if it changes in the future all that is needed is
     * to have a jsp initParam of type ieClassId="<value>"
     */
    private String ieClassId = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93";
    
    /**
     * What classpath should I use while compiling generated servlets?
     */
    private String classpath = null;
    
    /**
     * Compiler to use.
     */
    private String compiler = null;
    
    /**
     * Compiler target VM.
     */
    private String compilerTargetVM = "1.6";
    
    /**
     * The compiler source VM.
     */
    private String compilerSourceVM = "1.6";
    
    /**
     * The compiler class name.
     */
    private String compilerClassName = null;
    
    /**
     * Cache for the TLD locations
     */
    private TldLocationsCache tldLocationsCache = null;
    
    /**
     * Jsp config information
     */
    private JspConfig jspConfig = null;
    
    /**
     * TagPluginManager
     */
    private TagPluginManager tagPluginManager = null;
    
    /**
     * Java platform encoding to generate the JSP
     * page servlet.
     */
    private String javaEncoding = "UTF8";
    
    /**
     * Modification test interval.
     */
    private int modificationTestInterval = 4;
    
    /**
     * Is re-compilation attempted immediately after a failure?
     */
    private boolean recompileOnFail = false;
    
    /**
     * Is generation of X-Powered-By response header enabled/disabled?
     */
    private boolean xpoweredBy;
    
    /**
     * Should we include a source fragment in exception messages, which could be displayed
     * to the developer ?
     */
    private boolean displaySourceFragment = true;

    
    /**
     * The maximum number of loaded jsps per web-application. If there are more
     * jsps loaded, they will be unloaded.
     */
    private int maxLoadedJsps = -1;

    /**
     * The idle time in seconds after which a JSP is unloaded.
     * If unset or less or equal than 0, no jsps are unloaded.
     */
    private int jspIdleTimeout = -1;

    /**
     * When EL is used in JSP attribute values, should the rules for quoting of
     * attributes described in JSP.1.6 be applied to the expression?
     */
    private boolean quoteAttributeEL = true;

    public String getProperty(String name ) {
        return settings.getProperty( name );
    }
    
    public void setProperty(String name, String value ) {
        if (name != null && value != null){ 
            settings.setProperty( name, value );
        }
    }
    
    public void setQuoteAttributeEL(boolean b) {
        this.quoteAttributeEL = b;
    }

    @Override
    public boolean getQuoteAttributeEL() {
        return quoteAttributeEL;
    }

    /**
     * Are we keeping generated code around?
     */
    @Override
    public boolean getKeepGenerated() {
        return keepGenerated;
    }
    
    /**
     * Should template text that consists entirely of whitespace be removed?
     */
    @Override
    public boolean getTrimSpaces() {
        return trimSpaces;
    }
    
    @Override
    public boolean isPoolingEnabled() {
        return isPoolingEnabled;
    }
    
    /**
     * Are we supporting HTML mapped servlets?
     */
    @Override
    public boolean getMappedFile() {
        return mappedFile;
    }
    
    /**
     * Should class files be compiled with debug information?
     */
    @Override
    public boolean getClassDebugInfo() {
        return classDebugInfo;
    }
    
    /**
     * Background JSP compile thread check interval
     */
    @Override
    public int getCheckInterval() {
        return checkInterval;
    }
    
    /**
     * Modification test interval.
     */
    @Override
    public int getModificationTestInterval() {
        return modificationTestInterval;
    }
    
    /**
     * Re-compile on failure.
     */
    @Override
    public boolean getRecompileOnFail() {
        return recompileOnFail;
    }
    
    /**
     * Is Jasper being used in development mode?
     */
    @Override
    public boolean getDevelopment() {
        return development;
    }
    
    /**
     * Is the generation of SMAP info for JSR45 debugging suppressed?
     */
    @Override
    public boolean isSmapSuppressed() {
        return isSmapSuppressed;
    }
    
    /**
     * Should SMAP info for JSR45 debugging be dumped to a file?
     */
    @Override
    public boolean isSmapDumped() {
        return isSmapDumped;
    }
    
    /**
     * Are Text strings to be generated as char arrays?
     */
    @Override
    public boolean genStringAsCharArray() {
        return this.genStringAsCharArray;
    }
    
    /**
     * Class ID for use in the plugin tag when the browser is IE. 
     */
    @Override
    public String getIeClassId() {
        return ieClassId;
    }
    
    /**
     * What is my scratch dir?
     */
    @Override
    public File getScratchDir() {
        return scratchDir;
    }
    
    /**
     * What classpath should I use while compiling the servlets
     * generated from JSP files?
     */
    @Override
    public String getClassPath() {
        return classpath;
    }
    
    /**
     * Is generation of X-Powered-By response header enabled/disabled?
     */
    @Override
    public boolean isXpoweredBy() {
        return xpoweredBy;
    }
    
    /**
     * Compiler to use.
     */
    @Override
    public String getCompiler() {
        return compiler;
    }
    
    /**
     * @see Options#getCompilerTargetVM
     */
    @Override
    public String getCompilerTargetVM() {
        return compilerTargetVM;
    }
    
    /**
     * @see Options#getCompilerSourceVM
     */
    @Override
    public String getCompilerSourceVM() {
        return compilerSourceVM;
    }
    
    /**
     * Java compiler class to use.
     */
    @Override
    public String getCompilerClassName() {
        return compilerClassName;
    }

    @Override
    public boolean getErrorOnUseBeanInvalidClassAttribute() {
        return errorOnUseBeanInvalidClassAttribute;
    }
    
    public void setErrorOnUseBeanInvalidClassAttribute(boolean b) {
        errorOnUseBeanInvalidClassAttribute = b;
    }
    
    @Override
    public TldLocationsCache getTldLocationsCache() {
        return tldLocationsCache;
    }
    
    public void setTldLocationsCache( TldLocationsCache tldC ) {
        tldLocationsCache = tldC;
    }
    
    @Override
    public String getJavaEncoding() {
        return javaEncoding;
    }
    
    @Override
    public boolean getFork() {
        return fork;
    }
    
    @Override
    public JspConfig getJspConfig() {
        return jspConfig;
    }
    
    @Override
    public TagPluginManager getTagPluginManager() {
        return tagPluginManager;
    }
    
    @Override
    public boolean isCaching() {
        return false;
    }
    
    @Override
    public Map<String, TagLibraryInfo> getCache() {
        return null;
    }

    /**
     * Should we include a source fragment in exception messages, which could be displayed
     * to the developer ?
     */
    @Override
    public boolean getDisplaySourceFragment() {
        return displaySourceFragment;
    }

    /**
     * Should jsps be unloaded if to many are loaded?
     * If set to a value greater than 0 eviction of jsps is started. Default: -1
     */
    @Override
    public int getMaxLoadedJsps() {
        return maxLoadedJsps;
    }

    /**
     * Should any jsps be unloaded when being idle for this time in seconds?
     * If set to a value greater than 0 eviction of jsps is started. Default: -1
     */
    @Override
    public int getJspIdleTimeout() {
        return jspIdleTimeout;
    }

    /**
     * Create an EmbeddedServletOptions object using data available from
     * ServletConfig and ServletContext. 
     */
    public EmbeddedServletOptions(ServletConfig config,
            ServletContext context) {
        
        Enumeration<String> enumeration=config.getInitParameterNames();
        while( enumeration.hasMoreElements() ) {
            String k=enumeration.nextElement();
            String v=config.getInitParameter( k );
            setProperty( k, v);
        }
                
        String keepgen = config.getInitParameter("keepgenerated");
        if (keepgen != null) {
            if (keepgen.equalsIgnoreCase("true")) {
                this.keepGenerated = true;
            } else if (keepgen.equalsIgnoreCase("false")) {
                this.keepGenerated = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.keepgen"));
                }
            }
        }
        
        
        String trimsp = config.getInitParameter("trimSpaces"); 
        if (trimsp != null) {
            if (trimsp.equalsIgnoreCase("true")) {
                trimSpaces = true;
            } else if (trimsp.equalsIgnoreCase("false")) {
                trimSpaces = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.trimspaces"));
                }
            }
        }
        
        this.isPoolingEnabled = true;
        String poolingEnabledParam
        = config.getInitParameter("enablePooling"); 
        if (poolingEnabledParam != null
                && !poolingEnabledParam.equalsIgnoreCase("true")) {
            if (poolingEnabledParam.equalsIgnoreCase("false")) {
                this.isPoolingEnabled = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.enablePooling"));
                }
            }
        }
        
        String mapFile = config.getInitParameter("mappedfile"); 
        if (mapFile != null) {
            if (mapFile.equalsIgnoreCase("true")) {
                this.mappedFile = true;
            } else if (mapFile.equalsIgnoreCase("false")) {
                this.mappedFile = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.mappedFile"));
                }
            }
        }
        
        String debugInfo = config.getInitParameter("classdebuginfo");
        if (debugInfo != null) {
            if (debugInfo.equalsIgnoreCase("true")) {
                this.classDebugInfo  = true;
            } else if (debugInfo.equalsIgnoreCase("false")) {
                this.classDebugInfo  = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.classDebugInfo"));
                }
            }
        }
        
        String checkInterval = config.getInitParameter("checkInterval");
        if (checkInterval != null) {
            try {
                this.checkInterval = Integer.parseInt(checkInterval);
            } catch(NumberFormatException ex) {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.checkInterval"));
                }
            }
        }
        
        String modificationTestInterval = config.getInitParameter("modificationTestInterval");
        if (modificationTestInterval != null) {
            try {
                this.modificationTestInterval = Integer.parseInt(modificationTestInterval);
            } catch(NumberFormatException ex) {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.modificationTestInterval"));
                }
            }
        }
        
        String recompileOnFail = config.getInitParameter("recompileOnFail"); 
        if (recompileOnFail != null) {
            if (recompileOnFail.equalsIgnoreCase("true")) {
                this.recompileOnFail = true;
            } else if (recompileOnFail.equalsIgnoreCase("false")) {
                this.recompileOnFail = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.recompileOnFail"));
                }
            }
        }
        String development = config.getInitParameter("development");
        if (development != null) {
            if (development.equalsIgnoreCase("true")) {
                this.development = true;
            } else if (development.equalsIgnoreCase("false")) {
                this.development = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.development"));
                }
            }
        }
        
        String suppressSmap = config.getInitParameter("suppressSmap");
        if (suppressSmap != null) {
            if (suppressSmap.equalsIgnoreCase("true")) {
                isSmapSuppressed = true;
            } else if (suppressSmap.equalsIgnoreCase("false")) {
                isSmapSuppressed = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.suppressSmap"));
                }
            }
        }
        
        String dumpSmap = config.getInitParameter("dumpSmap");
        if (dumpSmap != null) {
            if (dumpSmap.equalsIgnoreCase("true")) {
                isSmapDumped = true;
            } else if (dumpSmap.equalsIgnoreCase("false")) {
                isSmapDumped = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.dumpSmap"));
                }
            }
        }
        
        String genCharArray = config.getInitParameter("genStringAsCharArray");
        if (genCharArray != null) {
            if (genCharArray.equalsIgnoreCase("true")) {
                genStringAsCharArray = true;
            } else if (genCharArray.equalsIgnoreCase("false")) {
                genStringAsCharArray = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.genchararray"));
                }
            }
        }
        
        String errBeanClass =
            config.getInitParameter("errorOnUseBeanInvalidClassAttribute");
        if (errBeanClass != null) {
            if (errBeanClass.equalsIgnoreCase("true")) {
                errorOnUseBeanInvalidClassAttribute = true;
            } else if (errBeanClass.equalsIgnoreCase("false")) {
                errorOnUseBeanInvalidClassAttribute = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.errBean"));
                }
            }
        }
        
        String ieClassId = config.getInitParameter("ieClassId");
        if (ieClassId != null)
            this.ieClassId = ieClassId;
        
        String classpath = config.getInitParameter("classpath");
        if (classpath != null)
            this.classpath = classpath;
        
        /*
         * scratchdir
         */
        String dir = config.getInitParameter("scratchdir"); 
        if (dir != null && Constants.IS_SECURITY_ENABLED) {
            log.info(Localizer.getMessage("jsp.info.ignoreSetting", "scratchdir", dir));
            dir = null;
        }
        if (dir != null) {
            scratchDir = new File(dir);
        } else {
            // First try the Servlet 2.2 javax.servlet.context.tempdir property
            scratchDir = (File) context.getAttribute(ServletContext.TEMPDIR);
            if (scratchDir == null) {
                // Not running in a Servlet 2.2 container.
                // Try to get the JDK 1.2 java.io.tmpdir property
                dir = System.getProperty("java.io.tmpdir");
                if (dir != null)
                    scratchDir = new File(dir);
            }
        }      
        if (this.scratchDir == null) {
            log.fatal(Localizer.getMessage("jsp.error.no.scratch.dir"));
            return;
        }
        
        if (!(scratchDir.exists() && scratchDir.canRead() &&
                scratchDir.canWrite() && scratchDir.isDirectory()))
            log.fatal(Localizer.getMessage("jsp.error.bad.scratch.dir",
                    scratchDir.getAbsolutePath()));
        
        this.compiler = config.getInitParameter("compiler");
        
        String compilerTargetVM = config.getInitParameter("compilerTargetVM");
        if(compilerTargetVM != null) {
            this.compilerTargetVM = compilerTargetVM;
        }
        
        String compilerSourceVM = config.getInitParameter("compilerSourceVM");
        if(compilerSourceVM != null) {
            this.compilerSourceVM = compilerSourceVM;
        }
        
        String javaEncoding = config.getInitParameter("javaEncoding");
        if (javaEncoding != null) {
            this.javaEncoding = javaEncoding;
        }
        
        String compilerClassName = config.getInitParameter("compilerClassName");
        if (compilerClassName != null) {
            this.compilerClassName = compilerClassName;
        }
        
        String fork = config.getInitParameter("fork");
        if (fork != null) {
            if (fork.equalsIgnoreCase("true")) {
                this.fork = true;
            } else if (fork.equalsIgnoreCase("false")) {
                this.fork = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.fork"));
                }
            }
        }
        
        String xpoweredBy = config.getInitParameter("xpoweredBy"); 
        if (xpoweredBy != null) {
            if (xpoweredBy.equalsIgnoreCase("true")) {
                this.xpoweredBy = true;
            } else if (xpoweredBy.equalsIgnoreCase("false")) {
                this.xpoweredBy = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.xpoweredBy"));
                }
            }
        }
        
        String displaySourceFragment = config.getInitParameter("displaySourceFragment"); 
        if (displaySourceFragment != null) {
            if (displaySourceFragment.equalsIgnoreCase("true")) {
                this.displaySourceFragment = true;
            } else if (displaySourceFragment.equalsIgnoreCase("false")) {
                this.displaySourceFragment = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.displaySourceFragment"));
                }
            }
        }
        
        String maxLoadedJsps = config.getInitParameter("maxLoadedJsps");
        if (maxLoadedJsps != null) {
            try {
                this.maxLoadedJsps = Integer.parseInt(maxLoadedJsps);
            } catch(NumberFormatException ex) {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.maxLoadedJsps", ""+this.maxLoadedJsps));
                }
            }
        }
        
        String jspIdleTimeout = config.getInitParameter("jspIdleTimeout");
        if (jspIdleTimeout != null) {
            try {
                this.jspIdleTimeout = Integer.parseInt(jspIdleTimeout);
            } catch(NumberFormatException ex) {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.jspIdleTimeout", ""+this.jspIdleTimeout));
                }
            }
        }

        String quoteAttributeEL = config.getInitParameter("quoteAttributeEL");
        if (quoteAttributeEL != null) {
            if (quoteAttributeEL.equalsIgnoreCase("true")) {
                this.quoteAttributeEL = true;
            } else if (quoteAttributeEL.equalsIgnoreCase("false")) {
                this.quoteAttributeEL = false;
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(Localizer.getMessage("jsp.warning.quoteAttributeEL"));
                }
            }
        }

        // Setup the global Tag Libraries location cache for this
        // web-application.
        tldLocationsCache = TldLocationsCache.getInstance(context);
        
        // Setup the jsp config info for this web app.
        jspConfig = new JspConfig(context);
        
        // Create a Tag plugin instance
        tagPluginManager = new TagPluginManager(context);
    }
    
}

