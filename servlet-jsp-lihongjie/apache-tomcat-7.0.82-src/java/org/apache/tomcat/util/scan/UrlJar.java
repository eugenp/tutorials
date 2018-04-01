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
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarEntry;

/**
 * Implementation of {@link Jar} that is optimised for non-file based JAR URLs
 * (e.g. JNDI based URLs of the form jar:jndi:...).
 */
public class UrlJar implements Jar {

    private NonClosingJarInputStream jarInputStream = null;
    private URL url = null;
    private JarEntry entry = null;

    public UrlJar(URL url) throws IOException {
        this.url = url;
        this.jarInputStream = createJarInputStream();
    }

    @Override
    public boolean entryExists(String name) throws IOException {
        JarEntry entry = jarInputStream.getNextJarEntry();
        while (entry != null) {
            if (name.equals(entry.getName())) {
                break;
            }
            entry = jarInputStream.getNextJarEntry();
        }

        return entry != null;
    }

    @Override
    public InputStream getInputStream(String name) throws IOException {
        JarEntry entry = jarInputStream.getNextJarEntry();
        while (entry != null) {
            if (name.equals(entry.getName())) {
                break;
            }
            entry = jarInputStream.getNextJarEntry();
        }

        if (entry == null) {
            return null;
        } else {
            return jarInputStream;
        }
    }

    @Override
    public void close() {
        if (jarInputStream != null) {
            try {
                jarInputStream.reallyClose();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }

    private NonClosingJarInputStream createJarInputStream() throws IOException {
        JarURLConnection jarConn = (JarURLConnection) url.openConnection();
        URL resourceURL = jarConn.getJarFileURL();
        URLConnection resourceConn = resourceURL.openConnection();
        resourceConn.setUseCaches(false);
        return new NonClosingJarInputStream(resourceConn.getInputStream());
    }

    @Override
    public void nextEntry() {
        try {
            entry = jarInputStream.getNextJarEntry();
        } catch (IOException ioe) {
            entry = null;
        }
    }

    @Override
    public String getEntryName() {
        if (entry == null) {
            return null;
        } else {
            return entry.getName();
        }
    }

    @Override
    public InputStream getEntryInputStream() throws IOException {
        return jarInputStream;
    }

    @Override
    public void reset() throws IOException {
        close();
        jarInputStream = createJarInputStream();
    }
}
