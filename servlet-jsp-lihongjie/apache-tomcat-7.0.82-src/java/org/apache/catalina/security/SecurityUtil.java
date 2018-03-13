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
package org.apache.catalina.security;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Globals;
import org.apache.catalina.comet.CometFilter;
import org.apache.catalina.comet.CometProcessor;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;
/**
 * This utility class associates a <code>Subject</code> to the current
 * <code>AccessControlContext</code>. When a <code>SecurityManager</code> is
 * used, the container will always associate the called thread with an
 * AccessControlContext containing only the principal of the requested
 * Servlet/Filter.
 *
 * This class uses reflection to invoke the methods.
 *
 * @author Jean-Francois Arcand
 */

public final class SecurityUtil{

    // Note that indexes overlap.
    // A Servlet uses "init", "service", "event", "destroy".
    // A Filter uses "doFilter", "doFilterEvent", "destroy".
    private static final int INIT= 0;
    private static final int SERVICE = 1;
    private static final int DOFILTER = 1;
    private static final int EVENT = 2;
    private static final int DOFILTEREVENT = 2;
    private static final int DESTROY = 3;

    private static final String INIT_METHOD = "init";
    private static final String DOFILTER_METHOD = "doFilter";
    private static final String SERVICE_METHOD = "service";
    private static final String EVENT_METHOD = "event";
    private static final String DOFILTEREVENT_METHOD = "doFilterEvent";
    private static final String DESTROY_METHOD = "destroy";

    /**
     * Cache every class for which we are creating methods.
     */
    private static final Map<Object,Method[]> classCache = new ConcurrentHashMap<Object,Method[]>();

    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( SecurityUtil.class );

    private static boolean packageDefinitionEnabled =
         (System.getProperty("package.definition") == null &&
           System.getProperty("package.access")  == null) ? false : true;

    /**
     * The string resources for this package.
     */
    private static final StringManager sm =
        StringManager.getManager(Constants.PACKAGE);


    /**
     * Perform work as a particular </code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param methodName the method to apply the security restriction
     * @param targetObject the <code>Servlet</code> on which the method will
     * be called.
     */
    public static void doAsPrivilege(final String methodName,
                                     final Servlet targetObject) throws java.lang.Exception{
         doAsPrivilege(methodName, targetObject, null, null, null);
    }


    /**
     * Perform work as a particular </code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param methodName the method to apply the security restriction
     * @param targetObject the <code>Servlet</code> on which the method will
     * be called.
     * @param targetType <code>Class</code> array used to instantiate a
     * <code>Method</code> object.
     * @param targetArguments <code>Object</code> array contains the runtime
     * parameters instance.
     */
    public static void doAsPrivilege(final String methodName,
                                     final Servlet targetObject,
                                     final Class<?>[] targetType,
                                     final Object[] targetArguments)
        throws java.lang.Exception{

         doAsPrivilege(methodName,
                       targetObject,
                       targetType,
                       targetArguments,
                       null);
    }


    /**
     * Perform work as a particular </code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param methodName the method to apply the security restriction
     * @param targetObject the <code>Servlet</code> on which the method will
     * be called.
     * @param targetParameterTypes <code>Class</code> array used to instantiate a
     * <code>Method</code> object.
     * @param targetArguments <code>Object</code> array contains the
     * runtime parameters instance.
     * @param principal the <code>Principal</code> to which the security
     * privilege apply..
     */
    public static void doAsPrivilege(final String methodName,
                                     final Servlet targetObject,
                                     final Class<?>[] targetParameterTypes,
                                     final Object[] targetArguments,
                                     Principal principal)
        throws java.lang.Exception{

        // CometProcessor instances must not be cached as Servlet or
        // NoSuchMethodException will be thrown.
        Class<? extends Servlet> targetType =
                targetObject instanceof CometProcessor ? CometProcessor.class : Servlet.class;

        Method method = null;
        Method[] methodsCache = classCache.get(Servlet.class);
        if(methodsCache == null) {
            method = createMethodAndCacheIt(methodsCache,
                                            targetType,
                                            methodName,
                                            targetParameterTypes);
        } else {
            method = findMethod(methodsCache, methodName);
            if (method == null) {
                method = createMethodAndCacheIt(methodsCache,
                                                targetType,
                                                methodName,
                                                targetParameterTypes);
            }
        }

        execute(method, targetObject, targetArguments, principal);
    }


