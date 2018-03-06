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
package org.apache.tomcat.util.scan;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provides an abstraction for use by the various classes that need to scan
 * JARs. The classes provided by the JRE for accessing JARs ({@link java.util.jar.JarFile} and
 * {@link java.util.jar.JarInputStream}) have significantly different performance
 * characteristics depending on the form of the URL used to access the JAR.
 * For file based JAR {@link java.net.URL}s, {@link java.util.jar.JarFile} is faster but for non-file
 * based {@link java.net.URL}s, {@link java.util.jar.JarFile} creates a copy of the JAR in the
 * temporary directory so {@link java.util.jar.JarInputStream} is faster.
 */
public interface Jar {

    /**
     * Determines if a specific entry exists within the JAR.
     *
     * @param name  Entry to look for
     * @return      <code>true</code> if the specified entry exists else
     *               <code>false</code>
     */
    boolean entryExists(String name) throws IOException;


    /**
     * Obtain an {@link InputStream} for a given entry in a JAR. The caller is
     * responsible for closing the stream.
     *
     * @param name  Entry to obtain an {@link InputStream} for
     * @return      An {@link InputStream} for the specified entry or null if
     *              the entry does not exist
     */
    InputStream getInputStream(String name) throws IOException;

    /**
     * Close any resources associated with this JAR.
     */
    void close();

    /**
     * Moves the internal pointer to the next entry in the JAR.
     */
    void nextEntry();

    /**
     * Obtains the name of the current entry.
     *
     * @return  The entry name
     */
    String getEntryName();

    /**
     * Obtains the input stream for the current entry.
     *
     * @return  The input stream
     * @throws IOException  If the stream cannot be obtained
     */
    InputStream getEntryInputStream() throws IOException;

    /**
     * Resets the internal pointer used to track JAR entries to the beginning of
     * the JAR.
     *
     * @throws IOException  If the pointer cannot be reset
     */
    void reset() throws IOException;
}
