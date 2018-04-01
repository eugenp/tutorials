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

import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;

import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Valve;
import org.apache.catalina.core.ContainerBase;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.HostConfig;
import org.apache.catalina.util.LifecycleMBeanBase;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.modeler.BaseModelMBean;

public class ContainerMBean extends BaseModelMBean {

    /**
     * Construct a <code>ModelMBean</code> with default
     * <code>ModelMBeanInfo</code> information.
     *
     * @exception MBeanException if the initializer of an object
     *  throws an exception
     * @exception RuntimeOperationsException if an IllegalArgumentException
     *  occurs
     */
    public ContainerMBean() 
        throws MBeanException, RuntimeOperationsException {
        
        super();
    }
    
    /**
     * Add a new child Container to those associated with this Container,
     * if supported. Won't start the child yet. Has to be started with a call to
     * Start method after necessary configurations are done.
     * 
     * @param type ClassName of the child to be added
     * @param name Name of the child to be added
     * 
     * @exception MBeanException if the child cannot be added
     */
    public void addChild(String type, String name) throws MBeanException{ 
        Container contained = null;
        try {
            contained = (Container)Class.forName(type).newInstance();
            contained.setName(name);
            
            if(contained instanceof StandardHost){
                HostConfig config = new HostConfig();
                contained.addLifecycleListener(config);
            } else if(contained instanceof StandardContext){
                ContextConfig config = new ContextConfig();
                contained.addLifecycleListener(config);
            }

        } catch (InstantiationException e) {
            throw new MBeanException(e);
        } catch (IllegalAccessException e) {
            throw new MBeanException(e);
        } catch (ClassNotFoundException e) {
            throw new MBeanException(e);
        }
        
        boolean oldValue= true;
        
        ContainerBase container = null;
        try {
            container = (ContainerBase)getManagedResource();
            oldValue = container.getStartChildren();
            container.setStartChildren(false);
            container.addChild(contained);
            contained.init();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        } catch (LifecycleException e){
            throw new MBeanException(e);
        } finally {
            if(container != null) {
                container.setStartChildren(oldValue);
            }
        }
    }
    
    /**
     * Remove an existing child Container from association with this parent
     * Container.
     *
     * @param name Name of the existing child Container to be removed
     */
    public void removeChild(String name) throws MBeanException{
        if(name != null){
            try {
                Container container = (Container)getManagedResource();
                Container contained = container.findChild(name);
                container.removeChild(contained);
            } catch (InstanceNotFoundException e) {
                throw new MBeanException(e);
            } catch (RuntimeOperationsException e) {
                throw new MBeanException(e);
            } catch (InvalidTargetObjectTypeException e) {
                throw new MBeanException(e);
            }
        }
    }
    
    /**
     * Adds a valve to this Container instance.
     *
     * @param valveType ClassName of the valve to be added
     * 
     * @exception MBeanException if a component cannot be removed
     */
    public String addValve(String valveType) throws MBeanException{ 
        Valve valve = null;
        try {
            valve = (Valve)Class.forName(valveType).newInstance();
        } catch (InstantiationException e) {
            throw new MBeanException(e);
        } catch (IllegalAccessException e) {
            throw new MBeanException(e);
        } catch (ClassNotFoundException e) {
            throw new MBeanException(e);
        }
        
        if (valve == null) {
            return null;
        }
            
        try {
            ContainerBase container = (ContainerBase)getManagedResource();
            container.addValve(valve);
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        return ((LifecycleMBeanBase)valve).getObjectName().toString();
    }
    
    /**
     * Remove an existing Valve.
     *
     * @param valveName MBean Name of the Valve to remove
     *
     * @exception MBeanException if a component cannot be removed
     */
    public void removeValve(String valveName) throws MBeanException{
        ContainerBase container=null;
        try {
            container = (ContainerBase)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        ObjectName oname;
        try {
            oname = new ObjectName(valveName);
        } catch (MalformedObjectNameException e) {
            throw new MBeanException(e);
        } catch (NullPointerException e) {
            throw new MBeanException(e);
        }
        
        if(container != null){
            Valve[] valves = container.getPipeline().getValves();
            for (int i = 0; i < valves.length; i++) {
                ObjectName voname = ((ValveBase) valves[i]).getObjectName();
                if (voname.equals(oname)) {
                    container.getPipeline().removeValve(valves[i]);
                }
            }
        }
    }
    
    /**
     * Add a LifecycleEvent listener to this component.
     *
     * @param type ClassName of the listener to add
     */
    public void addLifeCycleListener(String type) throws MBeanException{
        LifecycleListener listener = null;
        try {
            listener = (LifecycleListener)Class.forName(type).newInstance();
        } catch (InstantiationException e) {
            throw new MBeanException(e);
        } catch (IllegalAccessException e) {
            throw new MBeanException(e);
        } catch (ClassNotFoundException e) {
            throw new MBeanException(e);
        }
        
        if(listener != null){
            try {
                ContainerBase container = (ContainerBase)getManagedResource();
                container.addLifecycleListener(listener);
            } catch (InstanceNotFoundException e) {
                throw new MBeanException(e);
            } catch (RuntimeOperationsException e) {
                throw new MBeanException(e);
            } catch (InvalidTargetObjectTypeException e) {
                throw new MBeanException(e);
            }
        }
    }
    
    /**
     * Remove a LifecycleEvent listeners from this component.
     *
     * @param type The ClassName of the listeners to be removed. 
     * Note that all the listeners having given ClassName will be removed. 
     */
    public void removeLifeCycleListeners(String type) throws MBeanException{
        ContainerBase container=null;
        try {
            container = (ContainerBase)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        LifecycleListener[] listeners = container.findLifecycleListeners();
        for(LifecycleListener listener: listeners){
            if(listener.getClass().getName().equals(type)){
                container.removeLifecycleListener(listener);
            }
        }
    }

    
    /**
     * List the class name of each of the lifecycle listeners added to this
     * container.
     */
    public String[] findLifecycleListenerNames() throws MBeanException {
        ContainerBase container = null;
        List<String> result = new ArrayList<String>();

        try {
            container = (ContainerBase) getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }

        LifecycleListener[] listeners = container.findLifecycleListeners();
        for(LifecycleListener listener: listeners){
            result.add(listener.getClass().getName());
        }

        return result.toArray(new String[result.size()]);
    }

    
    /**
     * List the class name of each of the container listeners added to this
     * container.
     */
    public String[] findContainerListenerNames() throws MBeanException {
        ContainerBase container = null;
        List<String> result = new ArrayList<String>();

        try {
            container = (ContainerBase) getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }

        ContainerListener[] listeners = container.findContainerListeners();
        for(ContainerListener listener: listeners){
            result.add(listener.getClass().getName());
        }

        return result.toArray(new String[result.size()]);
    }
}
