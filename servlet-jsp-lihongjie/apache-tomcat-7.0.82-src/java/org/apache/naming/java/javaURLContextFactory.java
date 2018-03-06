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


package org.apache.naming.java;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.ObjectFactory;

import org.apache.naming.ContextBindings;
import org.apache.naming.NamingContext;
import org.apache.naming.SelectorContext;

/**
 * Context factory for the "java:" namespace.
 * <p>
 * <b>Important note</b> : This factory MUST be associated with the "java" URL
 * prefix, which can be done by either :
 * <ul>
 * <li>Adding a 
 * java.naming.factory.url.pkgs=org.apache.naming property
 * to the JNDI properties file</li>
 * <li>Setting an environment variable named Context.URL_PKG_PREFIXES with
 * its value including the org.apache.naming package name.
 * More detail about this can be found in the JNDI documentation :
 * {@link javax.naming.spi.NamingManager#getURLContext(java.lang.String, java.util.Hashtable)}.</li>
 * </ul>
 * 
 * @author Remy Maucherat
 */
public class javaURLContextFactory
    implements ObjectFactory, InitialContextFactory {


    // ----------------------------------------------------------- Constructors


    // -------------------------------------------------------------- Constants


    public static final String MAIN = "initialContext";


    // ----------------------------------------------------- Instance Variables


    /**
     * Initial context.
     */
    protected static volatile Context initialContext = null;


    // --------------------------------------------------------- Public Methods


    // -------------------------------------------------- ObjectFactory Methods


    /**
     * Crete a new Context's instance.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                                    Hashtable<?,?> environment)
        throws NamingException {
        if ((ContextBindings.isThreadBound()) || 
            (ContextBindings.isClassLoaderBound())) {
            return new SelectorContext((Hashtable<String,Object>)environment);
        }
        return null;
    }


    /**
     * Get a new (writable) initial context.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Context getInitialContext(Hashtable<?,?> environment)
        throws NamingException {
        if (ContextBindings.isThreadBound() || 
            (ContextBindings.isClassLoaderBound())) {
            // Redirect the request to the bound initial context
            return new SelectorContext(
                    (Hashtable<String,Object>)environment, true);
        }

        // If the thread is not bound, return a shared writable context
        if (initialContext == null) {
            synchronized(javaURLContextFactory.class) {
                if (initialContext == null) {
                    initialContext = new NamingContext(
                            (Hashtable<String,Object>)environment, MAIN);
                }
            }
        }
        return initialContext;
    }


}

