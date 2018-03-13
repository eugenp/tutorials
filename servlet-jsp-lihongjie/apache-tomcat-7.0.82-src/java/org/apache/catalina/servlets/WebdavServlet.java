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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;
import java.util.TimeZone;
import java.util.Vector;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.util.ConcurrentDateFormat;
import org.apache.catalina.util.DOMWriter;
import org.apache.catalina.util.URLEncoder;
import org.apache.catalina.util.XMLWriter;
import org.apache.naming.resources.CacheEntry;
import org.apache.naming.resources.Resource;
import org.apache.naming.resources.ResourceAttributes;
import org.apache.tomcat.util.http.FastHttpDateFormat;
import org.apache.tomcat.util.http.RequestUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



/**
 * Servlet which adds support for WebDAV level 2. All the basic HTTP requests
 * are handled by the DefaultServlet. The WebDAVServlet must not be used as the
 * default servlet (ie mapped to '/') as it will not work in this configuration.
 * <p/>
 * Mapping a subpath (e.g. <code>/webdav/*</code> to this servlet has the effect
 * of re-mounting the entire web application under that sub-path, with WebDAV
 * access to all the resources. This <code>WEB-INF</code> and <code>META-INF</code>
 * directories are protected in this re-mounted resource tree.
 * <p/>
 * To enable WebDAV for a context add the following to web.xml:
 * <pre>
 * &lt;servlet&gt;
 *  &lt;servlet-name&gt;webdav&lt;/servlet-name&gt;
 *  &lt;servlet-class&gt;org.apache.catalina.servlets.WebdavServlet&lt;/servlet-class&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;debug&lt;/param-name&gt;
 *      &lt;param-value&gt;0&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;listings&lt;/param-name&gt;
 *      &lt;param-value&gt;false&lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *  &lt;/servlet&gt;
 *  &lt;servlet-mapping&gt;
 *    &lt;servlet-name&gt;webdav&lt;/servlet-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *  &lt;/servlet-mapping&gt;
 * </pre>
 * This will enable read only access. To enable read-write access add:
 * <pre>
 *  &lt;init-param&gt;
 *    &lt;param-name&gt;readonly&lt;/param-name&gt;
 *    &lt;param-value&gt;false&lt;/param-value&gt;
 *  &lt;/init-param&gt;
 * </pre>
 * To make the content editable via a different URL, use the following
 * mapping:
 * <pre>
 *  &lt;servlet-mapping&gt;
 *    &lt;servlet-name&gt;webdav&lt;/servlet-name&gt;
 *    &lt;url-pattern&gt;/webdavedit/*&lt;/url-pattern&gt;
 *  &lt;/servlet-mapping&gt;
 * </pre>
 * By default access to /WEB-INF and META-INF are not available via WebDAV. To
 * enable access to these URLs, use add:
 * <pre>
 *  &lt;init-param&gt;
 *    &lt;param-name&gt;allowSpecialPaths&lt;/param-name&gt;
 *    &lt;param-value&gt;true&lt;/param-value&gt;
 *  &lt;/init-param&gt;
 * </pre>
 * Don't forget to secure access appropriately to the editing URLs, especially
 * if allowSpecialPaths is used. With the mapping configuration above, the
 * context will be accessible to normal users as before. Those users with the
 * necessary access will be able to edit content available via
 * http://host:port/context/content using
 * http://host:port/context/webdavedit/content
 *
 * @author Remy Maucherat
 */
public class WebdavServlet extends DefaultServlet {

    private static final long serialVersionUID = 1L;


    // -------------------------------------------------------------- Constants

    private static final URLEncoder URL_ENCODER_XML;
    static {
        URL_ENCODER_XML = (URLEncoder) URLEncoder.DEFAULT.clone();
        // Remove '&' from the safe character set since while it it permitted
        // in a URI path, it is not permitted in XML and encoding it is a simple
        // way to address this.
        URL_ENCODER_XML.removeSafeCharacter('&');
    }

    private static final String METHOD_PROPFIND = "PROPFIND";
    private static final String METHOD_PROPPATCH = "PROPPATCH";
    private static final String METHOD_MKCOL = "MKCOL";
    private static final String METHOD_COPY = "COPY";
    private static final String METHOD_MOVE = "MOVE";
    private static final String METHOD_LOCK = "LOCK";
    private static final String METHOD_UNLOCK = "UNLOCK";


    /**
     * PROPFIND - Specify a property mask.
     */
    private static final int FIND_BY_PROPERTY = 0;


    /**
     * PROPFIND - Display all properties.
     */
    private static final int FIND_ALL_PROP = 1;


    /**
     * PROPFIND - Return property names.
     */
    private static final int FIND_PROPERTY_NAMES = 2;


    /**
     * Create a new lock.
     */
    private static final int LOCK_CREATION = 0;


    /**
     * Refresh lock.
     */
    private static final int LOCK_REFRESH = 1;


    /**
     * Default lock timeout value.
     */
    private static final int DEFAULT_TIMEOUT = 3600;


    /**
     * Maximum lock timeout.
     */
    private static final int MAX_TIMEOUT = 604800;


    /**
     * Default namespace.
     */
    protected static final String DEFAULT_NAMESPACE = "DAV:";


