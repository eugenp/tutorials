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


package org.apache.catalina;


import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletSecurityElement;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.apache.catalina.core.ApplicationServletRegistration;
import org.apache.catalina.deploy.ApplicationListener;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.NamingResources;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.util.CharsetMapper;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.util.http.mapper.Mapper;


/**
 * A <b>Context</b> is a Container that represents a servlet context, and
 * therefore an individual web application, in the Catalina servlet engine.
 * It is therefore useful in almost every deployment of Catalina (even if a
 * Connector attached to a web server (such as Apache) uses the web server's
 * facilities to identify the appropriate Wrapper to handle this request.
 * It also provides a convenient mechanism to use Interceptors that see
 * every request processed by this particular web application.
 * <p>
 * The parent Container attached to a Context is generally a Host, but may
 * be some other implementation, or may be omitted if it is not necessary.
 * <p>
 * The child containers attached to a Context are generally implementations
 * of Wrapper (representing individual servlet definitions).
 * <p>
 *
 * @author Craig R. McClanahan
 */

public interface Context extends Container {


    // ----------------------------------------------------- Manifest Constants


    /**
     * The LifecycleEvent type sent when a context is reloaded.
     * @deprecated Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public static final String RELOAD_EVENT = "reload";

    /**
     * Container event for adding a welcome file.
     */
    public static final String ADD_WELCOME_FILE_EVENT = "addWelcomeFile";

    /**
     * Container event for removing a wrapper.
     */
    public static final String REMOVE_WELCOME_FILE_EVENT = "removeWelcomeFile";

    /**
     * Container event for clearing welcome files.
     */
    public static final String  CLEAR_WELCOME_FILES_EVENT = "clearWelcomeFiles";

    /**
     * Container event for changing the ID of a session.
     */
    public static final String CHANGE_SESSION_ID_EVENT = "changeSessionId";

    // ------------------------------------------------------------- Properties

    /**
     * Returns <code>true</code> if requests mapped to servlets without
     * "multipart config" to parse multipart/form-data requests anyway.
     *
     * @return <code>true</code> if requests mapped to servlets without
     *    "multipart config" to parse multipart/form-data requests,
     *    <code>false</code> otherwise.
     */
    public boolean getAllowCasualMultipartParsing();


   /**
     * Set to <code>true</code> to allow requests mapped to servlets that
     * do not explicitly declare @MultipartConfig or have
     * &lt;multipart-config&gt; specified in web.xml to parse
     * multipart/form-data requests.
     *
     * @param allowCasualMultipartParsing <code>true</code> to allow such
     *        casual parsing, <code>false</code> otherwise.
     */
    public void setAllowCasualMultipartParsing(boolean allowCasualMultipartParsing);


    /**
     * Return the set of initialized application event listener objects,
     * in the order they were specified in the web application deployment
     * descriptor, for this application.
     *
     * @exception IllegalStateException if this method is called before
     *  this application has started, or after it has been stopped
     */
    public Object[] getApplicationEventListeners();


    /**
     * Store the set of initialized application event listener objects,
     * in the order they were specified in the web application deployment
     * descriptor, for this application.
     *
     * @param listeners The set of instantiated listener objects.
     */
    public void setApplicationEventListeners(Object listeners[]);


    /**
     * Return the set of initialized application lifecycle listener objects,
     * in the order they were specified in the web application deployment
     * descriptor, for this application.
     *
     * @exception IllegalStateException if this method is called before
     *  this application has started, or after it has been stopped
     */
    public Object[] getApplicationLifecycleListeners();


    /**
     * Store the set of initialized application lifecycle listener objects,
     * in the order they were specified in the web application deployment
     * descriptor, for this application.
     *
     * @param listeners The set of instantiated listener objects.
     */
    public void setApplicationLifecycleListeners(Object listeners[]);


    /**
     * Return the application available flag for this Context.
     *
     * @deprecated  This will be removed in Tomcat 8.0.x onwards. Use
     *              {@link #getState()}.{@link LifecycleState#isAvailable()
     *              isAvailable()} instead
     */
    @Deprecated
    public boolean getAvailable();


    /**
     * Return the Locale to character set mapper for this Context.
     * @deprecated Use {@link #getCharset(Locale)}
     */
    @Deprecated
    public CharsetMapper getCharsetMapper();


    /**
     * Set the Locale to character set mapper for this Context.
     *
     * @param mapper The new mapper
     *
     * @deprecated
     */
    @Deprecated
    public void setCharsetMapper(CharsetMapper mapper);


    /**
     * Obtain the character set name to use with the given Locale. Note that
     * different Contexts may have different mappings of Locale to character
     * set.
     */
    public String getCharset(Locale locale);


    /**
     * Return the URL of the XML descriptor for this context.
     */
    public URL getConfigFile();


    /**
     * Set the URL of the XML descriptor for this context.
     *
     * @param configFile The URL of the XML descriptor for this context.
     */
    public void setConfigFile(URL configFile);


    /**
     * Return the "correctly configured" flag for this Context.
     */
    public boolean getConfigured();


    /**
     * Set the "correctly configured" flag for this Context.  This can be
     * set to false by startup listeners that detect a fatal configuration
     * error to avoid the application from being made available.
     *
     * @param configured The new correctly configured flag
     */
    public void setConfigured(boolean configured);


    /**
     * Return the "use cookies for session ids" flag.
     */
    public boolean getCookies();


