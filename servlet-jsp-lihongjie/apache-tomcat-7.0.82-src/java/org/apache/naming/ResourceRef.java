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

import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

/**
 * Represents a reference address to a resource.
 *
 * @author Remy Maucherat
 */
public class ResourceRef extends Reference {

    private static final long serialVersionUID = 1L;

    
    // -------------------------------------------------------------- Constants

    /**
     * Default factory for this reference.
     */
    public static final String DEFAULT_FACTORY = 
        org.apache.naming.factory.Constants.DEFAULT_RESOURCE_FACTORY;


    /**
     * Description address type.
     */
    public static final String DESCRIPTION = "description";


    /**
     * Scope address type.
     */
    public static final String SCOPE = "scope";


    /**
     * Auth address type.
     */
    public static final String AUTH = "auth";


    /**
     * Is this resource a singleton
     */
    public static final String SINGLETON = "singleton";

    // ----------------------------------------------------------- Constructors


    /**
     * Resource Reference.
     * 
     * @param resourceClass Resource class
     * @param scope Resource scope
     * @param auth Resource authentication
     */
    public ResourceRef(String resourceClass, String description, 
                       String scope, String auth, boolean singleton) {
        this(resourceClass, description, scope, auth, singleton, null, null);
    }


    /**
     * Resource Reference.
     * 
     * @param resourceClass Resource class
     * @param scope Resource scope
     * @param auth Resource authentication
     */
    public ResourceRef(String resourceClass, String description, 
                       String scope, String auth, boolean singleton,
                       String factory, String factoryLocation) {
        super(resourceClass, factory, factoryLocation);
        StringRefAddr refAddr = null;
        if (description != null) {
            refAddr = new StringRefAddr(DESCRIPTION, description);
            add(refAddr);
        }
        if (scope != null) {
            refAddr = new StringRefAddr(SCOPE, scope);
            add(refAddr);
        }
        if (auth != null) {
            refAddr = new StringRefAddr(AUTH, auth);
            add(refAddr);
        }
        // singleton is a boolean so slightly different handling
        refAddr = new StringRefAddr(SINGLETON, Boolean.toString(singleton));
        add(refAddr);
    }


    // ----------------------------------------------------- Instance Variables


    // ------------------------------------------------------ Reference Methods


    /**
     * Retrieves the class name of the factory of the object to which this 
     * reference refers.
     */
    @Override
    public String getFactoryClassName() {
        String factory = super.getFactoryClassName();
        if (factory != null) {
            return factory;
        } else {
            factory = System.getProperty(Context.OBJECT_FACTORIES);
            if (factory != null) {
                return null;
            } else {
                return DEFAULT_FACTORY;
            }
        }
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return a String rendering of this object.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("ResourceRef[");
        sb.append("className=");
        sb.append(getClassName());
        sb.append(",factoryClassLocation=");
        sb.append(getFactoryClassLocation());
        sb.append(",factoryClassName=");
        sb.append(getFactoryClassName());
        Enumeration<RefAddr> refAddrs = getAll();
        while (refAddrs.hasMoreElements()) {
            RefAddr refAddr = refAddrs.nextElement();
            sb.append(",{type=");
            sb.append(refAddr.getType());
            sb.append(",content=");
            sb.append(refAddr.getContent());
            sb.append("}");
        }
        sb.append("]");
        return (sb.toString());

    }


    // ------------------------------------------------------------- Properties


}
