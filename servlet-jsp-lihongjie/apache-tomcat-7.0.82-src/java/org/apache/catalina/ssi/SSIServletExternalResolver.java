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
package org.apache.catalina.ssi;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;
import org.apache.coyote.Constants;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.http.RequestUtil;

/**
 * An implementation of SSIExternalResolver that is used with servlets.
 * 
 * @author Dan Sandberg
 * @author David Becker
 */
public class SSIServletExternalResolver implements SSIExternalResolver {
    protected final String VARIABLE_NAMES[] = {"AUTH_TYPE", "CONTENT_LENGTH",
            "CONTENT_TYPE", "DOCUMENT_NAME", "DOCUMENT_URI",
            "GATEWAY_INTERFACE", "HTTP_ACCEPT", "HTTP_ACCEPT_ENCODING",
            "HTTP_ACCEPT_LANGUAGE", "HTTP_CONNECTION", "HTTP_HOST",
            "HTTP_REFERER", "HTTP_USER_AGENT", "PATH_INFO", "PATH_TRANSLATED",
            "QUERY_STRING", "QUERY_STRING_UNESCAPED", "REMOTE_ADDR",
            "REMOTE_HOST", "REMOTE_PORT", "REMOTE_USER", "REQUEST_METHOD",
            "REQUEST_URI", "SCRIPT_FILENAME", "SCRIPT_NAME", "SERVER_ADDR",
            "SERVER_NAME", "SERVER_PORT", "SERVER_PROTOCOL", "SERVER_SOFTWARE",
            "UNIQUE_ID"};
    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse res;
    protected boolean isVirtualWebappRelative;
    protected int debug;
    protected String inputEncoding;

    public SSIServletExternalResolver(ServletContext context,
            HttpServletRequest req, HttpServletResponse res,
            boolean isVirtualWebappRelative, int debug, String inputEncoding) {
        this.context = context;
        this.req = req;
        this.res = res;
        this.isVirtualWebappRelative = isVirtualWebappRelative;
        this.debug = debug;
        this.inputEncoding = inputEncoding;
    }


    @Override
    public void log(String message, Throwable throwable) {
        //We can't assume that Servlet.log( message, null )
        //is the same as Servlet.log( message ), since API
        //doesn't seem to say so.
        if (throwable != null) {
            context.log(message, throwable);
        } else {
            context.log(message);
        }
    }