    /**
     * Set the "use cookies for session ids" flag.
     *
     * @param cookies The new flag
     */
    public void setCookies(boolean cookies);


    /**
     * Gets the name to use for session cookies. Overrides any setting that
     * may be specified by the application.
     *
     * @return  The value of the default session cookie name or null if not
     *          specified
     */
    public String getSessionCookieName();


    /**
     * Sets the name to use for session cookies. Overrides any setting that
     * may be specified by the application.
     *
     * @param sessionCookieName   The name to use
     */
    public void setSessionCookieName(String sessionCookieName);


    /**
     * Gets the value of the use HttpOnly cookies for session cookies flag.
     *
     * @return <code>true</code> if the HttpOnly flag should be set on session
     *         cookies
     */
    public boolean getUseHttpOnly();


    /**
     * Sets the use HttpOnly cookies for session cookies flag.
     *
     * @param useHttpOnly   Set to <code>true</code> to use HttpOnly cookies
     *                          for session cookies
     */
    public void setUseHttpOnly(boolean useHttpOnly);


    /**
     * Gets the domain to use for session cookies. Overrides any setting that
     * may be specified by the application.
     *
     * @return  The value of the default session cookie domain or null if not
     *          specified
     */
    public String getSessionCookieDomain();


    /**
     * Sets the domain to use for session cookies. Overrides any setting that
     * may be specified by the application.
     *
     * @param sessionCookieDomain   The domain to use
     */
    public void setSessionCookieDomain(String sessionCookieDomain);


    /**
     * Gets the path to use for session cookies. Overrides any setting that
     * may be specified by the application.
     *
     * @return  The value of the default session cookie path or null if not
     *          specified
     */
    public String getSessionCookiePath();


    /**
     * Sets the path to use for session cookies. Overrides any setting that
     * may be specified by the application.
     *
     * @param sessionCookiePath   The path to use
     */
    public void setSessionCookiePath(String sessionCookiePath);


    /**
     * Is a / added to the end of the session cookie path to ensure browsers,
     * particularly IE, don't send a session cookie for context /foo with
     * requests intended for context /foobar.
     *
     * @return <code>true</code> if the slash is added, otherwise
     *         <code>false</code>
     */
    public boolean getSessionCookiePathUsesTrailingSlash();


    /**
     * Configures if a / is added to the end of the session cookie path to
     * ensure browsers, particularly IE, don't send a session cookie for context
     * /foo with requests intended for context /foobar.
     *
     * @param sessionCookiePathUsesTrailingSlash   <code>true</code> if the
     *                                             slash is should be added,
     *                                             otherwise <code>false</code>
     */
    public void setSessionCookiePathUsesTrailingSlash(
            boolean sessionCookiePathUsesTrailingSlash);


    /**
     * Return the "allow crossing servlet contexts" flag.
     */
    public boolean getCrossContext();


    /**
     * Return the alternate Deployment Descriptor name.
     */
    public String getAltDDName();


    /**
     * Set an alternate Deployment Descriptor name.
     */
    public void setAltDDName(String altDDName) ;


    /**
     * Set the "allow crossing servlet contexts" flag.
     *
     * @param crossContext The new cross contexts flag
     */
    public void setCrossContext(boolean crossContext);


    /**
     * Return the display name of this web application.
     */
    public String getDisplayName();


    /**
     * Set the display name of this web application.
     *
     * @param displayName The new display name
     */
    public void setDisplayName(String displayName);


    /**
     * Return the distributable flag for this web application.
     */
    public boolean getDistributable();


    /**
     * Set the distributable flag for this web application.
     *
     * @param distributable The new distributable flag
     */
    public void setDistributable(boolean distributable);


    /**
     * Return the document root for this Context.  This can be an absolute
     * pathname, a relative pathname, or a URL.
     */
    public String getDocBase();


    /**
     * Set the document root for this Context.  This can be an absolute
     * pathname, a relative pathname, or a URL.
     *
     * @param docBase The new document root
     */
    public void setDocBase(String docBase);


    /**
     * Return the URL encoded context path, using UTF-8.
     */
    public String getEncodedPath();


    /**
     * Return the boolean on the annotations parsing.
     */
    public boolean getIgnoreAnnotations();


    /**
     * Set the boolean on the annotations parsing for this web
     * application.
     *
     * @param ignoreAnnotations The boolean on the annotations parsing
     */
    public void setIgnoreAnnotations(boolean ignoreAnnotations);


    /**
     * Return the login configuration descriptor for this web application.
     */
    public LoginConfig getLoginConfig();


    /**
     * Set the login configuration descriptor for this web application.
     *
     * @param config The new login configuration
     */
    public void setLoginConfig(LoginConfig config);


    /**
     * Get the request dispatcher mapper.
     */
    public Mapper getMapper();


    /**
     * Return the naming resources associated with this web application.
     */
    public NamingResources getNamingResources();


    /**
     * Set the naming resources for this web application.
     *
     * @param namingResources The new naming resources
     */
    public void setNamingResources(NamingResources namingResources);


    /**
     * Return the context path for this web application.
     */
    public String getPath();


    /**
     * Set the context path for this web application.
     *
     * @param path The new context path
     */
    public void setPath(String path);


    /**
     * Return the public identifier of the deployment descriptor DTD that is
     * currently being parsed.
     */
    public String getPublicId();


