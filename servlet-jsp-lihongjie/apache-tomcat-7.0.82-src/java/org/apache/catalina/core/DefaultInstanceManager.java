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
package org.apache.catalina.core;

import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.xml.ws.WebServiceRef;

import org.apache.catalina.ContainerServlet;
import org.apache.catalina.Globals;
import org.apache.catalina.security.SecurityUtil;
import org.apache.catalina.util.Introspection;
import org.apache.juli.logging.Log;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.collections.ManagedConcurrentWeakHashMap;
import org.apache.tomcat.util.res.StringManager;

public class DefaultInstanceManager implements InstanceManager {

    // Used when there are no annotations in a class
    private static final AnnotationCacheEntry[] ANNOTATIONS_EMPTY
        = new AnnotationCacheEntry[0];

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);

    private final Context context;
    private final Map<String, Map<String, String>> injectionMap;
    protected final ClassLoader classLoader;
    protected final ClassLoader containerClassLoader;
    protected boolean privileged;
    protected boolean ignoreAnnotations;
    private final Set<String> restrictedClasses;
    private final ManagedConcurrentWeakHashMap<Class<?>, AnnotationCacheEntry[]> annotationCache =
            new ManagedConcurrentWeakHashMap<Class<?>, AnnotationCacheEntry[]>();
    private final Map<String, String> postConstructMethods;
    private final Map<String, String> preDestroyMethods;

    public DefaultInstanceManager(Context context, Map<String, Map<String, String>> injectionMap, org.apache.catalina.Context catalinaContext, ClassLoader containerClassLoader) {
        classLoader = catalinaContext.getLoader().getClassLoader();
        privileged = catalinaContext.getPrivileged();
        this.containerClassLoader = containerClassLoader;
        ignoreAnnotations = catalinaContext.getIgnoreAnnotations();
        Log log = catalinaContext.getLogger();
        Set<String> classNames = new HashSet<String>();
        loadProperties(classNames,
                "org/apache/catalina/core/RestrictedServlets.properties",
                "defaultInstanceManager.restrictedServletsResource", log);
        loadProperties(classNames,
                "org/apache/catalina/core/RestrictedListeners.properties",
                "defaultInstanceManager.restrictedListenersResource", log);
        loadProperties(classNames,
                "org/apache/catalina/core/RestrictedFilters.properties",
                "defaultInstanceManager.restrictedFiltersResource", log);
        restrictedClasses = Collections.unmodifiableSet(classNames);
        this.context = context;
        this.injectionMap = injectionMap;
        this.postConstructMethods = catalinaContext.findPostConstructMethods();
        this.preDestroyMethods = catalinaContext.findPreDestroyMethods();
    }

    @Override
    public Object newInstance(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, NamingException, InstantiationException {
        return newInstance(clazz.newInstance(), clazz);
    }

    @Override
    public Object newInstance(String className) throws IllegalAccessException, InvocationTargetException, NamingException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = loadClassMaybePrivileged(className, classLoader);
        return newInstance(clazz.newInstance(), clazz);
    }

    @Override
    public Object newInstance(final String className, final ClassLoader classLoader) throws IllegalAccessException, NamingException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = classLoader.loadClass(className);
        return newInstance(clazz.newInstance(), clazz);
    }

    @Override
    public void newInstance(Object o)
            throws IllegalAccessException, InvocationTargetException, NamingException {
        newInstance(o, o.getClass());
    }

    private Object newInstance(Object instance, Class<?> clazz) throws IllegalAccessException, InvocationTargetException, NamingException {
        if (!ignoreAnnotations) {
            Map<String, String> injections = assembleInjectionsFromClassHierarchy(clazz);
            populateAnnotationsCache(clazz, injections);
            processAnnotations(instance, injections);
            postConstruct(instance, clazz);
        }
        return instance;
    }

    private Map<String, String> assembleInjectionsFromClassHierarchy(Class<?> clazz) {
        Map<String, String> injections = new HashMap<String, String>();
        Map<String, String> currentInjections = null;
        while (clazz != null) {
            currentInjections = this.injectionMap.get(clazz.getName());
            if (currentInjections != null) {
                injections.putAll(currentInjections);
            }
            clazz = clazz.getSuperclass();
        }
        return injections;
    }

    @Override
    public void destroyInstance(Object instance) throws IllegalAccessException, InvocationTargetException {
        if (!ignoreAnnotations) {
            preDestroy(instance, instance.getClass());
        }
    }

    /**
     * Call postConstruct method on the specified instance recursively from deepest superclass to actual class.
     *
     * @param instance object to call postconstruct methods on
     * @param clazz    (super) class to examine for postConstruct annotation.
     * @throws IllegalAccessException if postConstruct method is inaccessible.
     * @throws java.lang.reflect.InvocationTargetException
     *                                if call fails
     */
    protected void postConstruct(Object instance, final Class<?> clazz)
            throws IllegalAccessException, InvocationTargetException {
        if (context == null) {
            // No resource injection
            return;
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            postConstruct(instance, superClass);
        }

        // At the end the postconstruct annotated
        // method is invoked
        AnnotationCacheEntry[] annotations = annotationCache.get(clazz);
        for (AnnotationCacheEntry entry : annotations) {
            if (entry.getType() == AnnotationCacheEntryType.POST_CONSTRUCT) {
                Method postConstruct = getMethod(clazz, entry);
                synchronized (postConstruct) {
                    boolean accessibility = postConstruct.isAccessible();
                    postConstruct.setAccessible(true);
                    postConstruct.invoke(instance);
                    postConstruct.setAccessible(accessibility);
                }
            }
        }
    }


    /**
     * Call preDestroy method on the specified instance recursively from deepest superclass to actual class.
     *
     * @param instance object to call preDestroy methods on
     * @param clazz    (super) class to examine for preDestroy annotation.
     * @throws IllegalAccessException if preDestroy method is inaccessible.
     * @throws java.lang.reflect.InvocationTargetException
     *                                if call fails
     */
    protected void preDestroy(Object instance, final Class<?> clazz)
            throws IllegalAccessException, InvocationTargetException {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            preDestroy(instance, superClass);
        }

        // At the end the postconstruct annotated
        // method is invoked
        AnnotationCacheEntry[] annotations = annotationCache.get(clazz);
        if (annotations == null) {
            // instance not created through the instance manager
            return;
        }
        for (AnnotationCacheEntry entry : annotations) {
            if (entry.getType() == AnnotationCacheEntryType.PRE_DESTROY) {
                Method preDestroy = getMethod(clazz, entry);
                synchronized (preDestroy) {
                    boolean accessibility = preDestroy.isAccessible();
                    preDestroy.setAccessible(true);
                    preDestroy.invoke(instance);
                    preDestroy.setAccessible(accessibility);
                }
            }
        }
    }


    public void backgroundProcess() {
        annotationCache.maintain();
    }


    /**
     * Make sure that the annotations cache has been populated for the provided
     * class.
     *
     * @param clazz         clazz to populate annotations for
     * @param injections    map of injections for this class from xml deployment
     *                      descriptor
     * @throws IllegalAccessException       if injection target is inaccessible
     * @throws javax.naming.NamingException if value cannot be looked up in jndi
     * @throws java.lang.reflect.InvocationTargetException
     *                                      if injection fails
     */
    protected void populateAnnotationsCache(Class<?> clazz,
            Map<String, String> injections) throws IllegalAccessException,
            InvocationTargetException, NamingException {

        List<AnnotationCacheEntry> annotations = null;

        while (clazz != null) {
            AnnotationCacheEntry[] annotationsArray = annotationCache.get(clazz);
            if (annotationsArray == null) {
                if (annotations == null) {
                    annotations = new ArrayList<AnnotationCacheEntry>();
                } else {
                    annotations.clear();
                }

                if (context != null) {
                    // Initialize fields annotations for resource injection if
                    // JNDI is enabled
                    Field[] fields = Introspection.getDeclaredFields(clazz);
                    for (Field field : fields) {
                        Resource resourceAnnotation;
                        EJB ejbAnnotation;
                        WebServiceRef webServiceRefAnnotation;
                        PersistenceContext persistenceContextAnnotation;
                        PersistenceUnit persistenceUnitAnnotation;
                        if (injections != null && injections.containsKey(field.getName())) {
                            annotations.add(new AnnotationCacheEntry(
                                    field.getName(), null,
                                    injections.get(field.getName()),
                                    AnnotationCacheEntryType.FIELD));
                        } else if ((resourceAnnotation =
                                field.getAnnotation(Resource.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(field.getName(), null,
                                    resourceAnnotation.name(), AnnotationCacheEntryType.FIELD));
                        } else if ((ejbAnnotation =
                                field.getAnnotation(EJB.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(field.getName(), null,
                                    ejbAnnotation.name(), AnnotationCacheEntryType.FIELD));
                        } else if ((webServiceRefAnnotation =
                                field.getAnnotation(WebServiceRef.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(field.getName(), null,
                                    webServiceRefAnnotation.name(),
                                    AnnotationCacheEntryType.FIELD));
                        } else if ((persistenceContextAnnotation =
                                field.getAnnotation(PersistenceContext.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(field.getName(), null,
                                    persistenceContextAnnotation.name(),
                                    AnnotationCacheEntryType.FIELD));
                        } else if ((persistenceUnitAnnotation =
                                field.getAnnotation(PersistenceUnit.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(field.getName(), null,
                                    persistenceUnitAnnotation.name(),
                                    AnnotationCacheEntryType.FIELD));
                        }
                    }
                }

                // Initialize methods annotations
                Method[] methods = Introspection.getDeclaredMethods(clazz);
                Method postConstruct = null;
                String postConstructFromXml = postConstructMethods.get(clazz.getName());
                Method preDestroy = null;
                String preDestroyFromXml = preDestroyMethods.get(clazz.getName());
                for (Method method : methods) {
                    if (context != null) {
                        // Resource injection only if JNDI is enabled
                        if (injections != null &&
                                Introspection.isValidSetter(method)) {
                            String fieldName = Introspection.getPropertyName(method);
                            if (injections.containsKey(fieldName)) {
                                annotations.add(new AnnotationCacheEntry(
                                        method.getName(),
                                        method.getParameterTypes(),
                                        injections.get(fieldName),
                                        AnnotationCacheEntryType.SETTER));
                                continue;
                            }
                        }
                        Resource resourceAnnotation;
                        EJB ejbAnnotation;
                        WebServiceRef webServiceRefAnnotation;
                        PersistenceContext persistenceContextAnnotation;
                        PersistenceUnit persistenceUnitAnnotation;
                        if ((resourceAnnotation =
                                method.getAnnotation(Resource.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(
                                    method.getName(),
                                    method.getParameterTypes(),
                                    resourceAnnotation.name(),
                                    AnnotationCacheEntryType.SETTER));
                        } else if ((ejbAnnotation =
                                method.getAnnotation(EJB.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(
                                    method.getName(),
                                    method.getParameterTypes(),
                                    ejbAnnotation.name(),
                                    AnnotationCacheEntryType.SETTER));
                        } else if ((webServiceRefAnnotation =
                                method.getAnnotation(WebServiceRef.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(
                                    method.getName(),
                                    method.getParameterTypes(),
                                    webServiceRefAnnotation.name(),
                                    AnnotationCacheEntryType.SETTER));
                        } else if ((persistenceContextAnnotation =
                                method.getAnnotation(PersistenceContext.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(
                                    method.getName(),
                                    method.getParameterTypes(),
                                    persistenceContextAnnotation.name(),
                                    AnnotationCacheEntryType.SETTER));
                        } else if ((persistenceUnitAnnotation = method.getAnnotation(PersistenceUnit.class)) != null) {
                            annotations.add(new AnnotationCacheEntry(
                                    method.getName(),
                                    method.getParameterTypes(),
                                    persistenceUnitAnnotation.name(),
                                    AnnotationCacheEntryType.SETTER));
                        }
                    }

                    postConstruct = findPostConstruct(postConstruct, postConstructFromXml, method);

                    preDestroy = findPreDestroy(preDestroy, preDestroyFromXml, method);
                }

                if (postConstruct != null) {
                    annotations.add(new AnnotationCacheEntry(
                            postConstruct.getName(),
                            postConstruct.getParameterTypes(), null,
                            AnnotationCacheEntryType.POST_CONSTRUCT));
                } else if (postConstructFromXml != null) {
                    throw new IllegalArgumentException("Post construct method "
                        + postConstructFromXml + " for class " + clazz.getName()
                        + " is declared in deployment descriptor but cannot be found.");
                }
                if (preDestroy != null) {
                    annotations.add(new AnnotationCacheEntry(
                            preDestroy.getName(),
                            preDestroy.getParameterTypes(), null,
                            AnnotationCacheEntryType.PRE_DESTROY));
                } else if (preDestroyFromXml != null) {
                    throw new IllegalArgumentException("Pre destroy method "
                        + preDestroyFromXml + " for class " + clazz.getName()
                        + " is declared in deployment descriptor but cannot be found.");
                }
                if (annotations.isEmpty()) {
                    // Use common object to save memory
                    annotationsArray = ANNOTATIONS_EMPTY;
                } else {
                    annotationsArray = annotations.toArray(
                            new AnnotationCacheEntry[annotations.size()]);
                }
                synchronized (annotationCache) {
                    annotationCache.put(clazz, annotationsArray);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }


    /**
     * Inject resources in specified instance.
     *
     * @param instance   instance to inject into
     * @param injections map of injections for this class from xml deployment descriptor
     * @throws IllegalAccessException       if injection target is inaccessible
     * @throws javax.naming.NamingException if value cannot be looked up in jndi
     * @throws java.lang.reflect.InvocationTargetException
     *                                      if injection fails
     */
    protected void processAnnotations(Object instance, Map<String, String> injections)
            throws IllegalAccessException, InvocationTargetException, NamingException {

        if (context == null) {
            // No resource injection
            return;
        }

        Class<?> clazz = instance.getClass();

        while (clazz != null) {
            AnnotationCacheEntry[] annotations = annotationCache.get(clazz);
            for (AnnotationCacheEntry entry : annotations) {
                if (entry.getType() == AnnotationCacheEntryType.SETTER) {
                    lookupMethodResource(context, instance,
                            getMethod(clazz, entry),
                            entry.getName(), clazz);
                } else if (entry.getType() == AnnotationCacheEntryType.FIELD) {
                    lookupFieldResource(context, instance,
                            getField(clazz, entry),
                            entry.getName(), clazz);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }


    /**
     * Makes cache size available to unit tests.
     */
    protected int getAnnotationCacheSize() {
        return annotationCache.size();
    }


    protected Class<?> loadClassMaybePrivileged(final String className, final ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> clazz;
        if (SecurityUtil.isPackageProtectionEnabled()) {
            try {
                clazz = AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>() {

                    @Override
                    public Class<?> run() throws Exception {
                        return loadClass(className, classLoader);
                    }
                });
            } catch (PrivilegedActionException e) {
                Throwable t = e.getCause();
                if (t instanceof ClassNotFoundException) {
                    throw (ClassNotFoundException) t;
                }
                throw new RuntimeException(t);
            }
        } else {
            clazz = loadClass(className, classLoader);
        }
        checkAccess(clazz);
        return clazz;
    }

    protected Class<?> loadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (className.startsWith("org.apache.catalina")) {
            return containerClassLoader.loadClass(className);
        }
        try {
            Class<?> clazz = containerClassLoader.loadClass(className);
            if (ContainerServlet.class.isAssignableFrom(clazz)) {
                return clazz;
            }
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
        }
        return classLoader.loadClass(className);
    }

    private void checkAccess(Class<?> clazz) {
        if (privileged) {
            return;
        }
        if (ContainerServlet.class.isAssignableFrom(clazz)) {
            throw new SecurityException(sm.getString(
                    "defaultInstanceManager.restrictedContainerServlet", clazz));
        }
        while (clazz != null) {
            if (restrictedClasses.contains(clazz.getName())) {
                throw new SecurityException(sm.getString(
                        "defaultInstanceManager.restrictedClass", clazz));
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Inject resources in specified field.
     *
     * @param context  jndi context to extract value from
     * @param instance object to inject into
     * @param field    field target for injection
     * @param name     jndi name value is bound under
     * @param clazz    class annotation is defined in
     * @throws IllegalAccessException       if field is inaccessible
     * @throws javax.naming.NamingException if value is not accessible in naming context
     */
    protected static void lookupFieldResource(Context context,
            Object instance, Field field, String name, Class<?> clazz)
            throws NamingException, IllegalAccessException {

        Object lookedupResource;
        boolean accessibility;

        String normalizedName = normalize(name);

        if ((normalizedName != null) && (normalizedName.length() > 0)) {
            lookedupResource = context.lookup(normalizedName);
        } else {
            lookedupResource =
                context.lookup(clazz.getName() + "/" + field.getName());
        }

        synchronized (field) {
            accessibility = field.isAccessible();
            field.setAccessible(true);
            field.set(instance, lookedupResource);
            field.setAccessible(accessibility);
        }
    }

    /**
     * Inject resources in specified method.
     *
     * @param context  jndi context to extract value from
     * @param instance object to inject into
     * @param method   field target for injection
     * @param name     jndi name value is bound under
     * @param clazz    class annotation is defined in
     * @throws IllegalAccessException       if method is inaccessible
     * @throws javax.naming.NamingException if value is not accessible in naming context
     * @throws java.lang.reflect.InvocationTargetException
     *                                      if setter call fails
     */
    protected static void lookupMethodResource(Context context,
            Object instance, Method method, String name, Class<?> clazz)
            throws NamingException, IllegalAccessException, InvocationTargetException {

        if (!Introspection.isValidSetter(method)) {
            throw new IllegalArgumentException(
                    sm.getString("defaultInstanceManager.invalidInjection"));
        }

        Object lookedupResource;
        boolean accessibility;

        String normalizedName = normalize(name);

        if ((normalizedName != null) && (normalizedName.length() > 0)) {
            lookedupResource = context.lookup(normalizedName);
        } else {
            lookedupResource = context.lookup(
                    clazz.getName() + "/" + Introspection.getPropertyName(method));
        }

        synchronized (method) {
            accessibility = method.isAccessible();
            method.setAccessible(true);
            method.invoke(instance, lookedupResource);
            method.setAccessible(accessibility);
        }
    }

    @Deprecated
    public static String getName(Method setter) {
        // Note: method signature has already been checked for correctness.
        // The method name always starts with "set".
        return Introspector.decapitalize(setter.getName().substring(3));
    }

    private static void loadProperties(Set<String> classNames, String resourceName,
            String messageKey, Log log) {
        Properties properties = new Properties();
        ClassLoader cl = DefaultInstanceManager.class.getClassLoader();
        InputStream is = null;
        try {
            is = cl.getResourceAsStream(resourceName);
            if (is == null) {
                log.error(sm.getString(messageKey, resourceName));
            } else {
                properties.load(is);
            }
        } catch (IOException ioe) {
            log.error(sm.getString(messageKey, resourceName), ioe);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        if (properties.isEmpty()) {
            return;
        }
        for (Map.Entry<Object, Object> e : properties.entrySet()) {
            if ("restricted".equals(e.getValue())) {
                classNames.add(e.getKey().toString());
            } else {
                log.warn(sm.getString(
                        "defaultInstanceManager.restrictedWrongValue",
                        resourceName, e.getKey(), e.getValue()));
            }
        }
    }

    private static String normalize(String jndiName){
        if(jndiName != null && jndiName.startsWith("java:comp/env/")){
            return jndiName.substring(14);
        }
        return jndiName;
    }

    private static Method getMethod(final Class<?> clazz,
            final AnnotationCacheEntry entry) {
        Method result = null;
        if (Globals.IS_SECURITY_ENABLED) {
            result = AccessController.doPrivileged(
                    new PrivilegedAction<Method>() {
                        @Override
                        public Method run() {
                            Method result = null;
                            try {
                                result = clazz.getDeclaredMethod(
                                        entry.getAccessibleObjectName(),
                                        entry.getParamTypes());
                            } catch (NoSuchMethodException e) {
                                // Should never happen. On that basis don't log
                                // it.
                            }
                            return result;
                        }
            });
        } else {
            try {
                result = clazz.getDeclaredMethod(
                        entry.getAccessibleObjectName(), entry.getParamTypes());
            } catch (NoSuchMethodException e) {
                // Should never happen. On that basis don't log it.
            }
        }
        return result;
    }

    private static Field getField(final Class<?> clazz,
            final AnnotationCacheEntry entry) {
        Field result = null;
        if (Globals.IS_SECURITY_ENABLED) {
            result = AccessController.doPrivileged(
                    new PrivilegedAction<Field>() {
                        @Override
                        public Field run() {
                            Field result = null;
                            try {
                                result = clazz.getDeclaredField(
                                        entry.getAccessibleObjectName());
                            } catch (NoSuchFieldException e) {
                                // Should never happen. On that basis don't log
                                // it.
                            }
                            return result;
                        }
            });
        } else {
            try {
                result = clazz.getDeclaredField(
                        entry.getAccessibleObjectName());
            } catch (NoSuchFieldException e) {
                // Should never happen. On that basis don't log it.
            }
        }
        return result;
    }


    private static Method findPostConstruct(Method currentPostConstruct,
            String postConstructFromXml, Method method) {
        return findLifecycleCallback(currentPostConstruct,
            postConstructFromXml, method, PostConstruct.class);
    }

    private static Method findPreDestroy(Method currentPreDestroy,
        String preDestroyFromXml, Method method) {
        return findLifecycleCallback(currentPreDestroy,
            preDestroyFromXml, method, PreDestroy.class);
    }

    private static Method findLifecycleCallback(Method currentMethod,
            String methodNameFromXml, Method method,
            Class<? extends Annotation> annotation) {
        Method result = currentMethod;
        if (methodNameFromXml != null) {
            if (method.getName().equals(methodNameFromXml)) {
                if (!Introspection.isValidLifecycleCallback(method)) {
                    throw new IllegalArgumentException(
                        "Invalid " + annotation.getName() + " annotation");
                }
                result = method;
            }
        } else {
            if (method.isAnnotationPresent(annotation)) {
                if (currentMethod != null ||
                    !Introspection.isValidLifecycleCallback(method)) {
                    throw new IllegalArgumentException(
                        "Invalid " + annotation.getName() + " annotation");
                }
                result = method;
            }
        }
        return result;
    }

    private static final class AnnotationCacheEntry {
        private final String accessibleObjectName;
        private final Class<?>[] paramTypes;
        private final String name;
        private final AnnotationCacheEntryType type;

        public AnnotationCacheEntry(String accessibleObjectName,
                Class<?>[] paramTypes, String name,
                AnnotationCacheEntryType type) {
            this.accessibleObjectName = accessibleObjectName;
            this.paramTypes = paramTypes;
            this.name = name;
            this.type = type;
        }

        public String getAccessibleObjectName() {
            return accessibleObjectName;
        }

        public Class<?>[] getParamTypes() {
            return paramTypes;
        }

        public String getName() {
            return name;
        }
        public AnnotationCacheEntryType getType() {
            return type;
        }
    }

    private static enum AnnotationCacheEntryType {
        FIELD, SETTER, POST_CONSTRUCT, PRE_DESTROY
    }
}
