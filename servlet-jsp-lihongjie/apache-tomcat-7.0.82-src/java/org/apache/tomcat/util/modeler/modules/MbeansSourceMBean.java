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
package org.apache.tomcat.util.modeler.modules;

import java.util.List;

import javax.management.ObjectName;


/**
 * This mbean will load an extended mlet file ( similar in syntax with jboss ).
 * It'll keep track of all attribute changes and update the file when attributes
 * change. 
 * @deprecated Unused: Will be removed in Tomcat 8.0.x
 */
@Deprecated
public interface MbeansSourceMBean
{
    /** Set the source to be used to load the mbeans
     * 
     * @param source File or URL
     */ 
    public void setSource( Object source );
    
    public Object getSource();
    
    /** Return the list of loaded mbeans names
     * 
     * @return List of ObjectName
     */ 
    public List<ObjectName> getMBeans();

    /** Load the mbeans from the source. Called automatically on init() 
     * 
     * @throws Exception
     */ 
    public void load() throws Exception;
    
    /** Call the init method on all mbeans. Will call load if not done already
     * 
     * @throws Exception
     */ 
    public void init() throws Exception;

    /** Save the file.
     */ 
    public void save();
}