    /**
     * Set the public identifier of the deployment descriptor DTD that is
     * currently being parsed.
     *
     * @param publicId The public identifier
     */
    public void setPublicId(String publicId);


    /**
     * Return the reloadable flag for this web application.
     */
    public boolean getReloadable();


    /**
     * Set the reloadable flag for this web application.
     *
     * @param reloadable The new reloadable flag
     */
    public void setReloadable(boolean reloadable);


    /**
     * Return the override flag for this web application.
     */
    public boolean getOverride();


    /**
     * Set the override flag for this web application.
     *
     * @param override The new override flag
     */
    public void setOverride(boolean override);


    /**
     * Return the privileged flag for this web application.
     */
    public boolean getPrivileged();


    /**
     * Set the privileged flag for this web application.
     *
     * @param privileged The new privileged flag
     */
    public void setPrivileged(boolean privileged);


    /**
     * Return the servlet context for which this Context is a facade.
     */
    public ServletContext getServletContext();


    /**
     * Return the default session timeout (in minutes) for this
     * web application.
     */
    public int getSessionTimeout();


    /**
     * Set the default session timeout (in minutes) for this
     * web application.
     *
     * @param timeout The new default session timeout
     */
    public void setSessionTimeout(int timeout);


    /**
     * Returns <code>true</code> if remaining request data will be read
     * (swallowed) even the request violates a data size constraint.
     *
     * @return <code>true</code> if data will be swallowed (default),
     *    <code>false</code> otherwise.
     */
    public boolean getSwallowAbortedUploads();


    /**
     * Set to <code>false</code> to disable request data swallowing
     * after an upload was aborted due to size constraints.
     *
     * @param swallowAbortedUploads <code>false</code> to disable
     *        swallowing, <code>true</code> otherwise (default).
     */
    public void setSwallowAbortedUploads(boolean swallowAbortedUploads);

    /**
     * Return the value of the swallowOutput flag.
     */
    public boolean getSwallowOutput();


    /**
     * Set the value of the swallowOutput flag. If set to true, the system.out
     * and system.err will be redirected to the logger during a servlet
     * execution.
     *
     * @param swallowOutput The new value
     */
    public void setSwallowOutput(boolean swallowOutput);


    /**
     * Return the Java class name of the Wrapper implementation used
     * for servlets registered in this Context.
     */
    public String getWrapperClass();


    /**
     * Set the Java class name of the Wrapper implementation used
     * for servlets registered in this Context.
     *
     * @param wrapperClass The new wrapper class
     */
    public void setWrapperClass(String wrapperClass);


    /**
     * Will the parsing of web.xml and web-fragment.xml files for this Context
     * be performed by a namespace aware parser?
     *
     * @return true if namespace awareness is enabled.
     */
    public boolean getXmlNamespaceAware();

    
    /**
     * Controls whether the parsing of web.xml and web-fragment.xml files for
     * this Context will be performed by a namespace aware parser.
     *
     * @param xmlNamespaceAware true to enable namespace awareness
     */
    public void setXmlNamespaceAware(boolean xmlNamespaceAware);

    
    /**
     * Will the parsing of web.xml and web-fragment.xml files for this Context
     * be performed by a validating parser?
     *
     * @return true if validation is enabled.
     */
    public boolean getXmlValidation();


    /**
     * Controls whether the parsing of web.xml and web-fragment.xml files
     * for this Context will be performed by a validating parser.
     *
     * @param xmlValidation true to enable xml validation
     */
    public void setXmlValidation(boolean xmlValidation);


    /**
     * *.tld files are always parsed using a namespace aware parser.
     *
     * @return Always <code>true</code>
     * 
     * @deprecated This option will be removed in 8.0.x.
     */
    @Deprecated
    public boolean getTldNamespaceAware();


    /**
     * *.tld files are always parsed using a namespace aware parser.
     *
     * @param tldNamespaceAware ignored
     * 
     * @deprecated This option will be removed in 8.0.x.
     */
    @Deprecated
    public void setTldNamespaceAware(boolean tldNamespaceAware);


    /**
     * Will the parsing of web.xml, web-fragment.xml, *.tld, *.jspx, *.tagx and
     * tagplugin.xml files for this Context block the use of external entities?
     *
     * @return true if access to external entities is blocked
     */
    public boolean getXmlBlockExternal();


    /**
     * Controls whether the parsing of web.xml, web-fragment.xml, *.tld, *.jspx,
     * *.tagx and tagplugin.xml files for this Context will block the use of
     * external entities.
     *
     * @param xmlBlockExternal true to block external entities
     */
    public void setXmlBlockExternal(boolean xmlBlockExternal);


    /**
     * Will the parsing of *.tld files for this Context be performed by a
     * validating parser?
     *
     * @return true if validation is enabled.
     */
    public boolean getTldValidation();


    /**
     * Controls whether the parsing of *.tld files for this Context will be
     * performed by a validating parser.
     *
     * @param tldValidation true to enable xml validation
     */
    public void setTldValidation(boolean tldValidation);


    /**
     * Get the Jar Scanner to be used to scan for JAR resources for this
     * context.
     * @return  The Jar Scanner configured for this context.
     */
    public JarScanner getJarScanner();

    /**
     * Set the Jar Scanner to be used to scan for JAR resources for this
     * context.
     * @param jarScanner    The Jar Scanner to be used for this context.
     */
    public void setJarScanner(JarScanner jarScanner);

