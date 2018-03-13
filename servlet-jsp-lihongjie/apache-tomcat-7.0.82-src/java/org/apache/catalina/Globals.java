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

import org.apache.tomcat.util.compat.JreVendor;

/**
 * Global constants that are applicable to multiple packages within Catalina.
 *
 * @author Craig R. McClanahan
 */
public final class Globals {

    /**
     * The servlet context attribute under which we store the alternate
     * deployment descriptor for this web application
     */
    public static final String ALT_DD_ATTR =
        "org.apache.catalina.deploy.alt_dd";


    /**
     * The request attribute under which we store the array of X509Certificate
     * objects representing the certificate chain presented by our client,
     * if any.
     */
    public static final String CERTIFICATES_ATTR =
        "javax.servlet.request.X509Certificate";


    /**
     * The request attribute under which we store the name of the cipher suite
     * being used on an SSL connection (as an object of type
     * java.lang.String).
     */
    public static final String CIPHER_SUITE_ATTR =
        "javax.servlet.request.cipher_suite";


    /**
     * Request dispatcher state.
     */
    public static final String DISPATCHER_TYPE_ATTR =
        "org.apache.catalina.core.DISPATCHER_TYPE";


    /**
     * Request dispatcher path.
     */
    public static final String DISPATCHER_REQUEST_PATH_ATTR =
        "org.apache.catalina.core.DISPATCHER_REQUEST_PATH";


    /**
     * The JNDI directory context which is associated with the context. This
     * context can be used to manipulate static files.
     */
    public static final String RESOURCES_ATTR =
        "org.apache.catalina.resources";


    /**
     * The servlet context attribute under which we store the class path
     * for our application class loader (as an object of type String),
     * delimited with the appropriate path delimiter for this platform.
     */
    public static final String CLASS_PATH_ATTR =
        "org.apache.catalina.jsp_classpath";


    /**
     * The request attribute under which we store the key size being used for
     * this SSL connection (as an object of type java.lang.Integer).
     */
    public static final String KEY_SIZE_ATTR =
        "javax.servlet.request.key_size";


    /**
     * The request attribute under which we store the session id being used
     * for this SSL connection (as an object of type java.lang.String).
     */
    public static final String SSL_SESSION_ID_ATTR =
        "javax.servlet.request.ssl_session_id";
    /**
     * Tomcat specific attribute as used in Tomcat 6.
     * @deprecated
     */
    @Deprecated
    public static final String SSL_SESSION_ID_TOMCAT_ATTR =
        "javax.servlet.request.ssl_session";

    /**
     * The request attribute key for the session manager.
     * This one is a Tomcat extension to the Servlet spec.
     */
    public static final String SSL_SESSION_MGR_ATTR =
        "javax.servlet.request.ssl_session_mgr";


    /**
     * The servlet context attribute under which the managed bean Registry
     * will be stored for privileged contexts (if enabled).
     * @deprecated Unused. Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public static final String MBEAN_REGISTRY_ATTR =
        "org.apache.catalina.Registry";


    /**
     * The servlet context attribute under which the MBeanServer will be stored
     * for privileged contexts (if enabled).
     * @deprecated Unused. Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public static final String MBEAN_SERVER_ATTR =
        "org.apache.catalina.MBeanServer";


    /**
     * The request attribute under which we store the servlet name on a
     * named dispatcher request.
     */
    public static final String NAMED_DISPATCHER_ATTR =
        "org.apache.catalina.NAMED";


    /**
     * The servlet context attribute under which we store a flag used
     * to mark this request as having been processed by the SSIServlet.
     * We do this because of the pathInfo mangling happening when using
     * the CGIServlet in conjunction with the SSI servlet. (value stored
     * as an object of type String)
     */
     public static final String SSI_FLAG_ATTR =
         "org.apache.catalina.ssi.SSIServlet";


    /**
     * The subject under which the AccessControlContext is running.
     */
    public static final String SUBJECT_ATTR =
        "javax.security.auth.subject";


    public static final String GSS_CREDENTIAL_ATTR =
        "org.apache.catalina.realm.GSS_CREDENTIAL";


