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
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Implementation of {@link Jar} that is optimised for file based JAR URLs (e.g
 * URLs of the form jar:file:...).
 */
public class FileUrlJar implements Jar {

    private JarFile jarFile;
    private Enumeration<JarEntry> entries;
    private JarEntry entry = null;

    public FileUrlJar(URL url) throws IOException {
        JarURLConnection jarConn = (JarURLConnection) url.openConnection();
        jarConn.setUseCaches(false);
        jarFile = jarConn.getJarFile();
    }

    @Override
    public boolean entryExists(String name) {
        ZipEntry entry = jarFile.getEntry(name);
        return entry != null;
    }

    @Override
    public InputStream getInputStream(String name) throws IOException {
        ZipEntry entry = jarFile.getEntry(name);
        if (entry == null) {
            return null;
        } else {
            return jarFile.getInputStream(entry);
        }
    }

    @Override
    public void close() {
        if (jarFile != null) {
            try {
                jarFile.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    @Override
    public void nextEntry() {
        if (entries == null) {
            entries = jarFile.entries();
        }
        if (entries.hasMoreElements()) {
            entry = entries.nextElement();
        } else {
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
        if (entry == null) {
            return null;
        } else {
            return jarFile.getInputStream(entry);
        }
    }

    @Override
    public void reset() throws IOException {
        entries = null;
        entry = null;
    }
}