    /**
     * Obtain the {@link Authenticator} that is used by this context or
     * <code>null</code> if none is used.
     */
    public Authenticator getAuthenticator();

    /**
     * Set whether or not the effective web.xml for this context should be
     * logged on context start.
     */
    public void setLogEffectiveWebXml(boolean logEffectiveWebXml);

    /**
     * Should the effective web.xml for this context be logged on context start?
     */
    public boolean getLogEffectiveWebXml();

    /**
     * Get the instance manager associated with this context.
     */
    public InstanceManager getInstanceManager();

    /**
     * Set the instance manager associated with this context.
     */
    public void setInstanceManager(InstanceManager instanceManager);

    /**
     * Sets the regular expression that specifies which container provided SCIs
     * should be filtered out and not used for this context. Matching uses
     * {@link java.util.regex.Matcher#find()} so the regular expression only has
     * to match a sub-string of the fully qualified class name of the container
     * provided SCI for it to be filtered out.
     *
     * @param containerSciFilter The regular expression against which the fully
     *                           qualified class name of each container provided
     *                           SCI should be checked
     */
    public void setContainerSciFilter(String containerSciFilter);

    /**
     * Obtains the regular expression that specifies which container provided
     * SCIs should be filtered out and not used for this context. Matching uses
     * {@link java.util.regex.Matcher#find()} so the regular expression only has
     * to match a sub-string of the fully qualified class name of the container
     * provided SCI for it to be filtered out.
     *
     * @return The regular expression against which the fully qualified class
     *         name of each container provided SCI will be checked
     */
    public String getContainerSciFilter();


    // --------------------------------------------------------- Public Methods

    /**
     * Add a new Listener class name to the set of Listeners configured for this
     * application.
     *
     * <p>
     * The {@link ApplicationListener} class is used to pass an additional
     * parameter that allows to differentiate listeners to Web Application added
     * via configuration (web.xml or annotations) vs. ones added by frameworks,
     * such as listeners declared in JSP tag libraries (TLD files) that are
     * added by Jasper JSP Engine.
     *
     * <p>
     * The recommended method to call for the first use case is
     * {@link #addApplicationListener(String)}. The recommended replacement for
     * the second use case is to use {@code addListener(...)} methods in
     * {@link javax.servlet.ServletContext}.
     *
     * @param listener
     *            Definition of a listener, including its java class name.
     * @deprecated This method is removed from Tomcat 8.0.9 onwards. Use
     *             {@link #addApplicationListener(String)} or
     *             {@link javax.servlet.ServletContext#addListener(String)}.
     */
    @Deprecated
    public void addApplicationListener(ApplicationListener listener);


    /**
     * Add a new Listener class name to the set of Listeners
     * configured for this application.
     *
     * @param listener Java class name of a listener class
     */
    public void addApplicationListener(String listener);


    /**
     * Add a new application parameter for this application.
     *
     * @param parameter The new application parameter
     */
    public void addApplicationParameter(ApplicationParameter parameter);


    /**
     * Add a security constraint to the set for this web application.
     */
    public void addConstraint(SecurityConstraint constraint);


    /**
     * Add an error page for the specified error or Java exception.
     *
     * @param errorPage The error page definition to be added
     */
    public void addErrorPage(ErrorPage errorPage);


    /**
     * Add a filter definition to this Context.
     *
     * @param filterDef The filter definition to be added
     */
    public void addFilterDef(FilterDef filterDef);


    /**
     * Add a filter mapping to this Context.
     *
     * @param filterMap The filter mapping to be added
     */
    public void addFilterMap(FilterMap filterMap);

    /**
     * Add a filter mapping to this Context before the mappings defined in the
     * deployment descriptor but after any other mappings added via this method.
     *
     * @param filterMap The filter mapping to be added
     *
     * @exception IllegalArgumentException if the specified filter name
     *  does not match an existing filter definition, or the filter mapping
     *  is malformed
     */
    public void addFilterMapBefore(FilterMap filterMap);

    /**
     * Add the classname of an InstanceListener to be added to each
     * Wrapper appended to this Context.
     *
     * @param listener Java class name of an InstanceListener class
     */
    public void addInstanceListener(String listener);


    /**
     * Add a Locale Encoding Mapping (see Sec 5.4 of Servlet spec 2.4)
     *
     * @param locale locale to map an encoding for
     * @param encoding encoding to be used for a give locale
     */
    public void addLocaleEncodingMappingParameter(String locale, String encoding);


    /**
     * Add a new MIME mapping, replacing any existing mapping for
     * the specified extension.
     *
     * @param extension Filename extension being mapped
     * @param mimeType Corresponding MIME type
     */
    public void addMimeMapping(String extension, String mimeType);


    /**
     * Add a new context initialization parameter, replacing any existing
     * value for the specified name.
     *
     * @param name Name of the new parameter
     * @param value Value of the new  parameter
     */
    public void addParameter(String name, String value);


    /**
     * Add a security role reference for this web application.
     *
     * @param role Security role used in the application
     * @param link Actual security role to check for
     */
    public void addRoleMapping(String role, String link);


    /**
     * Add a new security role for this web application.
     *
     * @param role New security role
     */
    public void addSecurityRole(String role);


