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
package org.apache.tomcat.util.http.fileupload;

/**
 * <p>High level API for processing file uploads.</p>
 *
 * <p>This class handles multiple files per single HTML widget, sent using
 * <code>multipart/mixed</code> encoding type, as specified by
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>.  Use {@link
 * #parseRequest(RequestContext)} to acquire a list
 * of {@link org.apache.tomcat.util.http.fileupload.FileItem FileItems} associated
 * with a given HTML widget.</p>
 *
 * <p>How the data for individual parts is stored is determined by the factory
 * used to create them; a given part may be in memory, on disk, or somewhere
 * else.</p>
 */
public class FileUpload
    extends FileUploadBase {

    // ----------------------------------------------------------- Data members

    /**
     * The factory to use to create new form items.
     */
    private FileItemFactory fileItemFactory;

    // ----------------------------------------------------------- Constructors

    /**
     * Constructs an uninitialised instance of this class.
     *
     * A factory must be
     * configured, using <code>setFileItemFactory()</code>, before attempting
     * to parse requests.
     *
     * @see #FileUpload(FileItemFactory)
     */
    public FileUpload() {
        super();
    }

    /**
     * Constructs an instance of this class which uses the supplied factory to
     * create <code>FileItem</code> instances.
     *
     * @see #FileUpload()
     * @param fileItemFactory The factory to use for creating file items.
     */
    public FileUpload(FileItemFactory fileItemFactory) {
        super();
        this.fileItemFactory = fileItemFactory;
    }

    // ----------------------------------------------------- Property accessors

    /**
     * Returns the factory class used when creating file items.
     *
     * @return The factory class for new file items.
     */
    @Override
    public FileItemFactory getFileItemFactory() {
        return fileItemFactory;
    }

    /**
     * Sets the factory class to use when creating file items.
     *
     * @param factory The factory class for new file items.
     */
    @Override
    public void setFileItemFactory(FileItemFactory factory) {
        this.fileItemFactory = factory;
    }

}
