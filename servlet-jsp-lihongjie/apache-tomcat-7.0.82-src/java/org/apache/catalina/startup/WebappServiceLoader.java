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
package org.apache.catalina.startup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.catalina.Context;
import org.apache.tomcat.util.buf.UriUtil;

/**
 * A variation of Java's JAR ServiceLoader that respects exclusion rules for
 * web applications.
 * <p/>
 * Primarily intended for use loading ServletContainerInitializers as defined
 * by Servlet 8.2.4. This implementation does not attempt lazy loading as the
 * container is required to introspect all implementations discovered.
 * <p/>
 * If the ServletContext defines ORDERED_LIBS, then only JARs in WEB-INF/lib
 * that are named in that set will be included in the search for
 * provider configuration files; if ORDERED_LIBS is not defined then
 * all JARs will be searched for provider configuration files. Providers
 * defined by resources in the parent ClassLoader will always be returned.
 * <p/>
 * Provider classes will be loaded using the context's ClassLoader.
 *
 * @see javax.servlet.ServletContainerInitializer
 * @see java.util.ServiceLoader
 */
public class WebappServiceLoader<T> {
    private static final String LIB = "/WEB-INF/lib/";
    private static final String SERVICES = "META-INF/services/";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final Context context;
    private final ServletContext servletContext;
    private final Pattern containerSciFilterPattern;

    /**
     * Construct a loader to load services from a ServletContext.
     *
     * @param context the context to use
     */
    public WebappServiceLoader(Context context) {
        this.context = context;
        this.servletContext = context.getServletContext();
        String containerSciFilter = context.getContainerSciFilter();
        if (containerSciFilter != null && containerSciFilter.length() > 0) {
            containerSciFilterPattern = Pattern.compile(containerSciFilter);
        } else {
            containerSciFilterPattern = null;
        }
    }

    /**
     * Load the providers for a service type.
     *
     * @param serviceType the type of service to load
     * @return an unmodifiable collection of service providers
     * @throws IOException if there was a problem loading any service
     */
    public List<T> load(Class<T> serviceType) throws IOException {
        String configFile = SERVICES + serviceType.getName();

        LinkedHashSet<String> applicationServicesFound = new LinkedHashSet<String>();
        LinkedHashSet<String> containerServicesFound = new LinkedHashSet<String>();

        ClassLoader loader = servletContext.getClassLoader();

        // if the ServletContext has ORDERED_LIBS, then use that to specify the
        // set of JARs from WEB-INF/lib that should be used for loading services
        @SuppressWarnings("unchecked")
        List<String> orderedLibs =
                (List<String>) servletContext.getAttribute(ServletContext.ORDERED_LIBS);
        if (orderedLibs != null) {
            // handle ordered libs directly, ...
            for (String lib : orderedLibs) {
                URL jarUrl = servletContext.getResource(LIB + lib);
                if (jarUrl == null) {
                    // should not happen, just ignore
                    continue;
                }

                String base = jarUrl.toExternalForm();
                URL url;
                if (base.endsWith("/")) {
                    url = new URL(base + configFile);
                } else {
                    url = UriUtil.buildJarUrl(base, configFile);
                }
                try {
                    parseConfigFile(applicationServicesFound, url);
                } catch (FileNotFoundException e) {
                    // no provider file found, this is OK
                }
            }

            // and the parent ClassLoader for all others
            loader = context.getParentClassLoader();
        }

        Enumeration<URL> resources;
        if (loader == null) {
            resources = ClassLoader.getSystemResources(configFile);
        } else {
            resources = loader.getResources(configFile);
        }
        while (resources.hasMoreElements()) {
            parseConfigFile(containerServicesFound, resources.nextElement());
        }

        // Filter the discovered container SCIs if required
        if (containerSciFilterPattern != null) {
            Iterator<String> iter = containerServicesFound.iterator();
            while (iter.hasNext()) {
                if (containerSciFilterPattern.matcher(iter.next()).find()) {
                    iter.remove();
                }
            }
        }

        // Add the application services after the container services to ensure
        // that the container services are loaded first
        containerServicesFound.addAll(applicationServicesFound);

        // load the discovered services
        if (containerServicesFound.isEmpty()) {
            return Collections.emptyList();
        }
        return loadServices(serviceType, containerServicesFound);
    }

    private void parseConfigFile(LinkedHashSet<String> servicesFound, URL url)
            throws IOException {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = url.openStream();
            InputStreamReader in = new InputStreamReader(is, UTF8);
            reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                int i = line.indexOf('#');
                if (i >= 0) {
                    line = line.substring(0, i);
                }
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                servicesFound.add(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    private List<T> loadServices(Class<T> serviceType, LinkedHashSet<String> servicesFound)
            throws IOException {
        ClassLoader loader = servletContext.getClassLoader();
        List<T> services = new ArrayList<T>(servicesFound.size());
        for (String serviceClass : servicesFound) {
            try {
                Class<?> clazz = Class.forName(serviceClass, true, loader);
                services.add(serviceType.cast(clazz.newInstance()));
            } catch (ClassNotFoundException e) {
                throw new IOException(e);
            } catch (InstantiationException e) {
                throw new IOException(e);
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            } catch (ClassCastException e) {
                throw new IOException(e);
            }
        }
        return Collections.unmodifiableList(services);
    }
}
