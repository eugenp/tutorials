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

import java.io.IOException;
import java.io.InputStream;

/**
 * <p> This interface provides access to a file or form item that was
 * received within a <code>multipart/form-data</code> POST request.
 * The items contents are retrieved by calling {@link #openStream()}.</p>
 * <p>Instances of this class are created by accessing the
 * iterator, returned by
 * {@link FileUploadBase#getItemIterator(RequestContext)}.</p>
 * <p><em>Note</em>: There is an interaction between the iterator and
 * its associated instances of {@link FileItemStream}: By invoking
 * {@link java.util.Iterator#hasNext()} on the iterator, you discard all data,
 * which hasn't been read so far from the previous data.</p>
 */
public interface FileItemStream extends FileItemHeadersSupport {

    /**
     * This exception is thrown, if an attempt is made to read
     * data from the {@link InputStream}, which has been returned
     * by {@link FileItemStream#openStream()}, after
     * {@link java.util.Iterator#hasNext()} has been invoked on the
     * iterator, which created the {@link FileItemStream}.
     */
    public static class ItemSkippedException extends IOException {

        /**
         * The exceptions serial version UID, which is being used
         * when serializing an exception instance.
         */
        private static final long serialVersionUID = -7280778431581963740L;

    }

    /**
     * Creates an {@link InputStream}, which allows to read the
     * items contents.
     *
     * @return The input stream, from which the items data may
     *   be read.
     * @throws IllegalStateException The method was already invoked on
     * this item. It is not possible to recreate the data stream.
     * @throws IOException An I/O error occurred.
     * @see ItemSkippedException
     */
    InputStream openStream() throws IOException;

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
     */
    String getName();

    /**
     * Returns the name of the field in the multipart form corresponding to
     * this file item.
     *
     * @return The name of the form field.
     */
    String getFieldName();

    /**
     * Determines whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @return <code>true</code> if the instance represents a simple form
     *         field; <code>false</code> if it represents an uploaded file.
     */
    boolean isFormField();

}