    @Override
    public void addVariableNames(Collection<String> variableNames) {
        for (int i = 0; i < VARIABLE_NAMES.length; i++) {
            String variableName = VARIABLE_NAMES[i];
            String variableValue = getVariableValue(variableName);
            if (variableValue != null) {
                variableNames.add(variableName);
            }
        }
        Enumeration<String> e = req.getAttributeNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            if (!isNameReserved(name)) {
                variableNames.add(name);
            }
        }
    }


    protected Object getReqAttributeIgnoreCase(String targetName) {
        Object object = null;
        if (!isNameReserved(targetName)) {
            object = req.getAttribute(targetName);
            if (object == null) {
                Enumeration<String> e = req.getAttributeNames();
                while (e.hasMoreElements()) {
                    String name = e.nextElement();
                    if (targetName.equalsIgnoreCase(name)
                            && !isNameReserved(name)) {
                        object = req.getAttribute(name);
                        if (object != null) {
                            break;
                        }
                    }
                }
            }
        }
        return object;
    }


    protected boolean isNameReserved(String name) {
        return name.startsWith("java.") || name.startsWith("javax.")
                || name.startsWith("sun.");
    }


    @Override
    public void setVariableValue(String name, String value) {
        if (!isNameReserved(name)) {
            req.setAttribute(name, value);
        }
    }


    @Override
    public String getVariableValue(String name) {
        String retVal = null;
        Object object = getReqAttributeIgnoreCase(name);
        if (object != null) {
            retVal = object.toString();
        } else {
            retVal = getCGIVariable(name);
        }
        return retVal;
    }


    protected String getCGIVariable(String name) {
        String retVal = null;
        String[] nameParts = name.toUpperCase(Locale.ENGLISH).split("_");
        int requiredParts = 2;
        if (nameParts.length == 1) {
            if (nameParts[0].equals("PATH")) {
                requiredParts = 1;
            }
        }
        else if (nameParts[0].equals("AUTH")) {
            if (nameParts[1].equals("TYPE")) {
                retVal = req.getAuthType();
            }
        } else if(nameParts[0].equals("CONTENT")) {
            if (nameParts[1].equals("LENGTH")) {
                int contentLength = req.getContentLength();
                if (contentLength >= 0) {
                    retVal = Integer.toString(contentLength);
                }
            } else if (nameParts[1].equals("TYPE")) {
                retVal = req.getContentType();
            }
        } else if (nameParts[0].equals("DOCUMENT")) {
            if (nameParts[1].equals("NAME")) {
                String requestURI = req.getRequestURI();
                retVal = requestURI.substring(requestURI.lastIndexOf('/') + 1);
            } else if (nameParts[1].equals("URI")) {
                retVal = req.getRequestURI();
            }
        } else if (name.equalsIgnoreCase("GATEWAY_INTERFACE")) {
            retVal = "CGI/1.1";
        } else if (nameParts[0].equals("HTTP")) {
            if (nameParts[1].equals("ACCEPT")) {
                String accept = null;
                if (nameParts.length == 2) {
                    accept = "Accept";
                } else if (nameParts[2].equals("ENCODING")) {
                    requiredParts = 3;
                    accept = "Accept-Encoding";
                } else if (nameParts[2].equals("LANGUAGE")) {
                    requiredParts = 3;
                    accept = "Accept-Language";
                }
                if (accept != null) {
                    Enumeration<String> acceptHeaders = req.getHeaders(accept);
                    if (acceptHeaders != null)
                        if (acceptHeaders.hasMoreElements()) {
                            StringBuilder rv = new StringBuilder(
                                    acceptHeaders.nextElement());
                            while (acceptHeaders.hasMoreElements()) {
                                rv.append(", ");
                                rv.append(acceptHeaders.nextElement());
                            }
                        retVal = rv.toString();
                    }
                }
            }
            else if (nameParts[1].equals("CONNECTION")) {
                retVal = req.getHeader("Connection");
            }
            else if (nameParts[1].equals("HOST")) {
                retVal = req.getHeader("Host");
            }
            else if (nameParts[1].equals("REFERER")) {
                retVal = req.getHeader("Referer");
            }
            else if (nameParts[1].equals("USER"))
                if (nameParts.length == 3)
                    if (nameParts[2].equals("AGENT")) {
                        requiredParts = 3;
                        retVal = req.getHeader("User-Agent");
                    }

        } else if (nameParts[0].equals("PATH")) {
            if (nameParts[1].equals("INFO")) {
                retVal = req.getPathInfo();
            } else if (nameParts[1].equals("TRANSLATED")) {
                retVal = req.getPathTranslated();
            }
        } else if (nameParts[0].equals("QUERY")) {
            if (nameParts[1].equals("STRING")) {
                String queryString = req.getQueryString();
                if (nameParts.length == 2) {
                    //apache displays this as an empty string rather than (none)
                    retVal = nullToEmptyString(queryString);
                } else if (nameParts[2].equals("UNESCAPED")) {
                    requiredParts = 3;
                    if (queryString != null) {
                        String uriEncoding = null;
                        boolean useBodyEncodingForURI = false;
                
                        // Get encoding settings from request / connector if
                        // possible
                        String requestEncoding = req.getCharacterEncoding();
                        if (req instanceof Request) {
                            Connector connector =  ((Request)req).getConnector();
                            uriEncoding = connector.getURIEncoding();
                            useBodyEncodingForURI = connector.getUseBodyEncodingForURI();
                        }
                
                        String queryStringEncoding;

                        // If valid, apply settings from request / connector
                        if (useBodyEncodingForURI && requestEncoding != null) {
                            queryStringEncoding = requestEncoding;
                        } else if (uriEncoding != null) {
                            queryStringEncoding = uriEncoding;
                        } else {
                            // Use default as a last resort
                            queryStringEncoding = Constants.DEFAULT_CHARACTER_ENCODING;
                        }
                
                        try {
                            retVal = URLDecoder.decode(queryString, queryStringEncoding);
                        } catch (UnsupportedEncodingException e) {
                            retVal = queryString;
                        }
                    }
                }
            }
        } else if(nameParts[0].equals("REMOTE")) {
            if (nameParts[1].equals("ADDR")) {
                retVal = req.getRemoteAddr();
            } else if (nameParts[1].equals("HOST")) {
                retVal = req.getRemoteHost();
            } else if (nameParts[1].equals("IDENT")) {
                // Not implemented
            } else if (nameParts[1].equals("PORT")) {
                retVal = Integer.toString( req.getRemotePort());
            } else if (nameParts[1].equals("USER")) {
                retVal = req.getRemoteUser();
            }
        } else if(nameParts[0].equals("REQUEST")) {
            if (nameParts[1].equals("METHOD")) {
                retVal = req.getMethod();
            }
            else if (nameParts[1].equals("URI")) {
                // If this is an error page, get the original URI
                retVal = (String) req.getAttribute(
                        RequestDispatcher.FORWARD_REQUEST_URI);
                if (retVal == null) retVal=req.getRequestURI();
            }
        } else if (nameParts[0].equals("SCRIPT")) {
            String scriptName = req.getServletPath();
            if (nameParts[1].equals("FILENAME")) {
                retVal = context.getRealPath(scriptName);
            }
            else if (nameParts[1].equals("NAME")) {
                retVal = scriptName;
            }
        } else if (nameParts[0].equals("SERVER")) {
            if (nameParts[1].equals("ADDR")) {
                retVal = req.getLocalAddr();
            }
            if (nameParts[1].equals("NAME")) {
                retVal = req.getServerName();
            } else if (nameParts[1].equals("PORT")) {
                retVal = Integer.toString(req.getServerPort());
            } else if (nameParts[1].equals("PROTOCOL")) {
                retVal = req.getProtocol();
            } else if (nameParts[1].equals("SOFTWARE")) {
                StringBuilder rv = new StringBuilder(context.getServerInfo());
                rv.append(" ");
                rv.append(System.getProperty("java.vm.name"));
                rv.append("/");
                rv.append(System.getProperty("java.vm.version"));
                rv.append(" ");
                rv.append(System.getProperty("os.name"));
                retVal = rv.toString();
            }
        } else if (name.equalsIgnoreCase("UNIQUE_ID")) {
            retVal = req.getRequestedSessionId();
        }
        if (requiredParts != nameParts.length) return null;
            return retVal;
    }

    @Override
    public Date getCurrentDate() {
        return new Date();
    }


    protected String nullToEmptyString(String string) {
        String retVal = string;
        if (retVal == null) {
            retVal = "";
        }
        return retVal;
    }


    protected String getPathWithoutFileName(String servletPath) {
        String retVal = null;
        int lastSlash = servletPath.lastIndexOf('/');
        if (lastSlash >= 0) {
            //cut off file name
            retVal = servletPath.substring(0, lastSlash + 1);
        }
        return retVal;
    }


    protected String getPathWithoutContext(final String contextPath,
            final String servletPath) {
        if (servletPath.startsWith(contextPath)) {
            return servletPath.substring(contextPath.length());
        }
        return servletPath;
    }


    protected String getAbsolutePath(String path) throws IOException {
        String pathWithoutContext = SSIServletRequestUtil.getRelativePath(req);
        String prefix = getPathWithoutFileName(pathWithoutContext);
        if (prefix == null) {
            throw new IOException("Couldn't remove filename from path: "
                    + pathWithoutContext);
        }
        String fullPath = prefix + path;
        String retVal = RequestUtil.normalize(fullPath);
        if (retVal == null) {
            throw new IOException("Normalization yielded null on path: "
                    + fullPath);
        }
        return retVal;
    }


    protected ServletContextAndPath getServletContextAndPathFromNonVirtualPath(
            String nonVirtualPath) throws IOException {
        if (nonVirtualPath.startsWith("/") || nonVirtualPath.startsWith("\\")) {
            throw new IOException("A non-virtual path can't be absolute: "
                    + nonVirtualPath);
        }
        if (nonVirtualPath.indexOf("../") >= 0) {
            throw new IOException("A non-virtual path can't contain '../' : "
                    + nonVirtualPath);
        }
        String path = getAbsolutePath(nonVirtualPath);
        ServletContextAndPath csAndP = new ServletContextAndPath(
                context, path);
        return csAndP;
    }


    protected ServletContextAndPath getServletContextAndPathFromVirtualPath(
            String virtualPath) throws IOException {

        if (!virtualPath.startsWith("/") && !virtualPath.startsWith("\\")) {
            return new ServletContextAndPath(context,
                    getAbsolutePath(virtualPath));
        }

        String normalized = RequestUtil.normalize(virtualPath);
        if (isVirtualWebappRelative) {
            return new ServletContextAndPath(context, normalized);
        }

        ServletContext normContext = context.getContext(normalized);
        if (normContext == null) {
            throw new IOException("Couldn't get context for path: "
                    + normalized);
        }
        //If it's the root context, then there is no context element
        // to remove,
        // ie:
        // '/file1.shtml' vs '/appName1/file1.shtml'
        if (!isRootContext(normContext)) {
            String noContext = getPathWithoutContext(
                    normContext.getContextPath(), normalized);
            if (noContext == null) {
                throw new IOException(
                        "Couldn't remove context from path: "
                                + normalized);
            }
            return new ServletContextAndPath(normContext, noContext);
        }

        return new ServletContextAndPath(normContext, normalized);
    }


    //Assumes servletContext is not-null
    //Assumes that identity comparison will be true for the same context
    //Assuming the above, getContext("/") will be non-null as long as the root
    // context is
    // accessible.
    //If it isn't, then servletContext can't be the root context anyway, hence
    // they will
    // not match.
    protected boolean isRootContext(ServletContext servletContext) {
        return servletContext == servletContext.getContext("/");
    }


    protected ServletContextAndPath getServletContextAndPath(
            String originalPath, boolean virtual) throws IOException {
        ServletContextAndPath csAndP = null;
        if (debug > 0) {
            log("SSIServletExternalResolver.getServletContextAndPath( "
                    + originalPath + ", " + virtual + ")", null);
        }
        if (virtual) {
            csAndP = getServletContextAndPathFromVirtualPath(originalPath);
        } else {
            csAndP = getServletContextAndPathFromNonVirtualPath(originalPath);
        }
        return csAndP;
    }


    protected URLConnection getURLConnection(String originalPath,
            boolean virtual) throws IOException {
        ServletContextAndPath csAndP = getServletContextAndPath(originalPath,
                virtual);
        ServletContext context = csAndP.getServletContext();
        String path = csAndP.getPath();
        URL url = context.getResource(path);
        if (url == null) {
            throw new IOException("Context did not contain resource: " + path);
        }
        URLConnection urlConnection = url.openConnection();
        return urlConnection;
    }


    @Override
    public long getFileLastModified(String path, boolean virtual)
            throws IOException {
        long lastModified = 0;
        try {
            URLConnection urlConnection = getURLConnection(path, virtual);
            lastModified = urlConnection.getLastModified();
        } catch (IOException e) {
            // Ignore this. It will always fail for non-file based includes
        }
        return lastModified;
    }


    @Override
    public long getFileSize(String path, boolean virtual) throws IOException {
        long fileSize = -1;
        try {
            URLConnection urlConnection = getURLConnection(path, virtual);
            fileSize = urlConnection.getContentLength();
        } catch (IOException e) {
            // Ignore this. It will always fail for non-file based includes
        }
        return fileSize;
    }


    //We are making lots of unnecessary copies of the included data here. If
    //someone ever complains that this is slow, we should connect the included
    // stream to the print writer that SSICommand uses.
    @Override
    public String getFileText(String originalPath, boolean virtual)
            throws IOException {
        try {
            ServletContextAndPath csAndP = getServletContextAndPath(
                    originalPath, virtual);
            ServletContext context = csAndP.getServletContext();
            String path = csAndP.getPath();
            RequestDispatcher rd = context.getRequestDispatcher(path);
            if (rd == null) {
                throw new IOException(
                        "Couldn't get request dispatcher for path: " + path);
            }
            ByteArrayServletOutputStream basos =
                new ByteArrayServletOutputStream();
            ResponseIncludeWrapper responseIncludeWrapper =
                new ResponseIncludeWrapper(context, req, res, basos);
            rd.include(req, responseIncludeWrapper);
            //We can't assume the included servlet flushed its output
            responseIncludeWrapper.flushOutputStreamOrWriter();
            byte[] bytes = basos.toByteArray();

            //Assume platform default encoding unless otherwise specified
            String retVal;
            if (inputEncoding == null) {
                retVal = new String( bytes );
            } else {
                retVal = new String (bytes,
                        B2CConverter.getCharset(inputEncoding));
            }

            //make an assumption that an empty response is a failure. This is
            // a problem
            // if a truly empty file
            //were included, but not sure how else to tell.
            if (retVal.equals("") && !req.getMethod().equalsIgnoreCase(
                    org.apache.coyote.http11.Constants.HEAD)) {
                throw new IOException("Couldn't find file: " + path);
            }
            return retVal;
        } catch (ServletException e) {
            throw new IOException("Couldn't include file: " + originalPath
                    + " because of ServletException: " + e.getMessage());
        }
    }

    protected static class ServletContextAndPath {
        protected ServletContext servletContext;
        protected String path;


        public ServletContextAndPath(ServletContext servletContext,
                                     String path) {
            this.servletContext = servletContext;
            this.path = path;
        }


        public ServletContext getServletContext() {
            return servletContext;
        }


        public String getPath() {
            return path;
        }
    }
}