    /**
     * Perform work as a particular </code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param methodName the method to apply the security restriction
     * @param targetObject the <code>Filter</code> on which the method will
     * be called.
     */
    public static void doAsPrivilege(final String methodName,
                                     final Filter targetObject)
        throws java.lang.Exception{

         doAsPrivilege(methodName, targetObject, null, null);
    }


    /**
     * Perform work as a particular <code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param methodName the method to apply the security restriction
     * @param targetObject the <code>Filter</code> on which the method will
     * be called.
     * @param targetType <code>Class</code> array used to instantiate a
     * <code>Method</code> object.
     * @param targetArguments <code>Object</code> array contains the
     * runtime parameters instance.
     */
    public static void doAsPrivilege(final String methodName,
                                     final Filter targetObject,
                                     final Class<?>[] targetType,
                                     final Object[] targetArguments)
        throws java.lang.Exception{

        doAsPrivilege(
                methodName, targetObject, targetType, targetArguments, null);
    }

    /**
     * Perform work as a particular <code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param methodName the method to apply the security restriction
     * @param targetObject the <code>Filter</code> on which the method will
     * be called.
     * @param targetParameterTypes <code>Class</code> array used to instantiate a
     * <code>Method</code> object.
     * @param targetParameterValues <code>Object</code> array contains the
     * runtime parameters instance.
     * @param principal the <code>Principal</code> to which the security
     * privilege apply
     */
    public static void doAsPrivilege(final String methodName,
                                     final Filter targetObject,
                                     final Class<?>[] targetParameterTypes,
                                     final Object[] targetParameterValues,
                                     Principal principal)
        throws java.lang.Exception{

        // CometFilter instances must not be cached as Filter or
        // NoSuchMethodException will be thrown.
        Class<? extends Filter> targetType =
                targetObject instanceof CometFilter ? CometFilter.class : Filter.class;

        Method method = null;
        Method[] methodsCache = classCache.get(Filter.class);
        if(methodsCache == null) {
            method = createMethodAndCacheIt(methodsCache,
                                            targetType,
                                            methodName,
                                            targetParameterTypes);
        } else {
            method = findMethod(methodsCache, methodName);
            if (method == null) {
                method = createMethodAndCacheIt(methodsCache,
                                                targetType,
                                                methodName,
                                                targetParameterTypes);
            }
        }

        execute(method, targetObject, targetParameterValues, principal);
    }


