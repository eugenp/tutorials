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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p> This class represents a file or form item that was received within a
 * <code>multipart/form-data</code> POST request.
 *
 * <p> After retrieving an instance of this class from a {@link
 * org.apache.tomcat.util.http.fileupload.FileUpload FileUpload} instance (see
 * {@link org.apache.tomcat.util.http.fileupload.FileUpload
 * #parseRequest(RequestContext)}), you may
 * either request all contents of the file at once using {@link #get()} or
 * request an {@link java.io.InputStream InputStream} with
 * {@link #getInputStream()} and process the file without attempting to load
 * it into memory, which may come handy with large files.
 *
 * <p> While this interface does not extend
 * <code>javax.activation.DataSource</code> per se (to avoid a seldom used
 * dependency), several of the defined methods are specifically defined with
 * the same signatures as methods in that interface. This allows an
 * implementation of this interface to also implement
 * <code>javax.activation.DataSource</code> with minimal additional work.
 *
 * @since 1.3 additionally implements FileItemHeadersSupport
 */
public interface FileItem extends FileItemHeadersSupport {

    // ------------------------------- Methods from javax.activation.DataSource

    /**
     * Returns an {@link java.io.InputStream InputStream} that can be
     * used to retrieve the contents of the file.
     *
     * @return An {@link java.io.InputStream InputStream} that can be
     *         used to retrieve the contents of the file.
     *
     * @throws IOException if an error occurs.
     */
    InputStream getInputStream() throws IOException;

    /**
     * Returns the content type passed by the browser or <code>null</code> if
     * not defined.
     *
     * @return The content type passed by the browser or <code>null</code> if
     *         not defined.
     */
    String getContentType();

    /**
     * Returns the original filename in the client's filesystem, as provided by
     * the browser (or other client software). In most cases, this will be the
     * base file name, without path information. However, some clients, such as
     * the Opera browser, do include path information.
     *
     * @return The original filename in the client's filesystem.
     * @throws InvalidFileNameException The file name contains a NUL character,
     *   which might be an indicator of a security attack. If you intend to
     *   use the file name anyways, catch the exception and use
     *   InvalidFileNameException#getName().
     */
    String getName();

    // ------------------------------------------------------- FileItem methods

    /**
     * Provides a hint as to whether or not the file contents will be read
     * from memory.
     *
     * @return <code>true</code> if the file contents will be read from memory;
     *         <code>false</code> otherwise.
     */
    boolean isInMemory();

    /**
     * Returns the size of the file item.
     *
     * @return The size of the file item, in bytes.
     */
    long getSize();

    /**
     * Returns the contents of the file item as an array of bytes.
     *
     * @return The contents of the file item as an array of bytes.
     */
    byte[] get();

    /**
     * Returns the contents of the file item as a String, using the specified
     * encoding.  This method uses {@link #get()} to retrieve the
     * contents of the item.
     *
     * @param encoding The character encoding to use.
     *
     * @return The contents of the item, as a string.
     *
     * @throws UnsupportedEncodingException if the requested character
     *                                      encoding is not available.
     */
    String getString(String encoding) throws UnsupportedEncodingException;

    /**
     * Returns the contents of the file item as a String, using the default
     * character encoding.  This method uses {@link #get()} to retrieve the
     * contents of the item.
     *
     * @return The contents of the item, as a string.
     */
    String getString();

    /**
     * A convenience method to write an uploaded item to disk. The client code
     * is not concerned with whether or not the item is stored in memory, or on
     * disk in a temporary location. They just want to write the uploaded item
     * to a file.
     * <p>
     * This method is not guaranteed to succeed if called more than once for
     * the same item. This allows a particular implementation to use, for
     * example, file renaming, where possible, rather than copying all of the
     * underlying data, thus gaining a significant performance benefit.
     *
     * @param file The <code>File</code> into which the uploaded item should
     *             be stored.
     *
     * @throws Exception if an error occurs.
     */
    void write(File file) throws Exception;

    /**
     * Deletes the underlying storage for a file item, including deleting any
     * associated temporary disk file. Although this storage will be deleted
     * automatically when the <code>FileItem</code> instance is garbage
     * collected, this method can be used to ensure that this is done at an
     * earlier time, thus preserving system resources.
     */
    void delete();

    /**
     * Returns the name of the field in the multipart form corresponding to
     * this file item.
     *
     * @return The name of the form field.
     */
    String getFieldName();

    /**
     * Sets the field name used to reference this file item.
     *
     * @param name The name of the form field.
     */
    void setFieldName(String name);

    /**
     * Determines whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @return <code>true</code> if the instance represents a simple form
     *         field; <code>false</code> if it represents an uploaded file.
     */
    boolean isFormField();

    /**
     * Specifies whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @param state <code>true</code> if the instance represents a simple form
     *              field; <code>false</code> if it represents an uploaded file.
     */
    void setFormField(boolean state);

    /**
     * Returns an {@link java.io.OutputStream OutputStream} that can
     * be used for storing the contents of the file.
     *
     * @return An {@link java.io.OutputStream OutputStream} that can be used
     *         for storing the contensts of the file.
     *
     * @throws IOException if an error occurs.
     */
    OutputStream getOutputStream() throws IOException;

}