    /**
     * Add a new servlet mapping, replacing any existing mapping for
     * the specified pattern.
     *
     * @param pattern URL pattern to be mapped
     * @param name Name of the corresponding servlet to execute
     */
    public void addServletMapping(String pattern, String name);


    /**
     * Add a new servlet mapping, replacing any existing mapping for
     * the specified pattern.
     *
     * @param pattern URL pattern to be mapped
     * @param name Name of the corresponding servlet to execute
     * @param jspWildcard true if name identifies the JspServlet
     * and pattern contains a wildcard; false otherwise
     */
    public void addServletMapping(String pattern, String name,
            boolean jspWildcard);


    /**
     * Add a resource which will be watched for reloading by the host auto
     * deployer. Note: this will not be used in embedded mode.
     *
     * @param name Path to the resource, relative to docBase
     */
    public void addWatchedResource(String name);


    /**
     * Add a new welcome file to the set recognized by this Context.
     *
     * @param name New welcome file name
     */
    public void addWelcomeFile(String name);


    /**
     * Add the classname of a LifecycleListener to be added to each
     * Wrapper appended to this Context.
     *
     * @param listener Java class name of a LifecycleListener class
     */
    public void addWrapperLifecycle(String listener);


    /**
     * Add the classname of a ContainerListener to be added to each
     * Wrapper appended to this Context.
     *
     * @param listener Java class name of a ContainerListener class
     */
    public void addWrapperListener(String listener);


    /**
     * Factory method to create and return a new Wrapper instance, of
     * the Java implementation class appropriate for this Context
     * implementation.  The constructor of the instantiated Wrapper
     * will have been called, but no properties will have been set.
     */
    public Wrapper createWrapper();


    /**
     * Return the set of application listener class names configured
     * for this application.
     */
    public String[] findApplicationListeners();


    /**
     * Return the set of application parameters for this application.
     */
    public ApplicationParameter[] findApplicationParameters();


    /**
     * Return the set of security constraints for this web application.
     * If there are none, a zero-length array is returned.
     */
    public SecurityConstraint[] findConstraints();


    /**
     * Return the error page entry for the specified HTTP error code,
     * if any; otherwise return <code>null</code>.
     *
     * @param errorCode Error code to look up
     */
    public ErrorPage findErrorPage(int errorCode);


    /**
     * Return the error page entry for the specified Java exception type,
     * if any; otherwise return <code>null</code>.
     *
     * @param exceptionType Exception type to look up
     */
    public ErrorPage findErrorPage(String exceptionType);



    /**
     * Return the set of defined error pages for all specified error codes
     * and exception types.
     */
    public ErrorPage[] findErrorPages();


    /**
     * Return the filter definition for the specified filter name, if any;
     * otherwise return <code>null</code>.
     *
     * @param filterName Filter name to look up
     */
    public FilterDef findFilterDef(String filterName);


    /**
     * Return the set of defined filters for this Context.
     */
    public FilterDef[] findFilterDefs();


    /**
     * Return the set of filter mappings for this Context.
     */
    public FilterMap[] findFilterMaps();


    /**
     * Return the set of InstanceListener classes that will be added to
     * newly created Wrappers automatically.
     */
    public String[] findInstanceListeners();


    /**
     * Return the MIME type to which the specified extension is mapped,
     * if any; otherwise return <code>null</code>.
     *
     * @param extension Extension to map to a MIME type
     */
    public String findMimeMapping(String extension);


    /**
     * Return the extensions for which MIME mappings are defined.  If there
     * are none, a zero-length array is returned.
     */
    public String[] findMimeMappings();


    /**
     * Return the value for the specified context initialization
     * parameter name, if any; otherwise return <code>null</code>.
     *
     * @param name Name of the parameter to return
     */
    public String findParameter(String name);


    /**
     * Return the names of all defined context initialization parameters
     * for this Context.  If no parameters are defined, a zero-length
     * array is returned.
     */
    public String[] findParameters();


    /**
     * For the given security role (as used by an application), return the
     * corresponding role name (as defined by the underlying Realm) if there
     * is one.  Otherwise, return the specified role unchanged.
     *
     * @param role Security role to map
     */
    public String findRoleMapping(String role);


    /**
     * Return <code>true</code> if the specified security role is defined
     * for this application; otherwise return <code>false</code>.
     *
     * @param role Security role to verify
     */
    public boolean findSecurityRole(String role);


    /**
     * Return the security roles defined for this application.  If none
     * have been defined, a zero-length array is returned.
     */
    public String[] findSecurityRoles();


    /**
     * Return the servlet name mapped by the specified pattern (if any);
     * otherwise return <code>null</code>.
     *
     * @param pattern Pattern for which a mapping is requested
     */
    public String findServletMapping(String pattern);


    /**
     * Return the patterns of all defined servlet mappings for this
     * Context.  If no mappings are defined, a zero-length array is returned.
     */
    public String[] findServletMappings();


    /**
     * Return the context-relative URI of the error page for the specified
     * HTTP status code, if any; otherwise return <code>null</code>.
     *
     * @param status HTTP status code to look up
     */
    public String findStatusPage(int status);


    /**
     * Return the set of HTTP status codes for which error pages have
     * been specified.  If none are specified, a zero-length array
     * is returned.
     */
    public int[] findStatusPages();


    /**
     * Return the set of watched resources for this Context. If none are
     * defined, a zero length array will be returned.
     */
    public String[] findWatchedResources();


