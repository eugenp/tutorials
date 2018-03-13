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
package org.apache.naming.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.naming.NamingEntry;

/**
 * Extended FileDirContext implementation that allows to expose multiple
 * directories of the filesystem under a single webapp, a feature mainly used
 * for development with IDEs.
 * This should be used in conjunction with
 * {@link org.apache.catalina.loader.VirtualWebappLoader}.
 *
 * Sample context xml configuration:
 *
 * <code>
 * &lt;Context path="/mywebapp" docBase="/Users/theuser/mywebapp/src/main/webapp" >
 *   &lt;Resources className="org.apache.naming.resources.VirtualDirContext"
 *              extraResourcePaths="/pictures=/Users/theuser/mypictures,/movies=/Users/theuser/mymovies" />
 *   &lt;Loader className="org.apache.catalina.loader.VirtualWebappLoader"
 *              virtualClasspath="/Users/theuser/mywebapp/target/classes" />
 *   &lt;JarScanner scanAllDirectories="true" />
 * &lt;/Context>
 * </code>
 *
 *
 * <strong>This is not meant to be used for production.
 * Its meant to ease development with IDE's without the
 * need for fully republishing jars in WEB-INF/lib</strong>
 *
 *
 * @author Fabrizio Giustina
 */
public class VirtualDirContext extends FileDirContext {

    private static final org.apache.juli.logging.Log log=
            org.apache.juli.logging.LogFactory.getLog(VirtualDirContext.class);

    private String extraResourcePaths = "";
    private Map<String, List<String>> mappedResourcePaths;

