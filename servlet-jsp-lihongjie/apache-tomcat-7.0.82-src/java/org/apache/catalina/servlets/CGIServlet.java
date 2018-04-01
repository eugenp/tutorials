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
package org.apache.catalina.servlets;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.IOTools;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;


/**
 *  CGI-invoking servlet for web applications, used to execute scripts which
 *  comply to the Common Gateway Interface (CGI) specification and are named
 *  in the path-info used to invoke this servlet.
 *
 * <p>
 * <i>Note: This code compiles and even works for simple CGI cases.
 *          Exhaustive testing has not been done.  Please consider it beta
 *          quality.  Feedback is appreciated to the author (see below).</i>
 * </p>
 * <p>
 *
 * <b>Example</b>:<br>
 * If an instance of this servlet was mapped (using
 *       <code>&lt;web-app&gt;/WEB-INF/web.xml</code>) to:
 * </p>
 * <p>
 * <code>
 * &lt;web-app&gt;/cgi-bin/*
 * </code>
 * </p>
 * <p>
 * then the following request:
 * </p>
 * <p>
 * <code>
 * http://localhost:8080/&lt;web-app&gt;/cgi-bin/dir1/script/pathinfo1
 * </code>
 * </p>
 * <p>
 * would result in the execution of the script
 * </p>
 * <p>
 * <code>
 * &lt;web-app-root&gt;/WEB-INF/cgi/dir1/script
 * </code>
 * </p>
 * <p>
 * with the script's <code>PATH_INFO</code> set to <code>/pathinfo1</code>.
 * </p>
 * <p>
 * Recommendation:  House all your CGI scripts under
 * <code>&lt;webapp&gt;/WEB-INF/cgi</code>.  This will ensure that you do not
 * accidentally expose your cgi scripts' code to the outside world and that
 * your cgis will be cleanly ensconced underneath the WEB-INF (i.e.,
 * non-content) area.
 * </p>
 * <p>
 * The default CGI location is mentioned above.  You have the flexibility to
 * put CGIs wherever you want, however:
 * </p>
 * <p>
 *   The CGI search path will start at
 *   webAppRootDir + File.separator + cgiPathPrefix
 *   (or webAppRootDir alone if cgiPathPrefix is
 *   null).
 * </p>
 * <p>
 *   cgiPathPrefix is defined by setting
 *   this servlet's cgiPathPrefix init parameter
 * </p>
 *
 * <p>
 *
 * <B>CGI Specification</B>:<br> derived from
 * <a href="http://cgi-spec.golux.com">http://cgi-spec.golux.com</a>.
 * A work-in-progress &amp; expired Internet Draft.  Note no actual RFC describing
 * the CGI specification exists.  Where the behavior of this servlet differs
 * from the specification cited above, it is either documented here, a bug,
 * or an instance where the specification cited differs from Best
 * Community Practice (BCP).
 * Such instances should be well-documented here.  Please email the
 * <a href="http://tomcat.apache.org/lists.html">Tomcat group</a>
 * with amendments.
 *
 * </p>
 * <p>
 *
 * <b>Canonical metavariables</b>:<br>
 * The CGI specification defines the following canonical metavariables:
 * <br>
 * [excerpt from CGI specification]
 * <PRE>
 *  AUTH_TYPE
 *  CONTENT_LENGTH
 *  CONTENT_TYPE
 *  GATEWAY_INTERFACE
 *  PATH_INFO
 *  PATH_TRANSLATED
 *  QUERY_STRING
 *  REMOTE_ADDR
 *  REMOTE_HOST
 *  REMOTE_IDENT
 *  REMOTE_USER
 *  REQUEST_METHOD
 *  SCRIPT_NAME
 *  SERVER_NAME
 *  SERVER_PORT
 *  SERVER_PROTOCOL
 *  SERVER_SOFTWARE
 * </PRE>
 * <p>
 * Metavariables with names beginning with the protocol name (<EM>e.g.</EM>,
 * "HTTP_ACCEPT") are also canonical in their description of request header
 * fields.  The number and meaning of these fields may change independently
 * of this specification.  (See also section 6.1.5 [of the CGI specification].)
 * </p>
 * [end excerpt]
 *
 * <h2> Implementation notes</h2>
 * <p>
 *
 * <b>standard input handling</b>: If your script accepts standard input,
 * then the client must start sending input within a certain timeout period,
 * otherwise the servlet will assume no input is coming and carry on running
 * the script.  The script's the standard input will be closed and handling of
 * any further input from the client is undefined.  Most likely it will be
 * ignored.  If this behavior becomes undesirable, then this servlet needs
 * to be enhanced to handle threading of the spawned process' stdin, stdout,
 * and stderr (which should not be too hard).
 * <br>
 * If you find your cgi scripts are timing out receiving input, you can set
 * the init parameter <code>stderrTimeout</code> of your webapps' cgi-handling
 * servlet.
 * </p>
 * <p>
 *
 * <b>Metavariable Values</b>: According to the CGI specification,
 * implementations may choose to represent both null or missing values in an
 * implementation-specific manner, but must define that manner.  This
 * implementation chooses to always define all required metavariables, but
 * set the value to "" for all metavariables whose value is either null or
 * undefined.  PATH_TRANSLATED is the sole exception to this rule, as per the
 * CGI Specification.
 *
 * </p>
 * <p>
 *
 * <b>NPH --  Non-parsed-header implementation</b>:  This implementation does
 * not support the CGI NPH concept, whereby server ensures that the data
 * supplied to the script are precisely as supplied by the client and
 * unaltered by the server.
 * </p>
 * <p>
 * The function of a servlet container (including Tomcat) is specifically
 * designed to parse and possible alter CGI-specific variables, and as
 * such makes NPH functionality difficult to support.
 * </p>
 * <p>
 * The CGI specification states that compliant servers MAY support NPH output.
 * It does not state servers MUST support NPH output to be unconditionally
 * compliant.  Thus, this implementation maintains unconditional compliance
 * with the specification though NPH support is not present.
 * </p>
 * <p>
 *
 * The CGI specification is located at
 * <a href="http://cgi-spec.golux.com">http://cgi-spec.golux.com</a>.
 *
 * </p>
 * <h3>TODO:</h3>
 * <ul>
 * <li> Support for setting headers (for example, Location headers don't work)
 * <li> Support for collapsing multiple header lines (per RFC 2616)
 * <li> Ensure handling of POST method does not interfere with 2.3 Filters
 * <li> Refactor some debug code out of core
 * <li> Ensure header handling preserves encoding
 * <li> Possibly rewrite CGIRunner.run()?
 * <li> Possibly refactor CGIRunner and CGIEnvironment as non-inner classes?
 * <li> Document handling of cgi stdin when there is no stdin
 * <li> Revisit IOException handling in CGIRunner.run()
 * <li> Better documentation
 * <li> Confirm use of ServletInputStream.available() in CGIRunner.run() is
 *      not needed
 * <li> [add more to this TODO list]
 * </ul>
 *
 * @author Martin T Dengler [root@martindengler.com]
 * @author Amy Roh
 */