    /**
     * Return <code>true</code> if the specified welcome file is defined
     * for this Context; otherwise return <code>false</code>.
     *
     * @param name Welcome file to verify
     */
    public boolean findWelcomeFile(String name);


    /**
     * Return the set of welcome files defined for this Context.  If none are
     * defined, a zero-length array is returned.
     */
    public String[] findWelcomeFiles();


    /**
     * Return the set of LifecycleListener classes that will be added to
     * newly created Wrappers automatically.
     */
    public String[] findWrapperLifecycles();


    /**
     * Return the set of ContainerListener classes that will be added to
     * newly created Wrappers automatically.
     */
    public String[] findWrapperListeners();


    /**
     * Notify all {@link javax.servlet.ServletRequestListener}s that a request
     * has started.
     * @return <code>true</code> if the listeners fire successfully, else
     *         <code>false</code>
     */
    public boolean fireRequestInitEvent(ServletRequest request);

    /**
     * Notify all {@link javax.servlet.ServletRequestListener}s that a request
     * has ended.
     * @return <code>true</code> if the listeners fire successfully, else
     *         <code>false</code>
     */
    public boolean fireRequestDestroyEvent(ServletRequest request);

    /**
     * Reload this web application, if reloading is supported.
     *
     * @exception IllegalStateException if the <code>reloadable</code>
     *  property is set to <code>false</code>.
     */
    public void reload();


    /**
     * Remove the specified application listener class from the set of
     * listeners for this application.
     *
     * @param listener Java class name of the listener to be removed
     */
    public void removeApplicationListener(String listener);


    /**
     * Remove the application parameter with the specified name from
     * the set for this application.
     *
     * @param name Name of the application parameter to remove
     */
    public void removeApplicationParameter(String name);


    /**
     * Remove the specified security constraint from this web application.
     *
     * @param constraint Constraint to be removed
     */
    public void removeConstraint(SecurityConstraint constraint);


    /**
     * Remove the error page for the specified error code or
     * Java language exception, if it exists; otherwise, no action is taken.
     *
     * @param errorPage The error page definition to be removed
     */
    public void removeErrorPage(ErrorPage errorPage);


    /**
     * Remove the specified filter definition from this Context, if it exists;
     * otherwise, no action is taken.
     *
     * @param filterDef Filter definition to be removed
     */
    public void removeFilterDef(FilterDef filterDef);


    /**
     * Remove a filter mapping from this Context.
     *
     * @param filterMap The filter mapping to be removed
     */
    public void removeFilterMap(FilterMap filterMap);


    /**
     * Remove a class name from the set of InstanceListener classes that
     * will be added to newly created Wrappers.
     *
     * @param listener Class name of an InstanceListener class to be removed
     */
    public void removeInstanceListener(String listener);


    /**
     * Remove the MIME mapping for the specified extension, if it exists;
     * otherwise, no action is taken.
     *
     * @param extension Extension to remove the mapping for
     */
    public void removeMimeMapping(String extension);


    /**
     * Remove the context initialization parameter with the specified
     * name, if it exists; otherwise, no action is taken.
     *
     * @param name Name of the parameter to remove
     */
    public void removeParameter(String name);


    /**
     * Remove any security role reference for the specified name
     *
     * @param role Security role (as used in the application) to remove
     */
    public void removeRoleMapping(String role);


    /**
     * Remove any security role with the specified name.
     *
     * @param role Security role to remove
     */
    public void removeSecurityRole(String role);


    /**
     * Remove any servlet mapping for the specified pattern, if it exists;
     * otherwise, no action is taken.
     *
     * @param pattern URL pattern of the mapping to remove
     */
    public void removeServletMapping(String pattern);


    /**
     * Remove the specified watched resource name from the list associated
     * with this Context.
     *
     * @param name Name of the watched resource to be removed
     */
    public void removeWatchedResource(String name);


    /**
     * Remove the specified welcome file name from the list recognized
     * by this Context.
     *
     * @param name Name of the welcome file to be removed
     */
    public void removeWelcomeFile(String name);


    /**
     * Remove a class name from the set of LifecycleListener classes that
     * will be added to newly created Wrappers.
     *
     * @param listener Class name of a LifecycleListener class to be removed
     */
    public void removeWrapperLifecycle(String listener);


    /**
     * Remove a class name from the set of ContainerListener classes that
     * will be added to newly created Wrappers.
     *
     * @param listener Class name of a ContainerListener class to be removed
     */
    public void removeWrapperListener(String listener);


    /**
     * Return the real path for a given virtual path, if possible; otherwise
     * return <code>null</code>.
     *
     * @param path The path to the desired resource
     */
    public String getRealPath(String path);


    /**
     * Return the effective major version of the Servlet spec used by this
     * context.
     */
    public int getEffectiveMajorVersion();


    /**
     * Set the effective major version of the Servlet spec used by this
     * context.
     */
    public void setEffectiveMajorVersion(int major);


    /**
     * Return the effective minor version of the Servlet spec used by this
     * context.
     */
    public int getEffectiveMinorVersion();


    /**
     * Set the effective minor version of the Servlet spec used by this
     * context.
     */
    public void setEffectiveMinorVersion(int minor);


    /**
     * Obtain the JSP configuration for this context.
     */
    public JspConfigDescriptor getJspConfigDescriptor();


