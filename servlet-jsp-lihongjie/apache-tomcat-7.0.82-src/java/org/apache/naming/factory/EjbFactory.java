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


package org.apache.naming.factory;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.apache.naming.EjbRef;

/**
 * Object factory for EJBs.
 * 
 * @author Remy Maucherat
 */

public class EjbFactory
    implements ObjectFactory {


    // ----------------------------------------------------------- Constructors


    // -------------------------------------------------------------- Constants


    // ----------------------------------------------------- Instance Variables


    // --------------------------------------------------------- Public Methods


    // -------------------------------------------------- ObjectFactory Methods


    /**
     * Create a new EJB instance.
     * 
     * @param obj The reference object describing the DataSource
     */
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                                    Hashtable<?,?> environment)
        throws Exception {
        
        if (obj instanceof EjbRef) {
            Reference ref = (Reference) obj;

            // If ejb-link has been specified, resolving the link using JNDI
            RefAddr linkRefAddr = ref.get(EjbRef.LINK);
            if (linkRefAddr != null) {
                // Retrieving the EJB link
                String ejbLink = linkRefAddr.getContent().toString();
                Object beanObj = (new InitialContext()).lookup(ejbLink);
                // Load home interface and checking if bean correctly
                // implements specified home interface
                /*
                String homeClassName = ref.getClassName();
                try {
                    Class home = Class.forName(homeClassName);
                    if (home.isInstance(beanObj)) {
                        System.out.println("Bean of type " 
                                           + beanObj.getClass().getName() 
                                           + " implements home interface " 
                                           + home.getName());
                    } else {
                        System.out.println("Bean of type " 
                                           + beanObj.getClass().getName() 
                                           + " doesn't implement home interface " 
                                           + home.getName());
                        throw new NamingException
                            ("Bean of type " + beanObj.getClass().getName() 
                             + " doesn't implement home interface " 
                             + home.getName());
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Couldn't load home interface "
                                       + homeClassName);
                }
                */
                return beanObj;
            }
            
            ObjectFactory factory = null;
            RefAddr factoryRefAddr = ref.get(Constants.FACTORY);
            if (factoryRefAddr != null) {
                // Using the specified factory
                String factoryClassName = 
                    factoryRefAddr.getContent().toString();
                // Loading factory
                ClassLoader tcl = 
                    Thread.currentThread().getContextClassLoader();
                Class<?> factoryClass = null;
                if (tcl != null) {
                    try {
                        factoryClass = tcl.loadClass(factoryClassName);
                    } catch(ClassNotFoundException e) {
                        NamingException ex = new NamingException
                            ("Could not load resource factory class");
                        ex.initCause(e);
                        throw ex;
                    }
                } else {
                    try {
                        factoryClass = Class.forName(factoryClassName);
                    } catch(ClassNotFoundException e) {
                        NamingException ex = new NamingException
                            ("Could not load resource factory class");
                        ex.initCause(e);
                        throw ex;
                    }
                }
                if (factoryClass != null) {
                    try {
                        factory = (ObjectFactory) factoryClass.newInstance();
                    } catch(Throwable t) {
                        NamingException ex = new NamingException
                            ("Could not load resource factory class");
                        ex.initCause(t);
                        throw ex;
                    }
                }
            } else {
                String javaxEjbFactoryClassName =
                    System.getProperty("javax.ejb.Factory",
                                       Constants.OPENEJB_EJB_FACTORY);
                try {
                    factory = (ObjectFactory)
                        Class.forName(javaxEjbFactoryClassName).newInstance();
                } catch(Throwable t) {
                    if (t instanceof NamingException)
                        throw (NamingException) t;
                    NamingException ex = new NamingException
                        ("Could not create resource factory instance");
                    ex.initCause(t);
                    throw ex;
                }
            }

            if (factory != null) {
                return factory.getObjectInstance
                    (obj, name, nameCtx, environment);
            } else {
                throw new NamingException
                    ("Cannot create resource instance");
            }

        }

        return null;

    }


}

