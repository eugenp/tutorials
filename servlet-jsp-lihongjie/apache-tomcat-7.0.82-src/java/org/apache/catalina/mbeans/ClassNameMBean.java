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


import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;

import org.apache.tomcat.util.modeler.BaseModelMBean;


/**
 * <p>A convenience base class for <strong>ModelMBean</strong> implementations
 * where the underlying base class (and therefore the set of supported
 * properties) is different for varying implementations of a standard
 * interface.  For Catalina, that includes at least the following:
 * Connector, Logger, Realm, and Valve.  This class creates an artificial
 * MBean attribute named <code>className</code>, which reports the fully
 * qualified class name of the managed object as its value.</p>
 *
 * @author Craig R. McClanahan
 */
public class ClassNameMBean extends BaseModelMBean {


     // ---------------------------------------------------------- Constructors


     /**
      * Construct a <code>ModelMBean</code> with default
      * <code>ModelMBeanInfo</code> information.
      *
      * @exception MBeanException if the initialize of an object
      *  throws an exception
      * @exception RuntimeOperationsException if an IllegalArgumentException
      *  occurs
      */
     public ClassNameMBean()
         throws MBeanException, RuntimeOperationsException {

         super();

     }


     // ------------------------------------------------------------ Properties


     /**
      * Return the fully qualified Java class name of the managed object
      * for this MBean.
      */
     @Override
    public String getClassName() {

         return (this.resource.getClass().getName());

     }


 }