    /**
     * All request attributes which names start with this prefix are used by
     * connector implementations. They are passed down to coyoteRequest and back
     * up. See <code>Request.setAttribute(String, Object)</code>.
     * @deprecated Unused. Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public static final String TOMCAT_CONNECTOR_ATTR_PREFIX =
        "org.apache.tomcat.";


    /**
     * The request attribute that is set to the value of {@code Boolean.TRUE}
     * if connector processing this request supports Comet API.
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String COMET_SUPPORTED_ATTR =
        org.apache.coyote.Constants.COMET_SUPPORTED_ATTR;


    /**
     * The request attribute that is set to the value of {@code Boolean.TRUE}
     * if connector processing this request supports setting
     * per-connection request timeout through Comet API.
     *
     * @see org.apache.catalina.comet.CometEvent#setTimeout(int)
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String COMET_TIMEOUT_SUPPORTED_ATTR =
            org.apache.coyote.Constants.COMET_TIMEOUT_SUPPORTED_ATTR;


    /**
     * The request attribute that can be set to a value of type
     * {@code java.lang.Integer} to specify per-connection request
     * timeout for Comet API. The value is in milliseconds.
     *
     * @see org.apache.catalina.comet.CometEvent#setTimeout(int)
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String COMET_TIMEOUT_ATTR =
        org.apache.coyote.Constants.COMET_TIMEOUT_ATTR;


    /**
     * The request attribute that is set to the value of {@code Boolean.TRUE}
     * if connector processing this request supports use of sendfile.
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String SENDFILE_SUPPORTED_ATTR =
            org.apache.coyote.Constants.SENDFILE_SUPPORTED_ATTR;


    /**
     * The request attribute that can be used by a servlet to pass
     * to the connector the name of the file that is to be served
     * by sendfile. The value should be {@code java.lang.String}
     * that is {@code File.getCanonicalPath()} of the file to be served.
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String SENDFILE_FILENAME_ATTR =
            org.apache.coyote.Constants.SENDFILE_FILENAME_ATTR;


    /**
     * The request attribute that can be used by a servlet to pass
     * to the connector the start offset of the part of a file
     * that is to be served by sendfile. The value should be
     * {@code java.lang.Long}. To serve complete file
     * the value should be {@code Long.valueOf(0)}.
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String SENDFILE_FILE_START_ATTR =
            org.apache.coyote.Constants.SENDFILE_FILE_START_ATTR;


    /**
     * The request attribute that can be used by a servlet to pass
     * to the connector the end offset (not including) of the part
     * of a file that is to be served by sendfile. The value should be
     * {@code java.lang.Long}. To serve complete file
     * the value should be equal to the length of the file.
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String SENDFILE_FILE_END_ATTR =
            org.apache.coyote.Constants.SENDFILE_FILE_END_ATTR;


    /**
     * The request attribute set by the RemoteIpFilter, RemoteIpValve (and may
     * be set by other similar components) that identifies for the connector the
     * remote IP address claimed to be associated with this request when a
     * request is received via one or more proxies. It is typically provided via
     * the X-Forwarded-For HTTP header.
     *
     * Duplicated here for neater code in the catalina packages.
     */
    public static final String REMOTE_ADDR_ATTRIBUTE =
            org.apache.coyote.Constants.REMOTE_ADDR_ATTRIBUTE;


    /**
     *
     */
    public static final String ASYNC_SUPPORTED_ATTR =
        "org.apache.catalina.ASYNC_SUPPORTED";


    /**
     * The request attribute that is set to {@code Boolean.TRUE} if some request
     * parameters have been ignored during request parameters parsing. It can
     * happen, for example, if there is a limit on the total count of parseable
     * parameters, or if parameter cannot be decoded, or any other error
     * happened during parameter parsing.
     */
    public static final String PARAMETER_PARSE_FAILED_ATTR =
        "org.apache.catalina.parameter_parse_failed";


    /**
     * The reason that the parameter parsing failed.
     */
    public static final String PARAMETER_PARSE_FAILED_REASON_ATTR =
            "org.apache.catalina.parameter_parse_failed_reason";


    /**
     * The master flag which controls strict servlet specification
     * compliance.
     */
    public static final boolean STRICT_SERVLET_COMPLIANCE =
        Boolean.parseBoolean(System.getProperty("org.apache.catalina.STRICT_SERVLET_COMPLIANCE", "false"));


    /**
     * Has security been turned on?
     */
    public static final boolean IS_SECURITY_ENABLED =
        (System.getSecurityManager() != null);


    /**
     * Default domain for MBeans if none can be determined
     */
    public static final String DEFAULT_MBEAN_DOMAIN = "Catalina";


    /**
     * Name of the system property containing
     * the tomcat product installation path
     */
    public static final String CATALINA_HOME_PROP = "catalina.home";


    /**
     * Name of the system property containing
     * the tomcat instance installation path
     */
    public static final String CATALINA_BASE_PROP = "catalina.base";


    /**
     * Name of the ServletContext init-param that determines if the JSP engine
     * should validate *.tld files when parsing them.
     * <p>
     * This must be kept in sync with org.apache.jasper.Constants
     */
    public static final String JASPER_XML_VALIDATION_TLD_INIT_PARAM =
            "org.apache.jasper.XML_VALIDATE_TLD";


    /**
     * Name of the ServletContext init-param that determines if the JSP engine
     * should validate web.xml files when parsing them.
     * <p>
     * This must be kept in sync with org.apache.jasper.Constants
     */
    public static final String JASPER_XML_VALIDATION_INIT_PARAM =
            "org.apache.jasper.XML_VALIDATE";


    /**
     * Name of the ServletContext init-param that determines if the JSP engine
     * will block external entities from being used in *.tld, *.jspx, *.tagx and
     * tagplugin.xml files.
     * <p>
     * This must be kept in sync with org.apache.jasper.Constants
     */
    public static final String JASPER_XML_BLOCK_EXTERNAL_INIT_PARAM =
            "org.apache.jasper.XML_BLOCK_EXTERNAL";

    @Deprecated // Will be removed in Tomcat 9.0.x
    public static final boolean IS_ORACLE_JVM = JreVendor.IS_ORACLE_JVM;

    @Deprecated // Will be removed in Tomcat 9.0.x
    public static final boolean IS_IBM_JVM = JreVendor.IS_IBM_JVM;
}