    /**
     * Add a URL for a JAR that contains static resources in a
     * META-INF/resources directory that should be included in the static
     * resources for this context.
     */
    public void addResourceJarUrl(URL url);


    /**
     * Add a ServletContainerInitializer instance to this web application.
     *
     * @param sci       The instance to add
     * @param classes   The classes in which the initializer expressed an
     *                  interest
     */
    public void addServletContainerInitializer(
            ServletContainerInitializer sci, Set<Class<?>> classes);

    /**
     * Is this Context paused whilst it is reloaded?
     */
    public boolean getPaused();


    /**
     * Is this context using version 2.2 of the Servlet spec?
     */
    boolean isServlet22();

    /**
     * Notification that servlet security has been dynamically set in a
     * {@link javax.servlet.ServletRegistration.Dynamic}
     * @param registration servlet security was modified for
     * @param servletSecurityElement new security constraints for this servlet
     * @return urls currently mapped to this registration that are already
     *         present in web.xml
     */
    Set<String> addServletSecurity(ApplicationServletRegistration registration,
            ServletSecurityElement servletSecurityElement);

    /**
     * Sets the (comma separated) list of Servlets that expect a resource to be
     * present. Used to ensure that welcome files associated with Servlets that
     * expect a resource to be present are not mapped when there is no resource.
     */
    public void setResourceOnlyServlets(String resourceOnlyServlets);

    /**
     * Obtains the list of Servlets that expect a resource to be present.
     *
     * @return  A comma separated list of Servlet names as used in web.xml
     */
    public String getResourceOnlyServlets();

    /**
     * Checks the named Servlet to see if it expects a resource to be present.
     *
     * @param servletName   Name of the Servlet (as per web.xml) to check
     * @return              <code>true</code> if the Servlet expects a resource,
     *                      otherwise <code>false</code>
     */
    public boolean isResourceOnlyServlet(String servletName);

    /**
     * Return the base name to use for WARs, directories or context.xml files
     * for this context.
     */
    public String getBaseName();

    /**
     * Set the version of this web application - used to differentiate
     * different versions of the same web application when using parallel
     * deployment.
     */
    public void setWebappVersion(String webappVersion);

    /**
     * Set the version of this web application - used to differentiate
     * different versions of the same web application when using parallel
     * deployment. If not specified, defaults to the empty string.
     */
    public String getWebappVersion();

    /**
     * Configure whether or not requests listeners will be fired on forwards for
     * this Context.
     */
    public void setFireRequestListenersOnForwards(boolean enable);

    /**
     * Determine whether or not requests listeners will be fired on forwards for
     * this Context.
     */
    public boolean getFireRequestListenersOnForwards();

    /**
     * Configures if a user presents authentication credentials, whether the
     * context will process them when the request is for a non-protected
     * resource.
     */
    public void setPreemptiveAuthentication(boolean enable);

    /**
     * Determines if a user presents authentication credentials, will the
     * context will process them when the request is for a non-protected
     * resource.
     */
    public boolean getPreemptiveAuthentication();

    /**
     * Configures if a response body is included when a redirect response is
     * sent to the client.
     */
    public void setSendRedirectBody(boolean enable);

    /**
     * Determines if the context is configured to include a response body as
     * part of a redirect response.
     */
    public boolean getSendRedirectBody();

    /**
     * Add a post construct method definition for the given class, if there is
     * an existing definition for the specified class - IllegalArgumentException
     * will be thrown.
     *
     * @param clazz Fully qualified class name
     * @param method
     *            Post construct method name
     * @throws IllegalArgumentException
     *             if the fully qualified class name or method name are
     *             <code>NULL</code>; if there is already post construct method
     *             definition for the given class
     */
    public void addPostConstructMethod(String clazz, String method);

    /**
     * Add a pre destroy method definition for the given class, if there is an
     * existing definition for the specified class - IllegalArgumentException
     * will be thrown.
     *
     * @param clazz Fully qualified class name
     * @param method
     *            Post construct method name
     * @throws IllegalArgumentException
     *             if the fully qualified class name or method name are
     *             <code>NULL</code>; if there is already pre destroy method
     *             definition for the given class
     */
    public void addPreDestroyMethod(String clazz, String method);

    /**
     * Removes the post construct method definition for the given class, if it
     * exists; otherwise, no action is taken.
     *
     * @param clazz
     *            Fully qualified class name
     */
    public void removePostConstructMethod(String clazz);

    /**
     * Removes the pre destroy method definition for the given class, if it
     * exists; otherwise, no action is taken.
     *
     * @param clazz
     *            Fully qualified class name
     */
    public void removePreDestroyMethod(String clazz);

    /**
     * Returns the method name that is specified as post construct method for
     * the given class, if it exists; otherwise <code>NULL</code> will be
     * returned.
     *
     * @param clazz
     *            Fully qualified class name
     *
     * @return the method name that is specified as post construct method for
     *         the given class, if it exists; otherwise <code>NULL</code> will
     *         be returned.
     */
    public String findPostConstructMethod(String clazz);

    /**
     * Returns the method name that is specified as pre destroy method for the
     * given class, if it exists; otherwise <code>NULL</code> will be returned.
     *
     * @param clazz
     *            Fully qualified class name
     *
     * @return the method name that is specified as pre destroy method for the
     *         given class, if it exists; otherwise <code>NULL</code> will be
     *         returned.
     */
    public String findPreDestroyMethod(String clazz);

