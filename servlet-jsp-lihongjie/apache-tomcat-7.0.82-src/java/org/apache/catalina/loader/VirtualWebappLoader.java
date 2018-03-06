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
package org.apache.catalina.loader;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.catalina.LifecycleException;

/**
 * A WebappLoader that allows a customized classpath to be added
 * through configuration in context xml. Any additional classpath entry will be
 * added to the default webapp classpath, making easy to emulate a standard
 * webapp without the need for assembly all the webapp dependencies as jars in
 * WEB-INF/lib.
 *
 * <pre>
 * &lt;Context docBase="\webapps\mydocbase">
 *   &lt;Loader className="org.apache.catalina.loader.VirtualWebappLoader"
 *              virtualClasspath="/dir/classes;/somedir/somejar.jar;
 *                /somedir/*.jar"/>
 * &lt;/Context>
 * </pre>
 *
 * <p>The <code>*.jar</code> suffix can be used to include all JAR files in a
 * certain directory. If a file or a directory does not exist, it will be
 * skipped.
 * </p>
 *
 *
 * @author Fabrizio Giustina
 */
public class VirtualWebappLoader extends WebappLoader {

    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( VirtualWebappLoader.class );

    /**
     * <code>;</code> separated list of additional path elements.
     */
    private String virtualClasspath = "";

    /**
     * Construct a new WebappLoader with no defined parent class loader (so that
     * the actual parent will be the system class loader).
     */
    public VirtualWebappLoader() {
        super();
    }

    /**
     * Construct a new WebappLoader with the specified class loader to be
     * defined as the parent of the ClassLoader we ultimately create.
     *
     * @param parent The parent class loader
     */
    public VirtualWebappLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * <code>virtualClasspath</code> attribute that will be automatically set
     * from the <code>Context</code> <code>virtualClasspath</code> attribute
     * from the context xml file.
     * @param path <code>;</code> separated list of path elements.
     */
    public void setVirtualClasspath(String path) {
        virtualClasspath = path;
    }

    /**
     * @return Returns searchVirtualFirst.
     */
    public boolean getSearchVirtualFirst() {
        return getSearchExternalFirst();
    }

    /**
     * @param searchVirtualFirst Whether the virtual class path should be searched before the webapp
     */
    public void setSearchVirtualFirst(boolean searchVirtualFirst) {
        setSearchExternalFirst(searchVirtualFirst);
    }

    /**
     * Implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected void startInternal() throws LifecycleException {

        // just add any jar/directory set in virtual classpath to the
        // repositories list before calling start on the standard WebappLoader
        StringTokenizer tkn = new StringTokenizer(virtualClasspath, ";");
        Set<String> set = new LinkedHashSet<String>();
        while (tkn.hasMoreTokens()) {
            String token = tkn.nextToken().trim();

            if (token.isEmpty()) {
                continue;
            }

            if (log.isDebugEnabled())
                log.debug(sm.getString("virtualWebappLoader.token", token));

            if (token.endsWith("*.jar")) {
                // glob
                token = token.substring(0, token.length() - "*.jar".length());

                File directory = new File(token);
                if (!directory.isDirectory()) {
                    if (log.isDebugEnabled()) {
                        log.debug(sm.getString(
                                "virtualWebappLoader.token.notDirectory",
                                directory.getAbsolutePath()));
                    }
                    continue;
                }
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString(
                            "virtualWebappLoader.token.glob.dir",
                            directory.getAbsolutePath()));
                }
                String filenames[] = directory.list();
                if (filenames != null) {
                    Arrays.sort(filenames);
                    for (int j = 0; j < filenames.length; j++) {
                        String filename = filenames[j].toLowerCase(Locale.ENGLISH);
                        if (!filename.endsWith(".jar"))
                            continue;
                        File file = new File(directory, filenames[j]);
                        if (!file.isFile()) {
                            if (log.isDebugEnabled()) {
                                log.debug(sm.getString(
                                        "virtualWebappLoader.token.notFile",
                                        file.getAbsolutePath()));
                            }
                            continue;
                        }
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString(
                                    "virtualWebappLoader.token.file",
                                    file.getAbsolutePath()));
                        }
                        set.add(file.toURI().toString());
                    }
                }
            } else {
                // single file or directory
                File file = new File(token);
                if (!file.exists()) {
                    if (log.isDebugEnabled()) {
                        log.debug(sm.getString(
                                "virtualWebappLoader.token.notExists",
                                file.getAbsolutePath()));
                    }
                    continue;
                }
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString(
                            "virtualWebappLoader.token.file",
                            file.getAbsolutePath()));
                }
                set.add(file.toURI().toString());
            }
        }

        for (String repository: set) {
            addRepository(repository);
        }

        super.startInternal();
    }

}
