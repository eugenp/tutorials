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

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.deploy.SecurityConstraint;

public class ContextMBean extends ContainerMBean {

    public ContextMBean() throws MBeanException, RuntimeOperationsException {
        
        super();
    }
    
     /**
     * Return the set of application parameters for this application.
     */
    public String[] findApplicationParameters() throws MBeanException {
        
        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        ApplicationParameter[] params = context.findApplicationParameters();
        String[] stringParams = new String[params.length];
        for(int counter=0; counter < params.length; counter++){
           stringParams[counter]=params[counter].toString();
        }
        
        return stringParams;
        
    }
    
    /**
     * Return the security constraints for this web application.
     * If there are none, a zero-length array is returned.
     */
    public String[] findConstraints() throws MBeanException {

        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        SecurityConstraint[] constraints = context.findConstraints();
        String[] stringConstraints = new String[constraints.length];
        for(int counter=0; counter < constraints.length; counter++){
            stringConstraints[counter]=constraints[counter].toString();
        }
        
        return stringConstraints;
        
    }
    
    /**
     * Return the error page entry for the specified HTTP error code,
     * if any; otherwise return <code>null</code>.
     *
     * @param errorCode Error code to look up
     */
    public String findErrorPage(int errorCode) throws MBeanException {

        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        return context.findErrorPage(errorCode).toString();
        
    }
    
    /**
     * Return the error page entry for the specified Java exception type,
     * if any; otherwise return <code>null</code>.
     *
     * @param exceptionType Exception type to look up
     */
    public String findErrorPage(String exceptionType) throws MBeanException {

        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        return context.findErrorPage(exceptionType).toString();
        
    }
    
    /**
     * Return the set of defined error pages for all specified error codes
     * and exception types.
     */
    public String[] findErrorPages() throws MBeanException {
        
        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        ErrorPage[] pages = context.findErrorPages();
        String[] stringPages = new String[pages.length];
        for(int counter=0; counter < pages.length; counter++){
            stringPages[counter]=pages[counter].toString();
        }
        
        return stringPages;
        
    }
    
    /**
     * Return the filter definition for the specified filter name, if any;
     * otherwise return <code>null</code>.
     *
     * @param name Filter name to look up
     */
    public String findFilterDef(String name) throws MBeanException {
        
        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        FilterDef filterDef = context.findFilterDef(name);
        return filterDef.toString();
        
    }
    
    /**
     * Return the set of defined filters for this Context.
     */
    public String[] findFilterDefs() throws MBeanException {
        
        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }

        FilterDef[] filterDefs = context.findFilterDefs();
        String[] stringFilters = new String[filterDefs.length];
        for(int counter=0; counter < filterDefs.length; counter++){
            stringFilters[counter]=filterDefs[counter].toString();
        }

        return stringFilters;
        
    }
    
    /**
     * Return the set of filter mappings for this Context.
     */
    public String[] findFilterMaps() throws MBeanException {
        
        Context context; 
        try {
            context = (Context)getManagedResource();
        } catch (InstanceNotFoundException e) {
            throw new MBeanException(e);
        } catch (RuntimeOperationsException e) {
            throw new MBeanException(e);
        } catch (InvalidTargetObjectTypeException e) {
            throw new MBeanException(e);
        }
        
        FilterMap[] maps = context.findFilterMaps();
        String[] stringMaps = new String[maps.length];
        for(int counter=0; counter < maps.length; counter++){
            stringMaps[counter]=maps[counter].toString();
        }
        
        return stringMaps;
        
    }

}
