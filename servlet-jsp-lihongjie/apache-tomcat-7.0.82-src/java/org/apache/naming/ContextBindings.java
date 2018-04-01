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


package org.apache.naming;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;

/**
 * Handles the associations :
 * <ul>
 * <li>Catalina context name with the NamingContext</li>
 * <li>Calling thread with the NamingContext</li>
 * </ul>
 *
 * @author Remy Maucherat
 */
public class ContextBindings {


    // -------------------------------------------------------------- Variables


    /**
     * Bindings name - naming context. Keyed by name.
     */
    private static final Hashtable<Object,Context> contextNameBindings =
        new Hashtable<Object,Context>();


    /**
     * Bindings thread - naming context. Keyed by thread id.
     */
    private static final Hashtable<Thread,Context> threadBindings =
        new Hashtable<Thread,Context>();


    /**
     * Bindings thread - name. Keyed by thread id.
     */
    private static final Hashtable<Thread,Object> threadNameBindings =
        new Hashtable<Thread,Object>();


    /**
     * Bindings class loader - naming context. Keyed by CL id.
     */
    private static final Hashtable<ClassLoader,Context> clBindings =
        new Hashtable<ClassLoader,Context>();


    /**
     * Bindings class loader - name. Keyed by CL id.
     */
    private static final Hashtable<ClassLoader,Object> clNameBindings =
        new Hashtable<ClassLoader,Object>();


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = 
        StringManager.getManager(Constants.Package);


    // --------------------------------------------------------- Public Methods


    /**
     * Binds a context name.
     * 
     * @param name Name of the context
     * @param context Associated naming context instance
     */
    public static void bindContext(Object name, Context context) {
        bindContext(name, context, null);
    }


    /**
     * Binds a context name.
     * 
     * @param name Name of the context
     * @param context Associated naming context instance
     * @param token Security token
     */
    public static void bindContext(Object name, Context context, 
                                   Object token) {
        if (ContextAccessController.checkSecurityToken(name, token))
            contextNameBindings.put(name, context);
    }


    /**
     * Unbind context name.
     * 
     * @param name Name of the context
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void unbindContext(Object name) {
        unbindContext(name, null);
    }


    /**
     * Unbind context name.
     * 
     * @param name Name of the context
     * @param token Security token
     */
    public static void unbindContext(Object name, Object token) {
        if (ContextAccessController.checkSecurityToken(name, token))
            contextNameBindings.remove(name);
    }


    /**
     * Retrieve a naming context.
     * 
     * @param name Name of the context
     */
    static Context getContext(Object name) {
        return contextNameBindings.get(name);
    }


    /**
     * Binds a naming context to a thread.
     * 
     * @param name Name of the context
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void bindThread(Object name)
        throws NamingException {
        bindThread(name, null);
    }


    /**
     * Binds a naming context to a thread.
     * 
     * @param name Name of the context
     * @param token Security token
     */
    public static void bindThread(Object name, Object token) 
        throws NamingException {
        if (ContextAccessController.checkSecurityToken(name, token)) {
            Context context = contextNameBindings.get(name);
            if (context == null)
                throw new NamingException
                    (sm.getString("contextBindings.unknownContext", name));
            threadBindings.put(Thread.currentThread(), context);
            threadNameBindings.put(Thread.currentThread(), name);
        }
    }


    /**
     * Unbinds a naming context to a thread.
     * 
     * @param name Name of the context
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void unbindThread(Object name) {
        unbindThread(name, null);
    }


    /**
     * Unbinds a naming context to a thread.
     * 
     * @param name Name of the context
     * @param token Security token
     */
    public static void unbindThread(Object name, Object token) {
        if (ContextAccessController.checkSecurityToken(name, token)) {
            threadBindings.remove(Thread.currentThread());
            threadNameBindings.remove(Thread.currentThread());
        }
    }


    /**
     * Retrieves the naming context bound to a thread.
     */
    public static Context getThread()
        throws NamingException {
        Context context = threadBindings.get(Thread.currentThread());
        if (context == null)
            throw new NamingException
                (sm.getString("contextBindings.noContextBoundToThread"));
        return context;
    }


    /**
     * Retrieves the naming context name bound to a thread.
     */
    static Object getThreadName()
        throws NamingException {
        Object name = threadNameBindings.get(Thread.currentThread());
        if (name == null)
            throw new NamingException
                (sm.getString("contextBindings.noContextBoundToThread"));
        return name;
    }


    /**
     * Tests if current thread is bound to a context.
     */
    public static boolean isThreadBound() {
        return (threadBindings.containsKey(Thread.currentThread()));
    }


    /**
     * Binds a naming context to a class loader.
     * 
     * @param name Name of the context
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void bindClassLoader(Object name)
        throws NamingException {
        bindClassLoader(name, null);
    }


    /**
     * Binds a naming context to a thread.
     * 
     * @param name Name of the context
     * @param token Security token
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void bindClassLoader(Object name, Object token)
        throws NamingException {
        bindClassLoader
            (name, token, Thread.currentThread().getContextClassLoader());
    }


    /**
     * Binds a naming context to a thread.
     * 
     * @param name Name of the context
     * @param token Security token
     */
    public static void bindClassLoader(Object name, Object token, 
                                       ClassLoader classLoader) 
        throws NamingException {
        if (ContextAccessController.checkSecurityToken(name, token)) {
            Context context = contextNameBindings.get(name);
            if (context == null)
                throw new NamingException
                    (sm.getString("contextBindings.unknownContext", name));
            clBindings.put(classLoader, context);
            clNameBindings.put(classLoader, name);
        }
    }


    /**
     * Unbinds a naming context to a class loader.
     * 
     * @param name Name of the context
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void unbindClassLoader(Object name) {
        unbindClassLoader(name, null);
    }


    /**
     * Unbinds a naming context to a class loader.
     * 
     * @param name Name of the context
     * @param token Security token
     *
     * @deprecated - unused
     */
    @Deprecated
    public static void unbindClassLoader(Object name, Object token) {
        unbindClassLoader(name, token, 
                          Thread.currentThread().getContextClassLoader());
    }


    /**
     * Unbinds a naming context to a class loader.
     * 
     * @param name Name of the context
     * @param token Security token
     */
    public static void unbindClassLoader(Object name, Object token, 
                                         ClassLoader classLoader) {
        if (ContextAccessController.checkSecurityToken(name, token)) {
            Object n = clNameBindings.get(classLoader);
            if ((n==null) || !(n.equals(name))) {
                return;
            }
            clBindings.remove(classLoader);
            clNameBindings.remove(classLoader);
        }
    }


    /**
     * Retrieves the naming context bound to a class loader.
     */
    public static Context getClassLoader()
        throws NamingException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Context context = null;
        do {
            context = clBindings.get(cl);
            if (context != null) {
                return context;
            }
        } while ((cl = cl.getParent()) != null);
        throw new NamingException
            (sm.getString("contextBindings.noContextBoundToCL"));
    }


    /**
     * Retrieves the naming context name bound to a class loader.
     */
    static Object getClassLoaderName()
        throws NamingException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Object name = null;
        do {
            name = clNameBindings.get(cl);
            if (name != null) {
                return name;
            }
        } while ((cl = cl.getParent()) != null);
        throw new NamingException
            (sm.getString("contextBindings.noContextBoundToCL"));
    }


    /**
     * Tests if current class loader is bound to a context.
     */
    public static boolean isClassLoaderBound() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        do {
            if (clBindings.containsKey(cl)) {
                return true;
            }
        } while ((cl = cl.getParent()) != null);
        return false;
    }


}