    /**
     * <p>
     * Allows to map a path of the filesystem to a path in the webapp. Multiple
     * filesystem paths can be mapped to the same path in the webapp. Filesystem
     * path and virtual path must be separated by an equal sign. Pairs of paths
     * must be separated by a comma.
     * </p>
     * Example: <code>
     * /=/Users/slaurent/mywebapp/src/main/webapp,/pictures=/Users/slaurent/sharedpictures
     * </code>
     * <p>
     * The path to the docBase must not be added here, otherwise resources would
     * be listed twice.
     * </p>
     *
     * @param path The set of file system paths and virtual paths to map them to
     *             in the required format
     */
    public void setExtraResourcePaths(String path) {
        extraResourcePaths = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void allocate() {
        super.allocate();

        mappedResourcePaths = new HashMap<String, List<String>>();
        StringTokenizer tkn = new StringTokenizer(extraResourcePaths, ",");
        while (tkn.hasMoreTokens()) {
            String resSpec = tkn.nextToken();
            if (resSpec.length() > 0) {
                int idx = resSpec.indexOf('=');
                String path;
                if (idx <= 0) {
                    path = "";
                }
                else {
                    if (resSpec.startsWith("/=")) {
                        resSpec = resSpec.substring(1);
                        idx--;
                    }
                    path = resSpec.substring(0, idx);
                }
                File dir = new File(resSpec.substring(idx + 1));
                List<String> resourcePaths = mappedResourcePaths.get(path);
                if (resourcePaths == null) {
                    resourcePaths = new ArrayList<String>();
                    mappedResourcePaths.put(path, resourcePaths);
                }
                try {
                    resourcePaths.add(dir.getCanonicalPath());
                } catch (IOException e) {
                    log.warn(sm.getString("fileResources.canonical.fail", dir.getPath()));
                    // Fall back to the absolute path
                    resourcePaths.add(dir.getAbsolutePath());
                }
            }
        }
        if (mappedResourcePaths.isEmpty()) {
            mappedResourcePaths = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void release() {
        mappedResourcePaths = null;

        super.release();
    }

    @Override
    public Attributes getAttributes(String name) throws NamingException {

        NamingException initialException;
        try {
            // first try the normal processing, if it fails try with extra
            // resources
            Attributes attributes = super.getAttributes(name);
            return attributes;
        } catch (NamingException exc) {
            initialException = exc;
        }

        if (mappedResourcePaths != null) {
            for (Map.Entry<String, List<String>> mapping : mappedResourcePaths.entrySet()) {
                String path = mapping.getKey();
                List<String> dirList = mapping.getValue();
                String resourcesDir = dirList.get(0);
                if (name.equals(path)) {
                    File f = new File(resourcesDir);
                    f = validate(f, name, true, resourcesDir);
                    if (f != null) {
                        return new FileResourceAttributes(f);
                    }
                }
                path += "/";
                if (name.startsWith(path)) {
                    String res = name.substring(path.length());
                    File f = new File(resourcesDir, res);
                    f = validate(f, res, true, resourcesDir);
                    if (f != null) {
                        return new FileResourceAttributes(f);
                    }
                }
            }
        }
        throw initialException;
    }


    @Override
    protected File file(String name) {
        return file(name, true);
    }


    @Override
    protected File file(String name, boolean mustExist) {
        File file = super.file(name, true);
        if (file != null || mappedResourcePaths == null) {
            return file;
        }
        // If not found under docBase, try our other resources
        // Ensure name string begins with a slash
        if (name.length() > 0 && name.charAt(0) != '/') {
            name = "/" + name;
        }
        for (Map.Entry<String, List<String>> mapping : mappedResourcePaths.entrySet()) {
            String path = mapping.getKey();
            List<String> dirList = mapping.getValue();
            if (name.equals(path)) {
                for (String resourcesDir : dirList) {
                    file = new File(resourcesDir);
                    file = validate(file, name, true, resourcesDir);
                    if (file != null) {
                        return file;
                    }
                }
            }
            if (name.startsWith(path + "/")) {
                String res = name.substring(path.length());
                for (String resourcesDir : dirList) {
                    file = new File(resourcesDir, res);
                    file = validate(file, res, true, resourcesDir);
                    if (file != null) {
                        return file;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected List<NamingEntry> list(File file) {
        List<NamingEntry> entries = super.list(file);

        if (mappedResourcePaths != null && !mappedResourcePaths.isEmpty()) {
            Set<String> entryNames = new HashSet<String>(entries.size());
            for (NamingEntry entry : entries) {
                entryNames.add(entry.name);
            }
            // Add appropriate entries from the extra resource paths
            String absPath = file.getAbsolutePath();
            if (absPath.startsWith(getDocBase() + File.separator)) {
                String relPath = absPath.substring(getDocBase().length());
                String fsRelPath = relPath.replace(File.separatorChar, '/');
                for (Map.Entry<String, List<String>> mapping : mappedResourcePaths.entrySet()) {
                    String path = mapping.getKey();
                    List<String> dirList = mapping.getValue();
                    String res = null;
                    if (fsRelPath.equals(path)) {
                        res = "";
                    } else if (fsRelPath.startsWith(path + "/")) {
                        res = relPath.substring(path.length());
                    }
                    if (res != null) {
                        for (String resourcesDir : dirList) {
                            File f = new File(resourcesDir, res);
                            f = validate(f, res, true, resourcesDir);
                            if (f != null && f.isDirectory()) {
                                List<NamingEntry> virtEntries = super.list(f);
                                for (NamingEntry entry : virtEntries) {
                                    // filter duplicate
                                    if (!entryNames.contains(entry.name)) {
                                        entryNames.add(entry.name);
                                        entries.add(entry);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        return entries;
    }

    @Override
    protected Object doLookup(String name) {

        Object retSuper = super.doLookup(name);
        if (retSuper != null || mappedResourcePaths == null) {
            return retSuper;
        }

        // Perform lookup using the extra resource paths
        for (Map.Entry<String, List<String>> mapping : mappedResourcePaths.entrySet()) {
            String path = mapping.getKey();
            List<String> dirList = mapping.getValue();
            if (name.equals(path)) {
                for (String resourcesDir : dirList) {
                    File f = new File(resourcesDir);
                    f = validate(f, name, true, resourcesDir);
                    if (f != null) {
                        if (f.isFile()) {
                            return new FileResource(f);
                        }
                        else {
                            // never goes here, if f is a directory the super
                            // implementation already returned a value
                        }
                    }
                }
            }
            path += "/";
            if (name.startsWith(path)) {
                String res = name.substring(path.length());
                for (String resourcesDir : dirList) {
                    File f = new File(resourcesDir, res);
                    f = validate(f, res, true, resourcesDir);
                    if (f != null) {
                        if (f.isFile()) {
                            return new FileResource(f);
                        }
                        else {
                            // never goes here, if f is a directory the super
                            // implementation already returned a value
                        }
                    }
                }
            }
        }
        return retSuper;
    }

    @Override
    protected String doGetRealPath(String path) {
        File file = file(path);
        if (null != file) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }


    protected File validate(File file, String name, boolean mustExist, String absoluteBase) {
        return validate(file, name, mustExist, normalize(absoluteBase), absoluteBase);
    }
}