    /**
     * Simple date format for the creation date ISO representation (partial).
     */
    protected static final ConcurrentDateFormat creationDateFormat =
        new ConcurrentDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US,
                TimeZone.getTimeZone("GMT"));


     /**
     * MD5 message digest provider.
     */
    protected static MessageDigest md5Helper;


    /**
     * The MD5 helper object for this class.
     *
     * @deprecated  Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected static final MD5Encoder md5Encoder = new MD5Encoder();


    // ----------------------------------------------------- Instance Variables

    /**
     * Repository of the locks put on single resources.
     * <p>
     * Key : path <br>
     * Value : LockInfo
     */
    private Hashtable<String,LockInfo> resourceLocks =
        new Hashtable<String,LockInfo>();


    /**
     * Repository of the lock-null resources.
     * <p>
     * Key : path of the collection containing the lock-null resource<br>
     * Value : Vector of lock-null resource which are members of the
     * collection. Each element of the Vector is the path associated with
     * the lock-null resource.
     */
    private Hashtable<String,Vector<String>> lockNullResources =
        new Hashtable<String,Vector<String>>();


    /**
     * Vector of the heritable locks.
     * <p>
     * Key : path <br>
     * Value : LockInfo
     */
    private Vector<LockInfo> collectionLocks = new Vector<LockInfo>();


    /**
     * Secret information used to generate reasonably secure lock ids.
     */
    private String secret = "catalina";


    /**
     * Default depth in spec is infinite. Limit depth to 3 by default as
     * infinite depth makes operations very expensive.
     */
    private int maxDepth = 3;


    /**
     * Is access allowed via WebDAV to the special paths (/WEB-INF and
     * /META-INF)?
     */
    private boolean allowSpecialPaths = false;


    // --------------------------------------------------------- Public Methods


    /**
     * Initialize this servlet.
     */
    @Override
    public void init()
        throws ServletException {

        super.init();

        if (getServletConfig().getInitParameter("secret") != null)
            secret = getServletConfig().getInitParameter("secret");

        if (getServletConfig().getInitParameter("maxDepth") != null)
            maxDepth = Integer.parseInt(
                    getServletConfig().getInitParameter("maxDepth"));

        if (getServletConfig().getInitParameter("allowSpecialPaths") != null)
            allowSpecialPaths = Boolean.parseBoolean(
                    getServletConfig().getInitParameter("allowSpecialPaths"));

        // Load the MD5 helper used to calculate signatures.
        try {
            md5Helper = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new UnavailableException("No MD5");
        }

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Return JAXP document builder instance.
     */
    protected DocumentBuilder getDocumentBuilder()
        throws ServletException {
        DocumentBuilder documentBuilder = null;
        DocumentBuilderFactory documentBuilderFactory = null;
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setExpandEntityReferences(false);
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setEntityResolver(
                    new WebdavResolver(this.getServletContext()));
        } catch(ParserConfigurationException e) {
            throw new ServletException
                (sm.getString("webdavservlet.jaxpfailed"));
        }
        return documentBuilder;
    }


    /**
     * Handles the special WebDAV methods.
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        final String path = getRelativePath(req);

        // Error page check needs to come before special path check since
        // custom error pages are often located below WEB-INF so they are
        // not directly accessible.
        if (req.getDispatcherType() == DispatcherType.ERROR) {
            doGet(req, resp);
            return;
        }

        // Block access to special subdirectories.
        // DefaultServlet assumes it services resources from the root of the web app
        // and doesn't add any special path protection
        // WebdavServlet remounts the webapp under a new path, so this check is
        // necessary on all methods (including GET).
        if (isSpecialPath(path)) {
            resp.sendError(WebdavStatus.SC_NOT_FOUND);
            return;
        }

        final String method = req.getMethod();

        if (debug > 0) {
            log("[" + method + "] " + path);
        }

        if (method.equals(METHOD_PROPFIND)) {
            doPropfind(req, resp);
        } else if (method.equals(METHOD_PROPPATCH)) {
            doProppatch(req, resp);
        } else if (method.equals(METHOD_MKCOL)) {
            doMkcol(req, resp);
        } else if (method.equals(METHOD_COPY)) {
            doCopy(req, resp);
        } else if (method.equals(METHOD_MOVE)) {
            doMove(req, resp);
        } else if (method.equals(METHOD_LOCK)) {
            doLock(req, resp);
        } else if (method.equals(METHOD_UNLOCK)) {
            doUnlock(req, resp);
        } else {
            // DefaultServlet processing
            super.service(req, resp);
        }

    }


    /**
     * Checks whether a given path refers to a resource under
     * <code>WEB-INF</code> or <code>META-INF</code>.
     * @param path the full path of the resource being accessed
     * @return <code>true</code> if the resource specified is under a special path
     */
    private final boolean isSpecialPath(final String path) {
        return !allowSpecialPaths && (
                path.toUpperCase(Locale.ENGLISH).startsWith("/WEB-INF") ||
                path.toUpperCase(Locale.ENGLISH).startsWith("/META-INF"));
    }


    /**
     * Check if the conditions specified in the optional If headers are
     * satisfied.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param resourceAttributes The resource information
     * @return boolean true if the resource meets all the specified conditions,
     * and false if any of the conditions is not satisfied, in which case
     * request processing is stopped
     */
    @Override
    protected boolean checkIfHeaders(HttpServletRequest request,
                                     HttpServletResponse response,
                                     ResourceAttributes resourceAttributes)
        throws IOException {

        if (!super.checkIfHeaders(request, response, resourceAttributes))
            return false;

        // TODO : Checking the WebDAV If header
        return true;

    }


    /**
     * URL rewriter.
     *
     * @param path Path which has to be rewritten
     * @return the rewritten path
     */
    @Override
    protected String rewriteUrl(String path) {
        return URL_ENCODER_XML.encode(path, "UTF-8");
    }


    /**
     * Override the DefaultServlet implementation and only use the PathInfo. If
     * the ServletPath is non-null, it will be because the WebDAV servlet has
     * been mapped to a url other than /* to configure editing at different url
     * than normal viewing.
     *
     * @param request The servlet request we are processing
     */
    @Override
    protected String getRelativePath(HttpServletRequest request) {
        return getRelativePath(request, false);
    }

    @Override
    protected String getRelativePath(HttpServletRequest request, boolean allowEmptyPath) {
        String pathInfo;

        if (request.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
            // For includes, get the info from the attributes
            pathInfo = (String) request.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);
        } else {
            pathInfo = request.getPathInfo();
        }

        StringBuilder result = new StringBuilder();
        if (pathInfo != null) {
            result.append(pathInfo);
        }
        if (result.length() == 0) {
            result.append('/');
        }

        return result.toString();
    }


    /**
     * Determines the prefix for standard directory GET listings.
     */
    @Override
    protected String getPathPrefix(final HttpServletRequest request) {
        // Repeat the servlet path (e.g. /webdav/) in the listing path
        String contextPath = request.getContextPath();
        if (request.getServletPath() !=  null) {
            contextPath = contextPath + request.getServletPath();
        }
        return contextPath;
    }


    /**
     * OPTIONS Method.
     *
     * @param req The request
     * @param resp The response
     * @throws ServletException If an error occurs
     * @throws IOException If an IO error occurs
     */
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.addHeader("DAV", "1,2");

        StringBuilder methodsAllowed = determineMethodsAllowed(resources,
                                                              req);

        resp.addHeader("Allow", methodsAllowed.toString());
        resp.addHeader("MS-Author-Via", "DAV");

    }


    /**
     * PROPFIND Method.
     */
    protected void doPropfind(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (!listings) {
            // Get allowed methods
            StringBuilder methodsAllowed = determineMethodsAllowed(resources,
                                                                  req);

            resp.addHeader("Allow", methodsAllowed.toString());
            resp.sendError(WebdavStatus.SC_METHOD_NOT_ALLOWED);
            return;
        }

        String path = getRelativePath(req);
        if (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);

        // Properties which are to be displayed.
        Vector<String> properties = null;
        // Propfind depth
        int depth = maxDepth;
        // Propfind type
        int type = FIND_ALL_PROP;

        String depthStr = req.getHeader("Depth");

        if (depthStr == null) {
            depth = maxDepth;
        } else {
            if (depthStr.equals("0")) {
                depth = 0;
            } else if (depthStr.equals("1")) {
                depth = 1;
            } else if (depthStr.equals("infinity")) {
                depth = maxDepth;
            }
        }

        Node propNode = null;

        if (req.getContentLength() > 0) {
            DocumentBuilder documentBuilder = getDocumentBuilder();

            try {
                Document document = documentBuilder.parse
                    (new InputSource(req.getInputStream()));

                // Get the root element of the document
                Element rootElement = document.getDocumentElement();
                NodeList childList = rootElement.getChildNodes();

                for (int i=0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        break;
                    case Node.ELEMENT_NODE:
                        if (currentNode.getNodeName().endsWith("prop")) {
                            type = FIND_BY_PROPERTY;
                            propNode = currentNode;
                        }
                        if (currentNode.getNodeName().endsWith("propname")) {
                            type = FIND_PROPERTY_NAMES;
                        }
                        if (currentNode.getNodeName().endsWith("allprop")) {
                            type = FIND_ALL_PROP;
                        }
                        break;
                    }
                }
            } catch (SAXException e) {
                // Something went wrong - bad request
                resp.sendError(WebdavStatus.SC_BAD_REQUEST);
                return;
            } catch (IOException e) {
                // Something went wrong - bad request
                resp.sendError(WebdavStatus.SC_BAD_REQUEST);
                return;
            }
        }

        if (type == FIND_BY_PROPERTY) {
            properties = new Vector<String>();
            // propNode must be non-null if type == FIND_BY_PROPERTY
            @SuppressWarnings("null")
            NodeList childList = propNode.getChildNodes();

            for (int i=0; i < childList.getLength(); i++) {
                Node currentNode = childList.item(i);
                switch (currentNode.getNodeType()) {
                case Node.TEXT_NODE:
                    break;
                case Node.ELEMENT_NODE:
                    String nodeName = currentNode.getNodeName();
                    String propertyName = null;
                    if (nodeName.indexOf(':') != -1) {
                        propertyName = nodeName.substring
                            (nodeName.indexOf(':') + 1);
                    } else {
                        propertyName = nodeName;
                    }
                    // href is a live property which is handled differently
                    properties.addElement(propertyName);
                    break;
                }
            }

        }

        boolean exists = true;
        Object object = null;
        try {
            object = resources.lookup(path);
        } catch (NamingException e) {
            exists = false;
            int slash = path.lastIndexOf('/');
            if (slash != -1) {
                String parentPath = path.substring(0, slash);
                Vector<String> currentLockNullResources =
                    lockNullResources.get(parentPath);
                if (currentLockNullResources != null) {
                    Enumeration<String> lockNullResourcesList =
                        currentLockNullResources.elements();
                    while (lockNullResourcesList.hasMoreElements()) {
                        String lockNullPath =
                            lockNullResourcesList.nextElement();
                        if (lockNullPath.equals(path)) {
                            resp.setStatus(WebdavStatus.SC_MULTI_STATUS);
                            resp.setContentType("text/xml; charset=UTF-8");
                            // Create multistatus object
                            XMLWriter generatedXML =
                                new XMLWriter(resp.getWriter());
                            generatedXML.writeXMLHeader();
                            generatedXML.writeElement("D", DEFAULT_NAMESPACE,
                                    "multistatus", XMLWriter.OPENING);
                            parseLockNullProperties
                                (req, generatedXML, lockNullPath, type,
                                 properties);
                            generatedXML.writeElement("D", "multistatus",
                                    XMLWriter.CLOSING);
                            generatedXML.sendData();
                            return;
                        }
                    }
                }
            }
        }

        if (!exists) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, path);
            return;
        }

        resp.setStatus(WebdavStatus.SC_MULTI_STATUS);

        resp.setContentType("text/xml; charset=UTF-8");

        // Create multistatus object
        XMLWriter generatedXML = new XMLWriter(resp.getWriter());
        generatedXML.writeXMLHeader();

        generatedXML.writeElement("D", DEFAULT_NAMESPACE, "multistatus",
                XMLWriter.OPENING);

        if (depth == 0) {
            parseProperties(req, generatedXML, path, type,
                            properties);
        } else {
            // The stack always contains the object of the current level
            Stack<String> stack = new Stack<String>();
            stack.push(path);

            // Stack of the objects one level below
            Stack<String> stackBelow = new Stack<String>();

            while ((!stack.isEmpty()) && (depth >= 0)) {

                String currentPath = stack.pop();
                parseProperties(req, generatedXML, currentPath,
                                type, properties);

                try {
                    object = resources.lookup(currentPath);
                } catch (NamingException e) {
                    continue;
                }

                if ((object instanceof DirContext) && (depth > 0)) {

                    try {
                        NamingEnumeration<NameClassPair> enumeration =
                            resources.list(currentPath);
                        while (enumeration.hasMoreElements()) {
                            NameClassPair ncPair = enumeration.nextElement();
                            String newPath = currentPath;
                            if (!(newPath.endsWith("/")))
                                newPath += "/";
                            newPath += ncPair.getName();
                            stackBelow.push(newPath);
                        }
                    } catch (NamingException e) {
                        resp.sendError
                            (HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                             path);
                        return;
                    }

                    // Displaying the lock-null resources present in that
                    // collection
                    String lockPath = currentPath;
                    if (lockPath.endsWith("/"))
                        lockPath =
                            lockPath.substring(0, lockPath.length() - 1);
                    Vector<String> currentLockNullResources =
                        lockNullResources.get(lockPath);
                    if (currentLockNullResources != null) {
                        Enumeration<String> lockNullResourcesList =
                            currentLockNullResources.elements();
                        while (lockNullResourcesList.hasMoreElements()) {
                            String lockNullPath =
                                lockNullResourcesList.nextElement();
                            parseLockNullProperties
                                (req, generatedXML, lockNullPath, type,
                                 properties);
                        }
                    }

                }

                if (stack.isEmpty()) {
                    depth--;
                    stack = stackBelow;
                    stackBelow = new Stack<String>();
                }

                generatedXML.sendData();

            }
        }

        generatedXML.writeElement("D", "multistatus", XMLWriter.CLOSING);

        generatedXML.sendData();

    }


    /**
     * PROPPATCH Method.
     */
    protected void doProppatch(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);

    }


    /**
     * MKCOL Method.
     */
    protected void doMkcol(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        String path = getRelativePath(req);

        boolean exists = true;
        try {
            resources.lookup(path);
        } catch (NamingException e) {
            exists = false;
        }

        // Can't create a collection if a resource already exists at the given
        // path
        if (exists) {
            // Get allowed methods
            StringBuilder methodsAllowed = determineMethodsAllowed(resources,
                                                                  req);

            resp.addHeader("Allow", methodsAllowed.toString());

            resp.sendError(WebdavStatus.SC_METHOD_NOT_ALLOWED);
            return;
        }

        if (req.getContentLength() > 0) {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            try {
                // Document document =
                documentBuilder.parse(new InputSource(req.getInputStream()));
                // TODO : Process this request body
                resp.sendError(WebdavStatus.SC_NOT_IMPLEMENTED);
                return;

            } catch(SAXException saxe) {
                // Parse error - assume invalid content
                resp.sendError(WebdavStatus.SC_UNSUPPORTED_MEDIA_TYPE);
                return;
            }
        }

        boolean result = true;
        try {
            resources.createSubcontext(path);
        } catch (NamingException e) {
            result = false;
        }

        if (!result) {
            resp.sendError(WebdavStatus.SC_CONFLICT,
                           WebdavStatus.getStatusText
                           (WebdavStatus.SC_CONFLICT));
        } else {
            resp.setStatus(WebdavStatus.SC_CREATED);
            // Removing any lock-null resource which would be present
            lockNullResources.remove(path);
        }

    }


    /**
     * DELETE Method.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        deleteResource(req, resp);

    }


    /**
     * Process a PUT request for the specified resource.
     *
     * @param req The servlet request we are processing
     * @param resp The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet-specified error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        super.doPut(req, resp);

        String path = getRelativePath(req);

        // Removing any lock-null resource which would be present
        lockNullResources.remove(path);

    }

    /**
     * COPY Method.
     */
    protected void doCopy(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        copyResource(req, resp);

    }


    /**
     * MOVE Method.
     */
    protected void doMove(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        String path = getRelativePath(req);

        if (copyResource(req, resp)) {
            deleteResource(path, req, resp, false);
        }

    }


    /**
     * LOCK Method.
     */
    protected void doLock(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        LockInfo lock = new LockInfo();

        // Parsing lock request

        // Parsing depth header

        String depthStr = req.getHeader("Depth");

        if (depthStr == null) {
            lock.depth = maxDepth;
        } else {
            if (depthStr.equals("0")) {
                lock.depth = 0;
            } else {
                lock.depth = maxDepth;
            }
        }

        // Parsing timeout header

        int lockDuration = DEFAULT_TIMEOUT;
        String lockDurationStr = req.getHeader("Timeout");
        if (lockDurationStr == null) {
            lockDuration = DEFAULT_TIMEOUT;
        } else {
            int commaPos = lockDurationStr.indexOf(',');
            // If multiple timeouts, just use the first
            if (commaPos != -1) {
                lockDurationStr = lockDurationStr.substring(0,commaPos);
            }
            if (lockDurationStr.startsWith("Second-")) {
                lockDuration = Integer.parseInt(lockDurationStr.substring(7));
            } else {
                if (lockDurationStr.equalsIgnoreCase("infinity")) {
                    lockDuration = MAX_TIMEOUT;
                } else {
                    try {
                        lockDuration = Integer.parseInt(lockDurationStr);
                    } catch (NumberFormatException e) {
                        lockDuration = MAX_TIMEOUT;
                    }
                }
            }
            if (lockDuration == 0) {
                lockDuration = DEFAULT_TIMEOUT;
            }
            if (lockDuration > MAX_TIMEOUT) {
                lockDuration = MAX_TIMEOUT;
            }
        }
        lock.expiresAt = System.currentTimeMillis() + (lockDuration * 1000);

        int lockRequestType = LOCK_CREATION;

        Node lockInfoNode = null;

        DocumentBuilder documentBuilder = getDocumentBuilder();

        try {
            Document document = documentBuilder.parse(new InputSource
                (req.getInputStream()));

            // Get the root element of the document
            Element rootElement = document.getDocumentElement();
            lockInfoNode = rootElement;
        } catch (IOException e) {
            lockRequestType = LOCK_REFRESH;
        } catch (SAXException e) {
            lockRequestType = LOCK_REFRESH;
        }

        if (lockInfoNode != null) {

            // Reading lock information

            NodeList childList = lockInfoNode.getChildNodes();
            StringWriter strWriter = null;
            DOMWriter domWriter = null;

            Node lockScopeNode = null;
            Node lockTypeNode = null;
            Node lockOwnerNode = null;

            for (int i=0; i < childList.getLength(); i++) {
                Node currentNode = childList.item(i);
                switch (currentNode.getNodeType()) {
                case Node.TEXT_NODE:
                    break;
                case Node.ELEMENT_NODE:
                    String nodeName = currentNode.getNodeName();
                    if (nodeName.endsWith("lockscope")) {
                        lockScopeNode = currentNode;
                    }
                    if (nodeName.endsWith("locktype")) {
                        lockTypeNode = currentNode;
                    }
                    if (nodeName.endsWith("owner")) {
                        lockOwnerNode = currentNode;
                    }
                    break;
                }
            }

            if (lockScopeNode != null) {

                childList = lockScopeNode.getChildNodes();
                for (int i=0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        break;
                    case Node.ELEMENT_NODE:
                        String tempScope = currentNode.getNodeName();
                        if (tempScope.indexOf(':') != -1) {
                            lock.scope = tempScope.substring
                                (tempScope.indexOf(':') + 1);
                        } else {
                            lock.scope = tempScope;
                        }
                        break;
                    }
                }

                if (lock.scope == null) {
                    // Bad request
                    resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
                }

            } else {
                // Bad request
                resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
            }

            if (lockTypeNode != null) {

                childList = lockTypeNode.getChildNodes();
                for (int i=0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        break;
                    case Node.ELEMENT_NODE:
                        String tempType = currentNode.getNodeName();
                        if (tempType.indexOf(':') != -1) {
                            lock.type =
                                tempType.substring(tempType.indexOf(':') + 1);
                        } else {
                            lock.type = tempType;
                        }
                        break;
                    }
                }

                if (lock.type == null) {
                    // Bad request
                    resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
                }

            } else {
                // Bad request
                resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
            }

            if (lockOwnerNode != null) {

                childList = lockOwnerNode.getChildNodes();
                for (int i=0; i < childList.getLength(); i++) {
                    Node currentNode = childList.item(i);
                    switch (currentNode.getNodeType()) {
                    case Node.TEXT_NODE:
                        lock.owner += currentNode.getNodeValue();
                        break;
                    case Node.ELEMENT_NODE:
                        strWriter = new StringWriter();
                        domWriter = new DOMWriter(strWriter, true);
                        domWriter.setQualifiedNames(false);
                        domWriter.print(currentNode);
                        lock.owner += strWriter.toString();
                        break;
                    }
                }

                if (lock.owner == null) {
                    // Bad request
                    resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
                }

            } else {
                lock.owner = "";
            }

        }

        String path = getRelativePath(req);

        lock.path = path;

        boolean exists = true;
        Object object = null;
        try {
            object = resources.lookup(path);
        } catch (NamingException e) {
            exists = false;
        }

        Enumeration<LockInfo> locksList = null;

        if (lockRequestType == LOCK_CREATION) {

            // Generating lock id
            String lockTokenStr = req.getServletPath() + "-" + lock.type + "-"
                + lock.scope + "-" + req.getUserPrincipal() + "-"
                + lock.depth + "-" + lock.owner + "-" + lock.tokens + "-"
                + lock.expiresAt + "-" + System.currentTimeMillis() + "-"
                + secret;
            String lockToken = MD5Encoder.encode(md5Helper.digest(
                    lockTokenStr.getBytes(Charset.defaultCharset())));

            if ( (exists) && (object instanceof DirContext) &&
                 (lock.depth == maxDepth) ) {

                // Locking a collection (and all its member resources)

                // Checking if a child resource of this collection is
                // already locked
                Vector<String> lockPaths = new Vector<String>();
                locksList = collectionLocks.elements();
                while (locksList.hasMoreElements()) {
                    LockInfo currentLock = locksList.nextElement();
                    if (currentLock.hasExpired()) {
                        resourceLocks.remove(currentLock.path);
                        continue;
                    }
                    if ( (currentLock.path.startsWith(lock.path)) &&
                         ((currentLock.isExclusive()) ||
                          (lock.isExclusive())) ) {
                        // A child collection of this collection is locked
                        lockPaths.addElement(currentLock.path);
                    }
                }
                locksList = resourceLocks.elements();
                while (locksList.hasMoreElements()) {
                    LockInfo currentLock = locksList.nextElement();
                    if (currentLock.hasExpired()) {
                        resourceLocks.remove(currentLock.path);
                        continue;
                    }
                    if ( (currentLock.path.startsWith(lock.path)) &&
                         ((currentLock.isExclusive()) ||
                          (lock.isExclusive())) ) {
                        // A child resource of this collection is locked
                        lockPaths.addElement(currentLock.path);
                    }
                }

                if (!lockPaths.isEmpty()) {

                    // One of the child paths was locked
                    // We generate a multistatus error report

                    Enumeration<String> lockPathsList = lockPaths.elements();

                    resp.setStatus(WebdavStatus.SC_CONFLICT);

                    XMLWriter generatedXML = new XMLWriter();
                    generatedXML.writeXMLHeader();

                    generatedXML.writeElement("D", DEFAULT_NAMESPACE,
                            "multistatus", XMLWriter.OPENING);

                    while (lockPathsList.hasMoreElements()) {
                        generatedXML.writeElement("D", "response",
                                XMLWriter.OPENING);
                        generatedXML.writeElement("D", "href",
                                XMLWriter.OPENING);
                        generatedXML.writeText(lockPathsList.nextElement());
                        generatedXML.writeElement("D", "href",
                                XMLWriter.CLOSING);
                        generatedXML.writeElement("D", "status",
                                XMLWriter.OPENING);
                        generatedXML
                            .writeText("HTTP/1.1 " + WebdavStatus.SC_LOCKED
                                       + " " + WebdavStatus
                                       .getStatusText(WebdavStatus.SC_LOCKED));
                        generatedXML.writeElement("D", "status",
                                XMLWriter.CLOSING);

                        generatedXML.writeElement("D", "response",
                                XMLWriter.CLOSING);
                    }

                    generatedXML.writeElement("D", "multistatus",
                            XMLWriter.CLOSING);

                    Writer writer = resp.getWriter();
                    writer.write(generatedXML.toString());
                    writer.close();

                    return;

                }

                boolean addLock = true;

                // Checking if there is already a shared lock on this path
                locksList = collectionLocks.elements();
                while (locksList.hasMoreElements()) {

                    LockInfo currentLock = locksList.nextElement();
                    if (currentLock.path.equals(lock.path)) {

                        if (currentLock.isExclusive()) {
                            resp.sendError(WebdavStatus.SC_LOCKED);
                            return;
                        } else {
                            if (lock.isExclusive()) {
                                resp.sendError(WebdavStatus.SC_LOCKED);
                                return;
                            }
                        }

                        currentLock.tokens.addElement(lockToken);
                        lock = currentLock;
                        addLock = false;

                    }

                }

                if (addLock) {
                    lock.tokens.addElement(lockToken);
                    collectionLocks.addElement(lock);
                }

            } else {

                // Locking a single resource

                // Retrieving an already existing lock on that resource
                LockInfo presentLock = resourceLocks.get(lock.path);
                if (presentLock != null) {

                    if ((presentLock.isExclusive()) || (lock.isExclusive())) {
                        // If either lock is exclusive, the lock can't be
                        // granted
                        resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
                        return;
                    } else {
                        presentLock.tokens.addElement(lockToken);
                        lock = presentLock;
                    }

                } else {

                    lock.tokens.addElement(lockToken);
                    resourceLocks.put(lock.path, lock);

                    // Checking if a resource exists at this path
                    exists = true;
                    try {
                        object = resources.lookup(path);
                    } catch (NamingException e) {
                        exists = false;
                    }
                    if (!exists) {

                        // "Creating" a lock-null resource
                        int slash = lock.path.lastIndexOf('/');
                        String parentPath = lock.path.substring(0, slash);

                        Vector<String> lockNulls =
                            lockNullResources.get(parentPath);
                        if (lockNulls == null) {
                            lockNulls = new Vector<String>();
                            lockNullResources.put(parentPath, lockNulls);
                        }

                        lockNulls.addElement(lock.path);

                    }
                    // Add the Lock-Token header as by RFC 2518 8.10.1
                    // - only do this for newly created locks
                    resp.addHeader("Lock-Token", "<opaquelocktoken:"
                                   + lockToken + ">");
                }

            }

        }

        if (lockRequestType == LOCK_REFRESH) {

            String ifHeader = req.getHeader("If");
            if (ifHeader == null)
                ifHeader = "";

            // Checking resource locks

            LockInfo toRenew = resourceLocks.get(path);
            Enumeration<String> tokenList = null;

            if (toRenew != null) {
                // At least one of the tokens of the locks must have been given
                tokenList = toRenew.tokens.elements();
                while (tokenList.hasMoreElements()) {
                    String token = tokenList.nextElement();
                    if (ifHeader.indexOf(token) != -1) {
                        toRenew.expiresAt = lock.expiresAt;
                        lock = toRenew;
                    }
                }
            }

            // Checking inheritable collection locks

            Enumeration<LockInfo> collectionLocksList =
                collectionLocks.elements();
            while (collectionLocksList.hasMoreElements()) {
                toRenew = collectionLocksList.nextElement();
                if (path.equals(toRenew.path)) {

                    tokenList = toRenew.tokens.elements();
                    while (tokenList.hasMoreElements()) {
                        String token = tokenList.nextElement();
                        if (ifHeader.indexOf(token) != -1) {
                            toRenew.expiresAt = lock.expiresAt;
                            lock = toRenew;
                        }
                    }

                }
            }

        }

        // Set the status, then generate the XML response containing
        // the lock information
        XMLWriter generatedXML = new XMLWriter();
        generatedXML.writeXMLHeader();
        generatedXML.writeElement("D", DEFAULT_NAMESPACE, "prop",
                XMLWriter.OPENING);

        generatedXML.writeElement("D", "lockdiscovery", XMLWriter.OPENING);

        lock.toXML(generatedXML);

        generatedXML.writeElement("D", "lockdiscovery", XMLWriter.CLOSING);

        generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);

        resp.setStatus(WebdavStatus.SC_OK);
        resp.setContentType("text/xml; charset=UTF-8");
        Writer writer = resp.getWriter();
        writer.write(generatedXML.toString());
        writer.close();

    }


    /**
     * UNLOCK Method.
     */
    protected void doUnlock(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (readOnly) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return;
        }

        if (isLocked(req)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return;
        }

        String path = getRelativePath(req);

        String lockTokenHeader = req.getHeader("Lock-Token");
        if (lockTokenHeader == null)
            lockTokenHeader = "";

        // Checking resource locks

        LockInfo lock = resourceLocks.get(path);
        Enumeration<String> tokenList = null;
        if (lock != null) {

            // At least one of the tokens of the locks must have been given

            tokenList = lock.tokens.elements();
            while (tokenList.hasMoreElements()) {
                String token = tokenList.nextElement();
                if (lockTokenHeader.indexOf(token) != -1) {
                    lock.tokens.removeElement(token);
                }
            }

            if (lock.tokens.isEmpty()) {
                resourceLocks.remove(path);
                // Removing any lock-null resource which would be present
                lockNullResources.remove(path);
            }

        }

        // Checking inheritable collection locks

        Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();
        while (collectionLocksList.hasMoreElements()) {
            lock = collectionLocksList.nextElement();
            if (path.equals(lock.path)) {

                tokenList = lock.tokens.elements();
                while (tokenList.hasMoreElements()) {
                    String token = tokenList.nextElement();
                    if (lockTokenHeader.indexOf(token) != -1) {
                        lock.tokens.removeElement(token);
                        break;
                    }
                }

                if (lock.tokens.isEmpty()) {
                    collectionLocks.removeElement(lock);
                    // Removing any lock-null resource which would be present
                    lockNullResources.remove(path);
                }

            }
        }

        resp.setStatus(WebdavStatus.SC_NO_CONTENT);

    }

    // -------------------------------------------------------- Private Methods

    /**
     * Check to see if a resource is currently write locked. The method
     * will look at the "If" header to make sure the client
     * has give the appropriate lock tokens.
     *
     * @param req Servlet request
     * @return boolean true if the resource is locked (and no appropriate
     * lock token has been found for at least one of the non-shared locks which
     * are present on the resource).
     */
    private boolean isLocked(HttpServletRequest req) {

        String path = getRelativePath(req);

        String ifHeader = req.getHeader("If");
        if (ifHeader == null)
            ifHeader = "";

        String lockTokenHeader = req.getHeader("Lock-Token");
        if (lockTokenHeader == null)
            lockTokenHeader = "";

        return isLocked(path, ifHeader + lockTokenHeader);

    }


    /**
     * Check to see if a resource is currently write locked.
     *
     * @param path Path of the resource
     * @param ifHeader "If" HTTP header which was included in the request
     * @return boolean true if the resource is locked (and no appropriate
     * lock token has been found for at least one of the non-shared locks which
     * are present on the resource).
     */
    private boolean isLocked(String path, String ifHeader) {

        // Checking resource locks

        LockInfo lock = resourceLocks.get(path);
        Enumeration<String> tokenList = null;
        if ((lock != null) && (lock.hasExpired())) {
            resourceLocks.remove(path);
        } else if (lock != null) {

            // At least one of the tokens of the locks must have been given

            tokenList = lock.tokens.elements();
            boolean tokenMatch = false;
            while (tokenList.hasMoreElements()) {
                String token = tokenList.nextElement();
                if (ifHeader.indexOf(token) != -1) {
                    tokenMatch = true;
                    break;
                }
            }
            if (!tokenMatch)
                return true;

        }

        // Checking inheritable collection locks

        Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();
        while (collectionLocksList.hasMoreElements()) {
            lock = collectionLocksList.nextElement();
            if (lock.hasExpired()) {
                collectionLocks.removeElement(lock);
            } else if (path.startsWith(lock.path)) {

                tokenList = lock.tokens.elements();
                boolean tokenMatch = false;
                while (tokenList.hasMoreElements()) {
                    String token = tokenList.nextElement();
                    if (ifHeader.indexOf(token) != -1) {
                        tokenMatch = true;
                        break;
                    }
                }
                if (!tokenMatch)
                    return true;

            }
        }

        return false;

    }


    /**
     * Copy a resource.
     *
     * @param req Servlet request
     * @param resp Servlet response
     * @return boolean true if the copy is successful
     */
    private boolean copyResource(HttpServletRequest req,
                                 HttpServletResponse resp)
            throws IOException {

        // Parsing destination header

        String destinationPath = req.getHeader("Destination");

        if (destinationPath == null) {
            resp.sendError(WebdavStatus.SC_BAD_REQUEST);
            return false;
        }

        // Remove url encoding from destination
        destinationPath = org.apache.catalina.util.RequestUtil.URLDecode(
                destinationPath, "UTF8");

        int protocolIndex = destinationPath.indexOf("://");
        if (protocolIndex >= 0) {
            // if the Destination URL contains the protocol, we can safely
            // trim everything upto the first "/" character after "://"
            int firstSeparator =
                destinationPath.indexOf('/', protocolIndex + 4);
            if (firstSeparator < 0) {
                destinationPath = "/";
            } else {
                destinationPath = destinationPath.substring(firstSeparator);
            }
        } else {
            String hostName = req.getServerName();
            if ((hostName != null) && (destinationPath.startsWith(hostName))) {
                destinationPath = destinationPath.substring(hostName.length());
            }

            int portIndex = destinationPath.indexOf(':');
            if (portIndex >= 0) {
                destinationPath = destinationPath.substring(portIndex);
            }

            if (destinationPath.startsWith(":")) {
                int firstSeparator = destinationPath.indexOf('/');
                if (firstSeparator < 0) {
                    destinationPath = "/";
                } else {
                    destinationPath =
                        destinationPath.substring(firstSeparator);
                }
            }
        }

        // Normalise destination path (remove '.' and '..')
        destinationPath = RequestUtil.normalize(destinationPath);

        String contextPath = req.getContextPath();
        if ((contextPath != null) &&
            (destinationPath.startsWith(contextPath))) {
            destinationPath = destinationPath.substring(contextPath.length());
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            String servletPath = req.getServletPath();
            if ((servletPath != null) &&
                (destinationPath.startsWith(servletPath))) {
                destinationPath = destinationPath
                    .substring(servletPath.length());
            }
        }

        if (debug > 0)
            log("Dest path :" + destinationPath);

        // Check destination path to protect special subdirectories
        if (isSpecialPath(destinationPath)) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return false;
        }

        String path = getRelativePath(req);

        if (destinationPath.equals(path)) {
            resp.sendError(WebdavStatus.SC_FORBIDDEN);
            return false;
        }

        // Parsing overwrite header

        boolean overwrite = true;
        String overwriteHeader = req.getHeader("Overwrite");

        if (overwriteHeader != null) {
            if (overwriteHeader.equalsIgnoreCase("T")) {
                overwrite = true;
            } else {
                overwrite = false;
            }
        }

        // Overwriting the destination

        boolean exists = true;
        try {
            resources.lookup(destinationPath);
        } catch (NamingException e) {
            exists = false;
        }

        if (overwrite) {

            // Delete destination resource, if it exists
            if (exists) {
                if (!deleteResource(destinationPath, req, resp, true)) {
                    return false;
                }
            } else {
                resp.setStatus(WebdavStatus.SC_CREATED);
            }

        } else {

            // If the destination exists, then it's a conflict
            if (exists) {
                resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
                return false;
            }

        }

        // Copying source to destination

        Hashtable<String,Integer> errorList = new Hashtable<String,Integer>();

        boolean result = copyResource(resources, errorList,
                                      path, destinationPath);

        if ((!result) || (!errorList.isEmpty())) {
            if (errorList.size() == 1) {
                resp.sendError(errorList.elements().nextElement().intValue());
            } else {
                sendReport(req, resp, errorList);
            }
            return false;
        }

        // Copy was successful
        if (exists) {
            resp.setStatus(WebdavStatus.SC_NO_CONTENT);
        } else {
            resp.setStatus(WebdavStatus.SC_CREATED);
        }

        // Removing any lock-null resource which would be present at
        // the destination path
        lockNullResources.remove(destinationPath);

        return true;

    }


    /**
     * Copy a collection.
     *
     * @param dirContext Resources implementation to be used
     * @param errorList Hashtable containing the list of errors which occurred
     * during the copy operation
     * @param source Path of the resource to be copied
     * @param dest Destination path
     */
    private boolean copyResource(DirContext dirContext,
            Hashtable<String,Integer> errorList, String source, String dest) {

        if (debug > 1)
            log("Copy: " + source + " To: " + dest);

        Object object = null;
        try {
            object = dirContext.lookup(source);
        } catch (NamingException e) {
            // Ignore
        }

        if (object instanceof DirContext) {

            try {
                dirContext.createSubcontext(dest);
            } catch (NamingException e) {
                errorList.put
                    (dest, Integer.valueOf(WebdavStatus.SC_CONFLICT));
                return false;
            }

            try {
                NamingEnumeration<NameClassPair> enumeration =
                    dirContext.list(source);
                while (enumeration.hasMoreElements()) {
                    NameClassPair ncPair = enumeration.nextElement();
                    String childDest = dest;
                    if (!childDest.equals("/"))
                        childDest += "/";
                    childDest += ncPair.getName();
                    String childSrc = source;
                    if (!childSrc.equals("/"))
                        childSrc += "/";
                    childSrc += ncPair.getName();
                    copyResource(dirContext, errorList, childSrc, childDest);
                }
            } catch (NamingException e) {
                errorList.put
                    (dest, Integer.valueOf(WebdavStatus.SC_INTERNAL_SERVER_ERROR));
                return false;
            }

        } else {

            if (object instanceof Resource) {
                try {
                    dirContext.bind(dest, object);
                } catch (NamingException e) {
                    if (e.getCause() instanceof FileNotFoundException) {
                        // We know the source exists so it must be the
                        // destination dir that can't be found
                        errorList.put(source,
                                Integer.valueOf(WebdavStatus.SC_CONFLICT));
                    } else {
                        errorList.put(source,
                                Integer.valueOf(WebdavStatus.SC_INTERNAL_SERVER_ERROR));
                    }
                    return false;
                }
            } else {
                errorList.put
                    (source,
                     Integer.valueOf(WebdavStatus.SC_INTERNAL_SERVER_ERROR));
                return false;
            }

        }

        return true;

    }


    /**
     * Delete a resource.
     *
     * @param req Servlet request
     * @param resp Servlet response
     * @return boolean true if the copy is successful
     */
    private boolean deleteResource(HttpServletRequest req,
                                   HttpServletResponse resp)
            throws IOException {

        String path = getRelativePath(req);

        return deleteResource(path, req, resp, true);

    }


    /**
     * Delete a resource.
     *
     * @param path Path of the resource which is to be deleted
     * @param req Servlet request
     * @param resp Servlet response
     * @param setStatus Should the response status be set on successful
     *                  completion
     */
    private boolean deleteResource(String path, HttpServletRequest req,
                                   HttpServletResponse resp, boolean setStatus)
            throws IOException {

        String ifHeader = req.getHeader("If");
        if (ifHeader == null)
            ifHeader = "";

        String lockTokenHeader = req.getHeader("Lock-Token");
        if (lockTokenHeader == null)
            lockTokenHeader = "";

        if (isLocked(path, ifHeader + lockTokenHeader)) {
            resp.sendError(WebdavStatus.SC_LOCKED);
            return false;
        }

        boolean exists = true;
        Object object = null;
        try {
            object = resources.lookup(path);
        } catch (NamingException e) {
            exists = false;
        }

        if (!exists) {
            resp.sendError(WebdavStatus.SC_NOT_FOUND);
            return false;
        }

        boolean collection = (object instanceof DirContext);

        if (!collection) {
            try {
                resources.unbind(path);
            } catch (NamingException e) {
                resp.sendError(WebdavStatus.SC_INTERNAL_SERVER_ERROR);
                return false;
            }
        } else {

            Hashtable<String,Integer> errorList =
                new Hashtable<String,Integer>();

            deleteCollection(req, resources, path, errorList);
            try {
                resources.unbind(path);
            } catch (NamingException e) {
                errorList.put(path, Integer.valueOf
                    (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
            }

            if (!errorList.isEmpty()) {

                sendReport(req, resp, errorList);
                return false;

            }

        }
        if (setStatus) {
            resp.setStatus(WebdavStatus.SC_NO_CONTENT);
        }
        return true;

    }


    /**
     * Deletes a collection.
     *
     * @param dirContext Resources implementation associated with the context
     * @param path Path to the collection to be deleted
     * @param errorList Contains the list of the errors which occurred
     */
    private void deleteCollection(HttpServletRequest req,
                                  DirContext dirContext,
                                  String path,
                                  Hashtable<String,Integer> errorList) {

        if (debug > 1)
            log("Delete:" + path);

        // Prevent deletion of special subdirectories
        if (isSpecialPath(path)) {
            errorList.put(path, Integer.valueOf(WebdavStatus.SC_FORBIDDEN));
            return;
        }

        String ifHeader = req.getHeader("If");
        if (ifHeader == null)
            ifHeader = "";

        String lockTokenHeader = req.getHeader("Lock-Token");
        if (lockTokenHeader == null)
            lockTokenHeader = "";

        Enumeration<NameClassPair> enumeration = null;
        try {
            enumeration = dirContext.list(path);
        } catch (NamingException e) {
            errorList.put(path, Integer.valueOf
                (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
            return;
        }

        while (enumeration.hasMoreElements()) {
            NameClassPair ncPair = enumeration.nextElement();
            String childName = path;
            if (!childName.equals("/"))
                childName += "/";
            childName += ncPair.getName();

            if (isLocked(childName, ifHeader + lockTokenHeader)) {

                errorList.put(childName, Integer.valueOf(WebdavStatus.SC_LOCKED));

            } else {

                try {
                    Object object = dirContext.lookup(childName);
                    if (object instanceof DirContext) {
                        deleteCollection(req, dirContext, childName, errorList);
                    }

                    try {
                        dirContext.unbind(childName);
                    } catch (NamingException e) {
                        if (!(object instanceof DirContext)) {
                            // If it's not a collection, then it's an unknown
                            // error
                            errorList.put
                                (childName, Integer.valueOf
                                    (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
                        }
                    }
                } catch (NamingException e) {
                    errorList.put
                        (childName, Integer.valueOf
                            (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
                }
            }

        }

    }


    /**
     * Send a multistatus element containing a complete error report to the
     * client.
     *
     * @param req Servlet request
     * @param resp Servlet response
     * @param errorList List of error to be displayed
     */
    private void sendReport(HttpServletRequest req, HttpServletResponse resp,
                            Hashtable<String,Integer> errorList)
            throws IOException {

        resp.setStatus(WebdavStatus.SC_MULTI_STATUS);

        String absoluteUri = req.getRequestURI();
        String relativePath = getRelativePath(req);

        XMLWriter generatedXML = new XMLWriter();
        generatedXML.writeXMLHeader();

        generatedXML.writeElement("D", DEFAULT_NAMESPACE, "multistatus",
                XMLWriter.OPENING);

        Enumeration<String> pathList = errorList.keys();
        while (pathList.hasMoreElements()) {

            String errorPath = pathList.nextElement();
            int errorCode = errorList.get(errorPath).intValue();

            generatedXML.writeElement("D", "response", XMLWriter.OPENING);

            generatedXML.writeElement("D", "href", XMLWriter.OPENING);
            String toAppend = errorPath.substring(relativePath.length());
            if (!toAppend.startsWith("/"))
                toAppend = "/" + toAppend;
            generatedXML.writeText(absoluteUri + toAppend);
            generatedXML.writeElement("D", "href", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText("HTTP/1.1 " + errorCode + " "
                    + WebdavStatus.getStatusText(errorCode));
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "response", XMLWriter.CLOSING);

        }

        generatedXML.writeElement("D", "multistatus", XMLWriter.CLOSING);

        Writer writer = resp.getWriter();
        writer.write(generatedXML.toString());
        writer.close();

    }


    /**
     * Propfind helper method.
     *
     * @param req The servlet request
     * @param resources Resources object associated with this context
     * @param generatedXML XML response to the Propfind request
     * @param path Path of the current resource
     * @param type Propfind type
     * @param propertiesVector If the propfind type is find properties by
     * name, then this Vector contains those properties
     */
    private void parseProperties(HttpServletRequest req,
                                 XMLWriter generatedXML,
                                 String path, int type,
                                 Vector<String> propertiesVector) {

        // Exclude any resource in the /WEB-INF and /META-INF subdirectories
        if (isSpecialPath(path))
            return;

        CacheEntry cacheEntry = resources.lookupCache(path);
        if (!cacheEntry.exists) {
            // File is in directory listing but doesn't appear to exist
            // Broken symlink or odd permission settings?
            return;
        }

        generatedXML.writeElement("D", "response", XMLWriter.OPENING);
        String status = "HTTP/1.1 " + WebdavStatus.SC_OK + " " +
                WebdavStatus.getStatusText(WebdavStatus.SC_OK);

        // Generating href element
        generatedXML.writeElement("D", "href", XMLWriter.OPENING);

        String href = req.getContextPath() + req.getServletPath();
        if ((href.endsWith("/")) && (path.startsWith("/")))
            href += path.substring(1);
        else
            href += path;
        if ((cacheEntry.context != null) && (!href.endsWith("/")))
            href += "/";

        generatedXML.writeText(rewriteUrl(href));

        generatedXML.writeElement("D", "href", XMLWriter.CLOSING);

        String resourceName = path;
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash != -1)
            resourceName = resourceName.substring(lastSlash + 1);

        switch (type) {

        case FIND_ALL_PROP :

            generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
            generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

            generatedXML.writeProperty("D", "creationdate",
                    getISOCreationDate(cacheEntry.attributes.getCreation()));
            generatedXML.writeElement("D", "displayname", XMLWriter.OPENING);
            generatedXML.writeData(resourceName);
            generatedXML.writeElement("D", "displayname", XMLWriter.CLOSING);
            if (cacheEntry.resource != null) {
                generatedXML.writeProperty
                    ("D", "getlastmodified", FastHttpDateFormat.formatDate
                           (cacheEntry.attributes.getLastModified(), null));
                generatedXML.writeProperty
                    ("D", "getcontentlength",
                     String.valueOf(cacheEntry.attributes.getContentLength()));
                String contentType = getServletContext().getMimeType
                    (cacheEntry.name);
                if (contentType != null) {
                    generatedXML.writeProperty("D", "getcontenttype",
                            contentType);
                }
                generatedXML.writeProperty("D", "getetag",
                        cacheEntry.attributes.getETag());
                generatedXML.writeElement("D", "resourcetype",
                        XMLWriter.NO_CONTENT);
            } else {
                generatedXML.writeElement("D", "resourcetype",
                        XMLWriter.OPENING);
                generatedXML.writeElement("D", "collection",
                        XMLWriter.NO_CONTENT);
                generatedXML.writeElement("D", "resourcetype",
                        XMLWriter.CLOSING);
            }

            generatedXML.writeProperty("D", "source", "");

            String supportedLocks = "<D:lockentry>"
                + "<D:lockscope><D:exclusive/></D:lockscope>"
                + "<D:locktype><D:write/></D:locktype>"
                + "</D:lockentry>" + "<D:lockentry>"
                + "<D:lockscope><D:shared/></D:lockscope>"
                + "<D:locktype><D:write/></D:locktype>"
                + "</D:lockentry>";
            generatedXML.writeElement("D", "supportedlock", XMLWriter.OPENING);
            generatedXML.writeText(supportedLocks);
            generatedXML.writeElement("D", "supportedlock", XMLWriter.CLOSING);

            generateLockDiscovery(path, generatedXML);

            generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText(status);
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            break;

        case FIND_PROPERTY_NAMES :

            generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
            generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

            generatedXML.writeElement("D", "creationdate",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "displayname", XMLWriter.NO_CONTENT);
            if (cacheEntry.resource != null) {
                generatedXML.writeElement("D", "getcontentlanguage",
                        XMLWriter.NO_CONTENT);
                generatedXML.writeElement("D", "getcontentlength",
                        XMLWriter.NO_CONTENT);
                generatedXML.writeElement("D", "getcontenttype",
                        XMLWriter.NO_CONTENT);
                generatedXML.writeElement("D", "getetag", XMLWriter.NO_CONTENT);
                generatedXML.writeElement("D", "getlastmodified",
                        XMLWriter.NO_CONTENT);
            }
            generatedXML.writeElement("D", "resourcetype",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "source", XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "lockdiscovery",
                                      XMLWriter.NO_CONTENT);

            generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText(status);
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            break;

        case FIND_BY_PROPERTY :

            Vector<String> propertiesNotFound = new Vector<String>();

            // Parse the list of properties

            generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
            generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

            Enumeration<String> properties = propertiesVector.elements();

            while (properties.hasMoreElements()) {

                String property = properties.nextElement();

                if (property.equals("creationdate")) {
                    generatedXML.writeProperty
                        ("D", "creationdate",
                         getISOCreationDate(cacheEntry.attributes.getCreation()));
                } else if (property.equals("displayname")) {
                    generatedXML.writeElement
                        ("D", "displayname", XMLWriter.OPENING);
                    generatedXML.writeData(resourceName);
                    generatedXML.writeElement
                        ("D", "displayname", XMLWriter.CLOSING);
                } else if (property.equals("getcontentlanguage")) {
                    if (cacheEntry.context != null) {
                        propertiesNotFound.addElement(property);
                    } else {
                        generatedXML.writeElement("D", "getcontentlanguage",
                                                  XMLWriter.NO_CONTENT);
                    }
                } else if (property.equals("getcontentlength")) {
                    if (cacheEntry.context != null) {
                        propertiesNotFound.addElement(property);
                    } else {
                        generatedXML.writeProperty
                            ("D", "getcontentlength",
                             (String.valueOf(cacheEntry.attributes.getContentLength())));
                    }
                } else if (property.equals("getcontenttype")) {
                    if (cacheEntry.context != null) {
                        propertiesNotFound.addElement(property);
                    } else {
                        generatedXML.writeProperty
                            ("D", "getcontenttype",
                             getServletContext().getMimeType
                             (cacheEntry.name));
                    }
                } else if (property.equals("getetag")) {
                    if (cacheEntry.context != null) {
                        propertiesNotFound.addElement(property);
                    } else {
                        generatedXML.writeProperty
                            ("D", "getetag", cacheEntry.attributes.getETag());
                    }
                } else if (property.equals("getlastmodified")) {
                    if (cacheEntry.context != null) {
                        propertiesNotFound.addElement(property);
                    } else {
                        generatedXML.writeProperty
                            ("D", "getlastmodified", FastHttpDateFormat.formatDate
                                    (cacheEntry.attributes.getLastModified(), null));
                    }
                } else if (property.equals("resourcetype")) {
                    if (cacheEntry.context != null) {
                        generatedXML.writeElement("D", "resourcetype",
                                XMLWriter.OPENING);
                        generatedXML.writeElement("D", "collection",
                                XMLWriter.NO_CONTENT);
                        generatedXML.writeElement("D", "resourcetype",
                                XMLWriter.CLOSING);
                    } else {
                        generatedXML.writeElement("D", "resourcetype",
                                XMLWriter.NO_CONTENT);
                    }
                } else if (property.equals("source")) {
                    generatedXML.writeProperty("D", "source", "");
                } else if (property.equals("supportedlock")) {
                    supportedLocks = "<D:lockentry>"
                        + "<D:lockscope><D:exclusive/></D:lockscope>"
                        + "<D:locktype><D:write/></D:locktype>"
                        + "</D:lockentry>" + "<D:lockentry>"
                        + "<D:lockscope><D:shared/></D:lockscope>"
                        + "<D:locktype><D:write/></D:locktype>"
                        + "</D:lockentry>";
                    generatedXML.writeElement("D", "supportedlock",
                            XMLWriter.OPENING);
                    generatedXML.writeText(supportedLocks);
                    generatedXML.writeElement("D", "supportedlock",
                            XMLWriter.CLOSING);
                } else if (property.equals("lockdiscovery")) {
                    if (!generateLockDiscovery(path, generatedXML))
                        propertiesNotFound.addElement(property);
                } else {
                    propertiesNotFound.addElement(property);
                }

            }

            generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText(status);
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            Enumeration<String> propertiesNotFoundList =
                propertiesNotFound.elements();

            if (propertiesNotFoundList.hasMoreElements()) {

                status = "HTTP/1.1 " + WebdavStatus.SC_NOT_FOUND + " " +
                        WebdavStatus.getStatusText(WebdavStatus.SC_NOT_FOUND);

                generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
                generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

                while (propertiesNotFoundList.hasMoreElements()) {
                    generatedXML.writeElement
                        ("D", propertiesNotFoundList.nextElement(),
                         XMLWriter.NO_CONTENT);
                }

                generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
                generatedXML.writeElement("D", "status", XMLWriter.OPENING);
                generatedXML.writeText(status);
                generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
                generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            }

            break;

        }

        generatedXML.writeElement("D", "response", XMLWriter.CLOSING);

    }


    /**
     * Propfind helper method. Displays the properties of a lock-null resource.
     *
     * @param resources Resources object associated with this context
     * @param generatedXML XML response to the Propfind request
     * @param path Path of the current resource
     * @param type Propfind type
     * @param propertiesVector If the propfind type is find properties by
     * name, then this Vector contains those properties
     */
    private void parseLockNullProperties(HttpServletRequest req,
                                         XMLWriter generatedXML,
                                         String path, int type,
                                         Vector<String> propertiesVector) {

        // Exclude any resource in the /WEB-INF and /META-INF subdirectories
        if (isSpecialPath(path))
            return;

        // Retrieving the lock associated with the lock-null resource
        LockInfo lock = resourceLocks.get(path);

        if (lock == null)
            return;

        generatedXML.writeElement("D", "response", XMLWriter.OPENING);
        String status = "HTTP/1.1 " + WebdavStatus.SC_OK + " " +
                WebdavStatus.getStatusText(WebdavStatus.SC_OK);

        // Generating href element
        generatedXML.writeElement("D", "href", XMLWriter.OPENING);

        String absoluteUri = req.getRequestURI();
        String relativePath = getRelativePath(req);
        String toAppend = path.substring(relativePath.length());
        if (!toAppend.startsWith("/"))
            toAppend = "/" + toAppend;

        generatedXML.writeText(rewriteUrl(RequestUtil.normalize(
                absoluteUri + toAppend)));

        generatedXML.writeElement("D", "href", XMLWriter.CLOSING);

        String resourceName = path;
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash != -1)
            resourceName = resourceName.substring(lastSlash + 1);

        switch (type) {

        case FIND_ALL_PROP :

            generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
            generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

            generatedXML.writeProperty("D", "creationdate",
                    getISOCreationDate(lock.creationDate.getTime()));
            generatedXML.writeElement("D", "displayname", XMLWriter.OPENING);
            generatedXML.writeData(resourceName);
            generatedXML.writeElement("D", "displayname", XMLWriter.CLOSING);
            generatedXML.writeProperty("D", "getlastmodified",
                                       FastHttpDateFormat.formatDate
                                       (lock.creationDate.getTime(), null));
            generatedXML.writeProperty("D", "getcontentlength",
                    String.valueOf(0));
            generatedXML.writeProperty("D", "getcontenttype", "");
            generatedXML.writeProperty("D", "getetag", "");
            generatedXML.writeElement("D", "resourcetype", XMLWriter.OPENING);
            generatedXML.writeElement("D", "lock-null", XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "resourcetype", XMLWriter.CLOSING);

            generatedXML.writeProperty("D", "source", "");

            String supportedLocks = "<D:lockentry>"
                + "<D:lockscope><D:exclusive/></D:lockscope>"
                + "<D:locktype><D:write/></D:locktype>"
                + "</D:lockentry>" + "<D:lockentry>"
                + "<D:lockscope><D:shared/></D:lockscope>"
                + "<D:locktype><D:write/></D:locktype>"
                + "</D:lockentry>";
            generatedXML.writeElement("D", "supportedlock", XMLWriter.OPENING);
            generatedXML.writeText(supportedLocks);
            generatedXML.writeElement("D", "supportedlock", XMLWriter.CLOSING);

            generateLockDiscovery(path, generatedXML);

            generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText(status);
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            break;

        case FIND_PROPERTY_NAMES :

            generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
            generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

            generatedXML.writeElement("D", "creationdate",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "displayname", XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "getcontentlanguage",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "getcontentlength",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "getcontenttype",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "getetag", XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "getlastmodified",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "resourcetype",
                                      XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "source", XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "lockdiscovery",
                                      XMLWriter.NO_CONTENT);

            generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText(status);
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            break;

        case FIND_BY_PROPERTY :

            Vector<String> propertiesNotFound = new Vector<String>();

            // Parse the list of properties

            generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
            generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

            Enumeration<String> properties = propertiesVector.elements();

            while (properties.hasMoreElements()) {

                String property = properties.nextElement();

                if (property.equals("creationdate")) {
                    generatedXML.writeProperty("D", "creationdate",
                            getISOCreationDate(lock.creationDate.getTime()));
                } else if (property.equals("displayname")) {
                    generatedXML.writeElement("D", "displayname",
                            XMLWriter.OPENING);
                    generatedXML.writeData(resourceName);
                    generatedXML.writeElement("D", "displayname",
                            XMLWriter.CLOSING);
                } else if (property.equals("getcontentlanguage")) {
                    generatedXML.writeElement("D", "getcontentlanguage",
                            XMLWriter.NO_CONTENT);
                } else if (property.equals("getcontentlength")) {
                    generatedXML.writeProperty("D", "getcontentlength",
                            (String.valueOf(0)));
                } else if (property.equals("getcontenttype")) {
                    generatedXML.writeProperty("D", "getcontenttype", "");
                } else if (property.equals("getetag")) {
                    generatedXML.writeProperty("D", "getetag", "");
                } else if (property.equals("getlastmodified")) {
                    generatedXML.writeProperty
                        ("D", "getlastmodified",
                          FastHttpDateFormat.formatDate
                         (lock.creationDate.getTime(), null));
                } else if (property.equals("resourcetype")) {
                    generatedXML.writeElement("D", "resourcetype",
                            XMLWriter.OPENING);
                    generatedXML.writeElement("D", "lock-null",
                            XMLWriter.NO_CONTENT);
                    generatedXML.writeElement("D", "resourcetype",
                            XMLWriter.CLOSING);
                } else if (property.equals("source")) {
                    generatedXML.writeProperty("D", "source", "");
                } else if (property.equals("supportedlock")) {
                    supportedLocks = "<D:lockentry>"
                        + "<D:lockscope><D:exclusive/></D:lockscope>"
                        + "<D:locktype><D:write/></D:locktype>"
                        + "</D:lockentry>" + "<D:lockentry>"
                        + "<D:lockscope><D:shared/></D:lockscope>"
                        + "<D:locktype><D:write/></D:locktype>"
                        + "</D:lockentry>";
                    generatedXML.writeElement("D", "supportedlock",
                            XMLWriter.OPENING);
                    generatedXML.writeText(supportedLocks);
                    generatedXML.writeElement("D", "supportedlock",
                            XMLWriter.CLOSING);
                } else if (property.equals("lockdiscovery")) {
                    if (!generateLockDiscovery(path, generatedXML))
                        propertiesNotFound.addElement(property);
                } else {
                    propertiesNotFound.addElement(property);
                }

            }

            generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "status", XMLWriter.OPENING);
            generatedXML.writeText(status);
            generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
            generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            Enumeration<String> propertiesNotFoundList = propertiesNotFound.elements();

            if (propertiesNotFoundList.hasMoreElements()) {

                status = "HTTP/1.1 " + WebdavStatus.SC_NOT_FOUND + " " +
                        WebdavStatus.getStatusText(WebdavStatus.SC_NOT_FOUND);

                generatedXML.writeElement("D", "propstat", XMLWriter.OPENING);
                generatedXML.writeElement("D", "prop", XMLWriter.OPENING);

                while (propertiesNotFoundList.hasMoreElements()) {
                    generatedXML.writeElement
                        ("D", propertiesNotFoundList.nextElement(),
                         XMLWriter.NO_CONTENT);
                }

                generatedXML.writeElement("D", "prop", XMLWriter.CLOSING);
                generatedXML.writeElement("D", "status", XMLWriter.OPENING);
                generatedXML.writeText(status);
                generatedXML.writeElement("D", "status", XMLWriter.CLOSING);
                generatedXML.writeElement("D", "propstat", XMLWriter.CLOSING);

            }

            break;

        }

        generatedXML.writeElement("D", "response", XMLWriter.CLOSING);

    }


    /**
     * Print the lock discovery information associated with a path.
     *
     * @param path Path
     * @param generatedXML XML data to which the locks info will be appended
     * @return true if at least one lock was displayed
     */
    private boolean generateLockDiscovery
        (String path, XMLWriter generatedXML) {

        LockInfo resourceLock = resourceLocks.get(path);
        Enumeration<LockInfo> collectionLocksList = collectionLocks.elements();

        boolean wroteStart = false;

        if (resourceLock != null) {
            wroteStart = true;
            generatedXML.writeElement("D", "lockdiscovery", XMLWriter.OPENING);
            resourceLock.toXML(generatedXML);
        }

        while (collectionLocksList.hasMoreElements()) {
            LockInfo currentLock = collectionLocksList.nextElement();
            if (path.startsWith(currentLock.path)) {
                if (!wroteStart) {
                    wroteStart = true;
                    generatedXML.writeElement("D", "lockdiscovery",
                            XMLWriter.OPENING);
                }
                currentLock.toXML(generatedXML);
            }
        }

        if (wroteStart) {
            generatedXML.writeElement("D", "lockdiscovery", XMLWriter.CLOSING);
        } else {
            return false;
        }

        return true;

    }


    /**
     * Get creation date in ISO format.
     */
    private String getISOCreationDate(long creationDate) {
        return creationDateFormat.format(new Date(creationDate));
    }

    /**
     * Determines the methods normally allowed for the resource.
     *
     */
    private StringBuilder determineMethodsAllowed(DirContext dirContext,
                                                 HttpServletRequest req) {

        StringBuilder methodsAllowed = new StringBuilder();
        boolean exists = true;
        Object object = null;
        try {
            String path = getRelativePath(req);

            object = dirContext.lookup(path);
        } catch (NamingException e) {
            exists = false;
        }

        if (!exists) {
            methodsAllowed.append("OPTIONS, MKCOL, PUT, LOCK");
            return methodsAllowed;
        }

        methodsAllowed.append("OPTIONS, GET, HEAD, POST, DELETE");
        // Trace - assume disabled unless we can prove otherwise
        if (req instanceof RequestFacade &&
                ((RequestFacade) req).getAllowTrace()) {
            methodsAllowed.append(", TRACE");
        }
        methodsAllowed.append(", PROPPATCH, COPY, MOVE, LOCK, UNLOCK");

        if (listings) {
            methodsAllowed.append(", PROPFIND");
        }

        if (!(object instanceof DirContext)) {
            methodsAllowed.append(", PUT");
        }

        return methodsAllowed;
    }

    // --------------------------------------------------  LockInfo Inner Class


    /**
     * Holds a lock information.
     */
    private class LockInfo {


        // -------------------------------------------------------- Constructor


        /**
         * Constructor.
         */
        public LockInfo() {
            // Ignore
        }


        // ------------------------------------------------- Instance Variables


        String path = "/";
        String type = "write";
        String scope = "exclusive";
        int depth = 0;
        String owner = "";
        Vector<String> tokens = new Vector<String>();
        long expiresAt = 0;
        Date creationDate = new Date();


        // ----------------------------------------------------- Public Methods


        /**
         * Get a String representation of this lock token.
         */
        @Override
        public String toString() {

            StringBuilder result =  new StringBuilder("Type:");
            result.append(type);
            result.append("\nScope:");
            result.append(scope);
            result.append("\nDepth:");
            result.append(depth);
            result.append("\nOwner:");
            result.append(owner);
            result.append("\nExpiration:");
            result.append(FastHttpDateFormat.formatDate(expiresAt, null));
            Enumeration<String> tokensList = tokens.elements();
            while (tokensList.hasMoreElements()) {
                result.append("\nToken:");
                result.append(tokensList.nextElement());
            }
            result.append("\n");
            return result.toString();
        }


        /**
         * Return true if the lock has expired.
         */
        public boolean hasExpired() {
            return (System.currentTimeMillis() > expiresAt);
        }


        /**
         * Return true if the lock is exclusive.
         */
        public boolean isExclusive() {

            return (scope.equals("exclusive"));

        }


        /**
         * Get an XML representation of this lock token. This method will
         * append an XML fragment to the given XML writer.
         */
        public void toXML(XMLWriter generatedXML) {

            generatedXML.writeElement("D", "activelock", XMLWriter.OPENING);

            generatedXML.writeElement("D", "locktype", XMLWriter.OPENING);
            generatedXML.writeElement("D", type, XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "locktype", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "lockscope", XMLWriter.OPENING);
            generatedXML.writeElement("D", scope, XMLWriter.NO_CONTENT);
            generatedXML.writeElement("D", "lockscope", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "depth", XMLWriter.OPENING);
            if (depth == maxDepth) {
                generatedXML.writeText("Infinity");
            } else {
                generatedXML.writeText("0");
            }
            generatedXML.writeElement("D", "depth", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "owner", XMLWriter.OPENING);
            generatedXML.writeText(owner);
            generatedXML.writeElement("D", "owner", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "timeout", XMLWriter.OPENING);
            long timeout = (expiresAt - System.currentTimeMillis()) / 1000;
            generatedXML.writeText("Second-" + timeout);
            generatedXML.writeElement("D", "timeout", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "locktoken", XMLWriter.OPENING);
            Enumeration<String> tokensList = tokens.elements();
            while (tokensList.hasMoreElements()) {
                generatedXML.writeElement("D", "href", XMLWriter.OPENING);
                generatedXML.writeText("opaquelocktoken:"
                                       + tokensList.nextElement());
                generatedXML.writeElement("D", "href", XMLWriter.CLOSING);
            }
            generatedXML.writeElement("D", "locktoken", XMLWriter.CLOSING);

            generatedXML.writeElement("D", "activelock", XMLWriter.CLOSING);

        }


    }


    // --------------------------------------------- WebdavResolver Inner Class
    /**
     * Work around for XML parsers that don't fully respect
     * {@link DocumentBuilderFactory#setExpandEntityReferences(boolean)} when
     * called with <code>false</code>. External references are filtered out for
     * security reasons. See CVE-2007-5461.
     */
    private static class WebdavResolver implements EntityResolver {
        private ServletContext context;

        public WebdavResolver(ServletContext theContext) {
            context = theContext;
        }

        @Override
        public InputSource resolveEntity (String publicId, String systemId) {
            context.log(sm.getString("webdavservlet.enternalEntityIgnored",
                    publicId, systemId));
            return new InputSource(
                    new StringReader("Ignored external entity"));
        }
    }
}


// --------------------------------------------------------  WebdavStatus Class


/**
 * Wraps the HttpServletResponse class to abstract the
 * specific protocol used.  To support other protocols
 * we would only need to modify this class and the
 * WebDavRetCode classes.
 *
 * @author              Marc Eaddy
 * @version             1.0, 16 Nov 1997
 */
class WebdavStatus {


    // ----------------------------------------------------- Instance Variables


    /**
     * This Hashtable contains the mapping of HTTP and WebDAV
     * status codes to descriptive text.  This is a static
     * variable.
     */
    private static Hashtable<Integer,String> mapStatusCodes =
        new Hashtable<Integer,String>();


    // ------------------------------------------------------ HTTP Status Codes


    /**
     * Status code (200) indicating the request succeeded normally.
     */
    public static final int SC_OK = HttpServletResponse.SC_OK;


    /**
     * Status code (201) indicating the request succeeded and created
     * a new resource on the server.
     */
    public static final int SC_CREATED = HttpServletResponse.SC_CREATED;


    /**
     * Status code (202) indicating that a request was accepted for
     * processing, but was not completed.
     */
    public static final int SC_ACCEPTED = HttpServletResponse.SC_ACCEPTED;


    /**
     * Status code (204) indicating that the request succeeded but that
     * there was no new information to return.
     */
    public static final int SC_NO_CONTENT = HttpServletResponse.SC_NO_CONTENT;


    /**
     * Status code (301) indicating that the resource has permanently
     * moved to a new location, and that future references should use a
     * new URI with their requests.
     */
    public static final int SC_MOVED_PERMANENTLY =
        HttpServletResponse.SC_MOVED_PERMANENTLY;


    /**
     * Status code (302) indicating that the resource has temporarily
     * moved to another location, but that future references should
     * still use the original URI to access the resource.
     */
    public static final int SC_MOVED_TEMPORARILY =
        HttpServletResponse.SC_MOVED_TEMPORARILY;


    /**
     * Status code (304) indicating that a conditional GET operation
     * found that the resource was available and not modified.
     */
    public static final int SC_NOT_MODIFIED =
        HttpServletResponse.SC_NOT_MODIFIED;


    /**
     * Status code (400) indicating the request sent by the client was
     * syntactically incorrect.
     */
    public static final int SC_BAD_REQUEST =
        HttpServletResponse.SC_BAD_REQUEST;


    /**
     * Status code (401) indicating that the request requires HTTP
     * authentication.
     */
    public static final int SC_UNAUTHORIZED =
        HttpServletResponse.SC_UNAUTHORIZED;


    /**
     * Status code (403) indicating the server understood the request
     * but refused to fulfill it.
     */
    public static final int SC_FORBIDDEN = HttpServletResponse.SC_FORBIDDEN;


    /**
     * Status code (404) indicating that the requested resource is not
     * available.
     */
    public static final int SC_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;


    /**
     * Status code (500) indicating an error inside the HTTP service
     * which prevented it from fulfilling the request.
     */
    public static final int SC_INTERNAL_SERVER_ERROR =
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR;


    /**
     * Status code (501) indicating the HTTP service does not support
     * the functionality needed to fulfill the request.
     */
    public static final int SC_NOT_IMPLEMENTED =
        HttpServletResponse.SC_NOT_IMPLEMENTED;


    /**
     * Status code (502) indicating that the HTTP server received an
     * invalid response from a server it consulted when acting as a
     * proxy or gateway.
     */
    public static final int SC_BAD_GATEWAY =
        HttpServletResponse.SC_BAD_GATEWAY;


    /**
     * Status code (503) indicating that the HTTP service is
     * temporarily overloaded, and unable to handle the request.
     */
    public static final int SC_SERVICE_UNAVAILABLE =
        HttpServletResponse.SC_SERVICE_UNAVAILABLE;


    /**
     * Status code (100) indicating the client may continue with
     * its request.  This interim response is used to inform the
     * client that the initial part of the request has been
     * received and has not yet been rejected by the server.
     */
    public static final int SC_CONTINUE = 100;


    /**
     * Status code (405) indicating the method specified is not
     * allowed for the resource.
     */
    public static final int SC_METHOD_NOT_ALLOWED = 405;


    /**
     * Status code (409) indicating that the request could not be
     * completed due to a conflict with the current state of the
     * resource.
     */
    public static final int SC_CONFLICT = 409;


    /**
     * Status code (412) indicating the precondition given in one
     * or more of the request-header fields evaluated to false
     * when it was tested on the server.
     */
    public static final int SC_PRECONDITION_FAILED = 412;


    /**
     * Status code (413) indicating the server is refusing to
     * process a request because the request entity is larger
     * than the server is willing or able to process.
     */
    public static final int SC_REQUEST_TOO_LONG = 413;


    /**
     * Status code (415) indicating the server is refusing to service
     * the request because the entity of the request is in a format
     * not supported by the requested resource for the requested
     * method.
     */
    public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;


    // -------------------------------------------- Extended WebDav status code


    /**
     * Status code (207) indicating that the response requires
     * providing status for multiple independent operations.
     */
    public static final int SC_MULTI_STATUS = 207;
    // This one collides with HTTP 1.1
    // "207 Partial Update OK"


    /**
     * Status code (418) indicating the entity body submitted with
     * the PATCH method was not understood by the resource.
     */
    public static final int SC_UNPROCESSABLE_ENTITY = 418;
    // This one collides with HTTP 1.1
    // "418 Reauthentication Required"


    /**
     * Status code (419) indicating that the resource does not have
     * sufficient space to record the state of the resource after the
     * execution of this method.
     */
    public static final int SC_INSUFFICIENT_SPACE_ON_RESOURCE = 419;
    // This one collides with HTTP 1.1
    // "419 Proxy Reauthentication Required"


    /**
     * Status code (420) indicating the method was not executed on
     * a particular resource within its scope because some part of
     * the method's execution failed causing the entire method to be
     * aborted.
     */
    public static final int SC_METHOD_FAILURE = 420;


    /**
     * Status code (423) indicating the destination resource of a
     * method is locked, and either the request did not contain a
     * valid Lock-Info header, or the Lock-Info header identifies
     * a lock held by another principal.
     */
    public static final int SC_LOCKED = 423;


    // ------------------------------------------------------------ Initializer


    static {
        // HTTP 1.0 status Code
        addStatusCodeMap(SC_OK, "OK");
        addStatusCodeMap(SC_CREATED, "Created");
        addStatusCodeMap(SC_ACCEPTED, "Accepted");
        addStatusCodeMap(SC_NO_CONTENT, "No Content");
        addStatusCodeMap(SC_MOVED_PERMANENTLY, "Moved Permanently");
        addStatusCodeMap(SC_MOVED_TEMPORARILY, "Moved Temporarily");
        addStatusCodeMap(SC_NOT_MODIFIED, "Not Modified");
        addStatusCodeMap(SC_BAD_REQUEST, "Bad Request");
        addStatusCodeMap(SC_UNAUTHORIZED, "Unauthorized");
        addStatusCodeMap(SC_FORBIDDEN, "Forbidden");
        addStatusCodeMap(SC_NOT_FOUND, "Not Found");
        addStatusCodeMap(SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        addStatusCodeMap(SC_NOT_IMPLEMENTED, "Not Implemented");
        addStatusCodeMap(SC_BAD_GATEWAY, "Bad Gateway");
        addStatusCodeMap(SC_SERVICE_UNAVAILABLE, "Service Unavailable");
        addStatusCodeMap(SC_CONTINUE, "Continue");
        addStatusCodeMap(SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
        addStatusCodeMap(SC_CONFLICT, "Conflict");
        addStatusCodeMap(SC_PRECONDITION_FAILED, "Precondition Failed");
        addStatusCodeMap(SC_REQUEST_TOO_LONG, "Request Too Long");
        addStatusCodeMap(SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
        // WebDav Status Codes
        addStatusCodeMap(SC_MULTI_STATUS, "Multi-Status");
        addStatusCodeMap(SC_UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        addStatusCodeMap(SC_INSUFFICIENT_SPACE_ON_RESOURCE,
                         "Insufficient Space On Resource");
        addStatusCodeMap(SC_METHOD_FAILURE, "Method Failure");
        addStatusCodeMap(SC_LOCKED, "Locked");
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Returns the HTTP status text for the HTTP or WebDav status code
     * specified by looking it up in the static mapping.  This is a
     * static function.
     *
     * @param   nHttpStatusCode [IN] HTTP or WebDAV status code
     * @return  A string with a short descriptive phrase for the
     *                  HTTP status code (e.g., "OK").
     */
    public static String getStatusText(int nHttpStatusCode) {
        Integer intKey = Integer.valueOf(nHttpStatusCode);

        if (!mapStatusCodes.containsKey(intKey)) {
            return "";
        } else {
            return mapStatusCodes.get(intKey);
        }
    }


    // -------------------------------------------------------- Private Methods


    /**
     * Adds a new status code -> status text mapping.  This is a static
     * method because the mapping is a static variable.
     *
     * @param   nKey    [IN] HTTP or WebDAV status code
     * @param   strVal  [IN] HTTP status text
     */
    private static void addStatusCodeMap(int nKey, String strVal) {
        mapStatusCodes.put(Integer.valueOf(nKey), strVal);
    }

}


