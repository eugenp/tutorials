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
package org.apache.tomcat.util.modeler;

import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

/**
 * <p>Internal configuration information for an <code>Operation</code>
 * descriptor.</p>
 *
 * @author Craig R. McClanahan
 */
public class OperationInfo extends FeatureInfo {

    static final long serialVersionUID = 4418342922072614875L;

    // ----------------------------------------------------------- Constructors

    /**
     * Standard zero-arguments constructor.
     */
    public OperationInfo() {
        super();
    }
   

    // ----------------------------------------------------- Instance Variables

    protected String impact = "UNKNOWN";
    protected String role = "operation";
    protected final ReadWriteLock parametersLock = new ReentrantReadWriteLock();
    protected ParameterInfo parameters[] = new ParameterInfo[0];


    // ------------------------------------------------------------- Properties

    /**
     * The "impact" of this operation, which should be a (case-insensitive)
     * string value "ACTION", "ACTION_INFO", "INFO", or "UNKNOWN".
     */
    public String getImpact() {
        return this.impact;
    }

    public void setImpact(String impact) {
        if (impact == null)
            this.impact = null;
        else
            this.impact = impact.toUpperCase(Locale.ENGLISH);
    }


    /**
     * The role of this operation ("getter", "setter", "operation", or
     * "constructor").
     */
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    /**
     * The fully qualified Java class name of the return type for this
     * operation.
     */
    public String getReturnType() {
        if(type == null) {
            type = "void";
        }
        return type;
    }

    public void setReturnType(String returnType) {
        this.type = returnType;
    }

    /**
     * The set of parameters for this operation.
     */
    public ParameterInfo[] getSignature() {
        Lock readLock = parametersLock.readLock();
        try {
            readLock.lock();
            return this.parameters;
        } finally {
            readLock.unlock();
        }
    }

    // --------------------------------------------------------- Public Methods


    /**
     * Add a new parameter to the set of arguments for this operation.
     *
     * @param parameter The new parameter descriptor
     */
    public void addParameter(ParameterInfo parameter) {

        Lock writeLock = parametersLock.writeLock();
        try {
            writeLock.lock();
            ParameterInfo results[] = new ParameterInfo[parameters.length + 1];
            System.arraycopy(parameters, 0, results, 0, parameters.length);
            results[parameters.length] = parameter;
            parameters = results;
            this.info = null;
        } finally {
            writeLock.unlock();
        }
    }


    /**
     * Create and return a <code>ModelMBeanOperationInfo</code> object that
     * corresponds to the attribute described by this instance.
     */
    MBeanOperationInfo createOperationInfo() {

        // Return our cached information (if any)
        if (info == null) {
            // Create and return a new information object
            int impact = MBeanOperationInfo.UNKNOWN;
            if ("ACTION".equals(getImpact()))
                impact = MBeanOperationInfo.ACTION;
            else if ("ACTION_INFO".equals(getImpact()))
                impact = MBeanOperationInfo.ACTION_INFO;
            else if ("INFO".equals(getImpact()))
                impact = MBeanOperationInfo.INFO;
    
            info = new MBeanOperationInfo(getName(), getDescription(), 
                                          getMBeanParameterInfo(),
                                          getReturnType(), impact);
        }
        return (MBeanOperationInfo)info;
    }

    protected MBeanParameterInfo[] getMBeanParameterInfo() {
        ParameterInfo params[] = getSignature();
        MBeanParameterInfo parameters[] =
            new MBeanParameterInfo[params.length];
        for (int i = 0; i < params.length; i++)
            parameters[i] = params[i].createParameterInfo();
        return parameters;
    }
}