    /**
     * Returns a map with keys - fully qualified class names of the classes that
     * have post construct methods and the values are the corresponding method
     * names. If there are no such classes an empty map will be returned.
     *
     * @return a map with keys - fully qualified class names of the classes that
     *         have post construct methods and the values are the corresponding
     *         method names.
     */
    public Map<String, String> findPostConstructMethods();

    /**
     * Returns a map with keys - fully qualified class names of the classes that
     * have pre destroy methods and the values are the corresponding method
     * names. If there are no such classes an empty map will be returned.
     *
     * @return a map with keys - fully qualified class names of the classes that
     *         have pre destroy methods and the values are the corresponding
     *         method names.
     */
    public Map<String, String> findPreDestroyMethods();

    /**
     * When a client provides the ID for a new session, should that ID be
     * validated? The only use case for using a client provided session ID is to
     * have a common session ID across multiple web applications. Therefore,
     * any client provided session ID should already exist in another web
     * application. If this check is enabled, the client provided session ID
     * will only be used if the session ID exists in at least one other web
     * application for the current host. Note that the following additional
     * tests are always applied, irrespective of this setting:
     * <ul>
     * <li>The session ID is provided by a cookie</li>
     * <li>The session cookie has a path of {@code /}</li>
     * </ul>
     *
     * @param validateClientProvidedNewSessionId
     *          {@code true} if validation should be applied
     */
    public void setValidateClientProvidedNewSessionId(boolean validateClientProvidedNewSessionId);

    /**
     * Will client provided session IDs be validated (see {@link
     * #setValidateClientProvidedNewSessionId(boolean)}) before use?
     *
     * @return {@code true} if validation will be applied. Otherwise, {@code
     *         false}
     */
    public boolean getValidateClientProvidedNewSessionId();

    /**
     * If enabled, requests for a web application context root will be
     * redirected (adding a trailing slash) by the Mapper. This is more
     * efficient but has the side effect of confirming that the context path is
     * valid.
     *
     * @param mapperContextRootRedirectEnabled Should the redirects be enabled?
     */
    public void setMapperContextRootRedirectEnabled(boolean mapperContextRootRedirectEnabled);

    /**
     * Determines if requests for a web application context root will be
     * redirected (adding a trailing slash) by the Mapper. This is more
     * efficient but has the side effect of confirming that the context path is
     * valid.
     *
     * @return {@code true} if the Mapper level redirect is enabled for this
     *         Context.
     */
    public boolean getMapperContextRootRedirectEnabled();

    /**
     * If enabled, requests for a directory will be redirected (adding a
     * trailing slash) by the Mapper. This is more efficient but has the
     * side effect of confirming that the directory is valid.
     *
     * @param mapperDirectoryRedirectEnabled Should the redirects be enabled?
     */
    public void setMapperDirectoryRedirectEnabled(boolean mapperDirectoryRedirectEnabled);

    /**
     * Determines if requests for a directory will be redirected (adding a
     * trailing slash) by the Mapper. This is more efficient but has the
     * side effect of confirming that the directory is valid.
     *
     * @return {@code true} if the Mapper level redirect is enabled for this
     *         Context.
     */
    public boolean getMapperDirectoryRedirectEnabled();

    /**
     * Controls whether HTTP 1.1 and later location headers generated by a call
     * to {@link javax.servlet.http.HttpServletResponse#sendRedirect(String)}
     * will use relative or absolute redirects.
     * <p>
     * Relative redirects are more efficient but may not work with reverse
     * proxies that change the context path. It should be noted that it is not
     * recommended to use a reverse proxy to change the context path because of
     * the multiple issues it creates.
     * <p>
     * Absolute redirects should work with reverse proxies that change the
     * context path but may cause issues with the
     * {@link org.apache.catalina.filters.RemoteIpFilter} if the filter is
     * changing the scheme and/or port.
     *
     * @param useRelativeRedirects {@code true} to use relative redirects and
     *                             {@code false} to use absolute redirects
     */
    public void setUseRelativeRedirects(boolean useRelativeRedirects);

    /**
     * Will HTTP 1.1 and later location headers generated by a call to
     * {@link javax.servlet.http.HttpServletResponse#sendRedirect(String)} use
     * relative or absolute redirects.
     *
     * @return {@code true} if relative redirects will be used {@code false} if
     *         absolute redirects are used.
     *
     * @see #setUseRelativeRedirects(boolean)
     */
    public boolean getUseRelativeRedirects();

    /**
     * Are paths used in calls to obtain a request dispatcher expected to be
     * encoded? This affects both how Tomcat handles calls to obtain a request
     * dispatcher as well as how Tomcat generates paths used to obtain request
     * dispatchers internally.
     *
     * @param dispatchersUseEncodedPaths {@code true} to use encoded paths,
     *        otherwise {@code false}
     */
    public void setDispatchersUseEncodedPaths(boolean dispatchersUseEncodedPaths);

    /**
     * Are paths used in calls to obtain a request dispatcher expected to be
     * encoded? This applys to both how Tomcat handles calls to obtain a request
     * dispatcher as well as how Tomcat generates paths used to obtain request
     * dispatchers internally.
     *
     * @return {@code true} if encoded paths will be used, otherwise
     *         {@code false}
     */
    public boolean getDispatchersUseEncodedPaths();
}
