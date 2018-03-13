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


package org.apache.catalina.startup;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.NamingResources;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.digester.Rule;


/**
 * <p>Rule implementation that calls a method on the (top-1) (parent)
 * object, passing the top object (child) as an argument.  It is
 * commonly used to establish parent-child relationships.</p>
 *
 * <p>This rule now supports more flexible method matching by default.
 * It is possible that this may break (some) code 
 * written against release 1.1.1 or earlier.
 * </p> 
 */

public class SetNextNamingRule extends Rule {


    // ----------------------------------------------------------- Constructors

    
    /**
     * Construct a "set next" rule with the specified method name.
     *
     * @param methodName Method name of the parent method to call
     * @param paramType Java class of the parent method's argument
     *  (if you wish to use a primitive type, specify the corresponding
     *  Java wrapper class instead, such as <code>java.lang.Boolean</code>
     *  for a <code>boolean</code> parameter)
     */
    public SetNextNamingRule(String methodName,
                       String paramType) {

        this.methodName = methodName;
        this.paramType = paramType;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The method name to call on the parent object.
     */
    protected String methodName = null;


    /**
     * The Java class name of the parameter type expected by the method.
     */
    protected String paramType = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Process the end of this element.
     * 
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     */
    @Override
    public void end(String namespace, String name) throws Exception {

        // Identify the objects to be used
        Object child = digester.peek(0);
        Object parent = digester.peek(1);

        NamingResources namingResources = null;
        if (parent instanceof Context) {
            namingResources = ((Context) parent).getNamingResources();
        } else {
            namingResources = (NamingResources) parent;
        }
        
        // Call the specified method
        IntrospectionUtils.callMethod1(namingResources, methodName,
                child, paramType, digester.getClassLoader());

    }


    /**
     * Render a printable version of this Rule.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("SetNextRule[");
        sb.append("methodName=");
        sb.append(methodName);
        sb.append(", paramType=");
        sb.append(paramType);
        sb.append("]");
        return (sb.toString());

    }


}