public final class CGIServlet extends HttpServlet {

    private static final Log log = LogFactory.getLog(CGIServlet.class);
    private static final StringManager sm = StringManager.getManager(CGIServlet.class);

    private static final String LINE_SEP = System.getProperty("line.separator");
    
    /* some vars below copied from Craig R. McClanahan's InvokerServlet */

    private static final long serialVersionUID = 1L;

    /**
     *  The CGI search path will start at
     *    webAppRootDir + File.separator + cgiPathPrefix
     *    (or webAppRootDir alone if cgiPathPrefix is
     *    null)
     */
    private String cgiPathPrefix = null;

    /** the executable to use with the script */
    private String cgiExecutable = "perl";

    /** additional arguments for the executable */
    private List<String> cgiExecutableArgs = null;

    /** the encoding to use for parameters */
    private String parameterEncoding =
        System.getProperty("file.encoding", "UTF-8");

    /**
     * The time (in milliseconds) to wait for the reading of stderr to complete
     * before terminating the CGI process.
     */
    private long stderrTimeout = 2000;

    /**
     * The regular expression used to select HTTP headers to be passed to the
     * CGI process as environment variables. The name of the environment
     * variable will be the name of the HTTP header converter to upper case,
     * prefixed with <code>HTTP_</code> and with all <code>-</code> characters
     * converted to <code>_</code>.
     */
    private Pattern envHttpHeadersPattern = Pattern.compile(
            "ACCEPT[-0-9A-Z]*|CACHE-CONTROL|COOKIE|HOST|IF-[-0-9A-Z]*|REFERER|USER-AGENT");

    /** object used to ensure multiple threads don't try to expand same file */
    private static final Object expandFileLock = new Object();

    /** the shell environment variables to be passed to the CGI script */
    private final Hashtable<String,String> shellEnv = new Hashtable<String,String>();

    /**
     * Enable creation of script command line arguments from query-string.
     * See https://tools.ietf.org/html/rfc3875#section-4.4
     * 4.4.  The Script Command Line
     */
    private boolean enableCmdLineArguments = true;

    /**
     * Sets instance variables.
     * <P>
     * Modified from Craig R. McClanahan's InvokerServlet
     * </P>
     *
     * @param config                    a <code>ServletConfig</code> object
     *                                  containing the servlet's
     *                                  configuration and initialization
     *                                  parameters
     *
     * @exception ServletException      if an exception has occurred that
     *                                  interferes with the servlet's normal
     *                                  operation
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        // Set our properties from the initialization parameters
        cgiPathPrefix = getServletConfig().getInitParameter("cgiPathPrefix");
        boolean passShellEnvironment =
            Boolean.parseBoolean(getServletConfig().getInitParameter("passShellEnvironment"));

        if (passShellEnvironment) {
            shellEnv.putAll(System.getenv());
        }

        Enumeration<String> e = config.getInitParameterNames();
        while(e.hasMoreElements()) {
            String initParamName = e.nextElement();
            if (initParamName.startsWith("environment-variable-")) {
                if (initParamName.length() == 21) {
                    throw new ServletException(sm.getString("cgiServlet.emptyEnvVarName"));
                }
                shellEnv.put(initParamName.substring(21), config.getInitParameter(initParamName));
            }
        }

        if (getServletConfig().getInitParameter("executable") != null) {
            cgiExecutable = getServletConfig().getInitParameter("executable");
        }

        if (getServletConfig().getInitParameter("executable-arg-1") != null) {
            List<String> args = new ArrayList<String>();
            for (int i = 1;; i++) {
                String arg = getServletConfig().getInitParameter(
                        "executable-arg-" + i);
                if (arg == null) {
                    break;
                }
                args.add(arg);
            }
            cgiExecutableArgs = args;
        }

        if (getServletConfig().getInitParameter("parameterEncoding") != null) {
            parameterEncoding = getServletConfig().getInitParameter("parameterEncoding");
        }

        if (getServletConfig().getInitParameter("stderrTimeout") != null) {
            stderrTimeout = Long.parseLong(getServletConfig().getInitParameter(
                    "stderrTimeout"));
        }

        if (getServletConfig().getInitParameter("envHttpHeaders") != null) {
            envHttpHeadersPattern =
                    Pattern.compile(getServletConfig().getInitParameter("envHttpHeaders"));
        }

        if (getServletConfig().getInitParameter("enableCmdLineArguments") != null) {
            enableCmdLineArguments =
                    Boolean.parseBoolean(config.getInitParameter("enableCmdLineArguments"));
        }
    }


    /**
     * Logs important Servlet API and container information.
     *
     * <p>
     * Copied from SnoopAllServlet by Craig R. McClanahan
     * </p>
     *
     * @param  out    Unused
     * @param  req    HttpServletRequest object used as source of information
     * @param  res    Unused
     *
     * @exception  IOException  if a write operation exception occurs
     *
     * @deprecated Use {@link #printServletEnvironment(HttpServletRequest)}.
     *             This will be removed in Tomcat 8.5.X onwards
     */
    @Deprecated
    protected void printServletEnvironment(ServletOutputStream out,
            HttpServletRequest req, HttpServletResponse res) throws IOException {
        printServletEnvironment(req);
    }

