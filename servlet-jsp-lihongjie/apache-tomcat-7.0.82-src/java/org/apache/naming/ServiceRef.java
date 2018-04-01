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
import java.util.Vector;

import javax.naming.Context;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

/**
 * Represents a reference web service.
 *
 * @author Fabien Carrion
 */

public class ServiceRef extends Reference {

    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------------- Constants

    /**
     * Default factory for this reference.
     */
    public static final String DEFAULT_FACTORY = 
        org.apache.naming.factory.Constants.DEFAULT_SERVICE_FACTORY;


    /**
     * Service Classname address type.
     */
    public static final String SERVICE_INTERFACE  = "serviceInterface";


    /**
     * ServiceQname address type.
     */
    public static final String SERVICE_NAMESPACE  = "service namespace";
    public static final String SERVICE_LOCAL_PART = "service local part";


    /**
     * Wsdl Location address type.
     */
    public static final String WSDL      = "wsdl";


    /**
     * Jaxrpcmapping address type.
     */
    public static final String JAXRPCMAPPING = "jaxrpcmapping";


    /**
     * port-component-ref/port-component-link address type.
     */
    public static final String PORTCOMPONENTLINK = "portcomponentlink";


    /**
     * port-component-ref/service-endpoint-interface address type.
     */
    public static final String SERVICEENDPOINTINTERFACE = "serviceendpointinterface";


    /**
     * The vector to save the handler Reference objects, because they can't be saved in the addrs vector.
     */
    private Vector<HandlerRef> handlers = new Vector<HandlerRef>();


    // ----------------------------------------------------------- Constructors

    public ServiceRef(String refname, String serviceInterface, String[] serviceQname, 
                       String wsdl, String jaxrpcmapping) {
        this(refname, serviceInterface, serviceQname, wsdl, jaxrpcmapping,
                        null, null);
    }

    public ServiceRef(@SuppressWarnings("unused") String refname,
                       String serviceInterface, String[] serviceQname, 
                       String wsdl, String jaxrpcmapping,
                       String factory, String factoryLocation) {
        super(serviceInterface, factory, factoryLocation);
        StringRefAddr refAddr = null;
        if (serviceInterface != null) {
            refAddr = new StringRefAddr(SERVICE_INTERFACE, serviceInterface);
            add(refAddr);
        }
        if (serviceQname[0] != null) {
            refAddr = new StringRefAddr(SERVICE_NAMESPACE, serviceQname[0]);
            add(refAddr);
        }
        if (serviceQname[1] != null) {
            refAddr = new StringRefAddr(SERVICE_LOCAL_PART, serviceQname[1]);
            add(refAddr);
        }
        if (wsdl != null) {
            refAddr = new StringRefAddr(WSDL, wsdl);
            add(refAddr);
        }
        if (jaxrpcmapping != null) {
            refAddr = new StringRefAddr(JAXRPCMAPPING, jaxrpcmapping);
            add(refAddr);
        }
    }


    // ----------------------------------------------------- Instance Variables


    // ------------------------------------------------------ Reference Methods


    /**
     * Add and Get Handlers classes.
     */
    public HandlerRef getHandler() {
        return handlers.remove(0);
    }


    public int getHandlersSize() {
        return handlers.size();
    }


    public void addHandler(HandlerRef handler) {
        handlers.add(handler);
    }


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

        StringBuilder sb = new StringBuilder("ServiceRef[");
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
