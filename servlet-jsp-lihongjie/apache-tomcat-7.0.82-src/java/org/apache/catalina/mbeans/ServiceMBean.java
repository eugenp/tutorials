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

package org.apache.catalina.mbeans;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;

import org.apache.catalina.Executor;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.modeler.BaseModelMBean;

public class ServiceMBean extends BaseModelMBean {

    public ServiceMBean() 
        throws MBeanException, RuntimeOperationsException {
        
        super();
        
    }
    
    /**
     * Add a new Connector to the set of defined Connectors, and associate it
     * with this Service's Container.
     *
     * @param address The IP address on which to bind
     * @param port TCP port number to listen on
     * @param isAjp Create a AJP/1.3 Connector
     * @param isSSL Create a secure Connector
     * 
     * @throws MBeanException 
     */
    public void addConnector(String address, int port, boolean isAjp, boolean isSSL) throws MBeanException {
        
        Service service; 
        try {
            service = (Service)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        Connector connector = new Connector();
        if ((address!=null) && (address.length()>0)) {
            connector.setProperty("address", address);
        }
        
        connector.setPort(port);
        connector.setProtocol(isAjp ? "AJP/1.3" : "HTTP/1.1");
        connector.setSecure(isSSL);
        connector.setScheme(isSSL ? "https" : "http");

        service.addConnector(connector);       

    }
    
    /**
     * Adds a named executor to the service
     * @param type Classname of the Executor to be added
     * @throws MBeanException 
     */
    public void addExecutor(String type) throws MBeanException {
        
        Service service; 
        try {
            service = (Service)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        Executor executor;
        try {
             executor = (Executor)Class.forName(type).newInstance();
        } catch (InstantiationException e) {
            throw new MBeanException(e);
        } catch (IllegalAccessException e) {
            throw new MBeanException(e);
        } catch (ClassNotFoundException e) {
            throw new MBeanException(e);
        }
        
        service.addExecutor(executor);
        
    }
    
    /**
     * Find and return the set of Connectors associated with this Service.
     * @throws MBeanException 
     */
    public String[] findConnectors() throws MBeanException {
        
        Service service; 
        try {
            service = (Service)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        Connector[] connectors = service.findConnectors(); 
        String[] str = new String[connectors.length];
        
        for(int i=0; i< connectors.length; i++){
            str[i] = connectors[i].toString();
        }
        
        return str;

    }
    
    /**
     * Retrieves all executors
     * @throws MBeanException 
     */
    public String[] findExecutors() throws MBeanException {

        Service service; 
        try {
            service = (Service)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        Executor[] executors = service.findExecutors();
        String[] str = new String[executors.length];
        
        for(int i=0; i< executors.length; i++){
            str[i] = executors[i].toString();
        }
        
        return str;
    }
    
    /**
     * Retrieves executor by name
     * @param name Name of the executor to be retrieved
     * @throws MBeanException 
     */
    public String getExecutor(String name) throws MBeanException{
       
        Service service; 
        try {
            service = (Service)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        Executor executor = service.getExecutor(name);
        return executor.toString();
        
    }

}