    /**
     * Logs important Servlet API and container information.
     *
     * <p>
     * Based on SnoopAllServlet by Craig R. McClanahan
     * </p>
     *
     * @param  req    HttpServletRequest object used as source of information
     *
     * @exception  IOException  if a write operation exception occurs
     */
    private void printServletEnvironment(HttpServletRequest req) throws IOException {

        // Document the properties from ServletRequest
        log.trace("ServletRequest Properties");
        Enumeration<String> attrs = req.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            log.trace("Request Attribute: " + attr + ": [ " + req.getAttribute(attr) +"]");
        }
        log.trace("Character Encoding: [" + req.getCharacterEncoding() + "]");
        log.trace("Content Length: [" + req.getContentLength() + "]");
        log.trace("Content Type: [" + req.getContentType() + "]");
        Enumeration<Locale> locales = req.getLocales();
        while (locales.hasMoreElements()) {
            Locale locale = locales.nextElement();
            log.trace("Locale: [" +locale + "]");
        }
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            for (String value : req.getParameterValues(param)) {
                log.trace("Request Parameter: " + param + ":  [" + value + "]");
            }
        }
        log.trace("Protocol: [" + req.getProtocol() + "]");
        log.trace("Remote Address: [" + req.getRemoteAddr() + "]");
        log.trace("Remote Host: [" + req.getRemoteHost() + "]");
        log.trace("Scheme: [" + req.getScheme() + "]");
        log.trace("Secure: [" + req.isSecure() + "]");
        log.trace("Server Name: [" + req.getServerName() + "]");
        log.trace("Server Port: [" + req.getServerPort() + "]");

        // Document the properties from HttpServletRequest
        log.trace("HttpServletRequest Properties");
        log.trace("Auth Type: [" + req.getAuthType() + "]");
        log.trace("Context Path: [" + req.getContextPath() + "]");
        Cookie cookies[] = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.trace("Cookie: " + cookie.getName() + ": [" + cookie.getValue() + "]");
            }
        }
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            log.trace("HTTP Header: " + header + ": [" + req.getHeader(header) + "]");
        }
        log.trace("Method: [" + req.getMethod() + "]");
        log.trace("Path Info: [" + req.getPathInfo() + "]");
        log.trace("Path Translated: [" + req.getPathTranslated() + "]");
        log.trace("Query String: [" + req.getQueryString() + "]");
        log.trace("Remote User: [" + req.getRemoteUser() + "]");
        log.trace("Requested Session ID: [" + req.getRequestedSessionId() + "]");
        log.trace("Requested Session ID From Cookie: [" +
                req.isRequestedSessionIdFromCookie() + "]");
        log.trace("Requested Session ID From URL: [" + req.isRequestedSessionIdFromURL() + "]");
        log.trace("Requested Session ID Valid: [" + req.isRequestedSessionIdValid() + "]");
        log.trace("Request URI: [" + req.getRequestURI() + "]");
        log.trace("Servlet Path: [" + req.getServletPath() + "]");
        log.trace("User Principal: [" + req.getUserPrincipal() + "]");

        // Process the current session (if there is one)
        HttpSession session = req.getSession(false);
        if (session != null) {

            // Document the session properties
            log.trace("HttpSession Properties");
            log.trace("ID: [" + session.getId() + "]");
            log.trace("Creation Time: [" + new Date(session.getCreationTime()) + "]");
            log.trace("Last Accessed Time: [" + new Date(session.getLastAccessedTime()) + "]");
            log.trace("Max Inactive Interval: [" + session.getMaxInactiveInterval() + "]");

            // Document the session attributes
            attrs = session.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String attr = attrs.nextElement();
                log.trace("Session Attribute: " + attr + ": [" + session.getAttribute(attr) + "]");
            }
        }

        // Document the servlet configuration properties
        log.trace("ServletConfig Properties");
        log.trace("Servlet Name: [" + getServletConfig().getServletName() + "]");

        // Document the servlet configuration initialization parameters
        params = getServletConfig().getInitParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String value = getServletConfig().getInitParameter(param);
            log.trace("Servlet Init Param: " + param + ": [" + value + "]");
        }

        // Document the servlet context properties
        log.trace("ServletContext Properties");
        log.trace("Major Version: [" + getServletContext().getMajorVersion() + "]");
        log.trace("Minor Version: [" + getServletContext().getMinorVersion() + "]");
        log.trace("Real Path for '/': [" + getServletContext().getRealPath("/") + "]");
        log.trace("Server Info: [" + getServletContext().getServerInfo() + "]");

        // Document the servlet context initialization parameters
        log.trace("ServletContext Initialization Parameters");
        params = getServletContext().getInitParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String value = getServletContext().getInitParameter(param);
            log.trace("Servlet Context Init Param: " + param + ": [" + value + "]");
        }

        // Document the servlet context attributes
        log.trace("ServletContext Attributes");
        attrs = getServletContext().getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            log.trace("Servlet Context Attribute: " + attr +
                    ": [" + getServletContext().getAttribute(attr) + "]");
        }
    }


    /**
     * Provides CGI Gateway service -- delegates to
     * {@link #doGet(HttpServletRequest, HttpServletResponse)}.
     *
     * @param  req   HttpServletRequest passed in by servlet container
     * @param  res   HttpServletResponse passed in by servlet container
     *
     * @exception  ServletException  if a servlet-specific exception occurs
     * @exception  IOException  if a read/write exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {
        doGet(req, res);
    }


    /**
     * Provides CGI Gateway service.
     *
     * @param  req   HttpServletRequest passed in by servlet container
     * @param  res   HttpServletResponse passed in by servlet container
     *
     * @exception  ServletException  if a servlet-specific exception occurs
     * @exception  IOException  if a read/write exception occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        CGIEnvironment cgiEnv = new CGIEnvironment(req, getServletContext());

        if (cgiEnv.isValid()) {
            CGIRunner cgi = new CGIRunner(cgiEnv.getCommand(),
                                          cgiEnv.getEnvironment(),
                                          cgiEnv.getWorkingDirectory(),
                                          cgiEnv.getParameters());

            if ("POST".equals(req.getMethod())) {
                cgi.setInput(req.getInputStream());
            }
            cgi.setResponse(res);
            cgi.run();
        } else {
            res.sendError(404);
        }

        if (log.isTraceEnabled()) {
            String[] cgiEnvLines = cgiEnv.toString().split(LINE_SEP);
            for (String cgiEnvLine : cgiEnvLines) {
                log.trace(cgiEnvLine);
            }

            printServletEnvironment(req);
        }
        }


    /**
     * Behaviour depends on the status code.
     *
     * Status < 400  - Calls setStatus. Returns false. CGI servlet will provide
     *                 the response body.
     * Status >= 400 - Calls sendError(status), returns true. Standard error
     *                 page mechanism will provide the response body.
     */
    private boolean setStatus(HttpServletResponse response, int status) throws IOException {
        if (status >= HttpServletResponse.SC_BAD_REQUEST) {
            response.sendError(status);
            return true;
        } else {
            response.setStatus(status);
            return false;
        }
    }


    /**
     * Encapsulates the CGI environment and rules to derive
     * that environment from the servlet container and request information.
     */
    protected class CGIEnvironment {


        /** context of the enclosing servlet */
        private ServletContext context = null;

        /** context path of enclosing servlet */
        private String contextPath = null;

        /** servlet URI of the enclosing servlet */
        private String servletPath = null;

        /** pathInfo for the current request */
        private String pathInfo = null;

        /** real file system directory of the enclosing servlet's web app */
        private String webAppRootDir = null;

        /** tempdir for context - used to expand scripts in unexpanded wars */
        private File tmpDir = null;

        /** derived cgi environment */
        private Hashtable<String, String> env = null;

        /** cgi command to be invoked */
        private String command = null;

        /** cgi command's desired working directory */
        private final File workingDirectory;

        /** cgi command's command line parameters */
        private final ArrayList<String> cmdLineParameters = new ArrayList<String>();

        /** whether or not this object is valid or not */
        private final boolean valid;


        /**
         * Creates a CGIEnvironment and derives the necessary environment,
         * query parameters, working directory, cgi command, etc.
         *
         * @param  req       HttpServletRequest for information provided by
         *                   the Servlet API
         * @param  context   ServletContext for information provided by the
         *                   Servlet API
         * @throws IOException an IO error occurred
         */
        protected CGIEnvironment(HttpServletRequest req,
                                 ServletContext context) throws IOException {
            setupFromContext(context);
            setupFromRequest(req);

            this.valid = setCGIEnvironment(req);

            if (this.valid) {
                workingDirectory = new File(command.substring(0,
                      command.lastIndexOf(File.separator)));
            } else {
                workingDirectory = null;
            }

        }


        /**
         * Uses the ServletContext to set some CGI variables
         *
         * @param  context   ServletContext for information provided by the
         *                   Servlet API
         */
        protected void setupFromContext(ServletContext context) {
            this.context = context;
            this.webAppRootDir = context.getRealPath("/");
            this.tmpDir = (File) context.getAttribute(ServletContext.TEMPDIR);
        }


        /**
         * Uses the HttpServletRequest to set most CGI variables
         *
         * @param  req   HttpServletRequest for information provided by
         *               the Servlet API
         * @throws UnsupportedEncodingException Unknown encoding
         */
        protected void setupFromRequest(HttpServletRequest req)
                throws UnsupportedEncodingException {

            boolean isIncluded = false;

            // Look to see if this request is an include
            if (req.getAttribute(
                    RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
                isIncluded = true;
            }
            if (isIncluded) {
                this.contextPath = (String) req.getAttribute(
                        RequestDispatcher.INCLUDE_CONTEXT_PATH);
                this.servletPath = (String) req.getAttribute(
                        RequestDispatcher.INCLUDE_SERVLET_PATH);
                this.pathInfo = (String) req.getAttribute(
                        RequestDispatcher.INCLUDE_PATH_INFO);
            } else {
                this.contextPath = req.getContextPath();
                this.servletPath = req.getServletPath();
                this.pathInfo = req.getPathInfo();
            }
            // If getPathInfo() returns null, must be using extension mapping
            // In this case, pathInfo should be same as servletPath
            if (this.pathInfo == null) {
                this.pathInfo = this.servletPath;
            }

            // If the request method is GET, POST or HEAD and the query string
            // does not contain an unencoded "=" this is an indexed query.
            // The parsed query string becomes the command line parameters
            // for the cgi command.
            if (enableCmdLineArguments && (req.getMethod().equals("GET")
                || req.getMethod().equals("POST") || req.getMethod().equals("HEAD"))) {
                String qs;
                if (isIncluded) {
                    qs = (String) req.getAttribute(
                            RequestDispatcher.INCLUDE_QUERY_STRING);
                } else {
                    qs = req.getQueryString();
                }
                if (qs != null && qs.indexOf('=') == -1) {
                    StringTokenizer qsTokens = new StringTokenizer(qs, "+");
                    while ( qsTokens.hasMoreTokens() ) {
                        cmdLineParameters.add(URLDecoder.decode(qsTokens.nextToken(),
                                              parameterEncoding));
                    }
                }
            }
        }


        /**
         * Resolves core information about the cgi script.
         *
         * <p>
         * Example URI:
         * </p>
         * <PRE> /servlet/cgigateway/dir1/realCGIscript/pathinfo1 </PRE>
         * <ul>
         * <LI><b>path</b> = $CATALINA_HOME/mywebapp/dir1/realCGIscript
         * <LI><b>scriptName</b> = /servlet/cgigateway/dir1/realCGIscript
         * <LI><b>cgiName</b> = /dir1/realCGIscript
         * <LI><b>name</b> = realCGIscript
         * </ul>
         * <p>
         * CGI search algorithm: search the real path below
         *    &lt;my-webapp-root&gt; and find the first non-directory in
         *    the getPathTranslated("/"), reading/searching from left-to-right.
         *</p>
         *<p>
         *   The CGI search path will start at
         *   webAppRootDir + File.separator + cgiPathPrefix
         *   (or webAppRootDir alone if cgiPathPrefix is
         *   null).
         *</p>
         *<p>
         *   cgiPathPrefix is defined by setting
         *   this servlet's cgiPathPrefix init parameter
         *
         *</p>
         *
         * @param pathInfo       String from HttpServletRequest.getPathInfo()
         * @param webAppRootDir  String from context.getRealPath("/")
         * @param contextPath    String as from
         *                       HttpServletRequest.getContextPath()
         * @param servletPath    String as from
         *                       HttpServletRequest.getServletPath()
         * @param cgiPathPrefix  subdirectory of webAppRootDir below which
         *                       the web app's CGIs may be stored; can be null.
         *                       The CGI search path will start at
         *                       webAppRootDir + File.separator + cgiPathPrefix
         *                       (or webAppRootDir alone if cgiPathPrefix is
         *                       null).  cgiPathPrefix is defined by setting
         *                       the servlet's cgiPathPrefix init parameter.
         *
         *
         * @return
         * <ul>
         * <li>
         * <code>path</code> -    full file-system path to valid cgi script,
         *                        or null if no cgi was found
         * <li>
         * <code>scriptName</code> -
         *                        CGI variable SCRIPT_NAME; the full URL path
         *                        to valid cgi script or null if no cgi was
         *                        found
         * <li>
         * <code>cgiName</code> - servlet pathInfo fragment corresponding to
         *                        the cgi script itself, or null if not found
         * <li>
         * <code>name</code> -    simple name (no directories) of the
         *                        cgi script, or null if no cgi was found
         * </ul>
         */
        protected String[] findCGI(String pathInfo, String webAppRootDir,
                                   String contextPath, String servletPath,
                                   String cgiPathPrefix) {
            String path = null;
            String name = null;
            String scriptname = null;

            if (webAppRootDir != null &&
                    webAppRootDir.lastIndexOf(File.separator) == (webAppRootDir.length() - 1)) {
                //strip the trailing "/" from the webAppRootDir
                webAppRootDir = webAppRootDir.substring(0, (webAppRootDir.length() - 1));
            }

            if (cgiPathPrefix != null) {
                webAppRootDir = webAppRootDir + File.separator + cgiPathPrefix;
            }

            if (log.isDebugEnabled()) {
                log.debug(sm.getString("cgiServlet.find.path", pathInfo, webAppRootDir));
            }

            File currentLocation = new File(webAppRootDir);
            StringTokenizer dirWalker = new StringTokenizer(pathInfo, "/");
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("cgiServlet.find.location",
                        currentLocation.getAbsolutePath()));
            }
            StringBuilder cginameBuilder = new StringBuilder();
            while (!currentLocation.isFile() && dirWalker.hasMoreElements()) {
                String nextElement = (String) dirWalker.nextElement();
                currentLocation = new File(currentLocation, nextElement);
                cginameBuilder.append('/').append(nextElement);
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("cgiServlet.find.location",
                            currentLocation.getAbsolutePath()));
                }
            }
            String cginame = cginameBuilder.toString();
            if (!currentLocation.isFile()) {
                return new String[] { null, null, null, null };
            }

            path = currentLocation.getAbsolutePath();
            name = currentLocation.getName();

            if (servletPath.startsWith(cginame)) {
                scriptname = contextPath + cginame;
            } else {
                scriptname = contextPath + servletPath + cginame;
            }

            if (log.isDebugEnabled()) {
                log.debug(sm.getString("cgiServlet.find.found", name, path, scriptname, cginame));
            }
            return new String[] { path, scriptname, cginame, name };
        }

        /**
         * Constructs the CGI environment to be supplied to the invoked CGI
         * script; relies heavily on Servlet API methods and findCGI
         *
         * @param    req request associated with the CGI
         *           Invocation
         *
         * @return   true if environment was set OK, false if there
         *           was a problem and no environment was set
         * @throws IOException an IO error occurred
         */
        protected boolean setCGIEnvironment(HttpServletRequest req) throws IOException {

            /*
             * This method is slightly ugly; c'est la vie.
             * "You cannot stop [ugliness], you can only hope to contain [it]"
             * (apologies to Marv Albert regarding MJ)
             */

            Hashtable<String,String> envp = new Hashtable<String,String>();

            // Add the shell environment variables (if any)
            envp.putAll(shellEnv);

            // Add the CGI environment variables
            String sPathInfoOrig = null;
            String sPathInfoCGI = null;
            String sPathTranslatedCGI = null;
            String sCGIFullPath = null;
            String sCGIScriptName = null;
            String sCGIFullName = null;
            String sCGIName = null;
            String[] sCGINames;


            sPathInfoOrig = this.pathInfo;
            sPathInfoOrig = sPathInfoOrig == null ? "" : sPathInfoOrig;

            if (webAppRootDir == null ) {
                // The app has not been deployed in exploded form
                webAppRootDir = tmpDir.toString();
                expandCGIScript();
            }

            sCGINames = findCGI(sPathInfoOrig,
                                webAppRootDir,
                                contextPath,
                                servletPath,
                                cgiPathPrefix);

            sCGIFullPath = sCGINames[0];
            sCGIScriptName = sCGINames[1];
            sCGIFullName = sCGINames[2];
            sCGIName = sCGINames[3];

            if (sCGIFullPath == null
                || sCGIScriptName == null
                || sCGIFullName == null
                || sCGIName == null) {
                return false;
            }

            envp.put("SERVER_SOFTWARE", "TOMCAT");

            envp.put("SERVER_NAME", nullsToBlanks(req.getServerName()));

            envp.put("GATEWAY_INTERFACE", "CGI/1.1");

            envp.put("SERVER_PROTOCOL", nullsToBlanks(req.getProtocol()));

            int port = req.getServerPort();
            Integer iPort =
                (port == 0 ? Integer.valueOf(-1) : Integer.valueOf(port));
            envp.put("SERVER_PORT", iPort.toString());

            envp.put("REQUEST_METHOD", nullsToBlanks(req.getMethod()));

            envp.put("REQUEST_URI", nullsToBlanks(req.getRequestURI()));


            /*-
             * PATH_INFO should be determined by using sCGIFullName:
             * 1) Let sCGIFullName not end in a "/" (see method findCGI)
             * 2) Let sCGIFullName equal the pathInfo fragment which
             *    corresponds to the actual cgi script.
             * 3) Thus, PATH_INFO = request.getPathInfo().substring(
             *                      sCGIFullName.length())
             *
             * (see method findCGI, where the real work is done)
             *
             */
            if (pathInfo == null
                || (pathInfo.substring(sCGIFullName.length()).length() <= 0)) {
                sPathInfoCGI = "";
            } else {
                sPathInfoCGI = pathInfo.substring(sCGIFullName.length());
            }
            envp.put("PATH_INFO", sPathInfoCGI);


            /*-
             * PATH_TRANSLATED must be determined after PATH_INFO (and the
             * implied real cgi-script) has been taken into account.
             *
             * The following example demonstrates:
             *
             * servlet info   = /servlet/cgigw/dir1/dir2/cgi1/trans1/trans2
             * cgifullpath    = /servlet/cgigw/dir1/dir2/cgi1
             * path_info      = /trans1/trans2
             * webAppRootDir  = servletContext.getRealPath("/")
             *
             * path_translated = servletContext.getRealPath("/trans1/trans2")
             *
             * That is, PATH_TRANSLATED = webAppRootDir + sPathInfoCGI
             * (unless sPathInfoCGI is null or blank, then the CGI
             * specification dictates that the PATH_TRANSLATED metavariable
             * SHOULD NOT be defined.
             *
             */
            if (!("".equals(sPathInfoCGI))) {
                sPathTranslatedCGI = context.getRealPath(sPathInfoCGI);
            }
            if (sPathTranslatedCGI == null || "".equals(sPathTranslatedCGI)) {
                //NOOP
            } else {
                envp.put("PATH_TRANSLATED", nullsToBlanks(sPathTranslatedCGI));
            }


            envp.put("SCRIPT_NAME", nullsToBlanks(sCGIScriptName));

            envp.put("QUERY_STRING", nullsToBlanks(req.getQueryString()));

            envp.put("REMOTE_HOST", nullsToBlanks(req.getRemoteHost()));

            envp.put("REMOTE_ADDR", nullsToBlanks(req.getRemoteAddr()));

            envp.put("AUTH_TYPE", nullsToBlanks(req.getAuthType()));

            envp.put("REMOTE_USER", nullsToBlanks(req.getRemoteUser()));

            envp.put("REMOTE_IDENT", ""); //not necessary for full compliance

            envp.put("CONTENT_TYPE", nullsToBlanks(req.getContentType()));


            /* Note CGI spec says CONTENT_LENGTH must be NULL ("") or undefined
             * if there is no content, so we cannot put 0 or -1 in as per the
             * Servlet API spec.
             */
            int contentLength = req.getContentLength();
            String sContentLength = (contentLength <= 0 ? "" :
                Integer.toString(contentLength));
            envp.put("CONTENT_LENGTH", sContentLength);


            Enumeration<String> headers = req.getHeaderNames();
            String header = null;
            while (headers.hasMoreElements()) {
                header = null;
                header = headers.nextElement().toUpperCase(Locale.ENGLISH);
                //REMIND: rewrite multiple headers as if received as single
                //REMIND: change character set
                //REMIND: I forgot what the previous REMIND means
                if (envHttpHeadersPattern.matcher(header).matches()) {
                    envp.put("HTTP_" + header.replace('-', '_'), req.getHeader(header));
                }
            }

            File fCGIFullPath = new File(sCGIFullPath);
            command = fCGIFullPath.getCanonicalPath();

            envp.put("X_TOMCAT_SCRIPT_PATH", command);  //for kicks

            envp.put("SCRIPT_FILENAME", command);  //for PHP

            this.env = envp;

            return true;

        }

        /**
         * Extracts requested resource from web app archive to context work
         * directory to enable CGI script to be executed.
         */
        protected void expandCGIScript() {
            StringBuilder srcPath = new StringBuilder();
            StringBuilder destPath = new StringBuilder();
            InputStream is = null;

            // paths depend on mapping
            if (cgiPathPrefix == null ) {
                srcPath.append(pathInfo);
                is = context.getResourceAsStream(srcPath.toString());
                destPath.append(tmpDir);
                destPath.append(pathInfo);
            } else {
                // essentially same search algorithm as findCGI()
                srcPath.append(cgiPathPrefix);
                StringTokenizer pathWalker =
                        new StringTokenizer (pathInfo, "/");
                // start with first element
                while (pathWalker.hasMoreElements() && (is == null)) {
                    srcPath.append("/");
                    srcPath.append(pathWalker.nextElement());
                    is = context.getResourceAsStream(srcPath.toString());
                }
                destPath.append(tmpDir);
                destPath.append("/");
                destPath.append(srcPath);
            }

            if (is == null) {
                // didn't find anything, give up now
                log.warn(sm.getString("cgiServlet.expandNotFound", srcPath));
                return;
            }

            File f = new File(destPath.toString());
            if (f.exists()) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.warn(sm.getString("cgiServlet.expandCloseFail", srcPath), e);
                }
                // Don't need to expand if it already exists
                return;
            }

            // create directories
            File dir = f.getParentFile();
            if (!dir.mkdirs() && !dir.isDirectory()) {
                log.warn(sm.getString("cgiServlet.expandCreateDirFail", dir.getAbsolutePath()));
                return;
            }

            try {
                synchronized (expandFileLock) {
                    // make sure file doesn't exist
                    if (f.exists()) {
                        return;
                    }

                    // create file
                    if (!f.createNewFile()) {
                        return;
                    }
                    FileOutputStream fos = new FileOutputStream(f);

                    try {
                        // copy data
                        IOTools.flow(is, fos);
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            log.warn(sm.getString("cgiServlet.expandError"), e);
                        }
                        fos.close();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug(sm.getString("cgiServlet.expandOk", srcPath, destPath));
                    }
                }
            } catch (IOException ioe) {
                log.warn(sm.getString("cgiServlet.expandFail", srcPath, destPath), ioe);
                // delete in case file is corrupted
                if (f.exists()) {
                    if (!f.delete()) {
                        log.warn(sm.getString("cgiServlet.expandDeleteFail", f.getAbsolutePath()));
                    }
                }
            }
        }


        /**
         * Returns important CGI environment information in a multi-line text
         * format.
         *
         * @return CGI environment info
         */
        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();

            sb.append("CGIEnvironment Info:");
            sb.append(LINE_SEP);

            if (isValid()) {
                sb.append("Validity: [true]");
                sb.append(LINE_SEP);

                sb.append("Environment values:");
                sb.append(LINE_SEP);
                for (Entry<String,String> entry : env.entrySet()) {
                    sb.append("  ");
                    sb.append(entry.getKey());
                    sb.append(": [");
                    sb.append(blanksToString(entry.getValue(), "will be set to blank"));
                    sb.append("]");
                    sb.append(LINE_SEP);
                }

                sb.append("Derived Command :[");
                sb.append(nullsToBlanks(command));
                sb.append("]");
                sb.append(LINE_SEP);


                sb.append("Working Directory: [");
                if (workingDirectory != null) {
                    sb.append(workingDirectory.toString());
                }
                sb.append("]");
                sb.append(LINE_SEP);

                sb.append("Command Line Params:");
                sb.append(LINE_SEP);
                for (String param : cmdLineParameters) {
                    sb.append("  [");
                    sb.append(param);
                    sb.append("]");
                    sb.append(LINE_SEP);
                }
            } else {
                sb.append("Validity: [false]");
                sb.append(LINE_SEP);
                sb.append("CGI script not found or not specified.");
                sb.append(LINE_SEP);
                sb.append("Check the HttpServletRequest pathInfo property to see if it is what ");
                sb.append(LINE_SEP);
                sb.append("you meant it to be. You must specify an existant and executable file ");
                sb.append(LINE_SEP);
                sb.append("as part of the path-info.");
                sb.append(LINE_SEP);
            }

            return sb.toString();
        }


        /**
         * Gets derived command string
         *
         * @return  command string
         *
         */
        protected String getCommand() {
            return command;
        }


        /**
         * Gets derived CGI working directory
         *
         * @return  working directory
         *
         */
        protected File getWorkingDirectory() {
            return workingDirectory;
        }


        /**
         * Gets derived CGI environment
         *
         * @return   CGI environment
         *
         */
        protected Hashtable<String,String> getEnvironment() {
            return env;
        }


        /**
         * Gets derived CGI query parameters
         *
         * @return   CGI query parameters
         *
         */
        protected ArrayList<String> getParameters() {
            return cmdLineParameters;
        }


        /**
         * Gets validity status
         *
         * @return   true if this environment is valid, false
         *           otherwise
         *
         */
        protected boolean isValid() {
            return valid;
        }


        /**
         * Converts null strings to blank strings ("")
         *
         * @param    s string to be converted if necessary
         * @return   a non-null string, either the original or the empty string
         *           ("") if the original was <code>null</code>
         */
        protected String nullsToBlanks(String s) {
            return nullsToString(s, "");
        }


        /**
         * Converts null strings to another string
         *
         * @param    couldBeNull string to be converted if necessary
         * @param    subForNulls string to return instead of a null string
         * @return   a non-null string, either the original or the substitute
         *           string if the original was <code>null</code>
         */
        protected String nullsToString(String couldBeNull,
                                       String subForNulls) {
            return (couldBeNull == null ? subForNulls : couldBeNull);
        }


        /**
         * Converts blank strings to another string
         *
         * @param    couldBeBlank string to be converted if necessary
         * @param    subForBlanks string to return instead of a blank string
         * @return   a non-null string, either the original or the substitute
         *           string if the original was <code>null</code> or empty ("")
         */
        protected String blanksToString(String couldBeBlank,
                                      String subForBlanks) {
            return (("".equals(couldBeBlank) || couldBeBlank == null)
                    ? subForBlanks
                    : couldBeBlank);
        }


    } //class CGIEnvironment


    /**
     * Encapsulates the knowledge of how to run a CGI script, given the
     * script's desired environment and (optionally) input/output streams
     *
     * <p>
     *
     * Exposes a <code>run</code> method used to actually invoke the
     * CGI.
     *
     * </p>
     * <p>
     *
     * The CGI environment and settings are derived from the information
     * passed to the constructor.
     *
     * </p>
     * <p>
     *
     * The input and output streams can be set by the <code>setInput</code>
     * and <code>setResponse</code> methods, respectively.
     * </p>
     */
    protected class CGIRunner {

        /** script/command to be executed */
        private final String command;

        /** environment used when invoking the cgi script */
        private final Hashtable<String,String> env;

        /** working directory used when invoking the cgi script */
        private final File wd;

        /** command line parameters to be passed to the invoked script */
        private final ArrayList<String> params;

        /** stdin to be passed to cgi script */
        private InputStream stdin = null;

        /** response object used to set headers & get output stream */
        private HttpServletResponse response = null;

        /** boolean tracking whether this object has enough info to run() */
        private boolean readyToRun = false;


        /**
         *  Creates a CGIRunner and initializes its environment, working
         *  directory, and query parameters.
         *  <BR>
         *  Input/output streams (optional) are set using the
         *  <code>setInput</code> and <code>setResponse</code> methods,
         *  respectively.
         *
         * @param  command  string full path to command to be executed
         * @param  env      Hashtable with the desired script environment
         * @param  wd       File with the script's desired working directory
         * @param  params   ArrayList with the script's query command line
         *                  parameters as strings
         */
        protected CGIRunner(String command, Hashtable<String,String> env,
                            File wd, ArrayList<String> params) {
            this.command = command;
            this.env = env;
            this.wd = wd;
            this.params = params;
            updateReadyStatus();
        }


        /**
         * Checks and sets ready status
         */
        protected void updateReadyStatus() {
            if (command != null
                && env != null
                && wd != null
                && params != null
                && response != null) {
                readyToRun = true;
            } else {
                readyToRun = false;
            }
        }


        /**
         * Gets ready status
         *
         * @return   false if not ready (<code>run</code> will throw
         *           an exception), true if ready
         */
        protected boolean isReady() {
            return readyToRun;
        }


        /**
         * Sets HttpServletResponse object used to set headers and send
         * output to
         *
         * @param  response   HttpServletResponse to be used
         *
         */
        protected void setResponse(HttpServletResponse response) {
            this.response = response;
            updateReadyStatus();
        }


        /**
         * Sets standard input to be passed on to the invoked cgi script
         *
         * @param  stdin   InputStream to be used
         *
         */
        protected void setInput(InputStream stdin) {
            this.stdin = stdin;
            updateReadyStatus();
        }


        /**
         * Converts a Hashtable to a String array by converting each
         * key/value pair in the Hashtable to a String in the form
         * "key=value" (hashkey + "=" + hash.get(hashkey).toString())
         *
         * @param  h   Hashtable to convert
         *
         * @return     converted string array
         *
         * @exception  NullPointerException   if a hash key has a null value
         *
         */
        protected String[] hashToStringArray(Hashtable<String,?> h)
            throws NullPointerException {
            Vector<String> v = new Vector<String>();
            Enumeration<String> e = h.keys();
            while (e.hasMoreElements()) {
                String k = e.nextElement();
                v.add(k + "=" + h.get(k).toString());
            }
            String[] strArr = new String[v.size()];
            v.copyInto(strArr);
            return strArr;
        }


        /**
         * Executes a CGI script with the desired environment, current working
         * directory, and input/output streams
         *
         * <p>
         * This implements the following CGI specification recommedations:
         * </p>
         * <UL>
         * <LI> Servers SHOULD provide the "<code>query</code>" component of
         *      the script-URI as command-line arguments to scripts if it
         *      does not contain any unencoded "=" characters and the
         *      command-line arguments can be generated in an unambiguous
         *      manner.
         * <LI> Servers SHOULD set the AUTH_TYPE metavariable to the value
         *      of the "<code>auth-scheme</code>" token of the
         *      "<code>Authorization</code>" if it was supplied as part of the
         *      request header.  See <code>getCGIEnvironment</code> method.
         * <LI> Where applicable, servers SHOULD set the current working
         *      directory to the directory in which the script is located
         *      before invoking it.
         * <LI> Server implementations SHOULD define their behavior for the
         *      following cases:
         *     <ul>
         *     <LI> <u>Allowed characters in pathInfo</u>:  This implementation
         *             does not allow ASCII NUL nor any character which cannot
         *             be URL-encoded according to internet standards;
         *     <LI> <u>Allowed characters in path segments</u>: This
         *             implementation does not allow non-terminal NULL
         *             segments in the the path -- IOExceptions may be thrown;
         *     <LI> <u>"<code>.</code>" and "<code>..</code>" path
         *             segments</u>:
         *             This implementation does not allow "<code>.</code>" and
         *             "<code>..</code>" in the the path, and such characters
         *             will result in an IOException being thrown (this should
         *             never happen since Tomcat normalises the requestURI
         *             before determining the contextPath, servletPath and
         *             pathInfo);
         *     <LI> <u>Implementation limitations</u>: This implementation
         *             does not impose any limitations except as documented
         *             above.  This implementation may be limited by the
         *             servlet container used to house this implementation.
         *             In particular, all the primary CGI variable values
         *             are derived either directly or indirectly from the
         *             container's implementation of the Servlet API methods.
         *     </ul>
         * </UL>
         *
         * @exception IOException if problems during reading/writing occur
         *
         * @see    java.lang.Runtime#exec(String command, String[] envp,
         *                                File dir)
         */
        protected void run() throws IOException {

            /*
             * REMIND:  this method feels too big; should it be re-written?
             */

            if (!isReady()) {
                throw new IOException(this.getClass().getName() + ": not ready to run.");
            }

            if (log.isDebugEnabled()) {
                log.debug("envp: [" + env + "], command: [" + command + "]");
            }

            if ((command.indexOf(File.separator + "." + File.separator) >= 0)
                || (command.indexOf(File.separator + "..") >= 0)
                || (command.indexOf(".." + File.separator) >= 0)) {
                throw new IOException(this.getClass().getName()
                                      + "Illegal Character in CGI command "
                                      + "path ('.' or '..') detected.  Not "
                                      + "running CGI [" + command + "].");
            }

            /* original content/structure of this section taken from
             * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4216884
             * with major modifications by Martin Dengler
             */
            Runtime rt = null;
            BufferedReader cgiHeaderReader = null;
            InputStream cgiOutput = null;
            BufferedReader commandsStdErr = null;
            Thread errReaderThread = null;
            BufferedOutputStream commandsStdIn = null;
            Process proc = null;
            int bufRead = -1;

            List<String> cmdAndArgs = new ArrayList<String>();
            if (cgiExecutable.length() != 0) {
                cmdAndArgs.add(cgiExecutable);
            }
            if (cgiExecutableArgs != null) {
                cmdAndArgs.addAll(cgiExecutableArgs);
            }
            cmdAndArgs.add(command);
            cmdAndArgs.addAll(params);

            try {
                rt = Runtime.getRuntime();
                proc = rt.exec(
                        cmdAndArgs.toArray(new String[cmdAndArgs.size()]),
                        hashToStringArray(env), wd);

                String sContentLength = env.get("CONTENT_LENGTH");

                if(!"".equals(sContentLength)) {
                    commandsStdIn = new BufferedOutputStream(proc.getOutputStream());
                    IOTools.flow(stdin, commandsStdIn);
                    commandsStdIn.flush();
                    commandsStdIn.close();
                }

                /* we want to wait for the process to exit,  Process.waitFor()
                 * is useless in our situation; see
                 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4223650
                 */

                boolean isRunning = true;
                commandsStdErr = new BufferedReader
                    (new InputStreamReader(proc.getErrorStream()));
                final BufferedReader stdErrRdr = commandsStdErr ;

                errReaderThread = new Thread() {
                    @Override
                    public void run () {
                        sendToLog(stdErrRdr);
                    }
                };
                errReaderThread.start();

                InputStream cgiHeaderStream =
                    new HTTPHeaderInputStream(proc.getInputStream());
                cgiHeaderReader =
                    new BufferedReader(new InputStreamReader(cgiHeaderStream));

                // Need to be careful here. If sendError() is called the
                // response body should be provided by the standard error page
                // process. But, if the output of the CGI process isn't read
                // then that process can hang.
                boolean skipBody = false;

                while (isRunning) {
                    try {
                        //set headers
                        String line = null;
                        while (((line = cgiHeaderReader.readLine()) != null) && !("".equals(line))) {
                            if (log.isTraceEnabled()) {
                                log.trace("addHeader(\"" + line + "\")");
                            }
                            if (line.startsWith("HTTP")) {
                                skipBody = setStatus(response, getSCFromHttpStatusLine(line));
                            } else if (line.indexOf(':') >= 0) {
                                String header =
                                    line.substring(0, line.indexOf(':')).trim();
                                String value =
                                    line.substring(line.indexOf(':') + 1).trim();
                                if (header.equalsIgnoreCase("status")) {
                                    skipBody = setStatus(response, getSCFromCGIStatusHeader(value));
                                } else {
                                    response.addHeader(header , value);
                                }
                            } else {
                                log.info(sm.getString("cgiServlet.runBadHeader", line));
                            }
                        }

                        //write output
                        byte[] bBuf = new byte[2048];

                        OutputStream out = response.getOutputStream();
                        cgiOutput = proc.getInputStream();

                        try {
                            while (!skipBody && (bufRead = cgiOutput.read(bBuf)) != -1) {
                                if (log.isTraceEnabled()) {
                                    log.trace("output " + bufRead + " bytes of data");
                                }
                                out.write(bBuf, 0, bufRead);
                            }
                        } finally {
                            // Attempt to consume any leftover byte if something bad happens,
                            // such as a socket disconnect on the servlet side; otherwise, the
                            // external process could hang
                            if (bufRead != -1) {
                                while ((bufRead = cgiOutput.read(bBuf)) != -1) {
                                    // NOOP - just read the data
                                }
                            }
                        }

                        proc.exitValue(); // Throws exception if alive

                        isRunning = false;

                    } catch (IllegalThreadStateException e) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ignored) {
                            // Ignore
                        }
                    }
                } //replacement for Process.waitFor()

            } catch (IOException e){
                log.warn(sm.getString("cgiServlet.runFail"), e);
                throw e;
            } finally {
                // Close the header reader
                if (cgiHeaderReader != null) {
                    try {
                        cgiHeaderReader.close();
                    } catch (IOException ioe) {
                        log.warn(sm.getString("cgiServlet.runHeaderReaderFail"), ioe);
                    }
                }
                // Close the output stream if used
                if (cgiOutput != null) {
                    try {
                        cgiOutput.close();
                    } catch (IOException ioe) {
                        log.warn(sm.getString("cgiServlet.runOutputStreamFail"), ioe);
                    }
                }
                // Make sure the error stream reader has finished
                if (errReaderThread != null) {
                    try {
                        errReaderThread.join(stderrTimeout);
                    } catch (InterruptedException e) {
                        log.warn(sm.getString("cgiServlet.runReaderInterrupt"));                    }
                }
                if (proc != null){
                    proc.destroy();
                    proc = null;
                }
            }
        }

        /**
         * Parses the Status-Line and extracts the status code.
         *
         * @param line The HTTP Status-Line (RFC2616, section 6.1)
         * @return The extracted status code or the code representing an
         * internal error if a valid status code cannot be extracted.
         */
        private int getSCFromHttpStatusLine(String line) {
            int statusStart = line.indexOf(' ') + 1;

            if (statusStart < 1 || line.length() < statusStart + 3) {
                // Not a valid HTTP Status-Line
                log.warn(sm.getString("cgiServlet.runInvalidStatus", line));
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }

            String status = line.substring(statusStart, statusStart + 3);

            int statusCode;
            try {
                statusCode = Integer.parseInt(status);
            } catch (NumberFormatException nfe) {
                // Not a valid status code
                log.warn(sm.getString("cgiServlet.runInvalidStatus", status));
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }

            return statusCode;
        }

        /**
         * Parses the CGI Status Header value and extracts the status code.
         *
         * @param value The CGI Status value of the form <code>
         *             digit digit digit SP reason-phrase</code>
         * @return The extracted status code or the code representing an
         * internal error if a valid status code cannot be extracted.
         */
        private int getSCFromCGIStatusHeader(String value) {
            if (value.length() < 3) {
                // Not a valid status value
                log.warn(sm.getString("cgiServlet.runInvalidStatus", value));
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }

            String status = value.substring(0, 3);

            int statusCode;
            try {
                statusCode = Integer.parseInt(status);
            } catch (NumberFormatException nfe) {
                // Not a valid status code
                log.warn(sm.getString("cgiServlet.runInvalidStatus", status));
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }

            return statusCode;
        }

        private void sendToLog(BufferedReader rdr) {
            String line = null;
            int lineCount = 0 ;
            try {
                while ((line = rdr.readLine()) != null) {
                    log.warn(sm.getString("cgiServlet.runStdErr", line));
                    lineCount++ ;
                }
            } catch (IOException e) {
                log.warn(sm.getString("cgiServlet.runStdErrFail"), e);
            } finally {
                try {
                    rdr.close();
                } catch (IOException e) {
                    log.warn(sm.getString("cgiServlet.runStdErrFail"), e);
                }
            }
            if (lineCount > 0) {
                log.warn(sm.getString("cgiServlet.runStdErrCount", Integer.valueOf(lineCount)));
            }
        }
    } //class CGIRunner

    /**
     * This is an input stream specifically for reading HTTP headers. It reads
     * upto and including the two blank lines terminating the headers. It
     * allows the content to be read using bytes or characters as appropriate.
     */
    protected static class HTTPHeaderInputStream extends InputStream {
        private static final int STATE_CHARACTER = 0;
        private static final int STATE_FIRST_CR = 1;
        private static final int STATE_FIRST_LF = 2;
        private static final int STATE_SECOND_CR = 3;
        private static final int STATE_HEADER_END = 4;

        private final InputStream input;
        private int state;

        HTTPHeaderInputStream(InputStream theInput) {
            input = theInput;
            state = STATE_CHARACTER;
        }

        /**
         * @see java.io.InputStream#read()
         */
        @Override
        public int read() throws IOException {
            if (state == STATE_HEADER_END) {
                return -1;
            }

            int i = input.read();

            // Update the state
            // State machine looks like this
            //
            //    -------->--------
            //   |      (CR)       |
            //   |                 |
            //  CR1--->---         |
            //   |        |        |
            //   ^(CR)    |(LF)    |
            //   |        |        |
            // CHAR--->--LF1--->--EOH
            //      (LF)  |  (LF)  |
            //            |(CR)    ^(LF)
            //            |        |
            //          (CR2)-->---

            if (i == 10) {
                // LF
                switch(state) {
                    case STATE_CHARACTER:
                        state = STATE_FIRST_LF;
                        break;
                    case STATE_FIRST_CR:
                        state = STATE_FIRST_LF;
                        break;
                    case STATE_FIRST_LF:
                    case STATE_SECOND_CR:
                        state = STATE_HEADER_END;
                        break;
                }

            } else if (i == 13) {
                // CR
                switch(state) {
                    case STATE_CHARACTER:
                        state = STATE_FIRST_CR;
                        break;
                    case STATE_FIRST_CR:
                        state = STATE_HEADER_END;
                        break;
                    case STATE_FIRST_LF:
                        state = STATE_SECOND_CR;
                        break;
                }

            } else {
                state = STATE_CHARACTER;
            }

            return i;
        }
    }  // class HTTPHeaderInputStream

} //class CGIServlet
