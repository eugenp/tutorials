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
package org.apache.catalina.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.juli.logging.Log;
import org.apache.tomcat.util.res.StringManager;

/**
 * Custom subclass of <code>ObjectInputStream</code> that loads from the
 * class loader for this web application.  This allows classes defined only
 * with the web application to be found correctly.
 *
 * @author Craig R. McClanahan
 * @author Bip Thelin
 */
public final class CustomObjectInputStream extends ObjectInputStream {

    private static final StringManager sm = StringManager.getManager(CustomObjectInputStream.class);

    private static final WeakHashMap<ClassLoader, Set<String>> reportedClassCache =
            new WeakHashMap<ClassLoader, Set<String>>();

    /**
     * The class loader we will use to resolve classes.
     */
    private ClassLoader classLoader = null;
    private final Set<String> reportedClasses;
    private final Log log;

    private final Pattern allowedClassNamePattern;
    private final String allowedClassNameFilter;
    private final boolean warnOnFailure;


    /**
     * Construct a new instance of CustomObjectInputStream without any filtering
     * of deserialized classes.
     *
     * @param stream The input stream we will read from
     * @param classLoader The class loader used to instantiate objects
     *
     * @exception IOException if an input/output error occurs
     */
    public CustomObjectInputStream(InputStream stream, ClassLoader classLoader) throws IOException {
        this(stream, classLoader, null, null, false);
    }


    /**
     * Construct a new instance of CustomObjectInputStream with filtering of
     * deserialized classes.
     *
     * @param stream The input stream we will read from
     * @param classLoader The class loader used to instantiate objects
     * @param log The logger to use to report any issues. It may only be null if
     *            the filterMode does not require logging
     * @param allowedClassNamePattern The regular expression to use to filter
     *                                deserialized classes. The fully qualified
     *                                class name must match this pattern for
     *                                deserialization to be allowed if filtering
     *                                is enabled.
     * @param warnOnFailure Should any failures be logged?
     *
     * @exception IOException if an input/output error occurs
     */
    public CustomObjectInputStream(InputStream stream, ClassLoader classLoader,
            Log log, Pattern allowedClassNamePattern, boolean warnOnFailure)
            throws IOException {
        super(stream);
        if (log == null && allowedClassNamePattern != null && warnOnFailure) {
            throw new IllegalArgumentException(
                    sm.getString("customObjectInputStream.logRequired"));
        }
        this.classLoader = classLoader;
        this.log = log;
        this.allowedClassNamePattern = allowedClassNamePattern;
        if (allowedClassNamePattern == null) {
            this.allowedClassNameFilter = null;
        } else {
            this.allowedClassNameFilter = allowedClassNamePattern.toString();
        }
        this.warnOnFailure = warnOnFailure;

        Set<String> reportedClasses;
        synchronized (reportedClassCache) {
            reportedClasses = reportedClassCache.get(classLoader);
            if (reportedClasses == null) {
                reportedClasses = Collections.newSetFromMap(new ConcurrentHashMap<String,Boolean>());
                reportedClassCache.put(classLoader, reportedClasses);
            }
        }
        this.reportedClasses = reportedClasses;
    }


    /**
     * Load the local class equivalent of the specified stream class
     * description, by using the class loader assigned to this Context.
     *
     * @param classDesc Class description from the input stream
     *
     * @exception ClassNotFoundException if this class cannot be found
     * @exception IOException if an input/output error occurs
     */
    @Override
    public Class<?> resolveClass(ObjectStreamClass classDesc)
        throws ClassNotFoundException, IOException {

        String name = classDesc.getName();
        if (allowedClassNamePattern != null) {
            boolean allowed = allowedClassNamePattern.matcher(name).matches();
            if (!allowed) {
                boolean doLog = warnOnFailure && reportedClasses.add(name);
                String msg = sm.getString("customObjectInputStream.nomatch", name, allowedClassNameFilter);
                if (doLog) {
                    log.warn(msg);
                } else if (log.isDebugEnabled()) {
                    log.debug(msg);
                }
                throw new InvalidClassException(msg);
            }
        }

        try {
            return Class.forName(name, false, classLoader);
        } catch (ClassNotFoundException e) {
            try {
                // Try also the superclass because of primitive types
                return super.resolveClass(classDesc);
            } catch (ClassNotFoundException e2) {
                // Rethrow original exception, as it can have more information
                // about why the class was not found. BZ 48007
                throw e;
            }
        }
    }


    /**
     * Return a proxy class that implements the interfaces named in a proxy
     * class descriptor. Do this using the class loader assigned to this
     * Context.
     */
    @Override
    protected Class<?> resolveProxyClass(String[] interfaces)
            throws IOException, ClassNotFoundException {

        Class<?>[] cinterfaces = new Class[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            cinterfaces[i] = classLoader.loadClass(interfaces[i]);
        }

        try {
            return Proxy.getProxyClass(classLoader, cinterfaces);
        } catch (IllegalArgumentException e) {
            throw new ClassNotFoundException(null, e);
        }
    }
}
