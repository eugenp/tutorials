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


package org.apache.naming.factory.webservices;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

/**
 * Object proxy for Web Services.
 * 
 * @author Fabien Carrion
 */

public class ServiceProxy
    implements InvocationHandler {

    /**
     * Service object.
     * used for delegation
     */
    private Service service = null;

    /**
     * changing behavior to method : Service.getPort(QName, Class)
     */
    private static Method portQNameClass = null;

    /**
     * changing behavior to method : Service.getPort(Class)
     */
    private static Method portClass = null;

    /**
     * PortComponentRef list
     */
    private Hashtable<String,QName> portComponentRef = null;

    /**
     * Constructs a new ServiceProxy wrapping given Service instance.
     * @param service the wrapped Service instance
     * @throws ServiceException should be never thrown
     */
    public ServiceProxy(Service service) throws ServiceException {
        this.service = service;
        try {
            portQNameClass = Service.class.getDeclaredMethod("getPort", new Class[]{QName.class, Class.class});
            portClass = Service.class.getDeclaredMethod("getPort", new Class[]{Class.class});
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @see InvocationHandler#invoke(Object, Method, Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        if (portQNameClass.equals(method)) {
            return getProxyPortQNameClass(args);
        }

        if (portClass.equals(method)) {
            return getProxyPortClass(args);
        }

        try {
            return method.invoke(service, args);
        } catch (InvocationTargetException ite) {
            throw ite.getTargetException();
        }
    }

    /**
     * @param args Method call arguments
     * @return Returns the correct Port
     * @throws ServiceException if port's QName is an unknown Port (not defined in WSDL).
     */
    @SuppressWarnings("unchecked")
    private Object getProxyPortQNameClass(Object[] args)
    throws ServiceException {
        QName name = (QName) args[0];
        String nameString = name.getLocalPart();
        Class<?> serviceendpointClass = (Class<?>) args[1];

        for (Iterator<QName> ports = service.getPorts(); ports.hasNext();) {
            QName portName = ports.next();
            String portnameString = portName.getLocalPart();
            if (portnameString.equals(nameString)) {
                return service.getPort(name, serviceendpointClass);
            }
        }

        // no ports have been found
        throw new ServiceException("Port-component-ref : " + name + " not found");
    }

    /**
     * @param portComponentRef List
     */
    public void setPortComponentRef(Hashtable<String,QName> portComponentRef) {
        this.portComponentRef = portComponentRef;
    }

    /**
     * @param args Method call arguments
     * @return Returns the correct Port
     * @throws ServiceException if port's QName is an unknown Port
     */
    private Remote getProxyPortClass(Object[] args) 
    throws ServiceException {
        Class<?> serviceendpointClass = (Class<?>) args[0];

        if (this.portComponentRef == null)
            return service.getPort(serviceendpointClass);

        QName portname = this.portComponentRef.get(serviceendpointClass.getName());
        if (portname != null) {
            return service.getPort(portname, serviceendpointClass);
        } else {
            return service.getPort(serviceendpointClass);
        }
    }

}