    /**
     * Perform work as a particular </code>Subject</code>. Here the work
     * will be granted to a <code>null</code> subject.
     *
     * @param method the method to apply the security restriction
     * @param targetObject the <code>Servlet</code> on which the method will
     * be called.
     * @param targetArguments <code>Object</code> array contains the
     * runtime parameters instance.
     * @param principal the <code>Principal</code> to which the security
     * privilege applies
     */
    private static void execute(final Method method,
                                final Object targetObject,
                                final Object[] targetArguments,
                                Principal principal)
        throws java.lang.Exception{

        try{
            Subject subject = null;
            PrivilegedExceptionAction<Void> pea =
                new PrivilegedExceptionAction<Void>(){
                    @Override
                    public Void run() throws Exception{
                       method.invoke(targetObject, targetArguments);
                       return null;
                    }
            };

            // The first argument is always the request object
            if (targetArguments != null
                    && targetArguments[0] instanceof HttpServletRequest){
                HttpServletRequest request =
                    (HttpServletRequest)targetArguments[0];

                boolean hasSubject = false;
                HttpSession session = request.getSession(false);
                if (session != null){
                    subject =
                        (Subject)session.getAttribute(Globals.SUBJECT_ATTR);
                    hasSubject = (subject != null);
                }

                if (subject == null){
                    subject = new Subject();

                    if (principal != null){
                        subject.getPrincipals().add(principal);
                    }
                }

                if (session != null && !hasSubject) {
                    session.setAttribute(Globals.SUBJECT_ATTR, subject);
                }
            }

            Subject.doAsPrivileged(subject, pea, null);
        } catch( PrivilegedActionException pe) {
            Throwable e;
            if (pe.getException() instanceof InvocationTargetException) {
                e = pe.getException().getCause();
                ExceptionUtils.handleThrowable(e);
            } else {
                e = pe;
            }

            if (log.isDebugEnabled()){
                log.debug(sm.getString("SecurityUtil.doAsPrivilege"), e);
            }

            if (e instanceof UnavailableException)
                throw (UnavailableException) e;
            else if (e instanceof ServletException)
                throw (ServletException) e;
            else if (e instanceof IOException)
                throw (IOException) e;
            else if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            else
                throw new ServletException(e.getMessage(), e);
        }
    }


    /**
     * Find a method stored within the cache.
     * @param methodsCache the cache used to store method instance
     * @param methodName the method to apply the security restriction
     * @return the method instance, null if not yet created.
     */
    private static Method findMethod(Method[] methodsCache,
                                     String methodName){
        if (methodName.equals(INIT_METHOD)){
            return methodsCache[INIT];
        } else if (methodName.equals(DESTROY_METHOD)){
            return methodsCache[DESTROY];
        } else if (methodName.equals(SERVICE_METHOD)){
            return methodsCache[SERVICE];
        } else if (methodName.equals(DOFILTER_METHOD)){
            return methodsCache[DOFILTER];
        } else if (methodName.equals(EVENT_METHOD)){
            return methodsCache[EVENT];
        } else if (methodName.equals(DOFILTEREVENT_METHOD)){
            return methodsCache[DOFILTEREVENT];
        }
        return null;
    }


    /**
     * Create the method and cache it for further re-use.
     * @param methodsCache the cache used to store method instance
     * @param targetType the class on which the method will be called.
     * @param methodName the method to apply the security restriction
     * @param parameterTypes <code>Class</code> array used to instantiate a
     * <code>Method</code> object.
     * @return the method instance.
     */
    private static Method createMethodAndCacheIt(Method[] methodsCache,
                                                 Class<?> targetType,
                                                 String methodName,
                                                 Class<?>[] parameterTypes)
            throws Exception {

        if (methodsCache == null) {
            methodsCache = new Method[4];
        }

        Method method = targetType.getMethod(methodName, parameterTypes);

        if (methodName.equals(INIT_METHOD)){
            methodsCache[INIT] = method;
        } else if (methodName.equals(DESTROY_METHOD)){
            methodsCache[DESTROY] = method;
        } else if (methodName.equals(SERVICE_METHOD)){
            methodsCache[SERVICE] = method;
        } else if (methodName.equals(DOFILTER_METHOD)){
            methodsCache[DOFILTER] = method;
        } else if (methodName.equals(EVENT_METHOD)){
            methodsCache[EVENT] = method;
        } else if (methodName.equals(DOFILTEREVENT_METHOD)){
            methodsCache[DOFILTEREVENT] = method;
        }

        classCache.put(targetType, methodsCache);

        return method;
    }


    /**
     * Remove the object from the cache.
     *
     * @param cachedObject The object to remove
     */
    public static void remove(Object cachedObject){
        classCache.remove(cachedObject);
    }


    /**
     * Return the <code>SecurityManager</code> only if Security is enabled AND
     * package protection mechanism is enabled.
     */
    public static boolean isPackageProtectionEnabled(){
        if (packageDefinitionEnabled && Globals.IS_SECURITY_ENABLED){
            return true;
        }
        return false;
    }


}
