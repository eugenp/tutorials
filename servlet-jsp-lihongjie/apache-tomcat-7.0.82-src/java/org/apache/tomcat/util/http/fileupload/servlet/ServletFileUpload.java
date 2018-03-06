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
package org.apache.tomcat.util.http.fileupload.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;


/**
 * <p>High level API for processing file uploads.</p>
 *
 * <p>This class handles multiple files per single HTML widget, sent using
 * <code>multipart/mixed</code> encoding type, as specified by
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>.  Use {@link
 * #parseRequest(org.apache.tomcat.util.http.fileupload.RequestContext)} to
 * acquire a list of {@link org.apache.tomcat.util.http.fileupload.FileItem}s
 * associated with a given HTML widget.</p>
 *
 * <p>How the data for individual parts is stored is determined by the factory
 * used to create them; a given part may be in memory, on disk, or somewhere
 * else.</p>
 */
public class ServletFileUpload extends FileUpload {

    /**
     * Constant for HTTP POST method.
     */
    private static final String POST_METHOD = "POST";

    // ---------------------------------------------------------- Class methods

    /**
     * Utility method that determines whether the request contains multipart
     * content.
     *
     * @param request The servlet request to be evaluated. Must be non-null.
     *
     * @return <code>true</code> if the request is multipart;
     *         <code>false</code> otherwise.
     */
    public static final boolean isMultipartContent(
            HttpServletRequest request) {
        if (!POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        return FileUploadBase.isMultipartContent(new ServletRequestContext(request));
    }

    // ----------------------------------------------------------- Constructors

    /**
     * Constructs an uninitialised instance of this class. A factory must be
     * configured, using <code>setFileItemFactory()</code>, before attempting
     * to parse requests.
     *
     * @see FileUpload#FileUpload(FileItemFactory)
     */
    public ServletFileUpload() {
        super();
    }

    /**
     * Constructs an instance of this class which uses the supplied factory to
     * create <code>FileItem</code> instances.
     *
     * @see FileUpload#FileUpload()
     * @param fileItemFactory The factory to use for creating file items.
     */
    public ServletFileUpload(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }

    // --------------------------------------------------------- Public methods

    /**
     * Processes an <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>
     * compliant <code>multipart/form-data</code> stream.
     *
     * @param request The servlet request to be parsed.
     *
     * @return A map of <code>FileItem</code> instances parsed from the request.
     *
     * @throws FileUploadException if there are problems reading/parsing
     *                             the request or storing files.
     *
     * @since 1.3
     */
    public Map<String, List<FileItem>> parseParameterMap(HttpServletRequest request)
            throws FileUploadException {
        return parseParameterMap(new ServletRequestContext(request));
    }

    /**
     * Processes an <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>
     * compliant <code>multipart/form-data</code> stream.
     *
     * @param request The servlet request to be parsed.
     *
     * @return An iterator to instances of <code>FileItemStream</code>
     *         parsed from the request, in the order that they were
     *         transmitted.
     *
     * @throws FileUploadException if there are problems reading/parsing
     *                             the request or storing files.
     * @throws IOException An I/O error occurred. This may be a network
     *   error while communicating with the client or a problem while
     *   storing the uploaded content.
     */
    public FileItemIterator getItemIterator(HttpServletRequest request)
    throws FileUploadException, IOException {
        return super.getItemIterator(new ServletRequestContext(request));
    }

}
